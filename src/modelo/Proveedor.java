package modelo;

public class Proveedor {
	private int id_proveedor;
	private String nombre;
	private String telefono;
	
	public Proveedor(int id_proveedor, String nombre, String telefono) {
		this.id_proveedor=id_proveedor;
		this.nombre= nombre;
		this.telefono=telefono;
	}
	
	public int getIdProveedor() {return id_proveedor;}
	public void setIdProveedor(int id_proveedor) {this.id_proveedor=id_proveedor;}
	
	public String getNombre() {return nombre;}
	public void setNombre(String nombre) {this.nombre=nombre;}
	
	public String getTelefono() {return telefono;}
	public void setTelefono(String telefono) {this.telefono=telefono;}
	
	@Override
	public String toString() {
		return nombre;
	}

}
