package com.vygovskiy.controls.checklistpanel;

import java.awt.Component;
import java.awt.Insets;
import java.awt.Rectangle;

/**
 * Определение строки разделителя секций (заголовка секции).
 * 
 * @author leonidv
 * 
 */
class Title implements ComponentRow {
	// Ссылка на компонент, играющий роль заголовка
	final private Component component;

	// Ссылка на менеджер раскладок, содержащий строку
	final private ChecklistLayout owner;

	Title(Component component, ChecklistLayout owner) {
		this.component = component;
		this.owner = owner;
	}

	public Insets getInsets() {
		if (!owner.isFirstRow(this)) {
			return new Insets(owner.getTitleGap(), owner.getTitleInsent(), 0, 0);
		} else {
			return new Insets(0,owner.getTitleInsent(),0,0);
		}
	}

	public int getMinimumWidth() {
		return component.getMinimumSize().width + getInsets().left
				+ getInsets().right;
	}

	public int getPrefferedHeight() {
		return component.getPreferredSize().height + getInsets().top
				+ getInsets().bottom;
	}

	/**
	 * Возвращает 0, потому что на самом деле колонки меток нет. Если возвращать
	 * другое число, оно будет участвовать при размещение колонки.
	 */
	public int getPrefferedLabelWidth() {
		return 0;
	}

	public int getPrefferedWidth() {
		return component.getPreferredSize().width + getInsets().left
				+ getInsets().right;
	}

	public void layout(Rectangle bounds, int labelWidth, int hgap) {
		Rectangle titleBounds = new Rectangle();
		titleBounds.height = component.getPreferredSize().height;
		titleBounds.width = java.lang.Math.min(getPrefferedWidth(),
				bounds.width);
		titleBounds.x = getInsets().left + bounds.x;

		// Для того, чтобы определится с положением по y нужно определится,
		// не является ли компоненты первым на панели. Это специфическое
		// ограничение, чтобы любой заголовок был прижат к верху.
			titleBounds.y = getInsets().top + bounds.y;
		component.setBounds(titleBounds);
	}

}
