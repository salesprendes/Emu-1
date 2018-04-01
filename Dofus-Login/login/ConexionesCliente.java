package login;

import java.util.ArrayList;
import java.util.List;

import login.enums.ErroresLogin;

public class ConexionesCliente 
{
	private String ip_cliente;
	private long tiempo_ultima_conexion;
	private final List<LoginRespuesta> clientes_login = new ArrayList<LoginRespuesta>();

	public ConexionesCliente(String ip, LoginRespuesta cliente)
	{
		ip_cliente = ip;
		tiempo_ultima_conexion = System.currentTimeMillis();
		clientes_login.add(cliente);
	}

	public void refrescar_Tiempo_Ultima_Conexion()
	{
		tiempo_ultima_conexion = System.currentTimeMillis();
	}

	public long get_Tiempo_Ultima_Conexion()
	{
		return System.currentTimeMillis() - tiempo_ultima_conexion;
	}

	public String get_Ip_Cliente()
	{
		return ip_cliente;
	}

	public List<LoginRespuesta> get_Clientes_Login()
	{
		return clientes_login;
	}

	public void agregar_Cliente(LoginRespuesta cliente)
	{
		refrescar_Tiempo_Ultima_Conexion();
		if (!clientes_login.contains(cliente)) 
		{
			clientes_login.add(cliente);
		}
	}

	public void eliminar_Cliente(LoginRespuesta g)
	{
		if (clientes_login.contains(g))
		{
			clientes_login.remove(g);
		}
	}
	
	public void expulsar_Todos_Clientes()
	{
		clientes_login.forEach(cliente ->
		{
			cliente.enviar_Paquete(ErroresLogin.SERVIDOR_EN_MANTENIMIENTO.toString());
			cliente.cerrar_Conexion();
		});
	}
}
