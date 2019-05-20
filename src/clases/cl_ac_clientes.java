/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

/**
 *
 * @author luis
 */
public class cl_ac_clientes {
    private int id_cliente;
    private String nombre;
    private String direccion;

    public cl_ac_clientes(int id_cliente, String nombre, String direccion) {
        this.id_cliente = id_cliente;
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    @Override
    public String toString() {
        return nombre + " | " + direccion;
    }
    
    
}
