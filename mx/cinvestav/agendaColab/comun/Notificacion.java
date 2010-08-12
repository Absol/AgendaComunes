/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.cinvestav.agendaColab.comun;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import mx.cinvestav.agendaColab.comun.beans.BeanUsuario;

/**
 *
 * @author rockderick
 */
public class Notificacion extends Evento{
    public static final int miTipo = 5;
    private BeanUsuario usuario;

    public Notificacion(){

    }

    public Notificacion(BeanUsuario usuario){
        this.usuario=usuario;
    }

    public void read(DataInputStream dinput) {
         try {

            String login=dinput.readUTF();
            usuario=new BeanUsuario(0, login, null);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void write(DataOutputStream doutput) {
        try {
            doutput.writeUTF(usuario.getLogin());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public int getMiTipo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @return the usuario
     */
    public BeanUsuario getUsuario() {
        return usuario;
    }

}
