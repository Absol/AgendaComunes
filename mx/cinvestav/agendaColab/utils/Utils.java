/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.cinvestav.agendaColab.utils;

/**
 *
 * @author rockderick
 */
import java.util.Vector;

public class Utils {


	public static String[] split(String p_text, String p_seperator)
	{
	    Vector vecStrings = new Vector();

	    int index;
	    int prevIdx = 0;

	    while ((index = p_text.indexOf(p_seperator, prevIdx)) > -1)
	    {
	      vecStrings.addElement(p_text.substring(prevIdx, index));
	      prevIdx = index + 1;
	    }
	    vecStrings.addElement(p_text.substring(prevIdx));

	    String[] result = new String[vecStrings.size()];
	    vecStrings.copyInto(result);

	    return result;
	}

}
