package bansy21.Gui;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

// public class Progress extends JDialog implements Runnable
public class Progress extends JInternalFrame implements Runnable
{
	public static void main(String[] args)
	{
		System.out.println("Hello World!");
	}

	private ImageIcon image;
        public String nombreMonitor=null;
	JLabel labelImage;
	JLabel label;
	private JTextArea outPut=null;
	public JProgressBar progressBar;
      JButton BtnGuardarComo; 
      private JFileChooser jFileChooser1;

	public Progress(JFrame parent,String title,boolean determinate,int maxValue, ImageIcon ima)
	{
//		super(parent, title, false);


		super(title, 
              true, //resizable
              true, //closable
              true, //maximizable
              true);//iconifiable

		setLocation(50, 50);
		setSize(300,300);
		setBackground(Color.white);
            setVisible( true);
		setTitle( "Reporte: " + title);
		setPreferredSize(new java.awt.Dimension(200, 200));
                nombreMonitor=title;
//            parent.getContentPane().add( this );
//            parent.getContentPane().add( this, BorderLayout.CENTER);

	      label = new JLabel("Trabajando...");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		progressBar = new JProgressBar(0, maxValue);
            progressBar.setValue(0);

	      BtnGuardarComo = new JButton();
      	BtnGuardarComo.setText("Guardar como ...");
	      BtnGuardarComo.addActionListener(new java.awt.event.ActionListener() 
		{   public void actionPerformed(java.awt.event.ActionEvent evt) 
		    { BtnGuardarComoActionPerformed(evt); }
	      });

            jFileChooser1 = new javax.swing.JFileChooser();
		jFileChooser1.setToolTipText("null");
		jFileChooser1.addActionListener(new java.awt.event.ActionListener() 
		{  public void actionPerformed(java.awt.event.ActionEvent evt) 
		   { jFileChooser1ActionPerformed(evt); }
            });
		
		if (determinate)
		{ progressBar.setStringPainted(true); }
		else
		{
			progressBar.setStringPainted(false);
			progressBar.setIndeterminate(true);
		}

		JPanel content=(JPanel)getContentPane();

		//labelImage=new JLabel(image);
		outPut=new JTextArea();
		outPut.setAutoscrolls(true);		
		JScrollPane pane=new JScrollPane(outPut);

      	content.add(BtnGuardarComo, BorderLayout.NORTH);
//		content.add(label,BorderLayout.NORTH);

		content.add(pane,BorderLayout.CENTER);
		content.add(progressBar,BorderLayout.SOUTH);
		
		//this.pack();
		setSize(150,300);
	}
// ------------------------------------------------------------------------
	private void BtnGuardarComoActionPerformed(java.awt.event.ActionEvent evt) 
	{ int x;
        x = jFileChooser1.showSaveDialog(this);
	}
// ------------------------------------------------------------------------
	private void jFileChooser1ActionPerformed(java.awt.event.ActionEvent evt) 
	{   File fichSalida = new File("C:\\" );
          try{ //Guarda un texto en un archivo
            //Se recupera el texto del campo y se convierte en un array

//            String texto = jTextArea1.getText();
		String texto = outPut.getText();

            byte b[] = texto.getBytes();
        
            //Se asigna nombre al archivo
            fichSalida = jFileChooser1.getSelectedFile();  
        
            //Se crea el canal de salida conectado a este archivo
            FileOutputStream canalSalida = new FileOutputStream( fichSalida );
            //Se escribe el contenido del array de bytes en el archivo
            canalSalida.write( b );
            //Se cierra el canal
            canalSalida.close();
          }catch( IOException e){ e.printStackTrace(); }
    }
// ------------------------------------------------------------------------
	public void run()
	{
		this.show();
	}
// ------------------------------------------------------------------------
	public void setProgress(int value)
	{
		repaint();
		progressBar.setValue(value);
	}
// ------------------------------------------------------------------------
	public void setNote(String note)
	{
		label.setText(note);
	}
// ------------------------------------------------------------------------
	public void close()
	{
		this.dispose();
	}

	public void incrementProgress()
	{
		progressBar.setValue(progressBar.getValue()+1);
	}

	public void setIndeterminate(boolean newValue)
	{
		progressBar.setIndeterminate(newValue);
	}

	public void setAtMax()
	{
		setProgress(progressBar.getMaximum());
	}
	/**
	*
	*/
	public void addNotify()
	{
		super.addNotify();
	}
	public void append(String s)
	{
		outPut.append(s+" \n");
	}
	public void println(String s)
	{
		outPut.append(s+" \n");
	}

	public void setDefaultCursor()
	{
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}

	public void setWaitCursor()
	{
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	}
}
// ------------------------------------------------------------------------
// ------------------------------------------------------------------------
