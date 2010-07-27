package mx.cinvestav.agendaColab.server.logica;

import mx.cinvestav.agendaColab.server.logica.dao.ColaDao;
import mx.cinvestav.agendaColab.server.logica.dao.SincroDao;
import mx.cinvestav.agendaColab.comun.ActualizacionUsuariosSincronizados;
import org.apache.log4j.Logger;

public class ProcesaModSincro {
    private static Logger log = Logger.getLogger(ProcesaModSincro.class);

    private static SincroDao mySincroDao = null;
    private static ColaDao myColaDao = null;

    public static boolean procesa(int id, ActualizacionUsuariosSincronizados actualizacion) {
        if(mySincroDao == null)
            mySincroDao = new SincroDao();
        if(myColaDao == null)
            myColaDao = new ColaDao();
        int result = 0;

        if(actualizacion.getTipoAct() == ActualizacionUsuariosSincronizados.NUEVA_SINCRO)
        {
            result = mySincroDao.nuevaSincro(id, actualizacion.getUsuario().getId());
            if(result < 0)
                return false;
            result = myColaDao.enqueActSincro(actualizacion, id);
            if(result < 0)
            {
                log.error("Fallo en transaccion");
                return false;
            }
        }
        else
        {
            result = mySincroDao.BorraSincro(id, actualizacion.getUsuario().getId());
        }

        if(result > 0)
            return true;
        else
            return false;
    }
}
