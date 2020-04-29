package com.jstarcraft.swing.component.calendar;

import java.awt.GridLayout;
import java.awt.event.MouseWheelEvent;
import java.util.EventObject;

import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import com.jstarcraft.swing.model.RollSpinnerModel;

/**
 * 历法微调面板
 * 
 * @author Birdy
 *
 */
// 参考MouseWheel与HtmlSpinnerEditor
public abstract class CalendarSpinnerPanel extends CalendarPanel {

    private boolean valueChange;

    protected final JSpinner yearSpinner;

    protected final JSpinner monthSpinner;

    protected final JSpinner daySpinner;

    private void wheelMoved(MouseWheelEvent event) {
        JSpinner component = (JSpinner) event.getComponent();
        RollSpinnerModel model = (RollSpinnerModel) component.getModel();
        int rotation = event.getWheelRotation();
        if (rotation > 0) {
            component.setValue(model.getNextValue());
        }
        if (rotation < 0) {
            component.setValue(model.getPreviousValue());
        }
    }

    private JSpinner getSpinner() {
        RollSpinnerModel model = new RollSpinnerModel(new SpinnerNumberModel());
        JSpinner spinner = new JSpinner(model);
        JLabel editor = new JLabel();
        spinner.setEditor(editor);
        spinner.addMouseWheelListener(this::wheelMoved);
        return spinner;
    }

    protected CalendarSpinnerPanel() {
        super(new GridLayout(1, 3));

        this.yearSpinner = getSpinner();
        this.monthSpinner = getSpinner();
        this.daySpinner = getSpinner();
        this.add(yearSpinner);
        this.add(monthSpinner);
        this.add(daySpinner);
        this.valueChange = true;
    }

    protected final void setSpinnerBoundary(JSpinner spinner, int minimum, int maximum) {
        valueChange = false;
        RollSpinnerModel model = (RollSpinnerModel) spinner.getModel();
        model.setMinimum(minimum);
        model.setMaximum(maximum);
        valueChange = true;
    }

    protected final boolean isValueChange() {
        return valueChange;
    }

    /**
     * 调整微调模型
     * 
     * @param event
     */
    protected abstract void adjustSpinnerModel(EventObject event);

    /**
     * 调整微调视图
     * 
     * @param event
     */
    protected abstract void adjustSpinnerView(EventObject event);

}
