package com.vygovskiy.controls.checklistpanel;

import java.awt.Insets;
import java.awt.Rectangle;

interface ComponentRow { 
	/**
	 * Определяет предпочитаемую высоту строки компонент. При этом отступы снизу
	 * и сверху не учитываются.
	 * 
	 * @return - препочитаемая высота строки
	 */
	int getPrefferedHeight();

	/**
	 * Возвращает предпочитаемую ширину строки компонент.
	 * 
	 * @return - предпочитаемая ширина строки компонентов
	 */
	int getPrefferedWidth();

	/**
	 * Возвращает минимальную ширину строки компонентов. При этом величина должна
	 * содержать оступы слева и справа.
	 */
	int getMinimumWidth();

	/**
	 * Возвращает предпочитаемую ширину метки.
	 * 
	 * @return
	 */
	int getPrefferedLabelWidth();

	/**
	 * Устанавливает компоненты в заданных границах.
	 * 
	 * @param bounds -
	 *            граница, в которой нужно разместить компоненты.
	 * @param labelWidth -
	 *            ширина колонки с меткой.
	 * @param hgap -
	 *            расстояние между колонкой с меткой и колонкой с редактором.
	 */
	void layout(Rectangle bounds, int labelWidth, int hgap);

	/**
	 * Возвращает отступ для строки.
	 */
	Insets getInsets();
}
