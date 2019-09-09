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
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author luis
 */
public class cl_vehiculo {

    cl_conectar c_conectar = new cl_conectar();

    private int id_vehiculo;
    private String placa;
    private String marca;
    private String modelo;
    private String brevete;
    private String chofer;

    public cl_vehiculo() {
    }

    public int getId_vehiculo() {
        return id_vehiculo;
    }

    public void setId_vehiculo(int id_vehiculo) {
        this.id_vehiculo = id_vehiculo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getBrevete() {
        return brevete;
    }

    public void setBrevete(String brevete) {
        this.brevete = brevete;
    }

    public String getChofer() {
        return chofer;
    }

    public void setChofer(String chofer) {
        this.chofer = chofer;
    }

    public void obtener_id() {
        try {
            Statement st = c_conectar.conexion();
            String query = "select ifnull(max(id_vehiculo)+1, 1) as codigo "
                    + "from vehiculos ";
            ResultSet rs = c_conectar.consulta(st, query);

            if (rs.next()) {
                id_vehiculo = rs.getInt("codigo");
            }

            c_conectar.cerrar(rs);
            c_conectar.cerrar(st);
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    public boolean cargar_datos() {
        boolean existe = false;

        try {
            Statement st = c_conectar.conexion();
            String query = "select * "
                    + "from vehiculos "
                    + "where id_vehiculo = '" + id_vehiculo + "'";
            ResultSet rs = c_conectar.consulta(st, query);

            if (rs.next()) {
                marca = rs.getString("marca");
                modelo = rs.getString("modelo");
                brevete = rs.getString("brevete");
                chofer = rs.getString("chofer_datos");
                placa = rs.getString("placas");
                existe = true;
            }

            c_conectar.cerrar(rs);
            c_conectar.cerrar(st);
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }

        return existe;
    }

    public boolean insertar() {
        boolean grabado = false;
        Statement st = c_conectar.conexion();
        String query = "insert into vehiculos "
                + "Values ('" + id_vehiculo + "', '" + placa + "', '" + marca + "','" + modelo + "', '" + brevete + "','" + chofer + "')";
        int resultado = c_conectar.actualiza(st, query);

        if (resultado > -1) {
            grabado = true;
        }

        c_conectar.cerrar(st);

        return grabado;
    }

    public boolean modificar() {
        boolean grabado = false;
        Statement st = c_conectar.conexion();
        String query = "update vehiculos "
                + "set placa = '" + placa + "', marca = '" + marca + "', modelo = '" + modelo + "', brevete = '" + brevete + "', chofer_datos = '" + chofer + "' "
                + "where id_vehiculo = '" + id_vehiculo + "'";
        int resultado = c_conectar.actualiza(st, query);

        if (resultado > -1) {
            grabado = true;
        }

        c_conectar.cerrar(st);

        return grabado;
    }
    
    public void mostrar(JTable tabla, String query) {
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

            mostrar.addColumn("Codigo");
            mostrar.addColumn("Placa");
            mostrar.addColumn("Vehiculo");
            mostrar.addColumn("Chofer");

            while (rs.next()) {
                Object fila[] = new Object[4];

                fila[0] = rs.getInt("id_vehiculo");
                fila[1] = rs.getString("placa");
                fila[2] = rs.getString("marca").trim() + " | " + rs.getString("modelo").trim();
                fila[3] = rs.getString("brevete").trim() + " | " + rs.getString("chofer_datos").trim();
                mostrar.addRow(fila);
            }

            c_conectar.cerrar(st);
            c_conectar.cerrar(rs);
            tabla.setModel(mostrar);
            tabla.getColumnModel().getColumn(0).setPreferredWidth(50);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(60);
            tabla.getColumnModel().getColumn(2).setPreferredWidth(200);
            tabla.getColumnModel().getColumn(3).setPreferredWidth(300);
        } catch (SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, ex);
        }
    }
}
