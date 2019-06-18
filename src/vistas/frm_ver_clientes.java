/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistas;

import clases.cl_cliente;
import clases.cl_varios;
import forms.frm_reg_cliente;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import models.cl_combobox;
import models.m_zonas;

/**
 *
 * @author CALIDAD
 */
public class frm_ver_clientes extends javax.swing.JInternalFrame {

    cl_varios c_varios = new cl_varios();
    cl_cliente c_cliente = new cl_cliente();

    int fila_seleccionada;
    int id_zona;

    m_zonas m_zona = new m_zonas();

    /**
     * Creates new form frm_ver_clientes
     */
    public frm_ver_clientes() {
        initComponents();

        m_zona.cbx_todas_zona(cbx_zonas);

        String query = "select c.id_cliente, c.id_zona, z.nombre as zona, c.documento, c.nombre, c.direccion, c.ventas, c.pagado "
                + "from clientes as c "
                + "inner join zona as z on z.id_zona = c.id_zona "
                + "where c.pagado < c.ventas "
                + "order by c.nombre asc";
        c_cliente.ver_clientes(t_clientes, query);
        contar_resultados();

    }

    private void contar_resultados() {
        int contar = 0;
        lbl_contar.setText(t_clientes.getRowCount() + "");
    }

    private void sumar_deudas() {
        double deudas = 0;
        int filas = t_detalle.getRowCount();
        if (filas > -1) {
            for (int i = 0; i < filas; i++) {
                deudas = deudas + Double.parseDouble(t_detalle.getValueAt(i, 6).toString());
            }
        }
        jd_txt_deuda.setText(c_varios.formato_totales(deudas));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jd_detalle = new javax.swing.JDialog();
        jScrollPane2 = new javax.swing.JScrollPane();
        t_detalle = new javax.swing.JTable();
        txt_kardex_descripcion = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jd_txt_deuda = new javax.swing.JTextField();
        btn_eliminar = new javax.swing.JButton();
        btn_modificar = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        btn_deuda = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        t_clientes = new javax.swing.JTable();
        txt_bus = new javax.swing.JTextField();
        cbx_zonas = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        lbl_contar = new javax.swing.JLabel();

        t_detalle.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        t_detalle.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        t_detalle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                t_detalleMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(t_detalle);

        txt_kardex_descripcion.setFocusable(false);
        txt_kardex_descripcion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_kardex_descripcionActionPerformed(evt);
            }
        });

        jLabel2.setText("Suma Deuda:");

        jd_txt_deuda.setEditable(false);
        jd_txt_deuda.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jd_txt_deuda.setText("0.00");

        javax.swing.GroupLayout jd_detalleLayout = new javax.swing.GroupLayout(jd_detalle.getContentPane());
        jd_detalle.getContentPane().setLayout(jd_detalleLayout);
        jd_detalleLayout.setHorizontalGroup(
            jd_detalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jd_detalleLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jd_detalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 928, Short.MAX_VALUE)
                    .addComponent(txt_kardex_descripcion)
                    .addGroup(jd_detalleLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jd_txt_deuda, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jd_detalleLayout.setVerticalGroup(
            jd_detalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jd_detalleLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(txt_kardex_descripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jd_detalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jd_txt_deuda, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        setTitle("Listado de Clientes");

        btn_eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/bin_closed.png"))); // NOI18N
        btn_eliminar.setText("Eliminar");
        btn_eliminar.setEnabled(false);
        btn_eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminarActionPerformed(evt);
            }
        });

        btn_modificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/application_edit.png"))); // NOI18N
        btn_modificar.setText("Modifcar");
        btn_modificar.setEnabled(false);
        btn_modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_modificarActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/cancel.png"))); // NOI18N
        jButton3.setText("Cerrar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        btn_deuda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/clipboard_text.png"))); // NOI18N
        btn_deuda.setText("Ver Deudas Detallada");
        btn_deuda.setEnabled(false);
        btn_deuda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deudaActionPerformed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/find.png"))); // NOI18N
        jLabel1.setText("Buscar:");

        t_clientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"46993209", "LUIS ENRIQUE OYANGUREN GIRON", "947396729", "Normal"},
                {"10469932091", "LEOG SYSTEMS ", "947396729", "Normal"},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Nro. Doc", "Nombre o Razon Social", "Telefono", "Estado"
            }
        ));
        t_clientes.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        t_clientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                t_clientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(t_clientes);

        txt_bus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_busKeyPressed(evt);
            }
        });

        cbx_zonas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbx_zonas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbx_zonasMouseClicked(evt);
            }
        });
        cbx_zonas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbx_zonasActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/find.png"))); // NOI18N
        jButton1.setText("Ver Clientes");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setText("Encontrados:");

        lbl_contar.setText("0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 834, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_bus, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(cbx_zonas, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btn_eliminar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_deuda)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_modificar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_contar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_bus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbx_zonas, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_deuda, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_modificar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(lbl_contar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarActionPerformed
        if (fila_seleccionada > -1) {

        } else {
            JOptionPane.showInternalMessageDialog(this, "NO HA SELECCIONADO UN CLIENTE");
        }
        btn_modificar.setEnabled(false);
        btn_eliminar.setEnabled(false);
        btn_deuda.setEnabled(false);
        fila_seleccionada = -1;
    }//GEN-LAST:event_btn_eliminarActionPerformed

    private void btn_modificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modificarActionPerformed
        if (fila_seleccionada > -1) {
            Frame f = JOptionPane.getRootFrame();
            frm_reg_cliente.c_cliente.setId_cliente(Integer.parseInt(t_clientes.getValueAt(fila_seleccionada, 0).toString()));
            frm_reg_cliente.origen = "cliente";
            frm_reg_cliente.accion = "modificar";
            frm_reg_cliente dialog = new frm_reg_cliente(f, true);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        } else {
            JOptionPane.showInternalMessageDialog(this, "NO HA SELECCIONADO UN CLIENTE");
        }
        btn_modificar.setEnabled(false);
        btn_eliminar.setEnabled(false);
        btn_deuda.setEnabled(false);
        fila_seleccionada = -1;
    }//GEN-LAST:event_btn_modificarActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btn_deudaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deudaActionPerformed
        if (fila_seleccionada > -1) {
            c_cliente.setId_cliente(Integer.parseInt(t_clientes.getValueAt(fila_seleccionada, 0).toString()));
            jd_detalle.setModal(true);
            jd_detalle.setSize(1020, 450);
            jd_detalle.setLocationRelativeTo(null);
            String query = "select v.periodo, v.id, v.nro_placa, v.fecha_venta, td.abreviado as documento, v.serie_doc, v.nro_doc, c.documento as doc_cliente, v.nombre_cliente, v.total, v.descuento, v.pagado, v.estado "
                    + "from ventas as v "
                    + "inner join tipo_documento as td on td.id = v.tipo_documento "
                    + "inner join clientes as c on c.codigo = v.cliente "
                    + "where (c.codigo = '" + c_cliente.getId_cliente() + "' or v.nro_placa IN (SELECT nro_placa from ventas inner join clientes on clientes.codigo = ventas.cliente where clientes.codigo = '" + c_cliente.getId_cliente() + "')) ";
            //+ "and v.pagado < (v.total - v.descuento) and v.estado = 0";
            c_cliente.ver_deuda_venta(t_detalle, query);
            txt_kardex_descripcion.setText(t_clientes.getValueAt(fila_seleccionada, 1).toString());
            sumar_deudas();
            jd_detalle.setVisible(true);
        } else {
            JOptionPane.showInternalMessageDialog(this, "NO HA SELECCIONADO UN CLIENTE");
        }
        btn_modificar.setEnabled(false);
        btn_eliminar.setEnabled(false);
        btn_deuda.setEnabled(false);
        fila_seleccionada = -1;
    }//GEN-LAST:event_btn_deudaActionPerformed

    private void t_clientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_t_clientesMouseClicked
        if (evt.getClickCount() == 2) {
            fila_seleccionada = t_clientes.getSelectedRow();
            btn_modificar.setEnabled(true);
            btn_eliminar.setEnabled(true);
            btn_deuda.setEnabled(true);
        }
    }//GEN-LAST:event_t_clientesMouseClicked

    private void txt_busKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_busKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txt_bus.getText().length() > 2) {
                String buscar = txt_bus.getText().trim();
                String query = "select c.id_cliente, c.id_zona, z.nombre as zona, c.documento, c.nombre, c.direccion, c.ventas, c.pagado "
                        + "from clientes as c "
                        + "inner join zona as z on z.id_zona = c.id_zona "
                        + "where c.nombre like '%" + buscar + "%' "
                        + "order by c.nombre asc";
                c_cliente.ver_clientes(t_clientes, query);
            }
        }
    }//GEN-LAST:event_txt_busKeyPressed

    private void txt_kardex_descripcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_kardex_descripcionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_kardex_descripcionActionPerformed

    private void t_detalleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_t_detalleMouseClicked
        fila_seleccionada = t_detalle.getSelectedRow();
        if (fila_seleccionada > -1) {
        }
    }//GEN-LAST:event_t_detalleMouseClicked

    private void cbx_zonasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbx_zonasMouseClicked

    }//GEN-LAST:event_cbx_zonasMouseClicked

    private void cbx_zonasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbx_zonasActionPerformed

    }//GEN-LAST:event_cbx_zonasActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        //ver clientes de esa zona
        cl_combobox c_combo = (cl_combobox) cbx_zonas.getSelectedItem();
        id_zona = c_combo.getId();
        String query = "select c.id_cliente, c.id_zona, z.nombre as zona, c.documento, c.nombre, c.direccion, c.ventas, c.pagado "
                + "from clientes as c "
                + "inner join zona as z on z.id_zona = c.id_zona "
                + "where id_zona = '" + id_zona + "' "
                + "order by c.nombre asc";
        c_cliente.ver_clientes(t_clientes, query);
        contar_resultados();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_deuda;
    private javax.swing.JButton btn_eliminar;
    private javax.swing.JButton btn_modificar;
    private javax.swing.JComboBox<String> cbx_zonas;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JDialog jd_detalle;
    private javax.swing.JTextField jd_txt_deuda;
    private javax.swing.JLabel lbl_contar;
    private javax.swing.JTable t_clientes;
    private javax.swing.JTable t_detalle;
    private javax.swing.JTextField txt_bus;
    private javax.swing.JTextField txt_kardex_descripcion;
    // End of variables declaration//GEN-END:variables
}
