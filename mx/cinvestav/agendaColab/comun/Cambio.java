/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.cinvestav.agendaColab.comun;

import mx.cinvestav.agendaColab.comun.beans.BeanContacto;
import mx.cinvestav.agendaColab.comun.beans.BeanUsuario;

/**
 *
 * @author rockderick
 */
public class Cambio {

    private BeanContacto contacto;
    private int tipoCambio;
    public final static int BAJA=0;
    public final static int ALTA=1;
    public final static int MODIFICACION=2;


    public Cambio(BeanContacto contacto,int tipoCambio){
        this.contacto=contacto;
        this.tipoCambio=tipoCambio;
    }

    /**
     * @return the contacto
     */
    public BeanContacto getContacto() {
        return contacto;
    }

    /**
     * @return the tipoCambio
     */
    public int getTipoCambio() {
        return tipoCambio;
    }
    public String toString(){
        return contacto+" tipo de modificacion: "+tipoCambio;
    }


}
