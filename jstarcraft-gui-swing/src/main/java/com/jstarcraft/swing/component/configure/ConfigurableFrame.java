package com.jstarcraft.swing.component.configure;

import java.awt.GraphicsConfiguration;

import javax.swing.JFrame;

import com.jstarcraft.core.common.configuration.Configurator;

/**
 * 可配置框架
 * 
 * @author 20191031
 *
 */
public abstract class ConfigurableFrame extends JFrame implements Configurable {

    protected final Configurator configurator;

    public ConfigurableFrame(Configurator configurator) {
        super();
        this.configurator = configurator;
    }

    public ConfigurableFrame(Configurator configurator, String title) {
        super(title);
        this.configurator = configurator;
    }

    public ConfigurableFrame(Configurator configurator, GraphicsConfiguration graphics) {
        super(graphics);
        this.configurator = configurator;
    }

    public ConfigurableFrame(Configurator configurator, String title, GraphicsConfiguration graphics) {
        super(title, graphics);
        this.configurator = configurator;
    }

}
