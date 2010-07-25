package mx.cinvestav.movil.http;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

public abstract class HttpRequester {
	protected String url;
	protected static final String PROTOCOL = "http://";
	HttpConnection conexion;

	/**
	 * 
	 * @param url
	 *            La url en la que se encuentran los recursos, se considera la
	 *            url como la primera parte de la uri, siendo la uri = url +
	 *            urn. La segunda parte de la uri es el nombre del recurso o
	 *            pagina, se puede tener un objeto por url para simplificar la
	 *            obtencion de varias paginas en el mismo dominio
 	 *            e.g. 
	 *                                uri
	 *            ( no )|         url       |   urn
	 *            http://www.javacourses.com/hello.txt
	 */
	public HttpRequester(String url) {
		this.url = url;
	}

	/**
	 * metodo estatico para manjear request simples y poco frecuentes para
	 * request de varios recursos (urn) de una misma url usar instancias de las
	 * clases hijas en vez de estaticos
	 * 
	 * @param uri
	 *            uri del recurso, es decir url + urn.
 	 *            e.g. 
	 *                                uri
	 *            ( no )|         url       |   urn
	 *            http://www.javacourses.com/hello.txt
*/
	 public static String RequestURI(String uri) {
		String documento = null;
		StringBuffer b = new StringBuffer();
		InputStream input = null;
		HttpConnection conexion = null;
		try {
			long len = 0;
			int ch = 0;
			conexion = (HttpConnection) Connector.open("http://" + uri);
			input = conexion.openInputStream();
			len = conexion.getLength();
			if (len != -1) {
				// Read exactly Content-Length bytes
				for (int i = 0; i < len; i++)
					if ((ch = input.read()) != -1) {
						b.append((char) ch);
					}
			} else {
				// Read until the connection is closed.
				while ((ch = input.read()) != -1) {
					len = input.available();
					b.append((char) ch);
				}
			}
			documento = b.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				conexion.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return documento;
	}
}
