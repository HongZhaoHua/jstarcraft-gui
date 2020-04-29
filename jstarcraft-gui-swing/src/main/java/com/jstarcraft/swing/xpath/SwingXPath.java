
package com.jstarcraft.swing.xpath;

import org.jaxen.BaseXPath;
import org.jaxen.JaxenException;

public class SwingXPath extends BaseXPath {

    public SwingXPath(String xpath) throws JaxenException {
        super(xpath, SwingNavigator.getInstance());
    }

}
