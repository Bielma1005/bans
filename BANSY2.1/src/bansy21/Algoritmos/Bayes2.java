package bansy21.Algoritmos;
import java.util.Vector;
import bansy21.Utilerias.*;
import bansy21.Red.*;

public class Bayes2
{
	public Node inicio;
	public Arco inArco;
	public String varDepName;
	public Node varDep=null;
	//objeto para accesar la utilerias
	public Utilerias util;
	//objeto para obtener informacion mutua
	public ProbInf pro;	
	public int casos;
	public double alfa;
	public int profundidad=2;
	public Red red;

	public Bayes2()
	{
		util=new Utilerias();
		pro=new ProbInf();
	}
	private void createRed(Node in)
	{
		red = new Red(); 
		Node temp=in;
		Node temp2=null; 
		do
		{
			if(red.inicio==null)
			{
				red.inicio=new Node();
				red.inicio.name=temp.name;
				red.inicio.x=temp.x;
				red.inicio.y=temp.y;
				red.inicio.column=temp.column;
				red.inicio.values=temp.values;
				temp2=red.inicio;
			}
			else
			{
				Node temp3=new Node();
				temp3.name=temp.name;
				temp3.x=temp.x;
				temp3.y=temp.y;
				temp3.column=temp.column;
				temp3.values=temp.values;
				temp2.sig=temp3;
				temp2=temp2.sig;
			}
			if(temp2.name.equals(varDepName))
			{
				//System.out.println("la varDep "+temp2.name);
				varDep=temp2;
			}
			temp=temp.sig;
		}while(temp!=null);
		inicio=red.inicio;
	}
	
	public Red bayes2(Node in)
	{
		ListN iList=new ListN();//lista temporal de las variables independientes
		ListN tempList=iList;
		createRed(in);
		red.varDep=varDep;
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
		bayes2(iList);
		red=pro.probabilityTables(red);
		red.inArco=inArco;	
		return red;
	}
	private void bayes2(ListN n)
	{
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
				if(T>=chiAlfa)
				{
					/*
					if(tempArco.from!=null)
					{
						tempArco.sig=new Arco();
						tempArco=tempArco.sig;
						tempArco.from=temp.n;
						tempArco.to=varDep;
//						System.out.println("arco from: "+temp.n.name+" to: "+varDep.name);
					}
					else
					{
						tempArco.from=temp.n;
						tempArco.to=varDep;
//						System.out.println("arco from: "+temp.n.name+" to: "+varDep.name);
					}
					*/
					w.addElement(temp);
				}
			}
			temp=temp.sig;
		}while(temp!=null);
		//ordenar la lista de acuerdo a la informacion proporcionada
		w=util.sort(w);
		ListN mayorInf=util.getMayor(v);
		if(w.size()>0)
		{
			for(int k=1;k<=profundidad;k++)//en este caso como es el bayes2, entonces la profundidad sera de dos
			{
				//Condicionar con la primera variable de la lista w, para ver si se eliminan arcos
				Node[] conCond=new Node[k];
				int dfCon=1;
				//los grados de libertadf del conjunto condicionante se calculan como el producto de los valores
				//posibles de cada variable				
				for(int j=0;j<k;j++)
				{
					if(j<w.size())
					{
						if(j==0)//se condiciona con la variable que proporciono mas informacion
						{
							conCond[j]=((ListN)w.elementAt(j)).n;//conjunto condicionante
							dfCon=dfCon*(conCond[j].values.size());
						}
						else
						{
							//se condiciona con la variable que proporciona mayor informacion, esta informacion ya se ha actualizado
							conCond[j]=util.getMayor(util.subConjunto(1,w)).n;//conjunto condicionante
							dfCon=dfCon*(conCond[j].values.size());
						}
					}
				}

				for(int i=k;i<w.size();i++)
				{
				//pro.combinaciones(conCond);
					double I=pro.mcIF(((ListN)w.elementAt(i)).n, conCond);
					((ListN)w.elementAt(i)).I=I;
					double T=2*(casos)*I;
					int df=(varDep.values.size()-1)*(((ListN)w.elementAt(i)).n.values.size()-1)*dfCon;
					double chiAlfa=util.chi(alfa,df);					
					if(!(T>=chiAlfa))
					{

						//inArco=util.remove(((ListN)w.elementAt(i)).n,varDep);
							//remover tambien la variable de la lista w
						w.removeElementAt(i);
						i--;//porque al remover un elemento, los demas se recorren, de disminuir i, entonces
//						System.out.println("quitar arco de: "+((ListN)w.elementAt(i)).n.name+" a: "+varDep.name);
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
//			System.out.println("\n");
		if(temp2.sig != null)
			bayes2(temp2);		
	}
	

}
