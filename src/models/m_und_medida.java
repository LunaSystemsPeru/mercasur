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
 * @author CALIDAD
 */
public class m_und_medida {

    cl_conectar c_conectar = new cl_conectar();

    public void cbx_und_medida(JComboBox cbx_medida) {
        cbx_medida.removeAllItems();
        try {
            Statement st = c_conectar.conexion();
            String query = "select id_unidad, descripcion "
                    + "from und_medida "
                    + "order by descripcion asc";
            ResultSet rs = c_conectar.consulta(st, query);

            while (rs.next()) {
                cbx_medida.addItem(new cl_combobox(rs.getInt("id_unidad"), rs.getString("descripcion")));
            }

            c_conectar.cerrar(st);
            c_conectar.cerrar(rs);
        } catch (SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, ex);
        }
    }
}
