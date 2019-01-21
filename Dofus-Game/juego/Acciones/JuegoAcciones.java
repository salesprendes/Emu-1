package juego.Acciones;

public interface JuegoAcciones
{
	boolean get_Esta_Iniciado();
    void get_Cancelar();
    void get_Fallo(String args);
    void get_Correcto(String args);
    int get_Id();
    int get_Accion_id();
}
