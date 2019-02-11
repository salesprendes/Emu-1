package database.objetos;

import java.util.TreeMap;

import com.zaxxer.hikari.HikariDataSource;

import database.DatabaseManager;
import main.consola.Consola;
import objetos.entidades.monstruos.MonstruosModelo;
import objetos.entidades.monstruos.MonstruosRaza;
import objetos.entidades.monstruos.MonstruosSuperRaza;

public class Monstruos_DB extends DatabaseManager
{
	public Monstruos_DB(HikariDataSource database_conexion) 
	{
		super(database_conexion);
	}
	
	public void get_Cargar_Todos_Monstruos()
	{
		try
		{
			final Ejecucion_Query query = ejecutar_Query_Select("SELECT * FROM monstruos;");

			TreeMap<Byte, String> grados;
			while(query.get_Rs().next())
			{
				grados = new TreeMap<Byte, String>();
				if(!query.get_Rs().getString(6).isEmpty())
					grados.put((byte) 1, query.get_Rs().getString(6));
				if(!query.get_Rs().getString(7).isEmpty())
					grados.put((byte) 2, query.get_Rs().getString(7));
				if(!query.get_Rs().getString(8).isEmpty())
					grados.put((byte) 3, query.get_Rs().getString(8));
				if(!query.get_Rs().getString(9).isEmpty())
					grados.put((byte) 4, query.get_Rs().getString(9));
				if(!query.get_Rs().getString(10).isEmpty())
					grados.put((byte) 5, query.get_Rs().getString(10));
				if(!query.get_Rs().getString(11).isEmpty())
					grados.put((byte) 6, query.get_Rs().getString(11));
				if(!query.get_Rs().getString(12).isEmpty())
					grados.put((byte) 7, query.get_Rs().getString(12));
				if(!query.get_Rs().getString(13).isEmpty())
					grados.put((byte) 8, query.get_Rs().getString(13));
				if(!query.get_Rs().getString(14).isEmpty())
					grados.put((byte) 9, query.get_Rs().getString(14));
				if(!query.get_Rs().getString(15).isEmpty())
					grados.put((byte) 10, query.get_Rs().getString(15));
				
				//id(1), nombre(2), gfx(3), raza(4), alineacion(5), grado_1(6), grado_2(7), grado_3(8), grado_4(9), grado_5(10), grado_6(11), grado_7(12), grado_8(13), grado_9(14), grado_10(15), colores(16)
				new MonstruosModelo(query.get_Rs().getInt(1), query.get_Rs().getInt(3), query.get_Rs().getByte(4), query.get_Rs().getByte(5), grados, query.get_Rs().getString(16));
			}
			cerrar(query);
		}
		catch (final Exception e)
		{
			Consola.println("ERROR SQL: " + e.toString());
		}
	}
	
	public void get_Cargar_Razas()
	{
		try
		{
			final Ejecucion_Query query = ejecutar_Query_Select("SELECT * FROM monstruos_raza;");

			while(query.get_Rs().next())
			{
				//id(1), nombre(2), Super_raza(3)
				new MonstruosRaza(query.get_Rs().getByte(1), query.get_Rs().getByte(3));
			}
			cerrar(query);
		}
		catch (final Exception e)
		{
			Consola.println("error sql: " + e);
		}
	}
	
	public void get_Cargar_Super_Razas()
	{
		try
		{
			final Ejecucion_Query query = ejecutar_Query_Select("SELECT * FROM monstruos_super_raza;");

			while(query.get_Rs().next())
			{
				//id(1), nombre(2)
				new MonstruosSuperRaza(query.get_Rs().getByte(1));
			}
			cerrar(query);
		}
		catch (final Exception e)
		{
			Consola.println("error sql: " + e);
		}
	}
}
