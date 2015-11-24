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
public class TestIteratorsOver
{
    @Test
    public void testIteratorOverTwoIterables()
    {
        List<String> iterable0 = Arrays.asList("A", "B");
        List<String> iterable1 = Arrays.asList("C", "D");
        
        Iterator<String> iterator = 
            Iterators.iteratorOverIterables(
                iterable0, iterable1);
        List<String> actual = Iterators.toList(iterator);
        
        List<String> expected = Arrays.asList("A", "B", "C", "D");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testIteratorOverIterablesViaIterable()
    {
        List<String> iterable0 = Arrays.asList("A", "B");
        List<String> iterable1 = Arrays.asList();
        List<String> iterable2 = Arrays.asList("C", "D");
        Iterable<? extends Iterable<String>> iterablesIterable = 
            Arrays.asList(iterable0, iterable1, iterable2);
        
        Iterator<String> iterator = 
            Iterators.iteratorOverIterables(iterablesIterable);
        List<String> actual = Iterators.toList(iterator);
        
        List<String> expected = Arrays.asList("A", "B", "C", "D");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testIteratorOverIterablesViaIterator()
    {
        List<String> iterable0 = Arrays.asList("A", "B");
        List<String> iterable1 = Arrays.asList();
        List<String> iterable2 = Arrays.asList("C", "D");
        Iterator<? extends Iterable<String>> iterablesIterator = 
            Arrays.asList(iterable0, iterable1, iterable2).iterator();
        
        Iterator<String> iterator = 
            Iterators.iteratorOverIterables(iterablesIterator);
        List<String> actual = Iterators.toList(iterator);
        
        List<String> expected = Arrays.asList("A", "B", "C", "D");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testIteratorOverTwoIterators()
    {
        List<String> iterable0 = Arrays.asList("A", "B");
        List<String> iterable1 = Arrays.asList("C", "D");
        
        Iterator<String> iterator = 
            Iterators.iteratorOverIterators(
                iterable0.iterator(), iterable1.iterator());
        List<String> actual = Iterators.toList(iterator);
        
        List<String> expected = Arrays.asList("A", "B", "C", "D");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testIteratorOverIteratorsViaIterable()
    {
        List<String> iterable0 = Arrays.asList("A", "B");
        List<String> iterable1 = Arrays.asList();
        List<String> iterable2 = Arrays.asList("C", "D");
        Iterable<? extends Iterator<String>> iteratorsIterable = 
            Arrays.asList(
                iterable0.iterator(), 
                iterable1.iterator(),
                iterable2.iterator());
        
        Iterator<String> iterator = 
            Iterators.iteratorOverIterators(iteratorsIterable);
        List<String> actual = Iterators.toList(iterator);
        
        List<String> expected = Arrays.asList("A", "B", "C", "D");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testIteratorOverIteratorsViaIterator()
    {
        List<String> iterable0 = Arrays.asList("A", "B");
        List<String> iterable1 = Arrays.asList();
        List<String> iterable2 = Arrays.asList("C", "D");
        Iterator<? extends Iterator<String>> iteratorsIterator = 
            Arrays.asList(
                iterable0.iterator(), 
                iterable1.iterator(),
                iterable2.iterator()).iterator();
        
        Iterator<String> iterator = 
            Iterators.iteratorOverIterators(iteratorsIterator);
        List<String> actual = Iterators.toList(iterator);
        
        List<String> expected = Arrays.asList("A", "B", "C", "D");
        assertEquals(expected, actual);
    }
    
    @Rule public ExpectedException thrown= ExpectedException.none();    
    @Test
    public void testIteratorOverIteratorsExactCalls()
    {
        List<String> iterable0 = Arrays.asList("A", "B");
        List<String> iterable1 = Arrays.asList();
        List<String> iterable2 = Arrays.asList("C", "D");
        List<String> iterable3 = Arrays.asList();
        
        Iterator<? extends Iterator<String>> iteratorsIterator = 
            Arrays.asList(
                iterable0.iterator(), 
                iterable1.iterator(),
                iterable2.iterator(),
                iterable3.iterator()).iterator();
        
        Iterator<String> iterator = 
            Iterators.iteratorOverIterators(iteratorsIterator);
        
        assertEquals("A", iterator.next());
        assertEquals("B", iterator.next());
        assertEquals("C", iterator.next());
        assertEquals("D", iterator.next());
        assertFalse(iterator.hasNext());
        
        thrown.expect(NoSuchElementException.class);
        iterator.next();
    }
    
    
}
