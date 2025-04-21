/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.thrends;

/**
 *
 * @author diego
 */
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Thrends {
    private static int contador = 0;
    private static final Queue<Integer> cola = new LinkedList<>();
    private static final int CAPACIDAD = 5;

    public static void main(String[] args) {
        // Ejercicio 1: Creación básica de hilos
        Thread hiloNumeros = new Thread() {
            @Override
            public void run() {
                for (int i = 1; i <= 5; i++) {
                    System.out.println("Número: " + i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        Thread hiloLetras = new Thread() {
            @Override
            public void run() {
                for (char c = 'A'; c <= 'E'; c++) {
                    System.out.println("Letra: " + c);
                    try {
                        Thread.sleep(2000); 
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        hiloNumeros.start();
        hiloLetras.start();

        try {
            hiloNumeros.join();
            hiloLetras.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Ejercicio 2: Uso de Runnable
        Runnable tareaNumeros = new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 5; i++) {
                    System.out.println("Runnable Número: " + i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        Runnable tareaLetras = new Runnable() {
            @Override
            public void run() {
                for (char c = 'A'; c <= 'E'; c++) {
                    System.out.println("Runnable Letra: " + c);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        Thread hiloRunnableNumeros = new Thread(tareaNumeros);
        Thread hiloRunnableLetras = new Thread(tareaLetras);

        hiloRunnableNumeros.start();
        hiloRunnableLetras.start();

        try {
            hiloRunnableNumeros.join();
            hiloRunnableLetras.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Ejercicio 3: Sincronización de hilos usando Runnable
        Runnable incrementar = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    incrementarContador();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        Runnable decrementar = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    decrementarContador();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        Thread hiloIncrementar = new Thread(incrementar);
        Thread hiloDecrementar = new Thread(decrementar);

        hiloIncrementar.start();
        hiloDecrementar.start();

        try {
            hiloIncrementar.join();
            hiloDecrementar.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Valor final del contador: " + contador);

        // Ejercicio 4: Uso de wait() y notify() usando Runnable
        Runnable productor = new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                while (true) {
                    synchronized (cola) {
                        while (cola.size() == CAPACIDAD) {
                            try {
                                cola.wait(); // Esperar si la cola está llena
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        int numero = random.nextInt(100);
                        cola.add(numero);
                        System.out.println("Producido: " + numero);
                        cola.notify(); // Notificar al consumidor
                    }
                    try {
                        Thread.sleep(500); // Pausa de 500 milisegundos
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        Runnable consumidor = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    synchronized (cola) {
                        while (cola.isEmpty()) {
                            try {
                                cola.wait(); // Esperar si la cola está vacía
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        int numero = cola.poll();
                        System.out.println("Consumido: " + numero);
                        cola.notify(); // Notificar al productor
                    }
                    try {
                        Thread.sleep(500); // Pausa de 500 milisegundos
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        Thread hiloProductor = new Thread(productor);
        Thread hiloConsumidor = new Thread(consumidor);

        // Iniciar ambos hilos
        hiloProductor.start();
        hiloConsumidor.start();
    }

    // Método sincronizado para incrementar el contador
    private synchronized static void incrementarContador() {
        contador++;
        System.out.println("Incrementar: " + contador);
    }

    // Método sincronizado para decrementar el contador
    private synchronized static void decrementarContador() {
        contador--;
        System.out.println("Decrementar: " + contador);
    }
}
