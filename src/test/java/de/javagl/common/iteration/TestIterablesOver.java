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
public class TestIterablesOver
{
    @Test
    public void testIterableOverIterablesViaIterable()
    {
        List<String> iterable0 = Arrays.asList("A", "B");
        List<String> iterable1 = Arrays.asList();
        List<String> iterable2 = Arrays.asList("C", "D");
        Iterable<? extends Iterable<String>> iterablesIterable = 
            Arrays.asList(iterable0, iterable1, iterable2);
        
        Iterable<String> iterable = 
            Iterables.iterableOverIterables(iterablesIterable);

        List<String> actual0 = Iterators.toList(iterable.iterator());
        List<String> actual1 = Iterators.toList(iterable.iterator());
        List<String> expected = Arrays.asList("A", "B", "C", "D");
        assertEquals(expected, actual0);
        assertEquals(expected, actual1);
    }
    
    @Test
    public void testIterableOverIteratorsExactCalls()
    {
        List<String> iterable0 = Arrays.asList("A", "B");
        List<String> iterable1 = Arrays.asList();
        List<String> iterable2 = Arrays.asList("C", "D");
        List<String> iterable3 = Arrays.asList();
        
        Iterable<? extends Iterable<String>> iterablesIterable = 
            Arrays.asList(iterable0, iterable1, iterable2, iterable3);
        
        Iterable<String> iterable = 
            Iterables.iterableOverIterables(iterablesIterable);
        
        Iterator<String> iterator0 = iterable.iterator();
        assertEquals("A", iterator0.next());
        assertEquals("B", iterator0.next());
        assertEquals("C", iterator0.next());
        assertEquals("D", iterator0.next());
        assertFalse(iterator0.hasNext());

        assertThrows(NoSuchElementException.class, iterator0::next);
        iterator0.next();
        
        Iterator<String> iterator1 = iterable.iterator();
        assertEquals("A", iterator1.next());
        assertEquals("B", iterator1.next());
        assertEquals("C", iterator1.next());
        assertEquals("D", iterator1.next());
        assertFalse(iterator1.hasNext());
        
        assertThrows(NoSuchElementException.class, iterator1::next);
        iterator1.next();
        
    }
}
