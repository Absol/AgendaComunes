package mx.cinvestav.agendaColab.forms;
import javax.microedition.lcdui.*;

/**
 *
 * @author eduardogiron
 */
public class F_UsersData extends Form implements CommandListener {
    private CommandListener comm;
    private TextField name,address,mail,tel;
    public F_UsersData(CommandListener c, String title){
        super(title);
	comm = c;
        //A_C = new Form ("Alta Contacto");
        name = new TextField("User:    ", "", 12, TextField.ANY);
        address = new TextField("Adress:", "", 20, TextField.ANY);
        mail = new TextField("Mail","", 25,TextField.EMAILADDR);
        tel = new TextField("Phone","", 10, TextField.PHONENUMBER);
       // ma = new Command("Guardar",Command.OK,2);
        //ma2 = new Command("Back",Command.OK,1);
        //name.setString("EDW");
        this.append(name);
        this.append(address);
        this.append(mail);
        this.append(tel);
        //this.addCommand(ma);
        //this.addCommand(ma2);
        this.setCommandListener(this);
        this.setCommandListener(this);
    }
    public void commandAction(Command c, Displayable d) {
		comm.commandAction(c, d);
	}

}
