package com.jstarcraft.swing.component.configure;

import java.awt.LayoutManager;

import javax.swing.JPanel;

import com.jstarcraft.core.common.configuration.Configurator;

/**
 * 可配置面板
 * 
 * @author Birdy
 *
 */
public abstract class ConfigurablePanel extends JPanel implements Configurable {

    protected final Configurator configurator;

    public ConfigurablePanel(Configurator configurator) {
        super();
        this.configurator = configurator;
    }

    public ConfigurablePanel(Configurator configurator, LayoutManager manager) {
        super(manager);
        this.configurator = configurator;
    }

    public ConfigurablePanel(Configurator configurator, boolean buffered) {
        super(buffered);
        this.configurator = configurator;
    }

    public ConfigurablePanel(Configurator configurator, LayoutManager manager, boolean buffered) {
        super(manager, buffered);
        this.configurator = configurator;
    }

}
