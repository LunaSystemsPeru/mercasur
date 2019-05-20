
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

/*
 clase para cargar productos en txt autocompleter.
 */
public class cl_ac_productos {

    cl_conectar c_conectar = new cl_conectar();
    cl_varios c_varios = new cl_varios();
    private int id;
    private String descripcion;
    private double cantidad;
    private String und_medida;
    private double precio;

    public cl_ac_productos() {
    }

    public cl_ac_productos(int id, String descripcion, double cantidad, String und_medida,
            double precio) {
        this.id = id;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.und_medida = und_medida;
        this.precio = precio;
    }

    @Override
    public String toString() {

        return String.format("%1$-80s\t", descripcion)
                + " || Cant: "
                + String.format("%7s", c_varios.formato_cantidad(cantidad))
                + " " + String.format("%-15s\t", und_medida.trim())
                + " || P.Ven: S/. "
                + String.format("%9s", c_varios.formato_precio(precio));

        /*return "<html>"
         + "<u>" + descripcion + "</u>"
         + "<br>&nbsp;&nbsp;&nbsp;" + "Cant: " + c_varios.formato_numero(cantidad) + "   " + und_medida.trim()
         + "<br>&nbsp;&nbsp;&nbsp;" + "Pre. Venta: " + c_varios.formato_totales(precio)
         + "</html>";
         */
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getUnd_medida() {
        return und_medida;
    }

    public void setUnd_medida(String und_medida) {
        this.und_medida = und_medida;
    }
}
