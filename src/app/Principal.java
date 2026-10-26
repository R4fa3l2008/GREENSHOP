package app;

import vista.VentanaPlantas;

import javax.swing.*;

public class Principal {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaPlantas ventana = new VentanaPlantas();
            ventana.setVisible(true);
        });
    }
}