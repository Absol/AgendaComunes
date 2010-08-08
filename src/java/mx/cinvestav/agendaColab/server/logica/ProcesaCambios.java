package mx.cinvestav.agendaColab.server.logica;

import java.io.IOException;
import java.io.DataOutputStream;
import java.util.ArrayList;
import mx.cinvestav.agendaColab.comun.Sincronizacion;
import mx.cinvestav.agendaColab.server.logica.dao.CambioDao;
import org.apache.log4j.Logger;

/**
 * Procesa cambios de una sincronizacion
 * @author absol
 */
public class ProcesaCambios {

    private static Logger log = Logger.getLogger(ProcesaCambios.class);
    private static CambioDao cambiosDao = null;

    public static boolean procesa(int idUsuarioOrigen, Sincronizacion sincro) {
        if (cambiosDao == null) {
            cambiosDao = new CambioDao();
        }
        int result = 0;

        ArrayList arr = new ArrayList(sincro.getListaCambios());

        int contador = cambiosDao.enqueCambio(sincro, idUsuarioOrigen);

        cambiosDao.guardaContactos(idUsuarioOrigen, contador, arr);

        return false;
    }

    public static void desencola(int idCola, DataOutputStream dataOutPut) {
        Sincronizacion sincr = cambiosDao.dequeSincronizacion(idCola);
        try {
            dataOutPut.writeInt(Sincronizacion.miTipo);
        } catch (IOException ex) {
            log.error(ex);
        }
        sincr.write(dataOutPut);
    }
}
