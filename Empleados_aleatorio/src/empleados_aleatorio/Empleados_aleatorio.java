/*
 * TAREA AD02.EJERCICIO 1.
 * 1- Crear un fichero EMPLEADOS.DAT de acceso aleatorio, que contenga 
 * al menos cinco empleados. Dicho fichero contendrá los campos siguientes: 
 * CODIGO (int), NOMBRE (string), DIRECCION (string), SALARIO (float) 
 * y COMISION (float).
 * 2- A partir de los datos del fichero EMPLEADOS.DAT crear un fichero 
 * llamado EMPLEADOS.XML usando DOM.
 */
package empleados_aleatorio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;


/**
 *
 * @author juang <juangmuelas@gmail.com>
 * @since 11/11/2021
 */
public class Empleados_aleatorio {

    /**
     * @param args the command line arguments
     */
       
        
    public static void main(String[] args) throws IOException {
        
        /**
         * PARTE 1.
         * @param empleados Array de datos para empleados.
         * Se ha generado una clase Empleados.java para operar con sus datos.
         */
         ArrayList<Empleados> empleados = new ArrayList<>();
         
        /**
         * Añadimos los datos para 5 empleados.
         */
        empleados.add(new Empleados(1,"Antonio Perez","Av Principal 9", 985.12f,11));
        empleados.add(new Empleados(2,"Manuel Perez","Av Principal 9", 1102.23f,15));
        empleados.add(new Empleados(3,"Ana Perez","Plaza Ayuntamiento 8", 985.12f,11));
        empleados.add(new Empleados(4,"María Perez","C Los Obeliscos 5", 1102.23f,15));
        empleados.add(new Empleados(5,"Carla Perez","Av Mar 21", 1114.89f,5));
        
        /**
         * Creamos nuestro archivo EMPLEADOS.dat desde un try-catch 
         * que envuelve a nuestro randomAccessFile.
         * Haciéndolo así hacemos un cerrado automático.
         * Valores en bytes a recordar:
         * double (8 bytes), long (8 bytes), 
         * int (4 bytes), float(4 bytes),
         * char (2 bytes),short (2 bytes), 
         * byte (1 byte), boolean (1 bit)
         */
        try (RandomAccessFile raf = new RandomAccessFile("EMPLEADOS.dat", "rw")) {
            //recorremos los array
            for( Empleados e : empleados){
                raf.writeInt(e.getCodigo()); //4 bytes
                //Para un correcto acceso aleatorio, vamos a limitar los campos
                StringBuffer sb = new StringBuffer(e.getNombre()); //40 bytes
                sb.setLength(20);
                raf.writeChars(sb.toString());
                StringBuffer sbD = new StringBuffer(e.getDireccion()); //40 bytes
                sbD.setLength(20);
                raf.writeChars(sbD.toString());
                raf.writeFloat(e.getSalario()); //4 bytes
                raf.writeFloat(e.getComision()); //4 bytes
            }
            
            /**
             * La suma de los campos nos da 92 bytes por registro.
             * Realizo una prueba para comprobar el correcto acceso.
             */
            raf.seek(92); //posicion 2
            System.out.println("Datos de posición " + raf.readInt() + ".");
            /**
             * Para los Strings se realiza un for que recorre los bytes
             * delimitados, sumando sus caracteres
             */
            String nombre ="";
            for(int i = 0; i < 20; i++){
                nombre += raf.readChar();       
            }
            String direccion ="";
            for(int i = 0; i < 20; i++){
                direccion += raf.readChar();
            }
            
            System.out.println(nombre);
            System.out.println(direccion);
            System.out.println(raf.readFloat());
            System.out.println(raf.readFloat());
            
            System.out.println("Datos de posición mostrados. \n**************");
                  
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            System.err.println("\nError de escritura\n");
        } //fin bloque try-catch para EMPLEADOS.dat
        
        /**
         * PARTE 2.
         * CREANDO XML usando DOM.
         * Declaramos las variables necesarias.
         * @param posicion integer para posicionar nuestro puntero.
         * Los siguientes parametros recogen los datos de empleados.
         * @param codigo integer.
         * @param nombre String.
         * @param direccion String.
         * @param salario float.
         * @param comision float.
         * @param nombreN array que recoge los caracteres del campo nombre.
         * @param direccionD array que recoge los caracteres del campo direccion.
         */
       
        int posicion=0;
        int codigo;
        String nombre="";
        String direccion="";
        float salario;
        float comision;
        char nombreN[] = new char[20], aux;
        char direccionD[] = new char[20], aux1;
        
        /**
         * Se crea una instancia DocumentBuilderFactory, construyendo el
         * parser en un try-catch, donde accedemos al archivo EMPLEADOS.dat,
         * igual que hicimos en el punto anterior, pero en este caso,
         * solo para lectura.
         */
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try (RandomAccessFile raf = new RandomAccessFile("EMPLEADOS.dat", "r")) {
            DocumentBuilder builder = factory.newDocumentBuilder();
            //Creamos un documento con el nodo raiz "Empleados".
            DOMImplementation implementation = builder.getDOMImplementation();
            Document document = implementation.createDocument(null, "Empleados", null);
            document.setXmlVersion("1.0"); //Asignamos versión XML
            
            /**
             * Para la recogida de datos, uso casi la misma estructura
             * utilizada en la parte anterior.
             * Se envuelve todo en un for que recorre mientras encuentre datos
             */  
            for(;;){             
                raf.seek(posicion);//nos posicionamos
                codigo = raf.readInt();//obtenemos codigo empleado
                for(int i = 0; i < 20; i++){
                    aux = raf.readChar();
                    nombreN[i]=aux;
                    nombre = new String(nombreN);
                }
                for(int i = 0; i < 20; i++){
                    aux1 = raf.readChar();
                    direccionD[i]=aux1;
                    direccion = new String(direccionD);
                }
                
                salario = raf.readFloat();
                comision = raf.readFloat();
                
                //los muestro en consola para seguir la correcta toma de datos
                System.out.println("posicion "+ posicion);
                System.out.println("CODIGO: " + codigo + ", NOMBRE: " 
                        + nombre + ", DIRECCION: " + direccion + ", SALARIO: " 
                        + salario + ", COMISION: " + comision);
                
                //guardo la siguiente posicion
                posicion +=32;
                
                /**
                 * Recogidos los datos, creamos cada nodo.
                 * Para ello, se crea la función CrearElemento();
                 */
                if(codigo>0){
                    Element raiz = document.createElement("empleado"); //nodo empleado
                    document.getDocumentElement().appendChild(raiz); //lo pegamos a la raiz
                    CrearElemento("codigo",Integer.toString(codigo), raiz, document); //añadir codigo
                    CrearElemento("nombre",nombre.trim() , raiz, document); //nombre
                    CrearElemento("direccion",direccion.trim() , raiz, document); //direccion
                    CrearElemento("salario",Float.toString(salario), raiz, document); //salario
                    CrearElemento("comision",Float.toString(comision), raiz, document); //comision
                }
                posicion = posicion + 60; 
                if (raf.getFilePointer() == raf.length() ) break; 
            } //fin bucle for           
            
            //Creamos la fuente XML a partir del documento
            Source source = new DOMSource(document);
            Result result = new StreamResult(new java.io.File("EMPLEADOS.xml"));
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(source, result);
            
            //mostrar doc por consola
            Result console = new StreamResult(System.out);
            transformer. transform (source, console);
           
        } catch (Exception e) {
            System.err.println("Error: " + e );
        } //Fin  try-catch para generar el XML
    } //fin main
    
    //Inserción de los datos del empleado
    static void CrearElemento(String datoEmple, String valor,
    Element raiz, Document document) {
        Element elem = document.createElement(datoEmple); //creamos hijo
        Text text = document.createTextNode(valor); //damos valor
        raiz.appendChild(elem); //pegamos el elemento hijo a la raiz
        elem.appendChild(text); //pegamos el valor
        }
    
} //fin clase Empleados_aleatorio
