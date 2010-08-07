package mx.cinvestav.agendaColab.forms;
import javax.microedition.lcdui.*;
/**
 *
 * @author eduardogiron
 */
public class F_Buscar extends Form implements CommandListener{
    private CommandListener comm;
    private TextField bc;
    public F_Buscar(CommandListener c, String title){
            super(title);
            bc= new TextField("User:    ", "", 12, TextField.ANY);
            this.append(bc);
            comm = c;
            this.setCommandListener(this);
    }

    public void commandAction(Command c, Displayable d) {
		comm.commandAction(c, d);
	}

}
