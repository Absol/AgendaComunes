/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.cinvestav.agendaColab.DAO;

/**
 *
 * @author rockderick
 */
import java.util.Vector;



public interface GenericDataDAO {
	 public Object create(Object obj);
         public Object cargar(Integer id);
	 public Vector getLista();


}
