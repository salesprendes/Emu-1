package database.objetos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.zaxxer.hikari.HikariDataSource;

import database.DatabaseManager;
import objetos.Cuentas;
import objetos.Servidores;

public class Cuentas_DB extends DatabaseManager
{
	public Cuentas_DB(HikariDataSource database_conexion) 
	{
		super(database_conexion);
	}
	
	public Cuentas cargar_Por_Nombre_Usuario(final String nombre_usuario)
	{
		Cuentas cuenta = null;
		try
		{
			final Ejecucion_Query query = ejecutar_Query_Select("SELECT * FROM cuentas WHERE usuario = '" + nombre_usuario + "';");
			
			if(query.get_Rs().next())
				cuenta = new Cuentas(query.get_Rs().getInt(1), query.get_Rs().getString(2), query.get_Rs().getString(3), query.get_Rs().getString(4), query.get_Rs().getByte(7), query.get_Rs().getByte(9), query.get_Rs().getBoolean(10));
			cerrar(query);
		}
		catch (final Exception e){}
		return cuenta;
	}
	
	public int get_Id_Cuenta(final String nombre_usuario)
	{
		int id = 0;
		try
		{
			final Ejecucion_Query query = ejecutar_Query_Select("SELECT id FROM cuentas WHERE usuario = '" + nombre_usuario + "';");
			
			if(query.get_Rs().next())
				id = query.get_Rs().getInt(1);
			cerrar(query);
			return id;
		}
		catch (final SQLException e){}
		return id;
	}
	
	public String get_Cuenta_Campo_String(final String campo, final int cuenta_id)
	{
		try
		{
			final Ejecucion_Query query = ejecutar_Query_Select("SELECT " + campo + " FROM cuentas WHERE id = " + cuenta_id + ";");
			
			String valor = null;
			if(query.get_Rs().next())
			{
				valor = query.get_Rs().getString(1);
			}
			cerrar(query);
			return valor;
		}
		catch (final SQLException e){}
		return null;
	}
	
	public String get_Contar_Personajes_Servidor(final Cuentas cuenta)
	{
		StringBuilder paquete = new StringBuilder();
		try
		{
			final Ejecucion_Query query = ejecutar_Query_Select("SELECT p.servidor_id, COUNT(p.id) FROM personajes p JOIN cuentas c ON p.cuenta_id = c.id WHERE c.usuario = '" + cuenta.get_Usuario() + "' GROUP BY p.servidor_id;");
			
			while(query.get_Rs().next())
			{
				paquete.append('|').append(query.get_Rs().getInt(1)).append(',').append(query.get_Rs().getInt(2));
			}
			cerrar(query);
		}
		catch (final SQLException e){}
		return paquete.toString();
	}
	
	public boolean get_Existe_Campo_Cuenta(final String campo, final String campo_condicion, final String nombre_condicion)
	{
		try
		{
			final Ejecucion_Query query = ejecutar_Query_Select("SELECT " + campo + " FROM cuentas WHERE " + campo_condicion + " = '" + nombre_condicion + "';");
			
			boolean existe = query.get_Rs().next();
			cerrar(query);
			return existe;
		}
		catch (final SQLException e){}
		return false;
	}
	
	public boolean get_Comprobar_Campo_Cuenta_Booleano(final String campo, final String campo_condicion, final String nombre_condicion)
	{
		try
		{
			final Ejecucion_Query query = ejecutar_Query_Select("SELECT " + campo + " FROM cuentas WHERE " + campo_condicion + " = '" + nombre_condicion + "';");
			
			query.get_Rs().next();
			boolean comprobacion = query.get_Rs().getBoolean(1);
			cerrar(query);
			return comprobacion;
		}
		catch (final SQLException e){}
		return false;
	}
	
	public String get_Paquete_Buscar_Servidores(final String nombre)
	{
		StringBuilder paquete = new StringBuilder();
		try
		{
			final Ejecucion_Query query = ejecutar_Query_Select("SELECT servidor_id, count(p.id) from cuentas c JOIN personajes p ON c.id = p.cuenta_id WHERE c.apodo = '" + nombre + "' or p.nombre = '" + nombre + "' GROUP BY p.servidor_id;");

			while(query.get_Rs().next())
			{
				paquete.append(query.get_Rs().getString(1)).append(",").append(query.get_Rs().getString(2)).append(";");
			}
			cerrar(query);
		}
		catch (final SQLException e){}
		return paquete.toString().isEmpty() ? "null" : paquete.toString();
	}
	
	public void actualizar_Apodo(final Cuentas cuenta)
	{
		ejecutar_Update_O_Insert("UPDATE cuentas SET apodo = '" + cuenta.get_Apodo() + "' WHERE id = " + cuenta.get_Id() + ";");
	}
	
	public void get_Cargar_Todas_Cuentas()
	{
		try
		{
			final Ejecucion_Query query = ejecutar_Query_Select("SELECT * FROM cuentas;");
			
			while(query.get_Rs().next())
			{
				//id(1), usuario(2), contraseña(3), apodo(4), uid(5), ip(6), rango_cuenta(7), tiempo_abono(8), comunidad(9), baneado(10)
				new Cuentas(query.get_Rs().getInt(1), query.get_Rs().getString(2), query.get_Rs().getString(3), query.get_Rs().getString(4), query.get_Rs().getByte(7), query.get_Rs().getByte(9), query.get_Rs().getBoolean(10));
			}
			cerrar(query);
		}
		catch (final Exception e){}
	}
	
	public Map<Servidores, ArrayList<Integer>> get_Cargar_Todos_Personajes_Cuenta_Id(final int cuenta_id, final int servidor_id) 
	{
		Map<Servidores, ArrayList<Integer>> mapeo = new HashMap<Servidores, ArrayList<Integer>>();
		try 
		{
			final Ejecucion_Query query = ejecutar_Query_Select("SELECT id, servidor_id FROM personajes WHERE cuenta_id = " + cuenta_id + " AND NOT servidor_id = " + servidor_id + ";");
			
			while(query.get_Rs().next()) 
			{
				Servidores server = Servidores.get_Servidores().get(query.get_Rs().getInt(2));
				int id = query.get_Rs().getInt(1);
				if(mapeo.get(server) == null) 
				{
					ArrayList<Integer> array = new ArrayList<Integer>();
					array.add(id);	
					mapeo.put(server, array);
				} 
				else
					mapeo.get(server).add(id);
			}
			cerrar(query);
		} 
		catch(SQLException e){}
		return mapeo;
	}
}
