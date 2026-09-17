/*
 * bansyProyEcoder.java
 *
 * Created on 11 de octubre de 2006, 19:30
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package bansy21.Utilerias;

import bansy21.Red.*;
import bansy21.Gui.*;
import bansy21.Inferencia.*;
import java.io.Serializable;
import java.util.*;
import javax.swing.JInternalFrame;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent; 
import java.beans.PropertyChangeEvent;
import javax.swing.JTextArea;


/**
 *
 * @author jjimenez
 */
public class bansyProyEcoder implements Serializable, java.beans.PropertyChangeListener
{  
        //lista de nodos
        public Node inicio=null;
	//lista de arcos
        public Arco inArco=null;
	//registros
	public Vector casos;
	//variable dependiente en las pruebas
	public Node varDep;
	public Node varDep2;
	public String baseName=null;

	public double alfa=0.05;	 // probabilidad de no rechazar la hipotesis nula
	public double alfaG=0.05; // error global
	public int profundidad=1; // profundidad = numero de variables con las que se condiciona para quitar arcos
      
        public double minPorcGan=5; // porcentaje de ganacia de informacion

	public int numVariables = 0; // Numero de variables involucradas
      
        public int Casos        = 0; // Numero d casos
        public int Decimales    = 4; // Numero de decimales, para formateo de numeros reales.
	public String PatronDecimales = "#0.0000";

      
        //Propiedades importantes de los paneles
        public Red[] netsOfPanels=null;
        public String[] namesPanels=null;
        
        public String[] namesMonitors=null;
        public JTextArea[] outPutMonitors=null;
    
    
    /** Creates a new instance of bansyProyEcoder */
    public bansyProyEcoder() 
    {
      
    }
    
   public void propertyChange(PropertyChangeEvent e)
    {
        System.out.println("cambios");
    }
    
}
