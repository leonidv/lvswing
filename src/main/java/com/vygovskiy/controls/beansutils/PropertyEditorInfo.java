package com.vygovskiy.controls.beansutils;

import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;

import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Property;

/**
 * Contains information about editor for property.
 * 
 * @author leonidv
 * 
 * @param <E>
 */
@SuppressWarnings("unchecked")
final public class PropertyEditorInfo<E extends JComponent> {
    /**
     * Create simple text editor. Linked with {@link JTextField}
     */
    final public static PropertyEditorInfo<JTextField> createStringEditorInfo() {
        return new PropertyEditorInfo<JTextField>(JTextField.class, "text",
                String.class);
    }

    /**
     * Formatted input for integers, doubles and etc. Linked with
     * {@link JFormattedTextField}
     */
    final public static PropertyEditorInfo<JFormattedTextField> createIntegerEditorInfo() {
        return new PropertyEditorInfo<JFormattedTextField>(
                JFormattedTextField.class, "value", Object.class);
    }
    
    /**
     * Create formatted input for decimal values. Linked with {@link JFormattedTextField}.
     * @return
     */
    final public static PropertyEditorInfo<JFormattedTextField> createDecimalEditorInfo() {
        return  new PropertyEditorInfo<JFormattedTextField>(
                JFormattedTextField.class, "text", String.class);
    }

    /**
     * Boolean input. Linked with {@link JCheckBox}
     */
    public static final PropertyEditorInfo<JCheckBox> createBooleanEditorInfo() {
        return new PropertyEditorInfo<JCheckBox>(JCheckBox.class, "selected",
                boolean.class);
    }

    /**
     * Editor's class.
     */
    final private Class<E> editorClass;

    /**
     * Editor's property that binding with bean.
     */
    final private Property editorProperty;

    /**
     * Path of editor's property.
     */
    final private String path;

    /**
     * The class of value setter method.
     */
    final private Class setterArgumentClass;

    /**
     * Создает описание редактора компонент
     * 
     * @param editorClass
     *            class of property editor.
     * @param editorProperty
     *            path in EL notation to editor's property that will bind with
     *            bean's property
     * @param setterArgumentClass
     *            the class of setter editor's value's argument.
     * 
     */
    public PropertyEditorInfo(Class<E> editorClass, String propertyPath,
            Class setterArgumentClass) {
        this.editorClass = editorClass;
        this.editorProperty = BeanProperty.create(propertyPath);
        this.path = propertyPath;
        this.setterArgumentClass = setterArgumentClass;
    }

    /**
     * Create new object of editor with default constructor.
     * 
     * @return
     * 
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     */
    public E getEditorInstance() throws InstantiationException,
            IllegalAccessException, SecurityException, NoSuchMethodException,
            IllegalArgumentException, InvocationTargetException {
        return editorClass.getConstructor().newInstance();
    }

    /**
     * @return the class of property's editor
     */
    public Class<E> getEditorClass() {
        return editorClass;
    }

    /**
     * @return property binding description
     */
    public Property getEditorProperty() {
        return editorProperty;
    }

    /**
     * @return the path of editor's property
     */
    public String getPath() {
        return path;
    }

    /**
     * Возвращает точный класс аргумента установки значения.<br/>
     * 
     * Это необходимо, т.к. при поиске метода с помощью рефлексии передается
     * точный класс сигнатуры метода. Потомки не подходят.
     * 
     * @return
     */
    public Class getSetterArgumentClass() {
        return setterArgumentClass;
    }

    public String toString() {
        return editorClass.getName() + " " + path;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((editorClass == null) ? 0 : editorClass.hashCode());
        result = prime * result + ((path == null) ? 0 : path.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final PropertyEditorInfo other = (PropertyEditorInfo) obj;
        if (editorClass == null) {
            if (other.editorClass != null)
                return false;
        } else if (!editorClass.equals(other.editorClass))
            return false;
        if (path == null) {
            if (other.path != null)
                return false;
        } else if (!path.equals(other.path))
            return false;
        return true;
    }

}
