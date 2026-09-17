/*
 * positionBySOM.java
 *
 * Created on 12 de febrero de 2007, 16:05
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package bansy21.Algoritmos;


import jSOM.*;
import bansy21.Red.*;

/**
 *
 * @author jjimenez
 */
public class positionBySOM {
    
    private SOMNet somNet=null;
    private SOMTrainer somTrainer=null;
    private Red bNet=null;
    public double[][] data=null;
     int numVar;
    
    /** Creates a new instance of positionBySOM */
    public positionBySOM(Red bNet_) {
        bNet=bNet_;
        numVar=bNet_.numVariables;
    }
    
    public void calcPositionBySOM()
    {
       
       
        Dijkstra d=new Dijkstra(bNet, numVar);
        data=d.getAllDijkstraDistances();
        changeMaxValues();           
        somNet=new SOMNet(data.length,(data[0].length*2));
        somTrainer=new SOMTrainer(somNet,data);
        somNet.somParameters.alfaTimeToChange=numVar;
        somNet.somParameters.sigmaTimeToChange=numVar;
        somNet.somParameters.MaxIter=data.length*50;
        somTrainer.preparTraning(data.length*50);
        somTrainer.train();
        
        
        setNewPositions();
    }
    
    private void changeMaxValues()
    {
        double max=0;
        for(int i=0;i<data.length;i++)
        {
            for(int j=0;j<data[i].length;j++)
            {
                if(data[i][j]!=10000 && data[i][j]>max)
                    max=data[i][j];
            }        
        }
        for(int i=0;i<data.length;i++)
        {
            for(int j=0;j<data[i].length;j++)
            {
                if(data[i][j]==10000)
                    data[i][j]=max+1;
            }        
        }
        
    }
    private void setNewPositions()
    {
      
      for(int i=0;i<somNet.neurons.length;i++)
      {
        for(int j=0;j<somNet.neurons[i].length;j++)
        {
            for (int k=0;k<somNet.neurons[i][j].Asociatedfeatures.size();k++)
            {
                double indexOfNode=new Double(somNet.neurons[i][j].Asociatedfeatures.elementAt(k).toString());
                setNewpositioToNode(indexOfNode,somNet.neurons[i][j]);
            }
        }
      }
      
    }
    
    private void setNewpositioToNode(double indexOfNode,SOMNeuron somNeuron)
    {
       int x0=50;
		int y0=50;
        Node temp=bNet.inicio;
        int i=0;
        while(temp!=null)
        {
            if(i==indexOfNode)
            {
                temp.x=x0+somNeuron.ja*20;
                temp.y=y0+somNeuron.ia*20;
                break;
            }
            i++;
            temp=temp.sig;
        }
    }
    
}
