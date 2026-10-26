package modelo;

public class Planta {
    private int idPlanta;
    private int idTipo;
    private int idProveedor;
    private String nombre;
    private double precio;
    private int cantidad;
    private String rutaImagen;

    public Planta(int idPlanta, int idTipo, int idProveedor, String nombre, double precio, int cantidad, String rutaImagen) {
        this.idPlanta = idPlanta;
        this.idTipo = idTipo;
        this.idProveedor = idProveedor;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.rutaImagen = rutaImagen;
    }

    public int getIdPlanta() { return idPlanta; }
    public void setIdPlanta(int idPlanta) { this.idPlanta = idPlanta; }

    public int getIdTipo() { return idTipo; }
    public void setIdTipo(int idTipo) { this.idTipo = idTipo; }

    public int getIdProveedor() { return idProveedor; }
    public void setIdProveedor(int idProveedor) { this.idProveedor = idProveedor; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public String getRutaImagen() { return rutaImagen; }
    public void setRutaImagen(String rutaImagen) { this.rutaImagen = rutaImagen; }
}