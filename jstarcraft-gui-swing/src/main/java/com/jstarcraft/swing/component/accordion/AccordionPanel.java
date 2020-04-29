package com.jstarcraft.swing.component.accordion;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.function.Function;

import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import com.jstarcraft.swing.component.dnd.DragDropScrollPane;

/**
 * 折叠面板
 * 
 * @author Birdy
 *
 */
// 参考AccordionPanel
public class AccordionPanel<T> extends JPanel {

    private final static int defaultScrollUnit = 25;

    /** 数据模型 */
    private ListModel<T> dataModel;

    /** 标题工厂 */
    private Function<T, JPanel> titleFactory;

    /** 内容工厂 */
    private Function<T, JPanel> contentFactory;

    /** 滚动单位 */
    private int scrollUnit;

    /** 拖放面板 */
    private DragDropScrollPane dragDropScroll;

    /** 折叠箱子 */
    private Box accordionBox;

    private ArrayList<TogglePanel> togglePanels;

    public AccordionPanel(ListModel<T> dataModel, Function<T, JPanel> titleFactory, Function<T, JPanel> contentFactory) {
        this(dataModel, titleFactory, contentFactory, defaultScrollUnit);
    }

    public AccordionPanel(ListModel<T> dataModel, Function<T, JPanel> titleFactory, Function<T, JPanel> contentFactory, int scrollUnit) {
        super(new BorderLayout());

        this.dataModel = dataModel;
        this.dataModel.addListDataListener(new ListDataListener() {

            @Override
            public void intervalAdded(ListDataEvent event) {
                dataChanged(event);
            }

            @Override
            public void intervalRemoved(ListDataEvent event) {
                dataChanged(event);
            }

            @Override
            public void contentsChanged(ListDataEvent event) {
                dataChanged(event);
            }

        });
        this.titleFactory = titleFactory;
        this.contentFactory = contentFactory;

        // TODO 无法随着dataModel顺序变化而变化,除非整个面板重建
        this.accordionBox = Box.createVerticalBox();
        dataChanged(new EventObject(this.dataModel));

        this.dragDropScroll = new DragDropScrollPane(this.accordionBox);
        this.add(this.dragDropScroll, BorderLayout.CENTER);

        this.setScrollUnit(scrollUnit);
    }

    private TogglePanel getTogglePanel(T data) {
        JPanel title = titleFactory.apply(data);
        JPanel content = contentFactory.apply(data);
        TogglePanel togglePanel = new TogglePanel(title, content, false);
        return togglePanel;
    }

    private void dataChanged(EventObject event) {
        accordionBox.removeAll();
        int size = dataModel.getSize();
        togglePanels = new ArrayList<>(size);
        for (int index = 0; index < size; index++) {
            TogglePanel togglePanel = getTogglePanel(dataModel.getElementAt(index));
            togglePanels.add(togglePanel);
            accordionBox.add(togglePanel);
            accordionBox.add(Box.createVerticalStrut(5));
        }
        accordionBox.add(Box.createVerticalGlue());
        revalidate();
    }

    /**
     * 获取格子数量
     * 
     * @return
     */
    public int getCellSize() {
        return togglePanels.size();
    }

    /**
     * 判断某个格子的伸缩状态
     * 
     * @param index
     * @return
     */
    public boolean isCellToggle(int index) {
        return togglePanels.get(index).isToggle();
    }

    /**
     * 设置某个格子的伸缩状态
     * 
     * @param index
     * @param toggle
     */
    public void setCellToggle(int index, boolean toggle) {
        this.togglePanels.get(index).setToggle(toggle);
    }

    /**
     * 获取滚动单位
     * 
     * @return
     */
    public int getScrollUnit() {
        return scrollUnit;
    }

    /**
     * 设置滚动单位
     * 
     * @param unit
     */
    public void setScrollUnit(int scrollUnit) {
        this.scrollUnit = scrollUnit;
        this.dragDropScroll.getHorizontalScrollBar().setUnitIncrement(scrollUnit);
        this.dragDropScroll.getVerticalScrollBar().setUnitIncrement(scrollUnit);
    }

}
