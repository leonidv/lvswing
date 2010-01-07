package com.vygovskiy.controls.demo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.vygovskiy.controls.SwingUtils;
import com.vygovskiy.controls.checklistpanel.ChecklistLayout;
import com.vygovskiy.controls.checklistpanel.ChecklistPanel;


public class ChecklistLayoutDemo extends JFrame {
	private static final long serialVersionUID = -6591968942554712201L;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ChecklistLayoutDemo frame = new ChecklistLayoutDemo();
		SwingUtils.centerMainFrame(frame, 600, 630);
		frame.setTitle("Checklist panel demo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public ChecklistLayoutDemo() {
		initComponents();
	}

	private void initComponents() {
		final ChecklistPanel examplePanel = new ChecklistPanel();
		examplePanel.setBorder(BorderFactory.createLineBorder(Color.RED));
		examplePanel.setMaximumSize(new Dimension(200, 200));

		final ChecklistLayout layout = (ChecklistLayout) examplePanel
				.getLayout();
		layout.setRowInsent(10);

		for (int i = 0; i < 9; i++) {
			if (i % 3 == 0) {
				examplePanel.addTitle(String.format("Row group %d", i / 3 + 1));
				examplePanel.addRow("Very-very long label", new JTextField());
			}

			examplePanel.addRow(String.format("Label %d", i + 1),
					new JTextField());
		}

		ChecklistPanel controlPanel = new ChecklistPanel();

		controlPanel.addTitle("Checklist panel controls");

		JSlider rowInsentSlider = createSlider(0, 60, layout.getRowInsent());
		rowInsentSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				layout.setRowInsent(source.getValue());
				examplePanel.revalidate();
			}
		});
		controlPanel.addRow("Row insent", rowInsentSlider);

		JSlider rowGapSlider = createSlider(0, 20, layout.getRowGap());
		rowGapSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				layout.setRowGap(source.getValue());
				examplePanel.revalidate();
			}
		});
		controlPanel.addRow("Row gap", rowGapSlider);

		JSlider columnGapSlider = createSlider(0, 60, layout.getColumnGap());
		columnGapSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				layout.setColumnGap(source.getValue());
				examplePanel.revalidate();
			}
		});
		controlPanel.addRow("Column gap", columnGapSlider);

		JSlider titleInsetSlider = createSlider(0, 60, layout.getTitleInsent());
		titleInsetSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				layout.setTitleInsent(source.getValue());
				examplePanel.revalidate();
			}
		});
		controlPanel.addRow("Title insent", titleInsetSlider);

		JSlider titleGapSlider = createSlider(0, 60, layout.getTitleGap());
		titleGapSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				layout.setTitleGap(source.getValue());
				examplePanel.revalidate();
			}
		});
		controlPanel.addRow("Title gap", titleGapSlider);

		JScrollPane scrollPane = new JScrollPane(examplePanel);
		this.setLayout(new BorderLayout());
		this.add(scrollPane, BorderLayout.CENTER);
		this.add(controlPanel, BorderLayout.SOUTH);
		int panelsHeight = 
			examplePanel.getLayout().preferredLayoutSize(examplePanel).height
			+ controlPanel.getLayout().preferredLayoutSize(controlPanel).height;
		this.setPreferredSize(new Dimension(600, panelsHeight));
	}

	private JSlider createSlider(int min, int max, int initialValue) {
		JSlider slider = new JSlider(min, max, initialValue);
		slider.setMajorTickSpacing((max - min) / 5);
		slider.setMinorTickSpacing(slider.getMajorTickSpacing() / 4);

		slider.setPaintLabels(true);
		slider.setSnapToTicks(true);
		slider.setPaintTicks(true);
		return slider;
	}
}
