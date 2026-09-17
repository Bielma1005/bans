package bansy21.Inferencia;

import bansy21.Red.*;
import bansy21.Utilerias.*;

public class NaiveInference 
{
	//esta es la clase
	public Node varDep=null;
	public NaiveInference()
	{
	}
	/**
	*devuelve el valor de la probabilidad obtenida,ademas es normalizada
	*Esta probabilidad representa la probabilidad de la clase dadas las variables
	*en la red dad se deben tener seleccionados todos los valos
	*en las varibales
	*/
	public double getProbability(Red red)
	{		
		Node temp=red.inicio;
		double[] probabilities=new double[varDep.values.size()];
		for (int i=0;i<probabilities.length;i++)
		{
			probabilities[i]=1;
		}
		if(varDep!=null)
		{			
			do
			{
				for (int i=0;i<probabilities.length;i++)
				{
					if(temp.equals(varDep))
					{						
						probabilities[i]=probabilities[i]*temp.table[0][temp.selectedValue].doubleValue();
					}
					else
						probabilities[i]=probabilities[i]*temp.table[i][temp.selectedValue].doubleValue();
				}
				temp=temp.sig;
			}
			while (temp!=null);
		}
		else
		{
			//send a message of error "initialize tha var class before"
			return -1;
		}
		return (probabilities[varDep.selectedValue]/getSuma(probabilities));
	}
	
	public double[] getProbabilities(Red red)
	{		
		Node temp=red.inicio;
		double[] probabilities=new double[varDep.values.size()];
		for (int i=0;i<probabilities.length;i++)
		{
			probabilities[i]=1;
		}
		if(varDep!=null)
		{			
			do
			{
				for (int i=0;i<probabilities.length;i++)
				{
					if(temp.equals(varDep))
					{						
						probabilities[i]=probabilities[i]*temp.table[0][temp.selectedValue].doubleValue();
					}
					else
						probabilities[i]=probabilities[i]*temp.table[i][temp.selectedValue].doubleValue();
				}
				temp=temp.sig;
			}
			while (temp!=null);
		}
		else
		{
			//send a message of error "initialize tha var class before"
			probabilities[0]=-1;
			return probabilities;
		}
		return probabilities;
	}
	/**
	*Devuelve la suma de los elementos del arreglo
	*/
	private double getSuma(double[] temp)
	{
		double suma=0;
		for (int i=0;i<temp.length;i++)
		{
			suma+=temp[i];
		}
		return suma;
	}
}