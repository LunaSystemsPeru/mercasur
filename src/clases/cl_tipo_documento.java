package clases;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class cl_tipo_documento {

    cl_conectar c_conectar = new cl_conectar();
    
    private int id;
    private String nombre;
    private String abreviatura;
    private String csunat;

    public cl_tipo_documento() {
    }

    public cl_tipo_documento(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public String getCsunat() {
        return csunat;
    }

    public void setCsunat(String csunat) {
        this.csunat = csunat;
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

    @Override
    public String toString() {
        return nombre;
    }

    public boolean ver_datos_documento() {
        boolean existe = false;

        try {
            Statement st = c_conectar.conexion();
            String query = "select * from "
                    + "tipo_documento "
                    + "where id_documento = '" + id + "'";
            ResultSet rs = c_conectar.consulta(st, query);

            while (rs.next()) {
                existe = true;
                nombre = rs.getString("nombre");
                abreviatura = rs.getString("abreviado");
                csunat = rs.getString("sunat");
            }

            c_conectar.cerrar(rs);
            c_conectar.cerrar(st);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }

        return existe;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
