package bansy21.Utilerias;
import java.util.Vector;
import java.io.*;
import bansy21.Red.*;
public class Archivo2
{
    public Archivo2()
    {
    }

    public Vector leerEjemplos(String file)throws IOException
    {
        Vector cases=new Vector();
        FileInputStream inputFile = new FileInputStream(file);
        BufferedReader archivo = new BufferedReader(new InputStreamReader(inputFile));
        String lin;
        while ((lin=archivo.readLine()) != null)
        {
            if (lin.trim().length()==0)
                continue;
            cases.addElement(lin.split(","));
        }
        archivo.close();
        return cases;
    }

    public void escribirArcos(Vector v,Node inicio,String nombre)
    {
        FileWriter f=null;
        PrintWriter sal=null;
        try
        {
            f = new FileWriter(nombre);
            sal = new PrintWriter(f);
            Node temp=inicio;
            String columnN="";
            while (temp!=null)
            {
                columnN+=temp.name;
                if (temp.sig!=null)
                    columnN+=",";
                temp=temp.sig;
            }
            sal.println(columnN);
            for (int i=0;i<v.size();i++)
                sal.println(makeString((String[])v.elementAt(i),","));
            sal.close();
            f.close();
        }
        catch (IOException e)
        {
            System.out.println("Error al escribir "+nombre+": "+e.getMessage());
        }
    }

    public void escribirDatos(Vector v, String nombre, Node n)
    {
        FileWriter f=null;
        PrintWriter sal=null;
        try
        {
            f = new FileWriter(nombre);
            sal = new PrintWriter(f);
            if (n!=null)
            {
                Node temp=n;
                String s="";
                while (temp!=null)
                {
                    s+=temp.name;
                    if (temp.sig!=null)
                        s+=",";
                    temp=temp.sig;
                }
                sal.println(s);
            }
            for (int i=0;i<v.size();i++)
                sal.println(makeString((String[])v.elementAt(i),","));
            sal.close();
            f.close();
        }
        catch (IOException e)
        {
            System.out.println("Error al escribir "+nombre+": "+e.getMessage());
        }
    }

    private String makeString(String[] ar, String separator)
    {
        String r="";
        for (int i=0;i<ar.length;i++)
        {
            if (i>0)
                r+=separator;
            r+=ar[i];
        }
        return r;
    }

    public Arco leerArcosBayes9(String nombre,Node inNode)throws IOException
    {
        Arco inicio=null;
        Arco ultimo=null;
        BufferedReader archivo = new BufferedReader(new InputStreamReader(new FileInputStream(nombre)));
        String lin;
        System.out.println("Estoy leyendo datos.."+nombre);
        while ((lin=archivo.readLine()) != null)
        {
            lin=lin.trim();
            if (lin.length()==0)
                continue;
            String[] partes=lin.split("\\s+(--|->)\\s+");
            if (partes.length!=2)
                continue;
            Node from=findNode(inNode,partes[0].trim());
            Node to=findNode(inNode,partes[1].trim());
            if (from==null || to==null)
                continue;
            Arco nuevo=new Arco();
            nuevo.from=from;
            nuevo.to=to;
            nuevo.directed=lin.indexOf("--")<0;
            if (inicio==null)
                inicio=nuevo;
            else
                ultimo.sig=nuevo;
            ultimo=nuevo;
        }
        archivo.close();
        return inicio;
    }

    private Node findNode(Node inicio,String nombre)
    {
        for (Node nodo=inicio; nodo!=null; nodo=nodo.sig)
            if (nombre.equals(nodo.name))
                return nodo;
        return null;
    }

    public void guardarProyecto(bansyProyEcoder bne, String fileName)
    {
        try
        {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
            out.writeObject(bne);
            out.close();
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }

    public bansyProyEcoder abrirProyecto(String fileName)
    {
        bansyProyEcoder bne=null;
        try
        {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
            bne = (bansyProyEcoder)in.readObject();
            in.close();
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
        catch(ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }
        return bne;
    }
}
