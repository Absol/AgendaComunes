/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.cinvestav.agendaColab.server.logica.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import mx.cinvestav.agendaColab.comun.Cambio;
import mx.cinvestav.agendaColab.comun.Sincronizacion;
import mx.cinvestav.agendaColab.comun.beans.BeanContacto;

/**
 * Dao para manejar los cambios de contactos
 * @author absol
 */
public class CambioDao extends DaoPadre {

    public int enqueCambio(Sincronizacion sincro, int idUsuarioOrigen) {
        int nuevoId = 0;
        boolean continuar = false;
        int i = 0;

        Statement stmt = null;
        String query;
        ArrayList<Integer> arr = new ArrayList<Integer>();
        try {
            stmt = con.createStatement();

            query = "SELECT proporciona_a FROM sincros WHERE id_usuario = "
                    + idUsuarioOrigen;
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                arr.add(rs.getInt("proporciona_a"));
                continuar = true;
                i++;
            }

            con.setAutoCommit(false);
            for (int j = 0; j < arr.size(); j++) {
                query = getQueryCola(arr.get(j).intValue());
                log.debug(query);
                stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    nuevoId = new Integer(rs.getInt(1));
                }

                query = getQueryCambio(nuevoId, idUsuarioOrigen);
                log.debug(query);
                stmt.executeUpdate(query);
            }

            con.commit();
        } catch (SQLException ex) {
            if (con != null) {
                try {
                    con.rollback();
                    i = -1;
                } catch (SQLException ex2) {
                    i = -2;
                    log.error(ex2);
                }
            }
            log.error(ex);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                    con.setAutoCommit(true);
                } catch (SQLException ex2) {
                    i = -3;
                    log.error(ex2);
                }
            }
        }

        return i;
    }

    public boolean guardaContactos(int idUsuarioOrigen, int contador, ArrayList cambios) {
        Cambio camb;
        BeanContacto cont;
        Statement stmt = null;
        String query;
        ArrayList<Integer> arr = new ArrayList<Integer>();
        try {
            stmt = con.createStatement();
            con.setAutoCommit(false);

            for (Object obj : cambios) {
                camb = (Cambio) obj;
                cont = camb.getContacto();
                query = "INSERT INTO contactos "
                        + "VALUES (0, '"
                        + idUsuarioOrigen + "', '"
                        + cont.getnombre() + "', '"
                        + cont.getapPaterno() + "', '"
                        + cont.getapMaterno() + "', '"
                        + cont.getEmail() + "', '"
                        + cont.getTelefono() + "', "
                        + contador + ","
                        + camb.getTipoCambio()
                        + ")";
                log.debug(query);
                stmt.executeUpdate(query);
            }

            con.commit();
        } catch (SQLException ex) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex2) {
                }
            }
            log.error(ex);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                    con.setAutoCommit(true);
                } catch (SQLException ex2) {
                    log.error(ex2);
                }
            }
        }

        return true;
    }

    public Sincronizacion dequeSincronizacion(int idCola) {
        Statement stmt = null;
        BeanContacto cont;
        Cambio camb;
        int idUsuarioOrig = 0, idContacto, idCambio = 0;
        int contador = 0, tipoCamb;
        String nombre, apPaterno, apMaterno, email, telefono;
        String query;
        Sincronizacion result = null;

        try {
            stmt = con.createStatement();

            query = "SELECT id_cambio, id_usuario_origen "
                    + "FROM cambios "
                    + "WHERE cola_id_entrada = " + idCola;
            log.debug(query);
            ResultSet rs = stmt.executeQuery(query);
            if(rs.next()) {
                idCambio = rs.getInt("id_cambio");
                idUsuarioOrig = rs.getInt("id_usuario_origen");
            } else {
                log.error("Fallo de consistencia idCola=" + idCola);
            }

            query = "SELECT id, nombre, ap_paterno, ap_materno, "
                    + "email, telefono, contador, tipo "
                    + "FROM contactos "
                    + "WHERE id_usuario_ori = " + idUsuarioOrig;
            log.debug(query);
            rs = stmt.executeQuery(query);
            result = new Sincronizacion();
            while (rs.next()) {
                idContacto = rs.getInt("id");
                contador = rs.getInt("contador");
                tipoCamb = rs.getInt("tipo");
                nombre = rs.getString("nombre");
                apPaterno = rs.getString("ap_paterno");
                apMaterno = rs.getString("ap_materno");
                email = rs.getString("email");
                telefono = rs.getString("telefono");
                cont = new BeanContacto(idContacto, idUsuarioOrig, nombre, apPaterno, apMaterno, email, telefono);
                camb = new Cambio(cont, tipoCamb);
                result.add(camb);
                contador--;
            }
            rs.close();

            if (contador > 0) {
                query = "UPDATE contactos "
                        + "SET contador = " + contador + " "
                        + "WHERE id_usuario_ori = " + idUsuarioOrig;
            } else {
                query = "DELETE FROM contactos "
                        + "WHERE id_usuario_ori = " + idUsuarioOrig;
            }
            String query2 = "DELETE FROM cambios WHERE id_cambio = " + idCambio;

            log.debug(query);
            log.debug(query2);
            con.setAutoCommit(false);
            stmt.executeUpdate(query);
            stmt.executeUpdate(query2);
            con.commit();
        } catch (SQLException ex) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex2) {
                    log.error(ex2);
                }
            }
            log.error(ex);
        } finally {
            if (stmt != null) {
                try {
                    con.setAutoCommit(true);
                    stmt.close();
                } catch (SQLException ex2) {
                    log.error(ex2);
                }
            }
        }

        return result;
    }

    private String getQueryCola(int idUsuDest) {
        String query = "INSERT INTO cola "
                + "VALUES (0, "
                + "NOW(), "
                + idUsuDest + ", "
                + Sincronizacion.miTipo
                + ")";
        return query;
    }

    private String getQueryCambio(int idCola, int idUsuOrig) {
        String query = "INSERT INTO cambios "
                + "VALUES (0, "
                + idCola + ", "
                + idUsuOrig
                + ")";
        return query;
    }
}
