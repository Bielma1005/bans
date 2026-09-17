package bansy21.Utilerias;
import java.util.Vector;
import java.util.StringTokenizer;
import java.io.*;
import bansy21.Red.*;
import java.awt.Image;

public class Archivo
{
	public Vector casos;
	public Node varDep;
	public int numVariables;
	public int registrosInvalidos;
// -----------------------------------------------------------------------------
	public void leeE(String directory,String file)throws IOException
	{
		String nombre=directory+file;
		String lin, deli, next;
		Vector nodos=new Vector();
		deli = new String(",	 ");		
		FileInputStream inputFile = new FileInputStream(nombre);
	  BufferedReader archivo = new BufferedReader (new InputStreamReader(inputFile)); 	 		    	    
	  System.out.println("Estoy leyendo datos..");

		while ((lin=archivo.readLine()) != null)
		{
			lin=lin.replaceAll("	","");
			 String[] reg=lin.split(" ");
			 if(reg[0].equals("node") || reg[0].equals("parents"))
				 nodos.addElement(lin);
		}
		
		
		FileWriter f=null;
        PrintWriter sal=null;
		String tem;
		tem = new String("");
		try 
		{ 
   			f = new FileWriter("Arcos"+directory);
			sal = new PrintWriter(f);
		}catch (IOException e) 
           {System.out.println(""+e);}
		/*

		for (int i=0;i<nodos.size();i++)
		{
			sal.println((String)nodos.elementAt(i));
		}
		*/

		for (int i=0;i<nodos.size();i=i+2)
		{
			String var=(String)nodos.elementAt(i);
			//System.out.println(var);
//			var=(((var.replaceAll("node","")).replaceAll(" ","")).replaceAll("{",""));
			var=var.replaceAll("node","");
			var=var.replace('{',' ');
			var=var.replaceAll(" ","");	

			//System.out.println(var+"2");
			String padres=(String)nodos.elementAt(i+1);
			padres = ((((((padres.replaceAll("parents","")).replace('(',' ')).replace(')',' ')).replace('=',' ')).replace(';',' ')).replaceAll(" ",""));
			String[] pad=padres.split(",");
			for (int j=0;j<pad.length;j++)
			{
				if(!pad[j].equals(""))
					sal.println(pad[j]+","+var);
			}
		}
	
		try 
		{
			 sal.close( );
			 f.close( );
			System.out.println("Escrito:  Arcos"+file);
		}catch (IOException e) 
		   {System.out.println("Error");};

	}
// -----------------------------------------------------------------------------
	public Node leer(String nombre)throws IOException
	{
        
		Node inicio=new Node();
		Node temp=inicio;
		String lin, deli, next;
		casos=new Vector();
		registrosInvalidos=0;
		deli = new String(",	 ");		
		FileInputStream inputFile = new FileInputStream(nombre);
		BufferedReader archivo = new BufferedReader (new InputStreamReader(inputFile)); 	 		    	    
		System.out.println("Estoy leyendo datos..");
		lin=archivo.readLine();
		if (lin==null || lin.trim().length()==0)
			throw new IOException("El archivo no contiene una cabecera de variables.");
		String separator=lin.indexOf('\t')>=0 ? "\\t" : ",";
		String[] headers=lin.split(separator,-1);

		int column=0;
		while (column<headers.length)
		{ 
			temp.name=headers[column].trim();
			temp.column=column;
			if(column<headers.length-1)
			{
				temp.sig=new Node();
				temp=temp.sig;
			}
			column++;			
		}
		numVariables=column;
		varDep=temp;
		
                int con=0;
                 String[] reg;
		try
		{ 
                  
		  while (  ( lin=archivo.readLine() ) != null  )
		  { 
			  if (lin.trim().length()==0)
				  continue;
					  reg=lin.split(separator,-1);
					  if (reg.length!=numVariables)
					  {
						  registrosInvalidos++;
						  continue;
					  }
					  for (int i=0;i<reg.length;i++)
						  reg[i]=reg[i].trim();
                      con++;
                      casos.addElement(reg);
		  }
		  archivo.close();
		} catch (java.lang.OutOfMemoryError e) 
                {
//                    //Out of memory
//                    datos.addElement(casos);
//                    String noNulo="1";
//                    Vector casos1=null;
//                    while(noNulo!=null)
//                    {
//                        try
//                        {
//                         datos.addElement(new Vector());
//                         
//                         while (  ( lin=archivo.readLine() ) != null )
//                         {                            
//                            reg=lin.split(",");
//                            ((Vector)datos.vectorElement(datos.size()-1)).addElement(reg);                            
//                         }
//                        } catch (java.lang.OutOfMemoryError e1) 
//                        {                            
//                            noNulo=lin;
//                        }
//                    }
                    
                }
                
                // Error
		return inicio;
	}
// -----------------------------------------------------------------------------
	public Arco leerArcos(String nombre,Node inNode)throws IOException
	{
		Arco inicio=new Arco();
		Arco temp=inicio;
		String lin, deli, next;
		deli = new String(",	 ");		
		FileInputStream inputFile = new FileInputStream(nombre);
		BufferedReader archivo = new BufferedReader (new InputStreamReader(inputFile)); 	 		    	    
                System.out.println("Estoy leyendo datos..");
	
		try
		{		
		 while ((lin=archivo.readLine()) != null)
		 {			
			 if(temp.from!=null)
			 {
				 temp.sig=new Arco();
				 temp=temp.sig;
			 }
			StringTokenizer st= new StringTokenizer(lin, deli, false);
			String from=st.nextToken();
			String to=st.nextToken();
			int con=0;
			Node Ntemp=inNode;		
			do
			{
				if(Ntemp.name.equals(from))
				{		
					temp.from=Ntemp;
					con++;
				}
				else if(Ntemp.name.equals(to))
				{
					temp.to=Ntemp;
					con++;
				}
				Ntemp=Ntemp.sig;
			}while(Ntemp!=null || con<2);
		 }		 
		}
		catch (Exception e)
		{
			//mensaje ="Error al leer el archivo";
		}		
		return inicio;
	}
// -----------------------------------------------------------------------------
	public void escribir(Vector v, String nombre)
	{
		FileWriter f=null;
    PrintWriter sal=null;
		String tem;
		tem = new String("");
		try 
		{ 
   			f = new FileWriter(nombre);
			sal = new PrintWriter(f);
		}catch (IOException e) 
           {System.out.println(""+e);}
		
		    sal.println("bayes2: "+v.elementAt(0));	
			sal.println("bayes5: "+v.elementAt(1));	
			sal.println("bayesN: "+v.elementAt(2));	
		try 
		{
			 sal.close( );
			 f.close( );
			System.out.println("Escrito:  "+nombre);
		}catch (IOException e) 
		   {System.out.println("Error");};
	}
	//variable para escribir el archivo
	private FileWriter fi=null;
// -----------------------------------------------------------------------------
	public PrintWriter openWriter(String nombre)
	{
		fi=null;
        PrintWriter sal=null;
		String tem;
		tem = new String("");
		try 
		{ 
   			fi = new FileWriter(nombre);
			sal = new PrintWriter(fi);
		}catch (IOException e) 
           {System.out.println(""+e);}

		return sal;
	}
// -----------------------------------------------------------------------------
	public void closeWriter(PrintWriter sal)
	{
		try 
		{
			 sal.close( );
			 fi.close( );			
		}catch (IOException e) 
		   {System.out.println("Error");};
	}
// -----------------------------------------------------------------------------	
	public void escribirArcos(Arco ini, String nombre)
	{
		FileWriter f=null;
        PrintWriter sal=null;		
		Arco temp=ini;		
		try 
		{ 
   			f = new FileWriter(nombre);
			sal = new PrintWriter(f);
		}catch (IOException e) 
           {System.out.println(""+e);}
		
		do
		{
			sal.println(""+temp.from.name+","+temp.to.name);
			temp=temp.sig;
		}while(temp!=null);
		  
		try 
		{
			 sal.close();
			 f.close();
			System.out.println("Escrito:  "+nombre);
		}catch (IOException e) 
		   {System.out.println("Error");};
	}
// -----------------------------------------------------------------------------
	//gurda la imagen  dada
	public void guardarGif(Image ima,String nombre)
	{
		try 
		{			
			FileOutputStream fOut = new FileOutputStream(nombre); 
			GifEncoder genc = new GifEncoder(ima, fOut);
			genc.encode(); 
			fOut.close();
		} catch( IOException e ) 
			{
				System.out.println( ""+e) ;
			}
			System.out.println("Escrito:  "+nombre);
	}
// -----------------------------------------------------------------------------

	///*********  toda esta parte esta encargada de leer el formato DNET

	public Red leerDNet(String fileName)throws IOException
	 {
		 Red red=new Red();
		 red.inicio=new Node();
		 Node temp=red.inicio;
		 red.inArco=new Arco();
		 Arco tempA=red.inArco;
		 //funcionar� como una pila
		 Vector llaves=new Vector();

		String lin, deli, next;
		deli = new String(",	 ");		
		FileInputStream inputFile = new FileInputStream(fileName);
		BufferedReader archivo = new BufferedReader (new InputStreamReader(inputFile)); 	 		    	    
	  System.out.println("Estoy leyendo datos..");
		lin=archivo.readLine();
		if (lin==null)
		{
			 lin="";
		}
		if (lin.equals("// ~->[DNET-1]->~"))//si se trata de un archivo DNET)
		{
			 try
				{	
					boolean flag=true;
					//leer el inicio de la red
					while (flag && (lin=archivo.readLine()) != null)
					{	
						String[] line=lin.split(" ");
						if (line[0].equals("dnet"))//empezar a leer la red
						{
							 red.name=line[1];
							 llaves.addElement(line[2]);
							 flag=false;
						}
					}

					//leer el comentario, si es que existe
					flag=true;
					while (flag && (lin=archivo.readLine()) != null)
					{
						 String[] line=lin.split(" ");
						 if(line[0].equals("comment"))
						 {
								int index=lin.indexOf("=");
								String sub=lin.substring(index);
								sub.replaceFirst(";","");
								red.comment+=sub;
							 while (!line[line.length-1].equals(";"))
							 {
									//leer todo el comentario, por que puede venir en varias lineas
							 }
							 flag=false;
						 }
					}
					
					//hay que ver si en el paso anterior no se lee por error un nodo, si es asi hay que poner algo que recupere esa informaci�n
					//empezar a leer los nodos
									
				}
				catch(Exception e){//error al leer datos}
				}
		}
		else
			{
			 //enviar un mensaje de error
			}		 
			return red;
		}
// -----------------------------------------------------------------------------
	public void saveDataBaseElv(String directory, Vector casos, Red red)
	{
		FileWriter f=null;
    PrintWriter sal=null;
		String tem;
		tem = new String("");
		try 
		{ 
  		f = new FileWriter(directory);
			sal = new PrintWriter(f);
		}catch (IOException e) 
           {System.out.println(""+e);}
		
    sal.println("data-base "+directory+" {");	
		sal.println("");
		sal.println("number-of-cases = "+casos.size()+";");
		sal.println("");
		sal.println("// Network Variables ");
		sal.println("");
		Node temp=red.inicio;
		do
		{
			sal.println("node "+temp.name+" {");
			sal.println("kind-of-node = chance;");
			sal.println("type-of-variable = finite-states;");
			sal.println("num-states = "+temp.values.size()+";");
			String t="states = (";
			int size=temp.values.size();
			for (int i=0;i<size;i++)
			{
				if(i==(size-1))
					t+="s"+temp.values.elementAt(i)+");";
				else
					t+="s"+temp.values.elementAt(i)+" ";
			}		
			sal.println(t);
			sal.println("}");
			sal.println("");
			temp=temp.sig;
		}
		while (temp!=null);

		sal.println("relation {");
		sal.println("");
		sal.println("memory = true;");
		sal.println("");
		sal.println("cases = (");
		int size=casos.size();
		for (int i=0;i<size;i++)
		{
			String t="[";
			String[] registro=(String[])casos.elementAt(i);
			for (int j=0;j<registro.length;j++)
			{
				if(j==(registro.length-1))
					t+="s"+registro[j];
				else
					t+="s"+registro[j]+",";
			}
			t+="]";
			sal.println(t);
		}
		sal.println(");");
		sal.println("}");
		sal.println("}");
		try 
		{
			 sal.close( );
			 f.close( );
			System.out.println("Escrito:  "+directory);
		}catch (IOException e) 
		   {System.out.println("Error");};
	}
// -----------------------------------------------------------------------------
	public void saveDataBaseArff(String directory, Vector casos, Red red)
	{
		FileWriter f=null;
		PrintWriter sal=null;
		String tem;
		tem = new String("");
		try 
		{ 
  		f = new FileWriter(directory);
			sal = new PrintWriter(f);
		}catch (IOException e) 
           {System.out.println(""+e);}
		
		sal.println("@relation "+red.name);
		sal.println("");
		Node temp=red.inicio;
		while(temp!=null)
		{
			String s="@attribute "+temp.name+" {";
			for (int i=0;i<temp.values.size();i++)
			{
				if(i==(temp.values.size()-1))
					s=s+temp.values.elementAt(i).toString()+"}";
				else
					s=s+temp.values.elementAt(i).toString()+", ";
			}
			sal.println(s);
			temp=temp.sig;
		}
		sal.println("");
		sal.println("@data");
		int size=casos.size();
		for (int i=0;i<size;i++)
		{
			String t="";
			String[] registro=(String[])casos.elementAt(i);
			for (int j=0;j<registro.length;j++)
			{
				if(j==(registro.length-1))
					t+=""+registro[j];
				else
					t+=""+registro[j]+",";
			}
			t+="";
			sal.println(t);
		}		
		try 
		{
			 sal.close( );
			 f.close( );
			System.out.println("Escrito:  "+directory);
		}catch (IOException e) 
		   {System.out.println("Error");};
	}
// -----------------------------------------------------------------------------
	public Arco leerArcosFromElviraFormat(String nombre,Node inNode)throws IOException
	{
		Arco inicio=new Arco();
		Arco temp=inicio;
		String lin, deli, next;
		deli = new String(",	 ");		
		FileInputStream inputFile = new FileInputStream(nombre);
		BufferedReader archivo = new BufferedReader (new InputStreamReader(inputFile)); 	 		    	    
		System.out.println("Estoy leyendo datos..");
				
		boolean finish=false;
		try
		{		
		 while (((lin=archivo.readLine()) != null) && !finish)
		 {
			 boolean found =false;
			 String[] line=null;
			 while ((lin=archivo.readLine()) != null)
			 {
				 line=lin.split(" ");
				 if(line[0].equals("link"))
				 {
					 found = true;
					 System.out.println("found");
					 break;
				 }
			 }
			 if(found == true)
			 {
				 String from=line[1];
				 String to=line[2];
				 to=to.replaceAll(";","");
				 System.out.println(""+from+"  "+to);
					int con=0;
					Node Ntemp=inNode;
					do
					{
						if(Ntemp.name.equals(from))
						{		
							temp.from=Ntemp;
							con++;
						}
						else if(Ntemp.name.equals(to))
						{
							temp.to=Ntemp;
							con++;
						}
						Ntemp=Ntemp.sig;
					}while(Ntemp!=null || con<2);

					while ((lin=archivo.readLine()) != null)
					{
						line=lin.split(" ");
						if(line[0].equals("link"))
						{
							if(temp.from!=null)
							{
							 temp.sig=new Arco();
							 temp=temp.sig;
							}
							from=line[1];
							to=line[2];
							to=to.replaceAll(";","");
							System.out.println(""+from+"  "+to);
							con=0;
							Ntemp=inNode;
							do
							{
								if(Ntemp.name.equals(from))
								{		
									temp.from=Ntemp;
									con++;
								}
								else if(Ntemp.name.equals(to))
								{
									temp.to=Ntemp;
									con++;
								}
								Ntemp=Ntemp.sig;
							}while(Ntemp!=null || con<2);
						}
						else if (line[0].equals(""))
						{
							//next
						}
						else
						{
							System.out.println(""+line.length);
							finish=true;
							break;
						}
					}
			 }
			 else
			 {
				 return null;
			 }
		 }
		}
		catch (Exception e)
		{
				//mensaje ="Error al leer el archivo";
		}		
		return inicio;
	}
}
// -----------------------------------------------------------------------------
// Fin.
// -----------------------------------------------------------------------------