package com.jstarcraft.swing.component.calendar;

import java.awt.BorderLayout;
import java.time.LocalDate;
import java.util.EnumMap;
import java.util.EventObject;

import com.jstarcraft.core.common.instant.CalendarType;
import com.jstarcraft.swing.model.RollSpinnerModel;

/**
 * 切换历法面板
 * 
 * @author Birdy
 *
 */
public abstract class SwitchCalendarPanel extends CalendarPanel {

    protected EnumMap<CalendarType, CalendarPanel> calendarPanels;

    protected RollSpinnerModel<CalendarType> switchModel;

    protected SwitchCalendarPanel(CalendarPanel... panels) {
        super(new BorderLayout());
        
        this.calendarPanels = new EnumMap<>(CalendarType.class);
        for (CalendarPanel calendarPanel : panels) {
            CalendarType type = calendarPanel.getCalendarType();
            if (this.calendarPanels.put(type, calendarPanel) != null) {
                throw new IllegalArgumentException();
            }
        }
    }

    protected void adjustSwitchModel(EventObject event) {
        CalendarType previousType = switchModel.getValue();
        CalendarPanel previousPanel = calendarPanels.get(previousType);
        LocalDate date = previousPanel.getCalendarDate();
        CalendarType nextType = switchModel.getNextValue();
        CalendarPanel nextPanel = calendarPanels.get(nextType);
        nextPanel.setCalendarDate(date);
        // 触发视图切换
        switchModel.setValue(nextType);
    }

    @Override
    public LocalDate getCalendarDate() {
        CalendarType type = switchModel.getValue();
        CalendarPanel calendarPanel = calendarPanels.get(type);
        return calendarPanel.getCalendarDate();
    }

    @Override
    public void setCalendarDate(LocalDate date) {
        CalendarType type = switchModel.getValue();
        CalendarPanel calendarPanel = calendarPanels.get(type);
        calendarPanel.setCalendarDate(date);
    }

    @Override
    public CalendarType getCalendarType() {
        CalendarType type = switchModel.getValue();
        CalendarPanel calendarPanel = calendarPanels.get(type);
        return calendarPanel.getCalendarType();
    }

    /**
     * 设置历法
     * 
     * @param type
     */
    public void setCalendarType(CalendarType type) {
        switchModel.setValue(type);
    }

}
