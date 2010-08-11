package mx.cinvestav.agendaColab.comun;

import java.io.DataOutputStream;
import java.io.DataInputStream;
import mx.cinvestav.agendaColab.comun.beans.BeanUsuario;

public class PullRequest extends Evento {

    public static final int miTipo = 11;
    private BeanUsuario usuario;

    public BeanUsuario getUsuario() {
        return usuario;
    }

    public PullRequest() {
        usuario = null;
    }

    public PullRequest(BeanUsuario usu) {
        usuario = usu;
    }

    public void read(DataInputStream dInput) {
        usuario = new BeanUsuario();
        usuario.read(dInput);
    }

    public void write(DataOutputStream dOutput) {
        usuario.write(dOutput);
    }

    public int getMiTipo() {
        return miTipo;
    }

    public String toString()
    {
        return "PullReques(" + usuario + ")";
    }

}
