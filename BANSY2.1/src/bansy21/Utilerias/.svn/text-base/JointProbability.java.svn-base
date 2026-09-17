package bansy21.Utilerias;

import java.util.*;
import java.lang.*;
import bansy21.Gui.Algorithm;
import bansy21.Red.*;

public class JointProbability extends Algorithm
{
	/**
	*Combinaciones de las varibales
	*/
	public String[][] comb=null;
	/**
	*	Tabla de probabilidades
	*/
	public double[] table=null;
	public Vector casos=null;
	public int numVariables=0;
	public Red red;

	public static void main(String[] args) 
	{
		System.out.println("Hello World!");
	}

	public JointProbability(Red red_,Vector v_)
	{
		red=red_;
		casos=v_;
		comb=new String[calculateLength()][red.getNumVariables()];
		table=new double[comb.length];
	}

	/**
	*caulcula la probabilidad condicional del conjunto de variables Jset, dado el conjunto de varibales CSet
	*/
	public double calculateConditionalProbability(Node[] JSet,Node[] CSet)
	{
		Node[] tempS=getSet(JSet,CSet);
		double jp=0;
		double cp=0;
		for (int i=0;i<comb.length;i++)
		{
			boolean flag=true;
			//System.out.println("i "+i);
			for (int j=0;j<tempS.length;j++)
			{
				Node tempN=tempS[j];
				String s = (String)tempN.values.elementAt(tempN.selectedValue);
				if (!(s.equals(comb[i][tempN.column])))
				{
					flag=false;
					break;
				}
			}
			if (flag)
			{
				jp+=table[i];
			}
			flag=true;
			for (int j=0;j<CSet.length;j++)
			{
				Node tempN=CSet[j];
				String s = (String)tempN.values.elementAt(tempN.selectedValue);
				if (!(s.equals(comb[i][tempN.column])))
				{
					flag=false;
					break;
				}
			}
			if (flag)
			{
				cp+=table[i];
			}
			incrementProgress();
		}
		//println("p: "+(jp/cp));
		return jp/cp;
	}
	
	private Node[] getSet(Node[] JSet,Node[] CSet)
	{
		Node[] tempS=new Node[JSet.length+CSet.length];
		int con=0;
		for (int i=0;i<JSet.length;i++)
		{
			tempS[con]=JSet[i];
			con++;
		}		
		for (int i=0;i<CSet.length;i++)
		{
			tempS[con]=CSet[i];
			con++;
		}
		return tempS;
	}
	/**
	*	Genera la tabla de probabilidades a partir de la base de datos
	*/
	public void generate()
	{
		double N=(double)casos.size();
		//para cada uno de los casos
		fillComb();
		for (int c=0;c<N;c++)//de esta forma solo se recorre la base de datos una sola ves
		{
			String[] registry=(String[])casos.elementAt(c);
			for (int i=0;i<comb.length;i++)
			{
				if (Arrays.equals(registry,comb[i]))
				{
					table[i]++;
					break;
				}
			}			
		}
		for (int i=0;i<table.length;i++)
		{
			table[i]=table[i]/N;
		}
	}

	/**
	*	Genera la tabla de probabildiades a partir de la base de datos
	*/
	public void generateFromRed()
	{
		//para cada uno de los casos
		fillComb();
		/*Para cada combinación calcular la probabilidad como el producto de laprobabilidades
		*condicionales codificadas en la tabla
		*/
		for (int c=0;c<comb.length;c++)
		{
			String[] combinacion=comb[c];
			table[c]=1;
			Node temp=red.inicio;
			while (temp!=null)
			{
				if(temp.parents.size()>0)
				{
					//habra tantos factores como combinaciones, es por eso que se usa la maquina de las vegas
					Node[] nodos=new Node[temp.parents.size()];
					temp.parents.copyInto(nodos);
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
							boolean key=true;
							//ver si el en el registro se cumplen los valores actuales de los padres
							for(int j=0;j<nodos.length;j++)
							{
								if(!(combinacion[nodos[j].column].equals(values[j].toString())))
								{
									key=false;
									break;
								}
							}
							if (key)
							{
								String value=combinacion[temp.column];
								int k=getPositionVariable(value,temp);
								table[c]*=temp.table[con][k].doubleValue();
								break;
							}							
							con++;//lleva la cuanta de las combinaciones, en este caso de los padres
						}
					}
				}
				else//el nodo no tiene padres, por lo que solo tendra un factor
				{
					String value=combinacion[temp.column];
					int k=getPositionVariable(value,temp);
					table[c]*=temp.table[0][k].doubleValue();
				}
				temp=temp.sig;
			}
		}		

	}


	private void fillComb()
	{
		Node[] nodos=red.getArray();
		int[] contador=new int[nodos.length];//en el se pondran los valores que
		//van hasta el momento para cada variable
		for(int i=0;i<contador.length;i++)
		{
			contador[i]=0;
			//System.out.println("value "+nodos[i].values.size());
		}
		int cont=0;
		while(contador[0]<nodos[0].values.size())
		{			
			String[] values=new String[nodos.length];
			for(int i=(nodos.length-1);i>=0;i--)
			{
				if(contador[i]<nodos[i].values.size())
				{
					values[i]=(String)nodos[i].values.elementAt(contador[i]);
					if(i==(nodos.length-1))
						contador[i]++;
				}
				else
				{
					if(i!=0)
					{
						contador[i]=0;
						values[i]=(String)nodos[i].values.elementAt(contador[i]);
						if(i==(nodos.length-1))
							contador[i]++;
						contador[i-1]++;
					}
				}
			}			
			if(values[0]!=null) //siguiente combinación
			{			
				comb[cont]=values;
				cont++;
			}
		}
	}	
	/**
	* el total de combonaciones de los padres, que será el tamaño de la tabla
	*/
	private int calculateLength()
	{
		Node temp=red.inicio;
		int size=1;
		do
		{
			size*=temp.values.size();
			temp=temp.sig;
		}
		while (temp!=null);
		return size;
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
}
