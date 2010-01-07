package com.vygovskiy.controls.binding;

import org.jdesktop.beansbinding.Validator;

/**
 * Определяет валидатор с максимальным и минимальным допустимым значением.
 * @author leonidv
 *
 */
public class IntegerValidator extends Validator<Integer> {
    // Минимально допустимое значение
    private int max;

    // Максимально допустимое значение
    private int min;

    // Сообщение, выводимое при ошибке валидации
    private String message = "Значение выходит за границы диапазона (%d, %d)";

    /**
     * Создает новый валидатор
     * 
     * @param max
     *          максимально допускаемое валидатором значение
     * @param min
     *          минимально допускаемое валидатором значение
     */
    public IntegerValidator(int max, int min) {
        super();
        this.max = max;
        this.min = min;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Result validate(Integer value) {
        if (value < min || value > max) {
            System.out.println("error in validator");
            return new Validator.Result(null, String.format(message, min, max,
                    value));
        }
        System.out.println("valide value");
        return null;
    }
}
