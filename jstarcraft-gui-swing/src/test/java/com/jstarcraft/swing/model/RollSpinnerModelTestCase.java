package com.jstarcraft.swing.model;

import javax.swing.SpinnerNumberModel;

import org.junit.Assert;
import org.junit.Test;

public class RollSpinnerModelTestCase {

    @Test
    public void testBoundary() {
        RollSpinnerModel<Number> model = new RollSpinnerModel<>(new SpinnerNumberModel(500, 500, 500, 1));
        Assert.assertEquals(500, model.getValue());
        Assert.assertEquals(500, model.getMinimum());
        Assert.assertEquals(500, model.getMaximum());
        Assert.assertEquals(500, model.getPreviousValue());
        Assert.assertEquals(500, model.getNextValue());

        model.setMinimum(1);
        model.setMaximum(1000);
        model.setValue(1);
        Assert.assertEquals(1000, model.getPreviousValue());
        model.setValue(1000);
        Assert.assertEquals(1, model.getNextValue());
    }

}
