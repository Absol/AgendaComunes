package mx.cinvestav.agendaColab.forms;
import javax.microedition.lcdui.*;
/**
 *
 * @author eduardogiron
 */
public class F_Agenda extends List implements CommandListener{
    private CommandListener comm;
    public F_Agenda(CommandListener c,String title){
        
            super(title,Choice.IMPLICIT);
            
            this.append("Nueva Cita", null);
            this.append("Citas", null);

            comm = c;
            this.setCommandListener(this);

    }
    public void commandAction(Command c, Displayable d) {
		comm.commandAction(c, d);
	}

}
