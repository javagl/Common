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

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Utility methods related to sets
 */
public class Sets
{
    /**
     * Assert that the given sets are equal, and throw an 
     * <code>IllegalArgumentException</code> if this is not the case,
     * with a message that contains information about the difference
     * between the sets
     * 
     * @param set0 The first set
     * @param set1 The second set
     * @throws IllegalArgumentException If the sets are not equal
     */
    public static void assertEqual(Set<?> set0, Set<?> set1)
    {
        if (Objects.equals(set0, set1))
        {
            return;
        }
        LinkedHashSet<?> extraIn0 = new LinkedHashSet<Object>(set0);
        extraIn0.removeAll(set1);
        LinkedHashSet<?> extraIn1 = new LinkedHashSet<Object>(set1);
        extraIn1.removeAll(set0);

        StringBuilder sb = new StringBuilder("The sets are not equal. ");
        if (!extraIn0.isEmpty())
        {
            sb.append("Set 0 contains unexpected " + extraIn0 + ". ");
        }
        if (!extraIn1.isEmpty())
        {
            sb.append("Set 1 contains unexpected " + extraIn1 + ". ");
        }
        throw new IllegalArgumentException(sb.toString());
    }
    
    /**
     * Private constructor to prevent instantiation
     */
    private Sets()
    {
        // Private constructor to prevent instantiation
    }

}
