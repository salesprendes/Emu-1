package objetos;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import login.comunicador.ComunicadorRespuesta;
import login.enums.ErroresServidor;

final public class Servidores 
{
	final private int id, puerto;
	final private Comunidades comunidad;
	private Estados_Servidor estado;
	public static final ConcurrentMap<Integer, Servidores> servidores = new ConcurrentHashMap<Integer, Servidores>();
	private ComunicadorRespuesta comunicador_game = null;
	private final boolean servidor_vip;
	private final String ip;
	private Poblacion poblacion;
	
	public Servidores(int _id, Comunidades _comunidad, byte _poblacion, final boolean _vip_necesario, final String _ip, final int _puerto)
	{
		id = _id;
		comunidad = _comunidad;
		estado = Estados_Servidor.APAGADO;
		servidor_vip = _vip_necesario;
		poblacion = Poblacion.values()[_poblacion];
		ip = _ip;
		puerto = _puerto;
		servidores.put(id, this);
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
        return servidores.containsKey(id) ? servidores.get(id) : null;
    }

	public ComunicadorRespuesta get_Comunicador_game()
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

	public void set_Comunicador_game(ComunicadorRespuesta _comunicador_game)
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
	
	public static String get_Obtener_Servidores()
	{
        final StringBuilder paquete_servidores = new StringBuilder(servidores.size() * 10).append("AH");
        servidores.values().forEach(servidor ->
        {
        	paquete_servidores.append(paquete_servidores.length() > 2 ? '|' : "");
        	paquete_servidores.append(servidor.get_Id()).append(';');
        	paquete_servidores.append(servidor.get_Estado().ordinal()).append(';');
        	paquete_servidores.append(servidor.get_Poblacion().get_Id()).append(';');
        	paquete_servidores.append(servidor.es_Servidor_Vip()? 1 : 0);
        });
        return paquete_servidores.toString();
    }
	
	public static String get_Obtener_Servidores_Disponibles() 
	{
        final StringBuilder paquete = new StringBuilder(ErroresServidor.SERVIDORES_LIBRES.toString());
        servidores.values().stream().filter(filtro -> !filtro.es_Servidor_Vip() && filtro.get_Poblacion() != Poblacion.COMPLETO).forEach(servidor -> 
        {
        	paquete.append(servidor.get_Id()).append('|');
        });
        return paquete.toString();
    }
	
	public enum Estados_Servidor
	{
        APAGADO,
        CONECTADO,
        GUARDANDO;
    }
	
	public enum Poblacion 
	{
		RECOMENDADO((byte) 1, 1000),
        MEDIA((byte) 2, 500),
        ELEVADA((byte) 3, 300),
        COMPLETO((byte) 4, 20);

        private final byte id;
        private final int plazas_libres;

        private Poblacion(byte _id, int _plazas_libres)
        {
        	id = _id;
        	plazas_libres = _plazas_libres;
        }

		public byte get_Id() 
		{
			return id;
		}

		public int get_Plazas_Libres() 
		{
			return plazas_libres;
		}
    }
}
