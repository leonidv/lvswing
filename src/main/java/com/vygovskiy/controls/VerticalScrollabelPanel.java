package com.vygovskiy.controls;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.JPanel;
import javax.swing.Scrollable;

/**
 * Класс реализует панель, которая растягивается только по вертикали. По умолчанию 
 * устанавливается собственный менеджер раскладок (улучшенная версия FlowLayout).
 * <br/>
 * Код взят отсюда: <br/>
 * http://forum.java.sun.com/thread.jspa?threadID=780453&messageID=4440133
 * @author leonidv
 *
 */
public class VerticalScrollabelPanel extends JPanel implements Scrollable {
    private static final long serialVersionUID = 4658119096935312244L;

    static class BetterFlowLayout extends FlowLayout {
        private static final long serialVersionUID = 6202902676438407910L;

        public BetterFlowLayout() {
            super();
        }

        public BetterFlowLayout(int align) {
            super(align);
        }

        public BetterFlowLayout(int align, int hgap, int vgap) {
            super(align, hgap, vgap);
        }

        @Override
        public Dimension preferredLayoutSize(Container target) {
            return betterPreferredSize(target);
        }

        @Override
        public Dimension minimumLayoutSize(Container target) {
            return betterPreferredSize(target);
        }
                
        public Dimension betterPreferredSize(Container target) {
            synchronized (target.getTreeLock()) {
                Insets insets = target.getInsets();
                int maxwidth = target.getWidth()
                        - (insets.left + insets.right + getHgap() * 2);
                int nmembers = target.getComponentCount();
                int x = 0, y = insets.top + getVgap();
                int rowh = 0;

                for (int i = 0; i < nmembers; i++) {
                    Component m = target.getComponent(i);
                    if (m.isVisible()) {
                        Dimension d = m.getPreferredSize();
                        m.setSize(d.width, d.height);

                        if ((x == 0) || ((x + d.width) <= maxwidth)) {
                            if (x > 0) {
                                x += getHgap();
                            }
                            x += d.width;
                            rowh = Math.max(rowh, d.height);
                        } else {
                            x = d.width;
                            y += getVgap() + rowh;
                            rowh = d.height;
                        }
                    }
                }
                return new Dimension(maxwidth, y + rowh + getVgap());
            }
        }
    }        
    
    /**
     * Переопределяем конструктор так, чтобы по умолчанию использовалась улучшенная
     * версия менедежра раскладок
     */
    public VerticalScrollabelPanel() {
        super(new BetterFlowLayout());
    }

    
    public Dimension getPreferredScrollableViewportSize() {
        return getPreferredSize();
    }

    public int getScrollableUnitIncrement(Rectangle visibleRect,
            int orientation, int direction) {
        return 20;
    }

    public int getScrollableBlockIncrement(Rectangle visibleRect,
            int orientation, int direction) {
        return 60;
    }

    public boolean getScrollableTracksViewportWidth() {
        return true;
    }

    public boolean getScrollableTracksViewportHeight() {
        return false;
    }
}
