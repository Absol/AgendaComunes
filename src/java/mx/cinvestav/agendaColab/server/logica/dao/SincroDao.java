package mx.cinvestav.agendaColab.server.logica.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import mx.cinvestav.agendaColab.comun.ActualizacionUsuariosSincronizados;
import mx.cinvestav.agendaColab.comun.beans.BeanUsuario;

public class SincroDao extends DaoPadre {

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
            con.setAutoCommit(true);
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
    public ActualizacionUsuariosSincronizados dequeActSincro(int idCola) {
        int idBorrado = 0;
        ActualizacionUsuariosSincronizados result = null;

        Statement stmt = null;
        try {
            stmt = con.createStatement();
            String query = "SELECT tipo, id_usuario_origen "
                    + "FROM act_sincro "
                    + "WHERE cola_id_entrada = " + idCola;
            log.debug(query);
            ResultSet rs = stmt.executeQuery(query);
            int idUsu, tipoAct;
            if(rs.next()) {
                idUsu = rs.getInt("id_usuario_origen");
                tipoAct = rs.getInt("tipo");
                log.debug(
                        "leido(" + idUsu
                        + ", " + tipoAct
                        + ")");
                result = new ActualizacionUsuariosSincronizados(new BeanUsuario(idUsu, null, null), tipoAct);
            }
            rs.close();
            stmt.close();

            stmt = con.createStatement();
            con.setAutoCommit(false);
            query = "DELETE FROM cola "
                    + "WHERE id_entrada = " + idCola;
            log.debug(query);
            stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                idBorrado = new Integer(rs.getInt(1));
                rs.close();
            }

            query = "DELETE FROM act_sincro "
                    +"WHERE cola_id_entrada = " + idCola;
            log.debug(query);
            stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                idBorrado = new Integer(rs.getInt(1));
                rs.close();
            }

            con.commit();
        } catch (SQLException ex) {
            if (con != null) {
                result = null;
                try {
                    con.rollback();
                    idBorrado = -1;
                } catch (SQLException ex2) {
                    idBorrado = -2;
                    log.error(ex2);
                }
            }
            log.error(ex);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex2) {
                    idBorrado = -3;
                    log.error(ex2);
                }
            }
        }

        return result;
    }
}
