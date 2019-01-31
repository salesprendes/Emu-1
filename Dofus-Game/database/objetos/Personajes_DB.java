package database.objetos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.zaxxer.hikari.HikariDataSource;

import database.DatabaseManager;
import juego.enums.TipoCaracteristica;
import main.Configuracion;
import main.consola.Consola;
import objetos.cuentas.Cuentas;
import objetos.entidades.caracteristicas.DefaultCaracteristicas;
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

			DefaultCaracteristicas stats_principales;
			while(query.get_Rs().next())
			{
				stats_principales = new DefaultCaracteristicas();
				
				stats_principales.set_Caracteristica(TipoCaracteristica.VITALIDAD, query.get_Rs().getInt(16));
				stats_principales.set_Caracteristica(TipoCaracteristica.SABIDURIA, query.get_Rs().getInt(17));
				stats_principales.set_Caracteristica(TipoCaracteristica.FUERZA, query.get_Rs().getInt(18));
				stats_principales.set_Caracteristica(TipoCaracteristica.INTELIGENCIA, query.get_Rs().getInt(19));
				stats_principales.set_Caracteristica(TipoCaracteristica.SUERTE, query.get_Rs().getInt(20));
				stats_principales.set_Caracteristica(TipoCaracteristica.AGILIDAD, query.get_Rs().getInt(21));
				
				//id(1), nombre(2), color1(3), color2(4), color3(5), nivel(6), gfx(7), tamano(8), mapa_id(9), celda_id(10), sexo(11), experiencia(12), kamas(13), porcentaje_vida(14), razaID(15), vitalidad(16), sabiduria(17), fuerza(18), inteligencia(19), suerte(20), agilidad(21), emotes(22), canales(23), cuentaID(24), derechos(25), derechos(26), servidorID(27)
				new Personajes(query.get_Rs().getInt(1), query.get_Rs().getString(2), query.get_Rs().getInt(3), query.get_Rs().getInt(4), query.get_Rs().getInt(5), query.get_Rs().getShort(6), query.get_Rs().getShort(7), query.get_Rs().getShort(8), query.get_Rs().getShort(9), query.get_Rs().getShort(10), query.get_Rs().getByte(11), query.get_Rs().getLong(12), query.get_Rs().getLong(13), query.get_Rs().getByte(14), query.get_Rs().getByte(15), stats_principales, query.get_Rs().getInt(22), query.get_Rs().getString(23), query.get_Rs().getInt(24), query.get_Rs().getInt(25), query.get_Rs().getShort(26), query.get_Rs().getShort(27));
			}
			cerrar(query);
		}
		catch (final Exception e)
		{
			Consola.println("ERROR SQL: " + e.toString());
		}
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
		catch (final Exception e)
		{
			Consola.println("ERROR SQL: " + e.toString());
		}
	}
	
	public void get_Cargar_Items_Personajes()
	{
		try
		{
			final Ejecucion_Query query = ejecutar_Query_Select("SELECT * FROM personajes_items;");
			
			while(query.get_Rs().next())
			{
				//personaje_id(1), id_objeto(2), id_modelo_objeto(3), cantidad(4), posicion_inventario(5), stats(6)
				Personajes.get_Personaje_Cargado(query.get_Rs().getInt(1)).get_objetos().put(query.get_Rs().getInt(2), new Items(query.get_Rs().getInt(2), query.get_Rs().getInt(3), query.get_Rs().getInt(4), query.get_Rs().getByte(5), Items.deserializar(query.get_Rs().getString(6))));
			}
			
			cerrar(query);
		}
		catch (final Exception e)
		{
			Consola.println("ERROR SQL: " + e.toString());
		}
	}
	
	public boolean get_Comprobar_Existe_Nombre_Personaje(final String nombre_personaje, final short servidor_id) 
	{
		boolean existe_personaje = false;
		try 
		{
			final Ejecucion_Query query = ejecutar_Query_Select("SELECT * FROM personajes WHERE nombre = '" + nombre_personaje + "' AND servidor_id = " + servidor_id + ";");
			existe_personaje = query.get_Rs().next();
			cerrar(query);
		} 
		catch (final Exception e)
		{
			Consola.println("ERROR SQL: " + e.toString());
		}
		return existe_personaje;
	}
	
	public void get_Eliminar_Personaje_Id(final int personaje_id,  final Cuentas cuenta)
	{
		try
		{
			ejecutar_Update("DELETE FROM personajes where id = " + personaje_id + ";");
			Personajes.eliminar_Personaje_Cargado(personaje_id);
			cuenta.eliminar_Personaje(personaje_id);
		}
		catch (final Exception e)
		{
			Consola.println("ERROR SQL: " + e.toString());
		}
	}
	
	public void get_Guardar_Personaje(final Personajes personaje)
	{
		synchronized (bloqueo)
		{
			Connection conexion = null;
			PreparedStatement statement = null;
			String query = null;
			try 
			{
				query = "INSERT INTO personajes (nombre, color_1, color_2, color_3, nivel, gfx, tamano, mapa_id, celda_id, sexo, experiencia, kamas, porcentaje_vida, raza_id, vitalidad, sabiduria, fuerza, inteligencia, suerte, agilidad, emotes, canales, cuenta_id, derechos, restricciones, servidor_id) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
				conexion = database_conexion.getConnection();
				statement = conexion.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				
				statement.setString(1, personaje.get_Nombre(false));
				statement.setInt(2, personaje.get_Color_1());
				statement.setInt(3, personaje.get_Color_2());
				statement.setInt(4, personaje.get_Color_3());
				statement.setInt(5, personaje.get_Nivel());
				statement.setShort(6, personaje.get_Gfx());
				statement.setShort(7, personaje.get_Tamano());
				statement.setShort(8, personaje.get_Mapa_Id());
				statement.setShort(9, personaje.get_Celda_Id());
				statement.setByte(10, personaje.get_Sexo());
				statement.setLong(11, personaje.get_Experiencia());
				statement.setLong(12, personaje.get_Kamas());
				statement.setByte(13, personaje.get_Porcentaje_Vida());
				statement.setByte(14, personaje.get_Raza().get_Id());
				
				//Stats
				statement.setInt(15, 0);//vitalidad(16)
				statement.setInt(16, 0);//sabiduria(17)
				statement.setInt(17, 0);// fuerza(18)
				statement.setInt(18, 0);//inteligencia(19)
				statement.setInt(19, 0);//suerte(20)
				statement.setInt(20, 0);//agilidad(21)
				
				statement.setInt(21, personaje.get_Emotes().get());
				statement.setString(22, personaje.get_Canales());
				statement.setInt(23, personaje.get_Cuenta().get_Id());
				statement.setInt(24, personaje.get_Derechos().get_Valor_Derechos());
				statement.setInt(25, personaje.get_Restricciones().get_Restricciones());
				statement.setInt(26, personaje.get_Servidor());
				
				statement.executeUpdate();
				
				try (ResultSet generatedKeys = statement.getGeneratedKeys()) 
				{
					if (generatedKeys.next())
						personaje.set_Id(generatedKeys.getInt(1));
		            else
		                throw new SQLException("La creación del personaje falló, no se encontro ID");
				}
			}
			catch(final SQLException e)
			{
				System.out.println(e);
				try
				{
					if (conexion != null) 
					{
						conexion.rollback();
					}
				} 
				catch (final Exception ignorar){}
			}
			finally 
			{
				cerrar_Statement(statement);
				cerrar_Conexion(conexion);
			}
		}
	}
}
