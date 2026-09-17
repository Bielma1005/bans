package bansy21.Algoritmos;

import java.util.Vector;
import bansy21.Utilerias.*;
import bansy21.Red.*;
import bansy21.Gui.Algorithm;
import bansy21.*;
import java.util.Calendar;

public class BayesN extends Algorithm
{
	
	public double alfa,alfaG;
	public int profundidad;
  public 	int numVariables;
  int pruebas=0;
	int pruebasH=0;
	///porcentaje de la ganacia de informacion, la define el usuario, y despues de hacer la prueba 
	public double minPorcGan;
	//variable para imprimer el archivo
	

  public String baseName=null;

	public void run()
	{
		setWaitCursor();		
		Calendar date1=Calendar.getInstance();
		bayesN(inicio);
		Calendar date2=Calendar.getInstance();
		println("Tiempo empleado: "+util.tiempoEmpleado(date1,date2));
		parent.agregarRed(red,baseName+"_BayesN");
		setNote("Listo");
		setDefaultCursor();
                
	}


	public BayesN()
	{
		super();
	}
	
	//******************************************************************
//******************************************************************
	//                   BayesN

	public Red bayesN(Node in)
	{
 		
		ListN iList=new ListN();//lista temporal de las variables independientes
		ListN tempList=iList;
		createRed(in);
		incrementProgress();
		setNote("Empezamos");		
		Node temp=inicio;
		do
		{
			if(!temp.equals(varDep))
			{
				tempList.n=temp;
				if(temp.sig!=null)
				{
				  tempList.sig=new ListN();
				  tempList=tempList.sig;
				}
			}
			else//ya se habia inicializado el siguiente objeto
			{
				if (temp.sig==null)
				{
					tempList=null;
				}
			}
			temp=temp.sig;
		}while(temp!=null);
		inArco=new Arco();
		util.inArco=inArco;
		estimaK();

		println("");
		println("");
		println("**********		BayesN		**********");
		println("");
		println("Base de datos: "+baseName);
		println("");
		println("Parámetros:");
		println("	alfaG: "+alfaG);		
		println("	Número estimado de pruebas: "+pruebas);
		println("	alfa: "+alfa);		
		println("	Porcentaje de ganancia de información: "+minPorcGan);
		println("	Profundidad: "+profundidad);
		println("");
		println("");

		bayesN(iList);
		red.inArco=inArco;
		pro.varDep=red.varDep;
		red=pro.probabilityTables(red);
		println("Pruebas hechas "+pruebasH);
		return red;
	}
	private void bayesN(ListN n)
	{
		incrementProgress();
		setNote(varDep.name);
		//println("VarDep:  "+varDep.name);		
		ListN ini=n;
		ListN temp=n;
		Arco tempArco=inArco;
		while(tempArco.sig!=null)
		{
			tempArco=tempArco.sig;
		}
		Vector w=new Vector();	
		Vector v=new Vector();	

		println("		PRIMERA ETAPA");
		println("");
		println("Variable Dependiente:  "+varDep.name);
		println("");

		do
		{
			if(temp.n!=null && !varDep.name.equals(temp.n.name))
			{
				//System.out.println(temp.n.values.size()+" "+temp.n.name);				
				double I=pro.miF(temp.n);
				temp.I=I;
				//porcentaje de ganacia de informacion
				double porcInf=(I/pro.infVarDep)*(-100);
				double T=2*(casos)*I;
				int df=(varDep.values.size()-1)*(temp.n.values.size()-1);
				double chiAlfa=util.chi(alfa,df);
				double chiT=util.pochisq(T,df);
				temp.chiT=chiT;
				v.addElement(temp);

				println("Variable explicativa: "+temp.n.name);
				println("Información mutua: "+I);
				println("Porcentaje de información mutua "+porcInf);
				println("T = "+T);
				println("Grados de libertad: "+df);
				println("Chi de alfa: "+chiAlfa);
				println("Probabilidad de T: "+chiT);
				println("");
				pruebasH++;
				//la hipotesis nula es que las variables son independientes
				//si T es menor que chiAlfa, entonces no se rechaza la hipo
				//si no se cumple, entoces se dibuja un arco, hipotesis alterna, no independientes
				//la segunda condicion que se debe cumplir es que el porcentanje de ganancia de 
				//informacion sea mayor que el minimo porcentaje especificado por el usuario
				if(!(T<chiAlfa) && porcInf>=minPorcGan)
				{
					w.addElement(temp);

					println("");
					println("	Agregar arco de "+temp.n.name+" a "+varDep.name);
					println("");

				}
	//			println("");
			}			
			temp=temp.sig;
		}while(temp!=null);

		println("");
		println("");

		//ordenar la lista de acuerdo a la informacion proporcionada
		w=util.sort(w);
		ListN mayorInf=util.getMayor(v);

		println("		SEGUNDA ETAPA ");
		println("");
		println("Variable más informativa: "+mayorInf.n.name);
		println("");

		if(w.size()>(1))
		{
//			System.out.println(" "+mayorInf.name+"   "+((ListN)w.firstElement()).n.name);			
		
			for(int k=1;k<=profundidad;k++)
			{
				//Condicionar con la primera variable de la lista w, para ver si se eliminan arcos
				Node[] conCond=new Node[k];
				int dfCon=1;
				//los grados de libertadf del conjunto condicionante se calculan como el producto de los valores
				//posibles de cada variable

				//formar el conjunto condicionante. lo encabeza la variable que proporciono mayor informacion. cuando la pro
				//fundidad es mayor a 1, entonces las informaciones que el resto de las variables proporciona debio
				//actualizarse. De estas variables que quedad tomar las que porporciuonan mayor informacion(esta informacion
				//esla informacion mutua condicional dado el grupo condicionante anterior)
	//			println("Conjunto condicionante");
				w=util.reOrder(w);
				for(int j=0;j<k;j++)
				{					
					if(j<w.size())
					{
						conCond[j]=((ListN)w.elementAt(j)).n;//conjunto condicionante
						println("Variable Condicionante"+j+": "+conCond[j].name);
						dfCon=dfCon*(conCond[j].values.size());
					}
				}
	//			println("");
				for(int i=k;i<w.size();i++)
				{
					//System.out.println(""+varDep.name+" k"+k+" w size"+w.size());
					double I=pro.mcIF(((ListN)w.elementAt(i)).n, conCond);
					//actualizar la informacion que proporcina la variable
					double porcInf=(I/pro.infVarDep)*(-100);
					System.out.println(" entropia"+pro.infVarDep);
					//System.out.println(""+porcInf);
					((ListN)w.elementAt(i)).I=I;
					double T=2*(casos)*I;
					int df=(varDep.values.size()-1)*(((ListN)w.elementAt(i)).n.values.size()-1)*dfCon;
					double chiAlfa=util.chi(alfa,df);
					double chiT=util.pochisq(T,df);
					
					println("Variable dependiente: "+varDep.name);
					println("Variable explicativa: "+((ListN)w.elementAt(i)).n.name);
					println("Información mutua: "+I);
					println("Porcentaje de información mutua "+porcInf);
					println("T = "+T);
					println("Grados de libertad: "+df);
					println("Chi de alfa: "+chiAlfa);
					println("Probabilidad de T: "+chiT);
					println("");
					pruebasH++;
					if(T<chiAlfa || porcInf<minPorcGan)
					{
						//remover el arco de la lista de arcos	
						//	System.out.println("quitar de "+((ListN)w.elementAt(i)).n.name+"  a  "+varDep.name);

						println("");
						println("	Quitar Arco de: "+((ListN)w.elementAt(i)).n.name+" a "+varDep.name);
						println("");

						w.removeElementAt(i);
						i--;//porque al remover un elemento, los demas se recorren, de disminuir i, entonces
						//ya no se revisaria la variable que quedo en el lugar de la que se quito						
					}					
				}
			}
		}
		/*
		if (w.size()==0)
		{
			w.addElement(mayorInf);
		}
		*/
		inArco=util.agregaArcos(w, inArco,varDep);
		varDep=mayorInf.n;
		pro.varDep=varDep;
		ListN temp2=util.remove(ini, varDep.name);
//		println("");
		if(temp2.sig != null)
			bayesN(temp2);
	}
	


//******************************************************************
//estima el valor "maximo" de pruebas que puede haber en el algoritmo
	public void estimaK()
	{
		int k_=0;
		int n_=numVariables;//este numero de variables incluye a la variable  dependiente, por lo
		//que en el primer siclo se debe comenzar desde uno
		for (int i=1;i<n_;i++)
		{
			for (int j=0;j<=profundidad;j++)
			{
				if ((n_-i)>j)
				{
					k_ = k_ + ((n_-i)-j);
				}
				else
				{//break;
				}
			}			
		}
		double k=(new Double(k_)).doubleValue();
		//println("K: "+k);
		pruebas=k_;
		//dado que siempre se realizan alrededor del 50% de pruebas solamente
		//k=k*0.4;
		alfa=1-Math.pow((1-alfaG),(1.0/k));
	}
}