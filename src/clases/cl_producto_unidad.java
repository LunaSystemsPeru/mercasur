/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author luis
 */
public class cl_producto_unidad {

    cl_conectar c_conectar = new cl_conectar();

    private int id_producto;
    private int id_unidad;
    private double factor;
    private String nombre;

    public cl_producto_unidad() {
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public int getId_unidad() {
        return id_unidad;
    }

    public void setId_unidad(int id_unidad) {
        this.id_unidad = id_unidad;
    }

    public double getFactor() {
        return factor;
    }

    public void setFactor(double factor) {
        this.factor = factor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean obtener_datos() {
        boolean existe = false;

        try {
            Statement st = c_conectar.conexion();
            String query = "select nombre, factor "
                    + "from producto_unidad "
                    + "where id_producto = '" + id_producto + "' and id_unidad = '" + id_unidad + "' ";
            ResultSet rs = c_conectar.consulta(st, query);

            while (rs.next()) {
                existe = true;
                nombre = rs.getString("nombre").toUpperCase().trim();
                factor = rs.getDouble("factor");
            }
            c_conectar.cerrar(rs);
            c_conectar.cerrar(st);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }

        return existe;
    }

    public void obtener_id() {
        try {
            Statement st = c_conectar.conexion();
            String query = "select ifnull(max(id_unidad)+1, 1) as codigo "
                    + "from producto_unidades "
                    + "where id_producto = '" + id_producto + "' ";
            ResultSet rs = c_conectar.consulta(st, query);

            if (rs.next()) {
                id_unidad = rs.getInt("codigo");
            }

            c_conectar.cerrar(rs);
            c_conectar.cerrar(st);
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    public boolean insertar() {
        boolean grabado = false;
        Statement st = c_conectar.conexion();
        String query = "insert into producto_unidades "
                + "Values ('" + id_producto + "', '" + id_unidad + "', '" + nombre + "','" + factor + "')";
        int resultado = c_conectar.actualiza(st, query);

        if (resultado > -1) {
            grabado = true;
        }

        c_conectar.cerrar(st);

        return grabado;
    }

}