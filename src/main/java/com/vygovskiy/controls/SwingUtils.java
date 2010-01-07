package com.vygovskiy.controls;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.JComponent;

public class SwingUtils {

    /**
     * Осуществляет задание начальных размеров и позиции формы на экране. Форма
     * размещается в центре окна по ширине и по золотому сечению окна по высоте.
     * 
     * @param window -
     *          форма, которая будет размещена
     * @param width -
     *          устанавливаемая ширина формы
     * @param height -
     *          устанавливаемая высота формы
     */
    public static void centerMainFrame(Window window, int width, int height) {       
        window.setSize(width, height);
        centerMainFrame(window);
    }
    
    /**
     * Осуществляет задания начальной позиции для переданной формы. При этом
     * размеры формы не изменяются
     * @param window
     */
    public static void centerMainFrame(Window window) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        window.setLocation((screenSize.width - window.getWidth()) / 2,
                (screenSize.height - window.getHeight()) / 3);
        
    }

    /**
     * Осуществляет блокировку не только самого компонента, но и всех
     * компонентов, которые принадлежат ему. <br/>
     * 
     * Метод работает рекурсивно.
     * 
     * @param component
     *          компонент, которому требуется полностью задать параметр
     *          блокировки
     * @param enabled
     *          блокировать или разблокировать компоненты
     */
    public static void setEnabledComponents(JComponent component,
            boolean enabled) {
        if (component.getComponentCount() == 0) {
            component.setEnabled(enabled);
        } else {
            for (int i = 0; i < component.getComponentCount(); i++) {
                if (component.getComponent(i) instanceof JComponent) {
                    setEnabledComponents(
                            (JComponent) component.getComponent(i), enabled);
                }
            }
        }
    }
}
