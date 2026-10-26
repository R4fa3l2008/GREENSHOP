package dao;

import conexion.ConexionBD;
import modelo.Planta;
import modelo.Proveedor;
import modelo.TipoPlanta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlantaDAO {

    public boolean guardar(Planta planta) {
        String sql = "INSERT INTO plantas (id_tipo, id_proovedor, nombre, precio, cantidad, ruta_imagen) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, planta.getIdTipo());
            pst.setInt(2, planta.getIdProveedor());
            pst.setString(3, planta.getNombre());
            pst.setDouble(4, planta.getPrecio());
            pst.setInt(5, planta.getCantidad());
            pst.setString(6, planta.getRutaImagen());
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Planta buscarPorId(int id) {
        String sql = "SELECT * FROM plantas WHERE id_planta = ?";
        Planta planta = null;
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    planta = new Planta(
                        rs.getInt("id_planta"),
                        rs.getInt("id_tipo"),
                        rs.getInt("id_proovedor"),
                        rs.getString("nombre"),
                        rs.getDouble("precio"),
                        rs.getInt("cantidad"),
                        rs.getString("ruta_imagen")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return planta;
    }

    public List<Planta> listar() {
        List<Planta> lista = new ArrayList<>();
        String sql = "SELECT * FROM plantas";
        try (Connection con = ConexionBD.obtenerConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Planta planta = new Planta(
                    rs.getInt("id_planta"),
                    rs.getInt("id_tipo"),
                    rs.getInt("id_proovedor"),
                    rs.getString("nombre"),
                    rs.getDouble("precio"),
                    rs.getInt("cantidad"),
                    rs.getString("ruta_imagen")
                );
                lista.add(planta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<TipoPlanta> cargarTipos() {
        List<TipoPlanta> lista = new ArrayList<>();
        String sql = "SELECT * FROM tipos_planta";
        try (Connection con = ConexionBD.obtenerConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new TipoPlanta(rs.getInt("id_tipo"), rs.getString("nombre"), rs.getString("descripcion")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<Proveedor> cargarProveedores() {
        List<Proveedor> lista = new ArrayList<>();
        String sql = "SELECT * FROM proovedores";
        try (Connection con = ConexionBD.obtenerConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Proveedor(rs.getInt("id_proovedor"), rs.getString("nombre"), rs.getString("telefono")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}