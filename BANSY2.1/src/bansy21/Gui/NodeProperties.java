package bansy21.Gui;


import bansy21.frmPrincipal;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.*;
import bansy21.Red.*;
import java.lang.*;
import java.lang.String.*;
import java.util.*;
import java.text.*;

// --------------------------------------------------------------------------------------------------------------
public class NodeProperties extends JDialog
{
 	public boolean cancel=true;
	public boolean noBayesN=true;

	JButton b1=new JButton("Aceptar");
	JButton b2=new JButton("Cancelar");
	JComboBox tipoNodo=new JComboBox(TipoNodo.tiposNodo);
	public JComboBox values;
	Node node;
	JTable table;
	String tipoNodoI;
	int inValue;
// --------------------------------------------------------------------------------------------------------------
//	public NodeProperties(JFrame padre, Node no)
	public NodeProperties(frmPrincipal padre, Node no)
	{
		super(padre,"Datos del nodo: "+no.name, true);
		node=no;
		inValue=no.selectedValue;
		setLocation(200,200);
                

		JPanel content=(JPanel)getContentPane();
		//content.setLayout(new GridLayout(3,1));
		JTabbedPane tabbedPane=new JTabbedPane();
		tabbedPane.setBorder(new BevelBorder (BevelBorder.RAISED));

		//Valor seleccionado por el usuario para la inferencia
		JPanel p=new JPanel();
		//p.setLayout(new GridLayout(1,2));
		//p.setBorder(new BevelBorder (BevelBorder.RAISED));
		p.add(new JLabel("Valor seleccionado"));
		values=new JComboBox(no.values);
		values.setSelectedIndex(no.selectedValue);
		values.addItemListener(new ItemListenerV());
		p.add(new JPanel().add(values));
		tabbedPane.add("Valor seleccionado",p);

		//Tipo de Nodo, por Default es Nada
		p=new JPanel();
		//p.setLayout(new GridLayout(1,2));
		//p.setBorder(new BevelBorder (BevelBorder.RAISED));
		p.add(new JLabel("Tipo de Nodo"));
		p.add(new JPanel().add(tipoNodo));
		tabbedPane.add("Tipo de Nodo",p);
		content.add(tabbedPane,BorderLayout.NORTH);

            
            // **************************************************************************

		//tabla de probabilidades
		Object[] columns = new Object[node.values.size()];
		node.values.copyInto(columns);
                
		table=new JTable((Object[][])node.table,columns);

		/*
		// COPIA los valores ( DECIMALES )
		Double d1 = new Double( 0.0 );
		DecimalFormat df = new DecimalFormat( padre.PatronDecimales );
		String str1;
		for( int r = 0; r < table.getRowCount(); r++ )
		  for( int c = 0; c < table.getColumnCount(); c++ )
		  {
		    d1   = (Double)table.getValueAt( 0, 0 );
		    str1 = new String(  df.format( d1 )  );
		    d1   = new Double( str1 );
		    try{ table.setValueAt( d1, 1, 1 ); } 
			catch( Exception e ) { System.out.println( "Excepci\u00f3n: " + e.toString() ); }
		    System.out.println( table.getValueAt(r, c)  );
		  }
		*/


		table.setToolTipText("Tabla de Probabilidades Condicionales");
		JScrollPane p1=new JScrollPane(table);
		p1.setBorder(new BevelBorder (BevelBorder.RAISED));	

            // **************************************************************************

		//nombre de las columnas de la tabla de las combinaciones e los padres
		columns = new Object[node.parents.size()];
		for (int i=0;i<node.parents.size();i++)
		{
			columns[i]=((Node)node.parents.elementAt(i)).name;
		}
		if (node.parents.size()>0)
		{		
			JTable table1=new JTable((Object[][])node.parentComb, columns);
			table1.setToolTipText("Tabla de valores de los padres");
			JScrollPane p2=new JScrollPane(table1);
			p2.setBorder(new BevelBorder (BevelBorder.RAISED));	

            // **************************************************************************

			
			JPanel pan=new JPanel();
		
			//pan.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
			pan.setLayout(new GridLayout(0, 2));
			
			/*
			JPanel pa1=new JPanel();
			pa1.add(p1, BorderLayout.CENTER);
			JPanel pa2=new JPanel();
			pa2.add(p2, BorderLayout.CENTER);
			*/

			pan.add(p1);
			pan.add(p2);
			content.add(pan,BorderLayout.CENTER);
		}
		else
		{
			content.add(p1,BorderLayout.CENTER);
		}

		
		p=new JPanel();
		p.setBorder(new BevelBorder (BevelBorder.RAISED));
		p.add(b1);		
		p.add(b2);
		content.add(p,BorderLayout.SOUTH);
	
  	b1.addActionListener(new ActionAceptar());
		b2.addActionListener(new ActionCancelar());	
		
		tipoNodoI=node.tipoNodo;
		tipoNodo.setSelectedItem(node.tipoNodo);
		tipoNodo.addItemListener(new ItemListenerM());



		b1.setVisible(true);
		b2.setVisible(true);
		b1.setToolTipText("Acepta los cambios y cierra el di\u00e1logo");
		b2.setToolTipText("Cancela los cambios y cierra el di\u00e1logo");
		table.setVisible(true);
		tipoNodo.setVisible(true);
		tipoNodo.setToolTipText("Selecciona el Tipo de Nodo");
		table.setAutoscrolls(true);
		pack();
		setSize(Math.max(getWidth(), 360), Math.max(getHeight(), 240));

	}
// --------------------------------------------------------------------------------------------------------------
	public class ItemListenerM implements ItemListener
	{
		public void itemStateChanged(ItemEvent e)
		{
			node.tipoNodo=(String)e.getItem();
		}
	}
// --------------------------------------------------------------------------------------------------------------
	public class ItemListenerV implements ItemListener
	{
		public void itemStateChanged(ItemEvent e)
		{
			node.selectedValue=values.getSelectedIndex();
		}
	}
// --------------------------------------------------------------------------------------------------------------
	public class ActionAceptar implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(true)
			{
				cancel=false;

				/*
				for (int i=0;i<node.table.length;i++)
				{
					for (int j=0;j<node.table[i].length;j++)
					{
						node.table[i][j]=(Double)table.getValueAt(i,j);
					}
				}
				*/
				
				dispose();
			}
			else
			{
				//mostrar al usuario un mensaje diciendole que faltan datos
				System.out.println("Faltan datos por capturar");
			}
		}
	}
// --------------------------------------------------------------------------------------------------------------
	public class ActionCancelar implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			node.tipoNodo=tipoNodoI;
			node.selectedValue=inValue;
			dispose();
		}
	}
}
// --------------------------------------------------------------------------------------------------------------