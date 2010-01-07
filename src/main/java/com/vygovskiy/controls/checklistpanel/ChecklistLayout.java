package com.vygovskiy.controls.checklistpanel;

import static java.lang.Math.*;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

/**
 * Менеджер раскладок, разработанный для создания окон с анкетными данными.
 * Позволяет легко организовывать поля ввода и текстовые ссылки к ним.
 * 
 * !!! Разделяет всю область на две колонки - левая колонка служит для
 * отображения пояснений к полям ввода. Правая колонка служит для ввода данных.
 * При расчете размеров колонки первичной считается колонка с текстом. Ее ширина
 * рассчитывается как наименьшее между двумя величинами. Первая велечина -
 * наибольшее значение из минимальных значений текстовых компонент. Вторая
 * величина - округленный результат умножения ширины компоненты на
 * 
 * Вторая колонка заполняет оставшуюся часть от первой. Между колонками
 * 
 * @author leonidv
 * 
 */
public class ChecklistLayout implements LayoutManager {
	// Список строк с компонентами
	private List<ComponentRow> rows = new ArrayList<ComponentRow>();

	// Разделитель между колонкой меток и колонкой текста
	private int columnGap = 3;

	// Разделитель, вставляемый после строк с данными
	private int rowGap = 3;

	// Отступ, вставляемый перед строкой, если она идет после заголовка
	private int rowInsent = 0;

	// Отступ, вставляемый перед заголовком группы
	private int titleInsent = 0;

	// Разделитель, вставляемый перед заголовком группы
	private int titleGap = 15;

	// Коэфициент, который задает размер колонки с метками
	private float columnRatio = 0.33f;

	public void addLayoutComponent(String name, Component comp) {
	}

	public void layoutContainer(Container parent) {
		synchronized (parent.getTreeLock()) {

			Insets parentInsets = parent.getInsets();
			// Получение размеров контейнера
			int parentWidth;
			parentWidth = parent.getSize().width - parentInsets.left
					- parentInsets.right;

			Rectangle bounds = new Rectangle();
			bounds.x = parentInsets.left;
			bounds.width = parentWidth;
			bounds.y = parentInsets.top;

			// Вычисляем размер столбца меток. Если ширина меток больше
			// заданного размера, берется заданный размер.
			int ratioWidth = round(parentWidth * columnRatio);
			int labelWidth = min(ratioWidth, getPrefferedLabelColumnWidth());

			/*
			 * Перебираем компоненты и задаем им размеры.
			 */
			for (ComponentRow row : rows) {
				row.layout(bounds, labelWidth, columnGap);
				bounds.y += row.getPrefferedHeight();
			}
		}
	}

	/**
	 * Определяет оптимальную ширину для колонки с метками. При этом такой
	 * шириной является максимальный найденный размер метки.
	 * 
	 * @return предпочитаемый на основе ширины меток размер столбца меток
	 */
	private int getPrefferedLabelColumnWidth() {
		int result = 0;
		for (ComponentRow row : rows) {
			result = max(result, row.getPrefferedLabelWidth());
		}
		return result;
	}

	public Dimension minimumLayoutSize(Container parent) {
		int width = parent.getInsets().right + parent.getInsets().left;
		int height = parent.getInsets().top + parent.getInsets().bottom;

		for (ComponentRow row : rows) {
			height += row.getPrefferedHeight();
			width += row.getMinimumWidth();
		}

		return new Dimension(width, height);
	}

	public Dimension preferredLayoutSize(Container parent) {
		int horizontalInsets = parent.getInsets().right
				+ parent.getInsets().left;
		int height = parent.getInsets().top + parent.getInsets().bottom;

		int width = 0;
		for (ComponentRow row : rows) {
			height += row.getPrefferedHeight();
			width = max(width, row.getPrefferedWidth());
		}
		return new Dimension(width + horizontalInsets, height);
	}

	public void removeLayoutComponent(Component comp) {
		// TODO Auto-generated method stub

	}

	/**
	 * Добавляет строку компонент в разметку. Данный метод должен вызываться
	 * только из ChecklistPanel
	 * 
	 * @param label -
	 *            метка
	 * @param editor -
	 *            редактор
	 */
	void addRow(JComponent label, JComponent editor) {
		ComponentRow row = new LabeledEditorRow(label, editor, this);
		rows.add(row);
	}

	/**
	 * Добавляет компоненту в виде заголовка в разметку. Данный метод должен
	 * вызываться только из ChecklistPanel.
	 * 
	 * @param label -
	 *            компонент, используемый в качестве заголовка
	 */
	void addTitle(JComponent label) {
		ComponentRow row = new Title(label, this);
		rows.add(row);
	}

	boolean isFirstRow(ComponentRow row) {
		return rows.get(0) == row;
	}
	
	public int getColumnGap() {
		return columnGap;
	}

	public void setColumnGap(int columnGap) {
		this.columnGap = columnGap;
	}

	public float getColumnRatio() {
		return columnRatio;
	}

	public void setColumnRatio(float columnRatio) {
		this.columnRatio = columnRatio;
	}

	public int getRowInsent() {
		return rowInsent;
	}

	public void setRowInsent(int insentRow) {
		this.rowInsent = insentRow;
	}

	public int getTitleInsent() {
		return titleInsent;
	}

	public void setTitleInsent(int titleInsent) {
		this.titleInsent = titleInsent;
	}

	public int getRowGap() {
		return rowGap;
	}

	public void setRowGap(int rowGap) {
		this.rowGap = rowGap;
	}

	public int getTitleGap() {
		return titleGap;
	}

	public void setTitleGap(int titleGap) {
		this.titleGap = titleGap;
	}

}
