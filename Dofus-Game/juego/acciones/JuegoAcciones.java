package juego.acciones;

public interface JuegoAcciones
{
	boolean get_Puede_Hacer_Accion();
    void get_Accion_Fallida(String args);
    void get_Accion_Correcta(String args);
    short get_Tipo_Accion();
}
