/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.cinvestav.agendaColab.DAO;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;

import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotOpenException;
import mx.cinvestav.agendaColab.comun.beans.BeanCita;
import mx.cinvestav.agendaColab.dataStorage.SimpleDataSource;
import mx.cinvestav.agendaColab.utils.Utils;

/**
 *
 * @author rockderick
 */
public class CItaDAO extends AbstractDAO{

    private static final String dataStorage="cita";

	public CItaDAO(){
		//super(objMidlet);
		datasource=SimpleDataSource.getInstance(dataStorage);
	}

	public Object create(Object obj) {
		      RecordStore rs=datasource.getRecordStore();

		      BeanCita cita =(BeanCita)obj;
		      ByteArrayOutputStream baos=new ByteArrayOutputStream();
		      DataOutputStream dos = new DataOutputStream(baos);

		      try {
		    	dos.writeUTF(cita.toString());
		    	
		    	System.out.println(cita.toString());
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

		return cita;
	}


	public boolean borrar(Integer identifier) {
		RecordStore rs=datasource.getRecordStore();
		String arr[];
		int dt,dy,mt,hr,yr;
		BeanCita cita;
		Calendar cal;
		 try{
	            
	            for(int i =1; i <= rs.getNumRecords();i++){
	            	String aux = getRecord(i,dataStorage);
	            	arr=Utils.split(aux,"-");
                    int id=new Integer(Integer.parseInt(arr[0])).intValue();
                    String asunto=arr[1];
                    Date fechaInicio=new Date(Long.parseLong(arr[2]));
                    Date fechaTermino=new Date(Long.parseLong(arr[3]));
                    int nivel=new Integer(Integer.parseInt(arr[4])).intValue();
                                    
                    cita=new BeanCita(id, asunto, fechaInicio, fechaTermino,nivel,0);
	                
	                Integer clv=new Integer(Integer.parseInt(arr[0]));
	                
	                if(clv.equals(identifier)){
	                	rs.deleteRecord(i);
	                	System.out.println("Se borro correctamente el registro");
	                	vec.removeElement(cita);
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
		BeanCita cita;

		 try{

	            for(int i =1; i <= rs.getNumRecords();i++){

	                String aux = getRecord(i,dataStorage);
	                //System.out.println("aux: "+aux);
	                arr=Utils.split(aux,"-");
	                
                    
                    int id=new Integer(Integer.parseInt(arr[0])).intValue();
                    String asunto=arr[1];
                    Date fechaInicio=new Date(Long.parseLong(arr[2]));
                    Date fechaTermino=new Date(Long.parseLong(arr[3]));
                    int nivel=new Integer(Integer.parseInt(arr[4])).intValue();
                                    
                    cita=new BeanCita(id, asunto, fechaInicio, fechaTermino,nivel,0);
                    
                    vec.addElement(cita);
	            }
	        }catch(RecordStoreNotOpenException e){
	            System.out.println(e.getMessage());
	        }
	     return vec;
    }

	public Object cargar(Integer identifier) {
		RecordStore rs=datasource.getRecordStore();
		String arr[];
		BeanCita cita;
		 try{
		    for(int i =1; i <= rs.getNumRecords();i++){
	            	String aux = getRecord(i,dataStorage);
                    
	                arr=Utils.split(aux,"-");
                       
	                int id=new Integer(Integer.parseInt(arr[0])).intValue();
                    String asunto=arr[1];
                    Date fechaInicio=new Date(Long.parseLong(arr[2]));
                    Date fechaTermino=new Date(Long.parseLong(arr[3]));
                    int nivel=new Integer(Integer.parseInt(arr[4])).intValue();
                                    
                    cita=new BeanCita(id, asunto, fechaInicio, fechaTermino,nivel,0);

	                Integer clv=new Integer(Integer.parseInt(arr[0]));
	                if(clv.equals(identifier)){
	                     return cita;
	                }

	            }
	        }catch(Exception e){
	            e.printStackTrace();
	        }
	     return null;

	}
	public void modificar(BeanCita cita){
	     RecordStore rs=datasource.getRecordStore();
	      ByteArrayOutputStream baos=new ByteArrayOutputStream();
	      DataOutputStream dos = new DataOutputStream(baos);
	      cargar(new Integer(cita.getIdCita()));
		 try {
		    	dos.writeUTF(cita.toString());
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
