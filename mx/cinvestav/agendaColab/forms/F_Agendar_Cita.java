package mx.cinvestav.agendaColab.forms;
import javax.microedition.lcdui.*;
import mx.cinvestav.agendaColab.comun.beans.BeanCita;
import mx.cinvestav.agendaColab.comun.beans.BeanUsuario;

/**
 *
 * @author eduardogiron
 */
public class F_Agendar_Cita extends Form implements CommandListener{
    private CommandListener comm;
    private TextField date,f_time,subj,level;
    private DateField s_time;

    public F_Agendar_Cita(CommandListener c,String title){
        super(title);
        comm = c;
        date = new TextField("Date:    ", "", 12, TextField.ANY);
        s_time = new DateField("Start:", DateField.DATE_TIME, null);
        f_time = new TextField("End","", 25,TextField.ANY);
        subj = new TextField("Subject","", 10, TextField.ANY);
        level = new TextField("Level","", 10, TextField.ANY);
        this.append(date);
        this.append(s_time);
        this.append(f_time);
        this.append(subj);
        this.append(level);
        this.setCommandListener(this);

    }

    public BeanCita getDatos(){
        BeanCita cita = new BeanCita(0,
                subj.getString(),
                s_time.getDate(), null, BeanCita.PRIVADA);
        return cita;
    }

    public void show_info(){
            
    }
    public void commandAction(Command c, Displayable d) {
		comm.commandAction(c, d);
	}

}
