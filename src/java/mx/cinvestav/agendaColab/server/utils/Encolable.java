/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.cinvestav.agendaColab.server.utils;

/**
 *
 * @author absol
 */
public class Encolable {
private int id = 0;
private int tipo = 0;

    public int getId() {
        return id;
    }

    public int getTipo() {
        return tipo;
    }

    public Encolable(int idVal, int tipoEvento) {
        id = idVal;
        tipo = tipoEvento;
    }

}
