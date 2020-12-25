/*
 * www.javagl.de - Common
 *
 * Copyright (c) 2012-2015 Marco Hutter - http://www.javagl.de
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
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Methods related to collections.<br>
 * <br>
 * These methods are not part of the main API, and may be removed or moved
 * to a different package or library in the future.
 */
public class CollectionsEx
{
    /**
     * Compute the indices of the elements from the given collection 
     * (in iteration order) that are contained in the given collection
     * of selected elements
     * 
     * @param <T> The element type
     * 
     * @param allElements All elements
     * @param selectedElements The selected elements
     * @return The indices
     */
    public static <T> List<Integer> computeIndicesInOrderOfAllElements(
        Collection<? extends T> allElements, 
        Collection<? extends T> selectedElements)
    {
        Set<? extends T> selectedElementsSet = asSet(selectedElements);
        List<Integer> indices = new ArrayList<Integer>();
        int n = allElements.size();
        Iterator<? extends T> iterator = allElements.iterator();
        for (int i = 0; i < n; i++)
        {
            T element = iterator.next();
            if (selectedElementsSet.contains(element))
            {
                indices.add(i);
            }
        }
        return indices;
    }
    
    /**
     * Compute the elements from the given collection whose indices (in 
     * iteration order) are contained in the given collection of indices 
     * 
     * @param <T> The element type
     * 
     * @param allElements All elements
     * @param selectedIndices The selected indices
     * @return The selected elements
     */
    public static <T> List<T> computeElementsInOrderOfAllElements(
        Collection<? extends T> allElements, 
        Collection<Integer> selectedIndices)
    {
        Set<Integer> selectedIndicesSet = asSet(selectedIndices);
        List<T> selectedElements = new ArrayList<T>();
        int n = allElements.size();
        Iterator<? extends T> iterator = allElements.iterator();
        for (int i = 0; i < n; i++)
        {
            T element = iterator.next();
            if (selectedIndicesSet.contains(i))
            {
                selectedElements.add(element);
            }
        }
        return selectedElements;
    }
    
    /**
     * Compute the indices of the elements from the given collection that 
     * are contained in the given selection, in the order in which they 
     * appear in the selection. Elements from the selection that do not 
     * appear in the collection of elements are ignored.  
     * 
     * @param <T> The element type
     * 
     * @param allElements All elements
     * @param selectedElements The selection
     * @return The indices
     * @throws IllegalArgumentException If the elements in the given
     * collection are not unique
     */
    public static <T> List<Integer> computeIndicesInOrderOfSelectedElements(
        Collection<? extends T> allElements, 
        Collection<? extends T> selectedElements)
    {
        List<Integer> indices = new ArrayList<Integer>();
        Map<? extends T, Integer> indexLookup = 
            createIndexLookupUnchecked(allElements);
        int n = selectedElements.size();
        Iterator<? extends T> iterator = selectedElements.iterator();
        for (int i = 0; i < n; i++)
        {
            T selectedElement = iterator.next();
            Integer index = indexLookup.get(selectedElement);
            if (index != null)
            {
                indices.add(index);
            }
        }
        return indices;
    }
    
    /**
     * Create a map for looking up the indices of the elements in the given
     * collection, in iteration order. If the collection contains duplicate 
     * elements, then the element will be mapped to the last index at which 
     * it appears.
     * 
     * @param <T> The element type
     * 
     * @param collection The collection
     * @return The lookup
     * @throws IllegalArgumentException If the collection contains duplicate 
     * elements
     */
    private static <T> Map<T, Integer> createIndexLookupUnchecked(
        Collection<? extends T> collection)
    {
        Map<T, Integer> map = new LinkedHashMap<T, Integer>();
        int n = collection.size();
        Iterator<? extends T> iterator = collection.iterator();
        for (int i = 0; i < n; i++)
        {
            T element = iterator.next();
            map.put(element, i);
        }
        return map;
    }
    
    /**
     * Computes the union of the given collections
     * 
     * @param <T> The element type
     * 
     * @param collections The collections
     * @return The union
     */
    public static <T> Set<T> computeUnion(
        Collection<? extends Collection<? extends T>> collections)
    {
        Set<T> result = new LinkedHashSet<T>();
        for (Collection<? extends T> collection : collections)
        {
            result.addAll(collection);
        }
        return result;
    }

    /**
     * Computes the union of the given collections
     * 
     * @param <T> The element type
     * 
     * @param collection0 The first collection
     * @param collection1 The second collection
     * @return The union
     */
    public static <T> Set<T> computeUnion(
        Collection<? extends T> collection0, 
        Collection<? extends T> collection1)
    {
        return computeUnion(Arrays.asList(collection0, collection1));
    }
    
    /**
     * Computes the intersection of the given collections. If there
     * is only one collection, then its contents will be returned.
     * 
     * @param <T> The element type
     * 
     * @param collections The collections
     * @return The intersection
     */
    public static <T> Set<T> computeIntersection(
        Collection<? extends Collection<? extends T>> collections)
    {
        Set<T> result = new LinkedHashSet<T>();
        if (collections.isEmpty())
        {
            return result; 
        }
        Iterator<? extends Collection<? extends T>> iterator = 
            collections.iterator();
        result.addAll(iterator.next());
        while (iterator.hasNext())
        {
            Collection<? extends T> collection = iterator.next();
            result.retainAll(asSet(collection));
        }
        return result;
    }
    
    /**
     * Computes the intersection of the given collections
     * 
     * @param <T> The element type
     * 
     * @param collection0 The first collection
     * @param collection1 The second collection
     * @return The intersection
     */
    public static <T> Set<T> computeIntersection(
        Collection<? extends T> collection0, 
        Collection<? extends T> collection1)
    {
        return computeIntersection(Arrays.asList(collection0, collection1));
    }
    
    
    /**
     * Computes the difference of the given collections. This is the 
     * result of removing the elements of all collections from the
     * first one.
     * 
     * @param <T> The element type
     * 
     * @param collections The collections
     * @return The difference
     */
    public static <T> Set<T> computeDifference(
        Collection<? extends Collection<? extends T>> collections)
    {
        Set<T> result = new LinkedHashSet<T>();
        if (collections.isEmpty())
        {
            return result; 
        }
        Iterator<? extends Collection<? extends T>> iterator = 
            collections.iterator();
        result.addAll(iterator.next());
        while (iterator.hasNext())
        {
            Collection<? extends T> collection = iterator.next();
            result.removeAll(asSet(collection));
        }
        return result;
    }
    
    /**
     * Computes the difference of the given collections
     * 
     * @param <T> The element type
     * 
     * @param collection0 The first collection
     * @param collection1 The second collection
     * @return The difference
     */
    public static <T> Set<T> computeDifference(
        Collection<? extends T> collection0, 
        Collection<? extends T> collection1)
    {
        return computeDifference(Arrays.asList(collection0, collection1));
    }
    
    /**
     * Returns whether any of the given collections is empty
     *  
     * @param collections The collections
     * @return Well, whether any of them is empty, what else?
     */
    public static boolean anyIsEmpty(
        Collection<? extends Collection<?>> collections)
    {
        return collections.stream().anyMatch(Collection::isEmpty);
    }
    
    /**
     * Returns whether the given collections are pairwise disjoint
     * 
     * @param collections The collections
     * @return Whether they are pairwise disjoint
     */
    public static boolean arePairwiseDisjoint(
        Collection<? extends Collection<?>> collections)
    {
        List<Collection<?>> list = new ArrayList<Collection<?>>(collections);
        for (int i = 0; i < list.size(); i++)
        {
            for (int j = i + 1; j < list.size(); j++)
            {
                Collection<?> collection0 = list.get(i);
                Collection<?> collection1 = list.get(j);
                if (!areDisjoint(collection0, collection1))
                {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Returns whether the given collections are disjoint
     * 
     * @param collection0 The first collection
     * @param collection1 The second collection
     * @return Whether the given collections are disjoint
     */
    public static boolean areDisjoint(
        Collection<?> collection0, Collection<?> collection1)
    {
        Set<Object> set0 = asSet(collection0);
        Set<Object> set1 = asSet(collection1);
        for (Object element0 : set0) 
        {
            if (set1.contains(element0))
            {
                return false;
            }
        }
        for (Object element1 : set1) 
        {
            if (set0.contains(element1))
            {
                return false;
            }
        }
        return false;
    }
    
    /**
     * If the given collection is a set, then it will be cast to 
     * <code>Set</code> and be returned. Otherwise, a set will
     * be created from the given collection.  
     * 
     * @param <T> The element type
     * 
     * @param collection The collection
     * @return The collection as a set
     */
    private static <T> Set<T> asSet(Collection<? extends T> collection)
    {
        if (collection instanceof Set)
        {
            @SuppressWarnings("unchecked")
            Set<T> set = (Set<T>) collection;
            return set;
        }
        return new LinkedHashSet<T>(collection);
    }
    
    /**
     * Private constructor to prevent instantiation
     */
    private CollectionsEx()
    {
        // Private constructor to prevent instantiation
    }
}
