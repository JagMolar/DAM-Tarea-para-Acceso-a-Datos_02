/*
 * TAREA AD02.EJERCICIO 1.
 * 1- Crear un fichero EMPLEADOS.DAT de acceso aleatorio, que contenga 
 * al menos cinco empleados. Dicho fichero contendrá los campos siguientes: 
 * CODIGO (int), NOMBRE (string), DIRECCION (string), SALARIO (float) 
 * y COMISION (float).
 * 2- A partir de los datos del fichero EMPLEADOS.DAT crear un fichero 
 * llamado EMPLEADOS.XML usando DOM.
 * 
 * La clase Empleados determina los campos a recoger y el acceso a sus datos.
 */
package empleados_aleatorio;

/**
 *
 * @author juang <juangmuelas@gmail.com>
 * @since 11/11/2021
 */
public class Empleados {
    //Declaramos las variables solicitadas en el ejercicio.
    private int codigo;
    private String nombre, direccion;
    private float salario, comision;
    
    //Constructor
    public Empleados(int codigo, String nombre, String direccion, float salario, float comision) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.direccion = direccion;
        this.salario = salario;
        this.comision = comision;
    }
    //getter y setter (aunque para este caso nos llegaría con getters

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public float getSalario() {
        return salario;
    }

    public void setSalario(float salario) {
        this.salario = salario;
    }

    public float getComision() {
        return comision;
    }

    public void setComision(float comision) {
        this.comision = comision;
    }
  
} //Fin clase Empleados
