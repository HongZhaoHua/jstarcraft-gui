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

    protected final Configurator option;

    public ConfigurablePanel(Configurator option) {
        super();
        this.option = option;
    }

    public ConfigurablePanel(Configurator option, LayoutManager manager) {
        super(manager);
        this.option = option;
    }

    public ConfigurablePanel(Configurator option, boolean buffered) {
        super(buffered);
        this.option = option;
    }

    public ConfigurablePanel(Configurator option, LayoutManager manager, boolean buffered) {
        super(manager, buffered);
        this.option = option;
    }

}
