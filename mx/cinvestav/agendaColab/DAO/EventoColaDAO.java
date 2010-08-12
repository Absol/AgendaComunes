package mx.cinvestav.agendaColab.DAO;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

import mx.cinvestav.agendaColab.comun.ActualizacionUsuariosSincronizados;
import mx.cinvestav.agendaColab.comun.Cambio;
import mx.cinvestav.agendaColab.comun.CitaPublica;
import mx.cinvestav.agendaColab.comun.Confirmacion;
import mx.cinvestav.agendaColab.comun.Evento;
import mx.cinvestav.agendaColab.comun.Respuesta;
import mx.cinvestav.agendaColab.comun.Sincronizacion;
import mx.cinvestav.agendaColab.comun.beans.BeanContacto;
import mx.cinvestav.agendaColab.dataStorage.SimpleDataSource;


public class EventoColaDAO  extends AbstractEventoColaDAO{
	private static final String dataStorage="evento";

	public EventoColaDAO() {
		// TODO Auto-generated constructor stub
		datasource=SimpleDataSource.getInstance(dataStorage);
	}

	public void guardarEvento(Evento e) {
	      RecordStore rs=datasource.getRecordStore();
          ByteArrayOutputStream baos=new ByteArrayOutputStream();
	      DataOutputStream dos = new DataOutputStream(baos);
	      
	     
	     try {
                 
              if(e instanceof ActualizacionUsuariosSincronizados){
                  ActualizacionUsuariosSincronizados aus=(ActualizacionUsuariosSincronizados)e;
                  dos.writeUTF(aus.toString());
	    	  System.out.println("Soy una ActualizacionUsuariosSincronizados "+aus);
	      }
	          
	      if (e instanceof CitaPublica){
                  CitaPublica cp=(CitaPublica)e;
                  dos.writeUTF(cp.toString());
	    	  System.out.println("Soy una cita Publica" +cp);
	      }
	      if (e instanceof Confirmacion){
                  Confirmacion c=(Confirmacion)e;
                  dos.writeUTF(c.toString());
	    	  System.out.println("******Soy una Confirmacion "+c);
	      }
	      if (e instanceof Respuesta){
                  Respuesta r=(Respuesta)e;
                  dos.writeUTF(r.toString());
	    	  System.out.println("Soy una Respuesta "+r);
	      }
             if (e instanceof Sincronizacion){
                 Sincronizacion s=(Sincronizacion)e;
                 dos.writeUTF(s.toString());
        	 System.out.println("Soy una Sincronizacion "+s);
	      }

	    	        //dos.writeUTF(contact.toString());
              //dos.writeUTF(usr.getName() + "-" + usr.getPass());
	      dos.flush();

			byte[] datos = baos.toByteArray();

          int id = rs.addRecord(datos, 0, datos.length);

          dos.close();

          baos.close();
		} catch (IOException ioe) {

			ioe.printStackTrace();
		}catch(RecordStoreException rse){
			rse.printStackTrace();
		}finally{
			//datasource.closeRecordStore();
		}

	   
	   	
		
	}

	

}
