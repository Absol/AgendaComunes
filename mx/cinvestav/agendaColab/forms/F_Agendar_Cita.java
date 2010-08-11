package mx.cinvestav.agendaColab.forms;
import javax.microedition.lcdui.*;
import mx.cinvestav.agendaColab.comun.beans.BeanCita;
import mx.cinvestav.agendaColab.comun.beans.BeanUsuario;
import java.util.TimeZone;

/**
 *
 * @author eduardogiron
 */
public class F_Agendar_Cita extends Form implements CommandListener{
    private CommandListener comm;
    private TextField subj;
    private DateField s_time,f_time;
    ChoiceGroup cg;
        /*
        this.idCita=id;
        this.asunto=asunto;
        this.fechaInicio=fechaInicio;
        this.fechaTermino=fechaTermino;
        this.nivel = nivel;
        this.idServidor = idSrv;
         */
    public F_Agendar_Cita(CommandListener c,String title){
        super(title);
        comm = c;
        subj = new TextField("Asunto","", 30, TextField.ANY);
        s_time = new DateField("Start:", DateField.DATE_TIME, TimeZone.getTimeZone("GMT"));
        f_time = new DateField("End", DateField.DATE_TIME, TimeZone.getTimeZone("GMT"));
        cg= new ChoiceGroup("Nivel",Choice.EXCLUSIVE);
        /*
            public static final int PRIVADA = 0;
            public static final int OCUPADO = 1;
            public static final int PUBLICA = 2;
         */
        cg.append("Privada", null);
        cg.append("Ocupado", null);
        cg.append("Publica", null);
        //level = new TextField("Level","", 10, TextField.ANY);
        this.append(subj);
        this.append(s_time);
        this.append(f_time);
        this.append(cg);
        
       // this.append(level);
        this.setCommandListener(this);

    }
    public int tipo_cita(){
        int sel=0,tipo=0;
        sel=cg.getSelectedIndex();
        if(sel==0)
            tipo= BeanCita.PRIVADA;
        if(sel==1)
            tipo= BeanCita.OCUPADO;
        if(sel==2)
            tipo= BeanCita.PUBLICA;
        return tipo;
    }
    public BeanCita getDatos(){
        System.out.println(subj.getString());
        System.out.println(s_time.getDate());
        System.out.println(f_time.getDate());
        System.out.println(tipo_cita());
        
        BeanCita cita = new BeanCita(0,
                subj.getString(),
                s_time.getDate(), f_time.getDate(), tipo_cita(),0);
        return cita;
    }

    public void show_info(){
            
    }
    public void commandAction(Command c, Displayable d) {
		comm.commandAction(c, d);
	}

}
