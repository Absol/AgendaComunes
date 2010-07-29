package mx.cinvestav.agendaColab.server.logica.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.log4j.Logger;

public class DaoPadre {
    protected static Logger log = Logger.getLogger(DaoPadre.class);
    Connection con;

    public DaoPadre(String url, String user, String pass) {
        if (con == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection(url, user, pass);
            } catch (SQLException ex) {
                log.error(ex);
            } catch (ClassNotFoundException ex) {
                log.error(ex);
            }
        }
    }
}
