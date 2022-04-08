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

    protected final Configurator option;

    public ConfigurableDialog(Configurator option) {
        super();
        this.option = option;
    }

    public ConfigurableDialog(Configurator option, Dialog owner) {
        super(owner);
        this.option = option;
    }

    public ConfigurableDialog(Configurator option, Dialog owner, String title) {
        super(owner, title);
        this.option = option;
    }

    public ConfigurableDialog(Configurator option, Dialog owner, boolean modal) {
        super(owner, modal);
        this.option = option;
    }

    public ConfigurableDialog(Configurator option, Dialog owner, String title, boolean modal) {
        super(owner, title, modal);
        this.option = option;
    }

    public ConfigurableDialog(Configurator option, Dialog owner, String title, boolean modal, GraphicsConfiguration graphics) {
        super(owner, title, modal, graphics);
        this.option = option;
    }

    // JFrame
    public ConfigurableDialog(Configurator option, Frame owner) {
        super(owner);
        this.option = option;
    }

    public ConfigurableDialog(Configurator option, Frame owner, String title) {
        super(owner, title);
        this.option = option;
    }

    public ConfigurableDialog(Configurator option, Frame owner, boolean modal) {
        super(owner, modal);
        this.option = option;
    }

    public ConfigurableDialog(Configurator option, Frame owner, String title, boolean modal) {
        super(owner, title, modal);
        this.option = option;
    }

    public ConfigurableDialog(Configurator option, Frame owner, String title, boolean modal, GraphicsConfiguration graphics) {
        super(owner, title, modal, graphics);
        this.option = option;
    }

    // JWindow
    public ConfigurableDialog(Configurator option, Window owner) {
        super(owner);
        this.option = option;
    }

    public ConfigurableDialog(Configurator option, Window owner, String title) {
        super(owner, title);
        this.option = option;
    }

    public ConfigurableDialog(Configurator option, Window owner, ModalityType modality) {
        super(owner, modality);
        this.option = option;
    }

    public ConfigurableDialog(Configurator option, Window owner, String title, ModalityType modality) {
        super(owner, title, modality);
        this.option = option;
    }

    public ConfigurableDialog(Configurator option, Window owner, String title, ModalityType modality, GraphicsConfiguration graphics) {
        super(owner, title, modality, graphics);
        this.option = option;
    }

}
