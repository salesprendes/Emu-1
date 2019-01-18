package objetos;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

import login.comunicador.ComunicadorSocket;
import login.enums.ErroresServidor;

final public class Servidores 
{
	final private int id, puerto;
	final private Comunidades comunidad;
	private Estados_Servidor estado;
	private ComunicadorSocket comunicador_game = null;
	private final boolean servidor_vip;
	private final String ip;
	private Poblacion poblacion;
	private static final ConcurrentHashMap<Integer, Servidores> servidores_cargados = new ConcurrentHashMap<Integer, Servidores>();
	
	public Servidores(int _id, byte _comunidad, byte _poblacion, final boolean _vip_necesario, final String _ip, final int _puerto)
	{
		id = _id;
		comunidad = Comunidades.get_Comunidades().get(_comunidad);
		estado = Estados_Servidor.APAGADO;
		servidor_vip = _vip_necesario;
		poblacion = Poblacion.values()[_poblacion];
		ip = _ip;
		puerto = _puerto;
		
		servidores_cargados.put(id, this);
	}
	
	public int get_Id() 
	{
		return id;
	}
	
	public Comunidades get_Comunidad()
	{
		return comunidad;
	}
	
	public static Servidores get(int id)
	{
        return servidores_cargados.containsKey(id) ? servidores_cargados.get(id) : null;
    }

	public ComunicadorSocket get_Comunicador_game()
	{
		return comunicador_game;
	}

	public Estados_Servidor get_Estado() 
	{
		return estado;
	}
	
	public String get_Ip() 
	{
		return ip;
	}

	public int get_Puerto()
	{
		return puerto;
	}

	public void set_Estado(Estados_Servidor _estado) 
	{
		estado = _estado;
	}

	public void set_Comunicador_game(ComunicadorSocket _comunicador_game)
	{
		comunicador_game = _comunicador_game;
	}

	public boolean es_Servidor_Vip()
	{
		return servidor_vip;
	}
	
	public Poblacion get_Poblacion()
	{
        return poblacion;
    }
	
	public void set_Poblacion(final int plazas_libres) 
	{
		poblacion = Arrays.stream(Poblacion.values()).filter(poblacion -> poblacion.get_Plazas_Libres() >= plazas_libres).findFirst().orElse(Poblacion.COMPLETO);
    }
	
	public static String get_Obtener_Servidores(Cuentas cuenta)
	{
        final StringBuilder paquete_servidores = new StringBuilder(servidores_cargados.size() * 10).append("AH");
        servidores_cargados.values().forEach(servidor ->
        {
        	paquete_servidores.append(paquete_servidores.length() > 2 ? '|' : "");
        	paquete_servidores.append(servidor.get_Id()).append(';');
        	paquete_servidores.append(servidor.get_Estado().ordinal()).append(';');
        	paquete_servidores.append(servidor.get_Poblacion().ordinal()).append(';');
        	paquete_servidores.append(servidor.es_Servidor_Vip() ? (cuenta.es_Cuenta_Abonada() ? 1 : 0) : 1);
        });
        return paquete_servidores.toString();
    }
	
	public static String get_Obtener_Servidores_Disponibles() 
	{
        final StringBuilder paquete = new StringBuilder(ErroresServidor.SERVIDORES_LIBRES.toString());
        servidores_cargados.values().stream().filter(filtro -> !filtro.es_Servidor_Vip() && filtro.get_Poblacion() != Poblacion.COMPLETO).forEach(servidor -> 
        {
        	paquete.append(servidor.get_Id()).append(paquete.length() > 0 ? '|' : "");
        });
        return paquete.toString();
    }
	
	public static ConcurrentHashMap<Integer, Servidores> get_Servidores() 
	{
		return servidores_cargados;
	}
	
	public enum Estados_Servidor
	{
        APAGADO,
        CONECTADO,
        GUARDANDO;
    }
	
	public enum Poblacion 
	{
		RECOMENDADO(1000),
        PROMEDIO(500),
        ALTA(300),
        BAJA(200),
        COMPLETO(20);

        private final int plazas_libres;

        private Poblacion(final int _plazas_libres)
        {
        	plazas_libres = _plazas_libres;
        }

		public int get_Plazas_Libres()
		{
			return plazas_libres;
		}
    }
}
