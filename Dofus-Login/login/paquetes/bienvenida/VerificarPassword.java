package login.paquetes.bienvenida;

import login.LoginRespuesta;
import login.enums.ErroresLogin;
import login.enums.EstadosLogin;
import main.Formulas;
import main.Main;
import main.consola.Consola;
import objetos.Cuentas;

public class VerificarPassword
{
	public void analizar(LoginRespuesta jugador, String paquete)
	{
		if(paquete.substring(0, 2).equalsIgnoreCase("#1"))
		{
			if(paquete.equals(Formulas.desencriptar_Password(jugador.get_Hash_key(), Main.get_Database().get_Cuentas().get_Obtener_Cuenta_Campo_String("password", jugador.get_Cuenta_paquete()))))
			{
				jugador.set_Cuenta(Main.get_Database().get_Cuentas().cargar_Cuenta(jugador.get_Cuenta_paquete()));

				/** puntero que extrae la dirección de memoria del hashmap **/
				Cuentas _cuenta = Cuentas.get_Cuentas_Cargadas().get(jugador.get_Cuenta().get_Id());

				if(_cuenta == null)//Si el puntero es nulo no esta conectado
				{

					Cuentas.agregar_Cuenta_Cargada(jugador.get_Cuenta());
					jugador.get_Cuenta().set_Login_respuesta(jugador);
					jugador.set_Estado_login(EstadosLogin.FILA_ESPERA);
				}
				else
				{
					if(_cuenta.get_Login_respuesta().get_Estado_login() != EstadosLogin.LISTA_SERVIDORES)
					{
						jugador.enviar_Paquete(ErroresLogin.CUENTA_CONECTADA.toString());
						jugador.cerrar_Conexion();
					}
					else
					{
						_cuenta.get_Login_respuesta().enviar_Paquete("ATE");
						jugador.enviar_Paquete(ErroresLogin.CUENTA_YA_CONECTADA.toString());
						jugador.cerrar_Conexion();
						_cuenta.get_Login_respuesta().cerrar_Conexion();
					}
				}
			}
			else
			{
				jugador.enviar_Paquete(ErroresLogin.CUENTA_PASSWORD_INCORRECTA.toString());
				jugador.cerrar_Conexion();
				return;
			}
		}
		else
		{
			jugador.enviar_Paquete(ErroresLogin.CUENTA_CONECTADA.toString());
			jugador.cerrar_Conexion();
			Consola.println("paquete incorrecto password");
			return;
		}
	}
}
