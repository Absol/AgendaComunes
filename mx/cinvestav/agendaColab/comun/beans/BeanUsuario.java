package mx.cinvestav.agendaColab.comun.beans;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class BeanUsuario {

    private String login;
    private int id;
    private String pass;

    public BeanUsuario() {
        id = 0;
        login = null;
        pass = null;
    }

    public BeanUsuario(int id, String logi, String pas) {
        this.id = id;
        login = logi;
        pass = pas;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPass() {
        return pass;
    }

    public String toString()
    {
        return "(" + id + ", " + login + ")";
    }

    public void read(DataInputStream dInput) {
        try {
            int longitud;
            byte[] bytesVec;

            //1 se lee el id
            id = dInput.readInt();
            //2 se lee longitud de login
            longitud = dInput.readByte();
            bytesVec = new byte[longitud];
            //3 se lee login
            if (longitud > 0) {
                dInput.read(bytesVec, 0, longitud);
                login = new String(bytesVec);
            } else {
                login = null;
            }
            //4 se lee long de pass;
            longitud = dInput.readByte();
            bytesVec = new byte[longitud];
            //5 se lee pass;
            if (longitud > 0) {
                dInput.read(bytesVec, 0, longitud);
                pass = new String(bytesVec);
            } else {
                pass = null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void write(DataOutputStream dOutput) {
        try {
            int longitud = 0;
            //1 escribe id
            dOutput.writeInt(id);
            if (login != null) {
                longitud = login.length();
                //2 escribe long de login
                dOutput.writeByte(longitud);
                //3 escribe login
                dOutput.write(login.getBytes());
            } else {
                longitud = 0;
                //2 escribe long de login
                dOutput.writeByte(longitud);
            }
            if (pass != null) {
                longitud = pass.length();
                //4 escribe long de pass
                dOutput.writeByte(longitud);
                //5 se escribe pass;
                dOutput.write(pass.getBytes());
            } else {
                longitud = 0;
                //4 escribe long de pass
                dOutput.writeByte(longitud);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }
}
