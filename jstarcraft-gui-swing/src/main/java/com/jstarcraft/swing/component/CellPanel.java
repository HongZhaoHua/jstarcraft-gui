package com.jstarcraft.swing.component;

import java.awt.LayoutManager;
import java.awt.Point;

import com.jstarcraft.swing.cell.DataCell;

/**
 * 格子面板
 * 
 * <pre>
 * 为渲染器与编辑器提供支撑
 * </pre>
 * 
 * @author Birdy
 *
 */
public abstract class CellPanel<C extends DataCell<D>, D> extends DataPanel<C, D> {

    public CellPanel() {
        super();
    }

    public CellPanel(LayoutManager manager) {
        super(manager);
    }

    public CellPanel(boolean buffered) {
        super(buffered);
    }

    public CellPanel(LayoutManager manager, boolean buffered) {
        super(manager, buffered);
    }

    /**
     * 判断点是否在可变区域
     * 
     * @param point
     * @return
     */
    public abstract boolean isVariable(Point point);

}
