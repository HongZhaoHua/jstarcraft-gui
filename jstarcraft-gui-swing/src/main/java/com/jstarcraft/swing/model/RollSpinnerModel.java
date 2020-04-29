package com.jstarcraft.swing.model;

import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

/**
 * 循环微调器模型
 * 
 * @author Birdy
 *
 */
public class RollSpinnerModel<T> extends BoundarySpinnerModel<T> {

    public RollSpinnerModel(SpinnerDateModel spinnerModel) {
        super(spinnerModel);
    }

    public RollSpinnerModel(SpinnerListModel spinnerModel) {
        super(spinnerModel);
    }

    public RollSpinnerModel(SpinnerNumberModel spinnerModel) {
        super(spinnerModel);
    }

    public RollSpinnerModel(SpinnerModel spinnerModel, Supplier minimumSupplier, Supplier maximumSupplier, Consumer minimumConsumer, Consumer maximumConsumer) {
        super(spinnerModel, minimumSupplier, maximumSupplier, minimumConsumer, maximumConsumer);
    }

    @Override
    public T getNextValue() {
        Object value = super.getNextValue();
        if (value == null) {
            value = super.getMinimum();
        }
        return (T) value;
    }

    @Override
    public T getPreviousValue() {
        Object value = super.getPreviousValue();
        if (value == null) {
            value = super.getMaximum();
        }
        return (T) value;
    }

}
