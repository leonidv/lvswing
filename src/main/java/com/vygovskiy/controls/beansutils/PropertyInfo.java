package com.vygovskiy.controls.beansutils;

import java.lang.reflect.InvocationTargetException;

import javax.swing.JComponent;

import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Converter;
import org.jdesktop.beansbinding.Property;
import org.jdesktop.beansbinding.Validator;

/**
 * Хранит представление описания одного свойства. Необходимо для передачи
 * информации при создании панели на основе JavaBeans.
 * 
 * @author leonidv
 * 
 */
final public class PropertyInfo {
    // Преобразователь свойства
    private Converter converter;

    // Swing-компонент, отвечающий за редактор свойства
    private JComponent editor;

    // Описание редактора свойства
    private PropertyEditorInfo<? extends JComponent> editorInfo;

    // Описание свойства beans, которое будет привязано к редактору
    private Property propertyBean;

    // Имя свойства
    private String label;

    // Валидатор свойства
    private Validator validator;

    // Имя свойства
    final private String name;

    /**
     * Создает информацию о свойстве beans
     * 
     * @param label
     *            имя свойства, отображаемое в при выводе на экран
     * @param editorInfo
     *            описание редактора свойства
     * @param propertyBean
     *            имя свойства у объекта
     * 
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws IllegalArgumentException
     * @throws SecurityException
     */
    public PropertyInfo(String label,
            PropertyEditorInfo<? extends JComponent> editorInfo,
            String propertyBean) throws InstantiationException,
            IllegalAccessException, SecurityException,
            IllegalArgumentException, NoSuchMethodException,
            InvocationTargetException {
        this.label = label;
        this.editorInfo = editorInfo;
        this.propertyBean = BeanProperty.create(propertyBean);
        this.editor = editorInfo.getEditorInstance();
        this.name = propertyBean;
    }

    /**
     * Возвращает конвертер значения
     * 
     * @return
     */
    public Converter getConverter() {
        return converter;
    }

    /**
     * Возвращает редактор, сопоставленный
     * 
     * @return
     */
    public JComponent getEditor() {
        return editor;
    }

    /**
     * Устанавливает редактор компонента. При этом делается проверка, что
     * редактор компонента является подклассом
     * 
     * @param editor
     * @throws IllegalArgumentException
     */
    public void setEditor(JComponent editor) throws IllegalArgumentException {
        if (!editorInfo.getEditorClass().isAssignableFrom(editor.getClass())) {
            final String message = String.format(
                    "Given editor class [%s] doesn't assignable to"
                            + "property info editor [%s]", editor.getClass()
                            .getCanonicalName(), editorInfo.getClass()
                            .getCanonicalName());
            
            new IllegalArgumentException(message);
        }
        this.editor = editor;
    }

    /**
     * Возвращает информацию о редакторе свойства
     * 
     * @return
     */
    public PropertyEditorInfo<? extends JComponent> getEditorInfo() {
        return editorInfo;
    }

    /**
     * Возвращает описание свойства beans
     * 
     * @return
     */
    public Property getPropertyBean() {
        return propertyBean;
    }

    /**
     * Возвращает описание свойства
     * 
     * @return
     */
    public String getLabel() {
        return label;
    }

    /**
     * Возвращает валидатор значения
     * 
     * @return
     */
    public Validator getValidator() {
        return validator;
    }

    /**
     * Возвращает истину, если установлен конвертер
     * 
     * @return
     */
    public boolean hasConverter() {
        return converter != null;
    }

    /**
     * Возвращает истину, если установлен валидатора
     * 
     * @return
     */
    public boolean hasValidator() {
        return validator != null;
    }

    /**
     * Устанавливает конвертер значения
     * 
     * @param converter
     */
    public void setConverter(Converter converter) {
        this.converter = converter;
    }

    /**
     * Возвращает имя свойства в нотации JavaBeans
     * 
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Устанавливает информацию о редакторе
     * 
     * @param editorInfo
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws IllegalArgumentException
     * @throws SecurityException
     */
    public void setEditorInfo(
            PropertyEditorInfo<? extends JComponent> editorInfo)
            throws SecurityException, IllegalArgumentException,
            InstantiationException, IllegalAccessException,
            NoSuchMethodException, InvocationTargetException {
        editor = editorInfo.getEditorInstance();
        this.editorInfo = editorInfo;
    }

    /**
     * Устанавливает описание свойства
     * 
     * @param label
     */
    public void setLabel(String title) {
        this.label = title;
    }

    /**
     * Устанавливает валидатор значения
     * 
     * @param validator
     */
    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final PropertyInfo other = (PropertyInfo) obj;
        if (editor == null) {
            if (other.editor != null)
                return false;
        } else if (!editor.equals(other.editor))
            return false;
        if (editorInfo == null) {
            if (other.editorInfo != null)
                return false;
        } else if (!editorInfo.equals(other.editorInfo))
            return false;
        if (propertyBean == null) {
            if (other.propertyBean != null)
                return false;
        } else if (!propertyBean.equals(other.propertyBean))
            return false;
        if (label == null) {
            if (other.label != null)
                return false;
        } else if (!label.equals(other.label))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((editor == null) ? 0 : editor.hashCode());
        result = prime * result
                + ((editorInfo == null) ? 0 : editorInfo.hashCode());
        result = prime * result
                + ((propertyBean == null) ? 0 : propertyBean.hashCode());
        result = prime * result + ((label == null) ? 0 : label.hashCode());
        return result;
    }

    /**
     * Реализует строковое представление информации о свойстве
     */
    public String toString() {
        return "label = '" + getLabel() + "' " + propertyBean;
    }

}
