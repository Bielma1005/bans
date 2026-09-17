package bansy21.Gui;

import java.awt.*;
import java.awt.event.*;
// import java.lang.*;
// import java.lang.String;
import javax.swing.*;
import javax.swing.JInternalFrame;

import bansy21.frmPrincipal;
import bansy21.Red.*;
import bansy21.Utilerias.*;
import java.util.Vector;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
// -------------------------------------------------------------------------------------
public class RedFrame extends JInternalFrame
{
      public frmPrincipal hta; 
	public RedPanel redPanel;
	JPanel contentPane;
	public ImageIcon icon=null;
	static final int xOffset = 30, yOffset = 30;	

      public  JPanel Panel_Btns;
      public  JButton Btn_GuardarImagen, Btn_Reporte1, Btn_Reporte2; 
      private JFileChooser jFileChooser1;

// -------------------------------------------------------------------------------------
	public void setIcon(ImageIcon i)
	{
		icon=i;
		setFrameIcon((Icon)icon);
	}
// -------------------------------------------------------------------------------------
  public RedFrame(String title, frmPrincipal LH)
  {
    super(title, 
      true, //resizable
      true, //closable
      true, //maximizable
      true);//iconifiable
    setLocation(xOffset, yOffset);
    setSize(450,450);
    setBackground(Color.white);

    hta = LH;
    redPanel=new RedPanel();
    redPanel.setBackground(Color.white);
    if (icon!=null) { setFrameIcon(icon); }
    JScrollPane p1=new JScrollPane(redPanel);
//    this.setContentPane(p1);
    getContentPane().add(p1, BorderLayout.CENTER );
    redPanel.repaint();

      Btn_GuardarImagen = new JButton();
      Btn_GuardarImagen.setText( "Guardar Imagen..." ); 

      Btn_GuardarImagen.addActionListener(new java.awt.event.ActionListener() 
	{   public void actionPerformed(java.awt.event.ActionEvent evt) 
		    { Btn_GuardarImagenActionPerformed(evt); }
      });

      jFileChooser1 = new javax.swing.JFileChooser();
      jFileChooser1.setToolTipText("null");
      jFileChooser1.addActionListener(new java.awt.event.ActionListener() 
      {  public void actionPerformed(java.awt.event.ActionEvent evt) 
        { jFileChooser1ActionPerformed(evt); }
      });

      Btn_Reporte1       = new JButton();
      Btn_Reporte1.setText( "Reporte 1" ); 
      Btn_Reporte1.addActionListener(new java.awt.event.ActionListener() 
	{   public void actionPerformed(java.awt.event.ActionEvent evt) 
		    { Btn_Reporte1ActionPerformed(evt); }
      });

      Btn_Reporte2       = new JButton();
      Btn_Reporte2.setText( "Reporte 2" ); 
      Btn_Reporte2.addActionListener(new java.awt.event.ActionListener() 
	{   public void actionPerformed(java.awt.event.ActionEvent evt) 
		    { Btn_Reporte2ActionPerformed(evt); }
      });

      Panel_Btns = new JPanel();
      Panel_Btns.add( Btn_GuardarImagen );
      Panel_Btns.add( Btn_Reporte1 );
      Panel_Btns.add( Btn_Reporte2 );
      getContentPane().add( Panel_Btns, BorderLayout.NORTH );
  }
// -------------------------------------------------------------------------------------
//Segundo Constructor
  public RedFrame(String title, boolean ConBotones, frmPrincipal LH)
  {
    super(title, 
      true, //resizable
      true, //closable
      true, //maximizable
      true);//iconifiable
    setLocation(xOffset, yOffset);
    setSize(450,450);
    setBackground(Color.white);

    hta = LH;
    redPanel=new RedPanel();
    redPanel.setBackground(Color.white);
    if (icon!=null) { setFrameIcon(icon); }

    JScrollPane p1=new JScrollPane(redPanel);		
//    this.setContentPane(p1);		
    getContentPane().add(p1, BorderLayout.CENTER );
    redPanel.repaint();

    if ( ConBotones == true ) //Agregar botones?
    { Btn_GuardarImagen = new JButton();
      Btn_GuardarImagen.setText( "Guardar Imagen..." ); 
      Btn_GuardarImagen.addActionListener(new java.awt.event.ActionListener() 
	{   public void actionPerformed(java.awt.event.ActionEvent evt) 
		    { Btn_GuardarImagenActionPerformed(evt); }
      });

      jFileChooser1 = new javax.swing.JFileChooser();
      jFileChooser1.setToolTipText("null");
      jFileChooser1.addActionListener(new java.awt.event.ActionListener() 
      {  public void actionPerformed(java.awt.event.ActionEvent evt) 
        { jFileChooser1ActionPerformed(evt); }
      });

      Btn_Reporte1       = new JButton();
      Btn_Reporte1.setText( "Reporte 1" ); 
      Btn_Reporte1.addActionListener(new java.awt.event.ActionListener() 
	{   public void actionPerformed(java.awt.event.ActionEvent evt) 
		    { Btn_Reporte1ActionPerformed(evt); }
      });

      Btn_Reporte2       = new JButton();
      Btn_Reporte2.setText( "Reporte 2" ); 
      Btn_Reporte2.addActionListener(new java.awt.event.ActionListener() 
	{   public void actionPerformed(java.awt.event.ActionEvent evt) 
		    { Btn_Reporte2ActionPerformed(evt); }
      });

      Panel_Btns = new JPanel();
      Panel_Btns.add( Btn_GuardarImagen );
      Panel_Btns.add( Btn_Reporte1 );
      Panel_Btns.add( Btn_Reporte2 );
      getContentPane().add( Panel_Btns, BorderLayout.NORTH );
    }

    pack();
  } // Fin de Segundo Constructor
// -------------------------------------------------------------------------------------
  private void Btn_GuardarImagenActionPerformed(java.awt.event.ActionEvent evt) 
  { 
    int x;
    x = jFileChooser1.showSaveDialog(this);
  }
// -------------------------------------------------------------------------------------
  private void Btn_Reporte1ActionPerformed(java.awt.event.ActionEvent evt) 
  { 
    RepRedG v1 = new RepRedG( redPanel.red, 1 ); 
    hta.desktop.add(v1);
    try { v1.setMaximum(true); }catch(Exception e){};
    try { v1.setSelected(true); } catch (java.beans.PropertyVetoException e) {}
    hta.createMenuVentana();
    pack();
  }
// -------------------------------------------------------------------------------------
  private void Btn_Reporte2ActionPerformed(java.awt.event.ActionEvent evt) 
  { 
    RepRedG v1 = new RepRedG( redPanel.red, 2); 
    hta.desktop.add(v1);
    try { v1.setMaximum(true); }catch(Exception e){};
    try { v1.setSelected(true); } catch (java.beans.PropertyVetoException e) {}
    hta.createMenuVentana();
    pack();
  }
// -------------------------------------------------------------------------------------
  private void jFileChooser1ActionPerformed(java.awt.event.ActionEvent evt) 
  {
    String directory = jFileChooser1.getSelectedFile().getPath();
    Image ima=null;
    ima = this.redPanel.createImage(640,435);
    this.redPanel.paint(ima.getGraphics());
    (new Archivo()).guardarGif(ima,directory);
  }
// -------------------------------------------------------------------------------------
// -------------------------------------------------------------------------------------
} // Fin de la Clase: RedFrame.
// -------------------------------------------------------------------------------------
