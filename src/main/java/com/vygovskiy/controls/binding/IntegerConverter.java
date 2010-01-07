package com.vygovskiy.controls.binding;

import java.util.HashMap;
import java.util.Map;

import org.jdesktop.beansbinding.Converter;

/**
 * Convert String value from text editors to integer value.
 * 
 * @author leonidv
 * 
 */
public class IntegerConverter extends Converter<String, Integer> {
	static private Map<Integer, IntegerConverter> cache = new HashMap<Integer, IntegerConverter>();

	static public IntegerConverter create(int defaulValue) {
		if (cache.containsKey(defaulValue)) {
			return cache.get(defaulValue);
		} else {
			IntegerConverter converter = new IntegerConverter(defaulValue);
			cache.put(defaulValue, converter);
			return converter;
		}
	}

	final private int defaultValue;

	private IntegerConverter(int defaultValue) {
		this.defaultValue = defaultValue;
	}

	@Override
	public String convertReverse(Integer value) {
		return String.valueOf(value);
	}

	@Override
	public Integer convertForward(String value) {
		int result = defaultValue;
		if (value != null && !value.trim().isEmpty()) {
			try {
				result = Integer.parseInt(value);
			} catch (NumberFormatException e) {
				// Нагло гасим исключение. Но здесь и не нужно ничего делать
			}
		}
		return result;
	}

}
