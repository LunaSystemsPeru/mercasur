/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_autocomplete;

/**
 *
 * @author luis
 */
public class cl_ac_clientes {

    private int id_cliente;
    private String nombre;
    private String direccion;
    private String documento;

    public cl_ac_clientes(int id_cliente, String nombre, String direccion, String documento) {
        this.id_cliente = id_cliente;
        this.nombre = nombre;
        this.direccion = direccion;
        this.documento = documento;
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

    public String getDocumento() {
        return documento;
    }

    @Override
    public String toString() {
        return nombre + " | " + documento + " | " + direccion;
    }

}
