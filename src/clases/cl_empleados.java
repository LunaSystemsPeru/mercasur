
package clases;
import java.sql.*;
import clases.cl_conectar;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JOptionPane;
public class cl_empleados {
  cl_conectar c_conectar=new cl_conectar();
  private int id_empleados;
  private String dni;
  private String nombres;
  private String ape_pat;
  private String ape_mat;
  private String direccion;
  private String correo;
  private String telefono;
  private String fecha_nacimiento;
  private String nick;
  private  String contraseña;
  private String estado;
  private Statement st;
  private ResultSet rs;
 
    public int getId_empleados() {
        return id_empleados;
    }

    /**
     * @param id_empleados the id_empleados to set
     */
    public void setId_empleados(int id_empleados) {
        this.id_empleados = id_empleados;
    }

    /**
     * @return the dni
     */
    public String getDni() {
        return dni;
    }

    /**
     * @param dni the dni to set
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * @return the nombres
     */
    public String getNombres() {
        return nombres;
    }

    /**
     * @param nombres the nombres to set
     */
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    /**
     * @return the ape_pat
     */
    public String getApe_pat() {
        return ape_pat;
    }

    /**
     * @param ape_pat the ape_pat to set
     */
    public void setApe_pat(String ape_pat) {
        this.ape_pat = ape_pat;
    }

    /**
     * @return the ape_mat
     */
    public String getApe_mat() {
        return ape_mat;
    }

    /**
     * @param ape_mat the ape_mat to set
     */
    public void setApe_mat(String ape_mat) {
        this.ape_mat = ape_mat;
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @param correo the correo to set
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the fecha_nacimiento
     */
    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    /**
     * @param fecha_nacimiento the fecha_nacimiento to set
     */
    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    /**
     * @return the nick
     */
    public String getNick() {
        return nick;
    }

    /**
     * @param nick the nick to set
     */
    public void setNick(String nick) {
        this.nick = nick;
    }

    /**
     * @return the contraseña
     */
    public String getContraseña() {
        return contraseña;
    }

    /**
     * @param contraseña the contraseña to set
     */
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }
     public void ver_zonas(JTable tabla, String query) {
        try {
            DefaultTableModel mostrar = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int fila, int columna) {
                    return false;
                }
            };
           st = c_conectar.conexion();
            rs = c_conectar.consulta(st, query);

            RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(mostrar);
            tabla.setRowSorter(sorter);

            mostrar.addColumn("Codigo");
            mostrar.addColumn("datos del empleado");

            while (rs.next()) {
                Object fila[] = new Object[2];

                fila[0] = rs.getInt("id_empleado");
                fila[1] = rs.getString("nombres")+ " " +rs.getString("ape_pat") + " "+rs.getString("ape_mat").trim();
               
                mostrar.addRow(fila);
            }

            c_conectar.cerrar(st);
            c_conectar.cerrar(rs);
            tabla.setModel(mostrar);
            tabla.getColumnModel().getColumn(0).setPreferredWidth(50);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(100);
            
        } catch (SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, ex);
        }
    }
  public boolean insertar(){
      boolean grabar=false;
      try {
          st=c_conectar.conexion();
       String squly="insert into empleados values('"+id_empleados+"','"+dni+"','"+nombres+"','"+ape_pat+"','"+ape_mat+"',"
               + "'"+direccion+"','"+correo+"','"+telefono+"','"+fecha_nacimiento+"','"+nick+"','"+contraseña+"','"+estado+"')";
       int respuesta=c_conectar.actualiza(st,squly);
       if(respuesta>-1){
           grabar=true;
       }
          
      } catch (Exception e) {
          JOptionPane.showMessageDialog(null,"no grabo por falta de coneccion");
      }
       return grabar;
  }
  
  public int obtener_codigo(){
      int codigo=0;
      try {
          st=c_conectar.conexion();
          String sql="select ifnull(max(id_empleado)+1, 1) as id_empleado from empleados";
        rs=c_conectar.consulta(st, sql);
         if(rs.next()){
             id_empleados=rs.getInt("id_empleado");
             codigo=id_empleados;
             c_conectar.cerrar(st);
             c_conectar.cerrar(rs);
         }
      } catch (Exception e) {
      }
      return codigo;
  }
 public boolean modificar(){
     boolean modificar=false;
     try {
         st=c_conectar.conexion();
         String Sql="update empleados set dni='"+dni+"',nombres='"+nombres+"',ape_pat='"+ape_pat+"',ape_mat='"+ape_mat+"',"
                 + "direccion='"+direccion+"',correo='"+correo+"',telefono='"+telefono+"',fecha_nacimiento='"+fecha_nacimiento+"',"
                         + " nick='"+nick+"',contrasena='"+contraseña+"',estado='"+estado+"'";
          int respuesta =c_conectar.actualiza(st,Sql);
          if(respuesta>-1){
              modificar=true;
          }
     } catch (Exception e) {
     }
     return modificar;
 }
/* public boolean bolver_datos(){
     boolean consulta=false;
     try {
         st=c_conectar.conexion();
         String sql="select * from empleados where id_empleados='"+id_empleados+"'";
     rs=c_conectar.consulta(st,sql);
         if(rs.next()){
              id_empleados=rs.getInt("id_empleado");
              dni=rs.getString("dni");
              nombres=rs.getString("nombres");
              ape_pat=rs.getString("ape_pat");
              ape_mat=rs.getString("ape_mat");
              direccion=rs.getString("direccion");
              correo=rs.getString("correo");
              telefono=rs.getString("telefono");
              fecha_nacimiento=rs.getString("fecha_nacimiento");
              nick=rs.getString("nick");
              contraseña=rs.getString("contrasena");
              estado=rs.getString("estado");
              c_conectar.cerrar(st);
              c_conectar.cerrar(rs);
              consulta = true;
         }
     } catch (Exception e) {
     }
     return consulta;
 }*/
   public boolean cargar_datos() {
        boolean existe = false;

        try {
             st = c_conectar.conexion();
            String query = "select * "
                    + "from empleados "
                    + "where id_empleado= '" + id_empleados 
                    + "'";
             rs = c_conectar.consulta(st, query);

            if (rs.next()) {
                  id_empleados=rs.getInt("id_empleado");
              dni=rs.getString("dni");
              nombres=rs.getString("nombres");
              ape_pat=rs.getString("ape_pat");
              ape_mat=rs.getString("ape_mat");
              direccion=rs.getString("direccion");
              correo=rs.getString("correo");
              telefono=rs.getString("telefono");
              fecha_nacimiento=rs.getString("fecha_nacimiento");
              nick=rs.getString("nick");
              contraseña=rs.getString("contrasena");
              estado=rs.getString("estado");
                existe = true;
            }

            c_conectar.cerrar(rs);
            c_conectar.cerrar(st);
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }

        return existe;
    }

 
}
