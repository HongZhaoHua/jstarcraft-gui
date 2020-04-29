package com.jstarcraft.swing.component;

import java.awt.LayoutManager;
import java.util.LinkedHashSet;

import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * 数据面板
 * 
 * <pre>
 * 为数据提供支撑
 * </pre>
 * 
 * @author Birdy
 *
 */
// TODO 考虑将DataPanel方法调整为setInput,getInput,getOutput
// TODO 考虑将DataPanel事件调整为InputChangeEvent,OutputChangeEvent
public abstract class DataPanel<I, O> extends JPanel {

    protected LinkedHashSet<ChangeListener> dataListeners = new LinkedHashSet<>();

    public DataPanel() {
        super();
    }

    public DataPanel(LayoutManager manager) {
        super(manager);
    }

    public DataPanel(boolean buffered) {
        super(buffered);
    }

    public DataPanel(LayoutManager manager, boolean buffered) {
        super(manager, buffered);
    }

    /**
     * 获取数据
     * 
     * @return
     */
    public abstract O getData();

    /**
     * 设置数据
     * 
     * @param data
     */
    public abstract void setData(I data);

    /**
     * 触发数据监控器
     */
    protected void triggerDataListeners() {
        ChangeEvent event = new ChangeEvent(this);
        for (ChangeListener listener : dataListeners) {
            listener.stateChanged(event);
        }
    }

    /**
     * 添加数据监控器
     * 
     * @param listener
     */
    public void attachDataListener(ChangeListener listener) {
        dataListeners.add(listener);
    }

    /**
     * 移除数据监控器
     * 
     * @param listener
     */
    public void detachDataListener(ChangeListener listener) {
        dataListeners.remove(listener);
    }

}
