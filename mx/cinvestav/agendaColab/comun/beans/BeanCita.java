package mx.cinvestav.agendaColab.comun.beans;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Date;

/**
 * @author rockderick
 */
public class BeanCita {
public static final int PRIVADA = 0;
public static final int OCUPADO = 1;
public static final int PUBLICA = 2;
    private int idCita;
    private String asunto;
    private Date fechaInicio;
    private Date fechaTermino;
    private int nivel;

    public BeanCita(){
    }

    public BeanCita(int id, String asunto, Date fechaInicio, Date fechaTermino, int nivel){
        this.idCita=id;
        this.asunto=asunto;
        this.fechaInicio=fechaInicio;
        this.fechaTermino=fechaTermino;
        this.nivel = nivel;
    }

    /**
     * @return the idCita
     */
    public int getIdCita() {
        return idCita;
    }

    public int getNivel() {
        return nivel;
    }

    /**
     * @return the nombreCita
     */
    public String getAsunto() {
        return asunto;
    }

    /**
     * @return the fechaInicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @return the fechaTermino
     */
    public Date getFechaTermino() {
        return fechaTermino;
    }

    public void write(DataOutputStream dOutput) {
        try {
            int longitud = 0;
            //1 escribe id
            dOutput.writeInt(idCita);
            if (asunto != null) {
                longitud = asunto.length();
                //2 escribe long de asunto
                dOutput.writeByte(longitud);
                //3 escribe asunto
                dOutput.write(asunto.getBytes());
            } else {
                longitud = 0;
                //2 escribe long de asunto
                dOutput.writeByte(longitud);
            }

            dOutput.writeLong(fechaInicio.getTime());
            dOutput.writeLong(fechaTermino.getTime());
            dOutput.writeInt(nivel);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void read(DataInputStream dInput) {
        try {
            int longitud;
            byte[] bytesVec;

            //1 se lee el id
            idCita = dInput.readInt();
            //2 se lee longitud de asunto
            longitud = dInput.readByte();
            bytesVec = new byte[longitud];
            //3 se lee login
            if (longitud > 0) {
                dInput.read(bytesVec, 0, longitud);
                asunto = new String(bytesVec);
            } else {
                asunto = null;
            }

            fechaInicio = new Date(dInput.readLong());
            fechaTermino = new Date(dInput.readLong());
            nivel = dInput.readInt();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public String toString(){
        return asunto+" "+fechaInicio+"-"+fechaTermino;
    }
}
