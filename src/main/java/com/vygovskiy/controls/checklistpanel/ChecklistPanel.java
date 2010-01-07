package com.vygovskiy.controls.checklistpanel;

import java.awt.Component;
import java.awt.LayoutManager;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;

import com.vygovskiy.controls.beansutils.PropertyEditorInfo;
import com.vygovskiy.controls.beansutils.PropertyInfo;


public class ChecklistPanel extends JPanel {
    private static final long serialVersionUID = -2330219714106943117L;

    private static final Object MARKER_CONSTRAINTS = new Object();

    private ChecklistLayout layout = new ChecklistLayout();

    /**
     * Create checklist panel. Layout mananger initialize to inner
     * ChecklistLayout and cann't be changed.
     * 
     */
    public ChecklistPanel() {
        super(new ChecklistLayout());
        layout = (ChecklistLayout) this.getLayout();
    }

    @Override
    public void setLayout(LayoutManager layout) {
        if (!(layout instanceof ChecklistLayout)) {
            throw new UnsupportedOperationException(
                    "Checklist panel use internal layout manager."
                            + "You cann't change this.");
        }

        super.setLayout(layout);
    }

    /**
     * Переопределяем данный метод так, чтобы объект можно было добавить только
     * из этого класса.
     */
    @Override
    protected void addImpl(Component comp, Object constraints, int index) {
        if (constraints != MARKER_CONSTRAINTS) {
            throw new UnsupportedOperationException(
                    "Don't use Containter.add(*) methods.\n"
                            + "Please, use addRow and addTitle methods.");
        }
        super.addImpl(comp, constraints, index);
    }

    /**
     * Добавляет на панель строку с меткой и редактором.
     * 
     * @param label -
     *          метка
     * @param editor -
     *          редактор
     */
    public void addRow(JComponent label, JComponent editor) {
        layout.addRow(label, editor);
        add(label, MARKER_CONSTRAINTS);
        add(editor, MARKER_CONSTRAINTS);
    }

    /**
     * Добавляет на панель редактор с заданной текстовой меткой.
     * 
     * @param label -
     *          текст метки к редактору
     * @param editor -
     *          редактор
     */
    public void addRow(String label, JComponent editor) {
        addRow(new JLabel(label), editor);
    }

    /**
     * Добавляет на панель разделитель групп
     * 
     * @param title -
     *          компонента разделителя
     */
    public void addTitle(JComponent title) {
        layout.addTitle(title);
        add(title, MARKER_CONSTRAINTS);
    }

    /**
     * Добавляет на панель текстовый разделитель групп
     * 
     * @param title -
     *          компонента разделителя
     */
    public void addTitle(String title) {
        addTitle(new JLabel(title));
    }

    /**
     * Создает связывание между Swing-редактором и
     * 
     * @param bean
     * @param propertyInfo
     * @return
     */
    @SuppressWarnings("unchecked")
    private Binding createBinding(Object bean, PropertyInfo propertyInfo) {
        final PropertyEditorInfo editorInfo = propertyInfo.getEditorInfo();
               
        Binding binding = Bindings.createAutoBinding(
                AutoBinding.UpdateStrategy.READ_WRITE,
                propertyInfo.getEditor(), editorInfo.getEditorProperty(), bean,
                propertyInfo.getPropertyBean());

        if (propertyInfo.hasConverter()) {
            binding.setConverter(propertyInfo.getConverter());
        }
        if (propertyInfo.hasValidator()) {
            binding.setValidator(propertyInfo.getValidator());            
        }
        addRow(propertyInfo.getLabel(), propertyInfo.getEditor());        
        return binding;
    }

    /**
     * Добавляет на панель свойство компоненты, связанное с редактором. При
     * изменение текста в редакторе, будет изменяться связанное значение поля.
     * 
     * @param bean
     *          JavaBeans, для которого будет связан редактор
     * @param propertyInfo
     *          информация о свойстве, необходимая для связывания
     */
    public void addBinding(Object bean, PropertyInfo propertyInfo) {
        createBinding(bean, propertyInfo).bind();
        System.out.println("add binding "+propertyInfo.getLabel());
    }

    /**
     * Добавляет список связанных свойств переданного объекта на панель.
     * 
     * @param bean
     *          JavaBeans, для свойств которого будут созданы связи с
     *          редакторами
     * @param propertyInfos
     *          информация о связываемых свойствах
     */
    public void addBindings(Object bean, List<PropertyInfo> propertyInfos) {
        BindingGroup group = new BindingGroup();
        for (PropertyInfo propertyInfo : propertyInfos) {
            group.addBinding(createBinding(bean, propertyInfo));
        }
        group.bind();
    }
}
