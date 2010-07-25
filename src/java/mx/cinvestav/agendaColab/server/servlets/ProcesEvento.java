/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.cinvestav.agendaColab.server.servlets;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.logging.Level;
import javax.servlet.ServletInputStream;
import mx.cinvestav.agendaColab.comun.ActualizacionUsuariosSincronizados;
import org.apache.log4j.Logger;

/**
 *
 * @author absol
 */
class ProcesEvento {
    private static Logger log = Logger.getLogger(ProcesEvento.class);

    public ProcesEvento(ServletInputStream inputStream) {
        DataInputStream is = new DataInputStream(inputStream);
        int tipo = -1;
        int numEventos = 0;
        int i;

        try {
            numEventos = is.readInt();
            log.debug(numEventos);

            for (i = 0; i < numEventos; i++) {
                tipo = is.readInt();
            log.debug(tipo);

                switch (tipo) {
                    case ActualizacionUsuariosSincronizados.miTipo: {
                        ActualizacionUsuariosSincronizados act = new ActualizacionUsuariosSincronizados();
                        act.read(is);
                        if (act.getTipoAct() == ActualizacionUsuariosSincronizados.NUEVA_SINCRO) {
                            log.debug("Nueva sincro: " + act);
                        } else {
                            log.debug("Borra sincro: " + act);
                        }
                    }
                }
            }
        } catch (IOException ex) {
            log.error(ex);
        } finally {
            try {
                is.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
