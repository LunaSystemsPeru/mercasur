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
    private int id_venta;
    private int id_producto;
    private double cantidad;
    private int id_unidad;
    private double precio;
    private double costo;

    public cl_detalle_venta() {
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public int getId_venta() {
        return id_venta;
    }

    public void setId_venta(int id_venta) {
        this.id_venta = id_venta;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public int getId_unidad() {
        return id_unidad;
    }

    public void setId_unidad(int id_unidad) {
        this.id_unidad = id_unidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public boolean insertar() {
        boolean grabado = false;
        Statement st = c_conectar.conexion();
        String query = "insert into detalle_venta "
                + "Values ('" + periodo + "', '" + id_venta + "', '" + id_producto + "', '" + cantidad + "', '" + id_unidad + "','" + precio + "', '" + costo + "')";
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
                + "where periodo = '" + periodo + "' and id_venta = '" + id_venta + "'";
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
                    + "where dv.periodo = '" + periodo + "' and id_venta = '" + id_venta + "'";
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

    public double mostrar_despacho(JTable tabla) {
        double suma_pagado = 0;
        try {
            DefaultTableModel modelo_pago = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int fila, int columna) {
                    return false;
                }
            };
            modelo_pago.addColumn("Descripcion");
            modelo_pago.addColumn("Cant");
            modelo_pago.addColumn("Grupo");
            modelo_pago.addColumn("Cant");
            modelo_pago.addColumn("Unidad");

            Statement st = c_conectar.conexion();
            String query = "select p.descripcion, sum(dv.cantidad) as cantidad, p.id_unidad, pu.factor, pu.nombre as cajas "
                    + "from detalle_venta as dv "
                    + "inner join productos as p on p.id_producto = dv.id_producto "
                    + "inner join producto_unidades as pu on pu.id_producto = dv.id_producto and pu.id_unidad = dv.id_unidad "
                    + "group by dv.id_producto";
            System.out.println(query);
            ResultSet rs = c_conectar.consulta(st, query);
            Object fila_p[] = new Object[5];
            while (rs.next()) {
                int factor = rs.getInt("factor");
                double dcantidad = rs.getDouble("cantidad");
                double total_unidades = dcantidad * factor;
                int total_cajas = (int) total_unidades;
                fila_p[0] = rs.getString("descripcion");
                fila_p[1] = total_cajas;
                fila_p[2] = rs.getString("cajas");
                fila_p[3] = dcantidad % factor;
                fila_p[4] = "UNDS";
                modelo_pago.addRow(fila_p);
            }
            tabla.setModel(modelo_pago);
            tabla.getColumnModel().getColumn(0).setPreferredWidth(450);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(60);
            tabla.getColumnModel().getColumn(2).setPreferredWidth(80);
            tabla.getColumnModel().getColumn(3).setPreferredWidth(60);
            tabla.getColumnModel().getColumn(4).setPreferredWidth(80);
            c_varios.centrar_celda(tabla, 2);
            c_varios.centrar_celda(tabla, 4);
            c_varios.derecha_celda(tabla, 1);
            c_varios.derecha_celda(tabla, 3);
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
                    + "where dv.periodo = '" + periodo + "' and id_venta = '" + id_venta + "'";
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

    public void ver_liquidacion_diaria (JTable tabla, String fecha) {
        String query = "select z.nombre as zona, td.abreviado as doc_sunat, v.serie_doc, v.nro_doc, p.descripcion, um.descripcion as unidad, dv.cantidad, dv.precio "
                + "from detalle_venta as dv "
                + "inner join ventas as v on v.periodo = dv.periodo and v.id_venta = dv.id_venta "
                + "inner join tipo_documento as td on td.id_documento = v.id_documento "
                + "inner join productos as p on p.id_producto = dv.id_producto "
                + "inner join und_medida as um on um.id_unidad = p.id_unidad "
                + "inner join zona as z on z.id_zona = v.id_zona";
        
        try {
            DefaultTableModel modelo_pago = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int fila, int columna) {
                    return false;
                }
            };
            modelo_pago.addColumn("Zona");
            modelo_pago.addColumn("Documento");
            modelo_pago.addColumn("Producto");
            modelo_pago.addColumn("C. Enviada");
            modelo_pago.addColumn("C. Rechazo ");
            modelo_pago.addColumn("U.M");
            modelo_pago.addColumn("Precio");
            modelo_pago.addColumn("Parcial Enviado");
            modelo_pago.addColumn("Parcial Entregado");

            Statement st = c_conectar.conexion();
            ResultSet rs = c_conectar.consulta(st, query);
            Object fila_p[] = new Object[9];
            while (rs.next()) {
                int pcantidad_enviado = rs.getInt("cantidad");
                double pprecio = rs.getDouble("precio");
                double pparcial = pcantidad_enviado * pprecio;

                fila_p[0] = rs.getString("zona");
                fila_p[1] = rs.getString("doc_sunat") + " | " + rs.getString("serie_doc") + " - " + rs.getString("nro_doc");
                fila_p[2] = rs.getString("descripcion");
                fila_p[3] = pcantidad_enviado;
                fila_p[4] = "0";
                fila_p[5] = rs.getString("unidad");
                fila_p[6] = c_varios.formato_numero(pprecio);
                fila_p[7] = c_varios.formato_numero(pparcial);
                fila_p[8] = c_varios.formato_numero(pparcial);
                modelo_pago.addRow(fila_p);
            }
            tabla.setModel(modelo_pago);
            tabla.getColumnModel().getColumn(0).setPreferredWidth(100);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(120);
            tabla.getColumnModel().getColumn(2).setPreferredWidth(400);
            tabla.getColumnModel().getColumn(3).setPreferredWidth(70);
            tabla.getColumnModel().getColumn(4).setPreferredWidth(70);
            tabla.getColumnModel().getColumn(5).setPreferredWidth(70);
            tabla.getColumnModel().getColumn(6).setPreferredWidth(70);
            tabla.getColumnModel().getColumn(7).setPreferredWidth(70);
            tabla.getColumnModel().getColumn(8).setPreferredWidth(70);
            c_varios.centrar_celda(tabla, 0);
            c_varios.centrar_celda(tabla, 1);
            c_varios.derecha_celda(tabla, 3);
            c_varios.derecha_celda(tabla, 4);
            c_conectar.cerrar(rs);
            c_conectar.cerrar(st);
        } catch (SQLException | NumberFormatException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}
