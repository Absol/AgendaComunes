/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.cinvestav.agendaColab.DAO;

import java.util.Vector;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;

import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotOpenException;
import mx.cinvestav.agendaColab.comun.beans.BeanContacto;
import mx.cinvestav.agendaColab.comun.beans.BeanUsuario;
import mx.cinvestav.agendaColab.utils.Utils;



import mx.cinvestav.agendaColab.dataStorage.SimpleDataSource;

/**
 *
 * @author rockderick
 */
public class UsuarioDAO extends AbstractDAO{
     private static final String dataStorage="usuario";

	public UsuarioDAO(){
		//super(objMidlet);
		datasource=SimpleDataSource.getInstance(dataStorage);
	}

	public Object create(Object obj) {
		      RecordStore rs=datasource.getRecordStore();

		      BeanUsuario user =(BeanUsuario)obj;
		      ByteArrayOutputStream baos=new ByteArrayOutputStream();
		      DataOutputStream dos = new DataOutputStream(baos);

		      try {
		    	dos.writeUTF(user.toString());
				
				dos.flush();

				byte[] datos = baos.toByteArray();

	            int id = rs.addRecord(datos, 0, datos.length);

	            dos.close();

	            baos.close();
			} catch (IOException e) {

				e.printStackTrace();
			}catch(RecordStoreException e){
				e.printStackTrace();
			}finally{
				//datasource.closeRecordStore();
			}

		return user;
	}
	
	public boolean validate(String nombre,String passwd){
		RecordStore rs=datasource.getRecordStore();
		String arr[];
		 try{
			 
	            for(int i =1; i <= rs.getNumRecords();i++){
	            	
	                String aux = getRecord(i,dataStorage);
	                arr=Utils.split(aux,"-");
	                if(arr[0].equals(nombre) && arr[8].equals(passwd))
	                	return true;
	                
	            }
	        }catch(RecordStoreNotOpenException e){
	            System.out.println(e.getMessage());
	        }
	     return false;   
	}

    public Object cargar(Integer id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Vector getLista() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public static void guardaMyUsuario(BeanUsuario myUsuer) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public static BeanUsuario getMyUser() {
        //To-Do desharcodear
        return new BeanUsuario(1, "Absol", "Zapato");
    }
}
