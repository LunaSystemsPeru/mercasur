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
 * @author CALIDAD
 */
public class m_zonas {
    cl_conectar c_conectar = new cl_conectar();

    public void cbx_zona(JComboBox combobox) {
        try {
            Statement st = c_conectar.conexion();
            String query = "select id_zona, nombre "
                    + "from zona "
                    + "order by nombre asc";
            ResultSet rs = c_conectar.consulta(st, query);

            while (rs.next()) {
                combobox.addItem(new cl_zona(rs.getInt("id_zona"), rs.getString("nombre")));
            }

            c_conectar.cerrar(st);
            c_conectar.cerrar(rs);
        } catch (SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, ex);
        }
    }
}
