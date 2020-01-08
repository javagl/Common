/*
 * www.javagl.de - Common
 *
 * Copyright (c) 2012-2015 Marco Hutter - http://www.javagl.de
 */
package de.javagl.common.collections;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * Basic integration test for the {@link Sets} class
 */
public class TestSets
{
    /**
     * A basic test
     * 
     * @param args Not used
     */
    public static void main(String[] args)
    {
        test(Arrays.asList("A", "B", "C"), Arrays.asList("A", "B", "C"));
        test(Arrays.asList("A", "B"), Arrays.asList("A", "B", "C"));
        test(Arrays.asList("A", "B", "C"), Arrays.asList("A", "B"));
        test(Arrays.asList("B", "C"), Arrays.asList("A", "B"));
    }
    
    /**
     * A basic test
     *  
     * @param c0 The first collection
     * @param c1 The second collection
     */
    private static void test(Collection<?> c0, Collection<?> c1)
    {
        System.out.println("Test with " + c0);
        System.out.println("      and " + c1);
        try
        {
            Sets.assertEqual(
                new LinkedHashSet<Object>(c0), 
                new LinkedHashSet<Object>(c1));
            System.out.println("Sets are equal");
        }
        catch (IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        }
        
    }

}
