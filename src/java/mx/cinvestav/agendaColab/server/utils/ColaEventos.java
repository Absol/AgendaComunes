package mx.cinvestav.agendaColab.server.utils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletOutputStream;
import mx.cinvestav.agendaColab.comun.ActualizacionUsuariosSincronizados;
import mx.cinvestav.agendaColab.comun.Cancelacion;
import mx.cinvestav.agendaColab.comun.CitaPublica;
import mx.cinvestav.agendaColab.comun.Confirmacion;
import mx.cinvestav.agendaColab.comun.Notificacion;
import mx.cinvestav.agendaColab.comun.Respuesta;
import mx.cinvestav.agendaColab.comun.Sincronizacion;
import mx.cinvestav.agendaColab.server.logica.ProcesaCambios;
import mx.cinvestav.agendaColab.server.logica.ProcesaModSincro;
import mx.cinvestav.agendaColab.server.logica.dao.ColaDao;
import org.apache.log4j.Logger;

/**
 * Clase que se encara de escribir los eventos que se encuentren en la cola del usuario
 * entrado, llama a logicas para que hagan las operaciones correspondientes en la DB
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
                case Sincronizacion.miTipo: {
                    ProcesaCambios.desencola(evento.getId(), dataOutPut);
                    break;
                }
                case CitaPublica.miTipo: {
                    ProcesaCambios.desencola(evento.getId(), dataOutPut);
                    break;
                }
                case Cancelacion.miTipo: {
                    throw new UnsupportedOperationException("Not yet implemented");
                }
                case Respuesta.miTipo: {
                    throw new UnsupportedOperationException("Not yet implemented");
                }
                case Notificacion.miTipo: {
                    throw new UnsupportedOperationException("Not yet implemented");
                }
                case Confirmacion.miTipo: {
                    throw new UnsupportedOperationException("Not yet implemented");
                }
            }
        }

        try {
            dataOutPut.close();
        } catch (IOException ex) {
            log.error(ex);
            return;
        }

    }
}
