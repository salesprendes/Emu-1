package database.objetos;

import com.zaxxer.hikari.HikariDataSource;

import database.DatabaseManager;
import main.Configuracion;
import objetos.cuentas.Cuentas;
import objetos.personajes.Personajes;

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

			while(query.get_Rs().next())
			{
				//id(1), nombre(2), color1(3), color2(4), color3(5), nivel(6), gfx(7), tamano(8), mapa_id(9), celda_id(10), razaID(11), cuentaID(12), derechos(13), derechos(14), servidorID(15)
				new Personajes(query.get_Rs().getInt(1), query.get_Rs().getString(2), query.get_Rs().getInt(3), query.get_Rs().getInt(4), query.get_Rs().getInt(5), query.get_Rs().getShort(6), query.get_Rs().getShort(7), query.get_Rs().getShort(8), query.get_Rs().getShort(9), query.get_Rs().getShort(10), query.get_Rs().getByte(11), query.get_Rs().getInt(12), query.get_Rs().getShort(13), query.get_Rs().getShort(14), query.get_Rs().getShort(15));
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
