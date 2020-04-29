package com.jstarcraft.swing.component.accordion;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Point;

import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;

import com.jstarcraft.core.utility.KeyValue;
import com.jstarcraft.swing.component.CellPanel;
import com.jstarcraft.swing.support.cell.TreeCell;

public class SupportPanel extends CellPanel<TreeCell<KeyValue<String, Boolean>>, KeyValue<String, Boolean>> {

    private JCheckBox checkBox;

    private JTextField textField;

    public SupportPanel() {
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
    public boolean isVariable(Point point) {
        Component component = SwingUtilities.getDeepestComponentAt(this, point.x, point.y);
        return component == checkBox || component == textField;
    }

}
