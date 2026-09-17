// -----------------------------------------------------------------------------
// Pide los Parametros antes de correr un algoritmo de Aprendizaje.

package bansy21.Gui;

import bansy21.frmPrincipal;
import java.awt.*;
import java.awt.event.*;
import java.lang.*;
import java.text.*;
import javax.swing.*;
import javax.swing.border.*;
// -----------------------------------------------------------------------------
public class DiaParametros extends JDialog
{
  public JLabel     l1 = new JLabel("Alfa global:(%)");
  public JTextField t1 = new JTextField("",10);

  public JLabel     l2 = new JLabel("Profundidad:");
  public JTextField t2 = new JTextField("",10);

  public JLabel     l3 = new JLabel("% de Ganancia de Inf. Minima:");
  public JTextField t3 = new JTextField("",10);

  public boolean cancel   = true;
  public boolean noBayesN = true;

  JButton b1=new JButton("Aceptar");
  JButton b2=new JButton("Cancelar");
// -----------------------------------------------------------------------------
//	public DiaParametros(JFrame padre)
	public DiaParametros(frmPrincipal padre)

	{
		super(padre,"Par�metros de Aprendizaje - BayesN", true);
		getContentPane().setLayout(null);
		b1.addActionListener(new ActionAceptar());
		b2.addActionListener(new ActionCancelar());
		getContentPane().add(l1);
		getContentPane().add(l2);
		getContentPane().add(l3);
		getContentPane().add(t1);
		getContentPane().add(t2);
		getContentPane().add(t3);
		getContentPane().add(b1);
		getContentPane().add(b2);
		l1.setVisible(true);
		l2.setVisible(true);
		l3.setVisible(true);
		setSize(360, 205);
		l1.setBounds(25,10,180,25);
		l2.setBounds(25,50,180,25);
		l3.setBounds(25,90,180,25);
		t1.setBounds(200,10,110,25);
		t2.setBounds(200,50,110,25);
		t3.setBounds(200,90,110,25);
		b1.setBounds(70,140,100,25);
		b2.setBounds(180,140,100,25);

	      DecimalFormat df = new DecimalFormat( "#" );
	      Double  AlfaG       = new Double(  padre.bPE.alfaG * 100 ); 
	      Integer Profundidad = new Integer( padre.bPE.profundidad );
	      Double  GIM         = new Double(  padre.bPE.minPorcGan  );  //Ganancia de Informacion M�nima
	      t1.setText( df.format( AlfaG.doubleValue() ) ); 
      	t2.setText( Profundidad.toString()     );
	      t3.setText( df.format( GIM.doubleValue()   ) );

		t1.setVisible(true);
		t2.setVisible(true);
		t3.setVisible(true);
		b1.setVisible(true);
		b2.setVisible(true);
		b1.setToolTipText("Acepta los cambios y cierra el di�logo");
		b2.setToolTipText("Cancela los cambios y cierra el di�logo");
		
		((JPanel)getContentPane()).setBorder(new EtchedBorder(EtchedBorder.RAISED));
		Rectangle screenRect = this.getGraphicsConfiguration().getBounds();
		this.setLocation(		screenRect.x + screenRect.width/2 - this.getSize().width/2,
			screenRect.y + screenRect.height/2 - this.getSize().height/2);	 
		repaint();
	}
// -----------------------------------------------------------------------------
	public void paint(Graphics g)
	{
		super.paint(g);
		l1.setBounds(25,10,180,25);
		l2.setBounds(25,50,180,25);
		l3.setBounds(25,90,180,25);
		t1.setBounds(200,10,80,25);
		t2.setBounds(200,50,80,25);
		t3.setBounds(200,90,80,25);
		b1.setBounds(70,140,90,25);
		b2.setBounds(170,140,90,25);
		l1.repaint();
		l2.repaint();
		l3.repaint();
		t1.repaint();
		t2.repaint();
		t3.repaint();
		b1.repaint();
		b2.repaint();
	}
// -----------------------------------------------------------------------------
  public class ActionAceptar implements ActionListener
  {
    public void actionPerformed(ActionEvent e)
      {
        if((!t1.getText().equals("") && !t2.getText().equals("") && !t3.getText().equals("")) || noBayesN)
        {
          cancel=false;
          dispose();
        }
        else
        {
          //mostrar al usuario un mensaje diciendole que faltan datos
          System.out.println("Faltan datos por capturar");
        }
    }
  }
// -----------------------------------------------------------------------------	
  public class ActionCancelar implements ActionListener
  {
    public void actionPerformed(ActionEvent e)
    {			
      dispose();
    }
  }	
}
// -----------------------------------------------------------------------------