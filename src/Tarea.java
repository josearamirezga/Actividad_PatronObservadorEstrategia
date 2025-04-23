import java.util.*;

public class Tarea {
    private String nombre;
    private int prioridad; // 1: Baja, 2: Media, 3: Alta
    private Date fechaEntrega;
    private String estado;
    private List<ObservadorTarea> observadores = new ArrayList<>();

    public Tarea(String nombre) {
        this.nombre = nombre;
        this.prioridad = 0;
        this.estado = "Por hacer";
    }

    public String getNombre() {
        return nombre;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public String getPrioridadTexto() {
        return switch (prioridad) {
            case 1 -> "Baja";
            case 2 -> "Media";
            case 3 -> "Alta";
            default -> "Sin asignar";
        };
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public String getEstado() {
        return estado;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
        notificar("Se ha actualizado la prioridad de '" + nombre + "' a " + getPrioridadTexto());
    }

    public void setFechaEntrega(Date fecha) {
        this.fechaEntrega = fecha;
        notificar("Se ha asignado la fecha de entrega a '" + nombre + "': " + fecha);
    }

    public void setEstado(String estado) {
        this.estado = estado;
        notificar("La tarea '" + nombre + "' ahora tiene estatus: " + estado);
    }

    public void agregarObservador(ObservadorTarea obs) {
        observadores.add(obs);
    }

    private void notificar(String mensaje) {
        for (ObservadorTarea obs : observadores) {
            obs.notificar(mensaje);
        }
    }

    @Override
    public String toString() {
        return nombre + " | Prioridad: " + getPrioridadTexto() + " | Fecha: " +
                (fechaEntrega != null ? fechaEntrega.toString() : "No asignada") +
                " | Estado: " + estado;
    }
}
