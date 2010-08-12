/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.cinvestav.agendaColab.forms;
import javax.microedition.lcdui.*;
import mx.cinvestav.agendaColab.comun.beans.BeanUsuario;
/**
 *
 * @author eduardogiron
 */
public class F_User extends Form implements CommandListener{
    private CommandListener comm;
    private TextField id,user,pass;
    private BeanUsuario usuario;
    public F_User(CommandListener c){
            super("System Logging");
            id= new TextField("ID:    ", "", 5, TextField.NUMERIC);
            user= new TextField("User:    ", "", 12, TextField.ANY);
            pass= new TextField("Pass:    ", "", 12, TextField.PASSWORD);
            this.append(id);
            this.append(user);
            this.append(pass);
            comm = c;
            this.setCommandListener(this);
    }
    public BeanUsuario get_data(){
        usuario = new BeanUsuario(Integer.parseInt(id.getString()),user.getString(),pass.getString());
        return usuario;
    }

    public void commandAction(Command c, Displayable d) {
		comm.commandAction(c, d);
	}

}
