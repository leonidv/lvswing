package com.vygovskiy.controls.checklistpanel;

import java.awt.Component;
import java.awt.Insets;
import java.awt.Rectangle;
import static java.lang.Math.max;

class LabeledEditorRow implements ComponentRow {
	// Ссылка на менеджр раскладки, который содержит строку
	ChecklistLayout owner;

	// Ссылка на компонент, который считается меткой
	Component label;

	// Ссылка на компонент, который считается редактором
	Component editor;

	/**
	 * Создает строку компонент. На вход принимает компонент метки и редактора
	 * 
	 * @param label
	 *            компонента, играющая рольк метки
	 * @param editor
	 *            компонента, играющая роль редактора
	 */
	LabeledEditorRow(Component label, Component editor, ChecklistLayout owner) {
		super();
		this.label = label;
		this.editor = editor;
		this.owner = owner;
	}

	public int getPrefferedWidth() {
		int componentsWidth = label.getPreferredSize().width
				+ editor.getPreferredSize().width;

		return componentsWidth + getHorizontalInsetsSum();
	}

	public int getPrefferedHeight() {
		int componentHeight = max(label.getPreferredSize().height, editor
				.getPreferredSize().height);

		return componentHeight + verticalInsetsSum();
	}

	/**
	 * Возвращает сумму верхнего и нижнего отступа
	 * 
	 * @return - сумма верхнего и нижнего отступа
	 */
	private int verticalInsetsSum() {
		int verticalInsets = getInsets().bottom + getInsets().top;
		return verticalInsets;
	}

	public int getMinimumWidth() {
		final int componentsWidth = label.getMinimumSize().width
				+ editor.getMinimumSize().width;
		
		return componentsWidth + getHorizontalInsetsSum();
	}

	/**
	 * @return - сумма левого и правого отступа
	 */
	private int getHorizontalInsetsSum() {
		return getInsets().left + getInsets().right;
	}

	public void layout(Rectangle bounds, int labelWidth, int hgap) {
		Rectangle labelBounds = new Rectangle();
		Rectangle editorBounds = new Rectangle();
		
		Insets insets = getInsets();
		
		// Все компоненты находятся на одной линии
		labelBounds.y = editorBounds.y = bounds.y;
		
		// Метка отступает от крайнего левого значения
		labelBounds.x = bounds.x + insets.left; 
		labelBounds.width = labelWidth - insets.left;
		labelBounds.height = label.getPreferredSize().height;

		editorBounds.x = labelBounds.width + hgap + labelBounds.x;
		editorBounds.width = bounds.width - (editorBounds.x + getInsets().right);
		editorBounds.height = editor.getPreferredSize().height;

		label.setBounds(labelBounds);
		editor.setBounds(editorBounds);
	}

	public int getPrefferedLabelWidth() {
		return label.getPreferredSize().width + getInsets().left;
	}

	public Insets getInsets() {
		Insets result = new Insets(0, owner.getRowInsent(), owner.getRowGap(),
				0);
		return result;
	}

}
