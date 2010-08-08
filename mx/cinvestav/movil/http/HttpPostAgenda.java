/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.cinvestav.movil.http;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Vector;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import mx.cinvestav.agendaColab.comun.Evento;
import mx.cinvestav.agendaColab.comun.FormadorVectorEventos;
import mx.cinvestav.agendaColab.comun.beans.BeanCita;
import mx.cinvestav.agendaColab.comun.beans.BeanUsuario;

/**
 *
 * @author absol
 */
public class HttpPostAgenda extends HttpPostRequest {

    public HttpPostAgenda(String url) {
        super(url);
    }

    public BeanUsuario buscaUsuario(BeanUsuario usuario) {
        BeanUsuario result = null;
        DataInputStream input = null;
        String urn = "BuscaUsuario";
        String uri = url + urn;
        System.out.println("d:Getting: " + uri);
        try {
            conexion = (HttpConnection) Connector.open(PROTOCOL + uri);
            addHeaders();

            DataOutputStream output = new DataOutputStream(conexion.openOutputStream());
            output.writeByte(usuario.getLogin().length());
            output.write(usuario.getLogin().getBytes());
            output.flush();//se cierra?
            output.close();

            input = conexion.openDataInputStream();
            if (input.readByte() != 0) {
                result = new BeanUsuario();
                result.read(input);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                    input = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    public Vector pullEventos(Vector eventos) {
        Integer doc = null;
        DataInputStream input = null;
        Vector eventosLlegantes = null;
        String urn = "Pull";
        String uri = url + urn;

        System.out.println("d:Getting: " + uri);
        try {
            conexion = (HttpConnection) Connector.open(PROTOCOL + uri);
            addHeaders();
            addEventos(eventos);
            FormadorVectorEventos formador = new FormadorVectorEventos();
            input = conexion.openDataInputStream();
            eventosLlegantes = formador.formar(input);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                    input = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return eventosLlegantes;
    }

    protected void addEventos(Vector eventos) throws IOException {
        Evento evento;
        System.out.println("d:size: " + eventos.size());

        DataOutputStream output = new DataOutputStream(conexion.openOutputStream());

        output.writeInt(eventos.size());
        for (int i = 0; i < eventos.size(); i++) {
            evento = (Evento) eventos.elementAt(i);
            System.out.println(evento.getMiTipo());
            output.writeInt(evento.getMiTipo());
            evento.write(output);
        }
        output.flush();//se cierra?
        output.close();
    }

    public Vector getCitasAgenas(int idUsu, Date fecIni, Date fecFin) {
        Vector result = new Vector();
        DataInputStream input = null;
        String urn = "CitasAjenas";
        String uri = url + urn;
        System.out.println("d:Getting: " + uri);

        try {
            conexion = (HttpConnection) Connector.open(PROTOCOL + uri);
            addHeaders();
            DataOutputStream output = new DataOutputStream(conexion.openOutputStream());
            output.writeInt(idUsu);
            output.writeLong(fecIni.getTime());
            output.writeLong(fecFin.getTime());
            output.flush();
            output.close();

            input = conexion.openDataInputStream();
            int size = input.readInt();
            BeanCita cita;
            for(int i = 0; i < size; i++)
            {
                cita = new BeanCita();
                cita.read(input);
                result.addElement(cita);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    public BeanUsuario registraUsuario(BeanUsuario usu) {
        BeanUsuario result = null;
        DataInputStream input = null;
        String urn = "RegistraUsuairo";
        String uri = url + urn;
        System.out.println("d:Getting: " + uri);
        try {
            conexion = (HttpConnection) Connector.open(PROTOCOL + uri);
            addHeaders();

            DataOutputStream output = new DataOutputStream(conexion.openOutputStream());
            usu.write(output);
            output.flush();//se cierra?
            output.close();

            input = conexion.openDataInputStream();
            result = new BeanUsuario();
            result.read(input);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                    input = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }
}
