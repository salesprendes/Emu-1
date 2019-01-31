package objetos.entidades.personajes;

import java.util.Arrays;
import java.util.Map;
import java.util.StringJoiner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import juego.acciones.JuegoAccion;
import juego.enums.EstadosJuego;
import juego.enums.TipoCanales;
import juego.enums.TipoCaracteristica;
import juego.enums.TipoDirecciones;
import main.Configuracion;
import main.Main;
import main.consola.Consola;
import objetos.cuentas.Cuentas;
import objetos.entidades.Entidades;
import objetos.entidades.Experiencia;
import objetos.entidades.caracteristicas.Caracteristicas;
import objetos.entidades.caracteristicas.DefaultCaracteristicas;
import objetos.mapas.Celdas;
import objetos.mapas.Mapas;

public class Personajes implements Entidades
{
	private int id; // superara 32767 (4 bytes)
	private int color_1, color_2, color_3, puntos_stats, puntos_hechizos, puntos_vida, puntos_vida_maxima; // superara 32767 (4 bytes)
	private short nivel, gfx, tamano; // no superara el maximo permitido 32767 (2 bytes)
	private final short servidor;
	private byte titulo, sexo; //No superara los 258 (1 byte)
	private long experiencia, kamas;
	private String nombre; //maximo 20 caracteres restringido por database
	private Razas raza;
	final private Cuentas cuenta; //referencia a la cuenta del personaje
	private boolean es_mercante = false;
	private Mapas mapa;
	private Celdas celda;
	final private Derechos derechos;
	final private Restricciones restricciones;
	final private Emotes emotes;
	private String canales;
	private Alineamientos alineamiento;
	private Caracteristicas stats_principales;
	private TipoDirecciones orientacion = TipoDirecciones.ABAJO_DERECHA;
	private final JuegoAccion juego_acciones;
	private final Map<Integer, Items> items = new ConcurrentHashMap<Integer, Items>();

	/** Caches **/
	private String cache_gm = "";
	private String cache_as = "";

	/** Condiciones **/

	private static final ConcurrentHashMap<Integer, Personajes> personajes_cargados = new ConcurrentHashMap<Integer, Personajes>();

	public Personajes(final int _id, final String _nombre_personaje, final int _color_1, final int _color_2, final int _color_3, final short _nivel, final short _gfx, final short _tamano, final short mapa_id, final short _celda_id, final byte _sexo, final long _experiencia, final long _kamas, final byte _porcentaje_vida, final byte _raza_id, final DefaultCaracteristicas _stats_principales, final int _emotes, final String _canales, final int cuenta_id, final int derecho_id, final short restriccion_id, final short servidor_id)
	{
		id = _id;
		nombre = _nombre_personaje;
		color_1 = _color_1;
		color_2 = _color_2;
		color_3 = _color_3;
		nivel = _nivel;
		gfx = _gfx;
		tamano = _tamano;
		sexo = _sexo;
		experiencia = _experiencia;
		kamas = _kamas;
		raza = Razas.get_Razas_Cargadas(_raza_id);
		stats_principales = _stats_principales;
		
		for (TipoCaracteristica stat : TipoCaracteristica.values())//agrega stats base de la raza
			stats_principales.set_Caracteristica(stat, raza.get_Stats_base(nivel).get_Caracteristica(stat));
	
		emotes = new Emotes(_emotes);
		canales = _canales;
		cuenta = Cuentas.get_Cuenta_Cargada(cuenta_id);
		try 
		{
			mapa = Mapas.get_Mapas_Cargados(mapa_id);
			set_Celda(mapa.get_Celda(_celda_id));
		}
		catch (Exception e)
		{
			mapa = Mapas.get_Mapas_Cargados(raza.get_Mapa_id_comienzo());
			set_Celda(mapa.get_Celda(raza.get_Celda_id_comienzo()));
		};
		derechos = new Derechos(derecho_id);
		restricciones = new Restricciones(restriccion_id);
		servidor = servidor_id;

		get_Actualizar_Vida_Maxima();
		if (_porcentaje_vida >= 100)
			puntos_vida = puntos_vida_maxima;
		else
			puntos_vida = puntos_vida_maxima * _porcentaje_vida / 100;

		if(cuenta != null && id > 0)
			cuenta.agregar_Personaje(this);

		juego_acciones = new JuegoAccion(this);
		personajes_cargados.put(id, this);
	}

	public synchronized static Personajes crear_Personaje(final int cuenta_id, final String nombre_personaje, final int color_1, final int color_2, final int color_3, final Razas raza, final byte sexo)
	{
		Personajes nuevo_personaje = new Personajes(-1, nombre_personaje, color_1, color_2, color_3, /** nivel **/ (short)1, /** gfx**/ (short) ((10 * raza.get_Id()) + sexo), (short)100, raza.get_Mapa_id_comienzo(), raza.get_Celda_id_comienzo(), sexo, /** experiencia **/ (long)0, /** kamas**/ (long)0, /** porcentaje vida**/ (byte)100, raza.get_Id(), new DefaultCaracteristicas(), 1, "*p?:", cuenta_id, 8192, (short)8, Configuracion.SERVIDOR_ID);
		Main.get_Database().get_Personajes().get_Guardar_Personaje(nuevo_personaje);
		return nuevo_personaje;
	}

	/** Get & Setters **/
	public int get_Id() 
	{
		return id;
	}

	public void set_Id(int _id)//creacion de personaje
	{
		id = _id;
	}

	public String get_Nombre(final boolean mostrar_admin)
	{
		return cuenta.get_Rango_cuenta() > 0 && mostrar_admin ? "[" + nombre + "]" : nombre;
	}

	public void set_Nombre(String _nombre) 
	{
		nombre = _nombre;
	}

	public int get_Nivel() 
	{
		return nivel;
	}

	public void set_Nivel(short _nivel) 
	{
		nivel = _nivel;
	}

	public short get_Gfx() 
	{
		return gfx;
	}

	public void set_Gfx(final short _gfx)
	{
		gfx = _gfx;
		get_Limpiar_Cache_Gm();
	}

	public long get_Experiencia()
	{
		return experiencia;
	}

	public void set_Experiencia(long _experiencia)
	{
		experiencia = _experiencia;
	}

	public synchronized void set_Celda(Celdas _celda)
	{
		boolean mapa_diferente = _celda == null || celda == null ? true : (_celda.get_Mapa() != celda.get_Mapa());

		if (celda != null) 
		{
			celda.get_Eliminar_Personaje(this, mapa_diferente || !get_Esta_Conectado());
		}
		if (_celda != null) 
		{
			celda = _celda;
			celda.get_Agregar_Jugador(this, mapa_diferente && get_Esta_Conectado());
		}
	}

	public int get_Color_1() 
	{
		return color_1;
	}

	public int get_Color_2() 
	{
		return color_2;
	}

	public int get_Color_3() 
	{
		return color_3;
	}

	public final void set_Colores(int _color_1, int _color_2, int _color_3) 
	{
		if (_color_1 < -1)
			_color_1 = -1;
		else if (_color_1 > 16777215)
			_color_1 = 16777215;

		if (_color_2 < -1)
			_color_2 = -1;
		else if (_color_2 > 16777215)
			_color_2 = 16777215;

		if (_color_3 < -1)
			_color_3 = -1;
		else if (_color_3 > 16777215)
			_color_3 = 16777215;

		color_1 = _color_1;
		color_2 = _color_2;
		color_3 = _color_3;
		get_Limpiar_Cache_Gm();
	}

	public String[] get_Array_Colores() 
	{
		return Arrays.stream(new int[] {color_1, color_2, color_3}).mapToObj(valor -> valor == -1 ? "-1" : Integer.toHexString(valor)).toArray(String[]::new);
	}

	public Cuentas get_Cuenta()
	{
		return cuenta;
	}

	public boolean get_Es_mercante()
	{
		return es_mercante;
	}

	public void set_Es_mercante(final boolean _es_mercante)
	{
		es_mercante = _es_mercante;
	}

	public Razas get_Raza() 
	{
		return raza;
	}

	public int get_Servidor()
	{
		return servidor;
	}

	public boolean get_Esta_Conectado()
	{
		return cuenta.get_Juego_socket() == null ? false : cuenta.get_Juego_socket().get_Estado_Juego() == EstadosJuego.CONECTADO;
	}

	public TipoEntidades get_Tipo() 
	{
		return TipoEntidades.PERSONAJE;
	}

	public TipoDirecciones get_Orientacion()
	{
		return orientacion;
	}

	public TipoDirecciones set_Orientacion(TipoDirecciones _orientacion)
	{
		return orientacion = _orientacion;
	}

	public byte get_Sexo()
	{
		return sexo;
	}

	public void set_Sexo(byte _sexo)
	{
		sexo = _sexo;
	}

	public long get_Kamas()
	{
		return kamas;
	}

	public void set_Kamas(long _kamas)
	{
		kamas = _kamas;
	}

	public Mapas get_Mapa()
	{
		return mapa;
	}

	public short get_Mapa_Id()
	{
		return mapa.get_Id();
	}

	public final Celdas get_Celda()
	{
		return celda;
	}

	public final short get_Celda_Id() 
	{
		return celda.get_Id();
	}

	public Derechos get_Derechos()
	{
		return derechos;
	}

	public final Restricciones get_Restricciones()
	{
		return restricciones;
	}

	public short get_Tamano()
	{
		return tamano;
	}

	public void set_Tamano(short _tamano)
	{
		tamano = _tamano;
	}
	
	public final String get_Canales()
	{
		return canales;
	}

	public final Emotes get_Emotes()
	{
		return emotes;
	}

	public Alineamientos get_Alineamiento()
	{
		return alineamiento;
	}

	public int get_Alineamiento_Id()
	{
		return alineamiento != null ? alineamiento.get_Alineamiento().get_Id() : 0;
	}

	public int get_Alineamiento_Honor()
	{
		return alineamiento != null ? alineamiento.get_Honor() : 0;
	}

	public int get_Alineamiento_Deshonor()
	{
		return alineamiento != null ? alineamiento.get_Deshonor() : 0;
	}

	public int get_Alineamiento_Orden()
	{
		return alineamiento != null ? alineamiento.get_Orden() : 0;
	}

	public int get_Alineamiento_Orden_Nivel()
	{
		return alineamiento != null ? alineamiento.get_Orden_nivel() : 0;
	}

	public int get_Alineamiento_Alas_Activadas_Nivel()
	{
		return alineamiento != null ? (alineamiento.get_Esta_Activado() ? get_Grado_Alas() : 0) : 0;
	}

	public byte get_Alineamiento_Tiene_Alas_Activadas()
	{
		return (byte) (alineamiento != null ? (alineamiento.get_Esta_Activado() ? 1 : 0) : 0);
	}

	public void set_Alineamiento(Alineamientos _alineamiento)
	{
		alineamiento = _alineamiento;
	}

	public final boolean get_Tiene_Emote(final int emote) 
	{
		return (emotes.get() & ((int) Math.pow(2, emote - 1))) != 0;
	}
	
	public byte get_Porcentaje_Vida() 
	{
		return (byte) Math.min(100, (100 * puntos_vida / puntos_vida_maxima));
	}

	public void get_Actualizar_Vida() 
	{
		final float porcentaje_vida = 100f * puntos_vida / puntos_vida_maxima;
		get_Actualizar_Vida_Maxima();
		puntos_vida = Math.round(puntos_vida_maxima * porcentaje_vida / 100);
	}

	public void get_Actualizar_Vida_Maxima() 
	{
		puntos_vida_maxima = (nivel - 1) * 5 + raza.get_Vida_Base() + stats_principales.get_Caracteristica(TipoCaracteristica.VITALIDAD);
	}

	public Caracteristicas get_Stats_principales()
	{
		return stats_principales;
	}
	
	public int get_Stats_Base(TipoCaracteristica caracteristica) 
	{
		//stats_principales.set_Caracteristica(TipoCaracteristica.PUNTOS_ACCION, raza.get_Stats_base(nivel).get_Caracteristica(TipoCaracteristica.PUNTOS_ACCION));

        return stats_principales.get_Caracteristica(caracteristica);
    }

	public Map<Integer, Items> get_objetos()
	{
		return items;
	}

	public JuegoAccion get_Juego_Acciones()
	{
		return juego_acciones;
	}

	public final void get_Limpiar_Cache_Gm()
	{
		cache_gm = "";
	}

	public final void get_Conexion_juego()
	{
		if (cuenta != null || cuenta.get_Juego_socket() != null)
		{
			if (es_mercante)
			{
				es_mercante = false;
				mapa.get_Personajes().forEach(personaje -> personaje.get_Cuenta().get_Juego_socket().enviar_Paquete("GM|-" + id));
			}

			cuenta.get_Juego_socket().get_Iniciar_Buffering();

			cuenta.set_Personaje_jugando(this);
			cuenta.get_Juego_socket().enviar_Paquete(get_Paquete_Ask());

			Especialidades especialidad = Especialidades.get_Especialidad(get_Alineamiento_Orden(), get_Alineamiento_Orden_Nivel());
			if (especialidad != null)
				cuenta.get_Juego_socket().enviar_Paquete("ZS" + especialidad.get_Id());

			cuenta.get_Juego_socket().enviar_Paquete("cC+" + canales + (nivel <= 16 ? TipoCanales.INCARNAM.get_Identificador() : "") + (cuenta.get_Rango_cuenta() > 0 ? TipoCanales.ADMINISTRADOR.get_Identificador() + TipoCanales.MEETIC.get_Identificador() : ""));
			cuenta.get_Juego_socket().enviar_Paquete("AR" + derechos.get_Convertir_Base36());
			cuenta.get_Juego_socket().enviar_Paquete("eL" + emotes.get());//Lista emotes
			cuenta.get_Juego_socket().enviar_Paquete("Im189");// Im bienvenida dofus
			cuenta.get_Juego_socket().enviar_Paquete("Im0153;" + cuenta.get_Ip());
			mapa.get_Personajes().forEach(personaje -> personaje.get_Cuenta().get_Juego_socket().enviar_Paquete("GM|+" + get_Paquete_Gm()));
			cuenta.get_Juego_socket().get_Detener_Buffering();
		}
		else
		{
			Consola.println("El personaje " + get_Nombre(false) + " tiene como entrada personaje NULL");
			return;
		}
	}

	public final void get_Crear_Juego()
	{
		cuenta.get_Juego_socket().enviar_Paquete("GCK|1|" + get_Nombre(false));
		cuenta.get_Juego_socket().enviar_Paquete("GDM|" + mapa.get_Id() + '|' + mapa.get_Fecha() + '|' + mapa.get_Key());
		cuenta.get_Juego_socket().enviar_Paquete(get_Paquete_As());

		celda.get_Agregar_Jugador(this, true);
	}

	/** Constructores Paquetes **/
	public final String get_Paquete_Ask()
	{
		final StringJoiner paquete = new StringJoiner("|").add("ASK");

		paquete.add(Integer.toString(id));
		paquete.add(get_Nombre(true));
		paquete.add(Integer.toString(nivel));
		paquete.add(Integer.toString(raza.get_Id()));
		paquete.add(Integer.toString(sexo));//Sexo
		paquete.add(Integer.toString(gfx));
		paquete.add(String.join("|", get_Array_Colores()));
		paquete.add(items.values().stream().map(Items::get_Item_String).collect(Collectors.joining(";")));

		return paquete.toString();
	}

	public String get_Paquete_Alk()
	{
		final StringBuilder personaje = new StringBuilder(20);

		personaje.append('|');
		personaje.append(id).append(';');
		personaje.append(get_Nombre(true)).append(';');
		personaje.append(nivel).append(';');
		personaje.append(gfx).append(';');
		personaje.append(String.join(";", get_Array_Colores()));
		personaje.append("").append(';');//objetos
		personaje.append(es_mercante ? 1 : 0).append(';');//mercante
		personaje.append(servidor).append(';');
		personaje.append(';');//DeathCount
		personaje.append(';');//LevelMax

		return personaje.toString();
	}

	public String get_Paquete_Gm()
	{
		if(cache_gm.isEmpty())
		{
			StringBuilder personaje = new StringBuilder(30);

			personaje.append(get_Tipo().get_Id()).append(';');
			personaje.append(id).append(';').append(get_Nombre(true)).append(';').append(raza.get_Id());
			personaje.append((titulo > 0 ? ("," + titulo + ";") : (';')));
			personaje.append(gfx).append('^').append(tamano).append(';');
			personaje.append(sexo).append(';').append(get_Alineamiento_Id()).append(',');//Sexo + Alineacion
			personaje.append(get_Alineamiento_Orden_Nivel()).append(",").append(get_Alineamiento_Alas_Activadas_Nivel()).append(',');
			personaje.append(nivel + id).append(',');
			personaje.append(alineamiento != null ? (alineamiento.get_Deshonor() > 0 ? 1 : 0) : 0).append(';');
			personaje.append(String.join(";", get_Array_Colores())).append(';');//3 - colores

			cache_gm = personaje.toString();
		}
		return new StringBuilder().append(celda.get_Id()).append(';').append(orientacion.ordinal()).append(';').append(cache_gm).toString();
	}

	public String get_Paquete_As()
	{
		if(cache_as.isEmpty())
		{
			StringBuilder paquete = new StringBuilder(500);

			paquete.append("As").append(get_Experiencia_Personaje(",")).append('|');
			paquete.append(kamas).append('|').append(puntos_stats).append('|').append(puntos_hechizos).append('|');
			paquete.append(get_Alineamiento_Id()).append('~').append(get_Alineamiento_Id()).append(',').append(get_Alineamiento_Orden_Nivel()).append(',').append(get_Grado_Alas()).append(',').append(get_Alineamiento_Honor()).append(',').append(get_Alineamiento_Deshonor()).append(',').append(get_Alineamiento_Tiene_Alas_Activadas()).append('|');

			get_Actualizar_Vida();
			paquete.append(puntos_vida).append(',').append(puntos_vida_maxima).append('|');
			paquete.append(10000).append(',').append(servidor == 22 ? 1 : 10000).append('|');//energia
			paquete.append(get_Iniciativa()).append('|');//iniciativa
			paquete.append(0).append('|');//prospeccion

			for (TipoCaracteristica stat : TipoCaracteristica.values())
				paquete.append(stats_principales.get_Caracteristica(stat)).append(',').append(0).append(',').append(0).append(',').append(0).append(',').append(0).append('|');

			cache_as = paquete.toString();
		}
		return cache_as;
	}

	public int get_Iniciativa() 
	{
		float fact = 4;
		int vida_maxima = puntos_vida_maxima - raza.get_Vida_Base();
		int vida = puntos_vida - raza.get_Vida_Base();
		if (raza.get_Id() == 11)//sacrogito
			fact = 8;
		int iniciativa = 0;

		if (vida_maxima > 0) 
		{
			iniciativa += vida_maxima / fact;
			fact = (float) vida / vida_maxima;
			iniciativa *= fact;
		}

		if (iniciativa < 0)
			iniciativa = 0;
		return 0;
	}

	public int get_Grado_Alas()
	{
		if(get_Alineamiento_Id() != 0)
		{
			if(alineamiento.get_Honor() >= 18000)
				return 10;
			for(byte n = 1; n <= 10; n++)
			{
				if(alineamiento.get_Honor() < Experiencia.get_Experiencia_Alineamientos(n))
					return n - 1;
			}
		}
		return 0;
	}

	public void get_Teleport(final short mapa_destino_id, final short celda_destino_id)
	{
		Mapas mapa_destino = Mapas.get_Mapas_Cargados(mapa_destino_id);
		if(mapa_destino != null)
		{
			Celdas celda = mapa_destino.get_Celda(celda_destino_id);
			if(celda != null)
			{
				if(mapa_destino.get_Sub_area().get_Area().get_Necesita_Abono() && !(cuenta.get_Fecha_abono() > 0))
				{
					cuenta.get_Juego_socket().enviar_Paquete("BP+10");
					cuenta.get_Juego_socket().enviar_Paquete("Im131");
					return;
				}
				mapa.get_Personajes().forEach(personaje -> personaje.get_Cuenta().get_Juego_socket().enviar_Paquete("GM|-" + id));
				mapa = mapa_destino;
				set_Celda(mapa.get_Celda(celda_destino_id));

				cuenta.get_Juego_socket().enviar_Paquete("GA;2;" + id + ';');
				cuenta.get_Juego_socket().enviar_Paquete("GDM|" + mapa.get_Id() + '|' + mapa.get_Fecha() + '|' + mapa.get_Key());
				mapa.get_Personajes().forEach(personaje -> personaje.get_Cuenta().get_Juego_socket().enviar_Paquete("GM|+" + get_Paquete_Gm()));
			}
		}
	}

	public String get_Experiencia_Personaje(final String separador) 
	{
		return Experiencia.get_Experiencia_Personajes(nivel) + separador + experiencia + separador + Experiencia.get_Experiencia_Personajes(nivel + 1);
	}

	public final void get_Agregar_Canal(final TipoCanales canal)
	{
		if (canales.contains(canal.get_Identificador()))
		{
			cuenta.get_Juego_socket().enviar_Paquete("cC-" + canal.get_Identificador());
		}
		else
		{
			canales += canal.get_Identificador();
			cuenta.get_Juego_socket().enviar_Paquete("cC+" + canal.get_Identificador());
		}
	}

	public void get_Eliminar_Canal(final TipoCanales canal) 
	{
		canales = canales.replace(canal.get_Identificador(), "");
		cuenta.get_Juego_socket().enviar_Paquete("cC-" + canal.get_Identificador());
	}

	public static ConcurrentHashMap<Integer, Personajes> get_Personajes_Cargados()
	{
		return personajes_cargados;
	}

	public static Personajes get_Personaje_Cargado(int personaje_id) 
	{
		return personajes_cargados.get(personaje_id);
	}

	public static void eliminar_Personaje_Cargado(final int personaje_id)
	{
		if (personajes_cargados.containsKey(personaje_id))
		{
			personajes_cargados.remove(personaje_id);
		}
	}
}
