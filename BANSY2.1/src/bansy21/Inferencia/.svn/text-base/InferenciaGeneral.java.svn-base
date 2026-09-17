package bansy21.Inferencia;

import bansy21.Red.*;
import bansy21.Utilerias.*;
import bansy21.Gui.*;

import java.util.Vector;

public class InferenciaGeneral extends Algorithm
{
	public static void main(String[] args) 
	{
		System.out.println("Hello World!");
	}
	
	public JointProbability jp=null;
	
	public InferenciaGeneral()
	{
		super();
	}

	public void run()
	{
		calculateConditionalProbability(red);
	}

	public void calculateConditionalProbability(Red r)
	{
		Vector vj=new Vector();
		Vector vc=new Vector();
		Node temp=r.inicio;
		while (temp!=null)
		{
			//System.out.println("sgfhfgb  "+temp.name);
			if (temp.tipoNodo.equals(TipoNodo.EVIDENCIA))
			{
				vc.addElement(temp);
			}
			else if (temp.tipoNodo.equals(TipoNodo.CONSULTA))
			{
				vj.addElement(temp);
			}		
			temp=temp.sig;
		}
		
		Node[] js = new Node[vj.size()];
		vj.copyInto(js);
		Node[] cs=new Node[vc.size()];
		vc.copyInto(cs);
		println("Variables de consulta: "+js.length);
		println("Variables evidencia: "+cs.length);
		String sal="P(";
		for (int i=0;i<js.length;i++)
		{
			sal+=js[i].name;
			sal+=" = "+js[i].values.elementAt(js[i].selectedValue);
			if (i!=(js.length-1))
			{
				sal+=", ";
			}
		}
		if(cs.length>0)
			sal+=" | ";
	
		for (int i=0;i<cs.length;i++)
		{
			sal+=cs[i].name;
			sal+=" = "+cs[i].values.elementAt(cs[i].selectedValue);
			if (i!=(cs.length-1))
			{
				sal+=", ";
			}
		}
	
		sal+=") = ";
		 
		if (js.length>0)
		{
			double prob=jp.calculateConditionalProbability(js,cs);
			sal+=""+prob;
			println(sal);
		}
	}	

	public double calculateConditionalProbability()
	{
		Red r=red;
		Vector vj=new Vector();
		Vector vc=new Vector();
		Node temp=r.inicio;		
		while (temp!=null)
		{
			//System.out.println("sgfhfgb  "+temp.name);
			if (temp.tipoNodo.equals(TipoNodo.EVIDENCIA))
			{
				vc.addElement(temp);
			}
			else if (temp.tipoNodo.equals(TipoNodo.CONSULTA))
			{
				vj.addElement(temp);
			}
			temp=temp.sig;
		}
		
		Node[] js = new Node[vj.size()];
		vj.copyInto(js);
		Node[] cs=new Node[vc.size()];
		vc.copyInto(cs);
		double prob=-1;
		if (js.length>0)
		{
			prob=jp.calculateConditionalProbability(js,cs);			
		}
		return prob;
	}	
}
