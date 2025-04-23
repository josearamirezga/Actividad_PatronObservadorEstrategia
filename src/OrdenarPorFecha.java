import java.util.*;

public class OrdenarPorFecha implements EstrategiaOrdenamiento{
    public void ordenar(List<Tarea> tareas) {
        tareas.sort(Comparator.comparingInt(Tarea::getPrioridad));
    }
}