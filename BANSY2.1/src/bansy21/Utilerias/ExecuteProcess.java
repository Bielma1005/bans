package bansy21.Utilerias;

import java.io.*;
import java.lang.Runtime;

public class ExecuteProcess
{
	public static void main(String[] args)  throws IOException
	{
		System.out.println("Hello World!");
		ExecuteProcess e=new ExecuteProcess();
		File f=new File("./");
		String[] com={"bayes9","0Training.txt"};
		Process p = e.execute(com);
	}

	public ExecuteProcess()
	{

	}

	public Process execute(String[] cmdarray) throws IOException
	{
		Runtime r = Runtime.getRuntime();
		Process p = r.exec(cmdarray);
		return p;
	}
}
