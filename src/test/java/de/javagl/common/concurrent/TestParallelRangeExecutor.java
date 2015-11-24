/*
 * www.javagl.de - Common
 *
 * Copyright (c) 2012-2015 Marco Hutter - http://www.javagl.de
 */
package de.javagl.common.concurrent;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import de.javagl.common.concurrent.ParallelRangeExecutor.RangeExecutor;

@SuppressWarnings("javadoc")
@RunWith(Parameterized.class)
public class TestParallelRangeExecutor
{
    private static class CollectingRangeExecutor implements RangeExecutor
    {
        private final List<Integer> elements = new ArrayList<Integer>();

        @Override
        public void execute(int taskIndex, int min, int max)
        {
            synchronized (this)
            {
                elements.addAll(create(min, max));
            }
        }
        
        List<Integer> getElements()
        {
            Collections.sort(elements);
            return elements;
        }
    }
    
    private static List<Integer> create(int min, int max)
    {
        List<Integer> list = new ArrayList<Integer>();
        for (int i=min; i<max; i++)
        {
            list.add(i);
        }
        return list;
    }
    
    @Parameters
    public static Collection<Object[]> data() 
    {
        Collection<Object[]> data = new ArrayList<Object[]>();
        for (int parallelism=1; parallelism<=4; parallelism++)
        {
            final int min = 0;
            for (int max=min; max<10; max++)
            {
                data.add(new Object[] { parallelism, min, max });
            }
        }
        for (int parallelism=1; parallelism<=4; parallelism++)
        {
            final int min = 10;
            for (int max=min; max<20; max++)
            {
                data.add(new Object[] { parallelism, min, max });
            }
        }
        return data;
    }

    private int parallelism;
    private int min;
    private int max;

    public TestParallelRangeExecutor(int parallelism, int min, int max) 
    {
        this.parallelism = parallelism;
        this.min = min;
        this.max = max;
    }

    @Test
    public void test() 
    {
        assertEquals(create(min, max), 
            TestParallelRangeExecutor.compute(parallelism, min, max));
    }
    
    private static List<Integer> compute(int parallelism, int min, int max)
    {
        CollectingRangeExecutor e = new CollectingRangeExecutor();
        ExecutorService ex = Executors.newCachedThreadPool();
        ParallelRangeExecutor.execute(parallelism, ex, min, max, e);
        return e.getElements();
    }
}
