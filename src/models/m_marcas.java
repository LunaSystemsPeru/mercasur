/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import clases.cl_conectar;
import clases.cl_moneda;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author luis
 */
public class m_marcas {
    cl_conectar c_conectar = new cl_conectar();
    //cl_tipo_documento c_tido = new cl_tipo_documento();

    public void llenar_combobox (JComboBox cbx_moneda) {
        cbx_moneda.removeAllItems();
        try {
            Statement st = c_conectar.conexion();
            String query = "select id_marca, nombre "
                    + "from marcas "
                    + "order by nombre asc";
            ResultSet rs = c_conectar.consulta(st, query);

            while (rs.next()) {
                cbx_moneda.addItem(new cl_combobox(rs.getInt("id_marca"), rs.getString("nombre")));
            }

            c_conectar.cerrar(st);
            c_conectar.cerrar(rs);
        } catch (SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, ex);
        }
    }
}
