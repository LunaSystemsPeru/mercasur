/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import clases.cl_conectar;
import clases.cl_und_medida;
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
    //cl_tipo_documento c_tido = new cl_tipo_documento();

    public void cbx_und_medida(JComboBox cbx_medida) {
        try {
            Statement st = c_conectar.conexion();
            String query = "select id, descripcion "
                    + "from und_medida "
                    + "order by descripcion asc";
            ResultSet rs = c_conectar.consulta(st, query);

            while (rs.next()) {
                cbx_medida.addItem(new cl_und_medida(rs.getInt("id"), rs.getString("descripcion")));
            }

            c_conectar.cerrar(st);
            c_conectar.cerrar(rs);
        } catch (SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, ex);
        }
    }
}
