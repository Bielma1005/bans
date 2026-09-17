package bansy21.Algoritmos;

import java.util.Vector;
import bansy21.Utilerias.*;
import bansy21.Red.*;

public class ID3
{
	public static void main(String[] args) 
	{
		System.out.println("Hello World!");
		ID3 id3=new ID3(args[0]);
	}

	Vector casos=null;
	public ProbInf pro;	
	Node inicio=null;
	private Utilerias util;	

	public ID3(String fileName)
	{
		Archivo arch=new Archivo();
		
		try
		{		
			inicio = arch.leer(fileName);
		}
		catch(Exception e){System.out.println("Error en el archivo");}
		if (inicio!=null)
		{
			Node clase = arch.varDep;
			Vector ejemplos = arch.casos;
			casos=ejemplos;
			Node temp=inicio;
			Vector atributos=new Vector();
			while (temp!=null)
			{
				if(!temp.equals(clase))
				{
					atributos.addElement(temp);
				}
				temp=temp.sig;
			}	
			System.out.println("atributos: "+atributos.size());
			System.out.println("Ejemplos: "+ejemplos.size());
			System.out.println("Clase: "+clase.name);
			getValues();
			Node root=id3(ejemplos,clase,atributos);
			print(root,"");
		}
	}

	private void print(Node root, String espacios)
	{
		if(root.hijos==null)
		{
			System.out.println(espacios+root.label);
		}
		else
		{
			for (int i=0;i<root.hijos.length;i++)
			{
				System.out.println(espacios+root.name+" = "+(String)root.values.elementAt(i));
				print(root.hijos[i],espacios+"  ");
			}
		}
	}


	public Node id3(Vector ejemplos, Node clase, Vector atributos)
	{
		Node root=new Node();
		//indida si es determinista
		boolean halt=false;

		/****verificar si para el conjunto de ejemplos es determinista o no*/
		int[] contador={0,0};
		int total=ejemplos.size();
		for (int i=0;i<total;i++)
		{
			if(((Object[])casos.elementAt(i))[clase.column].toString().equals((String)clase.values.elementAt(0)))
				contador[0]++;
			else
				contador[1]++;
		}
		if (atributos.size()==0)
		{
			halt=true;
			if (contador[0]>contador[1])
				root.label=(String)clase.values.elementAt(0);			
			else
				root.label=(String)clase.values.elementAt(1);
		}
		else if (contador[0]==0)//
		{
			halt=true;
			root.label=(String)clase.values.elementAt(1);
		}
		else if(contador[1]==0)
		{
			halt=true;
			root.label=(String)clase.values.elementAt(0);
		}
		/********/
		
		if(!halt)
		{
			root=buscaMejorParametro(ejemplos, clase, atributos);
		}
		return root;
	}
	private Node buscaMejorParametro(Vector ejemplos, Node clase, Vector atributos)
	{
		//Calcular la ganacia de información para ver que atributo explica mejor a la clase
		pro=new ProbInf();
		pro.varDep=clase;
		pro.casos=ejemplos;
		double[] inf=new double[atributos.size()];
		for (int i=0;i<inf.length;i++)
		{
			inf[i]=pro.miF((Node)atributos.elementAt(i));
		}
		Node mayor=(Node)atributos.elementAt(0);
		double infM=inf[0];
		for (int i=1;i<inf.length;i++)
		{
			if(inf[i]>infM)
			{
				mayor=(Node)atributos.elementAt(i);
				infM=inf[i];
			}
		}
		//eliminar el atributo mayor de la lista de atributos
		atributos.removeElement(mayor);
		//para cada valor del atributo mayor crear un rama
		mayor.hijos=new Node[mayor.values.size()];
		for (int i=0;i<mayor.hijos.length;i++)
		{
			Vector newEjemplos = new Vector();
			//recorrer los ejemplos actuales para encontar los que cumplen con el valor de la variable
			int total=ejemplos.size();
			for (int j=0;j<total;j++)
			{
				if(((Object[])ejemplos.elementAt(j))[mayor.column].equals((String)mayor.values.elementAt(i)))
					newEjemplos.addElement(ejemplos.elementAt(j));
			}
			mayor.hijos[i]=id3(newEjemplos,clase,atributos);
		}
		return mayor;
	}

	/*revisa los datos para obtener los valores posibles de las variables
	*/
	private void getValues()
	{
		int con1=0;
		util=new Utilerias();
		Node temp=inicio;
		do
		{
			temp.values=new Vector();
			for(int i=0;i<casos.size();i++)
			{
				if(!member(((Object[])casos.elementAt(i))[con1],temp.values))
				{
					temp.values.addElement(((Object[])casos.elementAt(i))[con1]);
				}
			}
			if(temp.values.size()==1)
				temp.values.addElement("0");
			//System.out.println(""+temp.values.size());
			temp.table=util.getTable(temp);
			temp=temp.sig;
			con1++;
		}while(temp!=null);
	}
	//verifica si el obejeto "o" es miembro del vector v
	private boolean member(Object o, Vector v)
	{
		for(int i=0;i<v.size();i++)
		{
			if(v.elementAt(i).toString().equals(o.toString()))
				return true;
		}
		return false;
	}
}
