/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.cinvestav.agendaColab.comun;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import mx.cinvestav.agendaColab.comun.beans.BeanCita;

/**
 *
 * @author rockderick
 */
public class Cancelacion extends Evento{
    public static final int miTipo = 4;
    private BeanCita cita;

    public Cancelacion(){
        cita=null;


    }

    public Cancelacion(BeanCita cita){
        this.cita=cita;
        
    }

     public void read(DataInputStream dinput) {
         try {

            cita = new BeanCita();
            getCita().read(dinput);

        } catch (Exception ex) {
            cita = null;

        }
    }

    public void write(DataOutputStream doutput) {
        try {
            getCita().write(doutput);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public int getMiTipo() {
        return miTipo;
    }

    /**
     * @return the cita
     */
    public BeanCita getCita() {
        return cita;
    }


}
