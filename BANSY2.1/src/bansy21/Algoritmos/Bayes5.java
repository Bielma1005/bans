package bansy21.Algoritmos;

import java.util.Vector;
import bansy21.Utilerias.*;
import bansy21.Red.*;
import bansy21.Gui.Algorithm;
import java.util.Calendar;

public class Bayes5 extends Algorithm
{
	public int profundidad;
	public Bayes5()
	{
		super();
		util=new Utilerias();
		pro=new ProbInf();	
	}

	public void run()
	{
		Calendar date1=Calendar.getInstance();
		bayes5(inicio);
		Calendar date2=Calendar.getInstance();													
		println("Tiempo empleado: "+util.tiempoEmpleado(date1,date2));
		parent.agregarRed(red,baseName+"_Bayes5");	
		setNote("Listo");
	}
	
	public void bayes5(Node in)
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
				if(temp.sig!=null && !temp.equals(varDep))
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
		println("");
		println("");
		println("**********		Bayes5		**********");
		println("");
		println("Base de datos: "+baseName);
		println("");
		println("Parámetros:");
		println("	alfa: "+alfa);
		println("");
		println("");
		bayes5(iList);
		red.inArco=inArco;		
		pro.varDep=red.varDep;
		red=pro.probabilityTables(red);

		//return red;
	}
	private void bayes5(ListN n)
	{
		incrementProgress();
		setNote(varDep.name);
		//System.out.println("VarDep:  "+varDep.name);
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
				//double I=temp.I;
				double T=2*(casos)*I;
				int df=(varDep.values.size()-1)*(temp.n.values.size()-1);
				double chiAlfa=util.chi(alfa,df);
				v.addElement(temp);
				//si T es mayor o igual que chiAlfa, entonces hay que agregar un arco
				//desde el nodo actual de la lista hacia la variable dependiente
				//System.out.println("T"+T+"      chiAlfa"+chiAlfa+"   I"+I);
				println("Variable explicativa: "+temp.n.name);
				println("Información mutua: "+I);
				println("T = "+T);
				println("Grados de libertad: "+df);
				println("Chi de alfa: "+chiAlfa);			
				println("");
				if(T>=chiAlfa)
				{
					/*
					if(tempArco.from!=null)
					{
						tempArco.sig=new Arco();
						tempArco=tempArco.sig;
						tempArco.from=temp.n;
						tempArco.to=varDep;
						System.out.println("arco from: "+temp.n.name+" to: "+varDep.name);
					}
					else
					{
						tempArco.from=temp.n;
						tempArco.to=varDep;
						System.out.println("arco from: "+temp.n.name+" to: "+varDep.name);
					}
					*/
					println("");
					println("	Agregar arco de "+temp.n.name+" a "+varDep.name);
					println("");
					w.addElement(temp);
				}
			}
			temp=temp.sig;
		}while(temp!=null);
		//ordenar la lista de acuerdo a la informacion proporcionada
		println("");
		println("");

		w=util.sort(w);
		ListN mayorInf=util.getMayor(v);
		println("		SEGUNDA ETAPA ");
		println("");
		println("Variable más informativa: "+mayorInf.n.name);
		println("");
		//System.out.println("mayor  "+mayorInf.name);
		if(w.size()>0)
		{
			for(int k=1;k<=profundidad;k++)
			{
				//Condicionar con la primera variable de la lista w, para ver si se eliminan arcos
				Node[] conCond=new Node[k];
				int dfCon=1;
				//los grados de libertadf del conjunto condicionante se calculan como el producto de los valores
				//posibles de cada variable
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
				for(int i=k;i<w.size();i++)
				{				
					double I=pro.mcIF(((ListN)w.elementAt(i)).n, conCond);					
					((ListN)w.elementAt(i)).I=I;
					double T=2*(casos)*I;
					int df=(varDep.values.size()-1)*(((ListN)w.elementAt(i)).n.values.size()-1)*dfCon;
					double chiAlfa=util.chi(alfa,df);					
					double porcInf=(I/pro.infVarDep)*(-100);
					//System.out.println(""+porcInf);
					//System.out.println("VarDep "+varDep.name+"    var"+((ListN)w.elementAt(i)).n.name);
					//System.out.println("T"+T+"      chiAlfa"+chiAlfa+"   I"+I);
					println("Variable dependiente: "+varDep.name);
					println("Variable explicativa: "+((ListN)w.elementAt(i)).n.name);
					println("Información mutua: "+I);
					println("Porcentaje de información mutua "+porcInf);
					println("T = "+T);
					println("Grados de libertad: "+df);
					println("Chi de alfa: "+chiAlfa);					
					println("");
					if(!(T>=chiAlfa))
					{
						//inArco=util.remove(((ListN)w.elementAt(i)).n,varDep);
							//remover tambien la variable de la lista w
						//System.out.println("quitar arco de: "+((ListN)w.elementAt(i)).n.name+" a: "+varDep.name);
						println("");
						println("	Quitar Arco de: "+((ListN)w.elementAt(i)).n.name+" a "+varDep.name);
						println("");
						w.removeElementAt(i);						
						i--;//porque al remover un elemento, los demas se recorren, de disminuir i, entonces
						
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
		//	System.out.println("\n");
		if(temp2.sig != null)
			bayes5(temp2);		
	}
	//Reordena el vetor w de acuerdo con la nueva informacion proporcionada por la variable
	//Pero deja en primer lugar a la variable que proporciono mayor informacion en la etapa de informacion
	//mutua marginal
	private Vector reOrder(Vector w)
	{
		Vector temp=new Vector();
		int size=w.size();
		for (int i=1;i<size;i++)
		{
			temp.addElement(w.elementAt(i));
		}
		temp=util.sort(temp);
		for (int i=1;i<size;i++)
		{
			w.set(i,temp.elementAt(i-1));
		}
		return w;
	}
}

