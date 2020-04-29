package com.jstarcraft.swing.component.pagination;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.event.ChangeListener;

import com.jstarcraft.swing.component.DataPanel;
import com.jstarcraft.swing.model.BoundarySpinnerModel;

/**
 * 分页面板
 * 
 * @author Birdy
 *
 * @param <T>
 */
public class PaginationPanel<T> extends DataPanel<BoundarySpinnerModel<T>, BoundarySpinnerModel<T>> {

    protected BoundarySpinnerModel<T> dataModel;

    protected DataPanel<T, T> dataPanel;

    protected JButton firstButton;

    protected JButton previousButton;

    protected JButton nextButton;

    protected JButton lastButton;

    protected ChangeListener dataListener = (event) -> {
        T data = dataModel.getValue();
        firstButton.setEnabled(!dataModel.getMinimum().equals(data));
        previousButton.setEnabled(!dataModel.getMinimum().equals(data));
        nextButton.setEnabled(!dataModel.getMaximum().equals(data));
        lastButton.setEnabled(!dataModel.getMaximum().equals(data));

        dataPanel.setData(data);

        triggerDataListeners();
    };

    public PaginationPanel() {
        this(null);
    }

    public PaginationPanel(DataPanel<T, T> dataPanel) {
        super(new BorderLayout());

        this.dataPanel = dataPanel;
        if (this.dataPanel != null) {
            this.dataPanel.attachDataListener((event) -> {
                T data = dataPanel.getData();
                if (data != null) {
                    dataModel.setValue(data);
                }
            });
        }

        this.firstButton = new JButton("|<");
        this.firstButton.addActionListener((event) -> {
            T data = dataModel.getMinimum();
            dataModel.setValue(data);
        });
        this.previousButton = new JButton("<");
        this.previousButton.addActionListener((event) -> {
            T data = dataModel.getPreviousValue();
            if (data != null) {
                dataModel.setValue(data);
            }
        });
        this.nextButton = new JButton(">");
        this.nextButton.addActionListener((event) -> {
            T data = dataModel.getNextValue();
            if (data != null) {
                dataModel.setValue(data);
            }
        });
        this.lastButton = new JButton(">|");
        this.lastButton.addActionListener((event) -> {
            T data = dataModel.getMaximum();
            dataModel.setValue(data);
        });

        Box box = Box.createHorizontalBox();
        box.add(this.firstButton);
        box.add(this.previousButton);
        if (this.dataPanel != null) {
            box.add(this.dataPanel);
        }
        box.add(this.nextButton);
        box.add(this.lastButton);
        this.add(box, BorderLayout.CENTER);
    }

    @Override
    public BoundarySpinnerModel<T> getData() {
        return dataModel;
    }

    @Override
    public void setData(BoundarySpinnerModel<T> data) {
        BoundarySpinnerModel<T> oldModel = dataModel;
        BoundarySpinnerModel<T> newModel = data;
        dataModel = data;
        if (oldModel != null) {
            oldModel.removeChangeListener(dataListener);
        }
        if (newModel != null) {
            newModel.addChangeListener(dataListener);
        }
        dataListener.stateChanged(null);
    }

}
