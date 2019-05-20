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
 * @author CALIDAD
 */
public class cl_cliente {

    cl_conectar c_conectar = new cl_conectar();
    cl_varios c_varios = new cl_varios();

    private int id_cliente;
    private int id_zona;
    private String documento;
    private String nombre;
    private String n_comercial;
    private String direccion;
    private int telefono;
    private int celular;
    private double ventas;
    private double pagado;

    public cl_cliente() {
    }

    public cl_cliente(int id_cliente, String nombre, String n_comercial) {
        this.id_cliente = id_cliente;
        this.nombre = nombre;
        this.n_comercial = n_comercial;
    }

    @Override
    public String toString() {
        return this.n_comercial;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public int getId_zona() {
        return id_zona;
    }

    public void setId_zona(int id_zona) {
        this.id_zona = id_zona;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getN_comercial() {
        return n_comercial;
    }

    public void setN_comercial(String n_comercial) {
        this.n_comercial = n_comercial;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public int getCelular() {
        return celular;
    }

    public void setCelular(int celular) {
        this.celular = celular;
    }

    public double getVentas() {
        return ventas;
    }

    public void setVentas(double ventas) {
        this.ventas = ventas;
    }

    public double getPagado() {
        return pagado;
    }

    public void setPagado(double pagado) {
        this.pagado = pagado;
    }

    public boolean insertar() {
        boolean grabado = false;
        Statement st = c_conectar.conexion();
        String query = "insert into clientes "
                + "Values ('" + id_cliente + "', '" + id_zona + "', '" + documento + "', '" + nombre + "', '" + n_comercial + "', '" + direccion + "','" + telefono + "', '" + celular + "', '0', '0', '1')";
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
        String query = "update clientes "
                + "set nombre = '" + nombre + "', direccion = '" + direccion + "', telefono = '" + telefono + "', celular = '" + celular + "' "
                + "where id_cliente = '" + id_cliente + "' and id_zona = '" + id_zona + "'";
        int resultado = c_conectar.actualiza(st, query);

        if (resultado > -1) {
            grabado = true;
        }

        c_conectar.cerrar(st);

        return grabado;
    }

    public int obtener_codigo() {
        int resultado = 0;

        try {
            Statement st = c_conectar.conexion();
            String query = "select ifnull(max(id_cliente) + 1, 1) as codigo "
                    + "from clientes "
                    + "where id_zona = '" + id_zona + "'";
            ResultSet rs = c_conectar.consulta(st, query);

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

    public boolean cargar_datos() {
        boolean existe = false;

        try {
            Statement st = c_conectar.conexion();
            String query = "select nombre, documento, nombre_comercial, direccion, telefono, celular, ventas, pagado "
                    + "from clientes where id_cliente = '" + id_cliente + "' and id_zona = '" + id_zona + "'";
            ResultSet rs = c_conectar.consulta(st, query);

            while (rs.next()) {
                existe = true;
                documento = rs.getString("documento");
                n_comercial = rs.getString("nombre_comercial");
                nombre = rs.getString("nombre").toUpperCase().trim();
                direccion = rs.getString("direccion").toUpperCase().trim();
                telefono = rs.getInt("telefono");
                celular = rs.getInt("celular");
                ventas = rs.getDouble("ventas");
                pagado = rs.getDouble("pagado");
            }
            c_conectar.cerrar(rs);
            c_conectar.cerrar(st);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }

        return existe;
    }
    
    public boolean buscar_cliente_documento() {
        boolean existe = false;

        try {
            Statement st = c_conectar.conexion();
            String query = "select id_cliente, id_zona "
                    + "from clientes where documento = '" + id_cliente + "'";
            ResultSet rs = c_conectar.consulta(st, query);

            while (rs.next()) {
                existe = true;
                id_cliente = rs.getInt("id_cliente");
                id_zona = rs.getInt("id_zona");
            }
            c_conectar.cerrar(rs);
            c_conectar.cerrar(st);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }

        return existe;
    }

    public void ver_clientes(JTable tabla, String query) {
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
            //mostrar.addColumn("Zona");
            mostrar.addColumn("Documento / Nombre");
            mostrar.addColumn("Monto Ventas");
            mostrar.addColumn("Monto Deuda");
            mostrar.addColumn("Estado");

            while (rs.next()) {
                double dventas = rs.getDouble("ventas");
                double dpagado = rs.getDouble("pagado");
                double ddeuda = dventas - dpagado;

                Object fila[] = new Object[5];

                fila[0] = c_varios.ceros_izquieda_numero(6, rs.getInt("id_cliente"));
                fila[1] = rs.getString("nombre").trim() + " | Nro Doc: " + rs.getString("documento").trim();
                fila[2] = c_varios.formato_totales(dventas);
                fila[3] = c_varios.formato_totales(ddeuda);
                if (dventas > 0 & ddeuda == 0) {
                    fila[4] = "BUEN CLIENTE";
                }
                if (dventas > 0 & ddeuda > 0) {
                    fila[4] = "DEUDOR";
                }

                if (ddeuda < 0) {
                    fila[4] = "DEUDOR";
                }
                if (dventas == 0) {
                    fila[4] = "INACTIVO";
                }
                mostrar.addRow(fila);
            }

            c_conectar.cerrar(st);
            c_conectar.cerrar(rs);
            tabla.setModel(mostrar);
            tabla.getColumnModel().getColumn(0).setPreferredWidth(50);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(500);
            tabla.getColumnModel().getColumn(2).setPreferredWidth(80);
            tabla.getColumnModel().getColumn(3).setPreferredWidth(80);
            tabla.getColumnModel().getColumn(4).setPreferredWidth(100);
            tabla.setDefaultRenderer(Object.class, new render_tables.render_clientes());
        } catch (SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public double ver_deuda_venta(JTable tabla, String query) {
        double total = 0;
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

            mostrar.addColumn("Codigo"); //periodo + id
            mostrar.addColumn("Fecha");
            mostrar.addColumn("Documento");
            mostrar.addColumn("Placa");
            mostrar.addColumn("Cliente");
            mostrar.addColumn("Monto");
            mostrar.addColumn("Deuda");
            mostrar.addColumn("Estado");
            //Creando las filas para el JTable
            while (rs.next()) {
                total = total + (rs.getDouble("total") - rs.getDouble("descuento"));
                double deuda = rs.getDouble("total") - rs.getDouble("descuento") - rs.getDouble("pagado");
                Object[] fila = new Object[8];
                fila[0] = rs.getString("periodo") + c_varios.ceros_izquieda_numero(5, rs.getInt("id"));
                fila[1] = c_varios.formato_fecha_vista(rs.getString("fecha_venta"));;
                fila[2] = rs.getString("documento") + " / " + c_varios.ceros_izquieda_letras(4, rs.getString("serie_doc")) + " - " + c_varios.ceros_izquieda_numero(7, rs.getInt("nro_doc"));
                fila[3] = rs.getString("nro_placa");
                fila[4] = rs.getString("doc_cliente") + " | " + rs.getString("nombre_cliente");
                fila[5] = c_varios.formato_totales(rs.getDouble("total") - rs.getDouble("descuento"));
                fila[6] = c_varios.formato_numero(deuda);
                if (rs.getString("estado").equals("1")) {
                    fila[7] = "PAGADO";
                }
                if (rs.getString("estado").equals("0")) {
                    fila[7] = "PENDIENTE";
                }
                if (rs.getString("estado").equals("2")) {
                    fila[7] = "ANULADO";
                }
                mostrar.addRow(fila);
            }
            c_conectar.cerrar(st);
            c_conectar.cerrar(rs);
            tabla.setModel(mostrar);
            tabla.getColumnModel().getColumn(0).setPreferredWidth(65);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(70);
            tabla.getColumnModel().getColumn(2).setPreferredWidth(130);
            tabla.getColumnModel().getColumn(3).setPreferredWidth(70);
            tabla.getColumnModel().getColumn(4).setPreferredWidth(410);
            tabla.getColumnModel().getColumn(5).setPreferredWidth(70);
            tabla.getColumnModel().getColumn(6).setPreferredWidth(70);
            tabla.getColumnModel().getColumn(7).setPreferredWidth(70);
            tabla.setDefaultRenderer(Object.class, new render_tables.render_ventas());
        } catch (SQLException e) {
            System.out.print(e);
        }
        return total;
    }
}
