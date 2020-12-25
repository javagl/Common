/*
 * www.javagl.de - Common
 *
 * Copyright (c) 2012-2015 Marco Hutter - http://www.javagl.de
 */
package de.javagl.common.iteration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Test;


@SuppressWarnings("javadoc")
public class TestIterables
{
    @Test
    public void testTransforming()
    {
        List<String> iterable = Arrays.asList("0", "1", "2");
        
        Iterable<Integer> transformingIterable =
            Iterables.transformingIterable(iterable, 
                s -> Integer.parseInt(s));
        
        Iterator<Integer> iterator0 = transformingIterable.iterator();
        Iterator<Integer> iterator1 = transformingIterable.iterator();
        List<Integer> expected = Arrays.asList(0,1,2);
        List<Integer> actual0 = Iterators.toList(iterator0);
        List<Integer> actual1 = Iterators.toList(iterator1);
        assertEquals(expected, actual0);
        assertEquals(expected, actual1);
    }
    
    @Test
    public void testFiltering()
    {
        List<String> iterable = Arrays.asList("0", "1", "2");
        
        Iterable<String> filteringIterable =
            Iterables.filteringIterable(iterable, 
                s -> !s.equals("1"));
        
        Iterator<String> iterator0 = filteringIterable.iterator();
        assertEquals(iterator0.next(), "0");
        assertEquals(iterator0.next(), "2");
        assertFalse(iterator0.hasNext());
        
        assertThrows(NoSuchElementException.class, iterator0::next);
        
        Iterator<String> iterator1 = filteringIterable.iterator();
        assertEquals(iterator1.next(), "0");
        assertEquals(iterator1.next(), "2");
        assertFalse(iterator1.hasNext());
        
        assertThrows(NoSuchElementException.class, iterator1::next);
    }
    
    @Test
    public void testFilteringAll()
    {
        List<String> iterable = Arrays.asList("0", "1", "2");
        
        Iterable<String> filteringIterable =
            Iterables.filteringIterable(iterable, 
                s -> false);
        
        Iterator<String> iterator = filteringIterable.iterator();
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }
    
}
