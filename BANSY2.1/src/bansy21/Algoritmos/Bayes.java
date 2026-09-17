package bansy21.Algoritmos;

import java.util.Vector;
import bansy21.Red.*;
import bansy21.Gui.Algorithm;
import bansy21.Utilerias.*;
import java.util.Calendar;


public class Bayes extends Algorithm
{
	public Bayes()
	{
		super();
	}

	public void run()
	{
		Calendar date1=Calendar.getInstance();
		bayes(inicio);
		Calendar date2=Calendar.getInstance();													
		println("Tiempo empleado: "+util.tiempoEmpleado(date1,date2));
		parent.agregarRed(red,baseName+"_Bayes2");	
		setNote("Listo");
	}

	public void bayes(Node in)
	{	
		ListN iList=new ListN();//lista temporal de las variables independientes
		ListN tempList=iList;
		createRed(in);
		red.varDep=varDep;
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
		println("**********		Bayes2		**********");
		println("");
		println("Base de datos: "+baseName);
		println("");
		println("Parámetros:");
		println("	alfa: "+alfa);
		println("");
		println("");

		bayes(iList);
		red.inArco=inArco;
		pro.varDep=red.varDep;
		red=pro.probabilityTables(red);
		//return red;		
	}
	
	private void bayes(ListN n)
	{	
		incrementProgress();
		ListN ini=n;
		ListN temp=n;
		Arco tempArco=inArco;
		setNote(varDep.name);
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
				//println(temp.n.values.size()+" "+temp.n.name);
				double I=pro.miF(temp.n);	
			//	println("VarDep = "+varDep.name+" Otra= "+temp.n.name);
		//		println("HA=: "+pro.infVarDep+" I: "+I);
				//println("Porcentaje de ganancia  informacion: "+(I/pro.infVarDep)*(-100));
				//println("alfa = "+alfa);				
				temp.I=I;
				double T=2*(casos)*I;
				int df=(varDep.values.size()-1)*(temp.n.values.size()-1);
				double chiAlfa=util.chi(alfa,df);
				double chiT=util.pochisq(T,df);
				temp.chiT=chiT;
				v.addElement(temp);
		//		println("df = "+df);
				println("Variable explicativa: "+temp.n.name);
				println("Información mutua: "+I);
				println("T = "+T);
				println("Grados de libertad: "+df);
				println("Chi de alfa: "+chiAlfa);
				println("Probabilidad de T: "+chiT);
				println("");

				//println("T = "+T+"   chi  "+chiAlfa);

				/*
				println("Chi = "+util.chi(alfa-0.01,df));
				println("Chi = "+chiAlfa);
				println("Chi = "+util.chi(alfa+0.01,df));

				println("chiT = "+util.pochisq((T-1),df));
				println("Probabilidad de T: "+chiT);
				println("chiT = "+util.pochisq(T+1,df));

				*/
				
				//si T es mayor o igual a chiAlfa, entonces hay que agregar un arco
				//desde el nodo actual de la lista hacia la variable dependiente
				if(T>=chiAlfa)
				{
					println("");
					println("	Agregar arco de "+temp.n.name+" a "+varDep.name);
					println("");
					/*
					if(tempArco.from!=null)
					{
						tempArco.sig=new Arco();
						tempArco=tempArco.sig;
						tempArco.from=temp.n;
						tempArco.to=varDep;
						//println("arco from: "+temp.n.name+" to: "+varDep.name);
						//println("\n");
					}
					else
					{
						tempArco.from=temp.n;
						tempArco.to=varDep;
						//println("arco from: "+temp.n.name+" to: "+varDep.name);
						//println("\n");
					}
					*/
		//			println("arco from: "+temp.n.name+" to: "+varDep.name);
					w.addElement(temp);
				}	
				else
				{
//					println("\n");
				}
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
		if(w.size()>0)
		{
			//Condicionar con la primera variable de la lista w, para ver si se eliminan arcos
//			Node[] conCond={((ListN)w.elementAt(0)).n};//conjunto condicionante
			//println("  mayor"+mayorInf.name);
			Node[] conCond={mayorInf.n};
			for(int i=1;i<w.size();i++)
			{
				//if(!((ListN)w.elementAt(i)).n.name.equals(mayorInf.name))
				//{
					double I=pro.mcIF(((ListN)w.elementAt(i)).n, conCond);
					//println("porcentaje de informacion: "+(I/pro.infVarDep)*100+"  HA=: "+pro.infVarDep+" I: "+I);
					double T=2*(casos)*I;
					int df=(varDep.values.size()-1)*(((ListN)w.elementAt(i)).n.values.size()-1)*(conCond[0].values.size());
					double chiAlfa=util.chi(alfa,df);
//					println("\n condicional");
		//			println("VarDep = "+varDep.name+" Otra= "+((ListN)w.elementAt(i)).n.name+" VarCon "+conCond[0].name);
	//				println("T = "+T+" I = "+I+" alfa = "+alfa+" df = "+df+" Chi = "+chiAlfa);
				
					println("T = "+T+" Chi = "+chiAlfa);
					if(T<chiAlfa)
					{
						//inArco=util.remove(((ListN)w.elementAt(i)).n,varDep);
						println("");
						println("	Quitar Arco de: "+((ListN)w.elementAt(i)).n.name+" a "+varDep.name);
						println("");
						w.removeElementAt(i);
						println("T = "+T+" Chi = "+chiAlfa);
						i--;
/*
						println("quitar de "+((ListN)w.elementAt(i)).n.name+"  a  "+varDep.name);									
						println("VarDep = "+varDep.name+" Otra= "+((ListN)w.elementAt(i)).n.name+" VarCon "+conCond[0].name);
						println("T = "+T+" I = "+I+" alfa = "+alfa+" df = "+df+" Chi = "+chiAlfa);
*/
					}
					//else{println("no quitar de "+((ListN)w.elementAt(i)).n.name+"  a  "+varDep.name);}
				//}
			}	
		}

		inArco=util.agregaArcos(w, inArco,varDep);
		varDep=mayorInf.n;//getMayor(v);
		pro.varDep=varDep;
		ListN temp2=util.remove(ini, varDep.name);
	//	println("\n");
		
	
		incrementProgress();
		if(temp2.sig != null)
			bayes(temp2);				
	}
	
}
