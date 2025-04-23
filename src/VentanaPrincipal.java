import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VentanaPrincipal extends JFrame {
    private JTextField campoNombre;
    private JButton btnContinuar;
    private JPanel panelTareas;
    private GestorDeTareas gestor;
    private VentanaNotificacion notificador;

    public VentanaPrincipal(GestorDeTareas gestor) {
        super("Gestor de Tareas");
        this.gestor = gestor;
        this.notificador = new VentanaNotificacion();

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        gbc.insets = new Insets(10, 10, 10, 10);

        campoNombre = new JTextField(20);
        btnContinuar = new JButton("Continuar");
        panelTareas = new JPanel(new GridLayout(0, 1, 5, 5));

        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(new JLabel("Ingresar nueva tarea:"), gbc);

        
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(campoNombre, gbc);

        
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        add(btnContinuar, gbc);

        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(new JScrollPane(panelTareas), gbc);

        // Acción para continuar
        btnContinuar.addActionListener(e -> {
            String nombre = campoNombre.getText().trim();
            if (!nombre.isEmpty()) {
                Tarea tarea = new Tarea(nombre);
                tarea.agregarObservador(notificador);
                gestor.agregarTarea(tarea);
                campoNombre.setText("");
                new VentanaDetalleTarea(this, tarea).setVisible(true);
                this.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, ingresa un nombre válido para la tarea.");
            }
        });

        actualizarPanelTareas();
    }

    public void actualizarPanelTareas() {
        ordenarTareasPorEstado();
        panelTareas.removeAll();

        for (Tarea t : gestor.getTareas()) {
            JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
            fila.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            fila.setBackground(Color.WHITE);

            JLabel info = new JLabel(t.toString());
            fila.add(info);

            JComboBox<String> comboEstado = new JComboBox<>(new String[]{"Por hacer", "En progreso", "Terminada"});
            comboEstado.setSelectedItem(t.getEstado());
            comboEstado.addActionListener(e -> {
                String nuevoEstado = (String) comboEstado.getSelectedItem();
                t.setEstado(nuevoEstado);
                ordenarTareasPorEstado();
                actualizarPanelTareas();
            });

            fila.add(new JLabel("Estado:"));
            fila.add(comboEstado);

            panelTareas.add(fila);
        }

        panelTareas.revalidate();
        panelTareas.repaint();
    }

    private void ordenarTareasPorEstado() {
        gestor.getTareas().sort((t1, t2) -> {
            int e1 = estadoAOrden(t1.getEstado());
            int e2 = estadoAOrden(t2.getEstado());
            return Integer.compare(e1, e2);
        });
    }

    private int estadoAOrden(String estado) {
        return switch (estado) {
            case "Por hacer" -> 0;
            case "En progreso" -> 1;
            case "Terminada" -> 2;
            default -> 3;
        };
    }
}
