/*
 * Dijkstra.java
 *
 * Created on 13 de febrero de 2007, 11:08
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package bansy21.Algoritmos;

import java.util.Vector;
import bansy21.Red.*;
        
        
/**
 *
 * @author jjimenez
 */
public class Dijkstra {
    
    NodeDij[] nodesDij;
    
    
    /** Creates a new instance of Dijkstra */
    public Dijkstra(Red bNet, int numVar) {
        nodesDij=new NodeDij[numVar];        
        copyNodesToNodeDij(bNet.inicio);
        copyArcsToArcsDij(bNet.inArco);
      
    }
    
     private void copyNodesToNodeDij(Node firstNode)
    {
        //Agregar los nodos a la lista de nodos dijstra
        int i=0;
        Node tempN=firstNode;
        while(tempN!=null)
        {
            nodesDij[i]=new NodeDij(tempN.name);
            i++;
            tempN=tempN.sig;
        }
    }
    
    private void copyArcsToArcsDij(Arco firstArc)
    {
      Arco tempA=firstArc;
      while(tempA!=null)
      {
          NodeDij tempFrom=null;
          NodeDij tempTo=null;
          //Buscar lso nodos from y to
          for(int i=0;i<nodesDij.length;i++)
          {
            if(tempA.from.name.equals(nodesDij[i].name))
                tempFrom=nodesDij[i];
            if(tempA.to.name.equals(nodesDij[i].name))
                tempTo=nodesDij[i];
          }
          //Agregar el hijo
          tempFrom.childs.add(tempTo);
          tempFrom.childsWeigth.add(tempA.weight);
          tempA=tempA.sig;
      }
    }
    
    /*
     *Devuelve la matriz de distancias dijkstra entre los nodos
     *Los valores de 10000 siginifcan que esos nodos noi estan conexos
     */    
    public double[][] getAllDijkstraDistances()
    {
        double[][] result = new double[nodesDij.length][nodesDij.length];
        for(int i=0;i<nodesDij.length;i++)
            result[i]=getDijkstraDis(nodesDij[i]);
        return result;
    }
    
    public double[] getDijkstraDis(NodeDij source)
    {
        double[] result=new double[nodesDij.length];
        //Poner en cerlo la distancia del node origen y un numero grande en el resto
        resetDistances(source);
        //Agregar todos los nodos a una lista
        Vector listOfNodes=addNodesToList(); 
        //Obtener el nodo con la distancia mas pequeña
        NodeDij tempNpdeD=getMinNodeDijAndEliminate(listOfNodes);
        while(tempNpdeD!=null)
        {
            //Recalcular la distancia de los hijos
            DistancesChilds(tempNpdeD);
            //Remover el nodo con distancia minima
            listOfNodes.remove(tempNpdeD);
            //Obtener el siguiente nodo con distancia minima
            tempNpdeD=getMinNodeDijAndEliminate(listOfNodes);
        }
        //Copiar las distancias minimas resultantes
        for(int i=0;i<result.length;i++)
            result[i]=nodesDij[i].distance;
        return result;
    }
    
    private void DistancesChilds(NodeDij n)
    {        
        int numChilds=n.childs.size();
        if(numChilds>0)
        {
            for(int i=0;i<numChilds;i++)
            {                
                if(((NodeDij)n.childs.elementAt(i)).distance > (n.distance + ((Double)n.childsWeigth.elementAt(i))))
                {
                    //Asignar la nueva distancia
                    ((NodeDij)n.childs.elementAt(i)).distance=(n.distance + ((Double)n.childsWeigth.elementAt(i)));
                    //Asignar el nuevo padre
                    ((NodeDij)n.childs.elementAt(i)).parent=n;                    
                }
            }
        }
        
    }
    
    private NodeDij getMinNodeDijAndEliminate(Vector lisNodes)
    {
        if(lisNodes.size()>0)
        {
            double min=((NodeDij)lisNodes.elementAt(0)).distance;
            NodeDij result=((NodeDij)lisNodes.elementAt(0));
            for (int i=1;i<lisNodes.size();i++)
            {
                if(((NodeDij)lisNodes.elementAt(i)).distance<min)
                {
                    min=((NodeDij)lisNodes.elementAt(i)).distance;
                    result=((NodeDij)lisNodes.elementAt(i));
                }
            }            
            return result;
        }
        else return null;
    }
    
    private Vector addNodesToList()
    {
        Vector result=new Vector();
        for(int i=0;i<nodesDij.length;i++)
            result.add(nodesDij[i]);
        return result;
    }
            
            
            
    private void resetDistances(NodeDij source)
    {
        for(int i=0;i<nodesDij.length;i++)
        {
            if(nodesDij[i].equals(source))
                nodesDij[i].distance=0;
            else
                nodesDij[i].distance=10000;
        }
    }
    
    
    
    
   
}
/*
 *Clase auxiliar para el algoritmo dijstra
 */
class NodeDij{    
    /*
    *   The node Name
    */
	String name="No name";
   NodeDij parent=null;
   Vector childs=null;
   Vector childsWeigth=null;
   double distance=10000;
   public NodeDij(String s)
   {
    name=s;
    childs=new Vector();
    childsWeigth=new Vector();
   }
   
   public boolean equals(NodeDij n)
   {
        return (this.name.equals(n.name));
   }
}

class ArcDij{
    public NodeDij from=null;
    public NodeDij to=null;
    public ArcDij next=null;  
}
        
       
