package com.vygovskiy.controls;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.JComponent;

public class SwingUtils {

    /**
     * Set new window size and align window by screen center.
     * 
     * @param window
     * @param width
     * @param height
     * @see #center(Window)
     */
    public static void center(Window window, int width, int height) {
        window.setSize(width, height);
        center(window);
    }

    /**
     * Center window by screen center. 
     * <p>
     * In fact, it's not center. Golden ratio is used for position calculation.
     * 
     * @param window
     */
    public static void center(Window window) {
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
     *            компонент, которому требуется полностью задать параметр
     *            блокировки
     * @param enabled
     *            блокировать или разблокировать компоненты
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
