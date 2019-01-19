package juego.paquetes.cuenta;

import juego.JuegoSocket;
import juego.enums.EstadosJuego;
import juego.paquetes.GestorPaquetes;
import juego.paquetes.Paquete;
import main.Configuracion;
import main.Main;
import objetos.cuentas.Cuentas;
import objetos.cuentas.Migracion;

@Paquete("AL")
public class ListaPersonajes implements GestorPaquetes
{
	public void analizar(JuegoSocket socket, String paquete) 
	{
		Cuentas cuenta = socket.get_Cuenta();

		if(cuenta != null && socket.get_Estado_Juego() != EstadosJuego.SELECCION_PERSONAJE)
		{
			if(Configuracion.TIPO_COMUNIDAD != get_Comunidad_Idioma(cuenta.get_Idioma_cliente()))
			{
				socket.enviar_Paquete("M019");
				socket.cerrar_Conexion();
			}
			else if(!Configuracion.ACTIVAR_FILA_DE_ESPERA || socket.get_Estado_Juego() == EstadosJuego.MIGRACION)
			{
				if(Main.get_Database().get_Cuentas().get_Puede_Migrar(cuenta.get_Id())) 
				{
					String servidores = Main.get_Database().get_Cuentas().get_Lista_Servidores_Otros_Personajes(cuenta.get_Id());
					if(!servidores.isEmpty())
					{
						new Migracion(cuenta.get_Id(), servidores);
						Main.socket_vinculador.enviar_Paquete("C|M|P|" + cuenta.get_Id());
						socket.set_Estado_Juego(EstadosJuego.MIGRACION);
					}
				}
				else
				{
					final StringBuilder paquete_enviado = new StringBuilder();
					paquete_enviado.append("ALK" + cuenta.get_Fecha_abono() + "|").append(cuenta.get_Personajes().size());
					cuenta.get_Personajes().forEach(personaje -> paquete_enviado.append(personaje.get_Paquete_Alk()));

					socket.enviar_Paquete(paquete_enviado.toString());
					socket.set_Estado_Juego(EstadosJuego.SELECCION_PERSONAJE);
				}
			}
			else
			{
				socket.set_Estado_Juego(EstadosJuego.FILA_ESPERA);
			}
		}
		else//Cuenta nula o intento de usar wpe intentando enviar paquete de lista de personajes estando conectado o en la seleccion
		{
			socket.enviar_Paquete("AlEn");
			socket.cerrar_Conexion();
		}
	}
	
	public static byte get_Comunidad_Idioma(String idioma) 
	{
		switch (idioma.toLowerCase())
		{
			case "fr" :
			case "ch" :
			case "be" :
			case "lu" :
				return 0;
				
			case "uk" :
			case "ie" :
			case "gb" :
				return 1;
				
			case "xx" :
				return 2;
				
			case "de" :
			case "at" :
			case "li" :
				return 3;
				
			case "es" :
			case "ad" :
			case "ar" :
			case "ck" :
			case "mx" :
				return 4;
				
			case "ru" :
				return 5;
				
			case "pt" :
			case "br" :
				return 6;
				
			case "nl" :
				return 7;
				
			case "it" :
				return 9;
				
			case "jp" :
				return 10;
				
			default :
				return 99;
		}
	}
}
