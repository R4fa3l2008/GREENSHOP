package vista;

import controlador.PlantaControlador;
import modelo.Planta;
import modelo.Proveedor;
import modelo.TipoPlanta;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.List;

public class VentanaPlantas extends JFrame {
    private JTextField txtId, txtNombre, txtPrecio, txtCantidad;
    private JComboBox<TipoPlanta> cmbTipo;
    private JComboBox<Proveedor> cmbProveedor;
    private JLabel lblImagen;
    private JTable tablaPlantas;
    private DefaultTableModel modeloTabla;
    
    private PlantaControlador controlador;
    private String rutaImagenSeleccionada = "";

    public VentanaPlantas() {
        controlador = new PlantaControlador();
        setTitle("GREENSHOP - Sistema de Gestión de Plantas");
        setSize(720, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        inicializarComponentes();
        cargarCombos();
        actualizarTabla();
    }

    private void inicializarComponentes() {
        JPanel panelFormulario = new JPanel(new GridLayout(7, 2, 5, 5));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos de la Planta"));

        panelFormulario.add(new JLabel("ID Planta (para consultar):"));
        txtId = new JTextField();
        panelFormulario.add(txtId);

        panelFormulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Tipo de Planta:"));
        cmbTipo = new JComboBox<>();
        panelFormulario.add(cmbTipo);

        panelFormulario.add(new JLabel("Proveedor:"));
        cmbProveedor = new JComboBox<>();
        panelFormulario.add(cmbProveedor);

        panelFormulario.add(new JLabel("Precio:"));
        txtPrecio = new JTextField();
        panelFormulario.add(txtPrecio);

        panelFormulario.add(new JLabel("Cantidad:"));
        txtCantidad = new JTextField();
        panelFormulario.add(txtCantidad);

        JButton btnSeleccionarImagen = new JButton("Seleccionar Imagen");
        btnSeleccionarImagen.addActionListener(e -> seleccionarImagen());
        panelFormulario.add(btnSeleccionarImagen);

        lblImagen = new JLabel("Sin imagen", JLabel.CENTER);
        lblImagen.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panelFormulario.add(lblImagen);

        add(panelFormulario, BorderLayout.WEST);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton btnGuardar = new JButton("Guardar");
        JButton btnConsultar = new JButton("Consultar");
        JButton btnMostrarTodos = new JButton("Mostrar todos");
        JButton btnLimpiar = new JButton("Limpiar");

        btnGuardar.addActionListener(e -> guardarPlanta());
        btnConsultar.addActionListener(e -> consultarPlanta());
        btnMostrarTodos.addActionListener(e -> actualizarTabla());
        btnLimpiar.addActionListener(e -> limpiarCampos());

        panelBotones.add(btnGuardar);
        panelBotones.add(btnConsultar);
        panelBotones.add(btnMostrarTodos);
        panelBotones.add(btnLimpiar);

        add(panelBotones, BorderLayout.NORTH);

        // Tabla de resultados
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Tipo ID", "Proveedor ID", "Precio", "Cantidad"}, 0);
        tablaPlantas = new JTable(modeloTabla);
        add(new JScrollPane(tablaPlantas), BorderLayout.CENTER);
    }

    private void cargarCombos() {
        List<TipoPlanta> tipos = controlador.obtenerTipos();
        for (TipoPlanta t : tipos) {
            cmbTipo.addItem(t);
        }

        List<Proveedor> proveedores = controlador.obtenerProveedores();
        for (Proveedor p : proveedores) {
            cmbProveedor.addItem(p);
        }
    }

    private void seleccionarImagen() {
        JFileChooser fileChooser = new JFileChooser();
        int resultado = fileChooser.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            rutaImagenSeleccionada = archivo.getAbsolutePath();
            ImageIcon icono = new ImageIcon(rutaImagenSeleccionada);
            Image imagenEscalada = icono.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            lblImagen.setIcon(new ImageIcon(imagenEscalada));
            lblImagen.setText("");
        }
    }

    private void guardarPlanta() {
        try {
            TipoPlanta tipo = (TipoPlanta) cmbTipo.getSelectedItem();
            Proveedor proveedor = (Proveedor) cmbProveedor.getSelectedItem();
            boolean exito = controlador.guardarPlanta(
                txtNombre.getText(),
                txtPrecio.getText(),
                txtCantidad.getText(),
                tipo,
                proveedor,
                rutaImagenSeleccionada
            );
            if (exito) {
                JOptionPane.showMessageDialog(this, "Planta guardada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                actualizarTabla();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de validación", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void consultarPlanta() {
        try {
            Planta planta = controlador.buscarPorId(txtId.getText());
            txtNombre.setText(planta.getNombre());
            txtPrecio.setText(String.valueOf(planta.getPrecio()));
            txtCantidad.setText(String.valueOf(planta.getCantidad()));
            
            for (int i = 0; i < cmbTipo.getItemCount(); i++) {
                if (cmbTipo.getItemAt(i).getIdTipo() == planta.getIdTipo()) {
                    cmbTipo.setSelectedIndex(i);
                    break;
                }
            }
            
            for (int i = 0; i < cmbProveedor.getItemCount(); i++) {
                if (cmbProveedor.getItemAt(i).getIdProveedor() == planta.getIdProveedor()) {
                    cmbProveedor.setSelectedIndex(i);
                    break;
                }
            }

            rutaImagenSeleccionada = planta.getRutaImagen();
            if (rutaImagenSeleccionada != null && !rutaImagenSeleccionada.isEmpty()) {
                File archivoImg = new File(rutaImagenSeleccionada);
                if (archivoImg.exists()) {
                    ImageIcon icono = new ImageIcon(rutaImagenSeleccionada);
                    Image img = icono.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
                    lblImagen.setIcon(new ImageIcon(img));
                    lblImagen.setText("");
                } else {
                    lblImagen.setIcon(null);
                    lblImagen.setText("Imagen no encontrada");
                }
            } else {
                lblImagen.setIcon(null);
                lblImagen.setText("Sin imagen");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de consulta", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        List<Planta> lista = controlador.listarPlantas();
        for (Planta p : lista) {
            modeloTabla.addRow(new Object[]{
                p.getIdPlanta(),
                p.getNombre(),
                p.getIdTipo(),
                p.getIdProveedor(),
                p.getPrecio(),
                p.getCantidad()
            });
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
        txtCantidad.setText("");
        if (cmbTipo.getItemCount() > 0) cmbTipo.setSelectedIndex(0);
        if (cmbProveedor.getItemCount() > 0) cmbProveedor.setSelectedIndex(0);
        lblImagen.setIcon(null);
        lblImagen.setText("Sin imagen");
        rutaImagenSeleccionada = "";
    }
}