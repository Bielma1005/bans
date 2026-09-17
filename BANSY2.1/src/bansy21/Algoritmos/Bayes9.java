package bansy21.Algoritmos;

import java.io.*;
import bansy21.Gui.Algorithm;
import bansy21.Utilerias.*;
import bansy21.Red.*;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class Bayes9 extends Algorithm
{
	
	public String baseName=null;
	public String dataFileName=null;
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
		catch(Exception e)
		{
			println("Error al ejecutar Bayes9: "+e.getMessage());
			setIndeterminate(false);
			setNote("Error");
			return;
		}
		if (!hasValidArcs(red))
		{
			println("Bayes9 no produjo una red valida.");
			setIndeterminate(false);
			setNote("Error");
			return;
		}
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
		
		println(baseName);
		File dataFile=new File(dataFileName==null ? baseName : dataFileName).getAbsoluteFile();
		File executable=new File(getLocalDirName(), "bayes9.exe");
		if (!executable.isFile())
			executable=new File(getLocalDirName(), "bayes9");
		if (!executable.isFile())
			throw new IOException("No se encontró el ejecutable de Bayes9 en "+getLocalDirName());
		String[] com={executable.getAbsolutePath(), dataFile.getAbsolutePath()};
		ProcessBuilder builder=new ProcessBuilder(com);
		builder.redirectErrorStream(true);
		builder.directory(dataFile.getParentFile());
		File outputFile=new File(dataFile.getParentFile(),"final_graph1.lsp");
		if (outputFile.exists())
			outputFile.delete();
		Process p=builder.start();
      
		println("Ejecutando proceso...");
		final BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
		Thread outputReader=new Thread(new Runnable()
		{
			public void run()
			{
				try
				{
					String line;
					while ((line=input.readLine()) != null)
						println(line);
				}
				catch (IOException e)
				{
					println("No se pudo leer la salida de Bayes9: "+e.getMessage());
				}
			}
		});
		outputReader.start();
		if (!p.waitFor(120, TimeUnit.SECONDS))
		{
			p.destroy();
			throw new IOException("Bayes9 excedio el tiempo maximo de ejecucion (120 segundos).");
		}
		outputReader.join(2000);
		result=p.exitValue();
		
		// Algunas versiones de bayes9.exe devuelven 44 aunque hayan generado
		// correctamente el archivo final_graph1.lsp.
		Archivo2 a=new Archivo2();
		inArco = outputFile.isFile() ? a.leerArcosBayes9(outputFile.getAbsolutePath(),inicio) : null;
		if (inArco!=null)
		{
			if (result!=0)
				println("Bayes9 termino con codigo "+result+", pero produjo una red valida.");
			else
				println("Proceso ejecutado satisfactoriamente");
			println("Arcos leidos correctamente");
			red.inArco=inArco;
			red.varDep=getVarDep(red);
			red = pro.probabilityTables(red);
		}
		else
		{
			println("Proceso no ejecutado correctamente (codigo "+result+").");
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

	private boolean hasValidArcs(Red candidate)
	{
		if (candidate==null || candidate.inicio==null || candidate.inArco==null)
			return false;
		for (Arco arc=candidate.inArco; arc!=null; arc=arc.sig)
		{
			if (arc.from==null || arc.to==null)
				return false;
		}
		return true;
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
		java.net.URL myURL = this.getClass().getResource(getClassName());
		if (myURL==null)
			return new File(".").getAbsolutePath();
		try
		{
			return new File(myURL.toURI()).getParentFile().getAbsolutePath();
		}
		catch (Exception e)
		{
			return new File(myURL.getPath()).getParentFile().getAbsolutePath();
		}
   }
}
