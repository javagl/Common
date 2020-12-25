/*
 * www.javagl.de - Common
 *
 * Copyright (c) 2012-2020 Marco Hutter - http://www.javagl.de
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
package de.javagl.common.comparators;

import java.util.Comparator;

/**
 * A comparator for <code>double</code> values
 */
public interface DoubleComparator
{
    /**
     * Returns a {@link DoubleComparator} that orders values in ascending order
     * 
     * @return The comparator
     */
    public static DoubleComparator ascending()
    {
        return Double::compare;
    }
    
    /**
     * Returns a {@link DoubleComparator} that orders values in descending order
     * 
     * @return The comparator
     */
    public static DoubleComparator descending()
    {
        return (v0, v1) -> Double.compare(v1, v0);
    }
    
    /**
     * Returns a comparator with the same order as this one, but which
     * treats <code>NaN</code> as being smaller than every other value
     * 
     * @return The comparator
     */
    default DoubleComparator nanFirst()
    {
        return (v0, v1) -> 
        {
            if (Double.isNaN(v0))
            {
                if (Double.isNaN(v1))
                {
                    return 0;
                }
                return -1;
            }
            else if (Double.isNaN(v1))
            {
                return 1;
            }
            return compareDouble(v0, v1);
        };
    }
    
    /**
     * Returns a comparator with the same order as this one, but which
     * treats <code>NaN</code> as being larger than every other value
     * 
     * @return The comparator
     */
    default DoubleComparator nanLast()
    {
        return (v0, v1) -> 
        {
            if (Double.isNaN(v0))
            {
                if (Double.isNaN(v1))
                {
                    return 0;
                }
                return 1;
            }
            else if (Double.isNaN(v1))
            {
                return -1;
            }
            return compareDouble(v0, v1);
        };
    }
    
    /**
     * Creates a comparator for <code>Double</code> objects from this one
     * 
     * @return The resulting comparator
     */
    default Comparator<Double> boxed()
    {
        return (v0, v1) -> compareDouble(v0, v1);
    }
    
    
    /**
     * Returns the result of comparing the given values, as of 
     * <code>Double#compareDouble</code>
     * 
     * @param v0 The first value
     * @param v1 The second value
     * @return The comparison result
     */
    int compareDouble(double v0, double v1);
}
