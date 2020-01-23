/*
 * www.javagl.de - Common
 *
 * Copyright (c) 2012-2014 Marco Hutter - http://www.javagl.de
 * 
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */
package de.javagl.common.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

import de.javagl.common.util.Comparators;

/**
 * Utility methods related to maps
 */
public class Maps
{
    /**
     * Create a map that is an index lookup for the given list. If the list
     * contains duplicate elements, then the element will be mapped to the
     * last index at which it appears.
     * 
     * @param <T> The element type
     * 
     * @param list The list
     * @return The map
     */
    public static <T> Map<T, Integer> createIndexLookupUnchecked(
        List<? extends T> list)
    {
        Map<T, Integer> map = new LinkedHashMap<T, Integer>();
        for (int i = 0; i < list.size(); i++)
        {
            T element = list.get(i);
            map.put(element, i);
        }
        return map;
    }
    
    /**
     * Create a map for looking up the indices of the elements in the given
     * list.
     * 
     * @param <T> The element type
     * 
     * @param list The list
     * @return The lookup
     * @throws IllegalArgumentException If the list contains duplicate elements
     */
    public static <T> Map<T, Integer> createIndexLookup(
        List<? extends T> list)
    {
        Map<T, Integer> map = new LinkedHashMap<T, Integer>();
        for (int i = 0; i < list.size(); i++)
        {
            T t = list.get(i);
            Integer previous = map.put(t, i);
            if (previous != null)
            {
                throw new IllegalArgumentException("Duplicate element " + t
                    + " at " + i + " and " + previous);
            }
        }
        return map;
    }
    
    /**
     * Combine the given maps, by merging their key sets to obtain the key
     * set of the result list, and collecting all values from the given
     * input maps into lists.
     * 
     * @param <K> The key type
     * @param <V> The value type
     * 
     * @param maps The input maps
     * @return The resulting map
     */
    public static <K, V> Map<K, List<V>> combine(
        Collection<? extends Map<? extends K, ? extends V>> maps)
    {
        Map<K, List<V>> result = new LinkedHashMap<K, List<V>>();
        for (Map<? extends K, ? extends V> map : maps)
        {
            for (Entry<? extends K, ? extends V> entry : map.entrySet())
            {
                K k = entry.getKey();
                V v = entry.getValue();
                addToList(result, k, v);
            }
        }
        return result;
    }
    
    /**
     * Sorts the given map by the double value of the values
     * 
     * @param <K> The key type
     * @param <V> The value type
     * 
     * @param map The input map
     * @param ascending Whether the map should be sorted ascendingly
     * @return The sorted map
     */
    public static <K, V extends Number> Map<K, V> sortByNumberValue(
        Map<K, V> map, boolean ascending)
    {
        List<Entry<K, V>> entries = new ArrayList<Entry<K, V>>(map.entrySet());
        
        Comparator<Number> valueComparator = Comparators.forNumbers();
        if (!ascending)
        {
            valueComparator = valueComparator.reversed();
        }
        Comparator<Entry<K, V>> comparator = 
            Entry.comparingByValue(valueComparator);
        Collections.sort(entries, comparator);
        return fromEntries(entries);
    }
    
    /**
     * Creates a new map from the given sequence of entries
     * 
     * @param <K> The key type
     * @param <V> The value type
     * 
     * @param entries The entries
     * @return The map
     */
    public static <K, V> Map<K, V> fromEntries(
        Iterable<? extends Entry<? extends K, ? extends V>> entries)
    {
        Map<K, V> result = new LinkedHashMap<K, V>();
        for (Entry<? extends K, ? extends V> entry : entries)
        {
            K k = entry.getKey();
            V v = entry.getValue();
            result.put(k, v);
        }
        return result;
    }
    
    /**
     * Transform the keys and values of the given map with the given 
     * functions, and return the result as a new map. If the given
     * key transform functions maps multiple input keys to the same
     * output key, then the output key will be mapped to the last
     * output value that was computed.
     * 
     * @param <KI> The input key type
     * @param <VI> The input value type
     * @param <KO> The output key type
     * @param <VO> The output value type
     * 
     * @param map The input map
     * @param keyTransform The key transform
     * @param valueTransform The value transform
     * @return The resulting map
     */
    public static <KI, VI, KO, VO> Map<KO, VO> transform(
        Map<KI, VI> map, 
        Function<? super KI, ? extends KO> keyTransform,
        Function<? super VI, ? extends VO> valueTransform)
    {
        Map<KO, VO> result = new LinkedHashMap<KO, VO>();
        for (Entry<KI, VI> entry : map.entrySet())
        {
            KI ki = entry.getKey();
            VI vi = entry.getValue();
            KO ko = keyTransform.apply(ki);
            VO vo = valueTransform.apply(vi);
            result.put(ko, vo);
        }
        return result;
    }
    
    
    /**
     * Increments the value that is stored for the given key in the given
     * map by one, or sets it to 1 if there was no value stored for the
     * given key.
     * 
     * @param <K> The key type
     * @param map The map 
     * @param k The key
     */
    public static <K> void incrementCount(Map<K, Integer> map, K k)
    {
        map.put(k, getCount(map, k)+1);
    }

    /**
     * Returns the value that is stored for the given key in the given map.
     * If there is no value stored, then 0 will be inserted into the map
     * and returned
     * 
     * @param <K> The key type
     * @param map The map
     * @param k The key
     * @return The value 
     */
    public static <K> Integer getCount(Map<K, Integer> map, K k)
    {
        Integer count = map.get(k);
        if (count == null)
        {
            count = 0;
            map.put(k, count);
        }
        return count;
    }
    
    /**
     * Returns the list that is stored under the given key in the given map.
     * If there is no list stored, then a new list will be created, inserted
     * and returned
     * 
     * @param <K> The key type
     * @param <E> The element type
     * @param map The map
     * @param k The key
     * @return The list
     */
    static <K, E> List<E> getList(Map<K, List<E>> map, K k)
    {
        List<E> list = map.get(k);
        if (list == null)
        {
            list = new ArrayList<E>();
            map.put(k, list);
        }
        return list;
    }
    
    
    /**
     * Adds the given element to the list that is stored under the
     * given key. If the list does not yet exist, it is created and
     * inserted into the map.
     * 
     * @param <K> The key type
     * @param <E> The element type
     * @param map The map
     * @param k The key
     * @param e The element
     */
    public static <K, E> void addToList(Map<K, List<E>> map, K k, E e)
    {
        getList(map, k).add(e);
    }

    /**
     * Removes the given element from the list that is stored under the
     * given key. If the list becomes empty, it is removed from the
     * map.
     * 
     * 
     * @param <K> The key type
     * @param <E> The element type
     * @param map The map
     * @param k The key
     * @param e The element
     */
    static <K, E> void removeFromList(Map<K, List<E>> map, K k, E e)
    {
        List<E> list = map.get(k);
        if (list != null)
        {
            list.remove(e);
            if (list.isEmpty())
            {
                map.remove(k);
            }
        }
    }
    
    /**
     * Fill the given map sequentially with the values from the given 
     * sequence. This method will iterate over all keys of the given map, 
     * and put the corresponding value element into the map.
     * If the map is larger than the sequence, then the remaining entries 
     * will remain unaffected.
     * 
     * @param <K> The key type
     * @param <V> The value type
     * @param map The map to fill
     * @param values The values
     */
    public static <K, V> void fillValues(Map<K, V> map, 
        Iterable<? extends V> values)
    {
        Iterator<? extends V> iterator = values.iterator();
        for (K k : map.keySet())
        {
            if (!iterator.hasNext())
            {
                break;
            }
            V value = iterator.next();
            map.put(k, value);
        }
    }
    
    
    
    /**
     * Returns the inverse of the given map. If multiple keys are mapped
     * to the same value in the given input map, then the value will be
     * mapped to the last key (in iteration order) for which it is found.
     * 
     * @param <K> The key type
     * @param <V> The value type
     * @param map The map
     * @return The inverse map
     */
    static <K, V> Map<V, K> invert(Map<K, V> map)
    {
        Map<V, K> result = new LinkedHashMap<V, K>();
        for (Entry<K, V> entry : map.entrySet())
        {
            result.put(entry.getValue(), entry.getKey());
        }
        return result;
    }
    
    /**
     * Creates a map that maps consecutive integer values to the 
     * corresponding elements in the given array.
     * 
     * @param <T> The value type
     * @param elements The elements
     * @return The map
     */
    @SafeVarargs
    public static <T> Map<Integer, T> fromElements(T ... elements)
    {
        return fromIterable(Arrays.asList(elements));
    }
    
    
    /**
     * Creates a map that maps consecutive integer values to the elements 
     * in the given sequence, in the order in which they appear.
     * 
     * @param <T> The value type
     * @param iterable The sequence
     * @return The map
     */
    public static <T> Map<Integer, T> fromIterable(
        Iterable<? extends T> iterable)
    {
        Map<Integer, T> map = new LinkedHashMap<Integer, T>();
        int i = 0;
        for (T t : iterable)
        {
            map.put(i, t);
            i++;
        }
        return map;
    }
    
    /**
     * Creates a map that maps the elements of the first sequence to
     * the corresponding elements in the second sequence. If the
     * given sequences have different lengths, then only the elements
     * of the shorter sequence will be contained in the map. 
     * 
     * @param <K> The key type
     * @param <V> The value type
     * @param iterable0 The first sequence
     * @param iterable1 The second sequence
     * @return The map
     */
    public static <K, V> Map<K, V> fromIterables(
        Iterable<? extends K> iterable0, 
        Iterable<? extends V> iterable1)
    {
        Map<K, V> map = new LinkedHashMap<K, V>();
        Iterator<? extends K> i0 = iterable0.iterator();
        Iterator<? extends V> i1 = iterable1.iterator();
        while (i0.hasNext() && i1.hasNext())
        {
            K k = i0.next();
            V v = i1.next();
            map.put(k, v);
        }
        return map;
    }
    
    /**
     * Creates an unmodifiable copy of the given map
     *  
     * @param <K> The key type
     * @param <V> The value type
     * @param map The input map
     * @return The resulting map
     */
    public static <K, V> Map<K, V> unmodifiableCopy(
        Map<? extends K, ? extends V> map)
    {
        return Collections.<K, V>unmodifiableMap(
            new LinkedHashMap<K, V>(map));
    }
    
    
    /**
     * Private constructor to prevent instantiation
     */
    private Maps()
    {
        // Private constructor to prevent instantiation
    }
}
