package mx.cinvestav.agendaColab.comun;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import mx.cinvestav.agendaColab.comun.beans.BeanCita;

/**
 * Clase para contener los datos de una cita no privada (solo publicacion al srv)
 * @author absol
 */
public class CitaPublica extends Evento {
public static final int miTipo = 12;
private BeanCita cita;

    public CitaPublica(BeanCita cita) {
        this.cita = cita;
    }

    public CitaPublica() {
    }

    public BeanCita getCita() {
        return cita;
    }

    public void read(DataInputStream dinput) {
        cita = new BeanCita();
        cita.read(dinput);
    }

    public void write(DataOutputStream doutput) {
        cita.write(doutput);
    }

    public int getMiTipo() {
        return miTipo;
    }
}
