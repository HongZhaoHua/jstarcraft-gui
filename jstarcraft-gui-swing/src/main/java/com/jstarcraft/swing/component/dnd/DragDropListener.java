package com.jstarcraft.swing.component.dnd;

import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;

/**
 * 拖放监控器
 * 
 * @author Birdy
 *
 */
// 参考MouseWheelScroll
public class DragDropListener extends MouseAdapter {

    /** 拖放游标 */
    protected Cursor dragCursor, dropCursor;

    protected final Point point = new Point();

    public DragDropListener(Cursor dragCursor, Cursor dropCursor) {
        this.dragCursor = dragCursor;
        this.dropCursor = dropCursor;
    }

    private JViewport getViewport(Component component) {
        Container container = component.getParent();
        while (container != null && !(container instanceof JViewport)) {
            container = container.getParent();
        }
        return (JViewport) container;
    }

    @Override
    public void mousePressed(MouseEvent event) {
        Component component = event.getComponent();
        component.setCursor(dragCursor);
        JViewport viewport = getViewport(component);
        if (viewport != null) {
            Point point = SwingUtilities.convertPoint(component, event.getPoint(), viewport);
            this.point.setLocation(point);
        }
    }

    @Override
    public void mouseDragged(MouseEvent event) {
        Component component = event.getComponent();
        JViewport viewport = getViewport(component);
        if (viewport != null) {
            // 关键代码
            Point point = SwingUtilities.convertPoint(component, event.getPoint(), viewport);
            Point view = viewport.getViewPosition();
            view.translate(this.point.x - point.x, this.point.y - point.y);
            ((JComponent) component).scrollRectToVisible(new Rectangle(view, viewport.getSize()));
            this.point.setLocation(point);
        }
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        Component component = event.getComponent();
        component.setCursor(dropCursor);
    }

}
