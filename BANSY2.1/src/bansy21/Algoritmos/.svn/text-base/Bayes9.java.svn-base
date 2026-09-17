package bansy21.Algoritmos;

import java.io.*;
import bansy21.Gui.Algorithm;
import bansy21.Utilerias.*;
import bansy21.Red.*;
import java.util.Calendar;

public class Bayes9 extends Algorithm
{
	
	public String baseName=null;
	int result=0;

	/**
	*Constructor
	*/
	public Bayes9()
	{
		super();
	}

	public void run()
	{	
		println("");
		println("");
		println("**********		Bayes9		**********");
		println("");
		println("Base de datos: "+baseName);
		println("");
		createRed(inicio);
		Calendar date1=Calendar.getInstance();
		try
		{
			red=getRed();
		}
		catch(Exception e){};	
		Calendar date2=Calendar.getInstance();													
		println("Tiempo empleado: "+util.tiempoEmpleado(date1,date2));
		parent.agregarRed(red,baseName+"_Bayes9");
		setIndeterminate(false);
		setAtMax();
		setNote("Listo");
	}	
	
	/**
	* Inicia el prceso del bayes9 y espera hasta que termine
	*/
	public Red getRed() throws IOException,InterruptedException
	{	
		
      ExecuteProcess e=new ExecuteProcess();      
		println(baseName);
      String na=getLocalDirName();
		String[] com={na+"/bayes9", baseName};
		Process p = e.execute(com);
      
		println("Ejecutando proceso...");
		String line;
		BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
    while ((line = input.readLine()) != null) 
		{
		 println(line);		
    }
    input.close();	
		
		if (result==0)//el proceso termino satisfactoriamente
		{			
			println("Proceso ejecutado satisfactoriamente");
			//leer la red generada por el bayes9
			Archivo2 a=new Archivo2();
			inArco = a.leerArcosBayes9("final_graph1.lsp",inicio);
			if (inArco!=null)
			{
				println("Arcos leidos correctamente");
				red.inArco=inArco;
				red.varDep=getVarDep(red);
				red = pro.probabilityTables(red);
			}
		}
		else
		{
			println("Proceso no ejecutado correctamente");
		}
		//p.destroy();
		return red;
	}

	/**
	* Encuentra la varibale dependiente
	*/
	private Node getVarDep(Red r)
	{
		Node temp=r.inicio;
		while (temp!=null)
		{
			if (temp.equals(varDep))
			{
				return temp;
			}
			temp=temp.sig;
		}
		System.out.println("<sdgagaergaregwe.,m");
		return varDep;
	}
   
   
    /**
    * Returns the disk file name of the class that is executing.
    *
    * @param none
    * @return Name of class that is currently executing
    */   
   public String getClassName()
   {
      String thisClassName;
      
      //Build a string with executing class's name
      thisClassName = this.getClass().getName();
      thisClassName = thisClassName.substring(thisClassName.lastIndexOf(".") + 1,thisClassName.length());
      thisClassName += ".class";  //this is the name of the bytecode file that is executing
      
      return thisClassName;
   }
   
   /**
    * Returns the name of the local directory based on the results of a call to getClassName()
    *
    * @param none
    * @return Name of directory that contains the executing class
    */      
   public String getLocalDirName()
   {
      String localDirName;
      
      //Use that name to get a URL to the directory we are executing in
      java.net.URL myURL = this.getClass().getResource(getClassName());  //Open a URL to the our .class file
      
      //Clean up the URL and make a String with absolute path name
      localDirName = myURL.getPath();  //Strip path to URL object out
      localDirName = myURL.getPath().replaceAll("%20", " ");  //change %20 chars to spaces  
      
      //Get the current execution directory
      localDirName = localDirName.substring(0,localDirName.lastIndexOf("/"));  //clean off the file name
      
      return localDirName;
   }
}
