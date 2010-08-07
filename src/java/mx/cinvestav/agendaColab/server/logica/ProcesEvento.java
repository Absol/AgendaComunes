package mx.cinvestav.agendaColab.server.logica;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;
import org.apache.log4j.Logger;
import mx.cinvestav.agendaColab.comun.ActualizacionUsuariosSincronizados;
import mx.cinvestav.agendaColab.comun.Cancelacion;
import mx.cinvestav.agendaColab.comun.CitaPublica;
import mx.cinvestav.agendaColab.comun.Confirmacion;
import mx.cinvestav.agendaColab.comun.Evento;
import mx.cinvestav.agendaColab.comun.FormadorVectorEventos;
import mx.cinvestav.agendaColab.comun.Notificacion;
import mx.cinvestav.agendaColab.comun.PullRequest;
import mx.cinvestav.agendaColab.comun.Respuesta;
import mx.cinvestav.agendaColab.comun.Sincronizacion;
/**
 * Clase para procesar los eventos entrantes, se llama a una logica especifica
 * que procese cada evento especificio
 * @author absol
 */
public class ProcesEvento {

    private static Logger log = Logger.getLogger(ProcesEvento.class);

    public static int Procesa(InputStream inputStream) {
        FormadorVectorEventos formador = new FormadorVectorEventos();
        Vector entradaVec = null;
        DataInputStream dInput = null;
        Evento evento = null;
        int tipo = -1;
        int numEventos = 0;
        int i;
        int id = 0;

        try {
            dInput = new DataInputStream(inputStream);
            entradaVec = formador.formar(dInput);
        } catch (IOException ex) {
            log.error(ex);
        }

        numEventos = entradaVec.size();
        log.debug("Eventos: " + numEventos);

        for (i = 0; i < numEventos; i++) {
            evento = (Evento) entradaVec.get(i);
            tipo = evento.getMiTipo();
            log.debug("Tipo: " + tipo);

            switch (tipo) {
                case ActualizacionUsuariosSincronizados.miTipo: {
                    ActualizacionUsuariosSincronizados act = (ActualizacionUsuariosSincronizados) evento;
                    ProcesaModSincro.procesa(id, act);
                    break;
                }
                case PullRequest.miTipo: {
                    PullRequest rqst = (PullRequest) evento;
                    id = rqst.getUsuario().getId();
                    break;
                }
                case Sincronizacion.miTipo: {
                    Sincronizacion sincro = (Sincronizacion) evento;
                    ProcesaCambios.procesa(id, sincro);
                    break;
                }
                case CitaPublica.miTipo: {
                    CitaPublica cita = (CitaPublica) evento;
                    ProcesaCitaPublica.procesa(id, cita);
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
            dInput.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return id;
    }
}