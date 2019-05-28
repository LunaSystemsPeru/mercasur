/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author CALIDAD
 */
public class cl_inventario {

    cl_conectar c_conectar = new cl_conectar();

    private String periodo;
    private int id_inventario;
    private String fecha;
    private int id_empleado;

    public cl_inventario() {
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public int getId_inventario() {
        return id_inventario;
    }

    public void setId_inventario(int id_inventario) {
        this.id_inventario = id_inventario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(int id_empleado) {
        this.id_empleado = id_empleado;
    }

    public boolean insertar() {
        boolean registrado = false;
        c_conectar.conectar();
        Statement st = c_conectar.conexion();
        String query = "insert into inventarios "
                + "values ('" + periodo + "' , '" + id_inventario + "', '" + fecha + "', '" + id_empleado + "')";
        int resultado = c_conectar.actualiza(st, query);

        if (resultado > -1) {
            registrado = true;
        }

        c_conectar.cerrar(st);
        return registrado;
    }

    public int obtener_codigo() {
        int resultado = 0;

        try {
            Statement st = c_conectar.conexion();
            String query = "select ifnull(max(id_inventario) + 1, 1) as codigo "
                    + "from inventarios "
                    + "where periodo = '" + periodo + "'";
            ResultSet rs = c_conectar.consulta(st, query);

            while (rs.next()) {
                this.id_inventario = rs.getInt("codigo");
            }

            c_conectar.cerrar(rs);
            c_conectar.cerrar(st);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }

        return resultado;

    }

    public void cargar_periodos(JComboBox cbx_combo) {
        try {
            Statement st = c_conectar.conexion();
            String query = "select distinct periodo "
                    + "from inventarios "
                    + "order by periodo desc";
            ResultSet rs = c_conectar.consulta(st, query);
            cbx_combo.removeAllItems();
            cbx_combo.addItem("SELECCIONAR PERIODO");
            while (rs.next()) {
                cbx_combo.addItem(rs.getString("periodo"));
            }

            c_conectar.cerrar(rs);
            c_conectar.cerrar(st);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }

    }

    public void cargar_codigo(String periodo, JComboBox cbx_combo) {
        try {
            Statement st = c_conectar.conexion();
            String query = "select id_inventario "
                    + "from inventarios "
                    + "where periodo = '" + periodo + "' "
                    + "order by id_inventario desc";
            ResultSet rs = c_conectar.consulta(st, query);
            cbx_combo.removeAllItems();
            cbx_combo.addItem("SELECCIONAR INVENTARIO");
            while (rs.next()) {
                cbx_combo.addItem(rs.getString("id_inventario"));
            }

            c_conectar.cerrar(rs);
            c_conectar.cerrar(st);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }

    }

    public void cargar_datos() {
        try {
            Statement st = c_conectar.conexion();
            String query = "select fecha, id_empleado "
                    + "from inventarios "
                    + "where periodo = '" + this.periodo + "' and id_inventario = '" + this.id_empleado + "'";
            ResultSet rs = c_conectar.consulta(st, query);

            while (rs.next()) {
                fecha = rs.getString("fecha");
                id_empleado = rs.getInt("id_empleado");
            }

            c_conectar.cerrar(rs);
            c_conectar.cerrar(st);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }

    }
}
