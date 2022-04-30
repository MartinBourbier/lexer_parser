package org.insane.project.src.ExpressionAnalysis.Interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

class OtherBtnHandler implements ActionListener
{
    /**
     *
     */
    private final CalculatorBox calculatorBox;

    /**
     * @param calculatorBox
     */
    OtherBtnHandler(CalculatorBox calculatorBox) {
        this.calculatorBox = calculatorBox;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        JButton btn = (JButton) e.getSource();

        if (btn == this.calculatorBox.clear)
            this.calculatorBox.clear();
        else if (btn == this.calculatorBox.del)
            this.calculatorBox.delete();
        else if (btn == this.calculatorBox.eq)
            this.calculatorBox.calculate();
    }
}