package bansy21.Metricas;
        
import bansy21.Red.*;
import bansy21.Gui.Algorithm;
import bansy21.Utilerias.*;
import java.util.Vector;
import java.util.Arrays;

/*
*  Clase que obtiene la medida Bayesiana para un red dada
*/
public class BMeasure extends Algorithm
{
	public Red red=null;
	//nodo inicio de la lista auxiliar
	private NodeH inNH=null;
	public Vector casos;
	
	/*
	* Constructor
	*/
	public BMeasure()
	{
		
	}

	public void run()
	{
		double m=getBM();
		println("expsdg"+Math.exp(m));
		println("BM: "+m);
                
	}
	/*
	*Calcula la medida bayesiana para la red dada
	*donde red es la red y casos es la base de datos
	*/
	public double getBM()
	{	
		red.inicio=pro.fillParents(red.inicio,red.inArco);
		//inicializar la lista auliar de nodos
		initNodeListHelper();
		
		double N=(double)casos.size();	
		//para cada uno de los casos
		for (int c=0;c<N;c++)//de esta forma solo se recorre la base de datos una sola ves
		{
			NodeH temp=inNH;
			
			//Para cada nodo calcular sus factores
			while (temp!=null)
			{				
				if(temp.node.parents.size()>0)
				{
					//habra tantos factores como combinaciones, es por eso que se usa la maquina de las vegas
					Node[] nodos=new Node[temp.node.parents.size()];
					temp.node.parents.copyInto(nodos);
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
							//Incrementar los valores de los Nijk en el arreglo del nodo, esto es para cada uno de
							//los valores de la valiable, condicioonado a la combinación actual de los padres
							
							boolean key=true;
							//ver si el en el registro se cumplen los valores actuales de los padres
							for(int j=0;j<nodos.length;j++)
							{
								if(!(((Object[])casos.elementAt(c))[nodos[j].column].toString().equals(values[j].toString())))
								{
									key=false;
									break;
								}
							}
							
							if(key)//si si se cumplen la combinación actual de los padres
							{
								founded=true;
								//entonces incrementar el sumador Nijk
								for (int i=0;i<temp.factores[con].Nijk.length;i++)
								{
									if ((((Object[])casos.elementAt(c))[temp.node.column].toString().equals(temp.node.values.elementAt(i).toString())))
									{
										temp.factores[con].Nijk[i]++;
										break;
									}
								}
							}
							if(founded)
								break;
							con++;//lleva la cuanta de los factores
						}
					}
				}
				else//el nodo no tiene padres, por lo que solo tendra un factor
				{
					for (int i=0;i<temp.factores[0].Nijk.length;i++)
					{
						if ((((Object[])casos.elementAt(c))[temp.node.column].toString().equals(temp.node.values.elementAt(i).toString())))
						{
							temp.factores[0].Nijk[i]++;
							break;
						}
					}
				}
				temp=temp.sig;
			}
			incrementProgress();

		}

		//hasta aqui ya se han hecho todas las sumas, solo falta realizar el producto
		println("normal "+calculaProducto());
		return calculaProducto2();
	}

	private double calculaProducto()
	{
		double producto=1;
		NodeH temp=inNH;
		double pb=1;
		while (temp!=null)
		{
			incrementProgress();
			setNote(temp.node.name);
			producto=producto*getFactor(temp);
			println("Nodo: "+temp.node.name+"  Producto: "+producto);
			temp=temp.sig;
			//hace falta agrgar las P(S)
		}
		println("");
		return producto;
	}

	private double calculaProducto2()
	{
		double producto=0;
		NodeH temp=inNH;
		double pb=0;

		while (temp!=null)
		{
			incrementProgress();
			setNote(temp.node.name);
			double fact=getFactor2(temp);
			producto=producto+fact;
			println("Nodo: "+temp.node.name+"  Producto: "+producto);
			pb++;
			temp=temp.sig;
			//hace falta agrgar las P(S)
		}
	
		return producto;
	}
	private double getFactor(NodeH temp)
	{
		double factor=1;
		//calcula el producto de los factores del nodo
		for (int i=0;i<temp.factores.length;i++)
		{
			if(verificaFactores(temp.factores[i].Nijk))
			{
				//entonces habra factoriales infinitos				
				factor=factor*getFactorDividiendo(temp,i);
			}
			else
			{
				//no habra factoriales infinitos				
				double[] reg=getNijAndFact(temp.factores[i].Nijk);
				//factor=factor*((factorialM(temp.r-1))/(factorialM(reg[0] + temp.r - 1)))*reg[1];
				factor=factor*((factorial(temp.r-1))/(factorial(reg[0] + temp.r - 1)))*reg[1];
			}		
		}
		
		return factor;
	}

	private double getFactor2(NodeH temp)
	{
		double factor=0;
		//calcula el producto de los factores del nodo
		for (int i=0;i<temp.factores.length;i++)
		{
				double[] reg=getNijAndFact2(temp.factores[i].Nijk);
				factor=factor+factorialM(temp.r-1)-factorialM(reg[0] + temp.r - 1)+reg[1];			
		}		
		return factor;
	}
	private boolean verificaFactores(double[] tempA)
	{
		boolean fact=false;
		double sum=0;
		for (int i=0;i<tempA.length;i++)
		{
			sum+=tempA[i];
			if (tempA[i]>170)
			{
				fact=true;
			}
		}
		if(sum>170)
		{
			fact=true;
		}
		return fact;
	}
	
	/*
	*Obntiene el factor dividiendo los factores
	*/
	private double getFactorDividiendo(NodeH temp,int pos)
	{
		double factor=1;	
		double[] tempA=new double[temp.factores[pos].Nijk.length];
		tempA=temp.factores[pos].Nijk;	
		
		
		/*
		- generar los factores del arreglo tempA, que son los factoriales de lsusu elementos
		- dividir por los factores en el vector tempV, de tal forma que queden numeros pequeños
		*/
		//encontrar el tamaño del arreglo del numerador
		double tamaño=0;
		for (int i=0;i<tempA.length;i++)
		{
			tamaño+=tempA[i];
		}
		double[] num=new double[(int)tamaño]; 
    int con=0;
		for (int i=0;i<tempA.length;i++)
		{			
			int cont=1;
			while (cont<=tempA[i])
			{
				num[con]=cont;
				cont++;
				con++;
			}
		}
		double denFactor=getNij(temp.factores[pos].Nijk) + temp.r - 1.0;
		double[] den=new double[(int)denFactor];
		con=1;
		while (con<=denFactor)
		{
			den[con-1]=con;
			con++;
		}				
		if(num.length<den.length)
		{
			int i=0;
			for (i=0;i<num.length;i++)
			{
				factor=factor*num[i]/den[i];
			}
			while (i<den.length)
			{
				factor=factor*(1.0/den[i]);
				i++;
			}
		}
		else
		{
			int i=0;
			for (i=0;i<num.length;i++)
			{
				factor=factor*num[i]/den[i];
			}
			while (i<den.length)
			{
				factor=factor*(num[i]);
				i++;
			}
		}		
		return (factorial(temp.r-1))*factor;
	}	

	/*
	*calcula la suma de los Nijk, asi como el producto de los factoriales de los elementos de Nijk
	*/
	private double[] getNijAndFact(double[] Nijk)
	{
		double sum=0;
		double producto=1;
		for (int i=0;i<Nijk.length;i++)
		{
			sum+=Nijk[i];
			producto=producto*factorial(Nijk[i]);
		}
		double[] reg={sum,producto};
		return reg;
	}	
	/*
	*calcula la suma de los Nijk, asi como el producto de los factoriales de los elementos de Nijk
	*/
	private double[] getNijAndFact2(double[] Nijk)
	{
		double sum=0;
		double producto=0;
		for (int i=0;i<Nijk.length;i++)
		{
			sum+=Nijk[i];
			producto=producto+factorialM(Nijk[i]);
		}
		double[] reg={sum,producto};
		return reg;
	}	
	/**
	*calcula la suma de los Nijk
	*/
	private double getNij(double[] Nijk)
	{
		double sum=0;
		for (int i=0;i<Nijk.length;i++)
		{
			sum+=Nijk[i];
		}
		return sum;
	}
	
	private double getProducto(double[] v)
	{
		double producto = 1;		
		for (int i=0;i<v.length;i++)
		{
			producto=producto*v[i];
		}
		return producto;
	}

	/*
	* Inicializa la lista auxiliar a con la misma estructura que la lista original de nodos
	*/
	private void initNodeListHelper()
	{
		//crear un nodo temporal para recorrer la lista  nodos
		Node temp=red.inicio;
		//crear el nodo inici  de la lista temporal, con el nodo inicio de la lista original
		inNH=new NodeH(temp);
		NodeH tempNH= inNH;

		while (temp.sig!=null)
		{
			//ya que hay un nodo siguiente, crear un nuevo nodo auxiliar a la lista auxiliar
			tempNH.sig=new NodeH(temp.sig);
			tempNH=tempNH.sig;
			temp=temp.sig;
		}
	}
	/*
	*Calcula el factorial de un numero, en forma recursiva
	*/
	private double factorial(double num)
	{		
		int con=1;
		double producto=1;
		while (con<=num)
		{
			producto*=(con);
			con++;
		}
		return producto;
	}
	/**
	*Calcula el factorial modificado un numero, en forma iterativa (en realidad lo que obtiene
	* es la suma de los logaritmos)
	*/
	private double factorialM(double num)
	{
		int con=1;
		double suma=0;
		while (con<=num)
		{
			suma+=Math.log(con);
			con++;
		}
		return suma;
	}

}


/*
* Clase que representa un factor del nodo
*/
class Factor
{
	//El tamaño de este arreglo es del numero de valores que pude tomar la variable a la que pertenece
	//y en el se iran sumando los casos en los que la variable toma el valor k condicionado al valor actual de la
	//combinación de los padres
	public double[] Nijk;
	public Factor(Node node)
	{
		Nijk=new double[node.values.size()];
	}
}
/*
*Clase Node Helper, la cual sirve para crear una lista temporal igual ala lista de nodos de la red
*original, pero que contine los elementos necesarios para calcular la medida Bayesiana
*/
class NodeH
{
	//nodo al que representa
	public Node node = null;
	//Factores aosiados al nodo
	public Factor[] factores = null;
	//frecuencias del los padres
	public int[] frecParents=null;
	//siguiente Nodo en la lista
	public NodeH sig = null;
	//numero de valores posibles del nodo
	public double r;
	/*K este solo se usa en el MDL que es qi(ri-1)
	donde qi es el numero de combinaciones d elos valores de los padres, ri son los valores posibles de la variable
	*/
	public double Ki;
	private ProbInf pro = new ProbInf();
	/*
	*Constructor, inicializa todo lo necesario
	*/
	public NodeH(Node n)
	{
		node = n;
		int qi=pro.getParentsComb(node);
		//inicializar el arreglo de factores del nodo
		factores=new Factor[qi];
		frecParents=new int[qi];
		//inicializar cada uno de los factores
		for (int i=0;i<factores.length;i++)
		{
			factores[i]=new Factor(node);
			frecParents[i]=0;
		}
		//el valor de r es el numero de valores posibles de la variable
		r=node.values.size();
		Ki=(double)qi*((double)node.values.size()-1);
	}
}