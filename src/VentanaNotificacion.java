import javax.swing.*;

public class VentanaNotificacion implements ObservadorTarea {
    @Override
    public void notificar(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Notificación", JOptionPane.INFORMATION_MESSAGE);
    }
}

