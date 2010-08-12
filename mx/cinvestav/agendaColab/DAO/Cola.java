package mx.cinvestav.agendaColab.DAO;
import java.util.Vector;
import mx.cinvestav.agendaColab.comun.ActualizacionUsuariosSincronizados;
import mx.cinvestav.agendaColab.comun.Cambio;
import mx.cinvestav.agendaColab.comun.CitaPublica;
import mx.cinvestav.agendaColab.comun.Confirmacion;
import mx.cinvestav.agendaColab.comun.PullRequest;
import mx.cinvestav.agendaColab.comun.beans.BeanCita;
import mx.cinvestav.agendaColab.comun.beans.BeanUsuario;

/**
 * @author eduardogiron
 */
public class Cola {

    //Eventos resultado de la operacion de la agenda
    public static void guadaContacto(Cambio cambio){

    }

    public static void guardaCitaPublica(BeanCita cita){
        if(cita.getNivel() == BeanCita.PRIVADA){
            return;
        }
        CitaPublica citaPub = new CitaPublica(cita);

    }

    public static void guardaCitaConjunta(BeanCita cita, Vector vec){
        Confirmacion conf = new Confirmacion(vec, cita);
        
    }

    public static void guardaCambioSincro(BeanUsuario usu, int tipo){
        ActualizacionUsuariosSincronizados act
                = new ActualizacionUsuariosSincronizados(usu, tipo);

    }

    public static Vector getEncolados(BeanUsuario usu){
        Vector vec = new Vector();
        vec.addElement(new PullRequest(usu));

        //TO-DO los demas eventos posibles

        return vec;
    }
}
