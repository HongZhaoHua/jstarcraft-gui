package com.jstarcraft.swing.component.configure;

import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Window;

import javax.swing.JWindow;

import com.jstarcraft.core.common.configuration.Configurator;

/**
 * 可配置窗体
 * 
 * @author Birdy
 *
 */
public abstract class ConfigurableWindow extends JWindow implements Configurable {

    protected final Configurator configurator;

    public ConfigurableWindow(Configurator configurator) {
        super();
        this.configurator = configurator;
    }

    public ConfigurableWindow(Configurator configurator, Frame owner) {
        super(owner);
        this.configurator = configurator;
    }

    public ConfigurableWindow(Configurator configurator, Window owner) {
        super(owner);
        this.configurator = configurator;
    }

    public ConfigurableWindow(Configurator configurator, GraphicsConfiguration graphics) {
        super(graphics);
        this.configurator = configurator;
    }

    public ConfigurableWindow(Configurator configurator, Window owner, GraphicsConfiguration graphics) {
        super(owner, graphics);
        this.configurator = configurator;
    }

}
