/*
 * www.javagl.de - Common
 *
 * Copyright (c) 2012-2015 Marco Hutter - http://www.javagl.de
 */
package de.javagl.common.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * A simple integration test for the {@link ExecutorServices}
 */
@SuppressWarnings("javadoc")
public class ExecutorServicesExample
{
    public static void main(String[] args)
    {
        ExecutorService executorService = 
            ExecutorServices.createFixedTimeoutRethrowingExecutorService(
                4, 5, TimeUnit.SECONDS, false);
        
        for (int i=0; i<10; i++)
        {
            executorService.submit(new Runnable()
            {
                @Override
                public void run()
                {
                    System.out.println("Running");
                    sleep(100);
                }
            });
        }
        
        ThreadPoolExecutor tpe = (ThreadPoolExecutor)executorService;
        for (int i=0; i<20; i++)
        {
            System.out.println("Active "+tpe);
            sleep(400);
        }
        
        System.out.println("Once more");
        
        for (int i=0; i<10; i++)
        {
            executorService.submit(new Runnable()
            {
                @Override
                public void run()
                {
                    System.out.println("Running");
                    sleep(100);
                }
            });
        }
        
        for (int i=0; i<20; i++)
        {
            System.out.println("Active "+tpe);
            sleep(400);
        }
    }
    
    private static void sleep(int ms)
    {
        try
        {
            Thread.sleep(ms);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}




