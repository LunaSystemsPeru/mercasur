/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistas;

import clases.cl_marca;
import clases.cl_varios;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author CALIDAD
 */
public class frm_ver_marcas extends javax.swing.JInternalFrame {

    cl_marca c_marca = new cl_marca();
    cl_varios c_varios = new cl_varios();

    String query;
    int fila;
    
    public static String accion;

    /**
     * Creates new form frm_ver_marcas
     */
    public frm_ver_marcas() {
        initComponents();
        
        query = "select * "
                + "from marcas "
                + "order by nombre asc ";
        c_marca.ver_marcas(t_marcas, query);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jd_formulario = new javax.swing.JDialog();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_frm_id = new javax.swing.JTextField();
        txt_frm_nombre = new javax.swing.JTextField();
        jToolBar1 = new javax.swing.JToolBar();
        btn_frm_grabar = new javax.swing.JButton();
        btn_frm_salir = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txt_comision = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        t_marcas = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        btn_modificar = new javax.swing.JButton();

        jLabel1.setText("Id:");

        jLabel2.setText("Nombre:");

        txt_frm_id.setEnabled(false);

        txt_frm_nombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_frm_nombreKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_frm_nombreKeyPressed(evt);
            }
        });

        jToolBar1.setFloatable(false);

        btn_frm_grabar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/add.png"))); // NOI18N
        btn_frm_grabar.setText("Grabar");
        btn_frm_grabar.setEnabled(false);
        btn_frm_grabar.setFocusable(false);
        btn_frm_grabar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_frm_grabar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_frm_grabar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_frm_grabarActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_frm_grabar);

        btn_frm_salir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/cancel.png"))); // NOI18N
        btn_frm_salir.setText("Salir");
        btn_frm_salir.setFocusable(false);
        btn_frm_salir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_frm_salir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_frm_salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_frm_salirActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_frm_salir);

        jLabel3.setText("Marca:");

        txt_comision.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_comisionKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jd_formularioLayout = new javax.swing.GroupLayout(jd_formulario.getContentPane());
        jd_formulario.getContentPane().setLayout(jd_formularioLayout);
        jd_formularioLayout.setHorizontalGroup(
            jd_formularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jd_formularioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jd_formularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jd_formularioLayout.createSequentialGroup()
                        .addGroup(jd_formularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGroup(jd_formularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jd_formularioLayout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(txt_frm_id, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(244, Short.MAX_VALUE))
                            .addGroup(jd_formularioLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jd_formularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_frm_nombre)
                                    .addGroup(jd_formularioLayout.createSequentialGroup()
                                        .addComponent(txt_comision, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())))
                    .addGroup(jd_formularioLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jd_formularioLayout.setVerticalGroup(
            jd_formularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jd_formularioLayout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jd_formularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_frm_id, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jd_formularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_frm_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jd_formularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_comision, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setTitle("Listar Marcas de Productos");

        t_marcas.setModel(new javax.swing.table.DefaultTableModel(
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
        t_marcas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                t_marcasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(t_marcas);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/add.png"))); // NOI18N
        jButton1.setText("Agregar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/cancel.png"))); // NOI18N
        jButton2.setText("Cerrar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        btn_modificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/application_edit.png"))); // NOI18N
        btn_modificar.setText("Modificar");
        btn_modificar.setEnabled(false);
        btn_modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_modificarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_modificar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_modificar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txt_frm_nombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_frm_nombreKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txt_frm_nombre.getText().length() > 0) {
                txt_comision.setEnabled(true);
                txt_comision.requestFocus();
            } else {
                JOptionPane.showMessageDialog(null, "ingrese datos");
            }
        }
    }//GEN-LAST:event_txt_frm_nombreKeyPressed

    private void btn_frm_grabarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_frm_grabarActionPerformed
        c_marca.setNombre(txt_frm_nombre.getText());
        c_marca.setComision(Double.parseDouble(txt_comision.getText()));
        c_marca.obtener_codigo();

        if (c_marca.insertar()) {
            c_marca.ver_marcas(t_marcas, query);
            jd_formulario.dispose();
        }
    }//GEN-LAST:event_btn_frm_grabarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        jd_formulario.setSize(400, 217); // establecer tamaño
        jd_formulario.setModal(true);
        jd_formulario.setLocationRelativeTo(null); //para centrar en el menu
        jd_formulario.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txt_frm_nombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_frm_nombreKeyTyped
        c_varios.limitar_caracteres(evt, txt_frm_nombre, 8);
    }//GEN-LAST:event_txt_frm_nombreKeyTyped

    private void t_marcasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_t_marcasMouseClicked
        fila = t_marcas.getSelectedRow();
        btn_modificar.setEnabled(true);
    }//GEN-LAST:event_t_marcasMouseClicked

    private void btn_modificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modificarActionPerformed
        c_marca.setId(Integer.parseInt(t_marcas.getValueAt(fila, 0).toString()));
        
    }//GEN-LAST:event_btn_modificarActionPerformed

    private void btn_frm_salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_frm_salirActionPerformed
            jd_formulario.dispose();
    }//GEN-LAST:event_btn_frm_salirActionPerformed

    private void txt_comisionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_comisionKeyPressed
         if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txt_comision.getText().length() > 0) {
                String comision = txt_comision.getText();
                if (c_varios.esDecimal(comision)) {
                btn_frm_grabar.setEnabled(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Ingrese un numero correcto");
                    txt_comision.selectAll();
                }
            } else {
                JOptionPane.showMessageDialog(null, "ingrese datos");
            }
        }
    }//GEN-LAST:event_txt_comisionKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_frm_grabar;
    private javax.swing.JButton btn_frm_salir;
    private javax.swing.JButton btn_modificar;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JDialog jd_formulario;
    private javax.swing.JTable t_marcas;
    private javax.swing.JTextField txt_comision;
    private javax.swing.JTextField txt_frm_id;
    private javax.swing.JTextField txt_frm_nombre;
    // End of variables declaration//GEN-END:variables
}
