package mx.cinvestav.agendaColab.server.logica.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SincroDao extends DaoPadre {

    public SincroDao() {
        super("jdbc:mysql://belldandy.no-ip.info:3306/agenda_colab", "agenda", "zapato");
    }

    public int nuevaSincro(int id, int id0) {
        int nuevoId = 0;
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            String query = "INSERT INTO sincros "
                    + "VALUES (0, "
                    + "(SELECT id_usuario FROM usuarios WHERE (id_usuario = " + id + ")), "
                    + "(SELECT id_usuario FROM usuarios WHERE (id_usuario = "
                    + id0 + "))"
                    + ")";
            log.debug(query);
            stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                nuevoId = rs.getInt(1);
            }
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

    public int BorraSincro(int idUsuMandante, int idUsuBorrado) {
        int idBorrado = 0;
        Statement stmt = null;
        try {
            // TODO borrar en el otro sentido
            stmt = con.createStatement();
            String query = "DELETE FROM sincros "
                    + "WHERE "
                    + "id_usuario = " + idUsuMandante + " AND "
                    + "proporciona_a = " + idUsuBorrado;
            log.debug(query);
            stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                idBorrado = rs.getInt(1);
            }
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

        return idBorrado;
    }
}
