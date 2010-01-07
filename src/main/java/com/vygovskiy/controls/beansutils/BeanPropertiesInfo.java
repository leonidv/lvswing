package com.vygovskiy.controls.beansutils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFormattedTextField;

import com.vygovskiy.controls.binding.DoubleConvertor;


/**
 * 
 * 
 * Обрабатываются только свойства, которые доступны на запись и чтение
 * (существуют методы get и set)
 */
public class BeanPropertiesInfo<E> {
    private static Logger LOG = Logger.getLogger(BeanPropertiesInfo.class
            .getCanonicalName());

    // Карта сопоставления именам свойств информации о них
    private Map<String, PropertyInfo> propertiesInfo;

    /**
     * Create information about given bean. Created properties info list and
     * load default values as current bean's values.
     * 
     * @param bean
     * @throws IntrospectionException
     */
    public BeanPropertiesInfo(E bean) throws IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
        propertiesInfo = getPropertiesInfo(bean, beanInfo);
    }

    /**
     * Возвращает карту сопоставления всех свойств переданного JavaBeans и
     * информации о них. В качестве ключ - имя свойства в нотации JavaBeans
     * (например, lastName) значение - информация о свойстве.<br/>
     * 
     * @param bean
     */
    @SuppressWarnings("unchecked")
    private Map<String, PropertyInfo> getPropertiesInfo(E bean,
            BeanInfo beanInfo) {
        Map<String, PropertyInfo> result = new HashMap<String, PropertyInfo>();

        for (PropertyDescriptor property : beanInfo.getPropertyDescriptors()) {
            try {
                // Don't process property name.
                if (property.getName().equals("class")) {
                    continue;
                }

                PropertyInfo propertyInfo;

                final Class clazz = property.getPropertyType();
                final String name = property.getName();
                final String title = name;

                if (clazz == null) {
                    throw new IllegalArgumentException(
                            "Not supported indexed properties");
                }

                PropertyEditorInfo editorInfo;

                if (isIntegerType(clazz)) {
                    editorInfo = PropertyEditorInfo.createIntegerEditorInfo();
                } else if (isDecimalType(clazz)) {
                    editorInfo = PropertyEditorInfo.createDecimalEditorInfo();
                } else if (isBoolean(clazz)) {
                    editorInfo = PropertyEditorInfo.createBooleanEditorInfo();
                } else {
                    editorInfo = PropertyEditorInfo.createStringEditorInfo();
                }

                propertyInfo = new PropertyInfo(title, editorInfo, name);
                result.put(name, propertyInfo);

                Object value = propertyInfo.getPropertyBean().getValue(bean);
                initEditor(propertyInfo, property, value);

            } catch (Exception e) {
//                System.out.println("Ошибка при обращение к свойству "
//                        + property.getName());
                LOG.log(Level.SEVERE, "Error on process property "
                        + property.getName(), e);
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    private boolean isBoolean(Class propertyClass) {
        return propertyClass == Boolean.class || propertyClass == boolean.class;
    }

    /**
     * Возвращает истину, если класс описывает один из целочисленных типов
     * 
     * @param propClass
     * @return
     */
    @SuppressWarnings("unchecked")
    private boolean isIntegerType(Class propertyClass) {
        return propertyClass == Integer.class || propertyClass == int.class
                || propertyClass == byte.class || propertyClass == short.class
                || propertyClass == long.class
                || propertyClass == Integer.class
                || propertyClass == Byte.class || propertyClass == Short.class
                || propertyClass == Long.class;

    }

    /**
     * Возвращает истину, если класс описывает один из вещественных типов
     * 
     * @param propertyClass
     * @return
     */
    @SuppressWarnings("unchecked")
    private boolean isDecimalType(Class propertyClass) {
        return propertyClass == Double.class || propertyClass == Float.class
                || propertyClass == double.class
                || propertyClass == float.class;
    }

    /**
     * Инициализирует редактор свойства. При этом для вещественных свойств
     * устанавливает редактор {@link JFormattedTextField} с {@link NumberFormat}
     * getNumerInstance. Для целых типов устанавливается редактор
     * {@link NumberFormat} getIntegerInstance. Для всех прочих полей
     * устанавливается редактор JTextField.
     * 
     * @param propertyInfo
     * @param value
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @SuppressWarnings("unchecked")
    private void initEditor(PropertyInfo propertyInfo,
            PropertyDescriptor property, Object value)
            throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException {
        PropertyEditorInfo editorInfo = propertyInfo.getEditorInfo();

        String setterName = NameUtils.getSetterName(editorInfo.getPath());

        Class propertyClass = property.getPropertyType();

        assert propertyClass != null;

        // Для некоторых типов полей изменяем редактор
        if (isDecimalType(propertyClass)) {
            DecimalFormat formatter = new DecimalFormat();
            formatter.setMaximumFractionDigits(10);
            formatter.setMinimumFractionDigits(1);
            JFormattedTextField editor = new JFormattedTextField(formatter);
            propertyInfo.setEditor(editor);
            propertyInfo.setConverter(new DoubleConvertor());
        }

        // В случае, если установлен конвертер, преобразуем значение
        if (propertyInfo.hasConverter()) {
            value = propertyInfo.getConverter().convertReverse(value);
        }

        Method setter = editorInfo.getEditorClass().getMethod(setterName,
                editorInfo.getSetterArgumentClass());
        setter.invoke(propertyInfo.getEditor(), value);

        // If property is read only, disable input component
        if (property.getWriteMethod() == null) {
            propertyInfo.getEditor().setEnabled(false);
        }
    }

    /**
     * Возвращает информацию о свойстве по его имени
     * 
     * @param propertyName
     *            имя свойства
     * @return информация о свойстве
     * @throws NoSuchElementException
     *             в случае, если свойство с переданным именем не найдено
     */
    public PropertyInfo getPropertyInfo(String propertyName)
            throws NoSuchElementException {
        if (!propertiesInfo.containsKey(propertyName)) {
            throw new NoSuchElementException("Свойство '" + propertyName
                    + "' не найдено");
        }
        return propertiesInfo.get(propertyName);
    }

    /**
     * Устанавливает новую метку свойству при отображении в панели
     * 
     * @param propertyName
     *            имя свойства
     * @param label
     *            метка для свойства
     * @throws NoSuchElementException
     *             в случае, если свойство не найдено
     */
    public void setLabel(String propertyName, String label)
            throws NoSuchElementException {
        getPropertyInfo(propertyName).setLabel(label);
    }

    /**
     * Возвращает массив с информацией о свойствах. При в массив попадают только
     * переданные свойства с указанными именами. Гарантируется, порядок
     * следования информации о свойствах в списке результата совпадает с
     * порядком пересления свойств в параметре функции.
     * 
     * @param propertyNames
     *            список свойств, для который будет возвращен список описаний
     * @return список описаний свойств
     */
    public List<PropertyInfo> getPropertiesList(String... propertyNames) {
        List<PropertyInfo> result = new ArrayList<PropertyInfo>(
                propertyNames.length);
        for (String propertyName : propertyNames) {
            result.add(getPropertyInfo(propertyName));
        }
        return result;
    }

    /**
     * Возвращает количество найденных свойств в javabeans
     * 
     * @return
     */
    public int size() {
        return propertiesInfo.size();
    }

    /**
     * Возвращает, содержатся свойство с указанным именем или нет
     * 
     * @param propertyName
     *            имя свойство
     * @return истина, если есть информация об указанном свойстве
     */
    public boolean contains(String propertyName) {
        return propertiesInfo.containsKey(propertyName);
    }
}
