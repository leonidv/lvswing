package com.vygovskiy.controls.fileedit;

import java.beans.PropertyEditorSupport;

/**
 * Класс содержит простой редактор свойства ButtonPosition компоненты FileEdit.
 * 
 * @author leonidv
 * 
 */
public class ButtonPositionEditor extends PropertyEditorSupport {
    final private String[] tags;

    public ButtonPositionEditor() {
        
        // Формируем текстового представления ButtonPosition, чтобы 
        // потом возвращать его в качестве меток
        ButtonPosition[] values = ButtonPosition.values();        
        tags = new String[values.length];        
        for (int i = 0; i < values.length; i++){
            tags[i] = values[i].toString();
        }
        
    }
    
    @Override
    public String getAsText() {
        return getValue().toString();
    }

    @Override
    public String[] getTags() {
        return tags;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        setValue(ButtonPosition.valueOf(text));
    }

    
    @Override
    public String getJavaInitializationString() {
        return "ButtonPosition."+getValue().toString();
    }

}
