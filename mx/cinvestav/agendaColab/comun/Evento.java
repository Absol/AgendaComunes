package mx.cinvestav.agendaColab.comun;

import java.io.DataOutputStream;
import java.io.DataInputStream;

public abstract class Evento {
  public static final int miTipo = 0;
  
  public abstract void read(DataInputStream dinput);

  public abstract void write(DataOutputStream doutput);
  
  public abstract int getMiTipo();
}