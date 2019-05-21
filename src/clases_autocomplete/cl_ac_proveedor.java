/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_autocomplete;

/**
 *
 * @author CALIDAD
 */
public class cl_ac_proveedor {

    private int id_proveedor;
    private String ruc;
    private String razon_social;

    public cl_ac_proveedor() {
    }

    public cl_ac_proveedor(int id_proveedor, String ruc, String razon_social) {
        this.id_proveedor = id_proveedor;
        this.ruc = ruc;
        this.razon_social = razon_social;
    }

    public int getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(int id_proveedor) {
        this.id_proveedor = id_proveedor;
    }
    
    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getRazon_social() {
        return razon_social;
    }

    public void setRazon_social(String razon_social) {
        this.razon_social = razon_social;
    }

    @Override
    public String toString() {
        return ruc + " | " + razon_social;
    }

}
