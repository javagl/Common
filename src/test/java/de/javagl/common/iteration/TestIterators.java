/*
 * www.javagl.de - Common
 *
 * Copyright (c) 2012-2015 Marco Hutter - http://www.javagl.de
 */
package de.javagl.common.iteration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


@SuppressWarnings("javadoc")
public class TestIterators
{
    @Test
    public void testTransforming()
    {
        List<String> iterable = Arrays.asList("0", "1", "2");
        Iterator<String> iterator = iterable.iterator();
        
        Iterator<Integer> transformingIterator =
            Iterators.transformingIterator(iterator, 
                s -> Integer.parseInt(s));
        
        List<Integer> expected = Arrays.asList(0,1,2);
        List<Integer> actual = Iterators.toList(transformingIterator);
        assertEquals(expected, actual);
    }
    
    @Rule public ExpectedException thrown= ExpectedException.none();    
    @Test
    public void testFiltering()
    {
        List<String> iterable = Arrays.asList("0", "1", "2");
        Iterator<String> iterator = iterable.iterator();
        
        Iterator<String> filteringIterator =
            Iterators.filteringIterator(iterator, 
                s -> !s.equals("1"));
        
        assertEquals(filteringIterator.next(), "0");
        assertEquals(filteringIterator.next(), "2");
        assertFalse(filteringIterator.hasNext());
        
        thrown.expect(NoSuchElementException.class);
        iterator.next();
    }
    
    @Test
    public void testFilteringAll()
    {
        List<String> iterable = Arrays.asList("0", "1", "2");
        Iterator<String> iterator = iterable.iterator();
        
        Iterator<String> filteringIterator =
            Iterators.filteringIterator(iterator, 
                s -> false);
        
        assertFalse(filteringIterator.hasNext());
        thrown.expect(NoSuchElementException.class);
        iterator.next();
    }
    
}
