// Estrategia concreta: Ordenar por prioridad ascendente

import java.util.*;

public class OrdenarPorPrioridad implements EstrategiaOrdenamiento{
    public void ordenar(List<Tarea> tareas) {
        tareas.sort(Comparator.comparingInt(Tarea::getPrioridad));
    }
}
 