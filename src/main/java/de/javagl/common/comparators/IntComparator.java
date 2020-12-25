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
 * A comparator for <code>int</code> values
 */
interface IntComparator
{
    /**
     * Returns an {@link IntComparator} that orders values in ascending order
     * 
     * @return The comparator
     */
    public static IntComparator ascending()
    {
        return Integer::compare;
    }
    
    /**
     * Returns an {@link IntComparator} that orders values in descending order
     * 
     * @return The comparator
     */
    public static IntComparator descending()
    {
        return (v0, v1) -> Integer.compare(v1, v0);
    }
    
    /**
     * Creates a comparator for <code>Integer</code> objects from this one
     * 
     * @return The resulting comparator
     */
    default Comparator<Integer> boxed()
    {
        return (v0, v1) -> compareInt(v0, v1);
    }
    
    /**
     * Returns the result of comparing the given values, as of 
     * <code>Integer#compareInt</code>
     * 
     * @param v0 The first value
     * @param v1 The second value
     * @return The comparison result
     */
    int compareInt(int v0, int v1);
}
