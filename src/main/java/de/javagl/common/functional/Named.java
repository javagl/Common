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
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleSupplier;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntSupplier;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongSupplier;
import java.util.function.LongToDoubleFunction;
import java.util.function.LongToIntFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.ObjDoubleConsumer;
import java.util.function.ObjIntConsumer;
import java.util.function.ObjLongConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleBiFunction;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntBiFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongBiFunction;
import java.util.function.ToLongFunction;
import java.util.function.UnaryOperator;

/**
 * Methods to create named instances of functional classes.<br>
 * <br>
 * The methods in this class create instances of functional classes that
 * have a <code>toString</code> implementation that returns a given name
 * string.<br>
 * <br> 
 * Concatenations of functions with <code>andThen</code> return 
 * instances where the name is extended with <code>" andThen "</code>
 * and the string representation of the argument (which may, in turn,
 * again be a named instance).<br>
 * <br>
 * Note that the functions that deal with predicates will return 
 * predicates that have their <code>and</code>, <code>negate</code>
 * and <code>or</code> methods implemented accordingly. In order 
 * to create predicates with string representations that resemble
 * code (i.e. that use <code>&amp;&amp;</code>, <code>!</code>, and 
 * <code>||</code> for "and", "not" and "or", respectively) may be 
 * created with the <code>Predicates</code> class.  
 */
public class Named
{
    /**
     * Returns a new BiConsumer that has the given name as its 
     * string representation, and performs the same operation
     * as the given one.
     * 
     * @param <T> The first argument type
     * @param <U> The second argument type
     * 
     * @param name The name
     * @param delegate The delegate
     * @return The result
     */
    public static <T, U> BiConsumer<T, U> biConsumer(
        String name, BiConsumer<T,U> delegate) 
    {
        Objects.requireNonNull(delegate, "The delegate may not be null");
        return new BiConsumer<T, U>()
        {
            @Override
            public void accept(T t, U u)
            {
                delegate.accept(t, u);
            }
            
            @Override
            public BiConsumer<T, U> andThen(
                BiConsumer<? super T, ? super U> after)
            {
                return biConsumer(name + " andThen " + after, 
                    BiConsumer.super.andThen(after));
            }

            @Override
            public String toString() 
            {
                return name;
            }
        };
    }

    /**
     * Returns a new BiFunction that has the given name as its 
     * string representation, and performs the same operation
     * as the given one.
     * 
     * @param <T> The first argument type
     * @param <U> The second argument type
     * @param <R> The result type
     * 
     * @param name The name
     * @param delegate The delegate
     * @return The result
     */
    public static <T, U, R> BiFunction<T, U, R> biFunction(
        String name, BiFunction<T, U, R> delegate) 
    {
        Objects.requireNonNull(delegate, "The delegate may not be null");
        return new BiFunction<T, U, R>()
        {
            @Override
            public R apply(T t, U u)
            {
                return delegate.apply(t, u);
            }
            
            @Override
            public <V> BiFunction<T, U, V> andThen(
                Function<? super R, ? extends V> after)
            {
                return biFunction(name + " andThen " + after,
                    BiFunction.super.andThen(after));
            }

            @Override
            public String toString() 
            {
                return name;
            }
        };
    }

    /**
     * Returns a new BinaryOperator that has the given name as its 
     * string representation, and performs the same operation
     * as the given one.
     * 
     * @param <T> The operand and result type
     * 
     * @param name The name
     * @param delegate The delegate
     * @return The result
     */
    public static <T> BinaryOperator<T> binaryOperator(
        String name, BinaryOperator<T> delegate) 
    {
        Objects.requireNonNull(delegate, "The delegate may not be null");
        return new BinaryOperator<T>()
        {
            @Override
            public T apply(T t, T u)
            {
                return delegate.apply(t, u);
            }
            
            @Override
            public <V> BiFunction<T, T, V> andThen(
                Function<? super T, ? extends V> after)
            {
                return biFunction(name + " andThen " + after,
                    BinaryOperator.super.andThen(after));
            }

            @Override
            public String toString() 
            {
                return name;
            }
        };
    }

    /**
     * Returns a new BiPredicate that has the given name as its 
     * string representation, and performs the same operation
     * as the given one.
     * 
     * @param <T> The first argument type
     * @param <U> The second argument type
     * 
     * @param name The name
     * @param delegate The delegate
     * @return The result
     */
    public static <T, U> BiPredicate<T, U> biPredicate(
        String name, BiPredicate<T,U> delegate) 
    {
        Objects.requireNonNull(delegate, "The delegate may not be null");
        return new BiPredicate<T, U>()
        {
            @Override
            public boolean test(T t, U u)
            {
                return delegate.test(t, u);
            }
            
            @Override
            public BiPredicate<T, U> and(
                BiPredicate<? super T, ? super U> other)
            {
                return biPredicate(name + " and " + other, 
                    BiPredicate.super.and(other));
            }
            
            @Override
            public BiPredicate<T, U> negate()
            {
                return biPredicate(name + " negate", 
                    BiPredicate.super.negate());
            }
            
            @Override
            public BiPredicate<T, U> or(
                BiPredicate<? super T, ? super U> other)
            {
                return biPredicate(name + " or " + other, 
                    BiPredicate.super.or(other));
            }

            @Override
            public String toString() 
            {
                return name;
            }
        };
    }

    /**
     * Returns a new BooleanSupplier that has the given name as its 
     * string representation, and performs the same operation
     * as the given one.
     * 
     * @param name The name
     * @param delegate The delegate
     * @return The result
     */
    public static BooleanSupplier booleanSupplier(
        String name, BooleanSupplier delegate) 
    {
        Objects.requireNonNull(delegate, "The delegate may not be null");
        return new BooleanSupplier()
        {
            @Override
            public boolean getAsBoolean()
            {
                return delegate.getAsBoolean();
            }

            @Override
            public String toString() 
            {
                return name;
            }
        };
    }

    /**
     * Returns a new Consumer that has the given name as its 
     * string representation, and performs the same operation
     * as the given one.
     * 
     * @param <T> The argument type
     * 
     * @param name The name
     * @param delegate The delegate
     * @return The result
     */
    public static <T> Consumer<T> consumer(
        String name, Consumer<T> delegate) 
    {
        Objects.requireNonNull(delegate, "The delegate may not be null");
        return new Consumer<T>()
        {
            @Override
            public void accept(T t)
            {
                delegate.accept(t);
            }
            
            @Override
            public Consumer<T> andThen(Consumer<? super T> after)
            {
                return consumer(name + " andThen " + after,
                    Consumer.super.andThen(after));
            }

            @Override
            public String toString() 
            {
                return name;
            }
        };
    }

    /**
     * Returns a new DoubleBinaryOperator that has the given name as its 
     * string representation, and performs the same operation
     * as the given one.
     * 
     * @param name The name
     * @param delegate The delegate
     * @return The result
     */
    public static DoubleBinaryOperator doubleBinaryOperator(
        String name, DoubleBinaryOperator delegate) 
    {
        Objects.requireNonNull(delegate, "The delegate may not be null");
        return new DoubleBinaryOperator()
        {
            @Override
            public double applyAsDouble(double left, double right) 
            {
                return delegate.applyAsDouble(left, right);
            }

            @Override
            public String toString() 
            {
                return name;
            }
        };
    }

    /**
     * Returns a new DoubleConsumer that has the given name as its 
     * string representation, and performs the same operation
     * as the given one.
     * 
     * @param name The name
     * @param delegate The delegate
     * @return The result
     */
    public static DoubleConsumer doubleConsumer(
        String name, DoubleConsumer delegate) 
    {
        Objects.requireNonNull(delegate, "The delegate may not be null");
        return new DoubleConsumer()
        {
            @Override
            public void accept(double value) 
            {
                delegate.accept(value);
            }
            
            @Override
            public DoubleConsumer andThen(DoubleConsumer after)
            {
                return doubleConsumer(name + " andThen " + after,
                    DoubleConsumer.super.andThen(after));
            }

            @Override
            public String toString() 
            {
                return name;
            }
        };
    }

    /**
     * Returns a new DoubleFunction that has the given name as its 
     * string representation, and performs the same operation
     * as the given one.
     * 
     * @param <R> The result type
     * 
     * @param name The name
     * @param delegate The delegate
     * @return The result
     */
    public static <R> DoubleFunction<R> doubleFunction(
        String name, DoubleFunction<R> delegate) 
    {
        Objects.requireNonNull(delegate, "The delegate may not be null");
        return new DoubleFunction<R>()
        {
            @Override
            public R apply(double value)
            {
                return delegate.apply(value);
            }
            
            @Override
            public String toString() 
            {
                return name;
            }
        };
    }

    /**
     * Returns a new DoublePredicate that has the given name as its 
     * string representation, and performs the same operation
     * as the given one.
     * 
     * @param name The name
     * @param delegate The delegate
     * @return The result
     */
    public static DoublePredicate doublePredicate(
        String name, DoublePredicate delegate) 
    {
        Objects.requireNonNull(delegate, "The delegate may not be null");
        return new DoublePredicate()
        {
            @Override
            public boolean test(double value)
            {
                return delegate.test(value);
            }
            
            @Override
            public DoublePredicate and(DoublePredicate other)
            {
                return doublePredicate(name + " and " + other, 
                    DoublePredicate.super.and(other));
            }
            
            @Override
            public DoublePredicate negate()
            {
                return doublePredicate(name + " negate", 
                    DoublePredicate.super.negate());
            }
            
            @Override
            public DoublePredicate or(DoublePredicate other)
            {
                return doublePredicate(name + " or " + other, 
                    DoublePredicate.super.or(other));
            }
            
            @Override
            public String toString() 
            {
                return name;
            }
        };
    }

    /**
     * Returns a new DoubleSupplier that has the given name as its 
     * string representation, and performs the same operation
     * as the given one.
     * 
     * @param name The name
     * @param delegate The delegate
     * @return The result
     */
    public static DoubleSupplier doubleSupplier(
        String name, DoubleSupplier delegate) 
    {
        Objects.requireNonNull(delegate, "The delegate may not be null");
        return new DoubleSupplier()
        {
            @Override
            public double getAsDouble()
            {
                return delegate.getAsDouble();
            }
            
            @Override
            public String toString() 
            {
                return name;
            }
        };
    }

    /**
     * Returns a new DoubleToIntFunction that has the given name as its 
     * string representation, and performs the same operation
     * as the given one.
     * 
     * @param name The name
     * @param delegate The delegate
     * @return The result
     */
    public static DoubleToIntFunction doubleToIntFunction(
        String name, DoubleToIntFunction delegate) 
    {
        Objects.requireNonNull(delegate, "The delegate may not be null");
        return new DoubleToIntFunction()
        {
            @Override
            public int applyAsInt(double value)
            {
                return delegate.applyAsInt(value);
            }
            
            @Override
            public String toString() 
            {
                return name;
            }
        };
    }

    /**
     * Returns a new DoubleToLongFunction that has the given name as its 
     * string representation, and performs the same operation
     * as the given one.
     * 
     * @param name The name
     * @param delegate The delegate
     * @return The result
     */
    public static DoubleToLongFunction doubleToLongFunction(
        String name, DoubleToLongFunction delegate) 
    {
        Objects.requireNonNull(delegate, "The delegate may not be null");
        return new DoubleToLongFunction()
        {
            @Override
            public long applyAsLong(double value)
            {
                return delegate.applyAsLong(value);
            }
            
            @Override
            public String toString() 
            {
                return name;
            }
        };
    }

    /**
     * Returns a new DoubleUnaryOperator that has the given name as its 
     * string representation, and performs the same operation
     * as the given one.
     * 
     * @param name The name
     * @param delegate The delegate
     * @return The result
     */
    public static DoubleUnaryOperator doubleUnaryOperator(
        String name, DoubleUnaryOperator delegate) 
    {
        Objects.requireNonNull(delegate, "The delegate may not be null");
        return new DoubleUnaryOperator()
        {
            @Override
            public double applyAsDouble(double operand)
            {
                return delegate.applyAsDouble(operand);
            }
            
            @Override
            public DoubleUnaryOperator andThen(DoubleUnaryOperator after)
            {
                return doubleUnaryOperator(name + " andThen " + after,
                    DoubleUnaryOperator.super.andThen(after));
            }

            @Override
            public String toString() 
            {
                return name;
            }
        };
    }

    /**
     * Returns a new Function that has the given name as its 
     * string representation, and performs the same operation
     * as the given one.
     * 
     * @param <T> The argument type
     * @param <R> The result type
     * 
     * @param name The name
     * @param delegate The delegate
     * @return The result
     */
    public static <T, R> Function<T, R> function(
        String name, Function<T, R> delegate) 
    {
        Objects.requireNonNull(delegate, "The delegate may not be null");
        return new Function<T, R>()
        {
            @Override
            public R apply(T t)
            {
                return delegate.apply(t);
            }
            
            @Override
            public <V> Function<T, V> andThen(
                Function<? super R, ? extends V> after)
            {
                return function(name + " andThen " + after,
                    Function.super.andThen(after));
            }

            @Override
            public String toString() 
            {
                return name;
            }
        };
    }

    /**
     * Returns a new IntBinaryOperator that has the given name as its 
     * string representation, and performs the same operation
     * as the given one.
     * 
     * @param name The name
     * @param delegate The delegate
     * @return The result
     */
    public static IntBinaryOperator intBinaryOperator(
        String name, IntBinaryOperator delegate) 
    {
        Objects.requireNonNull(delegate, "The delegate may not be null");
        return new IntBinaryOperator()
        {
            @Override
            public int applyAsInt(int left, int right)
            {
                return delegate.applyAsInt(left, right);
            }
            
            @Override
            public String toString() 
            {
                return name;
            }
        };
    }

    /**
     * Returns a new IntConsumer that has the given name as its 
     * string representation, and performs the same operation
     * as the given one.
     * 
     * @param name The name
     * @param delegate The delegate
     * @return The result
     */
    public static IntConsumer intConsumer(
        String name, IntConsumer delegate) 
    {
        Objects.requireNonNull(delegate, "The delegate may not be null");
        return new IntConsumer()
        {
            @Override
            public void accept(int value)
            {
                delegate.accept(value);
            }
            
            @Override
            public IntConsumer andThen(IntConsumer after)
            {
                return intConsumer(name + " andThen " + after,
                    IntConsumer.super.andThen(after));
            }

            @Override
            public String toString() 
            {
                return name;
            }
        };
    }

    /**
     * Returns a new IntFunction that has the given name as its 
     * string representation, and performs the same operation
     * as the given one.
     * 
     * @param <R> The result type
     * 
     * @param name The name
     * @param delegate The delegate
     * @return The result
     */
    public static <R> IntFunction<R> intFunction(
        String name, IntFunction<R> delegate) 
    {
        Objects.requireNonNull(delegate, "The delegate may not be null");
        return new IntFunction<R>()
        {
            @Override
            public R apply(int value)
            {
                return delegate.apply(value);
            }
            
            @Override
            public String toString() 
            {
                return name;
            }
        };
    }

    /**
     * Returns a new IntPredicate that has the given name as its 
     * string representation, and performs the same operation
     * as the given one.
     * 
     * @param name The name
     * @param delegate The delegate
     * @return The result
     */
    public static IntPredicate intPredicate(
        String name, IntPredicate delegate) 
    {
        Objects.requireNonNull(delegate, "The delegate may not be null");
        return new IntPredicate()
        {
            @Override
            public boolean test(int value)
            {
                return delegate.test(value);
            }
            
            @Override
            public IntPredicate and(IntPredicate other)
            {
                return intPredicate(name + " and " + other, 
                    IntPredicate.super.and(other));
            }
            
            @Override
            public IntPredicate negate()
            {
                return intPredicate(name + " negate", 
                    IntPredicate.super.negate());
            }
            
            @Override
            public IntPredicate or(IntPredicate other)
            {
                return intPredicate(name + " or " + other, 
                    IntPredicate.super.or(other));
            }

            @Override
            public String toString() 
            {
                return name;
            }
        };
    }

    /**
     * Returns a new IntSupplier that has the given name as its 
     * string representation, and performs the same operation
     * as the given one.
     * 
     * @param name The name
     * @param delegate The delegate
     * @return The result
     */
    public static IntSupplier intSupplier(
        String name, IntSupplier delegate) 
    {
        Objects.requireNonNull(delegate, "The delegate may not be null");
        return new IntSupplier()
        {
            @Override
            public int getAsInt()
            {
                return delegate.getAsInt();
            }
            
            @Override
            public String toString() 
            {
                return name;
            }
        };
    }

    /**
     * Returns a new IntToDoubleFunction that has the given name as its 
     * string representation, and performs the same operation
     * as the given one.
     * 
     * @param name The name
     * @param delegate The delegate
     * @return The result
     */
    public static IntToDoubleFunction intToDoubleFunction(
        String name, IntToDoubleFunction delegate) 
    {
        Objects.requireNonNull(delegate, "The delegate may not be null");
        return new IntToDoubleFunction()
        {
            @Override
            public double applyAsDouble(int value)
            {
                return delegate.applyAsDouble(value);
            }
            
            @Override
            public String toString() 
            {
                return name;
            }
        };
    }

    /**
     * Returns a new IntToLongFunction that has the given name as its 
     * string representation, and performs the same operation
     * as the given one.
     * 
     * @param name The name
     * @param delegate The delegate
     * @return The result
     */
    public static IntToLongFunction intToLongFunction(
        String name, IntToLongFunction delegate) 
    {
        Objects.requireNonNull(delegate, "The delegate may not be null");
        return new IntToLongFunction()
        {
            @Override
            public long applyAsLong(int value)
            {
                return delegate.applyAsLong(value);
            }
            
            @Override
            public String toString() 
            {
                return name;
            }
        };
    }

    /**
     * Returns a new IntUnaryOperator that has the given name as its 
     * string representation, and performs the same operation
     * as the given one.
     * 
     * @param name The name
     * @param delegate The delegate
     * @return The result
     */
    public static IntUnaryOperator intUnaryOperator(
        String name, IntUnaryOperator delegate) 
    {
        Objects.requireNonNull(delegate, "The delegate may not be null");
        return new IntUnaryOperator()
        {
            @Override
            public int applyAsInt(int operand)
            {
                return delegate.applyAsInt(operand);
            }
            
            @Override
            public IntUnaryOperator andThen(IntUnaryOperator after)
            {
                return intUnaryOperator(name + " andThen " + after,
                    IntUnaryOperator.super.andThen(after));
            }

            @Override
            public String toString() 
            {
                return name;
            }
        };
    }

    /**
     * Returns a new LongBinaryOperator that has the given name as its 
     * string representation, and performs the same operation
     * as the given one.
     * 
     * @param name The name
     * @param delegate The delegate
     * @return The result
     */
    public static LongBinaryOperator longBinaryOperator(
        String name, LongBinaryOperator delegate) 
    {
        Objects.requireNonNull(delegate, "The delegate may not be null");
        return new LongBinaryOperator()
        {
            @Override
            public long applyAsLong(long left, long right)
            {
                return delegate.applyAsLong(left, right);
            }
            
            @Override
            public String toString() 
            {
                return name;
            }
        };
    }

    /**
     * Returns a new LongConsumer that has the given name as its 
     * string representation, and performs the same operation
     * as the given one.
     * 
     * @param name The name
     * @param delegate The delegate
     * @return The result
     */
    public static LongConsumer longConsumer(
        String name, LongConsumer delegate) 
    {
        Objects.requireNonNull(delegate, "The delegate may not be null");
        return new LongConsumer()
        {
            @Override
            public void accept(long value)
            {
                delegate.accept(value);
            }
            
            @Override
            public LongConsumer andThen(LongConsumer after)
            {
                return longConsumer(name + " andThen " + after,
                    LongConsumer.super.andThen(after));  
            }

            @Override
            public String toString() 
            {
                return name;
            }
        };
    }

    /**
     * Returns a new LongFunction that has the given name as its 
     * string representation, and performs the same operation
     * as the given one.
     * 
     * @param <R> The result type
     * 
     * @param name The name
     * @param delegate The delegate
     * @return The result
     */
    public static <R> LongFunction<R> longFunction(
        String name, LongFunction<R> delegate) 
    {
        Objects.requireNonNull(delegate, "The delegate may not be null");
        return new LongFunction<R>()
        {
            @Override
            public R apply(long value)
            {
                return delegate.apply(value);
            }
            
            @Override
            public String toString() 
            {
                return name;
            }
        };
    }

    /**
     * Returns a new LongPredicate that has the given name as its 
     * string representation, and performs the same operation
     * as the given one.
     * 
     * @param name The name
     * @param delegate The delegate
     * @return The result
     */
    public static LongPredicate longPredicate(
        String name, LongPredicate delegate) 
    {
        Objects.requireNonNull(delegate, "The delegate may not be null");
        return new LongPredicate()
        {
            @Override
            public boolean test(long value)
            {
                return delegate.test(value);
            }
            
            @Override
            public LongPredicate and(LongPredicate other)
            {
                return longPredicate(name + " and " + other, 
                    LongPredicate.super.and(other));
            }
            
            @Override
            public LongPredicate negate()
            {
                return longPredicate(name + " negate", 
                    LongPredicate.super.negate());
            }
            
            @Override
            public LongPredicate or(LongPredicate other)
            {
                return longPredicate(name + " or " + other, 
                    LongPredicate.super.or(other));
            }

            @Override
            public String toString() 
            {
                return name;
            }
        };
    }

    /**
     * Returns a new LongSupplier that has the given name as its 
     * string representation, and performs the same operation
     * as the given one.
     * 
     * @param name The name
     * @param delegate The delegate
     * @return The result
     */
    public static LongSupplier longSupplier(
        String name, LongSupplier delegate) 
    {
        Objects.requireNonNull(delegate, "The delegate may not be null");
        return new LongSupplier()
        {
            @Override
            public long getAsLong()
            {
                return delegate.getAsLong();
            }

            @Override
            public String toString() 
            {
                return name;
            }
        };
    }

    /**
     * Returns a new LongToDoubleFunction that has the given name as its 
     * string representation, and performs the same operation
     * as the given one.
     * 
     * @param name The name
     * @param delegate The delegate
     * @return The result
     */
    public static LongToDoubleFunction longToDoubleFunction(
        String name, LongToDoubleFunction delegate) 
    {
        Objects.requireNonNull(delegate, "The delegate may not be null");
        return new LongToDoubleFunction()
        {
            @Override
            public double applyAsDouble(long value)
            {
                return delegate.applyAsDouble(value);
            }

            @Override
            public String toString() 
            {
                return name;
            }
        };
    }

    /**
     * Returns a new LongToIntFunction that has the given name as its 
     * string representation, and performs the same operation
     * as the given one.
     * 
     * @param name The name
     * @param delegate The delegate
     * @return The result
     */
    public static LongToIntFunction longToIntFunction(
        String name, LongToIntFunction delegate) 
    {
        Objects.requireNonNull(delegate, "The delegate may not be null");
        return new LongToIntFunction()
        {
            @Override
            public int applyAsInt(long value)
            {
                return delegate.applyAsInt(value);
            }
            
            @Override
            public String toString() 
            {
                return name;
            }
        };
    }

    /**
     * Returns a new LongUnaryOperator that has the given name as its 
     * string representation, and performs the same operation
     * as the given one.
     * 
     * @param name The name
     * @param delegate The delegate
     * @return The result
     */
    public static LongUnaryOperator longUnaryOperator(
        String name, LongUnaryOperator delegate) 
    {
        Objects.requireNonNull(delegate, "The delegate may not be null");
        return new LongUnaryOperator()
        {
            @Override
            public long applyAsLong(long operand)
            {
                return delegate.applyAsLong(operand);
            }
            
            @Override
            public LongUnaryOperator andThen(LongUnaryOperator after)
            {
                return longUnaryOperator(name + " andThen " + after,
                    LongUnaryOperator.super.andThen(after));
            }

            @Override
            public String toString() 
            {
                return name;
            }
        };
    }

    /**
     * Returns a new ObjDoubleConsumer that has the given name as its 
     * string representation, and performs the same operation
     * as the given one.
     * 
     * @param <T> The argument type
     * 
     * @param name The name
     * @param delegate The delegate
     * @return The result
     */
    public static <T> ObjDoubleConsumer<T> objDoubleConsumer(
        String name, ObjDoubleConsumer<T> delegate) 
    {
        Objects.requireNonNull(delegate, "The delegate may not be null");
        return new ObjDoubleConsumer<T>()
        {
            @Override
            public void accept(T t, double value)
            {
                delegate.accept(t, value);
            }
            
            @Override
            public String toString() 
            {
                return name;
            }
        };
    }

    /**
     * Returns a new ObjIntConsumer that has the given name as its 
     * string representation, and performs the same operation
     * as the given one.
     * 
     * @param <T> The argument type
     * 
     * @param name The name
     * @param delegate The delegate
     * @return The result
     */
    public static <T> ObjIntConsumer<T> objIntConsumer(
        String name, ObjIntConsumer<T> delegate) 
    {
        Objects.requireNonNull(delegate, "The delegate may not be null");
        return new ObjIntConsumer<T>()
        {
            @Override
            public void accept(T t, int value)
            {
                delegate.accept(t, value);
            }
            
            @Override
            public String toString() 
            {
                return name;
            }
        };
    }

    /**
     * Returns a new ObjLongConsumer that has the given name as its 
     * string representation, and performs the same operation
     * as the given one.
     * 
     * @param <T> The argument type
     * 
     * @param name The name
     * @param delegate The delegate
     * @return The result
     */
    public static <T> ObjLongConsumer<T> objLongConsumer(
        String name, ObjLongConsumer<T> delegate) 
    {
        Objects.requireNonNull(delegate, "The delegate may not be null");
        return new ObjLongConsumer<T>()
        {
            @Override
            public void accept(T t, long value)
            {
                delegate.accept(t, value);
            }

            @Override
            public String toString() 
            {
                return name;
            }
        };
    }

    /**
     * Returns a new Predicate that has the given name as its 
     * string representation, and performs the same operation
     * as the given one.
     * 
     * @param <T> The argument type
     * 
     * @param name The name
     * @param delegate The delegate
     * @return The result
     */
    public static <T> Predicate<T> predicate(
        String name, Predicate<T> delegate) 
    {
        Objects.requireNonNull(delegate, "The delegate may not be null");
        return new Predicate<T>()
        {
            @Override
            public boolean test(T t)
            {
                return delegate.test(t);
            }
            
            @Override
            public Predicate<T> and(Predicate<? super T> other)
            {
                return predicate(name + " and " + other, 
                    Predicate.super.and(other));
            }
            
            @Override
            public Predicate<T> negate()
            {
                return predicate(name + " negate", 
                    Predicate.super.negate());
            }
            
            @Override
            public Predicate<T> or(Predicate<? super T> other)
            {
                return predicate(name + " or " + other, 
                    Predicate.super.or(other));
            }

            @Override
            public String toString() 
            {
                return name;
            }
        };
    }

    /**
     * Returns a new Supplier that has the given name as its 
     * string representation, and performs the same operation
     * as the given one.
     * 
     * @param <T> The result type
     * 
     * @param name The name
     * @param delegate The delegate
     * @return The result
     */
    public static <T> Supplier<T> supplier(
        String name, Supplier<T> delegate) 
    {
        Objects.requireNonNull(delegate, "The delegate may not be null");
        return new Supplier<T>()
        {
            @Override
            public T get()
            {
                return delegate.get();
            }

            @Override
            public String toString() 
            {
                return name;
            }
        };
    }

    /**
     * Returns a new ToDoubleBiFunction that has the given name as its 
     * string representation, and performs the same operation
     * as the given one.
     * 
     * @param <T> The first argument type
     * @param <U> The second argument type
     * 
     * @param name The name
     * @param delegate The delegate
     * @return The result
     */
    public static <T, U> ToDoubleBiFunction<T, U> toDoubleBiFunction(
        String name, ToDoubleBiFunction<T, U> delegate) 
    {
        Objects.requireNonNull(delegate, "The delegate may not be null");
        return new ToDoubleBiFunction<T, U>()
        {
            @Override
            public double applyAsDouble(T t, U u)
            {
                return delegate.applyAsDouble(t, u);
            }
            
            @Override
            public String toString() 
            {
                return name;
            }
        };
    }

    /**
     * Returns a new ToDoubleFunction that has the given name as its 
     * string representation, and performs the same operation
     * as the given one.
     * 
     * @param <T> The argument type
     * 
     * @param name The name
     * @param delegate The delegate
     * @return The result
     */
    public static <T> ToDoubleFunction<T> toDoubleFunction(
        String name, ToDoubleFunction<T> delegate) 
    {
        Objects.requireNonNull(delegate, "The delegate may not be null");
        return new ToDoubleFunction<T>()
        {
            @Override
            public double applyAsDouble(T value)
            {
                return delegate.applyAsDouble(value);
            }
            
            @Override
            public String toString() 
            {
                return name;
            }
        };
    }

    /**
     * Returns a new ToIntBiFunction that has the given name as its 
     * string representation, and performs the same operation
     * as the given one.
     * 
     * @param <T> The first argument type
     * @param <U> The second argument type
     * 
     * @param name The name
     * @param delegate The delegate
     * @return The result
     */
    public static <T, U> ToIntBiFunction<T, U> toIntBiFunction(
        String name, ToIntBiFunction<T, U> delegate) 
    {
        Objects.requireNonNull(delegate, "The delegate may not be null");
        return new ToIntBiFunction<T, U>()
        {
            @Override
            public int applyAsInt(T t, U u)
            {
                return delegate.applyAsInt(t, u);
            }
            
            @Override
            public String toString() 
            {
                return name;
            }
        };
    }

    /**
     * Returns a new ToIntFunction that has the given name as its 
     * string representation, and performs the same operation
     * as the given one.
     * 
     * @param <T> The first argument type
     * 
     * @param name The name
     * @param delegate The delegate
     * @return The result
     */
    public static <T> ToIntFunction<T> toIntFunction(
        String name, ToIntFunction<T> delegate) 
    {
        Objects.requireNonNull(delegate, "The delegate may not be null");
        return new ToIntFunction<T>()
        {
            @Override
            public int applyAsInt(T value)
            {
                return delegate.applyAsInt(value);
            }
            
            @Override
            public String toString() 
            {
                return name;
            }
        };
    }

    /**
     * Returns a new ToLongBiFunction that has the given name as its 
     * string representation, and performs the same operation
     * as the given one.
     * 
     * @param <T> The first argument type
     * @param <U> The second argument type
     * 
     * @param name The name
     * @param delegate The delegate
     * @return The result
     */
    public static <T, U> ToLongBiFunction<T, U> toLongBiFunction(
        String name, ToLongBiFunction<T, U> delegate) 
    {
        Objects.requireNonNull(delegate, "The delegate may not be null");
        return new ToLongBiFunction<T, U>()
        {
            @Override
            public long applyAsLong(T t, U u)
            {
                return delegate.applyAsLong(t, u);
            }
            
            @Override
            public String toString() 
            {
                return name;
            }
        };
    }

    /**
     * Returns a new ToLongFunction that has the given name as its 
     * string representation, and performs the same operation
     * as the given one.
     * 
     * @param <T> The argument type
     * 
     * @param name The name
     * @param delegate The delegate
     * @return The result
     */
    public static <T> ToLongFunction<T> toLongFunction(
        String name, ToLongFunction<T> delegate) 
    {
        Objects.requireNonNull(delegate, "The delegate may not be null");
        return new ToLongFunction<T>()
        {
            @Override
            public long applyAsLong(T value)
            {
                return delegate.applyAsLong(value);
            }
            
            @Override
            public String toString() 
            {
                return name;
            }
        };
    }

    /**
     * Returns a new UnaryOperator that has the given name as its 
     * string representation, and performs the same operation
     * as the given one.
     * 
     * @param <T> The argument type
     * 
     * @param name The name
     * @param delegate The delegate
     * @return The result
     */
    public static <T> UnaryOperator<T> unaryOperator(
        String name, UnaryOperator<T> delegate) 
    {
        Objects.requireNonNull(delegate, "The delegate may not be null");
        return new UnaryOperator<T>()
        {
            @Override
            public T apply(T t)
            {
                return delegate.apply(t);
            }
            
            @Override
            public <V> Function<T, V> andThen(
                Function<? super T, ? extends V> after)
            {
                return function(name + " andThen " + after,
                    UnaryOperator.super.andThen(after));
            }
            
            @Override
            public String toString() 
            {
                return name;
            }
        };
    }

    /**
     * Private constructor to prevent instantiation
     */
    private Named()
    {
        // Private constructor to prevent instantiation
    }
}
