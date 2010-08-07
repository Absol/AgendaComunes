/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.cinvestav.agendaColab.server.logica.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import mx.cinvestav.agendaColab.server.utils.Encolable;

/**
 *
 * @author absol
 */
public class ColaDao extends DaoPadre {

    public ArrayList<Encolable> getEncolados(int idUsuario)
    {
        ArrayList<Encolable> result = new ArrayList<Encolable>();
        Statement stmt = null;

        try {
            stmt = con.createStatement();
            String query = "SELECT id_entrada, tipo_evento FROM cola "
                    + "WHERE id_usuario = "
                    + idUsuario;
            log.debug(query);
            ResultSet rs = stmt.executeQuery(query);
            int idVal, tipoEvento;
            while (rs.next()) {
                idVal = rs.getInt("id_entrada");
                tipoEvento = rs.getInt("tipo_evento");
                log.debug(
                        "leido(" + idVal
                        + ", " + tipoEvento
                        + ")");
                Encolable event = new Encolable(idVal, tipoEvento);
                result.add(event);
            }
            rs.close();

             query = "DELETE FROM cola "
                    + "WHERE id_usuario = "
                    + idUsuario;
            log.debug(query);
            stmt.executeUpdate(query);
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
