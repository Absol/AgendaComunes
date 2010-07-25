package mx.cinvestav.agendaColab.server.servlets;

/**
 *
 * @author absol
 */

import java.io.DataOutputStream;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mx.cinvestav.agendaColab.comun.ActualizacionUsuariosSincronizados;
import mx.cinvestav.agendaColab.comun.beans.BeanUsuario;

public class ProcesaPull extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        new ProcesEvento(request.getInputStream());

        BeanUsuario bean = new BeanUsuario(3, "Genaro", "zapato");
        ActualizacionUsuariosSincronizados act =
                new ActualizacionUsuariosSincronizados(bean,
                ActualizacionUsuariosSincronizados.NUEVA_SINCRO);


        DataOutputStream dataOutPut = new DataOutputStream(response.getOutputStream());
        //escribo la longitud
        dataOutPut.writeInt(1);
        //Escribo el tipo del primer Evento
        dataOutPut.writeInt(ActualizacionUsuariosSincronizados.miTipo);
        //Escribo el Evento
        act.write(dataOutPut);
        dataOutPut.close();
    }

    public void destroy() {
    }
}
