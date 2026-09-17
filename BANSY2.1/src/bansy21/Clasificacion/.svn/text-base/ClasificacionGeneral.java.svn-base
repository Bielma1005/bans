package bansy21.Clasificacion;

import java.util.Vector;
import bansy21.Red.*;
import bansy21.Inferencia.NaiveInference;
import bansy21.Inferencia.InferenciaGeneral;
import bansy21.Gui.OutPut;
import bansy21.Utilerias.*;

public class ClasificacionGeneral
{
		//red bayesiana
	private Red red;
	//clase o lo que llamamos variable dependiente
	private Node varDep;	
	//ventana de mensajes
	public OutPut sal=null;
	public JointProbability jp=null;

	public ClasificacionGeneral(Red red_)
	{
		red=red_;
		varDep=red.varDep;
	}
	/**
	*Calcula la probabilidad para cada registro, supone que los arcos llegan a la variable
	*dependiente o clase, es decir, que la red fue genrada con alguno de los algortimos de la
	*famile bayes
	*/
	public double makeClasification(Vector cases)
	{
		/*solo hay que ver que valores toman sus padres, y el valor que toma la clase, de alli 
		*buscar el valor de laprobabildiad en la tabla de probabilidades del nodo
		*/	
		if(varDep.parents.size()>0)
		{
			int size=cases.size();
			Object[] values=new Object[varDep.parents.size()];//valores que toman los padres
			Node[] nodos=new Node[values.length];
			varDep.parents.copyInto(nodos);
			//posisiones en la tabla de probabilidades de la variable dependiente, de donde
			//se obtendran ñlas p´robabilidades
			int con=0,k=0;
			//contador de las instancias clasificadas correctamente
			double pro=0;
			for (int i=0;i<size;i++)
			{
				Object[] instance=(Object[])cases.elementAt(i);
				String value=instance[varDep.column].toString();
				//obtener los valores que tomaron los padres
				for (int p=0;p<varDep.parents.size();p++)
				{
					values[p]=instance[((Node)varDep.parents.elementAt(p)).column];
				}
				con=getPositionParentsComb(values,nodos);
				k=getPositionVariable(value,varDep);

				if(k!=-1 && con!=-1)
				{
					//System.out.println("pro "+pro+"  tab "+varDep.table[con-1][k].doubleValue()+" k "+k+" value "+value);
					//System.out.println("  tab "+varDep.table[con-1][k].doubleValue());

					//este es el valor de la probabilidad de clasificar el valor de la instancia
					double proba=varDep.table[con][k].doubleValue();

					//veriricar si es la probabilidad mas grande
					boolean isTheBigest=true;
					for (int ki=0;ki<varDep.table[con].length;ki++)
					{
						if (varDep.table[con][ki].doubleValue()>proba)
						{
							isTheBigest=false;
							break;
						}
					}
					//si si es la probabilidad mas grande, entonces  la red clasificao correctamente la instancia
					//hay que aumentar el contador
					if (isTheBigest)
					{
						pro++;
					}
				}
			}
			return (pro/((double)size));
		}
		else
		{
			System.out.println("parents");
			return -1;
		}
	}
	/**
	*Calcula la probabilidad para cada registro, supone que los arcos llegan a la variable
	*dependiente o clase, es decir, que la red fue genrada con alguno de los algortimos de la
	*famile bayes
	*/
	public double makeClasificationChilds(Vector cases)
	{
		InferenciaGeneral infG=new InferenciaGeneral();
		infG.jp=jp;
		
		double pro=0;

		int size=cases.size();
		//clasificar cada uno de los casos
		for (int c=0;c<size;c++)
		{
			String[] registry=(String[])cases.elementAt(c);
			//instanciar cada uno de los nodos con los valores del registro, en caso
			//de la varibale dependiente, se instanciaran cada uno de sus valores para obtener la rpobailidad
			//de cada uno
			Node temp=red.inicio;
			while (temp!=null)
			{
				if (!temp.equals(varDep))
				{				
					temp.selectedValue=getPositionVariable(registry[temp.column],temp);					
					temp.tipoNodo=TipoNodo.EVIDENCIA;
				}
				temp=temp.sig;
			}
			double[] pr=new double[varDep.values.size()];
			varDep.tipoNodo=TipoNodo.CONSULTA;
			//indice del valor que realmente salio
			int k=getPositionVariable(registry[varDep.column],varDep);
			for (int i=0;i<pr.length;i++)
			{
				//calcular la probabilidad para este valor
				varDep.selectedValue=i;
				infG.red=red;
				pr[i]=infG.calculateConditionalProbability();
			}
			int index=getIndexOfTheBiggest(pr);
			if (index==k)
			{
				pro++;
			}
		}
		return pro/size;
	}
	/**
	*Calcula la probabilidad para cada registro, supone que los arcos llegan a la variable
	*dependiente o clase, es decir, que la red fue genrada con alguno de los algortimos de la
	*famile bayes, para el caso de que la varible dependiente no tiene hijos
	*
	*Para el caso de n valores de la varibale dependiente
	*/
	public double[][] makeClasification2(Vector cases)
	{
		/*solo hay que ver que valores toman sus padres, y el valor que toma la clase, de alli 
		*buscar el valor de laprobabildiad en la tabla de probabilidades del nodo
		*/
		double[][] aciertos=new double[varDep.values.size()][varDep.values.size()];//{{0,0},{0,0}};
		for (int i=0;i<aciertos.length;i++)
		{
			for (int j=0;j<aciertos[i].length;j++)
			{
				aciertos[i][j]=0;
			}
		}
		if(varDep.parents.size()>0)
		{
			int size=cases.size();
			Object[] values=new Object[varDep.parents.size()];//valores que toman los padres
			Node[] nodos=new Node[values.length];
			varDep.parents.copyInto(nodos);
			//posisiones en la tabla de probabilidades de la variable dependiente, de donde
			//se obtendran ñlas p´robabilidades
			int con=0,k=0;
			//contador de las instancias clasificadas correctamente		
			for (int i=0;i<size;i++)
			{
				Object[] instance=(Object[])cases.elementAt(i);
				String value=instance[varDep.column].toString();
				//obtener los valores que tomaron los padres
				for (int p=0;p<varDep.parents.size();p++)
				{
					values[p]=instance[((Node)varDep.parents.elementAt(p)).column];
				}
				con=getPositionParentsComb(values,nodos);
				k=getPositionVariable(value,varDep);			

				if(k!=-1 && con!=-1)
				{
					//System.out.println("pro "+pro+"  tab "+varDep.table[con-1][k].doubleValue()+" k "+k+" value "+value);
					//System.out.println("  tab "+varDep.table[con-1][k].doubleValue());

					//este es el valor de la probabilidad de clasificar el valor de la instancia

					int indexB=getIndexOfTheBiggest(con);
					aciertos[k][indexB]++;
				}
			}
			return aciertos;
		}
		else
		{
			System.out.println("parents");
			return aciertos;
		}
	}

	/**
	* Este metodo realiza la clasificación de cada uno de los valoresposibles de la varibale dependiente
	*	utilizando la inferencia general
	*/
	public double[][] makeClasificationWithChilds(Vector cases)
	{
		InferenciaGeneral infG=new InferenciaGeneral();
		infG.jp=jp;
		
		double[][] aciertos=new double[varDep.values.size()][varDep.values.size()];//{{0,0},{0,0}};
		for (int i=0;i<aciertos.length;i++)
		{
			for (int j=0;j<aciertos[i].length;j++)
			{
				aciertos[i][j]=0;
			}
		}
		int size=cases.size();
		//clasificar cada uno de los casos
		for (int c=0;c<size;c++)
		{
			String[] registry=(String[])cases.elementAt(c);
			//instanciar cada uno de los nodos con los valores del registro, en caso
			//de la varibale dependiente, se instanciaran cada uno de sus valores para obtener la rpobailidad
			//de cada uno
			Node temp=red.inicio;
			while (temp!=null)
			{
				if (!temp.equals(varDep))
				{				
					temp.selectedValue=getPositionVariable(registry[temp.column],temp);					
					temp.tipoNodo=TipoNodo.EVIDENCIA;
				}
				temp=temp.sig;
			}
			double[] pr=new double[varDep.values.size()];
			varDep.tipoNodo=TipoNodo.CONSULTA;
			//indice del valor que realmente salio
			int k=getPositionVariable(registry[varDep.column],varDep);
			for (int i=0;i<pr.length;i++)
			{
				//calcular la probabilidad para este valor
				varDep.selectedValue=i;
				infG.red=red;
				pr[i]=infG.calculateConditionalProbability();
			}
			int index=getIndexOfTheBiggest(pr);
			aciertos[k][index]++;
		}
		return aciertos;
	}

	/**
	*devuelve el indice del valor mas grande del arreglo
	*/
	private int getIndexOfTheBiggest(double[] pr)
	{
		double mayor=pr[0];
		int index=0;
		for (int i=0;i<pr.length;i++)
		{
			if (pr[i]>mayor)
			{
				mayor = pr[i];
				index=i;
			}
		}
		return index;
	}

	/**
	*Devuelve el indice de la probabilidad mas grande en el renglon "con" de la tabla de
	*de probabilidades de la varibale dependiente
	*/
	private int getIndexOfTheBiggest(int con)
	{
		double mayor=varDep.table[con][0].doubleValue();
		int index=0;
		for (int k=0;k<varDep.table[con].length;k++)
		{
			if (varDep.table[con][k].doubleValue()>mayor)
			{
				mayor=varDep.table[con][k].doubleValue();
				index=k;
			}
		}
		return index;
	}
	private int getPositionVariable(String value, Node variable)
	{
		int size=variable.values.size();
		for (int i=0;i<size;i++)
		{
			if (value.equals(variable.values.elementAt(i)))
			{
				return i;
			}
		}
		return -1;
	}
	private int getPositionParentsComb(Object[] comb, Node[] nodos)
	{
		//para cada combinación de los padre		
  	//Maquina de combinaciones
		int[] contador=new int[nodos.length];//en el se pondran los valores que van hasta el momento de cada variable			
		for(int i=0;i<contador.length;i++)
			contador[i]=0;			
		int con=0;
		while(contador[0]<nodos[0].values.size())
		{			
			//En el registro actual, solo se prodra cumplir una combinación actual de los padres
			//por lo que si esta es encuontrada, entonces hay que terminar de generar combinaciones
			boolean founded = false;
			Object[] values=new Object[nodos.length];
			for(int i=(nodos.length-1);i>=0;i--)
			{
				if(contador[i]<nodos[i].values.size())
				{
					values[i]=nodos[i].values.elementAt(contador[i]);
					if(i==(nodos.length-1))
						contador[i]++;
				}
				else
				{
					if(i!=0)
					{
						contador[i]=0;
						values[i]=nodos[i].values.elementAt(contador[i]);
						if(i==(nodos.length-1))
							contador[i]++;
						contador[i-1]++;
					}
				}
			}			
			if(values[0]!=null)//aqui ya se tiene la siguiente combinación
			{		
				boolean ya=true;
				for (int i=0;i<nodos.length;i++)
				{
					if (!comb[i].equals(values[i]))
					{
						ya=false;
						break;
					}
				}				
				if(ya)
					return con;
				con++;//lleva la cuenta de los factores
			}
		}
		return -1;
	}

}
