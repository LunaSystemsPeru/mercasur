package clases;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class cl_documento_tienda {

    cl_conectar c_conectar = new cl_conectar();
    private int id;
    private String nombre;
    private String abreviado;
    private String serie;
    private int numero;

    public cl_documento_tienda() {
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

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getAbreviado() {
        return abreviado;
    }

    public void setAbreviado(String abreviado) {
        this.abreviado = abreviado;
    }

    public void cbx_documentos(JComboBox cbx_tido) {
        try {
            Statement st = c_conectar.conexion();
            String query = "select dt.id, td.nombre, dt.serie, dt.numero "
                    + "from documentos_tienda as dt "
                    + "inner join tipo_documento as td on td.id = dt.id "
                    + "order by dt.id asc";
            ResultSet rs = c_conectar.consulta(st, query);

            while (rs.next()) {
                cbx_tido.addItem(rs.getString("nombre"));
            }

            c_conectar.cerrar(st);
            c_conectar.cerrar(rs);
        } catch (SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void datos_documento() {
        try {
            Statement st = c_conectar.conexion();
            String query = "select * "
                    + "from documento_tienda "
                    + "where id_documento = '" + id + "'";
            ResultSet rs = c_conectar.consulta(st, query);

            while (rs.next()) {
                serie = rs.getString("serie");
                numero = rs.getInt("numero");
            }

            c_conectar.cerrar(st);
            c_conectar.cerrar(rs);
        } catch (SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public boolean actualizar_numero() {
        boolean grabado = false;
        Statement st = c_conectar.conexion();
        String m_vehiculo = "update documento_tienda "
                + "set numero = numero + 1 "
                + "where id = '" + id + "'";
        int resultado = c_conectar.actualiza(st, m_vehiculo);

        if (resultado > -1) {
            grabado = true;
        }

        c_conectar.cerrar(st);

        return grabado;
    }

}
