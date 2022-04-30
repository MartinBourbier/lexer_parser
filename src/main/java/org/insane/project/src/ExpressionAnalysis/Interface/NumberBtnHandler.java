package org.insane.project.src.ExpressionAnalysis.Interface;

import java.awt.event.ActionListener;

import javax.swing.JButton;

class NumberBtnHandler implements ActionListener
{
    /**
     *
     */
    private final CalculatorBox calculatorBox;

    /**
     * @param calculatorBox
     */
    NumberBtnHandler(CalculatorBox calculatorBox) {
        this.calculatorBox = calculatorBox;
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e)
    {
        JButton btn = (JButton) e.getSource();

        for (JButton num : this.calculatorBox.nums)
        {
            if (btn == num)
            {
                this.calculatorBox.appendToOutput(num.getText());
            }
        }
    }
}