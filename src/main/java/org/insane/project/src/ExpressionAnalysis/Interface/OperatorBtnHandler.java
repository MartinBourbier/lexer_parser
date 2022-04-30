package org.insane.project.src.ExpressionAnalysis.Interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

class OperatorBtnHandler implements ActionListener
{
    /**
     *
     */
    private final CalculatorBox calculatorBox;

    /**
     * @param calculatorBox
     */
    OperatorBtnHandler(CalculatorBox calculatorBox) {
        this.calculatorBox = calculatorBox;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        JButton btn = (JButton) e.getSource();

        if (btn == this.calculatorBox.add)
            this.calculatorBox.appendToOutput("+");
        else if (btn == this.calculatorBox.sub)
            this.calculatorBox.appendToOutput("-");
        else if (btn == this.calculatorBox.mul)
            this.calculatorBox.appendToOutput("*");
        else if (btn == this.calculatorBox.div)
            this.calculatorBox.appendToOutput("/");
        else if (btn == this.calculatorBox.var)
            this.calculatorBox.appendToOutput("x");
        else if (btn == this.calculatorBox.paro)
            this.calculatorBox.appendToOutput("(");
        else if (btn == this.calculatorBox.parc)
            this.calculatorBox.appendToOutput(")");
    }
}