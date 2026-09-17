package bansy21.Utilerias;
import java.util.Vector;
import java.util.StringTokenizer;
import java.io.*;
import bansy21.Red.*;
public class Archivo2
{
	public Archivo2()
	{

	}

	public Vector leerEjemplos(String file)throws IOException
	{
		String nombre=file;
		String lin, deli, next;
		Vector cases=new Vector();
		deli = new String(",	 ");		
		FileInputStream inputFile = new FileInputStream(nombre);
	  BufferedReader archivo = new BufferedReader (new InputStreamReader(inputFile)); 	 		    	    
	  System.out.println("Estoy leyendo datos..");

		while ((lin=archivo.readLine()) != null)
		{
			 String[] reg=lin.split(",");
			 cases.addElement(reg);
		}
		return cases;
	}

	public void escribirArcos(Vector v,Node inicio,String nombre)
	{
		FileWriter f=null;
    PrintWriter sal=null;
		try 
		{ 
   			f = new FileWriter(nombre);
			sal = new PrintWriter(f);
		}catch (IOException e) 
           {System.out.println(""+e);}
		
				
		//imprimir los nombres
		Node temp=inicio;
		String columnN="";
		while (temp!=null)
		{
			if(temp.sig==null)
			{
				columnN+=temp.name;
			}
			else
			{
				columnN+=temp.name+",";
			}
			temp=temp.sig;
		}
		sal.println(columnN);

		//imprimir cada uno de los registros
		int size=v.size();
		for (int i=0;i<size;i++)
		{
			String s="";
			String[] r=(String[])v.elementAt(i);
			for (int j=0;j<r.length;j++)
			{
				if (j==(r.length-1))
				{
					s+=r[j];
				}
				else
				{
					s+=r[j]+",";
				}
			}
			sal.println(s);
		}		  
		try 
		{
			 sal.close();
			 f.close();
			System.out.println("Escrito:  "+nombre);
		}catch (IOException e) 
		   {System.out.println("Error");};
	}

	public void escribirDatos(Vector v, String nombre, Node n)
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
		
		if (n!=null)
		{
			Node temp=n;
			String s="";
			do
			{
				if (temp.sig==null)
				{
					s+=temp.name;
				}
				else
				{
					s+=temp.name+",";
				}
				temp=temp.sig;
			}
			while (temp!=null);
			sal.println(s);
		}		
		
		int size=v.size();
		for (int i=0;i<size;i++)
		{
			sal.println(makeString((String[])v.elementAt(i),","));
		}

		try 
		{
			 sal.close( );
			 f.close( );
			System.out.println("Escrito:  "+nombre);
		}catch (IOException e) 
		   {System.out.println("Error");};
	}
	
	/**
	*Construye un string a partir de los caracteres en el arreglo, separados por el para metro separator
	*/
	private String makeString(String[] ar, String separator)
	{
		String r="";
		for (int i=0;i<ar.length;i++)
		{
			if (i==(ar.length-1))
			{
				r+=ar[i];
			}
			else
			{
				r+=ar[i]+separator;
			}			
		}
		return r;
	}

	public Arco leerArcosBayes9(String nombre,Node inNode)throws IOException
	{
		Arco inicio=new Arco();
		Arco temp=inicio;
		String lin, deli, next;
		deli = new String("-> --");
		FileInputStream inputFile = new FileInputStream(nombre);
		BufferedReader archivo = new BufferedReader (new InputStreamReader(inputFile)); 	 		    	    
	    System.out.println("Estoy leyendo datos.."+nombre);
	
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
				String[] r=lin.split(" ");
				
				if(r[1].equals("--"))
				{
					temp.directed=false;
				}
				
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
        
        
        
        public void guardarProyecto(bansyProyEcoder bne, String fileName)
        {            
            FileOutputStream fos=null;
            ObjectOutputStream out = null;
            try
            {
                fos = new FileOutputStream(fileName);
                out = new ObjectOutputStream(fos);
                out.writeObject(bne);
                out.close();
            }
            catch(IOException ex)
            {
               ex.printStackTrace();
            }
        }
        
        public bansyProyEcoder abrirProyecto(String fileName)
        {
             bansyProyEcoder bne=null;             
             FileInputStream  fis = null;
             ObjectInputStream in = null;
             try
             {
                fis = new FileInputStream (fileName);
                in = new ObjectInputStream(fis);
                bne = (bansyProyEcoder)in.readObject();
                in.close();
            }
            catch(IOException ex)
            {
               ex.printStackTrace();
            }
            catch(ClassNotFoundException ex)
            {
               ex.printStackTrace();
            }
             return bne;
        }
}