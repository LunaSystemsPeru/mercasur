/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author CALIDAD
 */
public class cl_proveedor {

    cl_conectar c_conectar = new cl_conectar();
    cl_varios c_varios = new cl_varios();
    
    private int id;
    private String ruc;
    private String razon_social;
    private String nombre_comercial;
    private String direccion;
    private String telefono;
    private String web;
    private String email;
    private double total_compras;
    private double total_pagado;
    private String estado;

    public cl_proveedor() {
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getRazon_social() {
        return razon_social;
    }

    public void setRazon_social(String razon_social) {
        this.razon_social = razon_social;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public double getTotal_compras() {
        return total_compras;
    }

    public void setTotal_compras(double total_compras) {
        this.total_compras = total_compras;
    }

    public double getTotal_pagado() {
        return total_pagado;
    }

    public String getNombre_comercial() {
        return nombre_comercial;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public void setNombre_comercial(String nombre_comercial) {
        this.nombre_comercial = nombre_comercial;
    }

    public void setTotal_pagado(double total_pagado) {
        this.total_pagado = total_pagado;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList cargar_rucs() {
        ArrayList rucs = new ArrayList();

        try {
            Statement st = c_conectar.conexion();
            String c_placas = "select ruc_pro "
                    + "from proveedores "
                    + "order by ruc_pro asc";
            ResultSet rs = c_conectar.consulta(st, c_placas);

            while (rs.next()) {
                rucs.add(rs.getString("ruc_pro"));
            }

            c_conectar.cerrar(rs);
            c_conectar.cerrar(st);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }

        return rucs;
    }

    public boolean cargar_datos_proveedor() {
        boolean existe = false;

        try {
            Statement st = c_conectar.conexion();
            String query = " select id_proveedor,raz_soc_pro, dir_pro, telefono, web, email, total_pagado, total_compras "
                    + "from proveedores "
                    + "where ruc_pro = '" + ruc + "'";
            ResultSet rs = c_conectar.consulta(st, query);

            while (rs.next()) {
                existe = true;
                id=rs.getInt("id_proveedor");
                razon_social = rs.getString("raz_soc_pro");
                direccion = rs.getString("dir_pro");
                telefono = rs.getString("telefono");
                email = rs.getString("email");
                total_pagado = rs.getDouble("total_pagado");
                total_compras = rs.getInt("total_compras");
            }

            c_conectar.cerrar(rs);
            c_conectar.cerrar(st);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }

        return existe;
    }
    
     public double suma_pagos() {
        double pagos = 0;

        try {
            Statement st = c_conectar.conexion();
            String query = "select sum(total - pagado) as cobranza from compras";
            ResultSet rs = c_conectar.consulta(st, query);

            while (rs.next()) {
                pagos = rs.getDouble("cobranza");
            }
            c_conectar.cerrar(rs);
            c_conectar.cerrar(st);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }

        return pagos;
    }
     
     public int obtener_codigo() {
        int resultado = 0;

        try {
            Statement st = c_conectar.conexion();
            String query = "select ifnull(max(id) + 1, 1) as codigo "
                    + "from proveedores";
            ResultSet rs = c_conectar.consulta(st, query);
            System.out.println(query);
            while (rs.next()) {
                resultado = rs.getInt("codigo");
            }

            c_conectar.cerrar(rs);
            c_conectar.cerrar(st);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }

        return resultado;
    }

    public boolean insertar() {
        boolean grabado = false;
        Statement st = c_conectar.conexion();
        String query = "insert into proveedores "
                + "Values ('" + id + "', '" + ruc + "', '" + razon_social + "','" + nombre_comercial + "', '" + direccion + "', '" + telefono + "', '" + web + "', '" + email + "', 0, 0, '1')";
        int resultado = c_conectar.actualiza(st, query);

        if (resultado > -1) {
            grabado = true;
        }

        c_conectar.cerrar(st);

        return grabado;
    }
    
    public void ver_proveedores(JTable tabla, String query) {
        try {
            DefaultTableModel mostrar = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int fila, int columna) {
                    return false;
                }
            };

            Statement st = c_conectar.conexion();
            ResultSet rs = c_conectar.consulta(st, query);

            RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(mostrar);
            tabla.setRowSorter(sorter);

            mostrar.addColumn("RUC");
            mostrar.addColumn("Razon Social");
            mostrar.addColumn("S/ Compras");
            mostrar.addColumn("S/ Deudas");
            mostrar.addColumn("Estado");

            while (rs.next()) {
                double compras = rs.getDouble("total_compras");
                double pagado = rs.getDouble("total_pagado");
                double deuda = compras - pagado;

                Object fila[] = new Object[5];

                fila[0] = rs.getString("ruc_pro");
                fila[1] = rs.getString("raz_soc_pro").trim().toUpperCase();
                fila[2] = c_varios.formato_totales(compras);
                fila[3] = c_varios.formato_totales(deuda);
                if (compras > 0 & deuda == 0) {
                    fila[4] = "-";
                }
                if (compras > 0 & deuda > 0) {
                    fila[4] = "DEUDOR";
                }
                
                if (deuda < 0) {
                    fila[4] = "DEUDOR";
                }
                if (compras == 0) {
                    fila[4] = "INACTIVO";
                }
                mostrar.addRow(fila);
            }

            c_conectar.cerrar(st);
            c_conectar.cerrar(rs);
            tabla.setModel(mostrar);
            tabla.getColumnModel().getColumn(0).setPreferredWidth(100);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(500);
            tabla.getColumnModel().getColumn(2).setPreferredWidth(80);
            tabla.getColumnModel().getColumn(3).setPreferredWidth(80);
            tabla.getColumnModel().getColumn(4).setPreferredWidth(100);
            tabla.setDefaultRenderer(Object.class, new render_tables.render_proveedores());
        } catch (SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, ex);
        }
    }
}
