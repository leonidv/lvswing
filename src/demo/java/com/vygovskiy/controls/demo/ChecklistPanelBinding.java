package com.vygovskiy.controls.demo;

import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.swingbinding.JComboBoxBinding;
import org.jdesktop.swingbinding.SwingBindings;

import com.vygovskiy.controls.SwingUtils;
import com.vygovskiy.controls.beansutils.BeanPropertiesInfo;
import com.vygovskiy.controls.beansutils.PropertyEditorInfo;
import com.vygovskiy.controls.beansutils.PropertyInfo;
import com.vygovskiy.controls.binding.IntegerConverter;
import com.vygovskiy.controls.binding.IntegerValidator;
import com.vygovskiy.controls.checklistpanel.ChecklistPanel;
import com.vygovskiy.controls.demo.model.Car;
import com.vygovskiy.controls.demo.model.Person;


public class ChecklistPanelBinding extends JFrame {
    private static final long serialVersionUID = -2266104277856258513L;

    private Person person = new Person();

    public static void main(String[] args) {
        ChecklistPanelBinding frame = new ChecklistPanelBinding();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SwingUtils.center(frame);
        frame.setVisible(true);
    }

    public ChecklistPanelBinding() throws HeadlessException {
        super("Checklist binding example");
        init();
        setSize(400, 300);
        // this.pack();
    }

    private void init() {
        try {
            ChecklistPanel panel;
            panel = createPersonPanel();

            this.setLayout(new BorderLayout());
            JPanel centralPanel = new JPanel(new GridLayout(2, 1));

            this.add(centralPanel, BorderLayout.NORTH);

            centralPanel.add(panel);

            JComboBox carsComboBox = new JComboBox();

            JComboBoxBinding<Car, List<Car>, JComboBox> boxBinding = SwingBindings
                    .createJComboBoxBinding(UpdateStrategy.READ_WRITE, Car
                            .getCars(), carsComboBox);
            boxBinding.bind();
            centralPanel.add(carsComboBox);

            JButton testButton = new JButton("Print person to console");
            testButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println(person);
                }
            });

            this.add(testButton, BorderLayout.SOUTH);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ChecklistPanel createPersonPanel() throws RuntimeException,
            InstantiationException, IllegalAccessException,
            NoSuchMethodException, InvocationTargetException {
        ChecklistPanel panel = new ChecklistPanel();
        BeanPropertiesInfo<Person> beanInfo;
        try {
            beanInfo = new BeanPropertiesInfo<Person>(person);
            beanInfo.setLabel("firstName", "Имя");
            beanInfo.setLabel("lastName", "Фамилия");
            beanInfo.setLabel("age", "Возраст");
            beanInfo.setLabel("rate", "Рейтинг");
            beanInfo.setLabel("married", "Состоит в браке:");

            List<PropertyInfo> propertiesInfo = beanInfo.getPropertiesList(
                    "firstName", "lastName", "age", "rate", "married");

            panel.addBindings(person, propertiesInfo);
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return panel;
    }
}
