/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistas;

import clases.cl_producto_unidad;
import clases.cl_productos;
import clases.cl_varios;
import forms.frm_reg_producto;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author pc
 */
public class frm_ver_productos extends javax.swing.JInternalFrame {

    cl_producto_unidad u_producto = new cl_producto_unidad();
    cl_productos c_productos = new cl_productos();
    cl_varios c_varios = new cl_varios();

    int i;
    DefaultTableModel mostrar;
    String query_productos;
    String query_unidades;

    boolean registrar = true;
    int unidad_seleccionada;

    /**
     * Creates new form frm_ver_productos
     */
    public frm_ver_productos() {
        initComponents();
        query_productos = "select p.id_producto, p.descripcion, p.precio_venta, p.cant_actual, p.costo_compra, m.nombre as marca, m.comision, p.ultima_salida, "
                + "p.ultimo_ingreso, p.estado "
                + "from productos as p "
                + "inner join marcas as m on m.id_marca = p.id_marca";
        c_productos.ver_productos(t_productos, query_productos);
        lbl_encontrados.setText(t_productos.getRowCount() + "");
    }

    private void llenar() {

        u_producto.setNombre(txt_nombre.getText());
        u_producto.setFactor(Double.parseDouble(txt_factor.getText()));
        u_producto.setPrecio(Double.parseDouble(txt_precio.getText()));

    }

    private void activar_botones() {
        btn_eli.setEnabled(true);
        btn_kardex.setEnabled(true);
        btn_kardex_pdf.setEnabled(true);
        btn_mod.setEnabled(true);
        btn_unidad.setEnabled(true);
    }

    private void desactivar_botones() {
        btn_eli.setEnabled(false);
        btn_kardex.setEnabled(false);
        btn_kardex_pdf.setEnabled(false);
        btn_mod.setEnabled(false);
        btn_unidad.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jd_kardex = new javax.swing.JDialog();
        jScrollPane2 = new javax.swing.JScrollPane();
        t_kardex = new javax.swing.JTable();
        txt_kardex_descripcion = new javax.swing.JTextField();
        jd_unidad = new javax.swing.JDialog();
        jLabel3 = new javax.swing.JLabel();
        txt_nombre = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_factor = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        t_unidades = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        txt_precio = new javax.swing.JTextField();
        jToolBar2 = new javax.swing.JToolBar();
        btn_guardar = new javax.swing.JButton();
        btn_modificar_unidad = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        btn_eliminar_unidad = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        btn_cerrar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txt_bus = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        t_productos = new javax.swing.JTable();
        cbx_est = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        lbl_encontrados = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        btn_mod = new javax.swing.JButton();
        btn_unidad = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        btn_kardex = new javax.swing.JButton();
        btn_kardex_pdf = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btn_eli = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jButton3 = new javax.swing.JButton();

        jd_kardex.setTitle("Ver Kardex del Producto");

        t_kardex.setModel(new javax.swing.table.DefaultTableModel(
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
        t_kardex.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane2.setViewportView(t_kardex);

        txt_kardex_descripcion.setFocusable(false);

        javax.swing.GroupLayout jd_kardexLayout = new javax.swing.GroupLayout(jd_kardex.getContentPane());
        jd_kardex.getContentPane().setLayout(jd_kardexLayout);
        jd_kardexLayout.setHorizontalGroup(
            jd_kardexLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jd_kardexLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jd_kardexLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 928, Short.MAX_VALUE)
                    .addComponent(txt_kardex_descripcion))
                .addContainerGap())
        );
        jd_kardexLayout.setVerticalGroup(
            jd_kardexLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jd_kardexLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(txt_kardex_descripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
                .addContainerGap())
        );

        jd_unidad.setTitle("Presentaciones por Producto");

        jLabel3.setText("Descripcion");

        txt_nombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_nombreKeyPressed(evt);
            }
        });

        jLabel4.setText("Factor");

        txt_factor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_factorKeyPressed(evt);
            }
        });

        t_unidades.setModel(new javax.swing.table.DefaultTableModel(
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
        t_unidades.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                t_unidadesMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(t_unidades);

        jLabel6.setText("Precio:");

        txt_precio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_precioKeyPressed(evt);
            }
        });

        jToolBar2.setFloatable(false);

        btn_guardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/add.png"))); // NOI18N
        btn_guardar.setText("Grabar");
        btn_guardar.setEnabled(false);
        btn_guardar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_guardar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_guardarActionPerformed(evt);
            }
        });
        jToolBar2.add(btn_guardar);

        btn_modificar_unidad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/application_edit.png"))); // NOI18N
        btn_modificar_unidad.setText("Modificar");
        btn_modificar_unidad.setEnabled(false);
        btn_modificar_unidad.setFocusable(false);
        btn_modificar_unidad.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_modificar_unidad.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_modificar_unidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_modificar_unidadActionPerformed(evt);
            }
        });
        jToolBar2.add(btn_modificar_unidad);
        jToolBar2.add(jSeparator4);

        btn_eliminar_unidad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/bin_closed.png"))); // NOI18N
        btn_eliminar_unidad.setText("Eliminar");
        btn_eliminar_unidad.setEnabled(false);
        btn_eliminar_unidad.setFocusable(false);
        btn_eliminar_unidad.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_eliminar_unidad.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_eliminar_unidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminar_unidadActionPerformed(evt);
            }
        });
        jToolBar2.add(btn_eliminar_unidad);
        jToolBar2.add(jSeparator5);

        btn_cerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/cancel.png"))); // NOI18N
        btn_cerrar.setText("Salir");
        btn_cerrar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_cerrar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_cerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cerrarActionPerformed(evt);
            }
        });
        jToolBar2.add(btn_cerrar);

        javax.swing.GroupLayout jd_unidadLayout = new javax.swing.GroupLayout(jd_unidad.getContentPane());
        jd_unidad.getContentPane().setLayout(jd_unidadLayout);
        jd_unidadLayout.setHorizontalGroup(
            jd_unidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jd_unidadLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jd_unidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jd_unidadLayout.createSequentialGroup()
                        .addGroup(jd_unidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6))
                        .addGap(4, 4, 4)
                        .addGroup(jd_unidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_precio, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_factor, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                            .addComponent(txt_nombre))
                        .addGap(126, 126, 126))
                    .addGroup(jd_unidadLayout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jd_unidadLayout.setVerticalGroup(
            jd_unidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jd_unidadLayout.createSequentialGroup()
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jd_unidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jd_unidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_factor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jd_unidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_precio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                .addContainerGap())
        );

        setTitle("Ver Productos");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/find.png"))); // NOI18N
        jLabel1.setText("Buscar:");

        txt_bus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_busKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_busKeyReleased(evt);
            }
        });

        jScrollPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPane1MouseClicked(evt);
            }
        });

        t_productos.setModel(new javax.swing.table.DefaultTableModel(
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
        t_productos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        t_productos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                t_productosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(t_productos);

        cbx_est.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "DISPONIBLE", "POR TERMINAR", "NO DISPONIBLE", "NO ACTIVO" }));
        cbx_est.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbx_estActionPerformed(evt);
            }
        });

        jLabel2.setText("Encontrados:");

        lbl_encontrados.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_encontrados.setText("0");

        jToolBar1.setFloatable(false);

        btn_mod.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/application_edit.png"))); // NOI18N
        btn_mod.setText("Modifcar");
        btn_mod.setEnabled(false);
        btn_mod.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_mod.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_mod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_modActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_mod);

        btn_unidad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/find.png"))); // NOI18N
        btn_unidad.setText("Presentaciones");
        btn_unidad.setEnabled(false);
        btn_unidad.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_unidad.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_unidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_unidadActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_unidad);
        jToolBar1.add(jSeparator3);

        btn_kardex.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/clipboard_text.png"))); // NOI18N
        btn_kardex.setText("Kardex");
        btn_kardex.setEnabled(false);
        btn_kardex.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_kardex.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_kardex.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_kardexActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_kardex);

        btn_kardex_pdf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/clipboard_text.png"))); // NOI18N
        btn_kardex_pdf.setText("Kardex en PDF");
        btn_kardex_pdf.setEnabled(false);
        btn_kardex_pdf.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_kardex_pdf.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_kardex_pdf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_kardex_pdfActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_kardex_pdf);
        jToolBar1.add(jSeparator1);

        btn_eli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/bin_closed.png"))); // NOI18N
        btn_eli.setText("Eliminar");
        btn_eli.setEnabled(false);
        btn_eli.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_eli.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_eli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_eli);
        jToolBar1.add(jSeparator2);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/cancel.png"))); // NOI18N
        jButton3.setText("Cerrar");
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_bus, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_encontrados, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 172, Short.MAX_VALUE)
                        .addComponent(cbx_est, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_bus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbx_est, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(lbl_encontrados))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txt_busKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_busKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txt_bus.getText().length() > 0) {
                String busqueda = txt_bus.getText();
                query_productos = "select p.id_producto, p.descripcion, p.precio_venta, p.cant_actual, p.costo_compra, m.nombre as marca, m.comision, p.ultima_salida, "
                        + "p.ultimo_ingreso, p.estado "
                        + "from productos as p "
                        + "inner join marcas as m on m.id_marca = p.id_marca "
                        + "where p.descripcion like '%" + busqueda + "%'";
                c_productos.ver_productos(t_productos, query_productos);
                lbl_encontrados.setText(t_productos.getRowCount() + "");
            }
        }
    }//GEN-LAST:event_txt_busKeyPressed

    private void jScrollPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane1MouseClicked

    }//GEN-LAST:event_jScrollPane1MouseClicked

    private void btn_modActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modActionPerformed
        if (i > -1) {
            desactivar_botones();
            Frame f = JOptionPane.getRootFrame();
            frm_reg_producto.c_producto.setId_producto(Integer.parseInt(t_productos.getValueAt(i, 0).toString()));
            frm_reg_producto dialog = new frm_reg_producto(f, true);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "NO HA SELECCIONADO UNA FILA");
        }
    }//GEN-LAST:event_btn_modActionPerformed

    private void btn_eliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliActionPerformed
        desactivar_botones();
    }//GEN-LAST:event_btn_eliActionPerformed

    private void cbx_estActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbx_estActionPerformed

    }//GEN-LAST:event_cbx_estActionPerformed

    private void t_productosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_t_productosMouseClicked
        if (evt.getClickCount() == 2) {
            i = t_productos.getSelectedRow();
            activar_botones();
        }
    }//GEN-LAST:event_t_productosMouseClicked

    private void txt_busKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_busKeyReleased

    }//GEN-LAST:event_txt_busKeyReleased

    private void btn_kardexActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_kardexActionPerformed
        if (i > -1) {
            desactivar_botones();
            jd_kardex.setModal(true);
            jd_kardex.setSize(900, 380);
            jd_kardex.setLocationRelativeTo(null);
            c_productos.setId_producto(Integer.parseInt(t_productos.getValueAt(i, 0).toString()));
            txt_kardex_descripcion.setText(t_productos.getValueAt(i, 1).toString());
            c_productos.ver_kardex(t_kardex);
            jd_kardex.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "NO HA SELECCIONADO UNA FILA");
        }
    }//GEN-LAST:event_btn_kardexActionPerformed

    private void btn_kardex_pdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_kardex_pdfActionPerformed
        desactivar_botones();
        if (i > -1) {
            c_productos.setId_producto(Integer.parseInt(t_productos.getValueAt(i, 0).toString()));
            File miDir = new File(".");
            try {
                System.out.println("Directorio actual: " + miDir.getCanonicalPath());
                Map<String, Object> parametros = new HashMap<>();
                String id_productos = c_productos.getId_producto() + "";
                String path = miDir.getCanonicalPath();
                String separator = File.separator;
                String direccion = path + separator + "reports" + separator + "subreports" + separator;
                //String direccion = path + "\\reports\\subreports\\";
                System.out.println(direccion);
                //    parametros.put("SUBREPORT_DIR", direccion);
                parametros.put("input_producto", id_productos);
                c_varios.ver_reporte("rpt_kardex", parametros);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
            }
        } else {
            JOptionPane.showInternalMessageDialog(this, "no ha seleccionado un vehiculo");
        }
    }//GEN-LAST:event_btn_kardex_pdfActionPerformed

    private void btn_unidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_unidadActionPerformed
        desactivar_botones();
        jd_unidad.setModal(true);
        jd_unidad.setSize(433, 506);
        jd_unidad.setLocationRelativeTo(null);
        int id_producto = Integer.parseInt(t_productos.getValueAt(i, 0).toString());
        u_producto.setId_producto(id_producto);
        query_unidades = "select id_unidad, nombre, factor, precio "
                + "from producto_unidades "
                + " where id_producto = '" + id_producto + "' ";
        u_producto.ver_unidades_producto(t_unidades, query_unidades);
        jd_unidad.setVisible(true);
    }//GEN-LAST:event_btn_unidadActionPerformed

    private void limpiar() {
        registrar = true;
        txt_nombre.setText("");
        txt_factor.setText("");
        txt_precio.setText("");
        txt_factor.setEnabled(false);
        txt_precio.setEnabled(false);
        txt_nombre.requestFocus();
    }

    private void btn_guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_guardarActionPerformed
        btn_guardar.setEnabled(false);
        llenar();
        if (registrar) {
            u_producto.obtener_id();
            u_producto.insertar();
        } else {
            u_producto.modificar();
            t_unidades.setEnabled(true);
        }

        u_producto.ver_unidades_producto(t_unidades, query_unidades);
        limpiar();

    }//GEN-LAST:event_btn_guardarActionPerformed

    private void btn_cerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cerrarActionPerformed
        limpiar();
        t_unidades.setEnabled(true);
        jd_unidad.dispose();
    }//GEN-LAST:event_btn_cerrarActionPerformed

    private void txt_nombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nombreKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txt_nombre.getText().length() > 0) {
                txt_factor.setEnabled(true);
                txt_factor.requestFocus();
            }
        }
    }//GEN-LAST:event_txt_nombreKeyPressed

    private void txt_factorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_factorKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String factor = txt_factor.getText();
            if (c_varios.esDecimal(factor)) {
                txt_precio.setEnabled(true);
                txt_precio.requestFocus();
            }
        }
    }//GEN-LAST:event_txt_factorKeyPressed

    private void txt_precioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_precioKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String precio = txt_precio.getText();
            if (c_varios.esDecimal(precio)) {
                btn_guardar.setEnabled(true);
                btn_guardar.requestFocus();
            }
        }
    }//GEN-LAST:event_txt_precioKeyPressed

    private void t_unidadesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_t_unidadesMouseClicked
        unidad_seleccionada = t_unidades.getSelectedRow();
        btn_modificar_unidad.setEnabled(true);
        btn_eliminar_unidad.setEnabled(true);
    }//GEN-LAST:event_t_unidadesMouseClicked

    private void btn_modificar_unidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modificar_unidadActionPerformed
        int id_unidad = Integer.parseInt(t_unidades.getValueAt(unidad_seleccionada, 0).toString());
        u_producto.setId_unidad(id_unidad);
        u_producto.obtener_datos();

        txt_nombre.setText(u_producto.getNombre());
        txt_factor.setText(c_varios.formato_numero(u_producto.getFactor()));
        txt_precio.setText(c_varios.formato_numero(u_producto.getPrecio()));
        txt_nombre.setEnabled(true);
        txt_factor.setEnabled(true);
        txt_precio.setEnabled(true);
        registrar = false;
        btn_guardar.setEnabled(true);
        btn_modificar_unidad.setEnabled(false);
        btn_eliminar_unidad.setEnabled(false);
        t_unidades.setEnabled(false);
    }//GEN-LAST:event_btn_modificar_unidadActionPerformed

    private void btn_eliminar_unidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminar_unidadActionPerformed
        int dialogButton = JOptionPane.showConfirmDialog(null, "Desea eliminar el registro seleccionado??", "Atencion", JOptionPane.YES_NO_OPTION);
        if (dialogButton == JOptionPane.YES_OPTION) { //The ISSUE is here
            int id_unidad = Integer.parseInt(t_unidades.getValueAt(unidad_seleccionada, 0).toString());
            u_producto.setId_unidad(id_unidad);
            u_producto.eliminar();
            u_producto.ver_unidades_producto(t_unidades, query_unidades);
            limpiar();
            t_unidades.setEnabled(true);
        }
    }//GEN-LAST:event_btn_eliminar_unidadActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_cerrar;
    private javax.swing.JButton btn_eli;
    private javax.swing.JButton btn_eliminar_unidad;
    private javax.swing.JButton btn_guardar;
    private javax.swing.JButton btn_kardex;
    private javax.swing.JButton btn_kardex_pdf;
    private javax.swing.JButton btn_mod;
    private javax.swing.JButton btn_modificar_unidad;
    private javax.swing.JButton btn_unidad;
    private javax.swing.JComboBox cbx_est;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JDialog jd_kardex;
    private javax.swing.JDialog jd_unidad;
    private javax.swing.JLabel lbl_encontrados;
    private javax.swing.JTable t_kardex;
    private javax.swing.JTable t_productos;
    private javax.swing.JTable t_unidades;
    private javax.swing.JTextField txt_bus;
    private javax.swing.JTextField txt_factor;
    private javax.swing.JTextField txt_kardex_descripcion;
    private javax.swing.JTextField txt_nombre;
    private javax.swing.JTextField txt_precio;
    // End of variables declaration//GEN-END:variables
}
