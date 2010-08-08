package mx.cinvestav.agendaColab.server.logica;

import java.io.DataOutputStream;
import java.io.IOException;
import mx.cinvestav.agendaColab.server.logica.dao.SincroDao;
import mx.cinvestav.agendaColab.comun.ActualizacionUsuariosSincronizados;
import org.apache.log4j.Logger;

public class ProcesaModSincro {
    private static Logger log = Logger.getLogger(ProcesaModSincro.class);

    private static SincroDao mySincroDao = null;

    public static boolean procesa(int idUsuMandante, ActualizacionUsuariosSincronizados actualizacion) {
        if(mySincroDao == null)
            mySincroDao = new SincroDao();
        int result = 0;

        if(actualizacion.getTipoAct() == ActualizacionUsuariosSincronizados.NUEVA_SINCRO)
        {
            result = mySincroDao.nuevaSincro(idUsuMandante, actualizacion.getUsuario().getId());
            if(result < 0)
                return false;
            result = mySincroDao.enqueActSincro(actualizacion, idUsuMandante);
            if(result < 0)
            {
                log.error("Fallo en transaccion");
                return false;
            }
        }
        else
        {
            // TODO borrar de posible registros de la cola no
            result = mySincroDao.BorraSincro(idUsuMandante, actualizacion.getUsuario().getId());
        }

        if(result > 0)
            return true;
        else
            return false;
    }

    public static void desencola(int idCola, DataOutputStream dataOutPut) {
        ActualizacionUsuariosSincronizados act = mySincroDao.dequeActSincro(idCola);
        try {
            dataOutPut.writeInt(ActualizacionUsuariosSincronizados.miTipo);
        } catch (IOException ex) {
            log.error(ex);
        }
        act.write(dataOutPut);
    }
}
