/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.cinvestav.agendaColab.server.logica.dao;

import mx.cinvestav.agendaColab.comun.ActualizacionUsuariosSincronizados;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author absol
 */
public class ColaDao extends DaoPadre {

    public ColaDao() {
        super("jdbc:mysql://belldandy.no-ip.info:3306/agenda_colab", "agenda", "zapato");
    }

    public int enqueActSincro(ActualizacionUsuariosSincronizados actualizacion, int UsuOrigen) {
        int nuevoId = 0;

        Statement stmt = null;
        try {
            stmt = con.createStatement();
            con.setAutoCommit(false);
            String query = "INSERT INTO cola "
                    + "VALUES (0, "
                    + "NOW(), "
                    + actualizacion.getUsuario().getId() + ", "
                    + ActualizacionUsuariosSincronizados.NUEVA_SINCRO
                    + ")";
            log.debug(query);
            stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                nuevoId = new Integer(rs.getInt(1));
            }

            query = "INSERT INTO act_sincro "
                    +"VALUES (0, "
                    + nuevoId + ", "
                    + actualizacion.getTipoAct() + ", "
                    + UsuOrigen
                    + ")";
            log.debug(query);
            stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                nuevoId = new Integer(rs.getInt(1));
            }

            con.commit();
        } catch (SQLException ex) {
            if (con != null) {
                try {
                    con.rollback();
                    nuevoId = -1;
                } catch (SQLException ex2) {
                    nuevoId = -2;
                    log.error(ex2);
                }
            }
            log.error(ex);
        } finally {
            if (stmt != null) {
                try {
                stmt.close();
                } catch (SQLException ex2) {
                    nuevoId = -3;
                    log.error(ex2);
                }
            }
        }

        return nuevoId;
    }
}
