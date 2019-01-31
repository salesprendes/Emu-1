package database.objetos;

import java.util.Collections;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Pattern;

import com.zaxxer.hikari.HikariDataSource;

import database.DatabaseManager;
import main.consola.Consola;
import objetos.entidades.caracteristicas.Caracteristicas;
import objetos.entidades.caracteristicas.CaracteristicasTransformer;
import objetos.entidades.personajes.Razas;

public class Razas_DB extends DatabaseManager
{
	public Razas_DB(HikariDataSource database_conexion) 
	{
		super(database_conexion);
	}
	
	public void get_Cargar_Todas_Razas()
	{
		try
		{
			final Ejecucion_Query query = ejecutar_Query_Select("SELECT * FROM razas;");

			while(query.get_Rs().next())
			{
				//id(1), Nombre(2), stats(3), vida(4), mapa_inicial(5), celda_inicial(6)
				new Razas(query.get_Rs().getByte(1), deserializar(query.get_Rs().getString(3)), query.get_Rs().getShort(4), query.get_Rs().getShort(5), query.get_Rs().getShort(6));
			}
			cerrar(query);
		}
		catch (final Exception e)
		{
			Consola.println("ERROR SQL: " + e.toString());
		}
	}
	
	public SortedMap<Integer, Caracteristicas> deserializar(String cadena_stats) 
	{
        SortedMap<Integer, Caracteristicas> stats = new TreeMap<Integer, Caracteristicas>(Collections.reverseOrder());
        for (String stats_nivel : cadena_stats.split(Pattern.quote("|")))
        {
            String[] separador = stats_nivel.split("@", 2);
            stats.put(Integer.parseUnsignedInt(separador[0]), CaracteristicasTransformer.deserializar(separador[1]));
        }
        return stats;
    }
}
