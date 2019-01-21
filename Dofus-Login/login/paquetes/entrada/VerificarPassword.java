package login.paquetes.entrada;

import login.LoginSocket;
import login.enums.EstadosLogin;
import login.paquetes.salida.ErroresLogin;
import main.Formulas;
import main.Main;
import objetos.Cuentas;
import objetos.Servidores;

public class VerificarPassword
{
	public void analizar(LoginSocket socket, String paquete)
	{
		if(paquete.length() > 3 || paquete.substring(0, 2).equalsIgnoreCase("#1"))
		{
			/** "puntero" que extrae la dirección de memoria del hashmap **/
			Cuentas cuenta = Cuentas.get_Cuentas_Cargadas().get(Main.get_Database().get_Cuentas().get_Obtener_Id_Cuenta(socket.get_Cuenta_paquete()));

			if(cuenta == null)//Si el puntero es nulo no esta conectado
			{
				cuenta = Main.get_Database().get_Cuentas().cargar_Por_Nombre_Usuario(socket.get_Cuenta_paquete());
				
				if(cuenta == null)
				{
					socket.enviar_Paquete(ErroresLogin.CUENTA_NO_VALIDA.toString());
					socket.cerrar_Conexion();
					return;
				}
			}
			socket.set_Cuenta(cuenta);
			
			if(paquete.equals(Formulas.get_Desencriptar_Password(socket.get_Hash_key(), Main.get_Database().get_Cuentas().get_Obtener_Cuenta_Campo_String("password", socket.get_Cuenta().get_Id()))))
			{
				Servidores.get_Servidores().values().forEach(x-> 
				{
					if(x.get_Comunicador_game() != null)
						x.get_Comunicador_game().get_Desconectar_Cuenta(socket.get_Cuenta().get_Id());
				});
				
				if(cuenta.get_Login_respuesta() != null && cuenta.get_Login_respuesta() != socket)
				{
					cuenta.get_Login_respuesta().enviar_Paquete("ATE");
					socket.enviar_Paquete(ErroresLogin.CUENTA_CONECTADA_SERVIDOR_JUEGO.toString());
					socket.cerrar_Conexion();
					cuenta.get_Login_respuesta().cerrar_Conexion();
					return;
				}
				socket.get_Cuenta().set_Login_respuesta(socket);
				socket.set_Estado_login(EstadosLogin.FILA_ESPERA);
			}
			else
			{
				socket.enviar_Paquete(ErroresLogin.CUENTA_PASSWORD_INCORRECTA.toString());
				socket.cerrar_Conexion();
			}
		}
		else
		{
			socket.enviar_Paquete(ErroresLogin.CUENTA_CONECTADA.toString());
			socket.cerrar_Conexion();
			return;
		}
	}
}
