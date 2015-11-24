/*
 * www.javagl.de - Common
 *
 * Copyright (c) 2012-2015 Marco Hutter - http://www.javagl.de
 */
package de.javagl.common.beans;

import java.beans.ExceptionListener;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import de.javagl.common.beans.ExampleBean.ExampleBeanEnum;

/**
 * A simple integration test for the {@link VerboseXmlEncoder}
 */
@SuppressWarnings("javadoc")
public class VerboseXmlEncoderTest
{
    public static void main(String[] args)
    {
        // Create an XMLEncoder that writes to a byte array
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        XMLEncoder encoder = XmlEncoders.createVerbose(stream);
        encoder.setExceptionListener(new ExceptionListener()
        {
            @Override
            public void exceptionThrown(Exception e)
            {
                e.printStackTrace();
            }
        });


        ExampleBean oldExampleBean = new ExampleBean();
        
        List<ExampleSubBean> subBeans = new ArrayList<ExampleSubBean>();
        ExampleSubBean subBean0 = new ExampleSubBean();
        subBean0.setEnumValue(ExampleBeanEnum.MODIFIED_ENUM_VALUE);
        subBeans.add(subBean0);
        ExampleSubBean subBean1 = new ExampleSubBean();
        subBean1.setEnumValue(ExampleBeanEnum.MODIFIED_ENUM_VALUE);
        subBeans.add(subBean1);
        oldExampleBean.setSubBeans(subBeans);
        
        oldExampleBean.setIntValue(234);

        encoder.writeObject(oldExampleBean);
        encoder.flush();
        encoder.close();

        // Print the encoding result
        System.out.println(stream);

        // Read the instance back and print its properties
        XMLDecoder d =
            new XMLDecoder(new ByteArrayInputStream(stream.toByteArray()));
        ExampleBean newExampleBean = (ExampleBean) d.readObject();
        System.out.println("stringValue: " + newExampleBean.getStringValue());
        System.out.println("intValue   : " + newExampleBean.getIntValue());
        System.out.println("enumValue  : " + newExampleBean.getEnumValue());
        d.close();
    }
}
