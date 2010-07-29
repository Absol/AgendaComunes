/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.cinvestav.agendaColab.server.logica.dao;

import mx.cinvestav.agendaColab.comun.ActualizacionUsuariosSincronizados;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import mx.cinvestav.agendaColab.comun.beans.BeanUsuario;
import mx.cinvestav.agendaColab.server.utils.Encolable;

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
