package mx.cinvestav.agendaColab;

import javax.microedition.midlet.*;
import mx.cinvestav.agendaColab.FlujoController;

/**
 * @author eduardogiron
 */
public class Agenda_Colaborativa extends MIDlet {
    public void startApp() {
        new FlujoController(this);
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }
}
