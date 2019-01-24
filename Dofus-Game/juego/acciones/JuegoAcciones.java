package juego.acciones;

public interface JuegoAcciones
{
	boolean get_Esta_Iniciado();
    void get_Cancelar(String args);
    void get_Correcto(String args);
    int get_Id();
    int get_Accion_id();
}
