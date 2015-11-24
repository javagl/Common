/*
 * www.javagl.de - Common
 *
 * Copyright (c) 2012-2015 Marco Hutter - http://www.javagl.de
 */
package de.javagl.common.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * An example bean for the {@link VerboseXmlEncoderTest}
 */
@SuppressWarnings("javadoc")
public class ExampleBean
{
    enum ExampleBeanEnum
    {
        DEFAULT_ENUM_VALUE,
        MODIFIED_ENUM_VALUE,
    }
    private String stringValue;
    private String anotherStringValue;
    private int intValue;
    private ExampleBeanEnum enumValue;
    private ExampleBeanEnum anotherEnumValue;
    private ExampleSubBean exampleSubBean;
    
    private List<ExampleSubBean> subBeans;
    
    public ExampleBean()
    {
        stringValue = "Default String Value";
        setAnotherStringValue("Default String Value");
        intValue = 123;
        enumValue = ExampleBeanEnum.DEFAULT_ENUM_VALUE;
        anotherEnumValue = ExampleBeanEnum.DEFAULT_ENUM_VALUE;
        exampleSubBean = new ExampleSubBean();
        exampleSubBean.setEnumValue(ExampleBeanEnum.MODIFIED_ENUM_VALUE);
        
        subBeans = new ArrayList<ExampleSubBean>();
        subBeans.add(new ExampleSubBean());
        subBeans.add(new ExampleSubBean());
    }

    public List<ExampleSubBean> getSubBeans()
    {
        return subBeans;
    }

    public void setSubBeans(List<ExampleSubBean> subBeans)
    {
        this.subBeans = subBeans;
    }
    
    public String getStringValue()
    {
        return stringValue;
    }
    public void setStringValue(String stringValue)
    {
        this.stringValue = stringValue;
    }
    public int getIntValue()
    {
        return intValue;
    }
    public void setIntValue(int intValue)
    {
        this.intValue = intValue;
    }
    public ExampleBeanEnum getEnumValue()
    {
        return enumValue;
    }
    public void setEnumValue(ExampleBeanEnum enumValue)
    {
        this.enumValue = enumValue;
    }

    public ExampleBeanEnum getAnotherEnumValue()
    {
        return anotherEnumValue;
    }

    public void setAnotherEnumValue(ExampleBeanEnum anotherEnumValue)
    {
        this.anotherEnumValue = anotherEnumValue;
    }

    public String getAnotherStringValue()
    {
        return anotherStringValue;
    }

    public void setAnotherStringValue(String anotherStringValue)
    {
        this.anotherStringValue = anotherStringValue;
    }

    public ExampleSubBean getExampleSubBean()
    {
        return exampleSubBean;
    }

    public void setExampleSubBean(ExampleSubBean exampleSubBean)
    {
        this.exampleSubBean = exampleSubBean;
    }

}