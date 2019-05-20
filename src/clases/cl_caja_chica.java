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
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import mercasur.frm_menu;

/**
 *
 * @author STEVEN
 */
public class cl_caja_chica {

    cl_conectar c_conectar = new cl_conectar();
    cl_varios c_varios = new cl_varios();

    private String fecha;
    private double apertura;
    private double cierre;
    private double sistema;
    private double t_ventas;
    private double t_cobros;
    private double t_compras;
    private double t_devolucion;
    private double o_ingresos;
    private double o_salidas;

    public cl_caja_chica() {
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getApertura() {
        return apertura;
    }

    public void setApertura(double apertura) {
        this.apertura = apertura;
    }

    public double getCierre() {
        return cierre;
    }

    public void setCierre(double cierre) {
        this.cierre = cierre;
    }

    public double getSistema() {
        return sistema;
    }

    public void setSistema(double sistema) {
        this.sistema = sistema;
    }

    public double getT_ventas() {
        return t_ventas;
    }

    public void setT_ventas(double t_ventas) {
        this.t_ventas = t_ventas;
    }

    public double getT_cobros() {
        return t_cobros;
    }

    public void setT_cobros(double t_cobros) {
        this.t_cobros = t_cobros;
    }

    public double getT_compras() {
        return t_compras;
    }

    public void setT_compras(double t_compras) {
        this.t_compras = t_compras;
    }

    public double getT_devolucion() {
        return t_devolucion;
    }

    public void setT_devolucion(double t_devolucion) {
        this.t_devolucion = t_devolucion;
    }

    public double getO_ingresos() {
        return o_ingresos;
    }

    public void setO_ingresos(double o_ingresos) {
        this.o_ingresos = o_ingresos;
    }

    public double getO_salidas() {
        return o_salidas;
    }

    public void setO_salidas(double o_salidas) {
        this.o_salidas = o_salidas;
    }

    public boolean insertar() {
        boolean grabado = false;
        Statement st = c_conectar.conexion();
        String query = "insert into caja_diaria "
                + "Values ('" + fecha + "', '" + apertura + "', '0', '0', '0', '0', '0', '0', '0', '0')";
        int resultado = c_conectar.actualiza(st, query);

        if (resultado > -1) {
            grabado = true;
        }

        c_conectar.cerrar(st);

        return grabado;
    }

    public boolean cerrar_caja() {
        boolean grabado = false;
        Statement st = c_conectar.conexion();
        String query = "update caja_diaria "
                + "set monto_cierre = '" + cierre + "' "
                + "where fecha = '" + fecha + "'";
        int resultado = c_conectar.actualiza(st, query);

        if (resultado > -1) {
            grabado = true;
        }

        c_conectar.cerrar(st);

        return grabado;
    }

    public boolean validar_apertura() {
        boolean existe = false;

        try {
            Statement st = c_conectar.conexion();
            String c_saldo = "select * "
                    + "from caja_diaria "
                    + "where fecha = '" + fecha + "'";
            ResultSet rs = c_conectar.consulta(st, c_saldo);

            if (rs.next()) {
                apertura = rs.getDouble("monto_apertura");
                cierre = rs.getDouble("monto_cierre");
                t_ventas = rs.getDouble("ventas");
                t_cobros = rs.getDouble("cobros");
                t_compras = rs.getDouble("compras");
                t_devolucion = rs.getDouble("devolucion");
                o_ingresos = rs.getDouble("o_ingresos");
                o_salidas = rs.getDouble("o_salidas");
                existe = true;
            }

            c_conectar.cerrar(rs);
            c_conectar.cerrar(st);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }

        return existe;
    }

    public void ver_caja_mensual(JTable tabla, String query) {
        try {
            DefaultTableModel mostrar = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int fila, int columna) {
                    return false;
                }
            };
            Statement st = c_conectar.conexion();
            ResultSet rs = c_conectar.consulta(st, query);

            RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(mostrar);
            tabla.setRowSorter(sorter);

            mostrar.addColumn("Fecha");
            mostrar.addColumn("S/ Apertura");
            mostrar.addColumn("S/ T. Ingresos");
            mostrar.addColumn("S/ T. Salidas");
            mostrar.addColumn("S/ Sistema");
            mostrar.addColumn("S/ Ingresado");
            mostrar.addColumn("Diferencia");

            while (rs.next()) {
                double dapertura = rs.getDouble("monto_apertura");
                double dcierre = rs.getDouble("monto_cierre");
                double dsistema = rs.getDouble("monto_sistema");
                double dventas = rs.getDouble("ventas");
                double dcobros = rs.getDouble("cobros");
                double dcupon = rs.getDouble("cupon");
                double dcompras = rs.getDouble("compras");
                double ddevolucion = rs.getDouble("devolucion");
                double do_ingresos = rs.getDouble("o_ingresos");
                double do_salidas = rs.getDouble("o_salidas");
                double dt_ingresos =  dapertura + dventas + dcobros + dcupon + do_ingresos;
                double dt_salidas = dcompras + ddevolucion + do_salidas;
                double dt_sistema = dt_ingresos - dt_salidas;
                double ddiferencia = dcierre - dt_sistema;

                Object fila[] = new Object[7];

                fila[0] = c_varios.formato_fecha_vista(rs.getString("fecha"));
                fila[1] = c_varios.formato_totales(dapertura);
                fila[2] = c_varios.formato_totales(dt_ingresos);
                fila[3] = c_varios.formato_totales(dt_salidas);
                fila[4] = c_varios.formato_totales(dsistema);
                fila[5] = c_varios.formato_totales(dcierre);
                fila[6] = c_varios.formato_totales(ddiferencia);
                mostrar.addRow(fila);
            }

            c_conectar.cerrar(st);
            c_conectar.cerrar(rs);
            tabla.setModel(mostrar);
            tabla.getColumnModel().getColumn(0).setPreferredWidth(80);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(80);
            tabla.getColumnModel().getColumn(2).setPreferredWidth(80);
            tabla.getColumnModel().getColumn(3).setPreferredWidth(80);
            tabla.getColumnModel().getColumn(4).setPreferredWidth(80);
            tabla.getColumnModel().getColumn(5).setPreferredWidth(80);
            tabla.getColumnModel().getColumn(6).setPreferredWidth(80);
            c_varios.centrar_celda(tabla, 0);
            c_varios.derecha_celda(tabla, 1);
            c_varios.derecha_celda(tabla, 2);
            c_varios.derecha_celda(tabla, 3);
            c_varios.derecha_celda(tabla, 4);
            c_varios.derecha_celda(tabla, 5);
            c_varios.derecha_celda(tabla, 6);
        } catch (SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, ex);
        }
    }
}
