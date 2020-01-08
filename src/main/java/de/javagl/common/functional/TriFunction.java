/*
 * www.javagl.de
 *
 * Copyright (c) 2017-2019 Marco Hutter - http://www.javagl.de
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
package de.javagl.common.functional;

import java.util.Objects;
import java.util.function.Function;

/**
 * Interface for a function that receives three arguments and returns a result.
 * 
 * @param <A> The first argument type
 * @param <B> The second argument type 
 * @param <C> The third argument type
 * @param <D> The result type
 */
public interface TriFunction<A, B, C, D> {

    /**
     * Applies this function to the given arguments.
     *
     * @param a The first function argument
     * @param b The second function argument
     * @param c The third function argument
     * @return The function result
     */
    D apply(A a, B b, C c);
    
    /**
     * Returns a composed function that first applies this function to
     * its input, and then applies the <code>after</code> function to the 
     * result. If evaluation of either function throws an exception, it 
     * is relayed to the caller of the composed function.
     *
     * @param <Z> The type of output of the <code>after</code> function, 
     * and of the composed function
     * 
     * @param after the function to apply after this function is applied
     * @return a composed function that first applies this function and then
     * applies the {@code after} function
     * @throws NullPointerException if <code>after</code> is <code>null</code>
     */
    default <Z> TriFunction<A, B, C, Z> andThen(
        Function<? super D, ? extends Z> after)
    {
        Objects.requireNonNull(after);
        return (A a, B b, C c) -> after.apply(apply(a, b, c));
    }
}
