package com.jstarcraft.swing.support;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jstarcraft.swing.component.CellPanel;
import com.jstarcraft.swing.support.cell.ListCell;

/**
 * 列单元支撑器
 * 
 * @author Birdy
 *
 */
// 参考ButtonsInListCell,CheckBoxCellList,DifferentCellHeight
public class ListCellSupporter<D> implements ListCellRenderer<D>, PropertyChangeListener {

    protected static final Logger logger = LoggerFactory.getLogger(ListCellSupporter.class);

    /** 渲染面板 */
    protected CellPanel<ListCell<D>, D> renderPanel;

    @Override
    public Component getListCellRendererComponent(JList<? extends D> list, D value, int index, boolean selected, boolean focused) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (event.getPropertyName().equals("UI")) {
            // 兼容换肤
            // 由于组件作为Renderer或者Editor时,不在组件树之中,所以需要触发updateUI.
            SwingUtilities.updateComponentTreeUI(renderPanel);
        }
    }

}
