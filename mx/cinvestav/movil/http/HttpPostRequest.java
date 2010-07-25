package mx.cinvestav.movil.http;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

import mx.cinvestav.agendaColab.comun.Evento;

public class HttpPostRequest extends HttpRequester {
	public HttpPostRequest(String url) {
		super(url);
	}

	public String getResource(String urn) {
		String doc = null;
		String uri = null;

		uri = url + urn;
		System.out.println("d:Getting: " + uri);
		try {
			conexion = (HttpConnection) Connector.open(PROTOCOL + uri);
			addHeaders();
			doc = leerResponse();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (conexion != null)
				try {
					conexion.close();
					conexion = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

		return doc;
	}

	public Vector pullEventos(Vector eventos) {
		Integer doc = null;
		DataInputStream input = null;
		Vector eventosLlegantes = null;
		//@TODO desjarcodira la urn
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
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (input != null)
				try {
					input.close();
					input = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

		return eventosLlegantes;
	}

	public String getResource(String urn, Hashtable parametros) {
		String doc = null;
		String uri = null;

		uri = url + urn;
		System.out.println("d:Getting: " + uri);
		try {
			conexion = (HttpConnection) Connector.open(PROTOCOL + uri);
			addHeaders();
			addParameters(parametros);
			doc = leerResponse();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (conexion != null)
				try {
					conexion.close();
					conexion = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

		return doc;
	}

	private void addParameters(Hashtable parametros) throws IOException {
		OutputStream output = null;
		byte postmsg[];
		Enumeration llaves = parametros.keys();
		String llave;
		String elemento;
		
		output = conexion.openOutputStream();

		while(llaves.hasMoreElements())
		{
			llave = (String) llaves.nextElement();
			postmsg = llave.getBytes();
			output.write(postmsg.length);
			output.write(postmsg);
			
			elemento = (String) parametros.get(llave);
			postmsg = elemento.getBytes();
			output.write(postmsg.length);
			output.write(postmsg);
		}

		output.flush();
	}

	private void addEventos(Vector eventos) throws IOException {
		Evento evento;
		System.out.println(eventos.size());
		
		DataOutputStream output = new DataOutputStream(conexion.openOutputStream());

		output.writeInt(eventos.size());
		for(int i = 0; i < eventos.size(); i++)
		{
			evento = (Evento) eventos.elementAt(i);
			System.out.println(evento.getMiTipo());
			output.writeInt(evento.getMiTipo());
			evento.write(output);
		}
		output.flush();//se cierra?
	}

	private void addHeaders() throws IOException {
		conexion.setRequestMethod(HttpConnection.POST);
		conexion.setRequestProperty("IF-Modified-Since",
				"10 Nov 2009 17:29:12 GMT");
		conexion.setRequestProperty("User-Agent",
				"Profile/MIDP-1.0 Confirguration/CLDC-1.0");
		conexion.setRequestProperty("Content-Language", "es-MX");
	}
	
	private String leerResponse() throws IOException
	{
		InputStream input = null;

		input = conexion.openDataInputStream();
		int ch;
		StringBuffer buffer = new StringBuffer();
		while ((ch = input.read()) != -1) {
			buffer.append((char) ch);
		}
		if (input != null)
			try {
				input.close();
			} catch (IOException e) {
				System.err.println("e:No se pudo cerrar el inputstream");
				e.printStackTrace();
			}
		return buffer.toString();
	}
}
