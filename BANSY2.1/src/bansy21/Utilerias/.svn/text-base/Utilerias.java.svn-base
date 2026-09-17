package bansy21.Utilerias;

import java.util.Vector;
import java.util.Calendar;
import bansy21.Red.*;

public class Utilerias 
{
	public Arco inArco;
	public Utilerias()
	{

	}

	//verifica que arcos faltan
	public void comparaRedes(Arco inA, Arco inOrig)
	{
		Vector falt=new Vector();
		Arco temp=inOrig;
		do
		{
			if(!miembro(temp,inA))
				falt.addElement(temp);
			temp=temp.sig;
		}
		while (temp!=null);
		for (int i=0;i<falt.size();i++)
		{
			temp=(Arco)falt.elementAt(i);
			System.out.println("Falta de: "+temp.from.name+" a: "+temp.to.name);
		}
	}
	private boolean miembro(Arco f, Arco inA)
	{
		Arco temp=inA;
		do
		{
			if((temp.from.name.equals(f.from.name) && temp.to.name.equals(f.to.name)) || (temp.from.name.equals(f.to.name) && temp.to.name.equals(f.from.name)))
				return true;
			temp=temp.sig;
		}
		while (temp!=null);
		return false;
	}
	//Reordena el vetor w de acuerdo con la nueva informacion proporcionada por la variable
	//Pero deja en primer lugar a la variable que proporciono mayor informacion en la etapa de informacion
	//mutua marginal
	public Vector reOrder(Vector w)
	{
		Vector temp=subConjunto(1,w);
		temp=sort(temp);
		int size=w.size();
		for (int i=1;i<size;i++)
		{
			w.set(i,temp.elementAt(i-1));
		}
		return w;
	}
	//Este hace lo que el metodo anterior pero usando la probabilidad del estadistico T
	public Vector reOrder2(Vector w)
	{
		Vector temp=subConjunto(1,w);
		temp=sortC(temp);
		int size=w.size();
		for (int i=1;i<size;i++)
		{
			w.set(i,temp.elementAt(temp.size()-i));
		}
		return w;
	}
	//devuelve un vector con los elementos del vector w a partir de la posision indicada
	public Vector subConjunto(int pos, Vector w)
	{
		Vector temp=new Vector();
		int size=w.size();
		for (int i=pos;i<size;i++)
		{
			temp.addElement(w.elementAt(i));
		}
		return temp;
	}
	//busca cual fue el nodo que proporcionó mayor informacion
	public Node getMenor(Vector v)
	{
		ListN menor=(ListN)v.elementAt(0);
		int total=v.size();
		for (int i=1;i<total;i++)
		{
			if (((ListN)v.elementAt(i)).chiT<menor.chiT)
			{
				menor=(ListN)v.elementAt(i);
			}
		}
		return menor.n;
	}
	//busca cual fue el nodo que proporcionó mayor informacion
	public ListN getMayor(Vector v)
	{
		ListN mayor=(ListN)v.elementAt(0);
		int total=v.size();
		for (int i=1;i<total;i++)
		{
			if (((ListN)v.elementAt(i)).I>mayor.I)
			{
				mayor=(ListN)v.elementAt(i);
			}
		}
		return mayor;
	}
	public Node removeNode(Node toRemove, Node inicio)
	{
		Node temp=inicio;
		Node ant=null;	
		do
		{
			if(temp.name.equals(toRemove.name))
			{
				if(ant==null)
				{
					inicio=temp.sig;
				}
				else
				{
					ant.sig=temp.sig;
				}
				break;
			}
			else
			{
				ant=temp;
				temp=temp.sig;
			}
		}while(temp!=null);
		return inicio;
	}
	//remueve un arco de la lista
	public Arco remove(Node from, Node to)
	{
		Arco temp=inArco;
		Arco ant=null;
		do
		{                  
			if(temp.from.name.equals(from.name) && temp.to.name.equals(to.name))
			{
				if(ant==null)
				{
					System.out.println("remover inicial");
					inArco=temp.sig;
				}
				else 
				{
					ant.sig=temp.sig;
				}
			  break;
			}
			else
			{
				ant=temp;
				temp=temp.sig;
			}
		}while(temp!=null);
		return inArco;
	}
        
        //remueve un arco de la lista
	public Arco removeNodirectedArc(Node from, Node to)
	{
		Arco temp=inArco;
		Arco ant=null;
		do
		{                  
			if((temp.from.name.equals(from.name) && temp.to.name.equals(to.name))||(temp.from.name.equals(to.name) && temp.to.name.equals(from.name)))
			{
				if(ant==null)
				{
					System.out.println("remover inicial");
					inArco=temp.sig;
				}
				else 
				{
					ant.sig=temp.sig;
				}
			  break;
			}
			else
			{
				ant=temp;
				temp=temp.sig;
			}
		}while(temp!=null);
		return inArco;
	}
		//remueve un arco de la lista
	public Arco remove(Arco arcoToRemove, Arco in_)
	{
		Arco temp=in_;
		Node from=arcoToRemove.from;
		Node to=arcoToRemove.to;
		Arco ant=null;
		do
		{
			if(temp.from.name.equals(from.name) && temp.to.name.equals(to.name))
			{
				if(ant==null)
				{
					System.out.println("remover inicial");
					in_=temp.sig;
				}
				else 
				{
					ant.sig=temp.sig;
				}
			  break;
			}
			else
			{
				ant=temp;
				temp=temp.sig;
			}
		}while(temp!=null);
		return in_;
	}
	public Arco agregaArcos(Vector w, Arco inicio, Node to)
	{
		if(w.size()>0)
		{
			Arco temp=inicio;
			while(temp.sig!=null)
			{
				temp=temp.sig;
			}
			if(temp.from==null)//se trata del primer arco(la lista esta vacia)
			{
				temp.from=((ListN)w.elementAt(0)).n;
				temp.to=to;
				for (int i=1;i<w.size();i++)
				{
					temp.sig=new Arco();
					temp=temp.sig;
					temp.from=((ListN)w.elementAt(i)).n;
					temp.to=to;					
				}
			}
			else
			{
				for (int i=0;i<w.size();i++)
				{
					temp.sig=new Arco();
					temp=temp.sig;
					temp.from=((ListN)w.elementAt(i)).n;
					temp.to=to;					
				}
			}
		}
		return inicio;
	}
	//remueve un elemento de la lista temporal de las variables, mientras se esta ejecutando el algoritmo
	public ListN remove(ListN inicio, String name)
	{
	  ListN temp=inicio;
		ListN ant=null;
		ListN ini=inicio;
		do
		{
			if(temp.n.name.equals(name))
			{
				if(ant==null)
				{
					ini=temp.sig;					
				}
				else 
				{
					ant.sig=temp.sig;					
				}
			  break;
			}
			else
			{
				ant=temp;
				temp=temp.sig;
			}			
		}while(temp!=null);
		return ini;
	}
	//Agrega un nodo a la lista de nodos
	public Node add(Node toAdd, Node inicio)
	{
		Node temp=inicio;
		if(inicio==null)
		{
			inicio=toAdd;
		}
		else
		{
			while(temp.sig!=null)
			{
				temp=temp.sig;
			}
			temp.sig=toAdd;
		}
		return inicio;
	}
	//agrega un arco a la lista de arcos
	public Arco add(Arco toAdd, Arco inicio)
	{
		Arco temp=inicio;
		if(inicio==null)
		{
			inicio=toAdd;
		}
		else
		{
			while(temp.sig!=null)
			{
				temp=temp.sig;
			}
			temp.sig=toAdd;
		}
		return inicio;
	}
  public Vector sort(Vector b) 
  {   
    ListN hold;  // temporary holding area for swap
	  Vector a=b;
    for ( int pass = 1; pass < a.size(); pass++ ) 
	  {// passes
      for ( int i = 0; i < a.size() - 1; i++ )
		  {// one pass   
        if (((ListN)a.elementAt(i)).I < ((ListN)a.elementAt(i+1)).I ) 
				{      // one comparison
           hold =(ListN) a.elementAt(i);                   // one swap
           a.set(i,a.elementAt(i+1));
           a.set(i+1,hold);
        }
			}
		}
	   return a;
	}	
	//ordena pero usando el chiT
	public Vector sortC(Vector b) 
  {   
    ListN hold;  // temporary holding area for swap
	  Vector a=b;
    for ( int pass = 1; pass < a.size(); pass++ ) 
	  {// passes
      for ( int i = 0; i < a.size() - 1; i++ )
		  {// one pass   
        if (((ListN)a.elementAt(i)).chiT < ((ListN)a.elementAt(i+1)).chiT ) 
				{      // one comparison
           hold =(ListN) a.elementAt(i);                   // one swap
           a.set(i,a.elementAt(i+1));
           a.set(i+1,hold);
        }
			}
		}
	  return a;
	}	
	//verifica si el obejeto "o" es miembro del vector v
	public boolean miembro(Object o, Vector v)
	{
		for(int i=0;i<v.size();i++)
		{
			if(v.elementAt(i).toString().equals(o.toString()))
				return true;
		}
		return false;
	}


	//*********************************************
	//Calculo de chi cuadrada
	public double poz(double z) 
	{
        double y, x, w;
        double Z_MAX = 6.0;              /* Maximum meaningful z value */
        
        if (z == 0.0) {
            x = 0.0;
        } else {
            y = 0.5 * Math.abs(z);
            if (y >= (Z_MAX * 0.5)) {
                x = 1.0;
            } else if (y < 1.0) {
                w = y * y;
                x = ((((((((0.000124818987 * w
                         - 0.001075204047) * w + 0.005198775019) * w
                         - 0.019198292004) * w + 0.059054035642) * w
                         - 0.151968751364) * w + 0.319152932694) * w
                         - 0.531923007300) * w + 0.797884560593) * y * 2.0;
            } else {
                y -= 2.0;
                x = (((((((((((((-0.000045255659 * y
                               + 0.000152529290) * y - 0.000019538132) * y
                               - 0.000676904986) * y + 0.001390604284) * y
                               - 0.000794620820) * y - 0.002034254874) * y
                               + 0.006549791214) * y - 0.010557625006) * y
                               + 0.011630447319) * y - 0.009279453341) * y
                               + 0.005353579108) * y - 0.002141268741) * y
                               + 0.000535310849) * y + 0.999936657524;
            }
        }
        return z > 0.0 ? ((x + 1.0) * 0.5) : ((1.0 - x) * 0.5);
    }
	double BIGX = 20.0;                  /* max value to represent exp(x) */

    public double ex(double x) 
	{
        return (x < -BIGX) ? 0.0 : Math.exp(x);
    }   

	public double pochisq(double x,int df) 
	{
        double a, y=0, s;
        double e, c, z;
        boolean even;                     /* True if df is an even number */

        double LOG_SQRT_PI = 0.5723649429247000870717135; /* log(sqrt(pi)) */
        double I_SQRT_PI = 0.5641895835477562869480795;   /* 1 / sqrt(pi) */
        
        if (x <= 0.0 || df < 1) {
            return 1.0;
        }
        
        a = 0.5 * x;
		if((df%2)==0)
			even=true;
		else
			even =false;
		// even = !(new Boolean(df & 1));
        if (df > 1) 
		{
            y = ex(-a);
        }
        s = (even ? y : (2.0 * poz(-Math.sqrt(x))));
        if (df > 2) {
            x = 0.5 * (df - 1.0);
            z = (even ? 1.0 : 0.5);
            if (a > BIGX) {
                e = (even ? 0.0 : LOG_SQRT_PI);
                c = Math.log(a);
                while (z <= x) {
                    e = Math.log(z) + e;
                    s += ex(c * z - a - e);
                    z += 1.0;
                }
                return s;
            } else {
                e = (even ? 1.0 : (I_SQRT_PI / Math.sqrt(a)));
                c = 0.0;
                while (z <= x) {
                    e = e * (a / z);
                    c = c + e;
                    z += 1.0;
                }
                return c * y + s;
            }
        } else {
            return s;
        }
    }

	public double chi(double p,int df) 
	{

        double CHI_EPSILON = 0.000001;   /* Accuracy of critchi approximation */
        double CHI_MAX = 99999.0;        /* Maximum chi-square value */
        double minchisq = 0.0;
        double maxchisq = CHI_MAX;
        double chisqval;
        
        if (p <= 0.0) {
            return maxchisq;
        } else {
            if (p >= 1.0) {
                return 0.0;
            }
        }
        
        chisqval = df / Math.sqrt(p);    /* fair first value */
        while ((maxchisq - minchisq) > CHI_EPSILON) {
            if (pochisq(chisqval, df) < p) {
                maxchisq = chisqval;
            } else {
                minchisq = chisqval;
            }
            chisqval = (maxchisq + minchisq) * 0.5;
        }
        return chisqval;
    }
	//*********************************************

	public String tiempoEmpleado(Calendar date1,Calendar date2)
	{
		long dif=date2.getTimeInMillis()-date1.getTimeInMillis();
		String tiempo="";
		double ti=(new Double(dif)).doubleValue();
		ti=ti/1000;
		if(ti<=60)
		{
			tiempo="Tiempo empleado: "+ti+" segundos";
		}
		else
		{
			ti=ti/60;
			if(ti<=60)
			{
				tiempo="Tiempo empleado: "+ti+" minutos";
			}
			else
			{
				ti=ti/60;
				tiempo="Tiempo empleado: "+ti+" horas";
			}
		}
		return tiempo;
	}



	//MEtodos para la generacion de las tablas de probabilidad
	public Double[][] getTable(Node node)
	{
		Double[][] table;
		//si es un nodo raiz, entonces la tabla esde una solo renglon
		if(node.parents.size()>0)
		{
			int temp=1;
			//la tabla de probabilidades tendra tantos renglones como combinaciones
			//de los valores de sus padres
			for (int i=0;i<node.parents.size();i++)
			{
				temp=temp*((Node)node.parents.elementAt(i)).values.size();
			}
			table=new Double[temp][node.values.size()];
			double prob=1.0/((double)table[0].length);
			for (int i=0;i<table.length;i++)
			{
				for (int j=0;j<table[i].length;j++)
				{
					table[i][j]=new Double(prob);
				}
			}
		}
		else
		{
			table=new Double[1][node.values.size()];
			//asignar probabilidades iguales
			double prob=1.0/((double)table[0].length);
			for (int i=0;i<table.length;i++)
			{
				table[0][i]=new Double(prob);
			}
		}
		return table;
	}

		/*revisa los datos para obtener los valores posibles de las variables
	*/
	public Red getValues(Vector casos, Red red)
	{

		int con1=0;
		Node temp=red.inicio;
		do
		{
			temp.values=new Vector();
			for(int i=0;i<casos.size();i++)
			{
				if(!miembro(((Object[])casos.elementAt(i))[con1],temp.values))
				{
					temp.values.addElement(((Object[])casos.elementAt(i))[con1]);
				}
			}
			if(temp.values.size()==1)
				temp.values.addElement("0");
			//System.out.println(""+temp.values.size());
			temp.table=getTable(temp);
			temp=temp.sig;
			con1++;
		}while(temp!=null);
		return red;
	}
}
