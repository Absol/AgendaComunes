/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.cinvestav.agendaColab.server.logica;

import mx.cinvestav.agendaColab.server.logica.dao.CitasDao;
import mx.cinvestav.agendaColab.comun.CitaPublica;
import mx.cinvestav.agendaColab.comun.beans.BeanCita;
import org.apache.log4j.Logger;

/**
 *
 * @author absol
 */
class ProcesaCitaPublica {
    private static Logger log = Logger.getLogger(ProcesaCitaPublica.class);
    private static CitasDao myCitasDao = null;

    public static boolean procesa(int idUsuMandante, CitaPublica citaPub) {
        if(myCitasDao == null)
            myCitasDao = new CitasDao();
        int result = 0;

        BeanCita cita = citaPub.getCita();

        if(cita.getNivel() == BeanCita.PRIVADA)
        {
            log.error("Una cita privada fue mandada al server "
                    + idUsuMandante + "-" + citaPub.getCita().getIdCita());
        }
        else
        {
            if(cita.getNivel() != BeanCita.PUBLICA){
                cita = new BeanCita(cita.getIdCita(), "ocupado",
                        cita.getFechaInicio(), cita.getFechaTermino(),
                        cita.getNivel());
            }
            result = myCitasDao.guardaCita(idUsuMandante, cita);
            if(result < 0)
                return false;
        }

        return true;
    }

}
