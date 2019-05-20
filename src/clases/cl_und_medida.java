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

    public cl_und_medida(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
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
            String v_usuario = "select * from und_medida where id = '" + id + "'";
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
}
