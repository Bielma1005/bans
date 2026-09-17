package bansy21.Gui;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.*;
import java.io.File;

import bansy21.Red.*;
import bansy21.Utilerias.ExampleFileFilter;

public class DCrossValidation extends JDialog implements ActionListener
{
	public static void main(String[] args) 
	{
		System.out.println("Hello World!");
	}

	public JButton bFile;

	public JCheckBox naive;
	public JCheckBox bayesn;
	public JCheckBox bayes9;
	public JCheckBox bayes;
	public JCheckBox bayes2;
	public JCheckBox bayes5;
	public JCheckBox guardar;


	//TextFilds, parameters bayesN

	public JTextField tAlfa;
  public JTextField tProfundidad;
  public JTextField tIMin;
	public JTextField textFolds;
	public JTextField textFile;	

	JButton bAceptar=new JButton("Aceptar");
	JButton bCancelar=new JButton("Cancelar");

	public double alfa=5;
	public int profundidad=3;
	public double iMin=5;

	public boolean aceptar=true;
	public boolean cross=true;

	/**
	*este es el archivo	que el usuario tiene que esoger
	*/
	public String directory="";

	public String baseName="";

	JFileChooser chooser=null;

	/**
	*Constructor
	*/
	public DCrossValidation(JFrame parent,String title, boolean cross_)
	{
		//el contentPane seráun TabbedPane
		/*LLeva el 
		panel cross validation		
			holds (label, textField)
			file  (label, textFiedl, button(detalles, para seleccionar el archivo))
			algoritmos (checkBoxes) Naive Bayes,BayesN, Bayes9, ....
			guradar conjuntos(checkBox)
		
		Panel de parametros para el BayesN
		*/
		//El tabbedPane va en el centro del contentPane
		//en laparte de abajo los botones de aceptar y cancelar
		
		
		super(parent,title,true);
		cross=cross_;
		chooser=new JFileChooser();
		File f=new File("./");
		if (f.exists())
		{
			chooser.setCurrentDirectory(f);
		}
		
		JTabbedPane tabbedPane = new JTabbedPane();
		Component tabCross = getPanelCross();
		Component tabBayesn = getPanelBayesN();
		Component tabButtons = getPanelButtons();

		
		if (cross)
		{		
			tabbedPane.addTab("Cross-Validation",null,tabCross,"Cross-Validaton parameters");
		}
		else
		{
			tabbedPane.addTab("Hold-Out",null,tabCross,"Hold-Out parameters");
		}

		tabbedPane.addTab("Bayes-N",null,tabBayesn,"Bayes-N parameters");

		JPanel content=(JPanel)getContentPane();
		content.add(tabbedPane,BorderLayout.CENTER);
		content.add(tabButtons,BorderLayout.SOUTH);

		bAceptar.addActionListener(this);
		bCancelar.addActionListener(this);
		bFile.addActionListener(this);

		pack();
		Rectangle screenRect = this.getGraphicsConfiguration().getBounds();
		this.setLocation(		screenRect.x + screenRect.width/2 - this.getSize().width/2,
			screenRect.y + screenRect.height/2 - this.getSize().height/2);	   
		show();
	}

	
	/**
	*
	*/
	private Component getPanelButtons()
	{
		JPanel p1=new JPanel();
		p1.add(bAceptar,BorderLayout.CENTER);
		JPanel p2=new JPanel();
		p2.add(bCancelar,BorderLayout.CENTER);


		JPanel p=new JPanel();

		p.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		p.setLayout(new BorderLayout());
    p.add(p1, BorderLayout.CENTER);
		p.add(p2, BorderLayout.EAST);

		JPanel panel=new JPanel();
		panel.add(p,BorderLayout.CENTER);
	
		return panel;
	}

	/**
	* Devuelve el panel con los componentes para los parametros del algoritmo bayesN
	*/
	public Component getPanelBayesN()
	{

		JPanel labelPane=new JPanel();
		labelPane.setLayout(new GridLayout(0, 1));

		JLabel l1=new JLabel("Alfa global:(%)");
		JLabel l2=new JLabel("Profundidad:");
		JLabel l3=new JLabel("% de Ganancia de Inf. Minima:");
		
		//l1.setHorizontalTextPosition(SwingConstants.LEFT);
	//	l2.setHorizontalTextPosition(SwingConstants.LEFT);
	//	l3.setHorizontalTextPosition(SwingConstants.LEFT);

JPanel p1=new JPanel();
		/*
		JPanel p1=new JPanel();
		p1.add(l1,BorderLayout.WEST);
		p1.setAlignmentX(0);
		*/
		labelPane.add(l1);

		/*
		p1=new JPanel();
		p1.add(l2,BorderLayout.WEST);
		p1.setAlignmentX(0);
		*/
		labelPane.add(l2);
		/*
		p1=new JPanel();
		p1.add(l3,BorderLayout.WEST);
		p1.setAlignmentX(0);
		*/
		labelPane.add(l3);


		tAlfa=new JTextField(""+alfa,10);
		tProfundidad=new JTextField(""+profundidad,10);	
		tIMin=new JTextField(""+iMin,10);
		
		JPanel textPane=new JPanel();
		textPane.setLayout(new GridLayout(0, 1));
		p1=new JPanel();
		p1.add(tAlfa,BorderLayout.CENTER);
		textPane.add(p1);

		p1=new JPanel();
		p1.add(tProfundidad,BorderLayout.CENTER);
		textPane.add(p1);

		p1=new JPanel();
		p1.add(tIMin,BorderLayout.CENTER);
		textPane.add(p1);

		//labels, Textfields
		JPanel p=new JPanel();
		p.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		p.setLayout(new BorderLayout());
    p.add(labelPane, BorderLayout.CENTER);
		p.add(textPane, BorderLayout.EAST);	

	
		JPanel panel=new JPanel();
		panel.add(p,BorderLayout.CENTER);
		
		return panel;
	}
	/**
	*Hacer el panel de crossValidation
	*/
	private Component getPanelCross()
	{			
		
		//Etiquetas
		JPanel labelPane=new JPanel();
		labelPane.setLayout(new GridLayout(2, 0));
		JLabel  labelFolds;
		if (cross)
		{		
	    labelFolds= new JLabel("Folds");
		}
		else
		{
			labelFolds= new JLabel("% Training");
		}

		JLabel  labelFile= new JLabel("Data");

		JPanel pa=new JPanel();
		//pa.add(labelFolds,BorderLayout.CENTER);		
		labelPane.add(labelFolds);
		/*
		pa=new JPanel();
		pa.add(labelFile,BorderLayout.CENTER);
		*/
		labelPane.add(labelFile);

		//TextFields
		JPanel textPane =new JPanel();
		textPane.setLayout(new GridLayout(2,0));
		if (cross)
		{
			textFolds=new JTextField("5",10);
		}
		else
		{
			textFolds=new JTextField("66.66",10);
		}

		textFile=new JTextField("",10);

		pa=new JPanel();
		pa.add(textFolds,BorderLayout.CENTER);
		textPane.add(pa);

		pa=new JPanel();
		pa.add(textFile,BorderLayout.CENTER);
		textPane.add(pa);

		//buttons
		JPanel buttonPane=new JPanel();
		buttonPane.setLayout(new GridLayout(2,0));
		JLabel label=new JLabel("");
		bFile=new JButton("...");

		pa=new JPanel();
		pa.add(label,BorderLayout.CENTER);
		buttonPane.add(pa);

		pa=new JPanel();
		pa.add(bFile,BorderLayout.CENTER);
		buttonPane.add(pa);	

		//labels, Textfields & buttons
		JPanel p=new JPanel();
		p.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		p.setLayout(new BorderLayout());
		p.add(labelPane, BorderLayout.WEST);
    p.add(textPane, BorderLayout.CENTER);
		p.add(buttonPane, BorderLayout.EAST);

		JPanel pc=new JPanel();
		pc.add(p,BorderLayout.CENTER);

		/*****************Algoritmos*******/

		naive=new JCheckBox("Naive bayes");
		naive.setMnemonic(KeyEvent.VK_A);
		naive.setSelected(true);

		bayesn=new JCheckBox("Bayes-N");
		bayesn.setMnemonic(KeyEvent.VK_N);
		bayesn.setSelected(true);

		bayes9=new JCheckBox("Bayes-9");
		bayes9.setMnemonic(KeyEvent.VK_9);
		bayes9.setSelected(true);
		

		bayes=new JCheckBox("Bayes");
		bayes.setMnemonic(KeyEvent.VK_B);
		bayes.setSelected(false);	
		bayes.setEnabled(false);

		bayes2=new JCheckBox("Bayes-2");
		bayes2.setMnemonic(KeyEvent.VK_2);
		bayes2.setSelected(false);
		bayes2.setEnabled(false);
		
		bayes5=new JCheckBox("Bayes-5");
		bayes5.setMnemonic(KeyEvent.VK_5);
		bayes5.setSelected(false);
		bayes5.setEnabled(false);
		
		/*
		//Establecer el alineamiento
		naive.setHorizontalAlignment(SwingConstants.LEFT);
		bayesn.setHorizontalAlignment(SwingConstants.LEFT);
		bayes9.setHorizontalAlignment(SwingConstants.LEFT);
		bayes.setHorizontalAlignment(SwingConstants.LEFT);
		bayes2.setHorizontalAlignment(SwingConstants.LEFT);
		bayes5.setHorizontalAlignment(SwingConstants.LEFT);
	*/

		JPanel checkPanel=new JPanel();
		checkPanel.setLayout(new GridLayout(6,0));
		/*
		pa=new JPanel();
		pa.add(naive,BorderLayout.CENTER);
		*/
		checkPanel.add(naive);

/*
		pa=new JPanel();
		pa.add(bayesn,BorderLayout.CENTER);
		*/
		checkPanel.add(bayesn);
/*
		pa=new JPanel();
		pa.add(bayes9,BorderLayout.CENTER);*/
		checkPanel.add(bayes9);

/*		pa=new JPanel();
		pa.add(bayes,BorderLayout.CENTER);*/
		checkPanel.add(bayes);

/*		pa=new JPanel();
		pa.add(bayes2,BorderLayout.CENTER);*/
		checkPanel.add(bayes2);

/*		pa=new JPanel();
		pa.add(bayes5,BorderLayout.CENTER);*/
		checkPanel.add(bayes5);

		JPanel pcheck=new JPanel();
		pcheck.add(checkPanel,BorderLayout.CENTER);
		

		/********    save sets  **********/

		JPanel check2Panel = new JPanel();
		guardar=new JCheckBox("Save sets");
		guardar.setMnemonic(KeyEvent.VK_S);
		guardar.setSelected(false);
		guardar.setHorizontalAlignment(SwingConstants.LEFT);
		check2Panel.add(guardar,BorderLayout.CENTER);

		//este es el panel que contiene todo
		JPanel panel = new JPanel(false);
		//panel.setLayout(new GridLayout(3,1));
		panel.add(pc,BorderLayout.NORTH);
		panel.add(checkPanel,BorderLayout.CENTER);
		panel.add(check2Panel,BorderLayout.SOUTH);
    return panel;
	}

	public void actionPerformed(java.awt.event.ActionEvent event)
	{
		Object objeto=event.getSource();
		if (objeto == bAceptar)
		{
			if (baseName.equals(""))
			{
				//send a message, the file have not been choosen
				JOptionPane.showMessageDialog(this,"the file have not been choosen","Data",JOptionPane.WARNING_MESSAGE);
			}
			else
			{
				File f=new File(textFile.getText());
				if (f.exists())
				{
					this.dispose();
				}
				else
				{
					JOptionPane.showMessageDialog(this,"the file does not exists","Data",JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		else if (objeto == bCancelar)
		{
			aceptar=false;
			this.dispose();
		}
		else if (objeto == bFile)
		{
			showFileChooser();
		}
	}	
	
	private void showFileChooser()
	{
		ExampleFileFilter txtFilter = new ExampleFileFilter("txt", "Text Files");
		chooser.setFileFilter(txtFilter);
		chooser.setDialogType(JFileChooser.OPEN_DIALOG);
    int returnVal = chooser.showOpenDialog(this);
    if(returnVal == JFileChooser.APPROVE_OPTION) 
		{
			directory = chooser.getSelectedFile().getPath();
			baseName = chooser.getSelectedFile().getName();
			textFile.setText(directory);
		}
	}
}
