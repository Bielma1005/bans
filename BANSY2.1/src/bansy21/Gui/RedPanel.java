package bansy21.Gui;

import bansy21.frmPrincipal;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import javax.swing.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import bansy21.Red.*;
import bansy21.Utilerias.*;
// --------------------------------------------------------------------------------------------------
public class RedPanel extends JPanel
{	
	public frmPrincipal parent=null;
        
	public Red red=null;
	//VEctor con los nodos evidencia
	public Vector evidencia=null;
	//nodo de consulta
	public Node queryN=null;
	//nodo seleccionado
	public Node selectedN=null;
	//Arco seleccionado
	public Arco selectedA=null;

	//radio de los nodos()de los circulos que representan los nodos de la red
	public int radio=15;

	public int numVariables;
	public  boolean firstPaint=true;
	//bandera para el nodo seleccioando
	public boolean bandera=false;
	//bandera para arco seleccionado
	public boolean banderaA=false;
	public int clicks=0;
	public Node newN=null;
	public Arco newA=null;
	//bandera para saber si se esta agregando un nuevo nodo
	public boolean newNode=false;
	//bandera para saber si se esta agregando un nuevo Arco
	public boolean newArco=false;
	
	//tamaño actual de la red
	private Dimension area;
	//private Image fondo=null;
	//BufferedImage mBufferedImage=null;
        
          public java.awt.Image imgNodo = null;
          public java.awt.Image imgNodoSel = null;
	
// --------------------------------------------------------------------------------------------------
	public RedPanel()
	{
		// #557FAA	
		
		setBackground(Color.white);
		addMouseListener(new mouseListener());
		addMouseMotionListener(new mouseMotion());
		area = new Dimension(0,0);	
                 imgNodo=(new javax.swing.ImageIcon(getClass().getResource("/images/nuevos/nodoBig.png"))).getImage();
                 imgNodoSel=(new javax.swing.ImageIcon(getClass().getResource("/images/nuevos/nodoBigi.png"))).getImage();

        }	

// --------------------------------------------------------------------------------------------------
	public void paint(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;		
		Dimension d=this.getSize();
		evidencia = new Vector();

		double x0=d.width/2;//this.WIDTH/2;
		double y0=d.height/2;//this.HEIGHT/2;

		g.setColor(Color.white);
                g.setFont(Font.decode("Arial-BOLD-10"));                
		g.clearRect(0, 0, d.width, d.height);	
		g.fillRect(0, 0, d.width, d.height);		

                
		int x,y, mayorx=0, mayory=0;	
		double con=0;
	
		if(red!=null)
		{
			this.setVisible(true);
			Node temp=red.inicio;
			Arco tempArco=red.inArco;
			if(red.inicio!=null)
			{
				double angulo=(2*3.1416)/(double)numVariables;
				do
				{
					if(firstPaint)
					{
						x=(new Double(x0+150.0*Math.cos(-angulo*con))).intValue();
						y=(new Double(y0+150.0*Math.sin(-angulo*con))).intValue();
                                                
                                                /*
                                                double angulo2=(2*3.1416)/((double)6);
                                                
                                                System.out.println(""+(new Double(10+5.0*Math.cos(-angulo2*1))));
                                                System.out.println(""+(new Double(10+5.0*Math.sin(-angulo2*1))));
                                                System.out.println(""+(new Double(10+5.0*Math.cos(-angulo2*2))));
                                                System.out.println(""+(new Double(10+5.0*Math.sin(-angulo2*2))));
                                                System.out.println(""+(new Double(10+5.0*Math.cos(-angulo2*3))));
                                                System.out.println(""+(new Double(10+5.0*Math.sin(-angulo2*3))));
                                                System.out.println(""+(new Double(10+5.0*Math.cos(-angulo2*4))));
                                                System.out.println(""+(new Double(10+5.0*Math.sin(-angulo2*4))));
                                                System.out.println(""+(new Double(10+5.0*Math.cos(-angulo2*5))));
                                                System.out.println(""+(new Double(10+5.0*Math.sin(-angulo2*5))));
                                                System.out.println(""+(new Double(10+5.0*Math.cos(-angulo2*6))));
                                                System.out.println(""+(new Double(10+5.0*Math.sin(-angulo2*6))));
                                           */
                                                
                                                
						temp.x = x + radio;
						temp.y = y + radio;
					}
					else
					{
						x=temp.x-radio;
						y=temp.y-radio;
					}

					//para que se regresen los scrools bar
					if (x>mayorx)
					{
						mayorx=x+radio+2;
					}
					if (y>mayory)
					{
						mayory=y+radio+2;
					}

					area.width  = mayorx + 4*radio;
					area.height = mayory + 4*radio;
					updateS();

					//no permitir que los nodos salgan de la pantalla
					if (x<0)
					{
						x=0;
						temp.x=x+radio;
					}
					if (y<0)
					{
						y=0;
						temp.y=y+radio;
					}
                                        
                                        //g.fillArc(x,y,30,30,0,360);
                                        //g.fillOval(x,y, 30,30);
                                        g.drawImage(imgNodo,x,y,this);
					g.setColor(Color.BLUE);                    
					g.drawString(temp.name,x,y-10);
					//verificar si el no do es de consulta
					if(temp.tipoNodo.equals(TipoNodo.CONSULTA))
					{queryN=temp;}
					else
					{queryN=null;}
					//Verificar si el nodo es nodo de evidencia
					if(temp.tipoNodo.equals(TipoNodo.EVIDENCIA))
					{evidencia.addElement(temp);}
		
					temp=temp.sig;
					con++;
				}while(temp!=null);
				firstPaint=false;
			}
			
			if(red.inArco!=null)
			{
				if(red.inArco.to!=null)
				{
					int numArcos=0;
					do
					{
						drawLineFilled(tempArco.to.x, tempArco.to.y,tempArco.from.x,tempArco.from.y,13,g,tempArco.directed);
						tempArco=tempArco.sig;
						numArcos++;
					}while(tempArco!=null);					
				}
			}
			
			//marcar nodo seleccionado
			if(selectedN!=null)		
			{
				//g.setColor(Color.red);
				//g.drawArc(selectedN.x-radio,selectedN.y-radio,30,30,0,360);
                                g.drawImage(imgNodoSel,selectedN.x-radio,selectedN.y-radio,this);
                                
			}
			//marcar el nodo de consulta	
			if(queryN!=null)
			{
				g.setColor(Color.cyan);
				g.drawArc(queryN.x-radio,queryN.y-radio,30,30,0,360);
			}
			if(selectedA!=null)
			{
				g.setColor(Color.red);
				int[] reg=getPunto(selectedA.from.x,selectedA.from.y,selectedA.to.x, selectedA.to.y);		
				drawLineFilled(selectedA.to.x, selectedA.to.y,reg[0],reg[1],13,g,selectedA.directed);
			}
			//si se esta poniendo un nuevo nodo dibujarlo
			if(newNode)
			{
				g.drawArc(newN.x-radio,newN.y-radio,30,30,0,360);
				g.setColor(Color.black);
				g.drawString(newN.name,newN.x-radio,newN.y-10-radio);
			}
			//poner al arco que se esta trtatando de agregar
			if(newArco)
			{
				g.setColor(Color.red);
				int[] reg=getPunto(newA.from.x,newA.from.y,newA.to.x, newA.to.y);
				drawLineFilled(newA.to.x, newA.to.y,reg[0],reg[1],13,g,newA.directed);
			}
		}
		else
		{
			this.setVisible(false);
		}
	}	
// --------------------------------------------------------------------------------------------------
//***********    Clase para los eventos del mouse excepto los movimientos *¨************************
	public class mouseListener implements MouseListener
	{
		public void mouseClicked(MouseEvent e)
		{
			 
		}
		public void mousePressed(MouseEvent e)
		{
			int x=e.getX();
			int y=e.getY();		
		
			//System.out.println("Frame press");			 
			if(red.inicio!=null)
			{
				//System.out.println("ya");
				selectedNodeHelper(e,x,y);
				if(red.inArco!=null && !bandera)
					selectedArcHelper(e,x,y);
			}
			//no se ha seleccionado un nodo
			if(!bandera)
			{
				selectedN=null;
			}
			else if(newArco)//se ha seleccionado un nodo, pero se esta tratando de agregar un arco
			{				
				newA=new Arco();
				newA.from=selectedN;
				newA.to=new Node();
				newA.to.x=newA.from.x;
				newA.to.y=newA.from.y;
				bandera=false;//para que no se marque el nodo  seleccionado
			}
			//no se ha seleccionado un arco
			if(!banderaA && !newNode && !newArco)
			{
				selectedA=null;
			}			
			repaint();
		}
// --------------------------------------------------------------------------------------------------
		public void mouseReleased(MouseEvent e)
		{
			int x=e.getX();
			int y=e.getY();
			bandera=false;
			banderaA=false;

			/*si se esta agregando un nodo, agregarlo a la lista de nodos
			* con valores por default cero y uno
			* */
			if(newNode && newN!=null)
			{			
				Node temp=new Node();
				temp.name=newN.name;
				temp.x=x;
				temp.y=y;
				temp.values=new Vector();
				temp.values.addElement("0");
				temp.values.addElement("1");		
				temp.table=parent.util.getTable(temp);
				red.inicio = parent.util.add(temp,red.inicio);
				
				newNode=false;
				newN=null;
				numVariables++;
				red.modificada=true;
			}
			/*si se esta agregando un arco, verificar que en el punto que se solto el boton,
			existe un nodo, de lo contrario no hacer nada*/
			if(newArco && newA!=null)
			{
				selectedNodeHelper(e,x,y);				
				if(bandera)	
				{					
					//quiere decir que en el lugar que se libero el mouse, si existe un nodo
					//y hay que agregar el arco a la lista de arcos

					//debe verificarse que no haga un ciclo con el nuevo arco
					Arco temp1=new Arco();
					temp1.from=newA.from;
					temp1.to=selectedN;
					red.inArco=parent.util.add(temp1,red.inArco);
					newArco=false;
					newA=null;
					bandera=false;
					selectedN=null;
					red.modificada=true;
				}
				else
				{					
					newArco=false;
					newA=null;
				}
			}
			repaint();
		}
// --------------------------------------------------------------------------------------------------
		public void mouseEntered(MouseEvent e)
		{
			int x=e.getX();
 			int y=e.getY();
			if(newNode)
			{	
				//manejar agregar arco
				newN=new Node();
				newN.name="Nodo"+(numVariables+1);
				newN.x=x;
				newN.y=y;
				repaint();
			}			
		}
// --------------------------------------------------------------------------------------------------
		public void mouseExited(MouseEvent e)
		{}
	}

// --------------------------------------------------------------------------------------------------
// 			Clase para los movimientos del mouse  
// --------------------------------------------------------------------------------------------------
	public class mouseMotion implements MouseMotionListener
	{
		public void mouseDragged(MouseEvent e)
		{
			int x=e.getX();
		  int y=e.getY();			
			if(bandera)
			{
				selectedN.x=x;
				selectedN.y=y;

				Rectangle rect = new Rectangle(x, y, radio, radio);
				scrollRectToVisible(rect);
				
				int this_width = (x + radio + 2);
				int this_height = (y + radio + 2);				
				updateScroll(this_width,this_height);
				red.modificada=true;
				repaint();
			}
			//poner las nuevas coordenas al nodo del nuevo arco
			if(newArco && newA!=null)
			{
				newA.to.x=x;
				newA.to.y=y;
				repaint();
			}
		}
// --------------------------------------------------------------------------------------------------
		public void mouseMoved(MouseEvent e)
		{
			int x=e.getX();
		  int y=e.getY();
			//poner las coordenadas actuales del mouse al nuevo nodo
			if(newNode && newN!=null)
			{
				newN.x=x;
				newN.y=y;
				repaint();
			}			
		}
	}

// --------------------------------------------------------------------------------------------------
//		Verifica si se selecciono algun nodo con el mouse
// --------------------------------------------------------------------------------------------------
	private void selectedNodeHelper(MouseEvent e, int x, int y )
	{
		Node temp=red.inicio;				
		do
			{
				double d=Math.pow( ( Math.pow((x-temp.x), 2) + Math.pow((y-temp.y), 2) ), 0.5);
				if(d<=radio)
				{
					if(e.getClickCount()==2)//dos clicks
					{
						selectedN=temp;
						verInfNode();
					}
					else
					{
						bandera=true;
						selectedN=temp;					
					}
					break;
				}
				temp=temp.sig;
			}while(temp!=null);
	}
// --------------------------------------------------------------------------------------------------
// 	Este metodo se ocupa de verificar si se selecciona un arco

	private void selectedArcHelper(MouseEvent e, int x, int y)
	{
		Arco temp=red.inArco;
		do
		{
			int x1=temp.to.x;
			int y1=temp.to.y;
			int x2=temp.from.x;
			int y2=temp.from.y;					
			//cambiar el origen de cooredenadas a x1,y1
			x2=x2-x1;
			y2=y2-y1;
			x=x-x1;
			y=y-y1;
			boolean flag=false;
			if(x2==0 && Math.abs(y2)>0)
			{
				flag=Math.abs(y2-y)<Math.abs(y2);
				flag=flag && Math.abs(x)<=2;
			}
			else if(y2==0 && Math.abs(x2)>0)
			{
				flag=Math.abs(x2-x)<Math.abs(x2);
				flag=flag && Math.abs(y)<=2;
			}
			else
			{
				//verificar que tengan elmismosigno
				double te=(double)Math.abs(x2)/(double)x2;
				double te1=(double)Math.abs(x)/(double)(x);
				double te2=(double)Math.abs(y2)/(double)(y2);
				double te3=(double)Math.abs(y)/(double)(y);
				boolean b00=(te/te1)>0 && (te2/te3)>0;
				
				flag=Math.abs(x2)>Math.abs(x) && Math.abs(y2)>Math.abs(y) && b00;
				//flag=(x2-x)<(x2) && (y2-y)<(y2);
				double m=((double)(y2)/(double)(x2));
				double m1=(double)(y)/(double)(x);
				flag=flag && Math.abs(m-m1)<0.1;
			}			
				
				if(flag)
				{
					//System.out.println("x1= "+x1+" x2= "+x2+" x= "+x);
				    //System.out.println("y1= "+y1+" y2= "+y2+" y= "+y);
					banderaA=true;
					selectedA=temp;
					if(e.getClickCount() ==2)
					{
						verInfArc();
						//ver propiedades del arco
					}					
					break;
				}
				else
				{
					x=x+x1;
			    y=y+y1;
                                }
			
			temp=temp.sig;
		}while(temp!=null);
		//System.out.println("\n");
	}
// --------------------------------------------------------------------------------------------------
// 	verifica si las dimenciones sobrepasan o disminuyeron, si es asi actualiuza los scrolls

	private void updateScroll(int this_width,	int this_height)
	{
	  boolean changed=false;
	  if (this_width > area.width) 
	  {
	    area.width = this_width;
	    changed=true;
	  }
	  if (this_height > area.height) 
	  {
	    area.height = this_height; 
	    changed=true;
	  }
	  if (changed) updateS();
	}
// --------------------------------------------------------------------------------------------------
	private void updateS()
	{
		//Update client's preferred size because
		//the area taken up by the graphics has
		//gotten larger or smaller (if cleared).
		setPreferredSize(area);
		//Let the scroll pane know to update itself
		//and its scrollbars.
		revalidate();
	}
// --------------------------------------------------------------------------------------------------
// 	Dibuja una line con flecha rellena

	/// <summary>
	/// dibuja un linea con flecha rellena
	/// </summary>
	/// <param name="x1">cooredenada en x de la punta de la flecha</param>
	/// <param name="y1">coordenada en y de la punta de la flecha</param>
	/// <param name="x2">cooredenada en x de la cola de la flecha</param>
	/// <param name="y2">coordenada en y de la cola de la flecha</param>
	/// <param name="escala">escala de la punta de la flecha con respecto a la longitud de la linea</param>
	/// <param name="grosor">grosor de la linea</param>
	private void drawLineFilled(int x1, int y1, int x2, int y2, int escala, Graphics g,boolean directed)
	{
		int x3,y3,x4,y4,inclinacion;
		double teta,l;
		if(x1==x2)
		{
			inclinacion=15;
		}
		else
		{
			inclinacion=30;
		}			
		double ang=inclinacion*Math.PI/180.0;		

		if(x2==x1)
		{
			teta=Math.PI/2.0;
		}
		else
		{
			teta=Math.atan((double)(y2-y1)/(double)(x2-x1));
		}
   	int[] reg=getPunto(x1,y1,x2,y2);
		x1=reg[0];
		y1=reg[1];
                x2=reg[2];
		y2=reg[3];
		g.drawLine(x1,y1,x2,y2);

		if (directed)
		{	

			l=150;
			//l=Math.pow(Math.pow((x2-x1),2)+Math.pow((y2-y1),2),0.5);
			x3=(int)(l/escala*Math.cos(teta - ang));
			y3=(int)(l/escala*Math.sin(teta - ang));
			x4=(int)(l/escala*Math.cos(teta + ang));
			y4=(int)(l/escala*Math.sin(teta + ang));
			if(x1==x2)
			{
				if(y1<y2)
				{
					int[] xPoints ={x1,x1+x3-x4,x1-x3+x4};
					int[] yPoints ={y1,y1+y3,y1+y3};
					g.fillPolygon(xPoints,yPoints,3);
				}
				else
				{		
					y1=y1-30;
					int[] xPoints ={x1,x1+x3-x4,x1+x4-x3};
					int[] yPoints ={y1,y1-y4,y1-y4};				
					g.fillPolygon(xPoints,yPoints,3);
				}
			}
			else if(x1<x2)
			{			
				int[] xPoints ={x1,x1+x3,x1+x4};
				int[] yPoints ={y1,y1+y3,y1+y4};
				g.fillPolygon(xPoints,yPoints,3);
			}		
			else
			{
				int[] xPoints ={x1,x1-x3,x1-x4};
				int[] yPoints ={y1,y1-y3,y1-y4};				
				g.fillPolygon(xPoints,yPoints,3);				
			}		
		}
	}
// --------------------------------------------------------------------------------------------------
/**
	* devuelve el punto de la circunferencia hasta donde debe llegar la linea. El punto donde termina
	* la punta d ela flecha
*/
	private int[] getPunto(int x1,int y1,int x2, int y2)
	{
		double teta;
		if(x2==x1)
		{
			teta=Math.PI/2.0;
		}
		else
		{
			teta=Math.atan((double)(y2-y1)/(double)(x2-x1));
		}
		if(x2<x1)
		{
			if(y2>y1)
			{
				x1=x1-(int)(radio*Math.cos(teta));
				y1=y1-(int)(radio*Math.sin(teta));
                                
                                x2=x2+(int)(radio*Math.cos(teta));
				y2=y2+(int)(radio*Math.sin(teta));
			}
			else
			{
				x1=x1-(int)(radio*Math.cos(teta));
				y1=y1-(int)(radio*Math.sin(teta));
                                
                                x2=x2+(int)(radio*Math.cos(teta));
				y2=y2+(int)(radio*Math.sin(teta));
			}
		}
		else
		{
			if(y2>y1)
			{
				x1=x1+(int)(radio*Math.cos(teta));
				y1=y1+(int)(radio*Math.sin(teta));
                                
                                 x2=x2-(int)(radio*Math.cos(teta));
				y2=y2-(int)(radio*Math.sin(teta));
			}
			else
			{
				x1=x1+(int)(radio*Math.cos(teta));
				y1=y1+(int)(radio*Math.sin(teta));
                                
                                 x2=x2-(int)(radio*Math.cos(teta));
				y2=y2-(int)(radio*Math.sin(teta));
			}
		}
		int[] reg={x1,y1,x2,y2};
		return reg;
	}
// --------------------------------------------------------------------------------------------------
	/**
	*Presenta la información en un dialogo, y si el nodo se selecciona como nodo de 
	* consulta, entonces pone al nodo que estaba como de consulta anteriormente como 
	* Nada
	*/
	private void verInfNode()
	{	
		if(selectedN.table!=null)
		{
			NodeProperties dia=new NodeProperties(parent, selectedN);
			dia.show();
			
			//Si se eligió este nodo como de consulta, entonces, no debe haber otro nodo de consulta
			//Esto solamente en el caso de inferencia naive bayes

			/*
			if(selectedN.tipoNodo.equals(TipoNodo.CONSULTA))
			{
				Node temp=red.inicio;
				while (temp!=null)
				{
					if(!temp.name.equals(selectedN.name) && temp.tipoNodo.equals(TipoNodo.CONSULTA))	
						temp.tipoNodo=TipoNodo.NADA;
					temp=temp.sig;
				}
			}
			*/
		}
		repaint();
	}
// --------------------------------------------------------------------------------------------------
	private void verInfArc()
	{		
		ArcProperties ap=new ArcProperties(parent,"Arco",selectedA);
		ap.show();
	}
}
// --------------------------------------------------------------------------------------------------
// Fin.
// --------------------------------------------------------------------------------------------------
