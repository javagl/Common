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

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.RandomAccess;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;

import de.javagl.common.functional.TriFunction;

/**
 * Methods related to lists.<br>
 * <br>
 * These methods are not part of the main API, and may be removed or moved
 * to a different package or library in the future.
 */
public class Lists
{
    /**
     * A class that extends <code>AbstractList</code> and adds the 
     * <code>RandomAccess</code> tagging interface
     *
     * @param <T> The element type
     */
    private static abstract class AbstractRandomAccessList<T> 
        extends AbstractList<T> implements RandomAccess
    {
        // No additional methods
    }
    
    /**
     * Utility method that returns an unmodifiable list with the given
     * contents
     * 
     * @param <T> The type of the elements
     * 
     * @param input The input
     * @return The result
     */
    public static <T> List<T> unmodifiableListWith(
        Collection<? extends T> input)
    {
        if (input.isEmpty())
        {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(new ArrayList<T>(input));
    }
    
    /**
     * Returns a list that is an unmodifiable <i>view</i> on the given array
     * 
     * @param array The array
     * @return The list
     */
    public static List<Integer> fromArray(int ... array)
    {
        Objects.requireNonNull(array, "The array may not be null");
        class ResultList extends AbstractList<Integer> implements RandomAccess
        {
            @Override
            public Integer get(int index)
            {
                return array[index];
            }
            
            @Override
            public int size()
            {
                return array.length;
            }
        };
        return new ResultList();
    }
    
    /**
     * Create a <i>view</i> on the given list that converts the values with
     * the given function.<br>
     * <br>
     * The resulting list will implement the <code>RandomAccess</code> 
     * interface if the given list implements it.
     * 
     * @param <T> The input type
     * @param <U> The result type
     * 
     * @param list The list
     * @param function The function
     * @return The view
     */
    public static <T, U> List<U> createView(
        List<? extends T> list, Function<? super T, ? extends U> function)
    {
        Objects.requireNonNull(list, "The list may not be null");
        Objects.requireNonNull(function, "The function may not be null");
        AbstractList<U> view = new AbstractList<U>()
        {
            @Override
            public U get(int index)
            {
                T t = list.get(index);
                U u = function.apply(t);
                return u;
            }

            @Override
            public int size()
            {
                return list.size();
            }
        };
        if (list instanceof RandomAccess)
        {
            return withRandomAccess(view); 
        }
        return view;
    }
    
    /**
     * Creates a list that is a <i>view</i> on the result of applying the
     * given function to the elements of the given lists.<br>
     * <br>
     * The resulting list will implement the <code>RandomAccess</code> 
     * interface if the given lists implement it.
     * 
     * @param <A> The first element type
     * @param <B> The second element type
     * @param <C> The result type
     * 
     * @param list0 The first list
     * @param list1 The second list
     * @param function The function to combine the elements
     * @return The resulting list
     * @throws IllegalArgumentException If the given lists have different
     * sizes
     */
    public static <A, B, C> List<C> createView(
        List<? extends A> list0, List<? extends B> list1, 
        BiFunction<? super A, ? super B, ? extends C> function)
    {
        Objects.requireNonNull(list0, "The list0 may not be null");
        Objects.requireNonNull(list1, "The list1 may not be null");
        Objects.requireNonNull(function, "The function may not be null");
        
        if (list0.size() != list1.size())
        {
            throw new IllegalArgumentException(
                "The sizes must be equal, but are " + list0.size() 
                + " and " + list1.size());
        }
        AbstractList<C> view = new AbstractList<C>()
        {
            @Override
            public C get(int index)
            {
                A t = list0.get(index);
                B u = list1.get(index);
                C v = function.apply(t, u);
                return v;
            }

            @Override
            public int size()
            {
                return list0.size();
            }
        };
        if (list0 instanceof RandomAccess
            && list1 instanceof RandomAccess)
        {
            return withRandomAccess(view); 
        }
        return view;
    }
    
    /**
     * Creates a list that is a <i>view</i> on the result of applying the
     * given function to the elements of the given lists.<br>
     * <br>
     * The resulting list will implement the <code>RandomAccess</code> 
     * interface if the given lists implement it.
     * 
     * @param <A> The first element type
     * @param <B> The second element type
     * @param <C> The third element type
     * @param <D> The result type
     * 
     * @param list0 The first list
     * @param list1 The second list
     * @param list2 The third list
     * @param function The function to combine the elements
     * @return The resulting list
     * @throws IllegalArgumentException If the given lists have different
     * sizes
     */
    public static <A, B, C, D> List<D> createView(
        List<A> list0, List<B> list1, List<C> list2, 
        TriFunction<? super A, ? super B, ? super C, ? extends D> function)
    {
        Objects.requireNonNull(list0, "The list0 may not be null");
        Objects.requireNonNull(list1, "The list1 may not be null");
        Objects.requireNonNull(list2, "The list2 may not be null");
        Objects.requireNonNull(function, "The function may not be null");
        
        if (list0.size() != list1.size() || list1.size() != list2.size())
        {
            throw new IllegalArgumentException(
                "The sizes must be equal, but are " + list0.size() 
                + ", " + list1.size() + ", and " + list2.size());
        }
        AbstractList<D> view = new AbstractList<D>()
        {
            @Override
            public D get(int index)
            {
                A a = list0.get(index);
                B b = list1.get(index);
                C c = list2.get(index);
                D d = function.apply(a, b, c);
                return d;
            }

            @Override
            public int size()
            {
                return list0.size();
            }
        };
        if (list0 instanceof RandomAccess 
            && list1 instanceof RandomAccess 
            && list2 instanceof RandomAccess)
        {
            return withRandomAccess(view); 
        }
        return view;
        
    }
    
    /**
     * Create a <i>view</i> on the given function, as a list with the 
     * given size.
     * 
     * @param <T> The element type
     * 
     * @param size The size
     * @param function The function
     * @return The view
     */
    public static <T> List<T> createView(int size,
        IntFunction<? extends T> function)
    {
        validateSize(size);
        return createView(0, size, function);
    }
    
    /**
     * Returns a <i>view</i> on the given function, as a list that contains
     * the elements <code>f(minIndexInclusive)</code> to 
     * <code>f(maxIndexExclusive-1)</code>
     * 
     * @param <T> The element type
     * 
     * @param minIndexInclusive The minimum index, inclusive
     * @param maxIndexExclusive The maximum index, exclusive
     * @param f The function
     * @return The list
     * @throws IllegalArgumentException If the given minimum index is larger
     * than the maximum index
     */
    public static <T> List<T> createView( 
        int minIndexInclusive, int maxIndexExclusive, 
        IntFunction<? extends T> f)
    {
        Objects.requireNonNull(f, "The function may not be null");
        if (minIndexInclusive > maxIndexExclusive) 
        {
            throw new IllegalArgumentException(
                "The minimum index is " + minIndexInclusive
                + " but may not be larger than the maximum index, "
                + "which is " + maxIndexExclusive);
        }
        return new AbstractRandomAccessList<T>()
        {
            @Override
            public T get(int index)
            {
                validateIndex(index, size());
                return f.apply(index);
            }

            @Override
            public int size()
            {
                return maxIndexExclusive - minIndexInclusive;
            }
        };
    }
    
    /**
     * Returns a new list that wraps the given one, adding the 
     * <code>RandomAccess</code> tagging interface.<br>
     *  
     * @param <T> The element type
     * 
     * @param list The list
     * @return The resulting list
     */
    public static <T> List<T> withRandomAccess(List<T> list) 
    {
        Objects.requireNonNull(list, "The list may not be null");
        return new AbstractRandomAccessList<T>()
        {
            @Override
            public int size()
            {
                return list.size();
            }

            @Override
            public T get(int index)
            {
                return list.get(index);
            }

            @Override
            public T set(int index, T element)
            {
                return list.set(index, element);
            }
            
            @Override
            public void add(int index, T element)
            {
                list.add(index, element);
            }
            
            @Override
            public T remove(int index)
            {
                return list.remove(index);
            }
            
        };
    }
    
    /**
     * Returns an unmodifiable list with the given size that contains the 
     * values <code>offset + i * stepSize</code>
     * 
     * @param offset The offset
     * @param stepSize The step size
     * @param size The size
     * @return The list
     * @throws IllegalArgumentException If the size is negative
     */
    public static List<Integer> steps(int offset, int stepSize, int size) 
    {
        validateSize(size);
        return new AbstractRandomAccessList<Integer>()
        {

            @Override
            public Integer get(int index)
            {
                validateIndex(index, size());
                return offset + stepSize * index;
            }

            @Override
            public int size()
            {
                return size;
            }
        };
    }
    
    /**
     * Returns an unmodifiable list with the given size that contains the 
     * values <code>offset + i * stepSize</code>
     * 
     * @param offset The offset
     * @param stepSize The step size
     * @param size The size
     * @return The list
     * @throws IllegalArgumentException If the size is negative
     */
    public static List<Long> steps(long offset, long stepSize, int size) 
    {
        validateSize(size);
        return new AbstractRandomAccessList<Long>()
        {

            @Override
            public Long get(int index)
            {
                validateIndex(index, size());
                return offset + stepSize * index;
            }

            @Override
            public int size()
            {
                return size;
            }
        };
    }
    
    /**
     * Returns an unmodifiable list with the given size that contains the 
     * values <code>offset + i * stepSize</code>
     * 
     * @param offset The offset
     * @param stepSize The step size
     * @param size The size
     * @return The list
     * @throws IllegalArgumentException If the size is negative
     */
    public static List<Double> steps(double offset, double stepSize, int size) 
    {
        validateSize(size);
        return new AbstractRandomAccessList<Double>()
        {

            @Override
            public Double get(int index)
            {
                validateIndex(index, size());
                return offset + stepSize * index;
            }

            @Override
            public int size()
            {
                return size;
            }
        };
    }
    
    /**
     * Creates an unmodifiable list that contains consecutive integers
     * in the given range
     * 
     * @param min The minimum value, inclusive
     * @param max The maximum value, exclusive
     * @return The list
     * @throws IllegalArgumentException If the minimum is larger than the
     * maximum
     */
    public static List<Integer> fromRange(int min, int max)
    {
        if (min > max)
        {
            throw new IllegalArgumentException("The minimum (" + min
                + ") is greater than the maximum (" + max + ")");
        }
        return steps(min, 1, max - min);
    }

    /**
     * Creates an unmodifiable list that contains consecutive long values
     * in the given range
     * 
     * @param min The minimum value, inclusive
     * @param max The maximum value, exclusive
     * @return The list
     * @throws IllegalArgumentException If the minimum is larger than the
     * maximum
     */
    public static List<Long> fromRange(long min, long max)
    {
        if (min > max)
        {
            throw new IllegalArgumentException("The minimum (" + min
                + ") is greater than the maximum (" + max + ")");
        }
        return steps(min, 1L, (int)(max - min));
    }

    /**
     * Returns a new list that is a <i>view</i> on the given list, padded
     * with the specified number of elements. The values of these elements
     * will be the value of the first element of the list on the left, and
     * the value of the last element of the list on the right.<br>
     * <br>
     * The resulting list will implement the <code>RandomAccess</code> 
     * interface if the given list implements it.
     * 
     * @param <T> The element type
     * 
     * @param t The input list
     * @param padLeft The number of padding elements at the left
     * @param padRight The number of padding elements at the right
     * @return The padded list
     * @throws IllegalArgumentException If either of the given arguments 
     * is negative, or if the list is empty
     */
    static <T> List<T> pad(List<T> t, int padLeft, int padRight)
    {
        if (t.isEmpty())
        {
            throw new IllegalArgumentException("The list is empty");
        }
        T padLeftValue = t.get(0);
        T padRightValue = t.get(t.size() - 1);
        return pad(t, padLeft, padLeftValue, padRight, padRightValue);
    }
    
    /**
     * Returns a new list that is a <i>view</i> on the given list, padded
     * with the specified number of elements. <br>
     * <br>
     * The resulting list will implement the <code>RandomAccess</code> 
     * interface if the given list implements it.
     * 
     * @param <T> The element type
     * 
     * @param t The input list
     * @param padLeft The number of padding elements at the left
     * @param padLeftValue The value for the left padding elements
     * @param padRight The number of padding elements at the right
     * @param padRightValue The value for the right padding elements
     * @return The padded list
     * @throws IllegalArgumentException If either of the given arguments 
     * is negative
     */
    public static <T> List<T> pad(List<? extends T> t, 
        int padLeft, T padLeftValue, 
        int padRight, T padRightValue)
    {
        if (padLeft < 0)
        {
            throw new IllegalArgumentException(
                "The padLeft value may not be negative but is " + padLeft);
        }
        if (padRight < 0)
        {
            throw new IllegalArgumentException(
                "The padRight value may not be negative but is " + padRight);
        }
        AbstractList<T> view = new AbstractList<T>()
        {
            @Override
            public int size()
            {
                return t.size() + padLeft + padRight;
            }
            
            @Override
            public T get(int index)
            {
                validateIndex(index, size());
                if (index < padLeft)
                {
                    return padLeftValue;
                }
                if (index >= t.size() + padLeft)
                {
                    return padRightValue;
                }
                return t.get(index - padLeft);
            }
        };
        if (t instanceof RandomAccess)
        {
            return withRandomAccess(view); 
        }
        return view;
    }
    
    /**
     * Create a <i>view</i> on the given list that contains only the elements
     * that are indicated by the given indices.<br>
     * <br>
     * The returned list will me modifiable if and only if the given 
     * parent is modifiable. The given list may not be modified after
     * it has been passed to this method.
     * 
     * @param <T> The element type of the list
     * 
     * @param parent The parent list
     * @param indices The indices
     * @return The view
     * @throws NullPointerException If any argument is <code>null</code>
     */
    public static <T> List<T> createIndexView(
        List<T> parent, List<Integer> indices)
    {
        return createIndexView(parent, indices::get, indices.size());
    }
    
    /**
     * Create a <i>view</i> on the given list where each index will be
     * passed through the given lookup function in order to access the
     * corresponding element in the given parent list. The caller is 
     * responsible for making sure that the lookup works properly 
     * (i.e. it does not throw an exception for any argument).<br>
     * <br>
     * The returned list will me modifiable if and only if the given 
     * parent is modifiable.
     * 
     * @param <T> The element type of the list
     * 
     * @param parent The parent list
     * @param indexLookup The lookup
     * @param size The size of the resulting list
     * @return The view
     * @throws NullPointerException If any argument is <code>null</code>
     */
    public static <T> List<T> createIndexView(
        List<T> parent, IntUnaryOperator indexLookup, int size)
    {
        return new IndexViewList<T>(parent, indexLookup, size);
    }
    
    /**
     * Returns a list that consists of the elements of the given parent 
     * that have the given indices.<br>
     * <br>
     * The caller is responsible to make sure that the given parameters
     * are "consistent", meaning that the indices are valid for the given
     * parent, and the indices and the parent are not modified in a way
     * that violates this constraint.<br>
     * <br>
     * The returned list will me modifiable if and only if the given 
     * parent is modifiable.
     * 
     * @param <T> The type of the elements
     * 
     * @param parent The parent list
     * @param indices The indices
     * @return The resulting list
     * @throws NullPointerException If any argument is <code>null</code>
     */
    static <T> List<T> createIndexView(List<T> parent, int indices[])
    {
        Objects.requireNonNull(indices, "The indices may not be null");
        return createIndexView(parent, i -> indices[i], indices.length);
    }
    
    /**
     * Create a new list that is an unmodifiable <i>view</i> on the 
     * concatenation of the given lists.
     * 
     * @param <T> The type of the elements
     * 
     * @param list0 The first list
     * @param list1 The second list
     * @return The concatenated view
     */
    public static <T> List<T> concat(
        List<? extends T> list0, List<? extends T> list1)
    {
        Objects.requireNonNull(list0, "The list0 may not be null");
        Objects.requireNonNull(list1, "The list1 may not be null");
        if (list0 instanceof RandomAccess && list1 instanceof RandomAccess)
        {
            return concatRandomAccess(list0, list1);
        }
        return concatDefault(list0, list1);
        
    }
    
    /**
     * Create a new list that is an unmodifiable <i>view</i> on the 
     * concatenation of the given lists, with random access.
     * 
     * @param <T> The type of the elements
     * 
     * @param list0 The first list
     * @param list1 The second list
     * @return The concatenated view
     */
    private static <T> List<T> concatRandomAccess(
        List<? extends T> list0, List<? extends T> list1)
    {
        class ResultList extends AbstractList<T> implements RandomAccess
        {
            @Override
            public T get(int index)
            {
                if (index < list0.size())
                {
                    return list0.get(index);
                }
                return list1.get(index - list0.size());
            }
            
            @Override
            public int size()
            {
                return list0.size() + list1.size();
            }
        };
        return new ResultList();
    }
    
    /**
     * Create a new list that is an unmodifiable <i>view</i> on the 
     * concatenation of the given lists, without random access.
     * 
     * @param <T> The type of the elements
     * 
     * @param list0 The first list
     * @param list1 The second list
     * @return The concatenated view
     */
    private static <T> List<T> concatDefault(
        List<? extends T> list0, List<? extends T> list1)
    {
        class ResultList extends AbstractList<T>
        {
            @Override
            public T get(int index)
            {
                if (index < list0.size())
                {
                    return list0.get(index);
                }
                return list1.get(index - list0.size());
            }
            
            @Override
            public int size()
            {
                return list0.size() + list1.size();
            }
        };
        return new ResultList();
    }
    
    /**
     * Make sure that the given size is not negative, and throw an
     * <code>IllegalArgumentException</code> otherwise
     * 
     * @param size The size
     * @throws IllegalArgumentException If the size is negative
     */
    public static void validateSize(int size)
    {
        if (size < 0)
        {
            throw new IllegalArgumentException(
                "The size may not be negative, but is " + size);
        }
    }
    
    /**
     * Make sure that the given index is valid for a list with the given
     * size, and throw an <code>IndexOutOfBoundsException</code> if this 
     * is not the case.
     * 
     * @param index The index
     * @param size The size
     * @throws IndexOutOfBoundsException If the index is negative or not
     * smaller than the size
     */
    public static void validateIndex(int index, int size)
    {
        if (index < 0 || index >= size)
        {
            throw new IndexOutOfBoundsException(
                "The index may not be negative and must be smaller than " 
                + size + ", but is " + index);
        }
    }
    
    /**
     * Private constructor to prevent instantiation
     */
    private Lists()
    {
        // Private constructor to prevent instantiation
    }
    
}
