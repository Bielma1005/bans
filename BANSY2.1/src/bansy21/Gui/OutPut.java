package bansy21.Gui;

import javax.swing.*;

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
		outPut=new JTextArea(18, 70);
		outPut.setAutoscrolls(true);
		outPut.setLineWrap(true);
		outPut.setWrapStyleWord(true);
		//outPut.setEditable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		JScrollPane pane=new JScrollPane(outPut);
		setContentPane(pane);	
		pack();
		setSize(Math.max(getWidth(), 550), Math.max(getHeight(), 350));
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
