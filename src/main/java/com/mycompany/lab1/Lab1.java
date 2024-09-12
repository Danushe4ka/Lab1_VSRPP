/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.lab1;

/**
 *
 * @author User
 */

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Lab1 {

    private static final SimpleCalculator calculator = new SimpleCalculator();
    private static final int QUEUE_SIZE = 15;   //очередь сделал на 15
    private static final Random RANDOM = new Random();

    static class SimpleQueue<T> //потокобезопасная очередь для любого типа данных
    {
        private final Queue<T> queue = new LinkedList<>();
        private final int capacity;

        public SimpleQueue(int capacity) {
            this.capacity = capacity;
        }

        public synchronized void put(T item) throws InterruptedException    //syncronized чтобы только один поток выполнял один метод единовременно(можно и в блоке кода использовать)
        {
            while (queue.size() == capacity) {
                wait(); //  освобождаем монитор и оставляем потоки в ожидании
            }
            queue.add(item);
            notifyAll();    //для пробуждения всех потоков
        }

        public synchronized T take() throws InterruptedException
        {
            while (queue.isEmpty()) {
                wait();
            }
            T item = queue.poll();
            notifyAll();
            return item;
        }
    }


    static class Producer extends Thread    //поток-производитель
    {
        private final SimpleQueue<Double> queue;
        private final String operation;
        private final double a;
        private final double b;

        public Producer(SimpleQueue<Double> queue, String operation, double a, double b) {
            this.queue = queue;
            this.operation = operation;
            this.a = a;
            this.b = b;
        }

        @Override
        public void run() {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    double result = "op1".equals(operation) ? calculator.Calculate(a, '^')  : calculator.Calculate(a, b, '/');
                    queue.put(result);
                    Thread.sleep(RANDOM.nextInt(1000)); // Имитация работы
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    static class Consumer extends Thread    //поток-потребитель
    {
        private final SimpleQueue<Double> queue;

        public Consumer(SimpleQueue<Double> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    double result = queue.take();
                    System.out.println("Number: " + result);
                    Thread.sleep(RANDOM.nextInt(1000)); // Имитация работы
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    
    public static void main(String[] args) throws InterruptedException
    {
        SimpleQueue<Double> queue = new SimpleQueue<>(QUEUE_SIZE);
        
        Producer prod1 = new Producer(queue, "op1", 64, 0);
        Producer prod2 = new Producer(queue, "op2", 87.1, 11.1);
        Consumer cons = new Consumer(queue);
        prod1.start();
        prod2.start();
        cons.start();
        Thread.sleep(5000);
        prod1.interrupt();
        prod2.interrupt();
        cons.interrupt();
        prod1.join();
        prod2.join();
        cons.join();
    }
}
