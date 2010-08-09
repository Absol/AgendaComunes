package mx.cinvestav.agendaColab.comun;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;
import mx.cinvestav.agendaColab.comun.beans.BeanCita;
import mx.cinvestav.agendaColab.comun.beans.BeanUsuario;

/**
 * @author rockderick
 */
public class Confirmacion extends Evento {
    public static final int miTipo = 6;
    private BeanCita cita;
    private Vector listaUsuarios;

    public Confirmacion(){
        listaUsuarios=new Vector();
    }

    public Confirmacion(Vector listaUsuarios, BeanCita cita){
        this.listaUsuarios=listaUsuarios;
        this.cita = cita;
    }

    public void read(DataInputStream dinput) {
        try {
            int tamListaUsuarios=dinput.readInt();
            System.out.println("tam: "+tamListaUsuarios);
            for(int i=0;i<tamListaUsuarios;i++)
            {
               BeanUsuario usuario=new BeanUsuario();
               usuario.read(dinput);
               System.out.println("sds: "+usuario);
               add(usuario);
            }
            cita = new BeanCita();
            cita.read(dinput);
        } catch (IOException ex) {
            System.out.println(ex);
        }

    }

    public void write(DataOutputStream doutput) {
        try {
            //Se envia el tamaño de el vector de usuarios
            doutput.writeInt(listaUsuarios.size());
            //Escribimos el usuario origen;
            Enumeration lista = listaUsuarios.elements();
            while (lista.hasMoreElements()) {
                BeanUsuario usuario = (BeanUsuario) lista.nextElement();
                usuario.write(doutput);
            }
            cita.write(doutput);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public int getMiTipo() {
        return miTipo;
    }

    /**
     * @return the listaUsuarios
     */
    public Vector getListaUsuarios() {
        return listaUsuarios;
    }

    /**
     * @return the tamListaUsuarios
     */
    public int getTamListaUsuarios() {
        return listaUsuarios.size();
    }

    private void add(BeanUsuario usuario) {
        listaUsuarios.addElement(usuario);
    }

}
