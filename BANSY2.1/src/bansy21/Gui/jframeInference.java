/*
 * jframeInference.java
 *
 * Created on 22 de enero de 2007, 12:20
 */

package bansy21.Gui;

import bansy21.Red.*;
import bansy21.Utilerias.JointProbability;
import com.sun.org.apache.xpath.internal.operations.Bool;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.ListModel;
import java.util.Vector;

/**
 Esta clase se utiliza para la inferencia
 * @author  jjimenez
 */
public class jframeInference extends javax.swing.JInternalFrame {
    
   
    /**El modelo para la lista de los nodos evidencia*/
    private DefaultListModel listModelEvidence;
     /** El modelo para la lista de los nodos consulta */
    private DefaultListModel listModelConsulta;
    
    /** La red Bayesiana */
    private Red red= null;
    
    /** Nodo evidencia seleccionado */
    private Node selectedEvidenceNode=null;
    /** El indice del nodo evidencia seleccionado en la lista de nodos */
    private int selectedEvidenceElement=-1;
    
    /** Nodo Consulta seleccionado */
    private Node selectedConsultaNode=null;
    /** El indice del nodo Consulta seleccionado en la lista de nodos */
    private int selectedConsultaElement=-1;
    
    /** el texto de la probabilidad que se esta calculando.*/
    private String currentOutputString="";
    /** El conjunto de probabilidades que se han calculado */
    private Vector outPut=new Vector();
    
    /** El objecto para calcular probabilidades*/
    public JointProbability jp=null;
    
    
    /** Creates new form jframeInference */
    public jframeInference() {
        initComponents();
    }
    
    /** Creates new form jframeInference */
    public jframeInference(Red red_) {
        initComponents();
        red=red_;
        addVariablestoCombo();
        
        listModelEvidence = new DefaultListModel();
        jListEvidence.setModel(listModelEvidence);
        
         listModelConsulta = new DefaultListModel();
        jListConsulta.setModel(listModelConsulta);
    }
    
    
// <editor-fold defaultstate="collapsed" desc=" Metodos para generar el texto de las probabilidades ">
    
    /** Este metodo se encarga de actualizar el texto de probabilidad que se esta calculando*/
    public void calProb()
	{
		Vector vj=new Vector();
		Vector vc=new Vector();
		Node temp=red.inicio;
		while (temp!=null)
		{
			//System.out.println("sgfhfgb  "+temp.name);
			if (temp.tipoNodo.equals(TipoNodo.EVIDENCIA))
			{
				vc.addElement(temp);
			}
			else if (temp.tipoNodo.equals(TipoNodo.CONSULTA))
			{
				vj.addElement(temp);
			}		
			temp=temp.sig;
		}
		
		Node[] js = new Node[vj.size()];
		vj.copyInto(js);
		Node[] cs=new Node[vc.size()];
		vc.copyInto(cs);
	
		String sal="P(";
		for (int i=0;i<js.length;i++)
		{
			sal+=js[i].name;
			sal+=" = "+js[i].values.elementAt(js[i].selectedValue);
			if (i!=(js.length-1))
			{
				sal+=", ";
			}
		}
		if(cs.length>0)
			sal+=" | ";
	
		for (int i=0;i<cs.length;i++)
		{
			sal+=cs[i].name;
			sal+=" = "+cs[i].values.elementAt(cs[i].selectedValue);
			if (i!=(cs.length-1))
			{
				sal+=", ";
			}
		}
      
		sal+=") = ";
      if (js.length>0)
		{
			double prob=jp.calculateConditionalProbability(js,cs);
			sal+=""+prob;
      }
		currentOutputString="";
      outPut.add(0, sal);
      upDateOutPut();
    }
    
    /** Este metodo se encarga de actualizar el texto de probabilidad que se esta calculando*/
    public void upDateOutputString()
	{
		Vector vj=new Vector();
		Vector vc=new Vector();
		Node temp=red.inicio;
		while (temp!=null)
		{
			//System.out.println("sgfhfgb  "+temp.name);
			if (temp.tipoNodo.equals(TipoNodo.EVIDENCIA))
			{
				vc.addElement(temp);
			}
			else if (temp.tipoNodo.equals(TipoNodo.CONSULTA))
			{
				vj.addElement(temp);
			}		
			temp=temp.sig;
		}
		
		Node[] js = new Node[vj.size()];
		vj.copyInto(js);
		Node[] cs=new Node[vc.size()];
		vc.copyInto(cs);
	
		String sal="P(";
		for (int i=0;i<js.length;i++)
		{
			sal+=js[i].name;
			sal+=" = "+js[i].values.elementAt(js[i].selectedValue);
			if (i!=(js.length-1))
			{
				sal+=", ";
			}
		}
		if(cs.length>0)
			sal+=" | ";
	
		for (int i=0;i<cs.length;i++)
		{
			sal+=cs[i].name;
			sal+=" = "+cs[i].values.elementAt(cs[i].selectedValue);
			if (i!=(cs.length-1))
			{
				sal+=", ";
			}
		}
      
		sal+=") = ";		 
		currentOutputString=sal;
      upDateOutPut();
    }
    
    /** Genera el texto total para ponerlo en el area de texto. Usa los elementos del vector outPut*/
    private void upDateOutPut()
    {
        String sal=currentOutputString+"\n";        
        for(int i=0;i<outPut.size();i++)
            sal+=outPut.elementAt(i)+"\n";
        
        jTextPaneSalida.setText(sal);
    }
    
    // </editor-fold>
    
    /** Region ocul:*/
// <editor-fold defaultstate="collapsed" desc=" Codigo para manejar las lista de variables consulta y evidencia ">
    
    /** Agrega las variables de la red al combo principal*/
    private void addVariablestoCombo()
    {
      Node temp=red.inicio;
      while (temp!=null)
		{
			//Agregar al nombre del nodo al combo
         jComboBoxVariables.addItem(temp.name);
			temp=temp.sig;
		}
    }
    
    /** Agrega la variable seleccionada en el combo de las variables a la lista de variables evidencia*/
    private void addVariableEvidencia()
    {
         Node temp=red.inicio;
      while (temp!=null)
		{          
			//Agregar al nombre del nodo al combo
         if(temp.name.equals(jComboBoxVariables.getSelectedItem()))
         {
             if(!listModelEvidence.contains(temp.name) && !listModelConsulta.contains(temp.name) )
                 listModelEvidence.addElement(temp.name);
         }            
			temp=temp.sig;
		}
    }
    
    /** Quita la varibale seleccionada en la lista de evidencia*/
     private void quitarVariableEvidencia()
    {         
      Node temp=red.inicio;
      while (temp!=null)
		{          
			//Agregar al nombre del nodo al combo
         if(temp.name.equals(listModelEvidence.elementAt(selectedEvidenceElement)))
         {
          listModelEvidence.removeElement(temp.name);          
          temp.tipoNodo=TipoNodo.NADA;
          temp.selectedValue=-1;
          selectedEvidenceNode=null;
          break;
         }            
			temp=temp.sig;
		}
      
    }
    
     /** llena el combo de valores de la varibale evidencia con los dvalores de la variable seleccionada en la lista
      de variables de evidencia*/
    private void addValoresVariableEvidencia()
    { 
        jComboBoxEvidencia.removeAllItems();
        if(selectedEvidenceElement>=0)
        {
          Node temp=red.inicio;          
          while (temp!=null)
          {
             //Agregar al nombre del nodo al combo
             if(temp.name.equals(listModelEvidence.elementAt(selectedEvidenceElement)))
             {
                 //temp.values.co
                 for(int i=0;i<temp.values.size();i++)
                    jComboBoxEvidencia.addItem(temp.values.elementAt(i));
                 if(temp.selectedValue==-1)
                 {
                    //Es la primera ves que se estan agregando los datos
                    jComboBoxEvidencia.setSelectedIndex(0);                 
                    temp.selectedValue=0;
                 }else
                 {
                     //Se esta cambiando de variable en la lista y ya se habia seleccionado un valor 
                    jComboBoxEvidencia.setSelectedIndex(temp.selectedValue);
                 }
                 selectedEvidenceNode=temp;
                 selectedEvidenceNode.tipoNodo=TipoNodo.EVIDENCIA;
                 break;
             }
             temp=temp.sig;
          }
        }           
    }
   
    /** Agrega la variable seleccionada en el combo de variables a la lista de variables de consulta*/
    private void addVariableConsulta()
    {
      Node temp=red.inicio;
      while (temp!=null)
		{          
			//Agregar al nombre del nodo al combo
         if(temp.name.equals(jComboBoxVariables.getSelectedItem()))
         {          
            if(!listModelConsulta.contains(temp.name) && !listModelEvidence.contains(temp.name))
                listModelConsulta.addElement(temp.name);
         }            
			temp=temp.sig;
		}
    }
    
    /** Quita la varibal seleccionada de la lista de variables de consulta*/
     private void quitarVariableConsulta()
    {         
      Node temp=red.inicio;
      while (temp!=null)
		{          
			//Agregar al nombre del nodo al combo
         if(temp.name.equals(listModelConsulta.elementAt(selectedConsultaElement)))
         {
          listModelConsulta.removeElement(temp.name);
          temp.tipoNodo=TipoNodo.NADA;
          temp.selectedValue=-1;
          selectedConsultaNode=null;
          break;
         }            
			temp=temp.sig;
		}
      
    }
    
     /** agrega los varlores de la variable seleccionada en la lista de  variables de consulta
      al combo de valores de vairbale sde consulta*/
    private void addValoresVariableConsulta()
    { 
        jComboBoxConsulta.removeAllItems();
        if(selectedConsultaElement>=0)
        {
          Node temp=red.inicio;          
          while (temp!=null)
          {
             //Agregar al nombre del nodo al combo
             if(temp.name.equals(listModelConsulta.elementAt(selectedConsultaElement)))
             {
                 //temp.values.co
                 for(int i=0;i<temp.values.size();i++)
                    jComboBoxConsulta.addItem(temp.values.elementAt(i));
                 if(temp.selectedValue==-1)
                 {
                    //Es la primera ves que se estan agregando los datos
                    jComboBoxConsulta.setSelectedIndex(0);                 
                    temp.selectedValue=0;
                 }else
                 {
                     //Se esta cambiando de variable en la lista y ya se habia seleccionado un valor 
                    jComboBoxConsulta.setSelectedIndex(temp.selectedValue);
                 }
                 selectedConsultaNode=temp;
                 selectedConsultaNode.tipoNodo=TipoNodo.CONSULTA;
                 break;
             }
             temp=temp.sig;
          }
        }           
    }
  // </editor-fold>
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jComboBoxVariables = new javax.swing.JComboBox();
        jLabelEvidence = new javax.swing.JLabel();
        jButtonAgregarEvidencia = new javax.swing.JButton();
        jLabelConsulta = new javax.swing.JLabel();
        jButtonAgregarConsulta = new javax.swing.JButton();
        jComboBoxEvidencia = new javax.swing.JComboBox();
        jComboBoxConsulta = new javax.swing.JComboBox();
        jScrollPaneEvidencia = new javax.swing.JScrollPane();
        jListEvidence = new javax.swing.JList();
        jButtonQuitarEv = new javax.swing.JButton();
        jScrollPanelConsulta = new javax.swing.JScrollPane();
        jListConsulta = new javax.swing.JList();
        jButtonQuitarCon = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPaneSalida = new javax.swing.JTextPane();
        jLabelEvidence1 = new javax.swing.JLabel();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Inferencia");
        addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                propertyChangeHandler(evt);
            }
        });

        getContentPane().add(jComboBoxVariables, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 250, -1));

        jLabelEvidence.setText("Evidencia");
        getContentPane().add(jLabelEvidence, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, -1));

        jButtonAgregarEvidencia.setText("Agregar");
        jButtonAgregarEvidencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAgregarEvidenciaActionPerformed(evt);
            }
        });

        getContentPane().add(jButtonAgregarEvidencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, -1, -1));

        jLabelConsulta.setText("Consulta");
        getContentPane().add(jLabelConsulta, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 50, -1, -1));

        jButtonAgregarConsulta.setText("Agregar");
        jButtonAgregarConsulta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAgregarConsultaActionPerformed(evt);
            }
        });

        getContentPane().add(jButtonAgregarConsulta, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 40, -1, -1));

        jComboBoxEvidencia.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                itemStateChan(evt);
            }
        });

        getContentPane().add(jComboBoxEvidencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, 240, -1));

        jComboBoxConsulta.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                valorSeleccionadoConsulta(evt);
            }
        });

        getContentPane().add(jComboBoxConsulta, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 300, 240, -1));

        jScrollPaneEvidencia.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jListEvidence.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jListEvidence.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                valorCambiadoEv(evt);
            }
        });

        jScrollPaneEvidencia.setViewportView(jListEvidence);

        getContentPane().add(jScrollPaneEvidencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 240, 220));

        jButtonQuitarEv.setText("Quitar");
        jButtonQuitarEv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonQuitarEvActionPerformed(evt);
            }
        });

        getContentPane().add(jButtonQuitarEv, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 40, -1, -1));

        jScrollPanelConsulta.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jListConsulta.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jListConsulta.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                valorConsultaCambiado(evt);
            }
        });

        jScrollPanelConsulta.setViewportView(jListConsulta);

        getContentPane().add(jScrollPanelConsulta, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 70, 240, 220));

        jButtonQuitarCon.setText("Quitar");
        jButtonQuitarCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonQuitarConActionPerformed(evt);
            }
        });

        getContentPane().add(jButtonQuitarCon, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 40, -1, -1));

        jButton1.setText("Calcular");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, -1, -1));

        jButton2.setText("Borrar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 330, 70, -1));

        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setHorizontalScrollBar(null);
        jTextPaneSalida.setBorder(new javax.swing.border.EtchedBorder());
        jScrollPane1.setViewportView(jTextPaneSalida);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, 490, 140));

        jLabelEvidence1.setText("Variables");
        getContentPane().add(jLabelEvidence1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        pack();
    }
    // </editor-fold>//GEN-END:initComponents

// <editor-fold defaultstate="collapsed" desc=" Manejadores de eventos ">    
    
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
// TODO add your handling code here:
        outPut.removeAllElements();
        upDateOutputString();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
// TODO add your handling code here:
        //double prob=jp.
        calProb();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void propertyChangeHandler(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_propertyChangeHandler
// TODO add your handling code here:
        upDateOutputString();
    }//GEN-LAST:event_propertyChangeHandler

    private void valorSeleccionadoConsulta(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_valorSeleccionadoConsulta
// TODO add your handling code here:
        int selected=jComboBoxConsulta.getSelectedIndex();
        if(selected>=0 && selectedConsultaNode!=null)
            selectedConsultaNode.selectedValue=selected;
          upDateOutputString();
    }//GEN-LAST:event_valorSeleccionadoConsulta

    private void valorConsultaCambiado(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_valorConsultaCambiado
// TODO add your handling code here:
          //cambia el nodo Consulta seleccionado y manda a llamar el metodo encargado de llenar el combo asociado
        selectedConsultaElement=jListConsulta.getSelectedIndex();
        selectedConsultaNode=null;
        addValoresVariableConsulta();
          upDateOutputString();
    }//GEN-LAST:event_valorConsultaCambiado

    private void jButtonQuitarConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonQuitarConActionPerformed
// TODO add your handling code here:
        quitarVariableConsulta();
          upDateOutputString();
    }//GEN-LAST:event_jButtonQuitarConActionPerformed

    private void jButtonAgregarConsultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarConsultaActionPerformed
// TODO add your handling code here:
        addVariableConsulta();
          upDateOutputString();
    }//GEN-LAST:event_jButtonAgregarConsultaActionPerformed

    private void itemStateChan(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_itemStateChan
// TODO add your handling code here:
        int selected=jComboBoxEvidencia.getSelectedIndex();
        if(selected>=0 && selectedEvidenceNode!=null)
            selectedEvidenceNode.selectedValue=selected;
          upDateOutputString();
    }//GEN-LAST:event_itemStateChan

    private void jButtonQuitarEvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonQuitarEvActionPerformed
// TODO add your handling code here:
        quitarVariableEvidencia();
          upDateOutputString();
    }//GEN-LAST:event_jButtonQuitarEvActionPerformed

    private void valorCambiadoEv(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_valorCambiadoEv
// TODO add your handling code here:
        //cambia el nodo evidencia seleccionado y manda a llamar el metodo encargado de llenar el combo asociado
        selectedEvidenceElement=jListEvidence.getSelectedIndex();
        selectedEvidenceNode=null;
        addValoresVariableEvidencia();
          upDateOutputString();
    }//GEN-LAST:event_valorCambiadoEv

    private void jButtonAgregarEvidenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarEvidenciaActionPerformed
// TODO add your handling code here:
        addVariableEvidencia();
        upDateOutputString();
    }//GEN-LAST:event_jButtonAgregarEvidenciaActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new jframeInference().setVisible(true);
            }
        });
    }
    
    // </editor-fold>
    
// <editor-fold defaultstate="collapsed" desc="Declaracion de los atributos (variables) de la clase">
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButtonAgregarConsulta;
    private javax.swing.JButton jButtonAgregarEvidencia;
    private javax.swing.JButton jButtonQuitarCon;
    private javax.swing.JButton jButtonQuitarEv;
    private javax.swing.JComboBox jComboBoxConsulta;
    private javax.swing.JComboBox jComboBoxEvidencia;
    private javax.swing.JComboBox jComboBoxVariables;
    private javax.swing.JLabel jLabelConsulta;
    private javax.swing.JLabel jLabelEvidence;
    private javax.swing.JLabel jLabelEvidence1;
    private javax.swing.JList jListConsulta;
    private javax.swing.JList jListEvidence;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPaneEvidencia;
    private javax.swing.JScrollPane jScrollPanelConsulta;
    private javax.swing.JTextPane jTextPaneSalida;
    // End of variables declaration//GEN-END:variables
// </editor-fold > 
    
}
