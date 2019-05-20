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
public class cl_empleado {

    cl_conectar c_conectar = new cl_conectar();

    private int id_empleado;
    private int dni;
    private String nombres;
    private String ape_pat;
    private String ape_mat;
    private String direccion;
    private String email;
    private String celular;
    private String nick;
    private String contrasena;
    private String estado;
    private int id_cargo;

    public cl_empleado() {
    }

    public int getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(int id_empleado) {
        this.id_empleado = id_empleado;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApe_pat() {
        return ape_pat;
    }

    public void setApe_pat(String ape_pat) {
        this.ape_pat = ape_pat;
    }

    public String getApe_mat() {
        return ape_mat;
    }

    public void setApe_mat(String ape_mat) {
        this.ape_mat = ape_mat;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getId_cargo() {
        return id_cargo;
    }

    public void setId_cargo(int id_cargo) {
        this.id_cargo = id_cargo;
    }

    public boolean validar_usuario() {
        boolean bvalidar = false;

        try {
            Statement st = c_conectar.conexion();
            String query = "select id_empleado, nick, contrasena, estado from empleados where nick = '" + this.nick + "'";
            ResultSet rs = c_conectar.consulta(st, query);
            if (rs.next()) {
                bvalidar = true;
                id_empleado = rs.getInt("id_empleado");
                contrasena = rs.getString("contrasena").trim();
                nick = rs.getString("nick");
                estado = rs.getString("estado");
            }
            c_conectar.cerrar(rs);
            c_conectar.cerrar(st);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }

        return bvalidar;
    }

    public boolean cargar_datos() {
        boolean existe = false;

        try {
            Statement st = c_conectar.conexion();
            String query = "select * from empleados where id_empleado = '" + this.id_empleado + "'";
            ResultSet rs = c_conectar.consulta(st, query);

            if (rs.next()) {
                existe = true;
                dni = rs.getInt("dni");
                nombres = rs.getString("nombres");
                ape_pat = rs.getString("ape_pat");
                ape_mat = rs.getString("ape_mat");
            }

            c_conectar.cerrar(rs);
            c_conectar.cerrar(st);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }

        return existe;
    }
}
