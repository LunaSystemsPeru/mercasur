/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import clases.cl_conectar;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author luis
 */
public class m_unidad_producto {

    cl_conectar c_conectar = new cl_conectar();

    public void llenar_combo(JComboBox combobox, int id_producto) {
        try {
            combobox.removeAllItems();
            Statement st = c_conectar.conexion();
            String query = "select id_unidad, nombre "
                    + "from producto_unidades "
                    + "where id_producto = '" + id_producto + "'"
                    + "order by nombre asc";
            ResultSet rs = c_conectar.consulta(st, query);

            while (rs.next()) {
                combobox.addItem(new cl_combobox(rs.getInt("id_unidad"), rs.getString("nombre").trim()));
            }

            c_conectar.cerrar(st);
            c_conectar.cerrar(rs);
        } catch (SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, ex);
        }
    }
}
