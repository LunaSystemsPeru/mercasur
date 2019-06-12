/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import vistas.frm_ver_empleado;
import clases.cl_varios;
import clases.cl_empleados;
import java.awt.event.KeyEvent;
public class frm_reg_empleado extends javax.swing.JInternalFrame {

    cl_varios c_varios = new cl_varios();
    cl_empleados c_empleados = new cl_empleados();
    public static String accion;
    public static int id_empleados;
    

    public frm_reg_empleado() {
        initComponents();
        txt_nombre.setEnabled(false);
        txt_ape_pat.setEnabled(false);
        txt_ape_mat.setEnabled(false);
        txt_direccion.setEnabled(false);
        txt_correo.setEnabled(false);
        txt_telefono.setEnabled(false);
        txt_fecha_nacimiento.setEnabled(false);
        txt_nick.setEnabled(false);
        txt_contraseña.setEnabled(false);
        btn_registrar.setEnabled(false);
        if (accion.equals("modificar")) {
            c_empleados.setId_empleados(id_empleados);
            c_empleados.cargar_datos();
            txt_codigo.setText(String.valueOf(c_empleados.getId_empleados()));
            txt_nombre.setText(c_empleados.getNombres());
            txt_dni.setText(c_empleados.getDni());
            txt_ape_pat.setText(c_empleados.getApe_pat());
            txt_ape_mat.setText(c_empleados.getApe_mat());
            txt_direccion.setText(c_empleados.getDireccion());
            txt_correo.setText(c_empleados.getCorreo());
            txt_telefono.setText(c_empleados.getTelefono());
            txt_fecha_nacimiento.setText(c_empleados.getFecha_nacimiento());
            txt_nick.setText(c_empleados.getNick());
            txt_contraseña.setText(c_empleados.getContraseña());
            jButton3.setEnabled(true);
            btn_registrar.setEnabled(false);
            
        }

    }

    private void llenar() {
        if (accion.equals("registrar")) {
            txt_codigo.setText(String.valueOf(c_empleados.obtener_codigo()));
        } 
        if (accion.equals("modificar")) {
            c_empleados.setId_empleados(Integer.parseInt(txt_codigo.getText()));
        } 
        c_empleados.setDni(txt_dni.getText());
        c_empleados.setNombres(txt_nombre.getText());
        c_empleados.setApe_pat(txt_ape_pat.getText());
        c_empleados.setApe_mat(txt_ape_mat.getText());
        c_empleados.setDireccion(txt_direccion.getText());
        c_empleados.setCorreo(txt_correo.getText());
        c_empleados.setTelefono(txt_telefono.getText());
        
        String fecha_nacimento = txt_fecha_nacimiento.getText();
        String nueva_fecha = c_varios.formato_fecha_mysql(fecha_nacimento);
        c_empleados.setFecha_nacimiento(nueva_fecha);
        
        c_empleados.setNick(txt_nick.getText());
        c_empleados.setContraseña(txt_contraseña.getText());

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btn_cer = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txt_codigo = new javax.swing.JTextField();
        txt_nombre = new javax.swing.JTextField();
        txt_ape_pat = new javax.swing.JTextField();
        txt_ape_mat = new javax.swing.JTextField();
        txt_direccion = new javax.swing.JTextField();
        txt_correo = new javax.swing.JTextField();
        txt_telefono = new javax.swing.JTextField();
        txt_nick = new javax.swing.JTextField();
        txt_contraseña = new javax.swing.JTextField();
        txt_dni = new javax.swing.JTextField();
        btn_cer1 = new javax.swing.JButton();
        btn_registrar = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        txt_fecha_nacimiento = new javax.swing.JFormattedTextField();

        btn_cer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/cancel.png"))); // NOI18N
        btn_cer.setText("Cerrar");
        btn_cer.setFocusable(false);
        btn_cer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cerActionPerformed(evt);
            }
        });

        jLabel13.setText("jLabel13");

        jLabel1.setText("codigo");

        jLabel2.setText("DNI");

        jLabel3.setText("Nombres");

        jLabel4.setText("Ape. Paterno");

        jLabel5.setText("Ape. Materno");

        jLabel6.setText("Direccion");

        jLabel7.setText("Email");

        jLabel8.setText("Telefono");

        jLabel9.setText("Usuario");

        jLabel10.setText("contraseña");

        txt_codigo.setEnabled(false);

        txt_nombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_nombreKeyPressed(evt);
            }
        });

        txt_ape_pat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_ape_patKeyPressed(evt);
            }
        });

        txt_ape_mat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_ape_matActionPerformed(evt);
            }
        });
        txt_ape_mat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_ape_matKeyPressed(evt);
            }
        });

        txt_direccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_direccionKeyPressed(evt);
            }
        });

        txt_correo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_correoKeyPressed(evt);
            }
        });

        txt_telefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_telefonoKeyPressed(evt);
            }
        });

        txt_nick.setEnabled(false);

        txt_contraseña.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_contraseñaKeyPressed(evt);
            }
        });

        txt_dni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_dniActionPerformed(evt);
            }
        });
        txt_dni.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_dniKeyPressed(evt);
            }
        });

        btn_cer1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/cancel.png"))); // NOI18N
        btn_cer1.setText("Cerrar");
        btn_cer1.setFocusable(false);
        btn_cer1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cer1ActionPerformed(evt);
            }
        });

        btn_registrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/add.png"))); // NOI18N
        btn_registrar.setText("Registrar");
        btn_registrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_registrarActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/application_edit.png"))); // NOI18N
        jButton3.setText("Modificar");
        jButton3.setEnabled(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel12.setText("Fec. Nacimiento");

        try {
            txt_fecha_nacimiento.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txt_fecha_nacimiento.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_fecha_nacimiento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_fecha_nacimientoKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel12)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel1)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txt_nombre)
                                            .addComponent(txt_ape_pat)
                                            .addComponent(txt_ape_mat)
                                            .addComponent(txt_nick)
                                            .addComponent(txt_contraseña)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                    .addComponent(txt_fecha_nacimiento, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                                    .addComponent(txt_dni, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txt_telefono, javax.swing.GroupLayout.Alignment.LEADING))
                                                .addGap(0, 41, Short.MAX_VALUE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txt_codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addComponent(btn_cer1))
                            .addComponent(txt_direccion)
                            .addComponent(txt_correo)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btn_registrar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_cer1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_dni, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_ape_pat, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_ape_mat, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_direccion, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_correo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_fecha_nacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_nick, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_contraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_registrar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_ape_matActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_ape_matActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_ape_matActionPerformed

    private void btn_cerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cerActionPerformed
        this.dispose();
    }//GEN-LAST:event_btn_cerActionPerformed

    private void btn_cer1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cer1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_btn_cer1ActionPerformed

    private void btn_registrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_registrarActionPerformed
        llenar();
        c_empleados.obtener_codigo();
        c_empleados.insertar();
        frm_ver_empleado frm_empleados = new frm_ver_empleado();
        c_varios.llamar_ventana(frm_empleados);
        this.dispose();

    }//GEN-LAST:event_btn_registrarActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        llenar();
        c_empleados.modificar();
        frm_ver_empleado frm_empleados = new frm_ver_empleado();
        c_varios.llamar_ventana(frm_empleados);
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txt_fecha_nacimientoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_fecha_nacimientoKeyPressed
        // TODO add your handling code here:
        String fecha = txt_nombre.getText().charAt(0) + txt_ape_pat.getText().replace(" ","") + txt_ape_mat.getText().charAt(0);      
        //System.out.println(n);
        txt_nick.setText(fecha);
        if (evt.getKeyChar() == KeyEvent.VK_ENTER)
             this.txt_contraseña.requestFocus();
             txt_contraseña.setEnabled(true);
        
    }//GEN-LAST:event_txt_fecha_nacimientoKeyPressed

    private void txt_dniKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dniKeyPressed

          if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
             this.txt_nombre.requestFocus();
             txt_nombre.setEnabled(true);

        }
        
    }//GEN-LAST:event_txt_dniKeyPressed

    private void txt_dniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_dniActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_dniActionPerformed

    private void txt_nombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nombreKeyPressed
        
        if (evt.getKeyCode() == KeyEvent.VK_ENTER){
             this.txt_ape_pat.requestFocus();
             txt_ape_pat.setEnabled(true);
        }
    }//GEN-LAST:event_txt_nombreKeyPressed

    private void txt_ape_patKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_ape_patKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER){
             this.txt_ape_mat.requestFocus();
             txt_ape_mat.setEnabled(true);
        }
    }//GEN-LAST:event_txt_ape_patKeyPressed

    private void txt_ape_matKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_ape_matKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER){
             this.txt_direccion.requestFocus();
             txt_direccion.setEnabled(true);
        }
    }//GEN-LAST:event_txt_ape_matKeyPressed

    private void txt_direccionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_direccionKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER){
             this.txt_correo.requestFocus();
             txt_correo.setEnabled(true);
        }
    }//GEN-LAST:event_txt_direccionKeyPressed

    private void txt_correoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_correoKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER){
             this.txt_telefono.requestFocus();
             txt_telefono.setEnabled(true);
        }
    }//GEN-LAST:event_txt_correoKeyPressed

    private void txt_telefonoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_telefonoKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER){
             this.txt_fecha_nacimiento.requestFocus();
             txt_fecha_nacimiento.setEnabled(true);
        }
    }//GEN-LAST:event_txt_telefonoKeyPressed

    private void txt_contraseñaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_contraseñaKeyPressed
        
        if (evt.getKeyCode() == KeyEvent.VK_ENTER){
            if(txt_codigo.getText().length()>0){
                 jButton3.setEnabled(true);
                 btn_registrar.setEnabled(false);
                 jButton3.requestFocus();
            }
            else{
                
                jButton3.setEnabled(false);
                btn_registrar.setEnabled(true);
                btn_registrar.requestFocus();
                
               
            }
        }
    }//GEN-LAST:event_txt_contraseñaKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_cer;
    private javax.swing.JButton btn_cer1;
    private javax.swing.JButton btn_registrar;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField txt_ape_mat;
    private javax.swing.JTextField txt_ape_pat;
    private javax.swing.JTextField txt_codigo;
    private javax.swing.JTextField txt_contraseña;
    private javax.swing.JTextField txt_correo;
    private javax.swing.JTextField txt_direccion;
    private javax.swing.JTextField txt_dni;
    private javax.swing.JFormattedTextField txt_fecha_nacimiento;
    private javax.swing.JTextField txt_nick;
    private javax.swing.JTextField txt_nombre;
    private javax.swing.JTextField txt_telefono;
    // End of variables declaration//GEN-END:variables
}
