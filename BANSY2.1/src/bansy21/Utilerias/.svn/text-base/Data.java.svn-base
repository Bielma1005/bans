/*
 * Data.java
 *
 * Created on 5 de junio de 2008, 10:36 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */


package bansy21.Utilerias;
import java.util.Vector;
/**
 *
 * @author jjimenez
 */
public class Data {
    
    /** Has vectors of data */
     private Vector Datos= null;
     
     
    /** Creates a new instance of Data */
    public Data() {
        Datos = new Vector();
    }
    
    /** Returns the i-esimo vector */
    public Object elementAt(int element)
    {
        int vectorElements=0;
        for(int i=0; i < Datos.size();i++)
        {
            int elements = ((Vector)Datos.elementAt(i)).size();
            if(element<elements)
            {
                return ((Vector)Datos.elementAt(i)).elementAt(element);
            }
            else
                element = element - elements;
        }
        return null;
    }
    
    public Vector vectorElement(int element)
    {
        return (Vector)Datos.elementAt(element);
    }
    
        
    
    
    public void addElement(Vector v)
    {
        Datos.addElement(v);
    }
    public int size()
    {
        return Datos.size();
    }
    
}
