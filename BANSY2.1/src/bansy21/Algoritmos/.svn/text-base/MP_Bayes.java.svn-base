/*
 * MP_Bayes.java
 *
 * Created on 25 de junio de 2007, 07:18 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package bansy21.Algoritmos;

import java.util.Vector;
import bansy21.Utilerias.*;
import bansy21.Red.*;
import bansy21.Gui.Algorithm;
import bansy21.*;
import java.util.Calendar;
/**
 *
 * @author jjimenez
 */
public class MP_Bayes extends Algorithm{
    public double alfa;
    public boolean simple=true;
    //Conjunto de arcos
    Vector W=new Vector();
    
    /** Creates a new instance of MP_Bayes */
    public MP_Bayes() {
    }
    
    public void run()
	{
		setWaitCursor();		
		Calendar date1=Calendar.getInstance();
		
                MP(inicio);
                
		Calendar date2=Calendar.getInstance();													
		println("Tiempo empleado: "+util.tiempoEmpleado(date1,date2));
		parent.agregarRed(red,baseName+"_MP_Bayes");
		setNote("Listo");
		setDefaultCursor();
                
	}


	public Red MP(Node in)
	{            
            //Se empieza con una red totalmente conectada
            createRedConectadaNodirigida(in);
            //Obtener el conjunto de nodos en un arreglo            
            Node[] nodos=red.getArray();
            //Ejecutar la primera etapa. Se verifican idependnecia marginal entre par de varibales
           primeraEtapa(nodos);
            
            return red;
        }
        
        private void primeraEtapa(Node[] nodos)
        {
               //Calcular la matriz de informacion mutua. Todos contra todos
             double[][] matrizI=getMatrizInfMarginal(nodos);
             for(int i=0;i<matrizI.length;i++)
            {
                 for(int j=(i+1);j<matrizI[i].length;j++)
                 {
                    //Probar independencia marginal
                     double T=2*(casos)*matrizI[i][j];
                     int df=(nodos[i].values.size()-1)*(nodos[j].values.size()-1);
                     double chiAlfa=util.chi(alfa,df);
                     if(!(T<chiAlfa))
	   	     {
                         Node[] tempNs={nodos[i], nodos[j]};
                         W.add(tempNs);
                     }
                     else//Quitar arco
                     {
                        util.removeNodirectedArc(nodos[i], nodos[j]);
                     }
                 }
             }
        }
        
        private double[][] getMatrizInfMarginal(Node[] nodos)
        {
            double[][] temp=new double[nodos.length][nodos.length];
            for(int i=0;i<temp.length;i++)
            {
                 for(int j=(i+1);j<temp[i].length;j++)
                 {
                    pro.varDep=nodos[i];
                    temp[i][j]=pro.miF(nodos[j]);
                 }
            }
            
            return temp;
        }
        
        private void segundaEtapa()
        {
          //Tomar todos los elementos de W y verificar independneica condicional
          while(W.size()>0)
          {
            //Tomar el primer elemento de W
            Node[] parNodos=(Node[])W.elementAt(0);
            
            
            
            //Eliminar el par de elemntos de W
            W.remove(parNodos);
          }
        }
          
        private boolean sonCondicionalmenteIndpendientesDadoPowAdy(Node[] parNodos)
        {
          Node A=parNodos[0];
          Node B=parNodos[1];
           if(simple)
           {
            //Ady = Conjunto de adyacencia de A menos B

            //PowAdy = Conjunto potencia del conjunto de Adyacenica de A

            //Probar independencia condicional de A con B dado cada elemento de PowAdy
           }
           else//Formar conjunto potencia de B
           {
            
           }
          return true;
        } 
        
        private Vector conjuntoAdjacenciaMenosNod(Node nod, Node menosNod)
        {
          //Recorrer los arcos para sacar el conjunto potencia del nodo dado
          Arco temp=red.inArco;
          Vector resultado=new Vector();
          while(temp!=null)
          {
            if(temp.from.equals(nod) && !temp.to.equals(menosNod))
              resultado.add(temp.to);
            else if(temp.to.equals(nod)&& !temp.from.equals(menosNod))
              resultado.add(temp.from);
            temp=temp.sig;
          }
          return resultado;
        }
        
        private Vector conjuntoPotencia(Vector nodos)
        {
          Vector resultado =  new Vector();
          //voy
                  
          
          return resultado;
        }
        
        
        
    
}
