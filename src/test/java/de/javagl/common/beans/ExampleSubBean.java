/*
 * www.javagl.de - Common
 *
 * Copyright (c) 2012-2015 Marco Hutter - http://www.javagl.de
 */
package de.javagl.common.beans;

import de.javagl.common.beans.ExampleBean.ExampleBeanEnum;

/**
 * An example bean for the {@link VerboseXmlEncoderTest}
 */
@SuppressWarnings("javadoc")
public class ExampleSubBean
{
    private int intValue;
    private String stringValue;
    private ExampleBeanEnum enumValue;
    
    public ExampleSubBean()
    {
        enumValue = ExampleBeanEnum.DEFAULT_ENUM_VALUE;
    }
    
    public int getIntValue()
    {
        return intValue;
    }
    public void setIntValue(int intValue)
    {
        this.intValue = intValue;
    }
    public String getStringValue()
    {
        return stringValue;
    }
    public void setStringValue(String stringValue)
    {
        this.stringValue = stringValue;
    }
    public ExampleBeanEnum getEnumValue()
    {
        return enumValue;
    }
    public void setEnumValue(ExampleBeanEnum enumValue)
    {
        this.enumValue = enumValue;
    }
}
