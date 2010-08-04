package mx.cinvestav.agendaColab.server.logica.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.log4j.Logger;

public class DaoPadre {
protected static Logger log = Logger.getLogger(DaoPadre.class);
    Connection con;

    public DaoPadre() {

        if (con == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://belldandy.no-ip.info:3306/agenda_colab", "agenda", "zapato");
            } catch (SQLException ex) {
                log.error(ex);
            } catch (ClassNotFoundException ex) {
                log.error(ex);
            }
        }
    }
}
