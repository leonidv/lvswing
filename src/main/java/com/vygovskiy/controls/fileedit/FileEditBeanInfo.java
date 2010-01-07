package com.vygovskiy.controls.fileedit;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

/**
 * Описывает класс с информацией о Bean.
 * 
 * @author leonidv
 * 
 */
public class FileEditBeanInfo extends SimpleBeanInfo {
    public static void main(String[] args) {
        FileEditBeanInfo info = new FileEditBeanInfo();
        for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
            System.out.println(pd.getName());
        }
    }

    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        try {
            List<PropertyDescriptor> result = new ArrayList<PropertyDescriptor>();
            
            /*
             * Позиция кнопки относительно поля ввода.
             */
            PropertyDescriptor buttonPosition = new PropertyDescriptor(
                    "buttonPosition", FileEdit.class);
            buttonPosition.setPropertyEditorClass(ButtonPositionEditor.class);
            buttonPosition.setDisplayName("Button position");
            result.add(buttonPosition);
            
            /*
             * Заголовок кнопки.
             */
            PropertyDescriptor buttonText = new PropertyDescriptor(
                    "buttonText", FileEdit.class);
            buttonPosition.setDisplayName("Button text");
            result.add(buttonText);
            
            /*
             * Добавляем описание свойств, унаследованных от панели. При этом
             * прячеться свойство установки Layout и свойство установки цвета
             * панели (его все равно не видно).
             */
            PropertyDescriptor[] panelProperties = Introspector.getBeanInfo(
                    JPanel.class).getPropertyDescriptors();

            for (PropertyDescriptor descriptor : panelProperties) {

                if (!descriptor.getName().equalsIgnoreCase("Layout")
                        && !descriptor.getName().equalsIgnoreCase("Background")) {
                    result.add(descriptor);
                }
            }

            return result.toArray(new PropertyDescriptor[result.size()]);
        } catch (IntrospectionException e) {
            e.printStackTrace();
            return null;
        }
    }

}
