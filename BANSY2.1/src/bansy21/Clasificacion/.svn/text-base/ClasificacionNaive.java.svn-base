package bansy21.Clasificacion;

import java.util.Vector;
import bansy21.Red.*;
import bansy21.Inferencia.NaiveInference;

/**
* Esta clase realiza las pruebas de clasificación usando el naive bayes
*/
public class ClasificacionNaive 
{
	//red naive
	private Red red;
	//clase o lo que llamamos variable dependiente
	private Node varDep;	
	/**
	*Constructor
	*red = es la red naive con la que se hara la calsificación
	*vardep = es la clase
	*
	*/
	public ClasificacionNaive(Red red_)
	{
		red=red_;
		varDep=red.varDep;
	}
	/**
	*start
	*este metodo trata de clasificar cada uno de lso ejemplos dados en el vector
	*utilizando la inferencia naive, a partir de esta obtiene unaprobabilidad, la cual ira sumandi
	*lo que regresa es el promedio de estas probabilidades, con esto se sabra como estuve la clasi
	*ficación en general
	*cases = son los ejemplos que se tratara de clasificar
	*/
	public double makeClasification(Vector cases)
	{
		if (isNaive())
		{
			double pro=0;
			NaiveInference naiInf=new NaiveInference();
			//poner la variable dependiente
			naiInf.varDep=varDep;
			int size=cases.size();
			for (int i=0;i<size;i++)
			{
				Object[] instance=(Object[])cases.elementAt(i);
				//establecer los valores seleccionados
				setValues(instance);
				double prof=naiInf.getProbability(red);
				pro+=prof;
			}
			return (pro/((double)size));
		}
		else
		{
			return -1;
		}
	}
	/**
	*Recive los casos de ejemplos para calsificar
	*/
	public double makeClasification2(Vector cases)
	{
		/*
		en este metodo se corrige la cuestion de la clasificación
		para ver que valor se esta clasificando, hay que tomar el que tenga mayor probabilidad y ese es el
		que se esta clasificando, si coreesponde al valor en el registro, entonces se clasificó bien, de }
		lo contrario se clasifico mal

		SE debe llevar un conteo de cuantas veces se clasifico bien, para obtener le pporcenta, se divide 
		este numero entre el total de registros probabados

		*/

		if (isNaive())
		{
			double aciertos=0;

			NaiveInference naiInf=new NaiveInference();
			//poner la variable dependiente
			naiInf.varDep=varDep;
			int size=cases.size();
			for (int i=0;i<size;i++)
			{
				Object[] instance=(Object[])cases.elementAt(i);
				//establecer los valores seleccionados
				setValues(instance);
				double[] prof=naiInf.getProbabilities(red);
				if(getIndexOfMayor(prof)==varDep.selectedValue)
					aciertos++;
			}

			return (aciertos/((double)size));
		}
		else
		{
			return -1;
		}
	}

	/**
	*Recive los casos de ejemplos para calsificar
	*/
	public double[][] makeClasification3(Vector cases)
	{
		/*
		en este metodo se corrige la cuestion de la clasificación
		para ver que valor se esta clasificando, hay que tomar el que tenga mayor probabilidad y ese es el
		que se esta clasificando, si coreesponde al valor en el registro, entonces se clasificó bien, de }
		lo contrario se clasifico mal

		SE debe llevar un conteo de cuantas veces se clasifico bien, para obtener le pporcenta, se divide 
		este numero entre el total de registros probabados

		*/
		double[][] aciertos=new double[varDep.values.size()][varDep.values.size()];
		aciertos[0][0]=0.0;
		aciertos[0][1]=0.0;
		aciertos[1][0]=0.0;
		aciertos[1][1]=0.0;
		if (isNaive())
		{
			NaiveInference naiInf=new NaiveInference();
			//poner la variable dependiente
			naiInf.varDep=varDep;
			int size=cases.size();
			for (int i=0;i<size;i++)
			{
				Object[] instance=(Object[])cases.elementAt(i);
				//establecer los valores seleccionados
				setValues(instance);
				double[] prof=naiInf.getProbabilities(red);
				int index=(int)getIndexOfMayor(prof);
				aciertos[(int)varDep.selectedValue][index]++;
		/************
				System.out.println(""+varDep.selectedValue+"  "+index );
				****/
			}

			return aciertos;
		}
		else
		{
			return aciertos;
		}
	}
	
	/**
	*Obtiene el indece del valor mayor del arreglo
	*/
	private int getIndexOfMayor(double[] pro)
	{
		double mayor=pro[0];
		int index=0;
		for (int i=0;i<pro.length;i++)
		{
			if (pro[i]>mayor)
			{
				mayor=pro[i];
				index=i;
			}
		}
		return index;
	}
	/**
	*Toma los valores del registro y los establece como selectedValue en los nodos
	*/
	private void setValues(Object[] instance)
	{
		Node temp=red.inicio;
		do
		{
			String value=(String)instance[temp.column];
			for (int i=0;i<temp.values.size();i++)
			{
				if (value.equals(temp.values.elementAt(i).toString()))
				{
					temp.selectedValue=i;
					break;
				}
			}
			if (temp.selectedValue==(-1))
			{
				System.out.println("var "+temp.name);
				System.out.println("value "+value);
			}
			temp=temp.sig;
		}
		while (temp!=null);
	}
	/**
	*Verifica si la red es una red naive bayes, lo unico que hace es verificar que la clase sea padre
	*en todos los arcos de la red
	*/
	private boolean isNaive()
	{
		boolean reg=true;
		Arco temp = red.inArco;
		do
		{
			if(!temp.from.equals(varDep))
			{
				reg=false;
				temp=null;
			}
			temp=temp.sig;
		}
		while (temp!=null);
		return reg;
	}
}