package listas;

import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Lista simplemente enlazada genérica.
 *
 * INSTRUCCIONES PARA EL ESTUDIANTE
 * --------------------------------
 * 1. Cada método marcado con "TODO" lanza UnsupportedOperationException.
 *    Reemplace esa línea por su implementación.
 * 2. NO modifique las pruebas (src/test). 
 * 3. Resuelva en orden (Etapa 1, 2, 3...): cada etapa usa las anteriores.
 * 4. Solo se permite usar nodos propios (clase Nodo). No use
 *    java.util.LinkedList ni ArrayList dentro de esta clase.
 */
public class ListaEnlazada<T> {

    /** Primer nodo de la lista (null cuando la lista está vacía). */
    private Nodo<T> cabeza;

    /** Cantidad de elementos. Debe mantenerse actualizada en cada operación. */
    private int cantidad;

    public ListaEnlazada() {
        this.cabeza = null;
        this.cantidad = 0;
    }

    // =====================================================================
    // Métodos auxiliares privados
    // =====================================================================

    /**
     * Retorna el nodo que está en la posición indicada.
     * Se asume que el índice ya fue validado (0 <= indice < cantidad).
     */
    private Nodo<T> obtenerNodo(int indice) {
        Nodo<T> actual = cabeza;
        for (int i = 0; i < indice; i++) {
            actual = actual.getSiguiente();
        }
        return actual;
    }

    /** Lanza IndexOutOfBoundsException si indice < 0 o indice >= cantidad. */
    private void validarIndiceDeElemento(int indice) {
        if (indice < 0 || indice >= cantidad) {
            throw new IndexOutOfBoundsException(
                    "Índice " + indice + " fuera de rango (tamaño " + cantidad + ")");
        }
    }

    // =====================================================================
    // ETAPA 1. Estado de la lista
    // =====================================================================

    /** @return true si la lista no tiene elementos. */
    public boolean estaVacia() {
        return cantidad == 0;
        
    }

    /** @return número de elementos de la lista. */
    public int tamanio() {
        
        return cantidad;
    }

    // =====================================================================
    // ETAPA 2. Agregar en los extremos
    // =====================================================================

    /** Inserta el dato como nuevo primer elemento. */
    public void agregarAlInicio(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        nuevo.setSiguiente(cabeza);
        cabeza = nuevo;
        cantidad++;
    }

    /** Inserta el dato como nuevo último elemento. */
    public void agregarAlFinal(T dato) {
       if (estaVacia()) {
        agregarAlInicio(dato);
        return;
       }
        Nodo<T> ultimo = cabeza; 
        while (ultimo.getSiguiente() != null) {
            ultimo = ultimo.getSiguiente();
        }
        ultimo.setSiguiente(new Nodo<>(dato));
        cantidad++;
    }

    // =====================================================================
    // ETAPA 3. Consultar elementos
    // =====================================================================

    /** @throws NoSuchElementException si la lista está vacía */
    public T obtenerPrimero() {
        if (estaVacia()) {
            throw new NoSuchElementException("La lista está vacía");
            
        }
        return cabeza.getDato();
    }

    /** @throws NoSuchElementException si la lista está vacía */
    public T obtenerUltimo() {
        if (estaVacia()) {
            throw new NoSuchElementException("La lista está vacía");
        }
        return obtenerNodo(cantidad -1).getDato();
    }

    /**
     * @param indice posición (0 = primer elemento)
     * @throws IndexOutOfBoundsException si indice < 0 o indice >= tamanio()
     */
    public T obtener(int indice) {
       validarIndiceDeElemento(indice);
       return obtenerNodo(indice).getDato();
    }

    // =====================================================================
    // ETAPA 4. Búsqueda
    // =====================================================================

    /** @return true si algún elemento es igual (equals) al dato. Admite null. */
    public boolean contiene(T dato) {
        return indiceDe(dato) != -1; 

    }

    /** @return posición de la primera aparición del dato, o -1 si no está. */
    public int indiceDe(T dato) {
        Nodo<T> actual = cabeza; 
        int posicion = 0;
        while (actual != null) {
            if (Objects.equals(actual.getDato(),dato)){
                return posicion;
            }
            actual = actual.getSiguiente();
            posicion ++;
        }
        return -1;
    }

    // =====================================================================
    // ETAPA 5. Insertar y reemplazar en cualquier posición
    // =====================================================================

    /**
     * Inserta el dato en la posición indicada, desplazando los siguientes.
     * Se permite indice == tamanio() (equivale a agregar al final).
     *
     * @throws IndexOutOfBoundsException si indice < 0 o indice > tamanio()
     */
    public void insertar(int indice, T dato) {
       if (indice < 0 || indice > cantidad) {
        throw new IndexOutOfBoundsException("indice" + indice + " fuera de rango (tamaño"+cantidad+")");
       }
       if (indice== 0){
            agregarAlInicio(dato);
            return;
       }
       Nodo<T> anterior = obtenerNodo(indice -1);
         Nodo<T> nuevo = new Nodo<>(dato);
        nuevo.setSiguiente(anterior.getSiguiente());
        anterior.setSiguiente(nuevo);
        cantidad++;        
    }

    /**
     * Reemplaza el dato de la posición indicada.
     *
     * @return el dato que había antes
     * @throws IndexOutOfBoundsException si indice < 0 o indice >= tamanio()
     */
    public T reemplazar(int indice, T dato) {
        validarIndiceDeElemento(indice);
        Nodo<T> nodo = obtenerNodo(indice);
        T datoAnterior = nodo.getDato();
        nodo.setDato(dato);
        return datoAnterior;
    }

    // =====================================================================
    // ETAPA 6. Eliminar
    // =====================================================================

    /**
     * Elimina y retorna el primer elemento.
     * @throws NoSuchElementException si la lista está vacía
     */
    public T eliminarPrimero() {
        if (estaVacia()) {
            throw new NoSuchElementException("La lista está vacía");
        }
        T datoEliminado = cabeza.getDato();
        cabeza = cabeza.getSiguiente();
        cantidad--;
        return datoEliminado;
    }


    /**
     * Elimina y retorna el último elemento.
     * @throws NoSuchElementException si la lista está vacía
     */
    public T eliminarUltimo() {
        if (estaVacia()) {
            throw new NoSuchElementException("La lista está vacía");
        }
        if (cantidad == 1) {
            return eliminarPrimero();
        }
        Nodo<T> penultimo = obtenerNodo(cantidad - 2);
        T datoEliminado = penultimo.getSiguiente().getDato();
        penultimo.setSiguiente(null);
        cantidad--;
        return datoEliminado;
    }

    /**
     * Elimina y retorna el elemento de la posición indicada.
     * @throws IndexOutOfBoundsException si indice < 0 o indice >= tamanio()
     */
    public T eliminar(int indice) {
        validarIndiceDeElemento(indice);
        if (indice == 0) {
            return eliminarPrimero();
        }
        Nodo<T> anterior = obtenerNodo(indice - 1);
        Nodo<T> nodoAEliminar = anterior.getSiguiente();
        anterior.setSiguiente(nodoAEliminar.getSiguiente());
        cantidad--;
        return nodoAEliminar.getDato();
    }

    /**
     * Elimina la primera aparición del dato.
     * @return true si se eliminó algo, false si el dato no estaba
     */
    public boolean eliminarValor(T dato) {
        int indice = indiceDe(dato);
        if (indice ==-1) {
            return false;

        }
        eliminar (indice);
        return true;
        
    }

    /** Deja la lista vacía. */
    public void vaciar() {
       cabeza = null;
       cantidad = 0; 
    }

    // =====================================================================
    // ETAPA 7. Representación en texto
    // =====================================================================

    /**
     * Formato: "[1, 2, 3]". Lista vacía: "[]".
     * Un elemento null se imprime como "null".
     */
    @Override
    public String toString() {
        StringBuilder texto = new StringBuilder("[");
        Nodo<T> actual = cabeza; 
        while (actual != null) {
            texto.append(actual.getDato());
            if (actual.getSiguiente() != null) {
                texto.append(", ");
            }
            actual = actual.getSiguiente();
        }
        texto.append("]");
        return texto.toString();
    }

    // =====================================================================
    // ETAPA 8. Retos
    // =====================================================================

    /**
     * Invierte la lista EN SITIO: no cree nodos nuevos, solo cambie
     * las referencias 'siguiente' de los nodos existentes.
     */
    public void invertir() {
        Nodo<T> anterior = null;
        Nodo<T> actual = cabeza;
        while (actual != null) {
            Nodo<T> siguiente = actual.getSiguiente();
            actual.setSiguiente(anterior);
            anterior = actual;
            actual = siguiente;
        }
        cabeza = anterior;
    }

    /**
     * Retorna el elemento del medio. Si el tamaño es par, el segundo de los
     * dos centrales (ej.: [1,2,3,4] -> 3). Pista: dos punteros, uno avanza
     * de a 1 y otro de a 2.
     *
     * @throws NoSuchElementException si la lista está vacía
     */
    public T obtenerMedio() {
        if (estaVacia()) {
            throw new NoSuchElementException("La lista está vacía");
        }
        Nodo<T> lento = cabeza;
        Nodo<T> rapido = cabeza;
        while (rapido != null && rapido.getSiguiente() != null) {
            lento = lento.getSiguiente();
            rapido = rapido.getSiguiente().getSiguiente();
        }
        return lento.getDato();
    }

    /**
     * Elimina los elementos repetidos dejando solo la primera aparición
     * de cada uno, conservando el orden. Ej.: [1,2,1,3,2] -> [1,2,3]
     */
    public void eliminarDuplicados() {
        Nodo<T> actual = cabeza;
        while (actual != null) {
            Nodo<T> runner = actual;
            while (runner.getSiguiente() != null) {
                if (Objects.equals(runner.getSiguiente().getDato(), actual.getDato())) {
                    runner.setSiguiente(runner.getSiguiente().getSiguiente());
                    cantidad--;
                } else {
                    runner = runner.getSiguiente();
                }
            }
            actual = actual.getSiguiente();
        }
    }
}