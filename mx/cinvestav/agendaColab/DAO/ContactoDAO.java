/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.cinvestav.agendaColab.DAO;

/**
 *
 * @author rockderick
 */
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
import mx.cinvestav.agendaColab.utils.Utils;



import mx.cinvestav.agendaColab.dataStorage.SimpleDataSource;



public class ContactoDAO extends AbstractDAO {

	
        private static final String dataStorage="contacto";

	public ContactoDAO(){
		//super(objMidlet);
		datasource=SimpleDataSource.getInstance(dataStorage);
	}

	public Object create(Object obj) {
		      RecordStore rs=datasource.getRecordStore();

		      BeanContacto contact =(BeanContacto)obj;
		      ByteArrayOutputStream baos=new ByteArrayOutputStream();
		      DataOutputStream dos = new DataOutputStream(baos);

		      try {
		    	dos.writeUTF(contact.toString());
				//dos.writeUTF(usr.getName() + "-" + usr.getPass());
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

		return contact;
	}

	
	public boolean borrar(Integer identifier) {
		RecordStore rs=datasource.getRecordStore();
		String arr[];
		BeanContacto usr;
		 try{
	            System.out.println("antes de borrar");
	            for(int i =1; i <= rs.getNumRecords();i++){
	            	System.out.println("despues de borrar");
	                String aux = getRecord(i,dataStorage);
	                arr=Utils.split(aux,"-");
                        //BeanContacto(int id, int idUsu, String nombre, String apPaterno,String apMaterno, String email, String telefono);
                        int id=new Integer(Integer.parseInt(arr[0])).intValue();
                        int idUsu=new Integer(Integer.parseInt(arr[1])).intValue();
                        usr=new BeanContacto(id, idUsu, arr[2], arr[3], arr[4], arr[5], arr[6]);
	                //usr=new User(arr[0],arr[1],new Date(),new Integer(Integer.parseInt(arr[3])),arr[4],1,arr[6],arr[7],arr[8],3);
	                Integer clv=new Integer(Integer.parseInt(arr[0]));
	                System.out.println("clave*******: "+clv);
	                if(clv.equals(identifier)){
	                	rs.deleteRecord(i);
	                	vec.removeElement(usr);
	                	return true;
	                }

	            }
	        }catch(Exception e){
	            e.printStackTrace();
	        }
	     return false;
	}

        public Vector getLista(){
    	RecordStore rs=datasource.getRecordStore();
		String arr[];
		BeanContacto usr;

		 try{

	            for(int i =1; i <= rs.getNumRecords();i++){

	                String aux = getRecord(i,dataStorage);
	                arr=Utils.split(aux,"-");
                        System.out.println("arr[0]: "+arr[0]);
                        int id=new Integer(Integer.parseInt(arr[0])).intValue();
                        int idUsu=new Integer(Integer.parseInt(arr[1])).intValue();
	                usr=new BeanContacto(id, idUsu, arr[2], arr[3], arr[4], arr[5], arr[6]);
	                vec.addElement(usr);
	            }
	        }catch(RecordStoreNotOpenException e){
	            System.out.println(e.getMessage());
	        }
	     return vec;
    }

	public Object cargar(Integer identifier) {
		RecordStore rs=datasource.getRecordStore();
		String arr[];
		BeanContacto usr;
		 try{
		    for(int i =1; i <= rs.getNumRecords();i++){
	            	String aux = getRecord(i,dataStorage);
                        System.out.println("aux: "+aux);
	                arr=Utils.split(aux,"-");
                        int id=new Integer(Integer.parseInt(arr[0])).intValue();
                        int idUsu=new Integer(Integer.parseInt(arr[1])).intValue();
	                usr=new BeanContacto(id, idUsu, arr[2], arr[3], arr[4], arr[5], arr[6]);
	                
	                Integer clv=new Integer(Integer.parseInt(arr[0]));
	                if(clv.equals(identifier)){
	                	return usr;
	                }

	            }
	        }catch(Exception e){
	            e.printStackTrace();
	        }
	     return null;

	}
	public void modificar(BeanContacto user){
	     RecordStore rs=datasource.getRecordStore();
	      ByteArrayOutputStream baos=new ByteArrayOutputStream();
	      DataOutputStream dos = new DataOutputStream(baos);
	      cargar(new Integer(user.getIdUsuario()));
		 try {
		    	dos.writeUTF(user.toString());
				//dos.writeUTF(usr.getName() + "-" + usr.getPass());
				dos.flush();

				byte[] datos = baos.toByteArray();

				rs.setRecord(1, datos, 0, datos.length);



	            dos.close();

	            baos.close();
			} catch (IOException e) {

				e.printStackTrace();
			}catch(RecordStoreException e){
				e.printStackTrace();
			}finally{
				//datasource.closeRecordStore();
			}
	}



}

