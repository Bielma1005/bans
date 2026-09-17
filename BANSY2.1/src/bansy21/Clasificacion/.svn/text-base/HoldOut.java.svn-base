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
public class HoldOut extends Algorithm
{
	
	public int cas;
	public double alfaG=0.05;
	public double alfa=0.05;
	public double minPorcGan=5;
	public int profundidad=3;
	public int numVariables;
	public String baseName="";
	public double hold;
	public Vector trainingSet=null;
	public Vector casos=null;
	public Vector sampleSet=null;
	public Red red;

	public boolean naive=true;
	public boolean bayesn=false;
	public boolean bayes9=false;
	public boolean guardar=false;
	private Falsos[] falsos=null;

	Clasificacion c=new Clasificacion();


	public static void main(String[] args) 
	{
		System.out.println("Hello World!");		
	}
	
	/**
	*Constructor de la clase
	*/
	public HoldOut()
	{
		super();		
	}
	
	public void run()
	{
		getProbability();
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
		inicio=red.inicio;
		varDep=red.varDep;

		double[] prob={0,0,0};//Posicion 0 naive, posicioon 1 bayes
		falsos=new Falsos[3];
		falsos[0]=new Falsos();
		falsos[1]=new Falsos();
		falsos[2]=new Falsos();
		double[][] prof;
		

		c.pMonitor=pMonitor;
		c.baseName=baseName;
		c.varDep=red.varDep;
		c.alfaG=alfaG;
		c.alfa=alfa;
		c.minPorcGan=minPorcGan;
		c.profundidad=profundidad;
		c.numVariables=numVariables;

		//seleccion(casos,por);
		makeSelection(casos,hold);
		incrementProgress();	
		println("Tamaño del conjunto de entrenamiento: "+c.trainingSet.size());
		println("Tamaño del conjunto prueba: "+c.sampleSet.size());

		println("Variable dependiente (la última culumna de los datos): "+varDep.name+"\n");

		if (guardar)
		{
			Archivo2 a=new Archivo2();	
			a.escribirDatos(c.trainingSet,""+"Training"+baseName,inicio);
			a.escribirDatos(c.sampleSet,""+"Testing"+baseName,null);
		}
		
		if (naive)
		{

			//System.out.println("Naive:");
			//Naive bayes
			Red r=c.getRed(1,inicio,0);
			falsos[0].falsos=c.getProbClasification2(r,1);
		//	prob[0]+=c.getProbClasification(r,1);

			//falsos[0]=sumale(falsos[0],prof);
		
			
			parent.agregarRed(r,baseName+"_naive");
		}
		incrementProgress();	
		if (bayesn)
		{
			//bayes-N
			Red r1=c.getRed(2,inicio,minPorcGan);
			falsos[1].falsos=c.getProbClasification2(r1,2);
		//	prob[1]+=c.getProbClasification(r1,2);

		//	falsos[1]=sumale(falsos[1],prof);
			
		
			parent.agregarRed(r1,baseName+"_Bayes-N");
		}
		incrementProgress();	
		
		
		if (bayes9)
		{		
			//bayes 9
			Red r2=c.getRed(4,inicio,5);
			pro.casos=c.trainingSet;
			pro.varDep=r2.varDep;
			r2=pro.probabilityTables(r2);
			falsos[2].falsos=c.getProbClasification2(r2,2);
		//	prob[2]+=c.getProbClasification(r2,2);
			
			//falsos[2]=sumale(falsos[2],prof);
			//sumale(0,prof);

			parent.agregarRed(r2,baseName+"_Bayes-9");
		}
		if (naive)
		{
			printR("Naive Bayes",falsos[0].falsos);
		}						
		if (bayesn)
		{
			printR("Bayes-N",falsos[1].falsos);
		}		
		if (bayes9)
		{
			printR("Bayes-9",falsos[2].falsos);
		}		
		
		/*
		println("");
		println("Naive bayes: "+prob[0]/(1.0));
		println("Bayes-N: "+prob[1]/(1.0));
		println("Bayes-9: "+prob[2]/(1.0));
		*/
	}

	private double[][] sumale(double[][] falsos,double[][] prof)
	{
		for (int i=0;i<falsos.length;i++)
		{
			for (int j=0;j<falsos[i].length;j++)
			{
				falsos[i][j]+=prof[i][j];
			}
		}
		return falsos;
	}

	private void makeSelection(Vector casos,double pro)
	{
		Vector[] folds=CrossValidation.makeSelection(casos,2,true,pro);
		c.trainingSet=folds[0];
		c.sampleSet=folds[1];
	}

	private void printR(String a, double[][] f)
	{
		println(a);
		println("");

		//se guardan el numero de ejemplos del valor i-esimo de lo valores de la varibale
		double[] temp=new double[f.length];
		
		for (int i=0;i<f.length;i++)
		{
			temp[i]=0.0;
			for (int j=0;j<f[i].length;j++)
			{
				temp[i]+=f[i][j];
				
			}
		}
		
		int len=((String)varDep.values.elementAt(0)).length();
		String sal="";
		for (int i=0;i<len;i++)
		{
			sal+=" ";
		}
		sal+="	";
		int size=varDep.values.size();

		for (int i=0;i<size;i++)
		{
			if (i!=(size-1))
			{
				sal+=((String)varDep.values.elementAt(i))+"	";
			}
			else
			{
				sal+=((String)varDep.values.elementAt(i));
			}
		}

		println(sal);
		
		//se suman los casos clasificados correctamente
		double correctos=0;
		//representa todos los ejemplos clasificados
		double den=0;
		for (int i=0;i<f.length;i++)
		{
			sal="";
			sal+=((String)varDep.values.elementAt(i))+"("+temp[i]+")	";
			den+=temp[i];//sumar los casos
			for (int j=0;j<f[i].length;j++)
			{
				if (i==j)
				{
					correctos+=f[i][j];//sumar los correctos
				}
				if (j!=f[i].length)
				{
					Double proF=new Double((f[i][j]/temp[i]));
					int c=(new Double(f[i][j])).intValue();
					sal+=""+nFormat.format(proF.toString(),4)+"("+c+")	";
					//sal+=""+(f[i][j]/temp[i])+"	";
				}
				else
				{
					Double proF=new Double((f[i][j]/temp[i]));
					int c=(new Double(f[i][j])).intValue();
					sal+=""+nFormat.format(proF.toString(),4)+"("+c+")";
					//sal+=""+(f[i][j]/temp[i]);
				}				
			}
			println(sal);
		}

		println("");
		Double proF=new Double((correctos/den));
		println("Correctamente: "+nFormat.format(proF.toString(),4));
		println("");

		if (f.length==2)
		{
			double sensitividad = f[0][0]/(f[0][0]+f[0][1]);
			double especificidad = f[1][1]/(f[1][1]+f[1][0]);
			double PVmas = f[0][0]/(f[0][0]+f[1][0]);

			double PVmenos = f[1][1]/(f[1][1]+f[0][1]);
			println("Sensitividad:"+nFormat.format((new Double(sensitividad)).toString(),4));
			println("Especificidad:"+nFormat.format((new Double(especificidad)).toString(),4));
			println("PV+:"+nFormat.format((new Double(PVmas)).toString(),4));
			println("PV-:"+nFormat.format((new Double(PVmenos)).toString(),4));
			println("");
		}
	}
}



class Falsos
{
	public double[][] falsos;
	public Falsos()
	{
		
	}
}