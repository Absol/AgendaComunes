/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.cinvestav.agendaColab.server.utils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletOutputStream;
import mx.cinvestav.agendaColab.comun.ActualizacionUsuariosSincronizados;
import mx.cinvestav.agendaColab.comun.beans.BeanUsuario;
import mx.cinvestav.agendaColab.server.logica.ProcesaModSincro;
import mx.cinvestav.agendaColab.server.logica.dao.ColaDao;
import org.apache.log4j.Logger;

/**
 *
 * @author absol
 */
public class ColaEventos {

    private static Logger log = Logger.getLogger(ColaEventos.class);
    private static ColaDao colaDao = new ColaDao();

    public static void obtenEventos(int idUsuConsultante, ServletOutputStream outputStream) {
        Encolable evento = null;
        DataOutputStream dataOutPut = new DataOutputStream(outputStream);
        ArrayList<Encolable> cola = colaDao.getEncolados(idUsuConsultante);

        try {
            //escribo la longitud
            dataOutPut.writeInt(cola.size());
        } catch (IOException ex) {
            log.error(ex);
            return;
        }

        for (int i = 0; i < cola.size(); i++) {
            evento = cola.get(i);
            switch (evento.getTipo()) {
                case ActualizacionUsuariosSincronizados.miTipo: {
                    ProcesaModSincro.desencola(evento.getId(), dataOutPut);
                    break;
                }
            }
        }

        try {
            //escribo la longitud
            dataOutPut.close();
        } catch (IOException ex) {
            log.error(ex);
            return;
        }

    }
}
