package com.jstarcraft.swing.component.accordion;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.jstarcraft.core.utility.KeyValue;
import com.jstarcraft.swing.cell.TreeCell;
import com.jstarcraft.swing.component.CellPanel;

public class MockCellPanel extends CellPanel<TreeCell<KeyValue<String, Boolean>>, KeyValue<String, Boolean>> {

    private JCheckBox checkBox;

    private JTextField textField;

    public MockCellPanel() {
        super(new BorderLayout());

        this.checkBox = new JCheckBox();
        this.checkBox.addActionListener((event) -> {
            triggerDataListeners();
        });
        this.textField = new JTextField();
        this.textField.setHorizontalAlignment(SwingConstants.CENTER);

        this.add(this.checkBox, BorderLayout.WEST);
        this.add(this.textField, BorderLayout.CENTER);
    }

    @Override
    public KeyValue<String, Boolean> getData() {
        return new KeyValue<>(textField.getText(), checkBox.isSelected());
    }

    @Override
    public void setData(TreeCell<KeyValue<String, Boolean>> data) {
        KeyValue<String, Boolean> keyValue = data.getData();
        textField.setText(keyValue.getKey());
        checkBox.setSelected(keyValue.getValue());
        triggerDataListeners();
    }

    @Override
    public boolean isEditable(Component component) {
        return component == checkBox || component == textField;
    }

}
