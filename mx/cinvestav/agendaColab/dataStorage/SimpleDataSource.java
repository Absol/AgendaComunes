/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.cinvestav.agendaColab.dataStorage;

import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotOpenException;



public class SimpleDataSource {
	private RecordStore rs;
	private static SimpleDataSource INSTANCE;
	private static String dataStorage;

	private SimpleDataSource(String myDataStorage){
		 try{
	           rs=RecordStore.openRecordStore(myDataStorage,true);
	           dataStorage=myDataStorage;
	       }catch(RecordStoreException e){
	           e.printStackTrace();
	       }
	}

	public static SimpleDataSource getInstance(String myDataStorage){
		if (INSTANCE == null){
     	   INSTANCE=new SimpleDataSource(myDataStorage);
           
       }
		else{
			
			if(!myDataStorage.equals(dataStorage)){
				INSTANCE=new SimpleDataSource(myDataStorage);
			}
		}
        return INSTANCE;
	}

	public RecordStore getRecordStore(){
		return rs;
	}

	public  void closeRecordStore(){
	   try {
		rs.closeRecordStore();
	   }catch (RecordStoreNotOpenException e) {
		       e.printStackTrace();
	    }catch (RecordStoreException e) {
				e.printStackTrace();
	    }
	}


}

