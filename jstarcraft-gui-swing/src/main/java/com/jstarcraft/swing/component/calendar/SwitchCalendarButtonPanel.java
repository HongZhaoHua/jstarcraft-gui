package com.jstarcraft.swing.component.calendar;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EventObject;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SpinnerListModel;

import com.jstarcraft.core.common.instant.CalendarType;
import com.jstarcraft.swing.model.RollSpinnerModel;

public class SwitchCalendarButtonPanel extends SwitchCalendarPanel {

    private CardLayout switchManager = new CardLayout();

    private JButton switchButton;

    private JPanel switchPanel;

    public SwitchCalendarButtonPanel(CalendarPanel... panels) {
        super(panels);

        this.switchModel = new RollSpinnerModel<>(new SpinnerListModel(new ArrayList<>(this.calendarPanels.keySet())));
        this.switchPanel = new JPanel(this.switchManager);
        for (CalendarPanel calendarPanel : panels) {
            CalendarType type = calendarPanel.getCalendarType();
            String name = type.name();
            this.switchPanel.add(name, calendarPanel);
        }
        this.switchButton = new JButton();

        Box box = Box.createHorizontalBox();
        box.add(this.switchButton);
        box.add(this.switchPanel);
        this.add(box, BorderLayout.CENTER);

        this.switchButton.addActionListener(this::adjustSwitchModel);
        this.switchModel.addChangeListener(this::adjustSwitchView);

        // 调整视图
        this.adjustSwitchView(new EventObject(this.switchModel));
    }

    private void adjustSwitchView(EventObject event) {
        String name = switchModel.getValue().name();
        switchButton.setText(name);
        switchManager.show(switchPanel, name);
    }

}
