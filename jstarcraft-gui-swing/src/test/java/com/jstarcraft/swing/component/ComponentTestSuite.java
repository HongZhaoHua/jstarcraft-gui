package com.jstarcraft.swing.component;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.jstarcraft.swing.component.calendar.LunarCalendarSpinnerPanenlTestCase;
import com.jstarcraft.swing.component.calendar.SolarCalendarSpinnerPanenlTestCase;

@RunWith(Suite.class)
@SuiteClasses({

        FrameTestCase.class,

        InstancePropertyTableTestCase.class,

        LunarCalendarSpinnerPanenlTestCase.class,

        SolarCalendarSpinnerPanenlTestCase.class,

})
public class ComponentTestSuite {

}
