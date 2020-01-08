/*
 * www.javagl.de - Common
 *
 * Copyright (c) 2012-2015 Marco Hutter - http://www.javagl.de
 */
package de.javagl.common.collections;

import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("javadoc")
public class TestDoubleMaps
{
    public static void main(String[] args)
    {
        testSimple();
        testEmptyRange();
    }
    
    private static void testSimple()
    {
        Map<String, Double> map = new LinkedHashMap<String, Double>();
        map.put("A", 10.0);
        map.put("B", 20.0);
        map.put("C", 30.0);
        map.put("D", 40.0);
        Map<String, Double> result = 
            DoubleMaps.scaleValuesToRange(map, 0.0, 1.0);
        System.out.println(result);
    }

    private static void testEmptyRange()
    {
        Map<String, Double> map = new LinkedHashMap<String, Double>();
        map.put("A", 10.0);
        map.put("B", 10.0);
        map.put("C", 10.0);
        map.put("D", 10.0);
        Map<String, Double> result = 
            DoubleMaps.scaleValuesToRange(map, 0.0, 1.0);
        System.out.println(result);
    }
    
    
}
