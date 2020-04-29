package com.jstarcraft.swing.model;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.swing.AbstractSpinnerModel;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

/**
 * 边界微调器模型
 * 
 * @author Birdy
 *
 * @param <T>
 */
public class BoundarySpinnerModel<T> extends AbstractSpinnerModel {

    /** 最小值获取器 */
    private Supplier<T> minimumSupplier;

    /** 最大值获取器 */
    private Supplier<T> maximumSupplier;

    /** 最小值设置器 */
    private Consumer<T> minimumConsumer;

    /** 最大值设置器 */
    private Consumer<T> maximumConsumer;

    /** 微调模型 */
    private SpinnerModel spinnerModel;

    public BoundarySpinnerModel(SpinnerDateModel spinnerModel) {
        this(spinnerModel, spinnerModel::getStart, spinnerModel::getEnd, (value) -> {
            spinnerModel.setStart((Comparable) value);
        }, (value) -> {
            spinnerModel.setEnd((Comparable) value);
        });
    }

    public BoundarySpinnerModel(SpinnerListModel spinnerModel) {
        this(spinnerModel, () -> {
            List values = spinnerModel.getList();
            return (T) values.get(0);
        }, () -> {
            List values = spinnerModel.getList();
            return (T) values.get(values.size() - 1);
        }, (value) -> {
            List values = spinnerModel.getList();
            values.set(0, value);
        }, (value) -> {
            List values = spinnerModel.getList();
            values.set(values.size() - 1, value);
        });
    }

    public BoundarySpinnerModel(SpinnerNumberModel spinnerModel) {
        this(spinnerModel, spinnerModel::getMinimum, spinnerModel::getMaximum, (value) -> {
            spinnerModel.setMinimum((Comparable) value);
        }, (value) -> {
            spinnerModel.setMaximum((Comparable) value);
        });
    }

    public BoundarySpinnerModel(SpinnerModel spinnerModel, Supplier minimumSupplier, Supplier maximumSupplier, Consumer minimumConsumer, Consumer maximumConsumer) {
        this.spinnerModel = spinnerModel;
        this.minimumSupplier = minimumSupplier;
        this.maximumSupplier = maximumSupplier;
        this.minimumConsumer = minimumConsumer;
        this.maximumConsumer = maximumConsumer;
    }

    @Override
    public T getNextValue() {
        Object value = spinnerModel.getNextValue();
        return (T) value;
    }

    @Override
    public T getPreviousValue() {
        Object value = spinnerModel.getPreviousValue();
        return (T) value;
    }

    @Override
    public T getValue() {
        return (T) spinnerModel.getValue();
    }

    @Override
    public void setValue(Object value) {
        if (value == null) {
            throw new IllegalArgumentException();
        }
        if (!Objects.equals(spinnerModel.getValue(), value)) {
            spinnerModel.setValue(value);
            fireStateChanged();
        }
    }

    /**
     * 获取最小值
     * 
     * @return
     */
    public T getMinimum() {
        return minimumSupplier.get();
    }

    /**
     * 获取最大值
     * 
     * @return
     */
    public T getMaximum() {
        return maximumSupplier.get();
    }

    /**
     * 设置最小值
     * 
     * @param value
     */
    public void setMinimum(T value) {
        if (!Objects.equals(minimumSupplier.get(), value)) {
            minimumConsumer.accept(value);
            fireStateChanged();
        }
    }

    /**
     * 设置最大值
     * 
     * @param value
     */
    public void setMaximum(T value) {
        if (!Objects.equals(maximumSupplier.get(), value)) {
            maximumConsumer.accept(value);
            fireStateChanged();
        }
    }

    public SpinnerModel getSpinnerModel() {
        return spinnerModel;
    }

}
