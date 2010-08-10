package mx.cinvestav.agendaColab.comun.beans;

import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * @author rockderick
 */
public class BeanContacto {
    private int idContacto;
    private String nombre;
    private String apPaterno;
    private String apMaterno;
    private String email;
    private String telefono;
    private int idUsuario;

    public BeanContacto(){}

    public BeanContacto(int id, int idUsu, String nombre, String apPaterno,
            String apMaterno, String email, String telefono){
        this.idContacto=id;
        this.idUsuario = idUsu;
        this.nombre=nombre;
        this.apPaterno=apPaterno;
        this.apMaterno=apMaterno;
        this.email=email;
        this.telefono=telefono;

    }

    public int getIdUsuario() {
        return idUsuario;
    }

    /**
     * @return the idContacto
     */
    public int getidContacto() {
        return idContacto;
    }

    /**
     * @return the nombreCita
     */
    public String getnombre() {
        return nombre;
    }

    /**
     * @return the apPaterno
     */
    public String getapPaterno() {
        return apPaterno;
    }

    /**
     * @return the apMaterno
     */
    public String getapMaterno() {
        return apMaterno;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void write(DataOutputStream dOutput) {
        try {
            int longitud = 0;
            //escribe idContacto
            dOutput.writeInt(idContacto);
            //escribe idUsuario
            dOutput.writeInt(idUsuario);
            if (nombre != null) {
                longitud = nombre.length();
                //2 escribe long de nombre
                dOutput.writeByte(longitud);
                //3 escribe nombre
                dOutput.write(nombre.getBytes());
            } else {
                longitud = 0;
                //2 escribe long de nombre
                dOutput.writeByte(longitud);
            }
            if (apPaterno != null) {
                longitud = apPaterno.length();
                //4 escribe long de fecha de Inicio
                dOutput.writeByte(longitud);
                //5 se escribe fecha de Inicio;
                dOutput.write(apPaterno.getBytes());
            } else {
                longitud = 0;
                //4 escribe long de Fecha de Inicio
                dOutput.writeByte(longitud);
            }
            if (apMaterno != null) {
                longitud = apMaterno.length();
                //4 escribe long de fecha de Termino
                dOutput.writeByte(longitud);
                //5 se escribe fecha de Fecha de Termino;
                dOutput.write(apMaterno.getBytes());
            } else {
                longitud = 0;
                //4 escribe long de Fecha de Termino
                dOutput.writeByte(longitud);
            }
            if (email != null) {
                longitud = email.length();
                //4 escribe long de fecha de Termino
                dOutput.writeByte(longitud);
                //5 se escribe fecha de Fecha de Termino;
                dOutput.write(email.getBytes());
            } else {
                longitud = 0;
                //4 escribe long de Fecha de Termino
                dOutput.writeByte(longitud);
            }
            if (telefono != null) {
                longitud = telefono.length();
                //4 escribe long de fecha de Termino
                dOutput.writeByte(longitud);
                //5 se escribe fecha de Fecha de Termino;
                dOutput.write(telefono.getBytes());
            } else {
                longitud = 0;
                //4 escribe long de Fecha de Termino
                dOutput.writeByte(longitud);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void read(DataInputStream dInput) {
        try {
            int longitud;
            byte[] bytesVec;

            //se lee el id
            idContacto = dInput.readInt();
            //se lee el idUsuario
            idUsuario = dInput.readInt();
            //2 se lee longitud de nombre
            longitud = dInput.readByte();
            bytesVec = new byte[longitud];
            //3 se lee login
            if (longitud > 0) {
                dInput.read(bytesVec, 0, longitud);
                nombre = new String(bytesVec);
            } else {
                nombre = null;
            }
            //4 se lee long de apPaterno;
            longitud = dInput.readByte();
            bytesVec = new byte[longitud];
            //5 se lee pass;
            if (longitud > 0) {
                dInput.read(bytesVec, 0, longitud);
                apPaterno = new String(bytesVec);
            } else {
                apPaterno = null;
            }
            //4 se lee long de apPaterno;
            longitud = dInput.readByte();
            bytesVec = new byte[longitud];
            //5 se lee pass;
            if (longitud > 0) {
                dInput.read(bytesVec, 0, longitud);
                apMaterno = new String(bytesVec);
            } else {
                apMaterno = null;
            }
            //4 se lee long de apPaterno;
            longitud = dInput.readByte();
            bytesVec = new byte[longitud];
            //5 se lee pass;
            if (longitud > 0) {
                dInput.read(bytesVec, 0, longitud);
                email = new String(bytesVec);
            } else {
                email = null;
            }
            //4 se lee long de apPaterno;
            longitud = dInput.readByte();
            bytesVec = new byte[longitud];
            //5 se lee pass;
            if (longitud > 0) {
                dInput.read(bytesVec, 0, longitud);
                telefono = new String(bytesVec);
            } else {
                telefono = null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String toString(){
        return idContacto+" "+nombre+" "+apPaterno+" "+apMaterno+" "+email+" "+telefono;
    }
}
