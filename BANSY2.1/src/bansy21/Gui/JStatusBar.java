package bansy21.Gui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class JStatusBar extends JPanel
{
 JLabel text = new JLabel() ;

 public JStatusBar()
 {
   this.setLayout( new FlowLayout( FlowLayout.LEADING ) ) ;
	 setBorder(new BevelBorder (BevelBorder.RAISED));
   this.add( text ) ;

 }

 public void setStatus( String status )
 {
   text.setText( status ) ;
 }
} 