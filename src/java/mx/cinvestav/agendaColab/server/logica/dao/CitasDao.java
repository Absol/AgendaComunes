/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.cinvestav.agendaColab.server.logica.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import mx.cinvestav.agendaColab.comun.beans.BeanCita;

/**
 * Dao para guardar y consultar citas
 * @author absol
 */
public class CitasDao extends DaoPadre{
private static final SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public int guardaCita(int idUsuario, BeanCita cita) {
        int nuevoId = 0;
        Statement stmt = null;
        String query;
        ResultSet rs;

        try {
            stmt = con.createStatement();

                query = "INSERT INTO citas VALUES(0, "
                        + idUsuario +", '"
                        + cita.getAsunto() + "', '"
                        + formater.format(cita.getFechaInicio()) + "', '"
                        + formater.format(cita.getFechaTermino()) + "', "
                        + cita.getNivel()
                        + ")";
                log.debug(query);
                stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    nuevoId = new Integer(rs.getInt(1));
                }
                rs.close();
        } catch (SQLException ex) {
            log.error(ex);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex2) {
                    log.error(ex2);
                }
            }
        }

        return nuevoId;
    }

    public ArrayList<BeanCita> getCitasUsuario(int idUsuario, Date fecIni, Date fecFin){
        ArrayList<BeanCita> result = new ArrayList<BeanCita>();
        Statement stmt = null;

        try {
            stmt = con.createStatement();
            String query = "SELECT id_cita, asunto, fecha_inicio, fecha_fin, nivel "
                    + "FROM citas "
                    + "WHERE id_usuario = " + idUsuario + " "
                    + "AND nivel <> " + BeanCita.PRIVADA + " "
                    + "AND fecha_inicio >= '" + formater.format(fecIni) + "' "
                    + "AND fecha_fin <= '" + formater.format(fecFin) + "'";
            log.debug(query);
            ResultSet rs = stmt.executeQuery(query);
            int idCita, nivel;
            Date fechaIni = null, fechaFin = null;
            String asunto;
            while (rs.next()) {
                idCita = rs.getInt("id_cita");
                asunto = rs.getString("asunto");
                try {
                    //                fechaIni = rs.getDate("fecha_inicio");
                    //                fechaFin = rs.getDate("fecha_fin");
                    fechaIni = formater.parse(rs.getString("fecha_inicio"));
                        fechaFin = formater.parse(rs.getString("fecha_fin"));
                } catch (ParseException ex) {
                    log.error(ex);
                }
                nivel = rs.getInt("nivel");
                log.debug(
                        "leido(" + idCita
                        + ", " + asunto
                        + ", " + fechaIni
                        + ", " + fechaFin
                        + ")");
                BeanCita cita = new BeanCita(idCita, asunto, fechaIni, fechaFin, nivel);
                result.add(cita);
            }
            rs.close();
        } catch (SQLException ex) {
            log.error(ex);
        } finally {
            if (stmt != null) {
                try {
                stmt.close();
                } catch (SQLException ex2) {
                    log.error(ex2);
                }
            }
        }

        return result;
    }
}
