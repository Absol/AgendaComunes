/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.cinvestav.agendaColab.comun;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import mx.cinvestav.agendaColab.comun.beans.BeanCita;
import mx.cinvestav.agendaColab.comun.beans.BeanUsuario;

/**
 *
 * @author rockderick
 */
public class Respuesta extends Evento{
    public static final int miTipo = 3;
    private boolean respuesta;
    private BeanCita cita;
    private BeanUsuario usuario;

    public Respuesta(){
        cita=null;
        respuesta=false;

    }

    public Respuesta(BeanCita cita, BeanUsuario usu, boolean respuesta){
        this.cita=cita;
        this.respuesta=respuesta;
        this.usuario = usu;
    }

    public void read(DataInputStream dinput) {
         try {

            cita = new BeanCita();
            cita.read(dinput);
            if(dinput.readByte() != 0){
                usuario = new BeanUsuario();
                usuario.read(dinput);
            } else
                usuario = null;
            respuesta = dinput.readBoolean();
        } catch (Exception ex) {
            cita = null;
            respuesta=false;
        }
    }

    public void write(DataOutputStream doutput) {
        try {
            cita.write(doutput);
            if(usuario == null)
                doutput.writeByte(0);
            else{
                doutput.writeByte(1);
                usuario.write(doutput);
            }
            doutput.writeBoolean(respuesta);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public int getMiTipo() {
        return miTipo;
    }

    /**
     * @return the respuesta
     */
    public boolean isRespuesta() {
        return respuesta;
    }

    /**
     * @return the cita
     */
    public BeanCita getCita() {
        return cita;
    }

    public String toString()
    {
        return "(" + cita + ", " + usuario + ":" + respuesta + ")";
    }
}
