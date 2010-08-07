package mx.cinvestav.agendaColab.forms;
import javax.microedition.lcdui.*;

/**
 *
 * @author eduardogiron
 */
public class F_Citas extends List implements CommandListener{
    private CommandListener comm;

    public F_Citas(CommandListener c,String title){
            super(title,Choice.IMPLICIT);
            //Añadir citas
            //this.append("Alta Contacto",null);
            comm = c;
            this.setCommandListener(this);

    }
    public void load_personal(){
        this.append("28/07/10 13:30-14:30 Comida", null);
        this.append("28/07/10 14:30-16:30 Ocupado", null);
        this.append("29/07/10 11:00-12:00 Reunion", null);
        this.append("29/07/10 14:30-16:30 Inventario", null);
    }
    public void load_contac(String contact){
        
    }
    public void commandAction(Command c, Displayable d) {
		comm.commandAction(c, d);
	}

}
