package juego.paquetes.cuenta;

import java.util.regex.Pattern;

import juego.JuegoSocket;
import juego.enums.EstadosJuego;
import juego.paquetes.GestorPaquetes;
import juego.paquetes.Paquete;
import main.Configuracion;
import main.Main;
import main.consola.Consola;
import objetos.cuentas.Cuentas;
import objetos.entidades.personajes.Personajes;
import objetos.entidades.personajes.Razas;

@Paquete("AA")
/**
 * https://github.com/Emudofus/Dofus/blob/1.29/dofus/aks/Account.as#L90
 */
public class AgregarPersonaje implements GestorPaquetes
{
	public void analizar(JuegoSocket socket, String paquete)
	{
		Cuentas cuenta = socket.get_Cuenta();
		
		if(cuenta != null && socket.get_Personaje() == null && socket.get_Estado_Juego() == EstadosJuego.SELECCION_PERSONAJE)
		{
			try 
			{
				final String[] separador = paquete.substring(2).split(Pattern.quote("|"), 6);
				
				if (separador.length != 6)
				{
					socket.enviar_Paquete("AAEZ");
					Consola.println("Paquete invalido en creacion de personajes requiere 6 partes, paquete: " + paquete);
					return;
				}
				
				if (Main.get_Database().get_Personajes().get_Comprobar_Existe_Nombre_Personaje(separador[0], Configuracion.SERVIDOR_ID)) 
				{
					socket.enviar_Paquete("AAEa");
					return;
				}
				
				if (cuenta.get_Personajes().size() >= cuenta.get_Max_Creacion_Personajes_Total()) 
				{
					socket.enviar_Paquete("AAEf");
					return;
				}
				
				String nombre = separador[0];
				final byte raza_id = Byte.parseByte(separador[1]);
				final byte sexo = Byte.parseByte(separador[2]);
				final int color_1 = Integer.parseInt(separador[3]);
				final int color_2 = Integer.parseInt(separador[4]);
				final int color_3 = Integer.parseInt(separador[5]);
				
				if(nombre.matches("[-]{1}$A-Za-z[-]{1}]+") || nombre.isEmpty() || nombre.length() < 1 || nombre.length() > 20)
				{
					socket.enviar_Paquete("AAEn");
					return;
				}
				
				if (Razas.get_Razas_Cargadas(raza_id) == null) 
				{
					socket.enviar_Paquete("AAEz");
					return;
				}
				
				final Personajes personaje = cuenta.crearPersonaje(nombre, raza_id, sexo, color_1, color_2, color_3);

				if (personaje != null) 
				{
					socket.enviar_Paquete("AAK");
					
					final StringBuilder paquete_alk = new StringBuilder();
					paquete_alk.append("ALK" + cuenta.get_Fecha_abono() + '|').append(cuenta.get_Personajes().size());
					cuenta.get_Personajes().forEach(personajes -> paquete_alk.append(personajes.get_Paquete_Alk()));
					socket.enviar_Paquete(paquete_alk.toString());
					
					socket.enviar_Paquete("TB");
				}
				else
					socket.enviar_Paquete("AAEZ");
			}
			catch (final Exception e) 
			{
				socket.enviar_Paquete("AAEZ");
			}
		}
		else
		{
			socket.enviar_Paquete("AlEn");
			socket.cerrar_Conexion();
		}
	}
}
