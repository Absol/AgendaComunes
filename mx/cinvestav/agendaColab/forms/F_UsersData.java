package mx.cinvestav.agendaColab.forms;
import javax.microedition.lcdui.*;
import mx.cinvestav.agendaColab.comun.beans.BeanContacto;
import mx.cinvestav.agendaColab.comun.beans.BeanUsuario;

/**
 *
 * @author eduardogiron
 */
public class F_UsersData extends Form implements CommandListener {
    private CommandListener comm;
    private TextField name,apPaterno,apMaterno,mail,tel;
    private BeanUsuario myUser;
    private BeanContacto contacto;
    public F_UsersData(CommandListener c, String title,BeanUsuario us){
        super(title);
        /*
        this.idContacto=id;
        this.idUsuario = idUsu;
        this.nombre=nombre;
        this.apPaterno=apPaterno;
        this.apMaterno=apMaterno;
        this.email=email;
        this.telefono=telefono;
         */
        myUser= us;
	comm = c;
        name = new TextField("Nombre:    ", "", 12, TextField.ANY);
        apPaterno = new TextField("A. Paterno:", "", 12, TextField.ANY);
        apMaterno = new TextField("A. Materno:", "", 12, TextField.ANY);
        mail = new TextField("Mail","", 25,TextField.ANY);
        tel = new TextField("Phone","", 10, TextField.PHONENUMBER);
        this.append(name);
        this.append(apPaterno);
        this.append(apMaterno);
        this.append(mail);
        this.append(tel);
       
        this.setCommandListener(this);
    }

    public void load(BeanContacto cont)
            {

        name.setString(cont.getnombre());



    }

    public BeanContacto getDatos(){
/*(int id, int idUsu, String nombre, String apPaterno,
            String apMaterno, String email, String telefono)*/
        System.out.println(myUser.getId());
        System.out.println(name.getString());
        System.out.println(apPaterno.getString());
        System.out.println(apMaterno.getString());
        System.out.println(mail.getString());
        System.out.println(tel.getString());
         contacto = new BeanContacto(0,myUser.getId(),
                name.getString(),apPaterno.getString(),apMaterno.getString(),
                mail.getString(),tel.getString());
        
        

        return contacto;
        
    }
    public void commandAction(Command c, Displayable d) {
		comm.commandAction(c, d);
	}

}
