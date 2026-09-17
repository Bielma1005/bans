package bansy21.Gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.InternalFrameListener;
import javax.swing.event.InternalFrameEvent;

public class OutPut extends JDialog
{
	public static void main(String[] args) 
	{
		System.out.println("Hello World!");
		OutPut o=new OutPut(new JFrame(),"Salida");
		o.append("uno");
		o.append("uno");
		o.append("uno");
		o.append("uno");
			o.append("uno");
		o.append("uno");
		o.append("uno");
		o.append("uno");
			o.append("uno");
		o.append("uno");
		o.append("uno");
		o.append("uno");
			o.append("uno");
		o.append("uno");
		o.append("uno");
		o.append("uno");
			o.append("uno");
		o.append("uno");
		o.append("uno");
		o.append("uno df");

	}
	
	private JTextArea outPut=null;

	public OutPut(JFrame p,String title)
	{
		super(p,title,false);
		outPut=new JTextArea();
		outPut.setAutoscrolls(true);
		//outPut.setEditable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setSize(100,100);
		JScrollPane pane=new JScrollPane(outPut);
		setContentPane(pane);	
	}
	public void append(String s)
	{
		outPut.append(s+" \n");
	}
	public void println(String s)
	{
		outPut.append(s+" \n");
	}
}
