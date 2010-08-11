/*
 * Este es la clase de Rodrigo correspondiente a DAO
 * En esta clase es necesario guardar los datos del Usuario/Citas
 */

package mx.cinvestav.agendaColab.DAO;
import mx.cinvestav.agendaColab.comun.beans.BeanUsuario;

/**
 *
 * @author eduardogiron
 */
public class DaoUsuario {

    /**
     * Si esta guardado, permite recuperar el usuario actual
     */
    public static BeanUsuario getMyUser(){
        BeanUsuario usuario= new BeanUsuario(5,"edward","hola");
        return usuario;
    }
    /**
     * Permite almacenar un usuario
     */
    public static void guardaMyUsuario(BeanUsuario user){

    }
   
}
