import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;
import java.util.Date;

public class VentanaDetalleTarea extends JFrame {
    private JCheckBox baja, media, alta;
    private JComboBox<Integer> diaBox, anioBox;
    private JComboBox<String> mesBox;
    private JButton btnAgregar, btnVolver;
    private Tarea tarea;
    private VentanaPrincipal ventanaAnterior;

    public VentanaDetalleTarea(VentanaPrincipal vp, Tarea tarea) {
        super("Detalle de Tarea");
        this.ventanaAnterior = vp;
        this.tarea = tarea;

        setSize(400, 300);
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // prioridad
        baja = new JCheckBox("Baja");
        media = new JCheckBox("Media");
        alta = new JCheckBox("Alta");

        // Exclusividad entre checkboxes
        ActionListener exclusividad = e -> {
            JCheckBox source = (JCheckBox) e.getSource();
            baja.setSelected(source == baja);
            media.setSelected(source == media);
            alta.setSelected(source == alta);
        };
        baja.addActionListener(exclusividad);
        media.addActionListener(exclusividad);
        alta.addActionListener(exclusividad);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        add(new JLabel("Prioridad:"), gbc);
        gbc.gridy++;
        add(baja, gbc);
        gbc.gridy++;
        add(media, gbc);
        gbc.gridy++;
        add(alta, gbc);

        // fecha
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(new JLabel("Fecha de entrega:"), gbc);

        Calendar cal = Calendar.getInstance();
        int currentYear = cal.get(Calendar.YEAR);

        diaBox = new JComboBox<>();
        for (int d = 1; d <= 31; d++) diaBox.addItem(d);

        String[] meses = {
            "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
            "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
        };
        mesBox = new JComboBox<>(meses);

        anioBox = new JComboBox<>();
        for (int a = currentYear; a <= currentYear + 10; a++) anioBox.addItem(a);

        gbc.gridy++;
        add(diaBox, gbc);
        gbc.gridy++;
        add(mesBox, gbc);
        gbc.gridy++;
        add(anioBox, gbc);

        // botones
        btnAgregar = new JButton("Agregar");
        btnVolver = new JButton("Volver");

        gbc.gridy++;
        gbc.gridx = 0;
        add(btnVolver, gbc);
        gbc.gridx = 1;
        add(btnAgregar, gbc);

        // eventos
        btnAgregar.addActionListener(e -> {
            int prioridad = baja.isSelected() ? 1 : media.isSelected() ? 2 : alta.isSelected() ? 3 : 0;
            if (prioridad == 0) {
                JOptionPane.showMessageDialog(this, "Selecciona una prioridad.");
                return;
            }

            int dia = (int) diaBox.getSelectedItem();
            int mes = mesBox.getSelectedIndex(); // índice 0-11
            int anio = (int) anioBox.getSelectedItem();

            Calendar fecha = Calendar.getInstance();
            fecha.set(anio, mes, dia);
            Date fechaFinal = fecha.getTime();

            tarea.setPrioridad(prioridad);
            tarea.setFechaEntrega(fechaFinal);

            dispose();
            ventanaAnterior.setVisible(true);
            ventanaAnterior.actualizarPanelTareas();
        });

        btnVolver.addActionListener(e -> {
            dispose();
            ventanaAnterior.setVisible(true);
        });
    }
}
