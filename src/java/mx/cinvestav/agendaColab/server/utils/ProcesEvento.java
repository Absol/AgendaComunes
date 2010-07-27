/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.cinvestav.agendaColab.server.utils;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;
import mx.cinvestav.agendaColab.comun.ActualizacionUsuariosSincronizados;
import mx.cinvestav.agendaColab.comun.Evento;
import mx.cinvestav.agendaColab.comun.FormadorVectorEventos;
import mx.cinvestav.agendaColab.comun.PullRequest;
import org.apache.log4j.Logger;
import mx.cinvestav.agendaColab.server.logica.ProcesaModSincro;
/**
 *
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