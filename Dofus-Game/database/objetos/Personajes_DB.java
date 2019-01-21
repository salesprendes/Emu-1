package database.objetos;

import java.util.TreeMap;

import com.zaxxer.hikari.HikariDataSource;

import database.DatabaseManager;
import main.Configuracion;
import objetos.cuentas.Cuentas;
import objetos.entidades.personajes.Alineamientos;
import objetos.entidades.personajes.Items;
import objetos.entidades.personajes.Personajes;

public class Personajes_DB extends DatabaseManager
{
	public Personajes_DB(HikariDataSource database_conexion) 
	{
		super(database_conexion);
	}
	
	public void get_Cargar_Todos_Personajes()
	{
		try
		{
			final Ejecucion_Query query = ejecutar_Query_Select("SELECT * FROM personajes WHERE servidor_id = " + Configuracion.SERVIDOR_ID +";");

			TreeMap<Integer, Integer> stats_principales;
			while(query.get_Rs().next())
			{
				stats_principales = new TreeMap<Integer, Integer>();
				stats_principales.put(125, query.get_Rs().getInt(16));
				stats_principales.put(118, query.get_Rs().getInt(17));
				stats_principales.put(124, query.get_Rs().getInt(18));
				stats_principales.put(126, query.get_Rs().getInt(19));
				stats_principales.put(123, query.get_Rs().getInt(20));
				stats_principales.put(119, query.get_Rs().getInt(21));
				
				//id(1), nombre(2), color1(3), color2(4), color3(5), nivel(6), gfx(7), tamano(8), mapa_id(9), celda_id(10), sexo(11), experiencia(12), kamas(13), porcentaje_vida(14), razaID(15), vitalidad(16), sabiduria(17), fuerza(18), inteligencia(19), suerte(20), agilidad(21), emotes(22), canales(23), cuentaID(24), derechos(25), derechos(26), servidorID(27)
				new Personajes(query.get_Rs().getInt(1), query.get_Rs().getString(2), query.get_Rs().getInt(3), query.get_Rs().getInt(4), query.get_Rs().getInt(5), query.get_Rs().getShort(6), query.get_Rs().getShort(7), query.get_Rs().getShort(8), query.get_Rs().getShort(9), query.get_Rs().getShort(10), query.get_Rs().getByte(11), query.get_Rs().getLong(12), query.get_Rs().getLong(13), query.get_Rs().getByte(14), query.get_Rs().getByte(15), stats_principales, query.get_Rs().getInt(22), query.get_Rs().getString(23), query.get_Rs().getInt(24), query.get_Rs().getShort(25), query.get_Rs().getShort(26), query.get_Rs().getShort(27));
			}
			cerrar(query);
		}
		catch (final Exception e){}
	}
	
	public void get_Cargar_Alineaciones_Personajes()
	{
		try
		{
			final Ejecucion_Query query = ejecutar_Query_Select("SELECT * FROM personajes_alineamientos;");
			
			while(query.get_Rs().next())
			{
				//personaje_id(1), alineamiento(2), orden(3), orden_nivel(4), honor(5), deshonor(6), activado(7)
				Personajes.get_Personaje_Cargado(query.get_Rs().getInt(1)).set_Alineamiento(new Alineamientos(query.get_Rs().getByte(2), query.get_Rs().getInt(3), query.get_Rs().getInt(4), query.get_Rs().getInt(5), query.get_Rs().getInt(6), query.get_Rs().getBoolean(7)));
			}
			
			cerrar(query);
		}
		catch (final Exception e){}
	}
	
	public void get_Cargar_Items_Personajes()
	{
		try
		{
			final Ejecucion_Query query = ejecutar_Query_Select("SELECT * FROM personajes_items;");
			
			while(query.get_Rs().next())
			{
				//personaje_id(1), id_objeto(2), id_modelo_objeto(3), cantidad(4), posicion_inventario(5), stats(6)
				Personajes.get_Personaje_Cargado(query.get_Rs().getInt(1)).get_objetos().put(query.get_Rs().getInt(2), new Items(query.get_Rs().getInt(2), query.get_Rs().getInt(3), query.get_Rs().getInt(4), query.get_Rs().getByte(5), query.get_Rs().getString(6)));
			}
			
			cerrar(query);
		}
		catch (final Exception e){}
	}
	
	public boolean get_Comprobar_Existe_Nombre_Personaje(final String nombre_personaje) 
	{
		boolean existe_personaje = false;
		try 
		{
			final Ejecucion_Query query = ejecutar_Query_Select("select * FROM personajes WHERE nombre = '" + nombre_personaje + "'");
			existe_personaje = query.get_Rs().next();
			cerrar(query);
		} 
		catch(Exception e) {}
		return existe_personaje;
	}
	
	public void get_Eliminar_Personaje_Id(final int personaje_id,  final Cuentas cuenta)
	{
		try
		{
			ejecutar_Update_Insert("DELETE FROM personajes where id = " + personaje_id +";");
			Personajes.eliminar_Personaje_Cargado(personaje_id);
			cuenta.eliminar_Personaje(personaje_id);
		}
		catch (final Exception e){}
	}
}
