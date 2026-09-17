package bansy21.Clasificacion;

import java.util.Vector;
import bansy21.Red.*;
import bansy21.Inferencia.NaiveInference;
import bansy21.Utilerias.*;
import bansy21.Gui.Algorithm;
import bansy21.Algoritmos.*;

/**
*En esta clase se hara la seleccion aleatoria de los ejemplos de entrenamiento, asi como los de prueba
*se llamara a la clasificación Naive o bayes segun el caso para cada una de las selecciones realizadas
* se llevaran los registros de los aciertos y fallas en un objeto para hacer posible(en el caso de clase binaria)
*la obtención de los falsos positivos y los falsos negativos
*
*	En esta clase se lleva a cabo la prueba de hold aout
*/
public class Clasificacion extends Algorithm
{
	public static void main(String[] args)
	{
		System.out.println("Hello World!");
		try
		{
			Archivo arch=new Archivo();
			arch.leer("user/database/chestM.txt");
			Vector casos = arch.casos;
				System.out.println("ya"+casos.size());
			Clasificacion c= new Clasificacion();
		}
		catch(Exception e){}
	}

	public Vector trainingSet=null;
	public Vector casos=null;
	public Vector sampleSet=null;
	public Node varDep;

	//porcentaje de seleccion como conjunto de entrenamiento
	public double por;
	//tipo de 
	public int type;

	
	public int cas;
	public double alfaG=0.05;
	public double alfa=0.05;
	public double minPorcGan=5;
	public int profundidad=3;
	public int numVariables;

	/**
	*Constructor,la base de datos, el tipo de red(naive o bayes) y el porcentaje de labase
	*que sera tomado como entrenamiento para la red
	*/
	public Clasificacion()
	{

	}
	public void run()
	{
		clasification();
		setNote("Listo");
	}
	/**
	*Este metodo hace la clasificación hold-out
	*/
	public void clasification()
	{			
		por=.66;
		println(""+por);
		double[] prob={0,0,0};//Posicion 0 naive, posicioon 1 bayes
		double[][][] falsos=new double[3][2][2];

		switch(varDep.values.size())
		{
			case 2:	
				//en este caso se calcularan los falsos positivos y los falsos negativos
				//todavia no esta implementado
				for (int i=0;i<1;i++)
				{		
					//seleccion(casos,por);
					makeSelection(casos,por*100.0);
					println("T: "+trainingSet.size());
					println("S: "+sampleSet.size());

					Archivo2 a=new Archivo2();	
					a.escribirDatos(trainingSet,""+i+"Training"+baseName,inicio);
					a.escribirDatos(sampleSet,""+i+"Sample"+baseName,null);
					

					//Naive bayes
					Red r=getRed(1,inicio,0);
					double[][] prof=getProbClasification2(r,1);
					prob[0]+=getProbClasification(r,1);
					
					

					falsos[0][0][0]+=prof[0][0];
					falsos[0][0][1]+=prof[0][1];
					falsos[0][1][0]+=prof[1][0];
					falsos[0][1][1]+=prof[1][1];

					//bayes-N
					Red r1=getRed(2,inicio,0);
					prof=getProbClasification2(r1,2);
					prob[1]+=getProbClasification(r1,2);
					

					falsos[1][0][0]+=prof[0][0];
					falsos[1][0][1]+=prof[0][1];
					falsos[1][1][0]+=prof[1][0];
					falsos[1][1][1]+=prof[1][1];
					incrementProgress();
					setNote(""+i);
					
				
					//bayes 9
					Red r2=getRed(4,inicio,5);
					pro.casos=trainingSet;
					pro.varDep=r2.varDep;
					r2=pro.probabilityTables(r2);
					prof=getProbClasification2(r2,2);
					prob[2]+=getProbClasification(r2,2);
					
					parent.agregarRed(r,baseName+"_naive");
					parent.agregarRed(r1,baseName+"_Bayes-N");
					parent.agregarRed(r2,baseName+"_Bayes-9");


					falsos[2][0][0]+=prof[0][0];
					falsos[2][0][1]+=prof[0][1];
					falsos[2][1][0]+=prof[1][0];
					falsos[2][1][1]+=prof[1][1];
				}		
				println("Naive Bayes");
				println("");
				//(double)10*(double)sampleSet.size())

				double den=falsos[0][0][0]+falsos[0][0][1]+falsos[0][1][0]+falsos[0][1][1];

			//	println("den: "+den+" "+(double)100*(double)sampleSet.size()+"   "+sampleSet.size());
				println("Correctamente: "+(falsos[0][0][0]+falsos[0][1][1])/(den));
				println("10: "+falsos[0][0][1]/(falsos[0][1][1]+falsos[0][0][1]));
				println("01: "+falsos[0][1][0]/(falsos[0][1][0]+falsos[0][0][0]));

				den=falsos[1][0][0]+falsos[1][0][1]+falsos[1][1][0]+falsos[1][1][1];
			//	println("den: "+den+" "+(double)100*(double)sampleSet.size()+"   "+sampleSet.size());
				println("");
				println("Bayes-N");
				println("Correctamente: "+(falsos[1][0][0]+falsos[1][1][1])/(den));
				println("10: "+falsos[1][0][1]/(falsos[1][1][1]+falsos[1][0][1]));
				println("01: "+falsos[1][1][0]/(falsos[1][1][0]+falsos[1][0][0]));

				println("");
				den=falsos[2][0][0]+falsos[2][0][1]+falsos[2][1][0]+falsos[2][1][1];
			//	println("den: "+den+" "+(double)100*(double)sampleSet.size()+"   "+sampleSet.size());
				println("Bayes-9");
				println("Correctamente: "+(falsos[2][0][0]+falsos[2][1][1])/(den));
				println("10: "+falsos[2][0][1]/(falsos[2][1][1]+falsos[2][0][1]));
				println("01: "+falsos[2][1][0]/(falsos[2][1][0]+falsos[2][0][0]));

				println("");
				println("Naive bayes: "+prob[0]/(1.0));
				println("Bayes-N: "+prob[1]/(1.0));
				println("Bayes-9: "+prob[2]/(1.0));
				
				break;
			default :
				for (int i=0;i<1;i++)
				{		
					//seleccion(casos,por);
					makeSelection(casos,por*100.0);
					println("T: "+trainingSet.size());
					println("S: "+sampleSet.size());

					Archivo2 a=new Archivo2();	
					a.escribirDatos(trainingSet,""+i+"Training"+baseName,inicio);
					a.escribirDatos(sampleSet,""+i+"Sample"+baseName,null);		
				
					Red r=getRed(1,inicio,0);
					prob[0]+=getProbClasification(r,1);

					Red r1=getRed(2,inicio,0);
					prob[1]+=getProbClasification(r1,2);
					
					Red r2=getRed(4,inicio,5);					
					pro.casos=trainingSet;
					pro.varDep=r2.varDep;
					r2=pro.probabilityTables(r2);
					prob[2]+=getProbClasification(r2,2);

					parent.agregarRed(r,baseName+"_naive");
					parent.agregarRed(r1,baseName+"_Bayes-N");
					parent.agregarRed(r2,baseName+"_Bayes-9");

					incrementProgress();

				}		
				println("Naive bayes: "+prob[0]/(1.0));
				println("Bayes-N: "+prob[1]/(1.0));
				println("Bayes-9: "+prob[2]/(1.0));
				break;			
		}	
	}

	/**
	*Obtener el conjunto de entrenamiento y el conjunto de prueba
	*/
	private void seleccion(Vector casos,double por)
	{
		int porC=(new Double((double)casos.size()*por)).intValue();	
		trainingSet=new Vector();
		sampleSet=new Vector();
		String[] added=new String[casos.size()];
		for (int j=0;j<added.length;j++)
		{
			added[j]=null;
		}
		
		//llenar el vector de los ejemplos de entrenamiento
		while (trainingSet.size()<porC)
		{
			for (int j=0;j<added.length;j++)
			{
				if (trainingSet.size()<porC)
				{					
					if(added[j]==null)
					{
						if (push(50)==1)
						{								
							trainingSet.addElement((Object[])casos.elementAt(j));								
							added[j]=new String();
						}
					}
					else
					{						
					}
				}
				else
				{
					break;
				}
			}
		}
		//llenar el vector de ejemplos de prueba
		for (int j=0;j<added.length;j++)
		{
			if(added[j]==null)
			{
				sampleSet.addElement((Object[])casos.elementAt(j));
			}
		}
	}

	private void makeSelection(Vector casos,double pro)
	{
		Vector[] folds=CrossValidation.makeSelection(casos,2,true,pro);
		trainingSet=folds[0];
		sampleSet=folds[1];
	}
	/**
	*Hace la clasificacion de los ejemplos de prueba
	*/
	public double getProbClasification(Red r, int type)
	{
		double pro=0;
		switch(type)
		{
			case 1://Red naive
				ClasificacionNaive naiC=new ClasificacionNaive(r);
				pro=naiC.makeClasification2(sampleSet);
				break;
			default ://red bayes
				ClasificacionGeneral cla=new ClasificacionGeneral(r);
				if (hasChilds(r,varDep))
				{
					//println("hasChilds");
					JointProbability jp=new JointProbability(r,trainingSet);
					jp.generateFromRed();
					jp.pMonitor=pMonitor;
					cla.jp=jp;
					pro=cla.makeClasificationChilds(sampleSet);
				}
				else
				{
					pro=cla.makeClasification(sampleSet);
				}
				break;
		}
		return pro;
	}
	
	/**
	*Hace la clasificacion de los ejemplos de prueba
	*/
	public double[][] getProbClasification2(Red r, int type)
	{
		double[][] pro=null;
		switch(type)
		{
			case 1://Red naive
				ClasificacionNaive naiC=new ClasificacionNaive(r);
				pro=naiC.makeClasification3(sampleSet);
				break;
			case 2://red bayes
			
				ClasificacionGeneral cla=new ClasificacionGeneral(r);
				if (hasChilds(r,varDep))
				{
					//println("hasChilds");
					JointProbability jp=new JointProbability(r,trainingSet);
					jp.generateFromRed();
					jp.pMonitor=pMonitor;
					cla.jp=jp;
					pro=cla.makeClasificationWithChilds(sampleSet);
				}
				else
				{
					pro=cla.makeClasification2(sampleSet);
				}
				break;				
		}
		return pro;
	}

	/**
	*verifica si u nodo tiene hijos en una red
	*/
	private boolean hasChilds(Red r, Node n)
	{
		Arco temp=r.inArco;
		while (temp!=null)
		{
			if (n.equals(temp.from))
			{
				return true;
			}
			temp=temp.sig;
		}
		return false;
	}
	/**
	*Este metodo obtiene la red para los ejemplos actuales, calculando tablas de probabilidades y to
	*do lo necesario para que sobre esta red se aplique la inferencia sin nigun preoblema
	* type es el tipo de red
	*/
	public Red getRed(int type,Node in,double min)
	{
		Red r=null;
		inicio=in;
		switch(type)
		{
			case 1://Red naive
				NaiveBayes nai=new NaiveBayes();
				nai.varDep=varDep;
				nai.pro.casos=trainingSet;
				r=nai.getRedNaive(inicio);
				break;
			case 2://red bayes
					BayesN bN=new BayesN();
					bN.casos=trainingSet.size();
					bN.alfaG=alfaG;
					bN.alfa=alfa;
					bN.minPorcGan=(double)min;
					bN.profundidad=profundidad;
					bN.numVariables=numVariables;
					bN.pro.casos=trainingSet;
					bN.varDepName=varDep.name;
					r=bN.bayesN(inicio);
				break;
			case 3:
					Bayes2 b2=new Bayes2();
					b2.casos=cas;
					b2.alfa=alfa;
					b2.profundidad=2;
					//b2.numVariables=numVariables;
					b2.pro.casos=trainingSet;
					b2.varDepName=varDep.name;
					b2.pro.varDep=varDep;
					b2.varDep=varDep;
					r=b2.bayes2(inicio);				
				break;
			case 4:
				Bayes9 b9 = new  Bayes9();
				b9.baseName=baseName;
				b9.pro.casos=trainingSet;
				b9.varDepName=varDep.name;
				b9.createRed(inicio);
				try
				{
					r=b9.getRed();
					r.varDep=getVarDep(r);
					//r=checkArcos(r);
				}
				catch(Exception e){println("Error bayes9   "+baseName);};
				break;
		}
		return r;
	}

	/**
	* 
	*/
	private Red checkArcos(Red r)
	{
		Arco temp=r.inArco;
		while (temp!=null)
		{
			if (temp.from.equals(r.varDep))
			{
				Node temp1=temp.from;
				temp.from=temp.to;
				temp.to=temp1;
			}
			temp=temp.sig;
		}
		return r;
	}
	/**
	* Encuentra la varibale dependiente
	*/
	private Node getVarDep(Red r)
	{
		Node temp=r.inicio;
		while (temp!=null)
		{
			if (temp.equals(varDep))
			{
				return temp;
			}
			temp=temp.sig;
		}
		System.out.println("<sdgagaergaregwe.,m");
		return varDep;
	}
	/**
	* Genera un numero aleatorio entre cero y cien, si la probabilidad a (en porcentaje)
	*es mayor al valor generado, quiere decir que el valor cayo en este y devuelve uno, de lo
	*contrario devuelve cero
	*
	*/
	public int push(int a)
	{
		int valor=0;
		int b=new Double((Math.random())*100).intValue();
		if (b<a)
		{
			valor=1;			
		}		
		return valor;
	}
}