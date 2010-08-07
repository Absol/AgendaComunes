package mx.cinvestav.agendaColab.comun;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;
import mx.cinvestav.agendaColab.comun.beans.BeanContacto;

/**
 * @author rockderick
 */
public class Sincronizacion extends Evento {

    public static final int miTipo = 2;
    private Vector listaCambios;
    //private Cambio cambio;

    public Sincronizacion() {
        listaCambios = new Vector();
    }

    public void read(DataInputStream dinput) {
        try {
            //se lee el numero de cambios
            int tamSincro = dinput.readInt();
            for (int i = 0; i < tamSincro; i++) {
                //lee el tipo de cambio
                int tipoCambio = dinput.readInt();
                //lee el contacto
                BeanContacto contacto = new BeanContacto();
                contacto.read(dinput);
                Cambio camb = new Cambio(contacto, tipoCambio);
                add(camb);
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }

    }

    public void write(DataOutputStream doutput) {
        try {
            //Se envia el tamaño de el vector de sincronización
            doutput.writeInt(listaCambios.size());
            Enumeration lista = getListaCambios().elements();
            while (lista.hasMoreElements()) {
                Cambio change = (Cambio) lista.nextElement();
                //escribe tipo de cambio
                doutput.writeInt(change.getTipoCambio());
                //escribe contacto
                change.getContacto().write(doutput);
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public void add(Cambio cambio) {
        getListaCambios().addElement(cambio);
    }

    public int getMiTipo() {
        return miTipo;
    }

    /**
     * @return the listaCambios
     */
    public Vector getListaCambios() {
        return listaCambios;
    }
}
