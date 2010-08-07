package mx.cinvestav.agendaColab.comun;

import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import mx.cinvestav.agendaColab.comun.beans.BeanUsuario;

public class ActualizacionUsuariosSincronizados extends Evento {

    private int tipoActualizacion;
    private BeanUsuario usuario;
    private int tipoAct;
    public static final int miTipo = 1;
    public static final int NUEVA_SINCRO = 1;
    public static final int BORRA_SINCRO = 2;

    public ActualizacionUsuariosSincronizados()
    {
        usuario = null;
        tipoAct = 0;
    }

    public ActualizacionUsuariosSincronizados(BeanUsuario usu, int tipo)
    {
        usuario = usu;
        tipoAct = tipo;
    }

    public BeanUsuario getUsuario() {
        return usuario;
    }

    public int getTipoAct() {
        return tipoAct;
    }

    public String toString()
    {
        return "(" + tipoAct + ", " + usuario + ")";
    }

    public void read(DataInputStream dInput) {
        try {
            tipoAct = dInput.readInt();
        usuario = new BeanUsuario();
        usuario.read(dInput);
        } catch (Exception ex) {
            tipoAct = 0;
            usuario = null;
        }
    }

    public void write(DataOutputStream dOutput) {
        try {
            dOutput.writeInt(tipoAct);
        usuario.write(dOutput);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

	public int getMiTipo() {
		return miTipo;
	}
}
