package com.jstarcraft.swing.component.configure;

import java.awt.LayoutManager;

import javax.swing.JPanel;

import com.jstarcraft.core.common.option.Option;

/**
 * 可配置面板
 * 
 * @author Birdy
 *
 */
public abstract class ConfigurablePanel extends JPanel implements Configurable {

    protected final Option option;

    public ConfigurablePanel(Option option) {
        super();
        this.option = option;
    }

    public ConfigurablePanel(Option option, LayoutManager manager) {
        super(manager);
        this.option = option;
    }

    public ConfigurablePanel(Option option, boolean buffered) {
        super(buffered);
        this.option = option;
    }

    public ConfigurablePanel(Option option, LayoutManager manager, boolean buffered) {
        super(manager, buffered);
        this.option = option;
    }

}
