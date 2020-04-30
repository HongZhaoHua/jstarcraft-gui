package com.jstarcraft.swing.cell.renderer;

import java.awt.Component;
import java.util.function.Supplier;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import com.jstarcraft.swing.component.tip.Tip;
import com.jstarcraft.swing.component.tip.TipComponent;

/**
 * 提示列渲染器
 * 
 * @author Birdy
 *
 */
// TODO 考虑与PredicateListCellRenderer整合
public class TipListCellRenderer extends DefaultListCellRenderer {

    /** 提示组件 */
    protected TipComponent tipComponent;

    protected Supplier<ListCellRenderer> rendererSupplier;;

    public TipListCellRenderer(Tip tip, ListCellRenderer renderer) {
        this(tip, () -> {
            return renderer;
        });
    }

    public TipListCellRenderer(Tip tip, Supplier<ListCellRenderer> rendererSupplier) {
        this.tipComponent = new TipComponent(tip);
        this.rendererSupplier = rendererSupplier;
    }

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean selected, boolean focused) {
        // 关键代码
        // 注意,此处不能只判断index == -1
        if (index == -1 && value == null) {
            return tipComponent;
        } else {
            return rendererSupplier.get().getListCellRendererComponent(list, value, index, selected, focused);
        }
    }

}
