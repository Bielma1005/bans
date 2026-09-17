package bansy21.Utilerias;

import bansy21.Red.*;

import java.util.Vector;
/*
	Esta clase es la auxiliar para el calculo de la informacion mutua	
*/

public class ProbInf
{
	public Vector casos;
	public Node varDep;
	public double infVarDep=0;
	public ProbInf()
	{
	}
	//*******************************************************************
  //se calcula la informacion mutua con la variable dependiente y la variable dada
	public double mi(Node n)
	{	
		double inf=0;
		for(int i=0;i<varDep.values.size();i++)
		{
			for(int j=0;j<n.values.size();j++)
			{
				Node[] var=new Node[2];
				Object[] values=new Object[2];
				var[0]=varDep;
				var[1]=n;
				values[0]=varDep.values.elementAt(i);
				values[1]=n.values.elementAt(j);
				double[] reg=probC2(var,values);
				double px=reg[0];
				double py=reg[1];
				double pc=reg[2];

				if(pc!=0 && px!=0 && py!=0)
					inf+=(pc*(Math.log((pc)/(px*py))));
			}
		}
		return inf;
	}
	 //se calcula la informacion mutua con la variable dependiente y la variable dada
	public double miF(Node n)
	{
		//para cada registroen la base		
		double[][] frec=new double[varDep.values.size()*n.values.size()][3];
		double[] frecX=new double[varDep.values.size()];
		boolean first=true;
		infVarDep=0;
		double inf=0;
		double cas=(double)casos.size();
		for (int c=0;c<cas;c++)
		{
			int con=0;
			for(int i=0;i<varDep.values.size();i++)//x
			{		
				if(first)
				{
					frecX[i]=0;
				}
				if(((Object[])casos.elementAt(c))[varDep.column].toString().equals(varDep.values.elementAt(i).toString()))
				{
					frecX[i]++;
					//px++;
				}
				for(int j=0;j<n.values.size();j++)//y
				{
					if(first)
					{
						frec[con][0]=0;
						frec[con][1]=0;
						frec[con][2]=0;
					}
					if(((Object[])casos.elementAt(c))[varDep.column].toString().equals(varDep.values.elementAt(i).toString()))
					{
						frec[con][0]++;
						//px++;
					}
					if(((Object[])casos.elementAt(c))[n.column].toString().equals(n.values.elementAt(j).toString()))
					{
						frec[con][1]++;
						//py++;
					}
					if(((Object[])casos.elementAt(c))[varDep.column].toString().equals(varDep.values.elementAt(i).toString()) && ((Object[])casos.elementAt(c))[n.column].toString().equals(n.values.elementAt(j).toString()))
					{
						frec[con][2]++;
						//pc++;
					}
					con++;
				}
			}
			first=false;
		}
		for(int i=0;i<frecX.length;i++)
		{
      double px=(double)frecX[i]/cas;
			if(px!=0)
				infVarDep+=px*(Math.log(px));
		}
		double inf2=0;
		for(int i=0;i<frec.length;i++)
		{
			double px=(double)frec[i][0]/cas;
			double py=(double)frec[i][1]/cas;
			double pc=(double)frec[i][2]/cas;
			if(pc!=0 && px!=0 && py!=0)
			{
				inf+=(pc*(Math.log((pc)/(px*py))));
				inf2+=((double)frec[i][2])*(Math.log(((cas)*((double)frec[i][2]))/((double)frec[i][0]*(double)frec[i][1])));
			}
		}
	//	System.out.println("inf= "+inf);
//		System.out.println("inf2= "+(inf2*2));
		return inf;
	}
		//calcula la informacion mutua condicional de la varible a con respecto a la variable depemdiente
	//dado el conjunto c
	public double mcIF(Node var, Node[] nodos)
	{
		infVarDep=0;
		double inf=0;		
		//para cada registro de la base
		double cas=(double)casos.size();		
		int comb=1;
		for (int i=0;i<nodos.length;i++)
		{
			comb=comb*nodos[i].values.size();
		}
		comb=comb*var.values.size()*varDep.values.size();
	//	System.out.println("              comb "+comb+"  var "+var.values.size()+"  varDep "+varDep.values.size());
		//CONTADRO DE LAS FRECUENCIAS
		double[][] frec=new double[comb][3];
		//contador de los casos de cada conjunto
		double[] cases=new double[comb];

		double[] frecX=new double[comb/var.values.size()];
		double[] casesX=new double[frecX.length];
		
		for (int c=0;c<cas;c++)
		{		
			int con=0;
			int con2=0;
			//sumar para todos los valores popsibles de la variable dependiente
			for(int i=0;i<varDep.values.size();i++)
			{
			
				//para todos los valores de la variable a condionar
				for(int j=0;j<var.values.size();j++)
				{				
					//hacer todos las combinaciones del, conjunto condicionante

					int[] contador=new int[nodos.length];//en el se pondran los valores que
					//van hasta el momento para cada variable
					for(int k=0;k<contador.length;k++)
						contador[k]=0;			
					while(contador[0]<nodos[0].values.size())
					{			
						Object[] values=new Object[nodos.length];
						for(int k=(nodos.length-1);k>=0;k--)
						{
							if(contador[k]<nodos[k].values.size())
							{
								values[k]=nodos[k].values.elementAt(contador[k]);
								if(k==(nodos.length-1))
									contador[k]++;
							}
							else
							{
								if(k!=0)
								{
									contador[k]=0;
									values[k]=nodos[k].values.elementAt(contador[k]);
									if(k==(nodos.length-1))
										contador[k]++;
									contador[k-1]++;
								}
							}
						}								
						if(values[0]!=null)
						{
							//casos  (c)  base de datos
							//varDep (i)  variable dependiente
							//var    (j)  variable con la que se esta tratando de encontrar la informacion mutua condicional
							//nodos  (l)  conjunto condicionante
							//values (l)  combinacion actual de los valores del conjunto condicionante
							boolean key=true;
							//ver si el en el registro se cumplen los valores de las variables condicionales
							for(int l=0;l<nodos.length;l++)
							{
								if(!(((Object[])casos.elementAt(c))[nodos[l].column].toString().equals(values[l].toString())))
								{
									key=false;
									break;
								}
							}


							if(key)
							{//si es un registro en el que se cumplen llos valores de las variables condicionales
								cases[con]++;//aumentar el numero de casos (conjunto condicional)								
								if(j==0)
									casesX[con2]++;
								
								if(((Object[])casos.elementAt(c))[var.column].toString().equals(var.values.elementAt(j).toString()) && ((Object[])casos.elementAt(c))[varDep.column].toString().equals(varDep.values.elementAt(i).toString()))
								{
									frec[con][2]++;
									//frec0++;//ocurencia de todas
								}
								if(((Object[])casos.elementAt(c))[var.column].toString().equals(var.values.elementAt(j).toString()))
								{
									frec[con][0]++;
									//frec++;//ocurrencia de una variable
								}
								if(((Object[])casos.elementAt(c))[varDep.column].toString().equals(varDep.values.elementAt(i).toString()))
								{
									frec[con][1]++;
									if(j==0)
										frecX[con2]++;
									//frec2++;//ocurrencia de una variable
								}								
							}
							con++;						
							if(j==0)
								con2++;
						}						
					}				
				}
			}		
			//System.out.println("comb  "+comb+"  "+con);
		}
		for(int i=0;i<frecX.length;i++)
		{
			if (cas>0 && casesX[i]>0)
			{			
	      double pc=(double)frecX[i]/cas;
				double pxc=(double)frecX[i]/casesX[i];
				if(pxc!=0)
					infVarDep+=pc*(Math.log(pxc));
			}
		}
		for(int i=0;i<frec.length;i++)
		{
			double pxc=(double)frec[i][0]/cases[i];//probabilidad de x dado el conjunto condicionante
			double pyc=(double)frec[i][1]/cases[i];//probabilidad de y dado el conjunto condicionante
			double pcc=(double)frec[i][2]/cases[i];//probabilidad conjunta de x y y dado el conjunto condicionante		
			double pc=(double)frec[i][2]/cas;//probabilidad conjunta			
			if(pcc!=0 && pxc!=0 && pyc!=0 && cases[i]!=0)
				inf+=(pc*(Math.log((pcc)/(pxc*pyc))));
		}
		return inf;
	}
	//calcula la informacion mutua condicional de la varible a con respecto a la variable depemdiente
	//dado el conjunto c
	public double mcI(Node var, Node[] nodos)
	{
		double inf=0;
		//sumar para todos los valores popsibles de la variable dependiente
		for(int i=0;i<varDep.values.size();i++)
		{
			//para todos los valores de la variable a condionar
			for(int j=0;j<var.values.size();j++)
			{				
				//hacer todos las combinaciones del, conjunto condicionante

				int[] contador=new int[nodos.length];//en el se pondran los valores que
				//van hasta el momento para cada variable
				for(int k=0;k<contador.length;k++)
					contador[k]=0;
				int cont=0;
				while(contador[0]<nodos[0].values.size())
				{			
					Object[] values=new Object[nodos.length];
					for(int k=(nodos.length-1);k>=0;k--)
					{
						if(contador[k]<nodos[k].values.size())
						{
							values[k]=nodos[k].values.elementAt(contador[k]);
							if(k==(nodos.length-1))
								contador[k]++;
						}
						else
						{
							if(k!=0)
							{
								contador[k]=0;
								values[k]=nodos[k].values.elementAt(contador[k]);
								if(k==(nodos.length-1))
									contador[k]++;
								contador[k-1]++;
							}
						}
					}			
					if(values[0]!=null)
					{					
						//mandar calcular la probalidad conjunta(p(x,y,Z))
						Node[] variables=new Node[2+nodos.length];
						Object[] varValues=new Object[2+nodos.length];
						variables[0]=varDep;
						variables[1]=var;
						varValues[0]=varDep.values.elementAt(i);
						varValues[1]=var.values.elementAt(j);
						int con=0;
						for(int k=2;k<(2+nodos.length);k++)
						{
							variables[k]=nodos[con];
							varValues[k]=values[con];
							con++;
						}
						//probabilidadf conjunta						
				//		double pc=probC(variables,varValues);

						variables=new Node[2];
						varValues=new Object[2];
						variables[0]=varDep;
						variables[1]=var;
						varValues[0]=varDep.values.elementAt(i);
						varValues[1]=var.values.elementAt(j);

						//probailidad conjunta condicional
					//	double pcc=probCondi(variables,varValues,nodos,values);

						double[] reg=probCondi(varDep, varDep.values.elementAt(i), var, var.values.elementAt(j), nodos, values); 
						double pcx=reg[0];
						double pcy=reg[1];
						double pcc=reg[2];
						double pc=reg[3];
						if(pcc!=0 && pcx!=0 && pcy!=0)
							inf=inf+(pc*Math.log(pcc/(pcx*pcy)));
						//else
						//	System.out.println("pc: "+pc+" pcc: "+pcc);//+" pcx: "+pcx+" pcy: "+pcy);
					}
				}				
			}
		}
		return inf;
	}

	//encuentra las combinaciones de los valores de las variables dadas
  public void combinaciones(Node[] nodos)
  {
		int[] contador=new int[nodos.length];//en el se pondran los valores que
		//van hasta el momento para cada variable
		for(int i=0;i<contador.length;i++)
		{
			contador[i]=0;
			System.out.println("value "+nodos[i].values.size());
		}
		int cont=0;
		while(contador[0]<nodos[0].values.size())
		{			
			Object[] values=new Object[nodos.length];
			for(int i=(nodos.length-1);i>=0;i--)
			{
				if(contador[i]<nodos[i].values.size())
				{
					values[i]=nodos[i].values.elementAt(contador[i]);
					if(i==(nodos.length-1))
						contador[i]++;
				}
				else
				{
					if(i!=0)
					{
						contador[i]=0;
						values[i]=nodos[i].values.elementAt(contador[i]);
						if(i==(nodos.length-1))
							contador[i]++;
						contador[i-1]++;
					}
				}
			}			
			if(values[0]!=null)
			{
				cont++;
			}			
		}    
	}

//*******************************************************************

//--------------------------------------------------------------------
//                     probabilidades

  private double probM(Node n, Object value)
	{
		int con=0;
		int noCasos=casos.size();
		for (int i=0;i<noCasos;i++)
		{
			if(((Object[])casos.elementAt(i))[n.column].toString().equals(value.toString()))
				con++;
		}
		return ((double)con/(double)noCasos);
	}

	//calcula laq probalidad conjunta dados los nodos y sus valores
	private double probC(Node[] n, Object[] values)
	{
		double con1=0;
		double noCasos=casos.size();
		for(int i=0;i<noCasos;i++)
		{
			int con=0;
			boolean seguir=true;
			while(seguir && con<n.length)
			{
				if(!((Object[])casos.elementAt(i))[n[con].column].toString().equals(values[con].toString()))
					seguir=false;
				con++;
			}
			if(seguir)
				con1++;
		}
		return (double)con1/(double)noCasos;
	}
	//calcula la probabilidad marginal de las dos variables del arreglo, y su probabilidad mutua
	private double[] probC2(Node[] n, Object[] values)
	{		
		double px=0,py=0,pc=0;
		double noCasos=casos.size();
		for(int i=0;i<noCasos;i++)
		{
			if(((Object[])casos.elementAt(i))[n[0].column].toString().equals(values[0].toString()))
			{
				px++;
			}
			if(((Object[])casos.elementAt(i))[n[1].column].toString().equals(values[1].toString()))
			{
				py++;
			}
			if(((Object[])casos.elementAt(i))[n[0].column].toString().equals(values[0].toString()) && ((Object[])casos.elementAt(i))[n[1].column].toString().equals(values[1].toString()))
			{
				pc++;
			}
		}
		double[] reg={px/noCasos,py/noCasos,pc/noCasos};
		return reg;
	}


	//calcula la probabilidad condicional de las variables var, dadas las variables del conjunto varCond
	private double probCondi(Node[] var,Object[] varValues, Node[] varCond, Object[] varCondValues)
	{
		double frec=0, cases=0;
		int numRegistros=casos.size();
		for(int i=0;i<numRegistros;i++)
		{
			boolean key=true;
			//ver si el en el registro se cumplen los valores de las variables condicionales
			for(int j=0;j<varCond.length;j++)
			{
				if(!(((Object[])casos.elementAt(i))[varCond[j].column].toString().equals(varCondValues[j].toString())))
				{
					key=false;
					break;
				}
			}
			if(key)
			{//si es un registro en el que se cumplen llos valores de las variables condicionales
				cases++;//aumentar el numero de casos (conjunto condicional)
				boolean key2=true;
				for(int j=0;j<var.length;j++)//verificar si en el resgistro son iguales las varibles
				{
					if(!(((Object[])casos.elementAt(i))[var[j].column].toString().equals(varValues[j].toString())))//verificar si 
					{
						key2=false;
					}
				}
				if(key2)
				{
					frec++;
				}
			}
		}			
		return (double)frec/(double)cases;
	}

	//calcula la probabilidad conjunta de todas las variables, asi como la probablidad mutusa condicional y la probabilidad condicional marginal 
	private double[] probCondi(Node var,Object varValue, Node var2, Object varValue2,  Node[] varCond, Object[] varCondValues)
	{
		double frec=0, cases=0, frec2=0,frec0=0,frec00=0;
		int numRegistros=casos.size();
		for(int i=0;i<numRegistros;i++)
		{
			boolean key=true;
			//ver si el en el registro se cumplen los valores de las variables condicionales
			for(int j=0;j<varCond.length;j++)
			{
				if(!(((Object[])casos.elementAt(i))[varCond[j].column].toString().equals(varCondValues[j].toString())))
				{
					key=false;
					break;
				}
			}
			if(key)
			{//si es un registro en el que se cumplen llos valores de las variables condicionales
				cases++;//aumentar el numero de casos (conjunto condicional)
				if(((Object[])casos.elementAt(i))[var.column].toString().equals(varValue.toString()) && ((Object[])casos.elementAt(i))[var2.column].toString().equals(varValue2.toString()))
				{
					frec0++;//ocurencia de todas
				}
				if(((Object[])casos.elementAt(i))[var.column].toString().equals(varValue.toString()))
				{
					frec++;//ocurrencia de una variable
				}
				if(((Object[])casos.elementAt(i))[var2.column].toString().equals(varValue2.toString()))
				{
					frec2++;//ocurrencia de una variable
				}
			}
		}
		double[] reg={frec/cases, frec2/cases, frec0/cases, frec0/numRegistros};
		return reg;
	}

	/**
	*Calcula la probabilidad condicional de la variable, dado el conjunto condicionante
	*/
	private double probCondi(Node var,Object varValue, Node[] varCond, Object[] varCondValues)
	{
		
		double frec=0, cases=0;
		int numRegistros=casos.size();
		for(int i=0;i<numRegistros;i++)
		{
			boolean key=true;
			//ver si el en el registro se cumplen los valores de las variables condicionales
			for(int j=0;j<varCond.length;j++)
			{
				if(!(((Object[])casos.elementAt(i))[varCond[j].column].toString().equals(varCondValues[j].toString())))
				{
					key=false;
					break;
				}
			}
			if(key)
			{//si es un registro en el que se cumplen llos valores de las variables condicionales
				cases++;//aumentar el numero de casos (conjunto condicional)				
				if((((Object[])casos.elementAt(i))[var.column].toString().equals(varValue.toString())))//verificar si 
				{
					frec++;
				}			
			}
		}	
		if(cases>0)
			return (double)frec/(double)cases;
		else
			return 0.0;
	}
	
//--------------------------------------------------------------------
	

	//MEtodos para la generacion de las tablas de probabilidad
	public double[][] getTable(Node node)
	{
		double[][] table;
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
			table=new double[temp][node.values.size()];
			double prob=1.0/((double)table[0].length);
			for (int i=0;i<table.length;i++)
			{
				for (int j=0;j<table[i].length;j++)
				{
					table[i][j]=prob;
				}
			}
		}
		else
		{
			table=new double[1][node.values.size()];
			//asignar probabilidades iguales
			double prob=1.0/((double)table[0].length);
			for (int i=0;i<table.length;i++)
			{
				table[0][i]=prob;
			}
		}
		return table;
	}
	
	/**
	*	Calcula las tablas de probabilidades de los nodos
	*/
	public Red probabilityTables(Red red)
	{		
		Node inicio=red.inicio;
		Node temp=inicio;
		Arco inArco=red.inArco;	
		varDep=red.varDep;
		//llenar el vector de padres de cada arco
		inicio=fillParents(inicio,inArco);
		/*Echar a andar la maquina de las vegas, para generar las combinaciones, solo en caso de 
		*de que el nodo no sea raiz, en caso contrario, calcular sus probabilidades marginales
		*/

		//para cada nodo	
		
		while (temp!=null)
		{
			//inicializar la tabla de probabilidades del nodo			
			int valores=temp.values.size();			
			if(temp.parents.size()>0)
			{				
				temp.table=new Double[getParentsComb(temp)][valores];				
				Node[] nodos=new Node[temp.parents.size()];
				temp.parentComb= new String[temp.table.length][nodos.length];
				temp.parents.copyInto(nodos);
				//Maquina de combinaciones
				int[] contador=new int[nodos.length];//en el se pondran los valores que van hasta el momento de cada variable			
				for(int i=0;i<contador.length;i++)
					contador[i]=0;			
				int cont=0;
				while(contador[0]<nodos[0].values.size())
				{			
					Object[] values=new Object[nodos.length];
					for(int i=(nodos.length-1);i>=0;i--)
					{
						if(contador[i]<nodos[i].values.size())
						{
							values[i]=nodos[i].values.elementAt(contador[i]);
							if(i==(nodos.length-1))
								contador[i]++;
						}
						else
						{
							if(i!=0)
							{
								contador[i]=0;
								values[i]=nodos[i].values.elementAt(contador[i]);
								if(i==(nodos.length-1))
									contador[i]++;
								contador[i-1]++;
							}
						}
					}			
					if(values[0]!=null)//aqui ya se tiene la siguiente combinación
					{
						for (int i=0;i<valores;i++)
						{
							//calcular la probabilidad condicional de cada uno de los valores de la
							//variable en cuestión, dada la combinación actual de los padres
							temp.table[cont][i]=new Double(probCondi(temp,temp.values.elementAt(i),nodos,values));
						}
						for (int i=0;i<values.length;i++)
						{
							//guardar la combinación de los padres
							temp.parentComb[cont][i]=(String)values[i];							
						}
						cont++;
					}
				}
			}
			else
			{
				temp.table=new Double[1][valores];
				for (int i=0;i<valores;i++)
				{
					//calcular la probabilidad marginal para cada uno de los valores posibles de la variable					
					temp.table[0][i]=new Double(probM(temp,temp.values.elementAt(i)));
				}
			}
			temp=temp.sig;
		}
		return red;
	}

	/**
	*	Obtiene los padres de cada nodo
	*/
	public Node fillParents(Node inicio, Arco inArco)
	{
		Node temp=inicio;
		while (temp!=null)
		{
			Arco tempA=inArco;
			temp.parents=new Vector();
			while (tempA!=null)
			{
				if (tempA.to==null)
				{
					System.out.println("null");
				}
				if(tempA.to.name.equals(temp.name))
				{					
					temp.parents.addElement(tempA.from);				
				}
				tempA=tempA.sig;
			}			
			temp=temp.sig;
		}
		return inicio;
	}

	/**
	*calcula el numero de combinaciones de los padre
	*/
	public int getParentsComb(Node node)
	{
		int comb=1;
		//la tabla de probabilidades tendra tantos renglones como combinaciones
		//de los valores de sus padres
		for (int i=0;i<node.parents.size();i++)
		{
			comb=comb*((Node)node.parents.elementAt(i)).values.size();
		}
		return comb;
	}


	/**
	* construye la tabla de probabilidad es conjunta
	*/
	public void makeTDC()
	{
		/*
			la tabla se construye a partir de la red utilizando el producto de las probabildiades condicionales,
			sumando lo necesario
			otra forma de hacerlo es calcularla directamente de la base de datos, pero a lo mejor 
			seria mas tardado

		*/
	}

/*
	public double mcI(Node var, Node[] nodos, Object[] values)
	{
		double inf=0;
		//sumar para todos los valores popsibles de la variable dependiente
		for(int i=0;i<varDep.values.size();i++)
		{
			//para todos los valores de la variable a condionar
			for(int j=0;j<var.values.size();j++)
			{
					if(values[0]!=null)
					{					
						//mandar calcular la probalidad conjunta(p(x,y,Z))
						Node[] variables=new Node[2+nodos.length];
						Object[] varValues=new Object[2+nodos.length];
						variables[0]=varDep;
						variables[1]=var;
						varValues[0]=varDep.values.elementAt(i);
						varValues[1]=var.values.elementAt(j);
						int con=0;
						for(int k=2;k<(2+nodos.length);k++)
						{
							variables[k]=nodos[con];
							varValues[k]=values[con];
							con++;
						}
						//probabilidadf conjunta						
				//		double pc=probC(variables,varValues);

						variables=new Node[2];
						varValues=new Object[2];
						variables[0]=varDep;
						variables[1]=var;
						varValues[0]=varDep.values.elementAt(i);
						varValues[1]=var.values.elementAt(j);

						//probailidad conjunta condicional
					//	double pcc=probCondi(variables,varValues,nodos,values);

						double[] reg=probCondi(varDep, varDep.values.elementAt(i), var, var.values.elementAt(j), nodos, values); 
						double pcx=reg[0];
						double pcy=reg[1];
						double pcc=reg[2];
						double pc=reg[3];
						if(pcc!=0 && pcx!=0 && pcy!=0)
							inf=inf+(pc*Math.log(pcc/(pcx*pcy)));
						//else
						//	System.out.println("pc: "+pc+" pcc: "+pcc);//+" pcx: "+pcx+" pcy: "+pcy);
					}
				}				
			}
		}
		return inf;
	}
	*/
}