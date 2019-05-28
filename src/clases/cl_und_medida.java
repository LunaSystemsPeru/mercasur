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

/**
 *
 * @author CALIDAD
 */
public class cl_und_medida {

    cl_conectar c_conectar = new cl_conectar();

    private int id;
    private String nombre;
    private String abreviado;

    public cl_und_medida() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAbreviado() {
        return abreviado;
    }

    public void setAbreviado(String abreviado) {
        this.abreviado = abreviado;
    }

    @Override
    public String toString() {
        return nombre;
    }

    public boolean obtener_datos() {
        boolean bvalidar = false;

        try {
            Statement st = c_conectar.conexion();
            String v_usuario = "select * "
                    + "from und_medida "
                    + "where id_unidad = '" + id + "'";
            ResultSet rs = c_conectar.consulta(st, v_usuario);

            if (rs.next()) {
                bvalidar = true;
                nombre = rs.getString("descripcion");
                abreviado = rs.getString("nombre_corto");
            }

            c_conectar.cerrar(rs);
            c_conectar.cerrar(st);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }

        return bvalidar;
    }

    public boolean insertar() {
        boolean grabado = false;
        Statement st = c_conectar.conexion();
        String query = "insert into und_medida "
                + "Values ('" + id + "', '" + nombre + "', '" + abreviado + "')";
        int resultado = c_conectar.actualiza(st, query);

        if (resultado > -1) {
            grabado = true;
        }

        c_conectar.cerrar(st);

        return grabado;
    }

    public boolean modificar() {
        boolean grabado = false;
        Statement st = c_conectar.conexion();
        String query = "update und_medida "
                + "set descripcion = '" + nombre + "', nombre_corto = '" + abreviado + "' "
                + "where id_unidad = '" + id + "'";
        int resultado = c_conectar.actualiza(st, query);

        if (resultado > -1) {
            grabado = true;
        }

        c_conectar.cerrar(st);

        return grabado;
    }

    public void obtener_codigo() {
        try {
            Statement st = c_conectar.conexion();
            String query = "select ifnull(max(id_unidad) + 1, 1) as codigo "
                    + "from und_medida ";
            ResultSet rs = c_conectar.consulta(st, query);

            while (rs.next()) {
                id = rs.getInt("codigo");
            }
            c_conectar.cerrar(rs);
            c_conectar.cerrar(st);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }
    }
    
     public void ver_unidades (JTable tabla, String query) {
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

            mostrar.addColumn("Codigo");
            mostrar.addColumn("Nombre");
            mostrar.addColumn("Abreviado");

            while (rs.next()) {
                Object fila[] = new Object[3];

                fila[0] = rs.getInt("id_unidad");
                fila[1] = rs.getString("descripcion");
                fila[2] = rs.getString("nombre_corto");
                mostrar.addRow(fila);
            }

            c_conectar.cerrar(st);
            c_conectar.cerrar(rs);
            tabla.setModel(mostrar);
            tabla.getColumnModel().getColumn(0).setPreferredWidth(50);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(150);
            tabla.getColumnModel().getColumn(2).setPreferredWidth(100);
        } catch (SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, ex);
        }
    }

}
