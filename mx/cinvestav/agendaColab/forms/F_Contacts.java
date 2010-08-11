package mx.cinvestav.agendaColab.forms;
import javax.microedition.lcdui.*;
/**
 *
 * @author eduardogiron
 */
public class F_Contacts extends List implements CommandListener{
    private CommandListener comm;
    
    public F_Contacts(CommandListener c,String title){
            super(title,Choice.IMPLICIT);
            //Añadir contactos
            //this.append("Alta Contacto",null);
            comm = c;
            this.setCommandListener(this);
    }
    public void load_contacts(){
        this.append("R. Jurado", null);
        this.append("E. Girón", null);
        this.append("G. Saucedo", null);
        this.append("A. Olivares", null);
        this.append("H. Anzures", null);
        this.append("A. Beltran", null);
    }
    public void load_conections(){
        this.append("R. Jurado   ->", null);
        this.append("E. Girón    <-", null);
        this.append("A. Olivares <-", null);
        this.append("A. Beltran  ->", null);
    }
     public void commandAction(Command c, Displayable d) {
		comm.commandAction(c, d);
	}
}
