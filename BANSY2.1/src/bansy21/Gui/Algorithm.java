package bansy21.Gui;

import bansy21.*;
import bansy21.Red.*;
import bansy21.Utilerias.*;
import bansy21.frmPrincipal;

public class Algorithm extends Thread
{
	public Node inicio;
	public Arco inArco;
	public String varDepName;
	public Node varDep=null;
	public int casos;
	public double alfa;
	public Numero nFormat = new Numero();

	//objeto para accesar la utilerias
	public Utilerias util;
	//objeto para obtener informacion mutua
	public ProbInf pro;	
	public Red red;
	public String baseName;
	
	//herramioenta que muestra el progreso del algoritmo
	public Progress pMonitor=null;

	public frmPrincipal parent=null;

	public Algorithm()
	{
		util=new Utilerias();
		pro=new ProbInf();
	}

	public void incrementProgress()
	{
		if (pMonitor!=null)
		{
			pMonitor.incrementProgress();
		}
	}

	public void setNote(String note)
	{
		if (pMonitor!=null)
		{
			pMonitor.setNote(note);
		}
	}

	public void setIndeterminate(boolean b)
	{
		if (pMonitor!=null)		
		{
			pMonitor.setIndeterminate(b);
		}
	}
	public void setAtMax()
	{
		if (pMonitor!=null)		
		{
			pMonitor.setAtMax();
		}
	}

	public void println(String s)
	{
		if (pMonitor!=null)
		{
			pMonitor.println(s);
		}
	}

	public void setDefaultCursor()
	{
		pMonitor.setDefaultCursor();
	}

	public void setWaitCursor()
	{
		pMonitor.setWaitCursor();
	}

	public  void createRed(Node in)
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
			
			//System.out.println("Varp: "+temp.name);
			temp=temp.sig;
		}while(temp!=null);
		red.varDep=varDep;
		pro.varDep=varDep;
		inicio=red.inicio;
	}
        
        public  void createRedConectadaNodirigida(Node in)
	{
            createRed(in);
            //Conectar toda la red con arcos no dirigidos
            Node fromTemp=inicio;
            Node toTemp=null;
            Arco tempArco=null;
            while(fromTemp!=null)
            {
                toTemp=fromTemp.sig;
                while(toTemp!=null)
                {
                    if(tempArco!=null)
                    {
                        //Crear el arco que se agregará;
                        tempArco.sig=new Arco();
                        tempArco=tempArco.sig;
                    }
                    else{
                        red.inArco=new Arco();
                        tempArco=red.inArco;
                    }                    
                    tempArco.from=fromTemp;
                    tempArco.to=toTemp;
                    tempArco.directed=false;                   
                    toTemp=toTemp.sig;
                }
                fromTemp=fromTemp.sig;
            }
            util.inArco=red.inArco;          
         
	}
}
