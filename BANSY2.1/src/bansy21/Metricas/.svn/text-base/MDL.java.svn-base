package bansy21.Metricas;

import java.util.Vector;
import java.util.Arrays;
import bansy21.Red.*;
import bansy21.Utilerias.*;
import bansy21.Gui.Algorithm;

/*
*  Clase que obtiene la medida MDL para un red dada
*/
public class MDL extends Algorithm
{
	public Red red=null;	
	//nodo inicio de la lista auxiliar
	private NodeH inNH=null;
	public Vector casos;
	
	/*
	* Constructor
	*/
	public MDL()
	{
		super();
	}
	public void run()
	{
		println("MDL: "+getMDL());
	}
	/**
	*Calcula MDL para la red dada
	*donde red es la red y casos es la base de datos
	*/
	public double getMDL()
	{		
		pro.casos=casos;
		red.inicio=pro.fillParents(red.inicio,red.inArco);
		//inicializar la lista auliar de nodos
		initNodeListHelper();
		
		double N=(double)casos.size();
		//para cada uno de los casos
		//sumador de K
		double K=0;
		for (int c=0;c<N;c++)//de esta forma solo se recorre la base de datos una sola ves
		{
			NodeH temp=inNH;
			
			//Para cada nodo
			while (temp!=null)
			{					
				if(temp.node.parents.size()>0)
				{						
					//para cada combinación de los padre
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
				else//el nodo no tiene padres, por lo que solo una combunación
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
		return calculaMDL();
	}

	private double calculaMDL()
	{	
		double Hbd=0;//es lo que en la formula es H(Bs,D)
		NodeH temp=inNH;
		double N=(double)casos.size();
		double K=0;
		while (temp!=null)
		{
			K+=temp.Ki;
			for (int j=0;j<temp.factores.length;j++)
			{
				for (int k=0;k<temp.factores[j].Nijk.length;k++)
				{
					double Nij=getNij(temp.factores[j].Nijk);
				//	System.out.println("Name "+temp.node.name+" Hbd: "+Hbd);
					if(temp.factores[j].Nijk[k]!=0 && Nij!=0)
					{
						Hbd-=(temp.factores[j].Nijk[k]/N)*Math.log(temp.factores[j].Nijk[k]/Nij);
				//		if(temp.factores[j].Nijk[k]!=0 && temp.node.table[j][k].doubleValue()!=0)
					//		Hbd=Hbd*temp.factores[j].Nijk[k]*(temp.node.table[j][k].doubleValue());
					}
				}						
			}
			temp=temp.sig;
			incrementProgress();
		}	
		println("K = "+K+" Hbd: "+Hbd+" N: "+N);
		//return (Math.log(Hbd)+((0.5)*K*Math.log(N)));
		return ((-N*Hbd)-((0.5)*K*Math.log(N)));
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
}