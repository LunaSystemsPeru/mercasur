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
public class cl_productos {

    cl_conectar c_conectar = new cl_conectar();
    cl_varios c_varios = new cl_varios();

    private int id_producto;
    private String descripcion;
    private double costo;
    private double precio;
    private double cantidad;
    private int id_unidad;
    private int id_marca;
    private String imagen;
    private int estado;
    private String ultima_salida;
    private String ultimo_ingreso;
    private int id_proveedor;

    public cl_productos() {
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public int getId_unidad() {
        return id_unidad;
    }

    public void setId_unidad(int id_unidad) {
        this.id_unidad = id_unidad;
    }

    public int getId_marca() {
        return id_marca;
    }

    public void setId_marca(int id_marca) {
        this.id_marca = id_marca;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getUltima_salida() {
        return ultima_salida;
    }

    public void setUltima_salida(String ultima_salida) {
        this.ultima_salida = ultima_salida;
    }

    public String getUltimo_ingreso() {
        return ultimo_ingreso;
    }

    public void setUltimo_ingreso(String ultimo_ingreso) {
        this.ultimo_ingreso = ultimo_ingreso;
    }

    public int getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(int id_proveedor) {
        this.id_proveedor = id_proveedor;
    }

    public void ver_productos(JTable tabla, String query) {
        try {
            DefaultTableModel mostrar = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int fila, int columna) {
                    return false;
                }
            };

            Statement st = c_conectar.conexion();
            ResultSet rs = c_conectar.consulta(st, query);

            mostrar.addColumn("Codigo");
            mostrar.addColumn("Descripcion");
            mostrar.addColumn("Marca");
            mostrar.addColumn("Comision %");
            mostrar.addColumn("Cant.");
            mostrar.addColumn("Und Med.");
            mostrar.addColumn("P. Vta");
            mostrar.addColumn("Estado");

            String valor_estado = "NORMAL";

            while (rs.next()) {
                if (rs.getInt("estado") == 0) {
                    valor_estado = "NO ACTIVO";
                }

                if (rs.getInt("estado") == 1) {
                    if (rs.getDouble("cant_actual") <= 0) {
                        valor_estado = "NO DISPONIBLE";
                    }

                    if (rs.getDouble("cant_actual") > 0) {
                        valor_estado = "NORMAL";
                    }
                }

                Object fila[] = new Object[8];

                fila[0] = rs.getString("id_producto").toUpperCase();
                fila[1] = rs.getString("descripcion").trim().toUpperCase();
                fila[2] = rs.getString("marca").toUpperCase().trim();
                fila[3] = c_varios.formato_numero(rs.getDouble("comision"));
                fila[4] = c_varios.formato_numero(rs.getDouble("cant_actual"));
                fila[5] = "UND";
                fila[6] = c_varios.formato_numero(rs.getDouble("precio_venta"));
                fila[7] = valor_estado;

                mostrar.addRow(fila);
            }

            c_conectar.cerrar(st);
            c_conectar.cerrar(rs);
            tabla.setModel(mostrar);
            tabla.getColumnModel().getColumn(0).setPreferredWidth(70);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(400);
            tabla.getColumnModel().getColumn(2).setPreferredWidth(180);
            tabla.getColumnModel().getColumn(3).setPreferredWidth(80);
            tabla.getColumnModel().getColumn(4).setPreferredWidth(80);
            tabla.getColumnModel().getColumn(5).setPreferredWidth(80);
            tabla.getColumnModel().getColumn(6).setPreferredWidth(80);
            tabla.getColumnModel().getColumn(7).setPreferredWidth(80);
            tabla.setDefaultRenderer(Object.class, new render_tables.render_productos());

            RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(mostrar);
            tabla.setRowSorter(sorter);
        } catch (SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void ver_kardex(JTable tabla) {
        double saldo = 0;
        try {
            DefaultTableModel mostrar = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int fila, int columna) {
                    return false;
                }
            };
            String query = "select k.id_kardex, k.fecha, k.ruc, k.datos_ruc, tm.nom_mov, td.abreviado as documento, k.serie_doc, k.numero_doc, k.cant_ing, k.cant_sal, k.fecha_registro "
                    + "from kardex as k "
                    + "inner join tipo_movimiento as tm on k.tipo_movimiento = tm.idtipo_movimiento "
                    + "inner join tipo_documento as td on td.id = k.id_documento "
                    + "where k.id_producto = '" + id_producto + "'"
                    + "order by k.fecha asc";

            Statement st = c_conectar.conexion();
            ResultSet rs = c_conectar.consulta(st, query);

            RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(mostrar);
            tabla.setRowSorter(sorter);

            mostrar.addColumn("COD");
            mostrar.addColumn("FECHA");
            mostrar.addColumn("MOTIVO");
            mostrar.addColumn("CLIENTE / PROVEEDOR");
            mostrar.addColumn("DOCUMENTO");
            mostrar.addColumn("INGRESA");
            mostrar.addColumn("SALIDA");
            mostrar.addColumn("SALDO");

            while (rs.next()) {
                Object fila[] = new Object[8];
                saldo = saldo + (rs.getDouble("cant_ing") - rs.getDouble("cant_sal"));
                fila[0] = rs.getString("id_kardex").toUpperCase();
                fila[1] = c_varios.formato_fecha_vista(rs.getString("fecha"));
                fila[2] = rs.getString("nom_mov");
                fila[3] = rs.getString("ruc") + " | " + rs.getString("datos_ruc");
                fila[4] = rs.getString("documento") + " | " + c_varios.ceros_izquieda_letras(4, rs.getString("serie_doc")) + " - " + c_varios.ceros_izquieda_numero(7, rs.getInt("numero_doc"));
                fila[5] = c_varios.formato_totales(rs.getDouble("cant_ing"));
                fila[6] = c_varios.formato_totales(rs.getDouble("cant_sal"));
                fila[7] = c_varios.formato_totales(saldo);

                mostrar.addRow(fila);
            }

            c_conectar.cerrar(st);
            c_conectar.cerrar(rs);
            tabla.setModel(mostrar);
            tabla.getColumnModel().getColumn(0).setPreferredWidth(50);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(70);
            tabla.getColumnModel().getColumn(2).setPreferredWidth(70);
            tabla.getColumnModel().getColumn(3).setPreferredWidth(320);
            tabla.getColumnModel().getColumn(4).setPreferredWidth(120);
            tabla.getColumnModel().getColumn(5).setPreferredWidth(50);
            tabla.getColumnModel().getColumn(6).setPreferredWidth(50);
            tabla.getColumnModel().getColumn(7).setPreferredWidth(50);
            c_varios.centrar_celda(tabla, 0);
            c_varios.centrar_celda(tabla, 1);
            c_varios.centrar_celda(tabla, 2);
            c_varios.centrar_celda(tabla, 4);
            c_varios.derecha_celda(tabla, 5);
            c_varios.derecha_celda(tabla, 6);
            c_varios.derecha_celda(tabla, 7);
        } catch (SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public boolean ver_datos_producto() {
        boolean existe = false;

        try {
            Statement st = c_conectar.conexion();
            String query = "select * from "
                    + "productos "
                    + "where id_producto = '" + id_producto + "'";
            ResultSet rs = c_conectar.consulta(st, query);

            while (rs.next()) {
                existe = true;
                descripcion = rs.getString("descripcion");
                id_marca = rs.getInt("id_marca");
                id_unidad = rs.getInt("id_unidad");
                cantidad = rs.getDouble("cant_actual");
                precio = rs.getDouble("precio_venta");
                costo = rs.getDouble("costo_compra");
            }

            c_conectar.cerrar(rs);
            c_conectar.cerrar(st);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }

        return existe;
    }

    public boolean insertar() {
        boolean grabado = false;
        Statement st = c_conectar.conexion();
        String query = "insert into productos "
                + "values ('" + id_producto + "', '" + descripcion + "', '" + costo + "', '" + precio + "', '0', "
                + "'" + id_unidad + "', '" + id_marca + "', '1', 'noimage.jpg', '1000-01-01', '1000-01-01', 0)";
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
        String query = ""/*"update productos set descripcion = '" + descripcion + "', marca = '" + marca + "', modelo = '" + modelo + "', grado = '" + grado + "', costo_compra = '" + costo + "', "
                + "precio_venta = '" + precio + "', caracteristicas = '" + caracteristicas + "', referencia = '" + referencias + "' where idproducto = '" + codigo + "'"*/;
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
            String query = "select ifnull(max(id_producto) + 1, 1) as codigo "
                    + "from productos";
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

}
