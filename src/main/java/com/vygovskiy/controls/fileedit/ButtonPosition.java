package com.vygovskiy.controls.fileedit;

import java.awt.BorderLayout;

/**
 * Класс описывает позицию кнопки открытия диалога относительно поля ввода
 * имени файла. Осуществляется привязка к константам класса BorderLayout.
 * 
 * @author leonidv
 * 
 */
public enum ButtonPosition {
    West(BorderLayout.WEST),

    North(BorderLayout.NORTH),

    East(BorderLayout.EAST),

    South(BorderLayout.SOUTH);

    private String borderLayoutConstant;

    private ButtonPosition(String borderLayoutConstant) {
        this.borderLayoutConstant = borderLayoutConstant;
    }

    String getBorderLayoutConstant() {
        return borderLayoutConstant;
    }
}