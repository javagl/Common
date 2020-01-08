package de.javagl.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@SuppressWarnings("javadoc")
public class TestComparators
{
    public static void main(String[] args)
    {
        Comparator<String> comparator = 
            Comparators.createOrderIgnoreCase("");
        
        List<String> list = new ArrayList<String>(Arrays.asList(
            "XXXA",
            "YYYB",
            "ZZZC",
            "AAA",
            "CCC",
            "BBB"));
        
        Collections.sort(list, comparator);
        list.forEach(System.out::println);
        
    }
}
