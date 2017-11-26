/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package producerconsumerproblem.pkgsynchronized;

import java.util.LinkedList;

/**
 *
 * @author Marvin
 */
public class ProducerConsumerProblemSynchronized {
    public static void main(String[] args) throws InterruptedException{
        
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
 
    public static class CriticalRegion
    {
        LinkedList<Integer> list = new LinkedList<>();
        int capacity = 2;

        public void produce() throws InterruptedException
        {
            int value = 0;
            while (true)
            {
                synchronized (this)
                {
                    while (list.size()==capacity)
                        wait();
 
                    System.out.println("Producer produced-"
                                                  + value);

                    list.add(value++);
 
                    notify();
                }
            }
        }
 
        public void consume() throws InterruptedException
        {
            while (true)
            {
                synchronized (this)
                {
                    while (list.size()==0)
                        wait();
 
                    int val = list.removeFirst();
 
                    System.out.println("Consumer consumed-" + val);
 
                    notify();
                }
            }
        }
    }
}
