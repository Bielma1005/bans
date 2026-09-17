package bansy21.Clasificacion;

import java.util.Vector;
import bansy21.Red.*;
import bansy21.Inferencia.NaiveInference;
import bansy21.Utilerias.*;
import bansy21.Algoritmos.*;
import bansy21.Gui.Algorithm;

/**
*Implementa el algoritmo de validación cruzada
*/
public class CrossValidation extends Algorithm
{
	
	public int cas;
	public double alfaG=0.05;
	public double alfa=0.05;
	public double minPorcGan=5;
	public int profundidad=3;
	public int numVariables;
	public String baseName="";
	public int fold;
	public Vector casos;
	public Red red;

	public boolean naive=true;
	public boolean bayesn=false;
	public boolean bayes9=false;
	public boolean guardar=false;


	public static void main(String[] args) 
	{
		System.out.println("Hello World!");
		CrossValidation c=new CrossValidation();
		if (args.length==3)
		{
			c.cross(args[0],(new Integer(args[1])).intValue());
		}
		else
		{
			c.cross(args[0],(new Integer(args[1])).intValue());
		}
	}
	
	/**
	*Constructor de la clase
	*/
	public CrossValidation()
	{
		super();
	}
	
	public void run()
	{
		getProbability();
	}

	public void cross(String fileData, int fold_)
	{
		Red red= new Red();		
		try
		{
			//leer la base de datos
			Archivo arch=new Archivo();
			red.inicio = arch.leer(fileData);
			casos = arch.casos;
			red.varDep=arch.varDep;
			red=(new Utilerias()).getValues(casos,red);
			fold=fold_;
			getProbability();
		}
		catch(Exception e){e.printStackTrace(System.out);}
	}

	/**
	* Este metodo ejecuta el algoritmo
	* Recibe una red, la cual tiene la lista de nodos, la lista de arcos
	* y ademas tiene la variable clase(o dependiente)
	* Recibe también un Vector en el que esta cada uno de los registros
	*
	* Recibe tamnien el numero de subconjuntos(dobleces) enlos que se paritra la base de datos
	*/
	public void getProbability()
	{
		//arreglo en el que se guardan cada uno de los subconjuntos
		Vector[] folds;		
		// Efectuar aleatoriamente la selección de los registros en cada uno de los dobleces
		folds=makeSelection(casos,fold,false, 0);
		println("done selection");
		Clasificacion c=new Clasificacion();
		c.pMonitor=pMonitor;
		c.baseName=baseName;
		c.varDep=red.varDep;
		Node inicio=red.inicio;
		double sum=0;
		double[] prob={0,0,0};
		String sal="";
		if (naive)
		{
			sal+="naive	";
		}
		if (bayesn)
		{
			sal+="Bayes-N	";
		}
		if (bayes9)
		{
			sal+="Bayes-9";
		}
		println(sal);
		for (int i=0;i<folds.length;i++)
		{
			//el subconjunto i es el conjunto de prueba, y el resto es el conjunto
			//de entrenamiento
			c.trainingSet=getTrainingSet(folds,i);
			c.sampleSet=folds[i];
			double p=0,p2=0,p3=0;
			if (guardar)
			{			
				Archivo2 a=new Archivo2();
				a.escribirDatos(c.trainingSet,""+i+"Training"+baseName,red.inicio);
				a.escribirDatos(c.sampleSet,""+i+"Testing"+baseName,null);
			}			
			
			if (naive)
			{			
				Red r=c.getRed(1,inicio,minPorcGan);
				p=c.getProbClasification(r,1);			
				prob[0]+=p*c.sampleSet.size();
			}

			if (bayesn)
			{				
				Red r2=c.getRed(2,inicio,minPorcGan);
				//parent.agregarRed(r2,baseName+"_Bayes-N");
				p2=c.getProbClasification(r2,2);
				prob[1]+=p2*c.sampleSet.size();
			}

			if (bayes9)
			{
				Red r3=c.getRed(4,inicio,5);
				pro.casos=c.trainingSet;
				pro.varDep=r3.varDep;
				r3=pro.probabilityTables(r3);
				p3 = c.getProbClasification(r3,2);
				prob[2]+=p3*c.sampleSet.size();
			}

			sal="";
			if (naive)
			{
				Double proF=new Double(p);
				sal+=""+nFormat.format(proF.toString(),4)+"	";
			}
			if (bayesn)
			{
				Double proF=new Double(p2);
				sal+=""+nFormat.format(proF.toString(),4)+"	";
			}
			if (bayes9)
			{
				Double proF=new Double(p3);
				sal+=""+nFormat.format(proF.toString(),4)+"	";				
			}
			println(sal);

			incrementProgress();
			setNote(""+(i+1));
		//	println("size"+folds[i].size());
				
		}		
		println("");
		sal="";
		String sal1="";
		double N=(double)casos.size();
		if (naive)
		{
			Double proF=new Double(prob[0]/N);
			sal+=""+nFormat.format(proF.toString(),4)+"	";
			sal1+="var="+(proF.doubleValue()*(1-proF.doubleValue())/N)+"	";
		}
		if (bayesn)
		{
			Double proF=new Double(prob[1]/N);
			sal+=""+nFormat.format(proF.toString(),4)+"	";
			sal1+="var="+(proF.doubleValue()*(1-proF.doubleValue())/N)+"	";
		}
		if (bayes9)
		{	
			Double proF=new Double(prob[2]/N);
			sal+=""+nFormat.format(proF.toString(),4)+"	";
			sal1+="var="+(proF.doubleValue()*(1-proF.doubleValue())/N)+"	";
		}
		println(sal);
		println(sal1);
	}

	/**
	* Forma el conjunto de entrenamiento a partir de los subconjuntos
	* i es el conjunto ejemplo actual
	*/
	private Vector getTrainingSet(Vector[] folds, int i)
	{
		Vector train = new Vector();
		for (int j=0;j<folds.length;j++)
		{
			if (j!=i)
			{
				int size = folds[j].size();
				for (int k=0;k<size;k++)
				{
					train.addElement(folds[j].elementAt(k));
				}
			}
		}
		return train;
	}
	
	/**
	* Realiza la seleccion aleatoria de los registros de la base de datos, para 
	* llenar los subconjuntos
	*/
	public static Vector[] makeSelection(Vector casos, int fold, boolean holdOut,double pro)
	{
		//tamaño de cada subconjunto
		double N=(double)casos.size();
		double size=N/(double)fold;


		//arreglo en el que se guardan cada uno de los subconjuntos
		Vector[] folds=new Vector[fold];
		//arreglo con las probabilidades
		double[] prob=new double[fold];		
		if (!holdOut)
		{		
			for (int i=0;i<fold;i++)
			{
				//inicioalizar cada uno de los  vectores
				folds[i]=new Vector();
				prob[i]=100.0/(double)fold;
			}
		}
		else
		{
			//inicioalizar cada uno de los  vectores
				folds[0]=new Vector();
				folds[1]=new Vector();
				prob[0]=pro;
				prob[1]=100.0-pro;
		}
		
		boolean[] added=new boolean[casos.size()];
		for (int j=0;j<added.length;j++)
		{
			added[j]=false;
		}

		//con este valor contamos los subconbjuntos que se han llenado
		int con=0;
		while (con!=fold)
		{
			for (int i=0;i<added.length;i++)
			{
				if (!added[i])
				{
					int index=getIndex(prob);
					if (holdOut)
					{
						size=N*(prob[index]/100.0);
					}
					if (folds[index].size()<size)
					{
						folds[index].addElement(casos.elementAt(i));
						added[i]=true;
					}
					else
					{
						//el subconjunto ya esta lleno, por lo que hay que cambiar las probabilidades
						if (prob[index]!=0)
						{						
							con++;
							prob=modifyProbabilities(prob,index,fold,con);
						}
					}
				}
			}
			//este es el caso especial de que ya se hayan llenado fold-1 subconjuntos
			//en este caso el resto de los registros entrarian el el subconjunto que no esta lleno
			if (con==(fold-1))
			{
				int index=getNoFilled(prob);
				for (int i=0;i<added.length;i++)
				{
					if (!added[i])
					{
						folds[index].addElement(casos.elementAt(i));
						added[i]=true;
					}
				}
				con++;
			}
		}
		return folds;
	}

	/**
	*	Regresa el índice del subconjunto no lleno
	*/
	private static int getNoFilled(double[] prob)
	{
		for (int i=0;i<prob.length;i++)
		{
			if (prob[i]>0.0)
			{
				return i;
			}
		}
		return prob.length-1;
	}
	
	/**
	*	Este metodo se ejecuta cuando hay que midificar las probabildiades, DADO QUE ESE SUBCONJUNTO SE 
	* ha llenado
	* recibbe el conjunto de probabilidades(por que puede haber otros llenos) la posición del vector que 
	* ya se lleno, eñl numero de subconjuntos, y el numero de subconjuntos llenos
	*/
	private static double[] modifyProbabilities(double[]prob, int index, int fold, int con)
	{
		for (int j=0;j<prob.length;j++)
		{
			if (j==index)//poner sdu probabildiad en cero 
			{
				prob[j]=0.0;
			}
			else if (prob[j]>0.0)
			{
				prob[j]=100.0/(double)(fold-con);
			}
		}
		return  prob;
	}

	/**
	*Dadas las frecuencias, genmera un numero aleatorio, u devuelve el indice de de la posicion de la frecuencia
	* que cayo 
	*/
	public static int getIndex(double[] frec)
	{
		int ran=(new Double((Math.random())*100)).intValue();
		double sum=0;		
		for (int i=0;i<frec.length;i++)
		{		
			sum = sum + frec[i];
			if (ran<sum)
			{
				return i;
			}
		}
		return frec.length-1;
	}
}		