/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import clases.cl_conectar;
import clases.cl_zona;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author luis
 */
public class m_empleados {

    cl_conectar c_conectar = new cl_conectar();

    public void llenar_combobox(JComboBox combobox) {
        try {
            combobox.removeAllItems();
            Statement st = c_conectar.conexion();
            String query = "select id_empleado, nombres, ape_pat, ape_mat "
                    + "from empleados "
                    + "order by ape_pat, ape_mat, nombres asc";
            ResultSet rs = c_conectar.consulta(st, query);

            while (rs.next()) {
                combobox.addItem(new cl_combobox(rs.getInt("id_empleado"),
                        rs.getString("ape_pat") + " " + rs.getString("ape_mat") + " " + rs.getString("nombres")));
            }

            c_conectar.cerrar(st);
            c_conectar.cerrar(rs);
        } catch (SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, ex);
        }
    }
}
