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
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.Random;
import java.util.function.DoubleUnaryOperator;

/**
 * Utility methods related to maps containing <code>Double</code> 
 * keys and/or values
 */
public class DoubleMaps
{
    /**
     * An epsilon for double computations
     */
    private static final double EPSILON = 1e-10;
    
    /**
     * Create a map with the given keys, which are mapped to random values
     * in the specified range
     * 
     * @param <K> The key type
     * 
     * @param keys The keys
     * @param min The minimum value
     * @param max The maximum value
     * @param random The random number generator
     * @return The resulting map
     */
    public static <K> Map<K, Double> create(
        Iterable<? extends K> keys, double min, double max, Random random)
    {
        Map<K, Double> result = new LinkedHashMap<K, Double>();
        for (K k : keys)
        {
            double d = random.nextDouble();
            double v = min + d * (max - min);
            result.put(k, v);
        }
        return result;
    }

    /**
     * Create maps with the given keys, which are mapped to values that
     * are computed by applying the given operators to the range [0,1],
     * linearly interpolated for the given keys.
     * 
     * @param <K> The key type
     * 
     * @param keys The keys
     * @param operators The operators
     * @return The resulting maps
     */
    public static <K> List<Map<K, Double>> create(
        Collection<? extends K> keys, 
        List<? extends DoubleUnaryOperator> operators)
    {
        List<Map<K, Double>> results = new ArrayList<Map<K, Double>>();
        for (DoubleUnaryOperator operator : operators)
        {
            results.add(create(keys, operator));
        }
        return results;
    }
    
    /**
     * Create a map with the given keys, which are mapped to values that
     * are computed by applying the given operator to the range [0,1],
     * linearly interpolated for the given keys.
     * 
     * @param <K> The key type
     * 
     * @param keys The keys
     * @param operator The operator
     * @return The resulting map
     */
    public static <K> Map<K, Double> create(
        Collection<? extends K> keys, DoubleUnaryOperator operator)
    {
        if (keys.isEmpty())
        {
            return Collections.emptyMap();
        }
        if (keys.size() == 1)
        {
            K k = keys.iterator().next();
            double v = operator.applyAsDouble(0.0);
            return Collections.singletonMap(k, v);
        }
        Map<K, Double> result = new LinkedHashMap<K, Double>();
        double stepSize = 1.0 / (keys.size() - 1);
        int step = 0;
        for (K k : keys)
        {
            double a = step * stepSize;
            double v = operator.applyAsDouble(a);
            result.put(k, v);
            step++;
        }
        return result;
    }

    
    /**
     * Scale the values in the given map to be in the specified range. The
     * minimum value of the given map will be mapped to the given target
     * minimum, and the maximum value to the target maximum. If the range
     * of the values in the given map is not greater than a small epsilon,
     * then the minimum target value will be used.
     * 
     * @param <K> The key type
     * 
     * @param map The map
     * @param targetMin The target minimum
     * @param targetMax The target maximum
     * @return The resulting map
     */
    public static <K> Map<K, Double> scaleValuesToRange(
        Map<K, ? extends Number> map, 
        double targetMin, double targetMax)
    {
        return scaleValuesToRange(
            map, targetMin, targetMax, EPSILON, targetMin);
    }
    
    /**
     * Scale the values in the given map to be in the specified range. The
     * minimum value of the given map will be mapped to the given target
     * minimum, and the maximum value to the target maximum. If the range
     * of the values in the given map is not greater than the given epsilon,
     * then the given default value will be used.
     * 
     * @param <K> The key type
     * 
     * @param map The map
     * @param targetMin The target minimum
     * @param targetMax The target maximum
     * @param epsilon The epsilon
     * @param defaultValue The default value
     * @return The resulting map
     */
    public static <K> Map<K, Double> scaleValuesToRange(
        Map<K, ? extends Number> map, 
        double targetMin, double targetMax, 
        double epsilon, double defaultValue)
    {
        if (map.isEmpty()) 
        {
            return Collections.emptyMap();
        }
        double sourceMin = NumberCollections.min(map.values());
        double sourceMax = NumberCollections.max(map.values());
        double sourceDelta = sourceMax - sourceMin;
        double targetDelta = targetMax - targetMin;
        if (Math.abs(sourceMax - sourceMin) <= epsilon)
        {
            return transformValues(map, x -> defaultValue);
        }
        DoubleUnaryOperator op = 
            x -> targetMin + ((x - sourceMin) / sourceDelta) * targetDelta;
        return transformValues(map, op);
    }
    
    /**
     * Returns a new map that contains the same keys as the given one,
     * but has its values transformed with the given operator. If any
     * value of the input is <code>null</code>, then the value in the
     * result will also be <code>null</code>.
     * 
     * @param <K> The key type
     * 
     * @param map The map
     * @param op The operator
     * @return The result
     */
    public static <K> Map<K, Double> transformValues(
        Map<K, ? extends Number> map, DoubleUnaryOperator op)
    {
        if (map.isEmpty()) 
        {
            return Collections.emptyMap();
        }
        Map<K, Double> result = new LinkedHashMap<K, Double>();
        for (Entry<K, ? extends Number> entry : map.entrySet())
        {
            K key = entry.getKey();
            Number value = entry.getValue();
            if (value == null)
            {
                result.put(key, null);
            }
            else
            {
                double v = value.doubleValue();
                double r = op.applyAsDouble(v);
                result.put(key, r);
            }
        }
        return result;
    }
    
    /**
     * Returns a linearly interpolated value from the given map.<br>
     * <br>
     * For the given key, the next larger and next smaller key
     * will be determined. The values associated with these keys
     * will be interpolated, based on the location of the given
     * key between the next larger and smaller key.<br>
     * <br>
     * If the given key is larger than the largest key in the
     * given map, then the value of the largest key will be
     * returned.<br>
     * <br>
     * If the given key is smaller than the smallest key in the
     * given map, then the value of the smallest key will be
     * returned.
     *  
     * @param map The map
     * @param key The key
     * @return The interpolated value
     * @throws IllegalArgumentException If the given map is empty
     */
    public static double getInterpolated(
        NavigableMap<Double, ? extends Number> map, double key)
    {
        if (map.isEmpty())
        {
            throw new IllegalArgumentException("Empty map");
        }
        Entry<Double, ? extends Number> c = map.ceilingEntry(key);
        Entry<Double, ? extends Number> f = map.floorEntry(key);
        if (c == null)
        {
            return f.getValue().doubleValue();
        }
        if (f == null)
        {
            return c.getValue().doubleValue();
        }
        if (c.equals(f))
        {
            return c.getValue().doubleValue();
        }
        double deltaKeys = c.getKey() - f.getKey();
        double alpha = (key - f.getKey()) / deltaKeys;
        double deltaValues = 
            c.getValue().doubleValue() - 
            f.getValue().doubleValue();
        double result = f.getValue().doubleValue() + alpha * deltaValues;
        return result;
    }
    
    
    /**
     * Private constructor to prevent instantiation
     */
    private DoubleMaps()
    {
        // Private constructor to prevent instantiation
    }

}
