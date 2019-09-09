package clases;

import java.sql.*;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JOptionPane;

public class cl_empleados {

    cl_conectar c_conectar = new cl_conectar();
    private int id_empleado;
    private String dni;
    private String nombres;
    private String ape_pat;
    private String ape_mat;
    private String direccion;
    private String correo;
    private String telefono;
    private String fecha_nacimiento;
    private String nick;
    private String contrasena;
    private String estado;
    private Statement st;
    private ResultSet rs;

    public cl_empleados() {
    }

    public int getId_empleado() {
        return id_empleado;
    }

    /**
     * @param id_empleado the id_empleado to set
     */
    public void setId_empleado(int id_empleado) {
        this.id_empleado = id_empleado;
    }

    /**
     * @return the dni
     */
    public String getDni() {
        return dni;
    }

    /**
     * @param dni the dni to set
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * @return the nombres
     */
    public String getNombres() {
        return nombres;
    }

    /**
     * @param nombres the nombres to set
     */
    public void setNombres(String nombres) {
        this.nombres = nombres.toUpperCase();
    }

    /**
     * @return the ape_pat
     */
    public String getApe_pat() {
        return ape_pat;
    }

    /**
     * @param ape_pat the ape_pat to set
     */
    public void setApe_pat(String ape_pat) {
        this.ape_pat = ape_pat.toUpperCase();
    }

    /**
     * @return the ape_mat
     */
    public String getApe_mat() {
        return ape_mat;
    }

    /**
     * @param ape_mat the ape_mat to set
     */
    public void setApe_mat(String ape_mat) {
        this.ape_mat = ape_mat.toUpperCase();
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion.toUpperCase();
    }

    /**
     * @return the correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @param correo the correo to set
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the fecha_nacimiento
     */
    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    /**
     * @param fecha_nacimiento the fecha_nacimiento to set
     */
    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    /**
     * @return the nick
     */
    public String getNick() {
        return nick;
    }

    /**
     * @param nick the nick to set
     */
    public void setNick(String nick) {
        this.nick = nick.toLowerCase();
    }

    /**
     * @return the contraseña
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * @param contrasena the contrasena to set
     */
    public void setContraseña(String contrasena) {
        this.contrasena = contrasena;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void ver_empleados(JTable tabla, String query) {
        try {
            DefaultTableModel mostrar = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int fila, int columna) {
                    return false;
                }
            };
            st = c_conectar.conexion();
            rs = c_conectar.consulta(st, query);

            RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(mostrar);
            tabla.setRowSorter(sorter);

            mostrar.addColumn("Codigo");
            mostrar.addColumn("Usuario");
            mostrar.addColumn("DNI");
            mostrar.addColumn("Apellidos y Nombres");
            mostrar.addColumn("Telefono");
            mostrar.addColumn("Fecha Nac.");

            while (rs.next()) {
                Object fila[] = new Object[6];

                fila[0] = rs.getInt("id_empleado");
                fila[1] = rs.getString("nick");
                fila[2] = rs.getString("dni");
                fila[3] = rs.getString("ape_pat") + " " + rs.getString("ape_mat").trim() + " " + rs.getString("nombres");
                fila[4] = rs.getString("telefono");
                fila[5] = rs.getString("fecha_nacimiento");

                mostrar.addRow(fila);
            }

            c_conectar.cerrar(st);
            c_conectar.cerrar(rs);
            tabla.setModel(mostrar);
            tabla.getColumnModel().getColumn(0).setPreferredWidth(30);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(80);
            tabla.getColumnModel().getColumn(2).setPreferredWidth(80);
            tabla.getColumnModel().getColumn(3).setPreferredWidth(300);
            tabla.getColumnModel().getColumn(4).setPreferredWidth(100);
            tabla.getColumnModel().getColumn(5).setPreferredWidth(100);

        } catch (SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public boolean insertar() {
        boolean grabar = false;
        try {
            st = c_conectar.conexion();
            String squly = "insert into empleados values('" + id_empleado + "','" + dni + "','" + nombres + "','" + ape_pat + "','" + ape_mat + "',"
                    + "'" + direccion + "','" + correo + "','" + telefono + "','" + fecha_nacimiento + "','" + nick + "','" + contrasena + "','1')";
            int respuesta = c_conectar.actualiza(st, squly);
            if (respuesta > -1) {
                grabar = true;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "no grabo por falta de coneccion");
        }
        return grabar;
    }

    public int obtener_codigo() {
        int codigo = 0;
        try {
            st = c_conectar.conexion();
            String sql = "select ifnull(max(id_empleado)+1, 1) as id_empleado from empleados";
            rs = c_conectar.consulta(st, sql);
            if (rs.next()) {
                id_empleado = rs.getInt("id_empleado");
                codigo = id_empleado;

            }
        } catch (SQLException e) {
        }
        c_conectar.cerrar(st);
        c_conectar.cerrar(rs);

        return codigo;
    }

    public boolean modificar() {
        boolean modificar = false;
        try {
            st = c_conectar.conexion();
            String Sql = "update empleados "
                    + "set dni='" + dni + "',nombres='" + nombres + "',ape_pat='" + ape_pat + "',ape_mat='" + ape_mat + "',"
                    + "direccion='" + direccion + "',correo='" + correo + "',telefono='" + telefono + "',fecha_nacimiento='" + fecha_nacimiento + "',"
                    + " nick='" + nick + "',contrasena='" + contrasena + "' "
                    + "where id_empleado = '" + id_empleado + "'";
            int respuesta = c_conectar.actualiza(st, Sql);
            if (respuesta > -1) {
                modificar = true;
            }
        } catch (Exception e) {
        }
        return modificar;
    }

    public boolean cargar_datos() {
        boolean existe = false;

        try {
            st = c_conectar.conexion();
            String query = "select * "
                    + "from empleados "
                    + "where id_empleado = '" + id_empleado
                    + "'";
            rs = c_conectar.consulta(st, query);

            if (rs.next()) {
                id_empleado = rs.getInt("id_empleado");
                dni = rs.getString("dni");
                nombres = rs.getString("nombres");
                ape_pat = rs.getString("ape_pat");
                ape_mat = rs.getString("ape_mat");
                direccion = rs.getString("direccion");
                correo = rs.getString("correo");
                telefono = rs.getString("telefono");
                fecha_nacimiento = rs.getString("fecha_nacimiento");
                nick = rs.getString("nick");
                contrasena = rs.getString("contrasena");
                estado = rs.getString("estado");
                existe = true;
            }

            c_conectar.cerrar(rs);
            c_conectar.cerrar(st);
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }

        return existe;
    }

    public boolean validar_usuario() {
        boolean existe = false;
        try {
            st = c_conectar.conexion();
            String sql = "select id_empleado "
                    + "from empleados "
                    + "where nick = '" + nick + "'";
            rs = c_conectar.consulta(st, sql);
            if (rs.next()) {
                id_empleado = rs.getInt("id_empleado");
                existe = true;

            }
        } catch (SQLException e) {
        }
        c_conectar.cerrar(st);
        c_conectar.cerrar(rs);

        return existe;
    }

}
