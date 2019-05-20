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
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import mercasur.frm_menu;

/**
 *
 * @author CALIDAD
 */
public class cl_cobro_ventas {

    cl_conectar c_conectar = new cl_conectar();
    cl_varios c_varios = new cl_varios();

    private int venta;
    private String periodo;
    private String cod_venta;
    private int codigo;
    private String fecha;
    private double monto;
    private int tipo;

    public cl_cobro_ventas() {
    }

    public int getVenta() {
        return venta;
    }

    public void setVenta(int venta) {
        this.venta = venta;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getCod_venta() {
        return cod_venta;
    }

    public void setCod_venta(String cod_venta) {
        this.cod_venta = cod_venta;
    }

    public int obtener_codigo() {
        int resultado = 0;

        try {
            Statement st = c_conectar.conexion();
            String query = "select ifnull(max(id_cobro) + 1, 1) as codigo "
                    + "from cobro_ventas "
                    + "where periodo = '" + periodo + "' and id_venta = '" + venta + "' ";
            ResultSet rs = c_conectar.consulta(st, query);

            while (rs.next()) {
                resultado = rs.getInt("codigo");
            }

            c_conectar.cerrar(rs);
            c_conectar.cerrar(st);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }

        return resultado;
    }

    public boolean insertar() {
        boolean grabado = false;
        Statement st = c_conectar.conexion();
        String query = "insert into cobro_ventas "
                + "Values ('" + periodo + "', '" + venta + "', '" + codigo + "', '" + monto + "', '" + fecha + "', '" + tipo + "', '" + frm_menu.c_empleado.getId_empleado() + "')";
        int resultado = c_conectar.actualiza(st, query);

        if (resultado > -1) {
            grabado = true;
        }

        c_conectar.cerrar(st);

        return grabado;
    }
    
    public boolean eliminar_todo() {
        boolean grabado = false;
        c_conectar.conectar();
        Statement st = c_conectar.conexion();
        String query = "delete from cobro_ventas "
                + "where periodo = '" + periodo + "' and id_venta = '" + venta + "'";
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
        String query = "delete from cobro_ventas "
                + "where periodo = '" + periodo + "' and id_venta = '" + venta + "' and id_cobro = '" + codigo + "'";
        int resultado = c_conectar.actualiza(st, query);

        if (resultado > -1) {
            grabado = true;
        }

        c_conectar.cerrar(st);

        return grabado;
    }

    public double mostrar_cobros(JTable tabla) {
        double suma_pagado = 0;
        try {
            DefaultTableModel modelo_pago = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int fila, int columna) {
                    return false;
                }
            };
            modelo_pago.addColumn("idCobro");
            modelo_pago.addColumn("Fecha");
            modelo_pago.addColumn("Moneda");
            modelo_pago.addColumn("Tipo");
            modelo_pago.addColumn("Monto");

            Statement st = c_conectar.conexion();
            String query = "select id_cobro, monto, fecha, tipo_pago "
                    + "from cobro_ventas "
                    + "where concat (periodo, lpad (id_venta, 5, 0)) = '" + cod_venta + "'";
            System.out.println(query);
            ResultSet rs = c_conectar.consulta(st, query);
            Object fila_p[] = new Object[7];
            while (rs.next()) {
                fila_p[0] = rs.getString("id_cobro");
                fila_p[1] = c_varios.formato_fecha_vista(rs.getString("fecha"));
                fila_p[2] = "S/";
                if (rs.getInt("tipo_pago") == 1) {
                    fila_p[3] = "EFECTIVO";
                }
                if (rs.getInt("tipo_pago") == 2) {
                    fila_p[3] = "TARJETA";
                }
                if (rs.getInt("tipo_pago") == 3) {
                    fila_p[3] = "DEPOSITO";
                }
                if (rs.getInt("tipo_pago") == 4) {
                    fila_p[3] = "CUPON";
                }
                fila_p[4] = c_varios.formato_totales(rs.getDouble("monto"));
                suma_pagado = suma_pagado + rs.getDouble("monto");
                modelo_pago.addRow(fila_p);
            }
            tabla.setModel(modelo_pago);
            c_varios.centrar_celda(tabla, 0);
            c_varios.centrar_celda(tabla, 1);
            c_varios.centrar_celda(tabla, 2);
            c_varios.centrar_celda(tabla, 3);
            c_varios.derecha_celda(tabla, 4);
            c_conectar.cerrar(rs);
            c_conectar.cerrar(st);
        } catch (SQLException | NumberFormatException e) {
            System.out.println(e.getLocalizedMessage());
        }
        return suma_pagado;
    }
}
