package juego.paquetes.cuenta;

import juego.JuegoSocket;
import juego.enums.EstadosJuego;
import juego.paquetes.GestorPaquetes;
import juego.paquetes.Paquete;
import main.Configuracion;
import main.Main;
import objetos.cuentas.Cuentas;
import objetos.cuentas.Migracion;

@Paquete("AM")
public class GestorMigracion implements GestorPaquetes
{
	public void analizar(JuegoSocket socket, String paquete)
	{
		Cuentas cuenta = socket.get_Cuenta();
		Migracion migracion = Migracion.get_Migracion(cuenta.get_Id());
		
		if(cuenta != null && migracion != null && socket.get_Estado_Juego() == EstadosJuego.MIGRACION)
		{
			switch(paquete.charAt(2)) 
			{
				case '-':
					try 
					{
						int id_personaje = Integer.parseInt(paquete.substring(3));
						int servidor = migracion.get_Buscar_Servidor_personaje(id_personaje);
						
						if(servidor > 0 && migracion.get_Personajes().get(servidor).contains((Integer)id_personaje))
						{
							Main.get_Database().get_Personajes().get_Eliminar_Personaje_Id(id_personaje, cuenta);
							migracion.get_Personajes().get(servidor).remove((Integer)id_personaje);

							if(migracion.get_Personajes().get(servidor).size() > 0)
								Main.socket_vinculador.enviar_Paquete("C|M|P|" + cuenta.get_Id());
							else
							{
								Main.get_Database().get_Cuentas().get_Actualizar_Campo("migracion", "0", cuenta.get_Id());
								Configuracion.get_Paquetes_Emulador().get("AL").analizar(socket, paquete);
							}
								
						}
						else//Generalmente WPE
						{
							socket.enviar_Paquete("ADE");
						}
					}
					catch(Exception e) {}
				break;
				
				default:
					String[] separador = paquete.substring(2).split("\\;");
					String nombre = separador[1];
					if(!Main.get_Database().get_Personajes().get_Comprobar_Existe_Nombre_Personaje(nombre, Configuracion.SERVIDOR_ID))
					{
						
					}
					else
					{
						socket.enviar_Paquete("AAEa");
					}
				break;
			}
		}
		else
		{
			socket.enviar_Paquete("AlEn");
			socket.cerrar_Conexion();
		}
	}
}
