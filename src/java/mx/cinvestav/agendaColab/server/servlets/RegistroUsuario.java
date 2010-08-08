package mx.cinvestav.agendaColab.server.servlets;

/**
 * @author absol
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import mx.cinvestav.agendaColab.server.logica.ProcesEvento;
import mx.cinvestav.agendaColab.server.utils.ColaEventos;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mx.cinvestav.agendaColab.comun.beans.BeanCita;
import mx.cinvestav.agendaColab.comun.beans.BeanUsuario;
import mx.cinvestav.agendaColab.server.logica.dao.UsuarioDao;

public class RegistroUsuario extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        UsuarioDao dao = new UsuarioDao();
        DataInputStream dInput = new DataInputStream(request.getInputStream());
        BeanUsuario usu = new BeanUsuario();
        usu.read(dInput);
        usu = dao.registraUsuario(usu);
        dInput.close();

        DataOutputStream output = new DataOutputStream(response.getOutputStream());
        usu.write(output);
        output.close();
    }

    public void destroy() {
    }
}
