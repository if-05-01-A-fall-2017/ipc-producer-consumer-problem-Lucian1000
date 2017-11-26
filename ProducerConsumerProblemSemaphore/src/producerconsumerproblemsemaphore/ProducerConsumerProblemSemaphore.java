/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package producerconsumerproblemsemaphore;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;

/**
 *
 * @author Marvin
 */
public class ProducerConsumerProblemSemaphore {
    public static void main(String[] args) throws InterruptedException {
        final CriticalRegion region = new CriticalRegion();
 
        Thread t1 = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    region.produce();
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        });
 
        Thread t2 = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    region.consume();
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        });
        
        t1.start();
        t2.start();
 
        t1.join();
        t2.join();
    }
 
    public static class CriticalRegion{
        LinkedList<Integer> list = new LinkedList<>();
        int capacity = 2;
        Semaphore sem = new Semaphore(1);

        public void produce() throws InterruptedException{
            int value = 0;
            while (true){
                while (list.size()==capacity)
                    sem.acquire();

                System.out.println("Producer produced-" + list.size());

                list.add(value++);

                sem.release();
                Thread.sleep(500);
            }
        }
 
        public void consume() throws InterruptedException{
            while (true){
                while (list.size()==0)
                    sem.acquire();

                int val = list.removeFirst();

                System.out.println("Consumer consumed-" + list.size());

                sem.release();
                Thread.sleep(500);
            }
        }
    }
}
