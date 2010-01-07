package com.vygovskiy.controls.binding;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jdesktop.beansbinding.Converter;

/**
 * Convert string to double.
 * 
 * @author leonidv
 * 
 */
public class DoubleConvertor extends Converter<String, Double> {
    private static final Logger logger = Logger.getLogger(DoubleConvertor.class
            .getCanonicalName());

    private NumberFormat format = NumberFormat.getNumberInstance();

    @Override
    public Double convertForward(String value) {
        if ((value == null) || (value.isEmpty())) {
            return 0.0;
        }
        try {
            Number number = format.parse(value);
            return number.doubleValue();
        } catch (ParseException e) {
            final String msg = String.format(
                    "Can't convert string [%s] to double value", value);
            logger.log(Level.WARNING, msg, e);
            e.printStackTrace();
        }
        return 0.0;
    }

    @Override
    public String convertReverse(Double value) {
        if (value == null) {
            return String.valueOf(0.0);
        }
        return format.format(value);
    }

}
