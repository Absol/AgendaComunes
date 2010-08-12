package mx.cinvestav.agendaColab.comun;

/**
 * @author absol
 */
public class ConfirmacionReagendado extends Confirmacion{
public static final int miTipo = 14;

    public int getMiTipo() {
        return this.miTipo;
    }

    public String toString() {
        return "ConfirmacionReagendado{" + "cita=" + cita + "listaUsuarios=" + listaUsuarios + '}';
    }
}
