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
public class cl_movimiento_dinero {

    cl_conectar c_conectar = new cl_conectar();
    cl_varios c_varios = new cl_varios();

    private int codigo;
    private String fecha;
    private String glosa;
    private double ingreso;
    private double salida;
    private String origen;
    private String destino;
    private String tipo;
    private int moneda;

    public cl_movimiento_dinero() {
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

    public String getGlosa() {
        return glosa;
    }

    public void setGlosa(String glosa) {
        this.glosa = glosa;
    }

    public double getIngreso() {
        return ingreso;
    }

    public void setIngreso(double ingreso) {
        this.ingreso = ingreso;
    }

    public double getSalida() {
        return salida;
    }

    public void setSalida(double salida) {
        this.salida = salida;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getMoneda() {
        return moneda;
    }

    public void setMoneda(int moneda) {
        this.moneda = moneda;
    }

    public void ver_movimientos(String query, JTable tabla) {
        try {
            DefaultTableModel modelo;
            modelo = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int fila, int columna) {
                    return false;
                }
            };

            modelo.addColumn("Usuario");
            modelo.addColumn("Destino");
            modelo.addColumn("Descripcion");
            modelo.addColumn("Motivo");
            modelo.addColumn("Ingreso");
            modelo.addColumn("Salida");

            Statement st = c_conectar.conexion();
            ResultSet rs = c_conectar.consulta(st, query);

            //Creando las filas para el JTable
            while (rs.next()) {
                Object[] fila = new Object[6];
                fila[0] = rs.getObject("usuario");
                String ddestino = rs.getString("tipo");
                String dtipo = rs.getString("destino");
                if (ddestino.equals("E")) {
                    fila[1] = "EFECTIVO";
                } else {
                    fila[1] = "TARJETA";
                }
                fila[2] = rs.getObject("glosa");
                if (dtipo.equals("V")) {
                    fila[3] = "VENTAS";
                }
                if (dtipo.equals("C")) {
                    fila[3] = "COMPRAS";
                }
                if (dtipo.equals("G")) {
                    fila[3] = "GASTOS";
                }
                String origen = rs.getString("origen");
                if (origen.equals("I")) {
                    fila[4] = c_varios.formato_numero(rs.getDouble("entrada"));
                    fila[5] = "0.00";
                } else {
                    fila[4] = "0.00";
                    fila[5] = c_varios.formato_numero(rs.getDouble("salida"));
                }

                modelo.addRow(fila);
            }
            c_conectar.cerrar(st);
            c_conectar.cerrar(rs);
            tabla.setModel(modelo);
            tabla.getColumnModel().getColumn(0).setPreferredWidth(80);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(80);
            tabla.getColumnModel().getColumn(2).setPreferredWidth(400);
            tabla.getColumnModel().getColumn(3).setPreferredWidth(80);
            tabla.getColumnModel().getColumn(4).setPreferredWidth(70);
            tabla.getColumnModel().getColumn(5).setPreferredWidth(70);
            c_varios.centrar_celda(tabla, 0);
            c_varios.centrar_celda(tabla, 1);
            c_varios.centrar_celda(tabla, 3);
            c_varios.derecha_celda(tabla, 4);
            c_varios.derecha_celda(tabla, 5);
        } catch (SQLException e) {
            System.out.print(e);
        }
    }

    public void ver_tipo_movimientos(String query, JTable tabla) {
        try {
            DefaultTableModel modelo;
            modelo = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int fila, int columna) {
                    return false;
                }
            };

            modelo.addColumn("Hora");
            modelo.addColumn("Tipo ");
            modelo.addColumn("Descripcion");
            modelo.addColumn("Monto");

            Statement st = c_conectar.conexion();
            ResultSet rs = c_conectar.consulta(st, query);

            //Creando las filas para el JTable
            while (rs.next()) {
                Object[] fila = new Object[4];
                fila[0] = c_varios.formato_hora(rs.getString("fecha_registro"));
                String ddestino = rs.getString("tipo");
                String dtipo = rs.getString("destino");
                if (ddestino.equals("E")) {
                    fila[1] = "EFECTIVO";
                } else {
                    fila[1] = "TARJETA";
                }
                fila[2] = rs.getObject("glosa");

                String sorigen = rs.getString("origen");
                if (sorigen.equals("I")) {
                    fila[3] = c_varios.formato_numero(rs.getDouble("entrada"));
                } else {
                    fila[3] = c_varios.formato_numero(rs.getDouble("salida"));
                }

                modelo.addRow(fila);
            }
            c_conectar.cerrar(st);
            c_conectar.cerrar(rs);
            tabla.setModel(modelo);
            tabla.getColumnModel().getColumn(0).setPreferredWidth(80);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(80);
            tabla.getColumnModel().getColumn(2).setPreferredWidth(400);
            tabla.getColumnModel().getColumn(3).setPreferredWidth(80);
            c_varios.centrar_celda(tabla, 0);
            c_varios.centrar_celda(tabla, 1);
            c_varios.derecha_celda(tabla, 3);
        } catch (SQLException e) {
            System.out.print(e);
        }
    }

    public int obtener_codigo() {
        int resultado = 0;

        try {
            Statement st = c_conectar.conexion();
            String query = "select ifnull(max(id_movimiento) + 1, 1) as codigo "
                    + "from movimiento "
                    + "where fec_mov = '" + fecha + "'";
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
        String query = "insert into movimiento values ('" + codigo + "', '" + fecha + "', '" + glosa + "', '" + ingreso + "', '" + salida + "', "
                + "'" + origen + "', '" + destino + "', '" + tipo + "', '" + frm_menu.c_empleado.getId_empleado()+ "', '" + moneda + "', current_time())";
        int resultado = c_conectar.actualiza(st, query);

        if (resultado > -1) {
            grabado = true;
        }

        c_conectar.cerrar(st);

        return grabado;
    }
}
