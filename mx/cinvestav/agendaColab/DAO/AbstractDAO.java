/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.cinvestav.agendaColab.DAO;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Vector;
import mx.cinvestav.agendaColab.dataStorage.SimpleDataSource;
import javax.microedition.rms.InvalidRecordIDException;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotOpenException;



/**
 *
 * @author rockderick
 */
public abstract class AbstractDAO implements GenericDAO {
	//protected Tarea2 objMIDlet;
	protected SimpleDataSource datasource;
	protected Vector vec=new Vector();

    public AbstractDAO(){
        //objMIDlet = MIDlet;

    }

    public String getRecord(int id,String dataStorage){
        String toReturn = "";
        datasource=SimpleDataSource.getInstance(dataStorage);
	RecordStore rs=datasource.getRecordStore();
        try{
            int tamRecord = rs.getRecordSize(id);

            byte[] dados = new byte[tamRecord];

            ByteArrayInputStream bais = new ByteArrayInputStream(dados);

            DataInputStream dis = new DataInputStream(bais);

            rs.getRecord(id, dados, 0);

            toReturn = dis.readUTF();

            bais.close();
            dis.close();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println(e.getMessage());
        }catch(InvalidRecordIDException e){
            System.out.println(e.getMessage());
        }catch(RecordStoreNotOpenException e){
            System.out.println(e.getMessage());
        }catch(RecordStoreException e){
            System.out.println(e.getMessage());
        }
        return toReturn;
    }


}

