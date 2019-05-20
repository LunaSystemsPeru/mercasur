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
    private int codigo;
    private String inventario;
    private String fecha;
    private String usuario;

    public cl_inventario() {
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

    public String getInventario() {
        return inventario;
    }

    public void setInventario(String inventario) {
        this.inventario = inventario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public boolean insertar() {
        boolean registrado = false;
        c_conectar.conectar();
        Statement st = c_conectar.conexion();
        String query = "insert into inventarios "
                + "values ('" + periodo + "' , '" + codigo + "', '" + inventario + "', '" + fecha + "', '" + usuario + "')";
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
            String query = "select ifnull(max(codigo) + 1, 1) as codigo "
                    + "from inventarios "
                    + "where periodo = '" + periodo + "'";
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
            String query = "select inventario "
                    + "from inventarios "
                    + "where periodo = '" + periodo + "' "
                    + "order by inventario desc";
            ResultSet rs = c_conectar.consulta(st, query);
            cbx_combo.removeAllItems();
            cbx_combo.addItem("SELECCIONAR INVENTARIO");
            while (rs.next()) {
                cbx_combo.addItem(rs.getString("inventario"));
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
            String query = "select fecha, usuario "
                    + "from inventarios "
                    + "where inventario = '" + inventario + "'";
            ResultSet rs = c_conectar.consulta(st, query);

            while (rs.next()) {
                fecha = rs.getString("fecha");
                usuario = rs.getString("usuario");
            }

            c_conectar.cerrar(rs);
            c_conectar.cerrar(st);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }

    }
}
