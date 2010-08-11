package mx.cinvestav.agendaColab.forms;
import javax.microedition.lcdui.*;
/**
 *
 * @author eduardogiron
 */
public class F_Menu extends List implements CommandListener{
        private CommandListener comm;

        public F_Menu(CommandListener c){
            super("Menu",Choice.IMPLICIT);
            this.append("Alta Contacto",null);
            this.append("Buscar Contacto",null);
            this.append("Sincronizar",null);
            this.append("Ver Citas Grupo",null);
            this.append("Agenda Personal",null);
            comm = c;
            this.setCommandListener(this);
        }

        public void commandAction(Command c, Displayable d) {
		comm.commandAction(c, d);
	}
}
