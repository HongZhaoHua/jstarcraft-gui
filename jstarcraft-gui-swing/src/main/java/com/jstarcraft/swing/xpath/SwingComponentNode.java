package com.jstarcraft.swing.xpath;

import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 组件节点
 * 
 * @author Birdy
 *
 */
public class SwingComponentNode extends SwingNode<Component> {

    public SwingComponentNode(Component root) {
        super(null, root.getClass().getSimpleName(), root);
    }

    SwingComponentNode(SwingNode parent, Component node) {
        super(parent, node.getClass().getSimpleName(), node);
    }

    @Override
    Container getComponent() {
        Container component = (Container) getValue();
        return component;
    }

    static Collection<SwingComponentNode> getInstances(SwingNode node, String name) {
        Container container = node.getComponent();
        Component[] components = container.getComponents();
        ArrayList<SwingComponentNode> instances = new ArrayList<>(components.length);
        for (Component component : components) {
            if (component.getClass().getSimpleName().equals(name)) {
                instances.add(new SwingComponentNode(node, component));
            }
        }
        return instances;
    }

    static Collection<SwingComponentNode> getInstances(SwingNode node) {
        Container container = node.getComponent();
        Component[] components = container.getComponents();
        ArrayList<SwingComponentNode> instances = new ArrayList<>(components.length);
        for (Component component : components) {
            instances.add(new SwingComponentNode(node, component));
        }
        return instances;
    }

}
