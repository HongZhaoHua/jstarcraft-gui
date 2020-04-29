package com.jstarcraft.swing.component.configure;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Window;

import javax.swing.JDialog;

import com.jstarcraft.core.common.configuration.Configurator;

/**
 * 可配置对话框
 * 
 * @author Birdy
 *
 */
public abstract class ConfigurableDialog extends JDialog implements Configurable {

    protected final Configurator configurator;

    public ConfigurableDialog(Configurator configurator) {
        super();
        this.configurator = configurator;
    }

    public ConfigurableDialog(Configurator configurator, Dialog owner) {
        super(owner);
        this.configurator = configurator;
    }

    public ConfigurableDialog(Configurator configurator, Dialog owner, String title) {
        super(owner, title);
        this.configurator = configurator;
    }

    public ConfigurableDialog(Configurator configurator, Dialog owner, boolean modal) {
        super(owner, modal);
        this.configurator = configurator;
    }

    public ConfigurableDialog(Configurator configurator, Dialog owner, String title, boolean modal) {
        super(owner, title, modal);
        this.configurator = configurator;
    }

    public ConfigurableDialog(Configurator configurator, Dialog owner, String title, boolean modal, GraphicsConfiguration graphics) {
        super(owner, title, modal, graphics);
        this.configurator = configurator;
    }

    // JFrame
    public ConfigurableDialog(Configurator configurator, Frame owner) {
        super(owner);
        this.configurator = configurator;
    }

    public ConfigurableDialog(Configurator configurator, Frame owner, String title) {
        super(owner, title);
        this.configurator = configurator;
    }

    public ConfigurableDialog(Configurator configurator, Frame owner, boolean modal) {
        super(owner, modal);
        this.configurator = configurator;
    }

    public ConfigurableDialog(Configurator configurator, Frame owner, String title, boolean modal) {
        super(owner, title, modal);
        this.configurator = configurator;
    }

    public ConfigurableDialog(Configurator configurator, Frame owner, String title, boolean modal, GraphicsConfiguration graphics) {
        super(owner, title, modal, graphics);
        this.configurator = configurator;
    }

    // JWindow
    public ConfigurableDialog(Configurator configurator, Window owner) {
        super(owner);
        this.configurator = configurator;
    }

    public ConfigurableDialog(Configurator configurator, Window owner, String title) {
        super(owner, title);
        this.configurator = configurator;
    }

    public ConfigurableDialog(Configurator configurator, Window owner, ModalityType modality) {
        super(owner, modality);
        this.configurator = configurator;
    }

    public ConfigurableDialog(Configurator configurator, Window owner, String title, ModalityType modality) {
        super(owner, title, modality);
        this.configurator = configurator;
    }

    public ConfigurableDialog(Configurator configurator, Window owner, String title, ModalityType modality, GraphicsConfiguration graphics) {
        super(owner, title, modality, graphics);
        this.configurator = configurator;
    }

}
