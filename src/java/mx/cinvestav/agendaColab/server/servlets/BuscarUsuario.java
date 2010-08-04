/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.cinvestav.agendaColab.server.servlets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mx.cinvestav.agendaColab.comun.beans.BeanUsuario;
import mx.cinvestav.agendaColab.server.logica.dao.UsuarioDao;
import org.apache.log4j.Logger;

/**
 * @author absol
 */
public class BuscarUsuario extends HttpServlet {

    private static Logger log = Logger.getLogger(BuscarUsuario.class);

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        DataInputStream input = new DataInputStream(request.getInputStream());

        byte buff[];
        int leido = input.readByte();
        int tamano = leido;
        buff = new byte[tamano];
        leido = input.read(buff, 0, tamano);
        if (leido < tamano) {
            log.error("final inesperado");
            throw new IOException("Se leyo menos de los esperado");
        }
        input.close();
        String login = new String(buff);
        UsuarioDao dao = new UsuarioDao();
        BeanUsuario usuario = dao.getUsuarioByLogin(login);
        DataOutputStream output = new DataOutputStream(response.getOutputStream());
        if (usuario != null) {
            output.writeByte(1);
            usuario.write(output);
        } else {
            output.writeByte(0);
        }
        output.close();
    }
}
