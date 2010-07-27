/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.cinvestav.agendaColab.server.utils;

import java.io.DataOutputStream;
import java.io.IOException;
import javax.servlet.ServletOutputStream;
import mx.cinvestav.agendaColab.comun.ActualizacionUsuariosSincronizados;
import mx.cinvestav.agendaColab.comun.beans.BeanUsuario;
import org.apache.log4j.Logger;

/**
 *
 * @author absol
 */
public class ColaEventos {
    private static Logger log = Logger.getLogger(ProcesEvento.class);

    public static void obtenEventos(int id, ServletOutputStream outputStream) {
        BeanUsuario bean = new BeanUsuario(3, "Genaro", "zapato");
        ActualizacionUsuariosSincronizados act = new ActualizacionUsuariosSincronizados(bean, ActualizacionUsuariosSincronizados.NUEVA_SINCRO);
        DataOutputStream dataOutPut = new DataOutputStream(outputStream);

        try {
            //escribo la longitud
            dataOutPut.writeInt(1);
            //Escribo el tipo del primer Evento
            dataOutPut.writeInt(ActualizacionUsuariosSincronizados.miTipo);
            //Escribo el Evento
            act.write(dataOutPut);
            dataOutPut.close();
        } catch (IOException ex) {
            log.error(ex);
        }
    }
}
