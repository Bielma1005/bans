package bansy21.Algoritmos;

import java.util.Vector;
import bansy21.Utilerias.*;
import bansy21.Red.*;

public class NaiveBayes 
{
	public Red red;
	//objeto para accesar la utilerias
	public Utilerias util;
	//objeto para obtener informacion mutua
	public ProbInf pro;
	//clase
	public Node varDep;
	public NaiveBayes()
	{
		red = new Red();
		
		util=new Utilerias();
		pro=new ProbInf();
	}
	public Red getRedNaive(Node in)
	{
		red.inicio=in;
		Node temp=red.inicio;
		red.inArco=new Arco();

		
		Arco tempA=red.inArco;		
		do
		{		
			if(!temp.equals(varDep))
			{
				if (tempA.from==null)
				{
					tempA.from=varDep;
					tempA.to=temp;
				}
				else
				{
					tempA.sig=new Arco();
					tempA.sig.from=varDep;
					tempA.sig.to=temp;
					tempA=tempA.sig;
				}
				//temp.tipoNodo=TipoNodo.EVIDENCIA;
			}
			else
			{
				//temp.tipoNodo=TipoNodo.CONSULTA;
			}
			temp=temp.sig;
		}
		while (temp!=null);
		red=pro.probabilityTables(red);
		red.varDep=varDep;
		
		/*
		//red multiconectada
		Arco tempA=red.inArco;
		do
		{
			Node temp2=temp.sig;
			do
			{
				if (tempA.from==null)
				{
					tempA.from=temp;
					tempA.to=temp2;
				}
				else
				{
					tempA.sig=new Arco();
					tempA.sig.from=temp;
					tempA.sig.to=temp2;
					tempA=tempA.sig;
				}
				temp2=temp2.sig;
			}
			while (temp2!=null);			
			temp=temp.sig;
		}
		while (temp.sig!=null);
		red=pro.probabilityTables(red);
		red.varDep=varDep;
		*/
		return red;
	}
}