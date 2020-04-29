package com.jstarcraft.swing.event;

import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.util.concurrent.atomic.AtomicBoolean;

public class MockHierarchyListener implements HierarchyListener {

    private AtomicBoolean hierarchyChanged = new AtomicBoolean();

    private AtomicBoolean displayChanged = new AtomicBoolean();

    private AtomicBoolean visibleChanged = new AtomicBoolean();

    @Override
    public void hierarchyChanged(HierarchyEvent event) {
        long flags = event.getChangeFlags();
        if ((flags & HierarchyEvent.PARENT_CHANGED) != 0) {
            // Hierarchy变更
            hierarchyChanged.set(true);
        }
        if ((flags & HierarchyEvent.DISPLAYABILITY_CHANGED) != 0) {
            // Display变更(是否可摸)
            displayChanged.set(true);
        }
        if ((flags & HierarchyEvent.SHOWING_CHANGED) != 0) {
            // Visible变更(是否可见)
            visibleChanged.set(true);
        }
    }

    boolean check(boolean hierarchy, boolean display, boolean visible) {
        return hierarchyChanged.get() == hierarchy && displayChanged.get() == display && visibleChanged.get() == visible;
    }

    void reset() {
        hierarchyChanged.set(false);
        displayChanged.set(false);
        visibleChanged.set(false);
    }

}
