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
public class cl_detalle_venta {

    cl_conectar c_conectar = new cl_conectar();
    cl_varios c_varios = new cl_varios();

    private String periodo;
    private int venta;
    private int producto;
    private double cantidad;
    private double precio;

    public cl_detalle_venta() {
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public int getVenta() {
        return venta;
    }

    public void setVenta(int venta) {
        this.venta = venta;
    }

    public int getProducto() {
        return producto;
    }

    public void setProducto(int producto) {
        this.producto = producto;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public boolean insertar() {
        boolean grabado = false;
        Statement st = c_conectar.conexion();
        String query = "insert into detalle_venta "
                + "Values ('" + periodo + "', '" + venta + "', '" + producto + "', '" + cantidad + "', '" + precio + "')";
        int resultado = c_conectar.actualiza(st, query);

        if (resultado > -1) {
            grabado = true;
        }

        c_conectar.cerrar(st);

        return grabado;
    }

    public boolean eliminar() {
        boolean grabado = false;
        Statement st = c_conectar.conexion();
        String query = "delete from detalle_venta "
                + "where periodo = '" + periodo + "' and idventa = '" + venta + "'";
        int resultado = c_conectar.actualiza(st, query);

        if (resultado > -1) {
            grabado = true;
        }

        c_conectar.cerrar(st);

        return grabado;
    }

    public double mostrar_productos(JTable tabla) {
        double suma_pagado = 0;
        try {
            DefaultTableModel modelo_pago = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int fila, int columna) {
                    return false;
                }
            };
            modelo_pago.addColumn("Cant");
            modelo_pago.addColumn("Und Med");
            modelo_pago.addColumn("Descripcion");
            modelo_pago.addColumn("P. Unit");
            modelo_pago.addColumn("Parcial");

            Statement st = c_conectar.conexion();
            String query = "select dv.cantidad, dv.precio, p.descripcion, p.grado, p.marca, p.modelo, u.nombre_corto "
                    + "from detalle_venta as dv "
                    + "inner join productos as p on p.idproducto = dv.idproducto "
                    + "inner join und_medida as u on p.unidad_medida = u.id "
                    + "where dv.periodo = '" + periodo + "' and idventa = '" + venta + "'";
            System.out.println(query);
            ResultSet rs = c_conectar.consulta(st, query);
            Object fila_p[] = new Object[5];
            while (rs.next()) {
                double dcantidad = rs.getDouble("cantidad");
                double dprecio = rs.getDouble("precio");

                fila_p[0] = c_varios.formato_cantidad(rs.getDouble("cantidad"));
                fila_p[1] = rs.getString("nombre_corto");
                fila_p[2] = (rs.getString("descripcion").trim() + " | " + rs.getString("marca").trim() + " | " + rs.getString("modelo").trim() + " | " + rs.getString("grado").trim()).toUpperCase();
                fila_p[3] = c_varios.formato_totales(dprecio);
                fila_p[4] = c_varios.formato_totales(dcantidad * dprecio);
                modelo_pago.addRow(fila_p);
            }
            tabla.setModel(modelo_pago);
            tabla.getColumnModel().getColumn(0).setPreferredWidth(65);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(70);
            tabla.getColumnModel().getColumn(2).setPreferredWidth(400);
            tabla.getColumnModel().getColumn(3).setPreferredWidth(70);
            tabla.getColumnModel().getColumn(4).setPreferredWidth(70);
            c_varios.centrar_celda(tabla, 0);
            c_varios.centrar_celda(tabla, 1);
            c_varios.derecha_celda(tabla, 3);
            c_varios.derecha_celda(tabla, 4);
            c_conectar.cerrar(rs);
            c_conectar.cerrar(st);
        } catch (SQLException | NumberFormatException e) {
            System.out.println(e.getLocalizedMessage());
        }
        return suma_pagado;
    }

    public double mostrar_vendidos(JTable tabla) {
        double suma_pagado = 0;
        try {
            DefaultTableModel modelo_pago = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int fila, int columna) {
                    return false;
                }
            };
            modelo_pago.addColumn("Cant");
            modelo_pago.addColumn("Und Med");
            modelo_pago.addColumn("Descripcion");
            modelo_pago.addColumn("P. Unit");
            modelo_pago.addColumn("Parcial");

            Statement st = c_conectar.conexion();
            String ver_pagos = "select dv.cantidad, dv.precio, p.descripcion, p.grado, p.marca, p.modelo, u.nombre_corto "
                    + "from detalle_venta as dv "
                    + "inner join productos as p on p.idproducto = dv.idproducto "
                    + "inner join und_medida as u on p.unidad_medida = u.id "
                    + "where dv.periodo = '" + periodo + "' and idventa = '" + venta + "'";
            ResultSet rs = c_conectar.consulta(st, ver_pagos);
            Object fila_p[] = new Object[5];
            while (rs.next()) {
                double dcantidad = rs.getDouble("cantidad");
                double dprecio = rs.getDouble("precio");

                fila_p[0] = c_varios.formato_cantidad(rs.getDouble("cantidad"));
                fila_p[1] = rs.getString("nombre_corto");
                fila_p[2] = (rs.getString("descripcion").trim() + " | " + rs.getString("marca").trim() + " | " + rs.getString("modelo").trim() + " | " + rs.getString("grado").trim()).toUpperCase();
                fila_p[3] = c_varios.formato_totales(dprecio);
                fila_p[4] = c_varios.formato_totales(dcantidad * dprecio);
                modelo_pago.addRow(fila_p);
            }
            tabla.setModel(modelo_pago);
            tabla.getColumnModel().getColumn(0).setPreferredWidth(65);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(70);
            tabla.getColumnModel().getColumn(2).setPreferredWidth(400);
            tabla.getColumnModel().getColumn(3).setPreferredWidth(70);
            tabla.getColumnModel().getColumn(4).setPreferredWidth(70);
            c_varios.centrar_celda(tabla, 0);
            c_varios.centrar_celda(tabla, 1);
            c_varios.derecha_celda(tabla, 3);
            c_varios.derecha_celda(tabla, 4);
            c_conectar.cerrar(rs);
            c_conectar.cerrar(st);
        } catch (SQLException | NumberFormatException e) {
            System.out.println(e.getLocalizedMessage());
        }
        return suma_pagado;
    }
}
