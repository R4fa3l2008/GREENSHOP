package controlador;

import dao.PlantaDAO;
import modelo.Planta;
import modelo.Proveedor;
import modelo.TipoPlanta;

import java.util.List;

public class PlantaControlador {
    private PlantaDAO dao;

    public PlantaControlador() {
        this.dao = new PlantaDAO();
    }

    public boolean guardarPlanta(String nombreStr, String precioStr, String cantidadStr, TipoPlanta tipo, Proveedor proveedor, String rutaImagen) {
        if (nombreStr == null || nombreStr.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la planta no puede estar vacío.");
        }
        if (tipo == null) {
            throw new IllegalArgumentException("Debe seleccionar un tipo de planta.");
        }
        if (proveedor == null) {
            throw new IllegalArgumentException("Debe seleccionar un proveedor.");
        }

        double precio;
        try {
            precio = Double.parseDouble(precioStr);
            if (precio <= 0) {
                throw new IllegalArgumentException("El precio debe ser mayor que cero.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El precio debe tener un formato numérico válido.");
        }

        int cantidad;
        try {
            cantidad = Integer.parseInt(cantidadStr);
            if (cantidad < 0) {
                throw new IllegalArgumentException("La cantidad no puede ser negativa.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("La cantidad debe tener un formato numérico entero válido.");
        }

        Planta planta = new Planta(0, tipo.getIdTipo(), proveedor.getIdProveedor(), nombreStr.trim(), precio, cantidad, rutaImagen);
        return dao.guardar(planta);
    }

    public Planta buscarPorId(String idStr) {
        try {
            int id = Integer.parseInt(idStr);
            Planta planta = dao.buscarPorId(id);
            if (planta == null) {
                throw new IllegalArgumentException("No se encontró ninguna planta con el ID especificado.");
            }
            return planta;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El identificador debe ser un número entero válido.");
        }
    }

    public List<Planta> listarPlantas() {
        return dao.listar();
    }

    public List<TipoPlanta> obtenerTipos() {
        return dao.cargarTipos();
    }

    public List<Proveedor> obtenerProveedores() {
        return dao.cargarProveedores();
    }
}