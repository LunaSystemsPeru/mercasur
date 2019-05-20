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
 * @author CALIDAD
 */
public class m_caja_chica {

    cl_conectar c_conectar = new cl_conectar();

    public void cbx_anio(JComboBox combobox) {
        try {
            Statement st = c_conectar.conexion();
            String query = "select distinct(year(fecha)) as anio "
                    + "from caja_chica "
                    + "order by anio desc";
            ResultSet rs = c_conectar.consulta(st, query);

            while (rs.next()) {
                combobox.addItem(rs.getString("anio"));
            }

            c_conectar.cerrar(st);
            c_conectar.cerrar(rs);
        } catch (SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void cbx_mes(JComboBox combobox, String anio) {
        try {
            Statement st = c_conectar.conexion();
            String query = "select distinct(month(fecha)) as mes "
                    + "from caja_chica "
                    + "where year(fecha) = '" + anio + "' "
                    + "order by mes desc";
            ResultSet rs = c_conectar.consulta(st, query);

            while (rs.next()) {
                combobox.addItem(rs.getString("mes"));
            }

            c_conectar.cerrar(st);
            c_conectar.cerrar(rs);
        } catch (SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, ex);
        }
    }
}
