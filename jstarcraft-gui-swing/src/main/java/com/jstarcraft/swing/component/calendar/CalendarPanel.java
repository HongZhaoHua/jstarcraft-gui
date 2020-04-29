package com.jstarcraft.swing.component.calendar;

import java.awt.LayoutManager;
import java.time.LocalDate;

import javax.swing.JPanel;

import com.jstarcraft.core.common.instant.CalendarType;

/**
 * 历法面板
 * 
 * @author Birdy
 *
 */
// TODO 准备重构为基于DataPanel或者CellPanel
public abstract class CalendarPanel extends JPanel {

    public CalendarPanel(LayoutManager manager) {
        super(manager);
    }

    /**
     * 获取历法日期
     * 
     * @return
     */
    public abstract LocalDate getCalendarDate();

    /**
     * 设置历法日期
     * 
     * @param date
     */
    public abstract void setCalendarDate(LocalDate date);

    /**
     * 获取历法类型
     * 
     * @return
     */
    public abstract CalendarType getCalendarType();

}
