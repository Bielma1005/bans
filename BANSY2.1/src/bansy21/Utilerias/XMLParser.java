package bansy21.Utilerias;

import javax.xml.parsers.*;
import java.io.*;
import org.xml.sax.SAXException;
import org.w3c.dom.*;
import bansy21.Red.*;
import java.util.Vector;

public class XMLParser
{	
	Red red;
	private String errorMessage;
	public static void main(String[] args) 
	{
		System.out.println("Hello World!");
		(new XMLParser()).getRedFromXMLFile("bayes/utilerias/red.xml");
	}
	public XMLParser()
	{
		
	}
	/**
	* Parsea la red escrita en una archivo XML, y devuelve un objeto Red
	*/
	public Red getRedFromXMLFile(String fileName)
	{
		errorMessage=null;
		red=new Red();
		Document document=getDocument(fileName);
		if(document==null)
			return null;
		if(document!=null)
		{
			//obtener el elemento del documento, que en realidad es todo el documento
			Element el=document.getDocumentElement();
			//obtener la red bayesiana
			NodeList n = el.getElementsByTagName("BNMODEL");
			if(n.getLength()>0)
			{
				//
				Element bmodel=(Element)n.item(0);
				//obtener las variables
				NodeList variables=bmodel.getElementsByTagName("VARIABLES");
				if(variables.getLength()>0)
				{
					//process the varibales to obtain their properties
					processNodes(variables);
					NodeList structure = bmodel.getElementsByTagName("STRUCTURE");
					NodeList distributions = bmodel.getElementsByTagName("DISTRIBUTIONS");
					if(structure.getLength()>0)
					{
						//it has relatios 
						processStructure(structure);
					}
					if(distributions.getLength()>0)
					{
						//has tables
						processDistribution(distributions);
					}
				}
				for (int i=0;i<n.getLength();i++)
				{
					System.out.println("    "+n.item(i).getNodeName());
				}					
			}
			else
			{
				errorMessage="El archivo no contiene una red bayesiana (BNMODEL).";
				return null;
			}		
		}
		return red;
	}

	public String getErrorMessage()
	{
		return errorMessage;
	}
	/**
	*Recive el elemento en el que estan todos las variables, para ontener cada una de ellas 
	*e ir creando la lista de nodos en la red
	*/
	private void processNodes(NodeList variables)
	{
		red.inicio=new bansy21.Red.Node();
		bansy21.Red.Node temp=red.inicio;
		Element e=(Element)variables.item(0);
		NodeList nodes=e.getElementsByTagName("VAR");
		for (int i=0;i<nodes.getLength();i++)
		{
			temp.values=new Vector();
			Element var=(Element)nodes.item(i);

			//obtener los atributos
			temp.name=var.getAttribute("NAME");
			String xpos=var.getAttribute("XPOS");
			String ypos=var.getAttribute("YPOS");
			if(xpos.length()>0)
				temp.x=(new Integer(xpos)).intValue();
			if (ypos.length()>0)
				temp.y=(new Integer(ypos)).intValue();

			//obtener la descripcion
			NodeList des=var.getElementsByTagName("DESCRIPTION");
			if(des.getLength()>0 && des.item(0).getFirstChild()!=null)
				temp.description=des.item(0).getFirstChild().getNodeValue();

			//obtener los estados de la variable
			NodeList values=var.getElementsByTagName("STATENAME");
			if(values.getLength()>0)
			{
				for (int j=0;j<values.getLength();j++)
				{
					if (values.item(j).getFirstChild()==null)
						continue;
					String value=values.item(j).getFirstChild().getNodeValue();					
					if(value!=null)
						temp.values.addElement(value);					
				}
			}
			if(i!=(nodes.getLength()-1)) 
			{
				temp.sig=new bansy21.Red.Node();
				temp=temp.sig;
			}			
		}
	}
	/**
	*Recive el elemento en el que estan todos las relaciones, para ontener cada una de ellas 
	*e ir creando la lista de narcos
	*/
	private void processStructure(NodeList structure)
	{		
		Element struc=(Element)structure.item(0);
		NodeList arcos=struc.getElementsByTagName("ARC");
		int numAr=arcos.getLength();
		red.inArco=new Arco();
		Arco tempA=red.inArco;
		if(numAr>0)
		{
			for (int i=0;i<numAr;i++)
			{
				Element ar=(Element)arcos.item(i);
				String from=ar.getAttribute("PARENT");
				String to=ar.getAttribute("CHILD");
				tempA=addArco(from,to,tempA);			
				if(tempA.from != null && tempA.to != null && i<(numAr-1))
				{					
					tempA.sig=new Arco();
					tempA=tempA.sig;					
				}
			}
		}
	}
	private Arco addArco(String from, String to, Arco tempA)
	{
		bansy21.Red.Node temp=red.inicio;
		int con=0;
		do
		{
			if (temp.name.equals(from))
			{
				tempA.from=temp;
				con++;
			}
			if (temp.name.equals(to))
			{
				tempA.to=temp;
				con++;
			}
			temp=temp.sig;
		}
		while (temp!=null && con<2);
		return tempA;
	}

	/**
	*Recive el elemento en el que estan todos las probabilidades condicionales, para ontener cada una de ellas 
	*e ir llenendo las tablas de probabilidades en la lista de nodos
	*/
	private void processDistribution(NodeList nodes)
	{
		Element dist=(Element)nodes.item(0);
		NodeList tables = dist.getElementsByTagName("DIST");
		int numTables=tables.getLength();
		for (int i=0;i<numTables;i++)
		{
			//obtener la tabla
			Element table=(Element)tables.item(i);
			//OBTENER EL NOMBRE DE LA VARIBALE QUE SE TRATA
			NodeList privateNodes=table.getElementsByTagName("PRIVATE");
			if (privateNodes.getLength()==0)
				continue;
			String nameN=((Element)privateNodes.item(0)).getAttribute("NAME");
			bansy21.Red.Node temp=red.inicio;
			while (temp!=null && !temp.name.equals(nameN))
				temp=temp.sig;
			if (temp==null)
				continue;

			//OBTENER EL NODO, PARA IR GUARDANDO LOS PADRES Y SU TABLA DE PROBABILIDAD

			//obatener el conjunto condicioonante
			NodeList condSet=table.getElementsByTagName("CONDSET");
			if (condSet.getLength()>0)
			{
				//tiene padres
				Element condS=(Element)condSet.item(0);
				NodeList elem=condS.getElementsByTagName("CONDELEME");
				int c=elem.getLength();
				String[] names=new String[c];
				for (int j=0;j<c;j++)
				{
					names[j]=((Element)elem.item(j)).getAttribute("NAME");
				}
				addParents(names, temp);
			}
			//obtener las probabilidades
			NodeList dpis=table.getElementsByTagName("DPIS");
			if (dpis.getLength()==0)
				continue;
			Element dpisE=(Element)dpis.item(0);
			NodeList lines=dpisE.getElementsByTagName("DPI");
			int con=lines.getLength();
			temp.table=new Double[con][temp.values.size()];
			for (int t=0;t<con;t++)
			{
				if (lines.item(t).getFirstChild()==null)
					continue;
				String lin=lines.item(t).getFirstChild().getNodeValue();
				lin=lin.substring(1);				
				String[] line=lin.split(" ");
				for (int j=0;j<line.length;j++)
				{
					if(line[j].length()>0)
					{				
						temp.table[t][j]=new Double(line[j]);
					}
				}				
			}

		}
	}

	private void addParents(String[] names, bansy21.Red.Node node)
	{
		bansy21.Red.Node temp=red.inicio;
		do
		{
			for (int i=0;i<names.length;i++)
			{
				if(temp.name.equals(names[i]))
					node.parents.addElement(temp);
			}
			temp=temp.sig;
		}
		while (temp!=null);
	}

	/**
	*Abre el archivo XML, y devuelve un objeto Document
	*/
	private Document getDocument(String fileName)
	{
		Document document=null;
		DocumentBuilder builder;
		DocumentBuilderFactory factory =
		DocumentBuilderFactory.newInstance();		
		try {
			builder = factory.newDocumentBuilder();
			File f=new File(fileName);			
			document = builder.parse(f);
		}	catch (SAXException se) 
		{
			errorMessage="El XML no tiene un formato válido: "+se.getMessage();
		} catch (IOException ioe) {
			errorMessage="No se pudo abrir el archivo: "+ioe.getMessage();
		} catch (ParserConfigurationException pce) {
			errorMessage="No se pudo configurar el lector XML: "+pce.getMessage();
		}
		return document;
	}
}
