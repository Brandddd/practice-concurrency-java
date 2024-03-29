package com.example;

import java.util.concurrent.ForkJoinPool;
//import java.util.concurrent.ThreadLocalRandom;

public class Main {

    // We use a lot of memory
    // The project properties should have a Run, VM Option of -Xmx1024m
    public static void main(String[] args) {
        int[] data = new int[1024 * 1024 * 128]; // 512MB
        long startTime = System.currentTimeMillis();

        /*
         * for (int i = 0; i < data.length; i++) {
         * /*
         * Se usa ThreadLocalRandom en lugar de Math.Random() porque Math.Random() No se
         * escala
         * cuando se ejecuta simultaneamente con varios threads y eliminaría cualquier
         * ventaja de aplicar
         * el marco Fork-join.
         */
        // data[i] = ThreadLocalRandom.current().nextInt(); //Generador de numeros
        // aleatorios dentro de la matriz data
        // }

        // * Forma secuencial de encontrar el max_value
        // int max = Integer.MIN_VALUE;
        // for (int value : data) {
        // if (value > max) {
        // max = value;
        // }
        // }
        // System.out.println("Max value found:" + max);

        ForkJoinPool pool = new ForkJoinPool(); // Invoca una tarea de tipo forkJoinTask.
        // Nuevo elemento RandomArrayAction
        RandomArrayAction arraction = new RandomArrayAction(data, 0, data.length - 1, data.length / 16);
        pool.invoke(arraction); // Llamar elemento forkJoin para la ejecucion de la subtarea
        System.out.println("Max valor encontrado en la matriz: " + arraction.getMax());
        System.out.println(
                "Tiempo de ejecución tomado: " + (System.currentTimeMillis() - startTime) / 1000.0000 + " segundos.");

        // FindMaxTask task = new FindMaxTask(data, 0, data.length-1, data.length/16);
        // Invoca clase FindMaxTask
        // Integer result = pool.invoke(task);
        // System.out.println("Max Value FindMaxTask: " + result);
    }
}

/*
 * Para poder invocar múltiples subtareas en paralelo de forma recursiva,
 * invocará a una tarea
 * de tipo RecursiveAction, que extiende de ForkJoinTask.
 * La clase RecursiveACtion contiene el método compute(). la cual será la
 * encargada de de ejecutar
 * nuestra tarea paralelizable.
 */