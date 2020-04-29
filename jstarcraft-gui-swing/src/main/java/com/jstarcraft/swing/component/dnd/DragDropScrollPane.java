package com.jstarcraft.swing.component.dnd;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

/**
 * 拖放面板
 * 
 * @author Birdy
 *
 */
// 参考MouseWheelScroll
public class DragDropScrollPane extends JScrollPane {

    private MouseAdapter dndListener = new DragDropListener(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR), Cursor.getDefaultCursor());

    public DragDropScrollPane() {
        this(null);
    }

    public DragDropScrollPane(Component view) {
        this(view, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    }

    public DragDropScrollPane(int vsbPolicy, int hsbPolicy) {
        this(null, vsbPolicy, hsbPolicy);
    }

    public DragDropScrollPane(Component view, int verticalPolicy, int horizontalPolicy) {
        super(view, verticalPolicy, horizontalPolicy);

        if (view != null) {
            view.addMouseListener(this.dndListener);
            view.addMouseMotionListener(this.dndListener);
        }
        JScrollBar verticalBar = this.getVerticalScrollBar();
        verticalBar.setPreferredSize(new Dimension(0, 0));
        JScrollBar horizontalBar = this.getHorizontalScrollBar();
        horizontalBar.setPreferredSize(new Dimension(0, 0));
    }

    @Override
    public void setViewport(JViewport viewport) {
        JViewport oldViewport = getViewport();
        JViewport newViewport = viewport;
        if (oldViewport != null) {
            Component view = oldViewport.getView();
            if (view != null) {
                view.removeMouseListener(this.dndListener);
                view.removeMouseMotionListener(this.dndListener);
            }
        }
        if (newViewport != null) {
            Component view = newViewport.getView();
            if (view != null) {
                view.addMouseListener(this.dndListener);
                view.addMouseMotionListener(this.dndListener);
            }
        }
        super.setViewport(viewport);
    }

    @Override
    public void setViewportView(Component view) {
        JViewport viewport = getViewport();
        if (viewport != null) {
            Component oldView = viewport.getView();
            Component newView = view;
            if (oldView != null) {
                oldView.removeMouseListener(this.dndListener);
                oldView.removeMouseMotionListener(this.dndListener);
            }
            if (newView != null) {
                newView.addMouseListener(this.dndListener);
                newView.addMouseMotionListener(this.dndListener);
            }
            super.setViewportView(view);
        }
    }

    public MouseAdapter getDndListener() {
        return dndListener;
    }

}
