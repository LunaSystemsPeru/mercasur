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
import javax.swing.table.DefaultTableModel;
import mercasur.frm_menu;

/**
 *
 * @author CALIDAD
 */
public class cl_pago_compras {

    cl_conectar c_conectar = new cl_conectar();
    cl_varios c_varios = new cl_varios();

    private int codigo;
    private String periodo;
    private int compra;
    private String fecha;
    private int moneda;
    private int tipo;
    private double tc;
    private double monto;
    private String usuario;

    public cl_pago_compras() {
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public int getMoneda() {
        return moneda;
    }

    public void setMoneda(int moneda) {
        this.moneda = moneda;
    }

    public double getTc() {
        return tc;
    }

    public void setTc(double tc) {
        this.tc = tc;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getCompra() {
        return compra;
    }

    public void setCompra(int compra) {
        this.compra = compra;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double mostrar_pagos(JTable tabla) {
        double suma_pagado = 0;
        try {
            DefaultTableModel modelo_pago = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int fila, int columna) {
                    return false;
                }
            };
            modelo_pago.addColumn("idPago");
            modelo_pago.addColumn("Fecha");
            modelo_pago.addColumn("Moneda");
            modelo_pago.addColumn("Tipo");
            modelo_pago.addColumn("Monto");
            modelo_pago.addColumn("T.C");
            modelo_pago.addColumn("Monto M.N");

            Statement st = c_conectar.conexion();
            String ver_pagos = "select p.id_pago, m.corto, p.id_moneda as moneda_pago, c.idmoneda as moneda_compra, p.monto, p.fec_pago, p.tc as compra, p.tipo_pago "
                    + "from pagos_compra as p "
                    + "inner join compras as c on c.periodo = p.periodo and c.idcompra = p.compra "
                    + "inner join moneda as m on p.id_moneda = m.idmon "
                    + "where p.compra = '" + compra + "' and p.periodo = '" + periodo + "'";
            ResultSet rs = c_conectar.consulta(st, ver_pagos);
            Object fila_p[] = new Object[7];
            while (rs.next()) {
                int moneda_pago = rs.getInt("moneda_pago");
                int moneda_compra = rs.getInt("moneda_compra");
                fila_p[0] = rs.getString("id_pago");
                fila_p[1] = c_varios.formato_fecha_vista(rs.getString("fec_pago"));
                fila_p[2] = rs.getString("corto");
                if (rs.getInt("tipo_pago") == 1) {
                    fila_p[3] = "EFECTIVO";
                }
                if (rs.getInt("tipo_pago") == 2) {
                    fila_p[3] = "TARJETA";
                }
                if (moneda_pago == moneda_compra) {
                    fila_p[4] = c_varios.formato_totales(rs.getDouble("monto"));
                    suma_pagado = suma_pagado + rs.getDouble("monto");
                    fila_p[5] = "-";
                    fila_p[6] = "-";
                } else {
                    if (moneda_pago == 1) {
                        fila_p[4] = c_varios.formato_totales(rs.getDouble("monto") / rs.getDouble("compra"));
                        fila_p[5] = c_varios.formato_tc(rs.getDouble("compra"));
                        fila_p[6] = c_varios.formato_totales(rs.getDouble("monto"));
                        suma_pagado = suma_pagado + (rs.getDouble("monto") / rs.getDouble("compra"));
                    }
                    if (moneda_pago == 2) {
                        fila_p[4] = c_varios.formato_totales(rs.getDouble("monto"));
                        fila_p[5] = c_varios.formato_tc(rs.getDouble("compra"));
                        fila_p[6] = c_varios.formato_totales(rs.getDouble("monto") * rs.getDouble("compra"));
                        suma_pagado = suma_pagado + (rs.getDouble("monto") * rs.getDouble("compra"));
                    }
                }
                modelo_pago.addRow(fila_p);
            }
            tabla.setModel(modelo_pago);
            c_varios.centrar_celda(tabla, 0);
            c_varios.centrar_celda(tabla, 1);
            c_varios.centrar_celda(tabla, 2);
            c_varios.centrar_celda(tabla, 3);
            c_varios.derecha_celda(tabla, 4);
            c_varios.derecha_celda(tabla, 5);
            c_varios.derecha_celda(tabla, 6);
            c_conectar.cerrar(rs);
            c_conectar.cerrar(st);
        } catch (SQLException | NumberFormatException e) {
            System.out.println(e.getLocalizedMessage());
        }
        return suma_pagado;
    }

    public int obtener_codigo() {
        int resultado = 0;

        try {
            Statement st = c_conectar.conexion();
            String query = "select ifnull(max(id_pago) + 1, 1) as codigo "
                    + "from pagos_compra "
                    + "where periodo = '" + periodo + "' and compra = '" + compra + "' ";
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

    public boolean insertar() {
        boolean grabado = false;
        Statement st = c_conectar.conexion();
        String query = "insert into pagos_compra "
                + "Values ('" + periodo + "', '" + compra + "', '" + codigo + "', '" + fecha + "', '" + tipo + "', '" + moneda + "', "
                + "'" + tc + "', '" + monto + "', '" + frm_menu.c_empleado.getId_empleado() + "')";
        int resultado = c_conectar.actualiza(st, query);

        if (resultado > -1) {
            grabado = true;
        }

        c_conectar.cerrar(st);

        return grabado;
    }
    
    public boolean eliminar() {
        boolean grabado = false;
        Statement st = c_conectar.conexion();
        String query = "delete from pagos_compra "
                + "where periodo = '" + periodo + "' and compra = '" + compra + "' and id_pago = '" + codigo + "'";
        int resultado = c_conectar.actualiza(st, query);

        if (resultado > -1) {
            grabado = true;
        }

        c_conectar.cerrar(st);

        return grabado;
    }
}
