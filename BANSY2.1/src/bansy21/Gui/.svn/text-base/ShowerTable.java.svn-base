package bansy21.Gui;

import bansy21.Red.*;
import bansy21.frmPrincipal;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.text.*;

// ---------------------------------------------------------------------------
//public class ShowerTable extends JDialog
public class ShowerTable extends JInternalFrame

{
// ---------------------------------------------------------------------------
	public static void main(String[] args) 
	{
		System.out.println("Hello World!");
	}
// ---------------------------------------------------------------------------
	String col2 = new String("");
	public Red red=null;
	public JTable jtable=null;	
      public JButton BtnGuardarComo; 
      private JFileChooser jFileChooser1;
	private String texto = ""; 

// ---------------------------------------------------------------------------
//	public ShowerTable(JFrame padre, String[][] table, double[] prob, Red r)
	public ShowerTable(frmPrincipal padre, String[][] table, double[] prob, Red r)
	{
//		super(padre,"Tabla de datos", false);
//            padre.add(this);  

            this.setIconifiable( true); 
            this.setMaximizable( true);
            this.setResizable( true);
            this.setClosable( true);
            this.setVisible( true);
		this.setTitle( "Tabla de Probabilidades");
		this.setPreferredSize(new java.awt.Dimension(200, 200));
//            padre.getContentPane().add( this );
//            padre.getContentPane().add( this, BorderLayout.CENTER);

		red=r;
		JPanel content=(JPanel)getContentPane();
		
		setSize(320,200);

	      BtnGuardarComo = new JButton();
      	BtnGuardarComo.setText("Guardar como (*.CSV) ...");
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

		String[] tempCol = red.getVariablesNames();
		Object[][] temp  = new Object[table.length][tempCol.length+1];
		double suma=0;
            DecimalFormat df = new DecimalFormat( padre.bPE.PatronDecimales );


//		texto = new String [table.length][table[0].length];

		for (int i=0;i<table.length;i++)
		{
			int j=0;
			for (j=0;j<table[i].length;j++)
			{
				temp[i][j]=table[i][j];
				texto += table[i][j] + ","; 
				//	if (j+1 < table[i].length) texto += ",";
			}
			//	if (i+1 < table.length) texto += "\r";

			suma+=prob[i];

//			temp[i][j]= new Double(prob[i]);			
			temp[i][j]= new String(  df.format( prob[i] )  );

			texto += temp[i][j] + "\r";

		}
		System.out.println("   "+suma+"  "+table.length);

		String[] columnas=new String[tempCol.length+1];
		for (int i=0;i<tempCol.length;i++)
		{
			columnas[i]=tempCol[i];
			col2 = col2 + tempCol[i];
                  if ( i < tempCol.length - 1)  
                    col2 = col2 + ",";
		}
		columnas[columnas.length-1] = new String("Probabilidad");
//            texto = col2 + "\r" + texto;
            texto = col2  + ", Probabilidad, " + "\r" + texto;

		jtable=new JTable(temp,columnas);
		jtable.setToolTipText("Tabla de distribución conjunta");
		JScrollPane p1=new JScrollPane(jtable);
		p1.setBorder(new BevelBorder (BevelBorder.RAISED));	
      	content.add(BtnGuardarComo, BorderLayout.NORTH);
		content.add(p1,BorderLayout.CENTER);

		show();
	} // Fin Constructor. public ShowerTable
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
} // Fin. public class ShowerTable 
// ---------------------------------------------------------------------------
