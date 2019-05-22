/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CALIDAD
 */
public class cl_detalle_inventario {

    cl_conectar c_conectar = new cl_conectar();
    cl_varios c_varios = new cl_varios();

    private String inventario;
    private int producto;
    private double precio;
    private double cactual;
    private double cfisico;
    private String ubicacion;

    public cl_detalle_inventario() {
    }

    public String getInventario() {
        return inventario;
    }

    public void setInventario(String inventario) {
        this.inventario = inventario;
    }

    public int getProducto() {
        return producto;
    }

    public void setProducto(int producto) {
        this.producto = producto;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getCactual() {
        return cactual;
    }

    public void setCactual(double cactual) {
        this.cactual = cactual;
    }

    public double getCfisico() {
        return cfisico;
    }

    public void setCfisico(double cfisico) {
        this.cfisico = cfisico;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public boolean insertar() {
        boolean registrado = false;
        Statement st = c_conectar.conexion();
        String query = "insert into detalle_inventario  "
                + "values ('" + inventario + "' , '" + producto + "', '" + precio + "', '" + cactual + "', '" + cfisico + "', '" + ubicacion + "')";
        int resultado = c_conectar.actualiza(st, query);

        if (resultado > -1) {
            registrado = true;
        }

        c_conectar.cerrar(st);
        return registrado;
    }

    public void mostrar_productos(JTable tabla) {
        try {
            DefaultTableModel detalle = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int fila, int columna) {
                    return false;
                }
            };
            detalle.addColumn("Id");
            detalle.addColumn("Descripcion");
            detalle.addColumn("P. Venta");
            detalle.addColumn("Und. Medida");
            detalle.addColumn("Ubicacion");
            detalle.addColumn("Actual");
            detalle.addColumn("Fisico");
            detalle.addColumn("Diferencia");

            Statement st = c_conectar.conexion();
            String query = "select di.id_producto, p.descripcion, m.nombre as marca, p.precio_venta, di.cactual, di.cfisico, di.ubicacion, u.nombre_corto as und_medida "
                    + "from detalle_inventario as di "
                    + "inner join productos as p on p.id_producto = di.id_producto "
                    + "inner join marcas as m on m.id_marca = p.id_marca "
                    + "inner join und_medida as u on p.id_unidad = u.id_unidad "
                    + "where di.id_inventario = '" + this.inventario + "' "
                    + "order by p.descripcion asc";
            System.out.println(query);
            ResultSet rs = c_conectar.consulta(st, query);
            Object fila_p[] = new Object[8];
            while (rs.next()) {
                double cpactual = rs.getDouble("cactual");
                double cpfisico = rs.getDouble("cfisico");
                double diferencia = cpfisico - cpactual;
                double dprecio = rs.getDouble("precio_venta");

                fila_p[0] = rs.getString("id_producto");
                fila_p[1] = (rs.getString("descripcion").trim() + " | " + rs.getString("marca").trim()).toUpperCase();
                fila_p[2] = c_varios.formato_totales(dprecio);
                fila_p[3] = rs.getString("und_medida");
                fila_p[4] = rs.getString("ubicacion");
                fila_p[5] = c_varios.formato_totales(cpactual);
                fila_p[6] = c_varios.formato_totales(cpfisico);
                fila_p[7] = c_varios.formato_totales(diferencia);
                detalle.addRow(fila_p);
            }
            tabla.setModel(detalle);
            tabla.getColumnModel().getColumn(0).setPreferredWidth(65);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(430);
            tabla.getColumnModel().getColumn(2).setPreferredWidth(70);
            tabla.getColumnModel().getColumn(3).setPreferredWidth(70);
            tabla.getColumnModel().getColumn(4).setPreferredWidth(70);
            tabla.getColumnModel().getColumn(5).setPreferredWidth(70);
            tabla.getColumnModel().getColumn(6).setPreferredWidth(70);
            tabla.getColumnModel().getColumn(7).setPreferredWidth(70);
            c_varios.centrar_celda(tabla, 0);
            c_varios.derecha_celda(tabla, 2);
            c_varios.centrar_celda(tabla, 3);
            c_varios.centrar_celda(tabla, 4);
            c_varios.derecha_celda(tabla, 5);
            c_varios.derecha_celda(tabla, 6);
            c_varios.derecha_celda(tabla, 7);
            c_conectar.cerrar(rs);
            c_conectar.cerrar(st);
        } catch (SQLException | NumberFormatException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}
