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

import java.util.Collection;

/**
 * Methods related to collections of <code>Number</code> instances
 */
public class NumberCollections
{
    /**
     * Returns an array containing the double values of the given numbers
     * 
     * @param numbers The numbers
     * @return The array
     */
    public static double[] toDoubleArray(Collection<? extends Number> numbers)
    {
        return numbers.stream().mapToDouble(Number::doubleValue).toArray();
    }
    
    /**
     * Computes the minimum of the double values of the numbers in the
     * given collection.<br>
     * <br>
     * Returns <code>POSITIVE_INFINITY</code> when the collection is empty.
     * 
     * @param numbers The numbers
     * @return The minimum value
     */
    public static double min(Collection<? extends Number> numbers)
    {
        if (numbers.isEmpty())
        {
            return Double.POSITIVE_INFINITY;
        }
        return numbers.stream()
            .mapToDouble(Number::doubleValue)
            .min()
            .getAsDouble();
    }

    /**
     * Computes the maximum of the double values of the numbers in the
     * given collection.<br>
     * <br>
     * Returns <code>NEGATIVE_INFINITY</code> when the collection is empty.
     * 
     * @param numbers The numbers
     * @return The minimum value
     */
    public static double max(Collection<? extends Number> numbers)
    {
        if (numbers.isEmpty())
        {
            return Double.NEGATIVE_INFINITY;
        }
        return numbers.stream()
            .mapToDouble(Number::doubleValue)
            .max()
            .getAsDouble();
    }

    /**
     * Private constructor to prevent instantiation
     */
    private NumberCollections()
    {
        // Private constructor to prevent instantiation
    }
}
