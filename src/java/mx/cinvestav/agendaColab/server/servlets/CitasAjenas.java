/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.cinvestav.agendaColab.server.servlets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mx.cinvestav.agendaColab.comun.beans.BeanCita;
import mx.cinvestav.agendaColab.server.logica.dao.CitasDao;

/**
 *
 * @author absol
 */
public class CitasAjenas extends HttpServlet {
private static final CitasDao dao = new CitasDao();

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        DataInputStream input = new DataInputStream(request.getInputStream());

        int idUsuario = input.readInt();
        Date fechaIni = new Date(input.readLong());
        Date fechaFin = new Date(input.readLong());
        input.close();

        ArrayList<BeanCita> result = dao.getCitasUsuario(idUsuario, fechaIni, fechaFin);
        DataOutputStream output = new DataOutputStream(response.getOutputStream());
        output.writeInt(result.size());
        for(BeanCita cita : result)
            cita.write(output);
        output.close();
    }
}
