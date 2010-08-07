package mx.cinvestav.agendaColab;
import javax.microedition.lcdui.*;
import mx.cinvestav.agendaColab.forms.*;


/**
 *
 * @author eduardogiron
 */
public class FlujoController implements CommandListener {
    public Display display;
    Agenda_Colaborativa applic;
    F_Menu menu;
    F_UsersData F_Alta, F_Info;
    F_Contacts F_Lista,F_Lista_Conect;
    F_Buscar F_B_Contacts;
    F_Agenda f_agenda;
    F_Citas f_cita;

    //Comandos Menu Principal
    private Command exit = new Command("Salir", Command.EXIT, 1);
    private Command aceptar = new Command("Aceptar", Command.OK, 1);
    //Comandos Comunes
    private Command back= new Command("Back",Command.BACK,1);
    //Comandos Alta Contacto
    private Command guardar = new Command("Save",Command.OK,1);
    //Comandos Buscar Contacto
    private Command ok = new Command("Selected",Command.OK,1);
    private Command buscar = new Command("Buscar",Command.OK,1);
    //Comandos Sincronizar Contactos
    private Command new_sinc = new Command("Nueva Sincronización",Command.OK,1);
    private Command des_sinc = new Command("Desincronizar",Command.OK,1);
    //Comandos Ver citas Contactos
    private Command ok_c = new Command("Aceptar",Command.OK,1);
    //Comandos Agenda 
    private Command ok_a = new Command("Aceptar",Command.OK,1);

    //Comandos
    public FlujoController(Agenda_Colaborativa app) {
                applic = app;
		display = Display.getDisplay(app);
		menu = new F_Menu(this);
		menu.addCommand(exit);
                menu.addCommand(aceptar);
		display.setCurrent(menu);

    }

    private void cambioFroma(Command c) {
        //Funciones del menu principal
        if (c == aceptar){
            //Alta contacto
             if (menu.isSelected(0)){
                 F_Alta= new F_UsersData(this, "Alta Contacto");
                 F_Alta.addCommand(back);
                 F_Alta.addCommand(guardar);
                 display.setCurrent(F_Alta);
             }
             //Buscar Contacto
             if (menu.isSelected(1)){
                 F_Lista= new F_Contacts(this,"Contacts List");
                 F_Lista.load_contacts();
                 F_Lista.addCommand(back);
                 F_Lista.addCommand(ok);
                 F_Lista.addCommand(buscar);
                 display.setCurrent(F_Lista);
             }
             //Sincronizar
             if (menu.isSelected(2)){
                 F_Lista_Conect = new F_Contacts(this, "Connection Contacts");
                 F_Lista_Conect.load_conections();
                 F_Lista_Conect.addCommand(back);
                 F_Lista_Conect.addCommand(new_sinc);
                 F_Lista_Conect.addCommand(des_sinc);
                 display.setCurrent(F_Lista_Conect);
             }
             //Ver Citas Grupo
             if (menu.isSelected(3)){
                 F_B_Contacts = new F_Buscar(this,"Buscar Contacto");
                 F_B_Contacts.addCommand(back);
                 F_B_Contacts.addCommand(ok_c);
                 display.setCurrent(F_B_Contacts);

             }
             //Agenda personal
             if (menu.isSelected(4)){
                 f_agenda = new F_Agenda(this,"Personal Agenda");
                 f_agenda.addCommand(back);
                 f_agenda.addCommand(ok_a);
                 display.setCurrent(f_agenda);
             }

            }
        // Común
        if(c==back){
            display.setCurrent(menu);
        }
        //Alta contacto
        if(c==guardar){
            //Agregar función de almacenamiento
            display.setCurrent(menu);
        }
        //Buscar Contacto
        if(c==ok){
            String name=F_Lista.getString(F_Lista.getSelectedIndex());
            //Agregar función de busqueda de contacto

        }
        if(c==buscar){

        }
        //Sincronizar
        if(c==new_sinc){

        }
        if(c==des_sinc){

        }
        //Ver Citas Grupo
        if(c==ok_c){

        }
        //Agenda Personal
        if(c==ok_a){
            
        }
    }


    public void commandAction(Command c, Displayable d) {
		if (c == exit){
                    this.applic.destroyApp(false);
                    this.applic.notifyDestroyed();
		}
		else
			cambioFroma(c);
	}



}
