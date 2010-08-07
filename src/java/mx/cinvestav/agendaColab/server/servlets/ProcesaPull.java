package mx.cinvestav.agendaColab.server.servlets;

/**
 * @author absol
 */

import mx.cinvestav.agendaColab.server.logica.ProcesEvento;
import mx.cinvestav.agendaColab.server.utils.ColaEventos;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProcesaPull extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        int id = ProcesEvento.Procesa(request.getInputStream());

        ColaEventos.obtenEventos(id, response.getOutputStream());
    }

    public void destroy() {
    }
}
