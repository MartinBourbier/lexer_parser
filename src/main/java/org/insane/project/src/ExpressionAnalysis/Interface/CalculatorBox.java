package org.insane.project.src.ExpressionAnalysis.Interface;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.insane.project.src.ExpressionAnalysis.Computation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;



public class CalculatorBox extends JFrame
{
    JButton add, sub, div, mul;
    public JButton clear;
    public JButton del;
    public JButton eq;
    JButton var;
    JButton paro;
    JButton parc;
    JButton nums[];
    JTextField output;
    String expression;

    public CalculatorBox()
    {
        super("Calculator");
        JPanel mainPanel = new JPanel();

        JPanel row1 = new JPanel();
        JPanel row2 = new JPanel();
        JPanel row3 = new JPanel();
        JPanel row4 = new JPanel();
        JPanel row5 = new JPanel();

        output = new JTextField(16);
        add = new JButton("+");
        sub = new JButton("-");
        div = new JButton("/");
        mul = new JButton("*");
        clear = new JButton("C");
        del = new JButton("D");
        eq = new JButton("=");
        var = new JButton("x");
        paro = new JButton("(");
        parc = new JButton(")");

        NumberBtnHandler numBtnHandler = new NumberBtnHandler(this);
        OtherBtnHandler otherBtnHandler = new OtherBtnHandler(this);
        OperatorBtnHandler opBtnHandler = new OperatorBtnHandler(this);

        nums = new JButton[10];

        for (int i = 0; i < 10; i++)
        {
            nums[i] = new JButton(Integer.toString(i));
            nums[i].setFont(new Font("Monospaced", Font.BOLD, 22));
            nums[i].addActionListener(numBtnHandler);
        }

        add.setFont(new Font("Monospaced", Font.BOLD, 22));
        sub.setFont(new Font("Monospaced", Font.BOLD, 22));
        div.setFont(new Font("Monospaced", Font.BOLD, 22));
        mul.setFont(new Font("Monospaced", Font.BOLD, 22));
        clear.setFont(new Font("Monospaced", Font.BOLD, 22));
        del.setFont(new Font("Monospaced", Font.BOLD, 22));
        eq.setFont(new Font("Monospaced", Font.BOLD, 22));
        var.setFont(new Font("Monospaced", Font.BOLD, 22));
        paro.setFont(new Font("Monospaced", Font.BOLD, 22));
        parc.setFont(new Font("Monospaced", Font.BOLD, 22));

        output.setMaximumSize(new Dimension(185, 40));
        output.setFont(new Font("Monospaced", Font.BOLD, 27));
        output.setDisabledTextColor(new Color(0, 0, 0));
        output.setMargin(new Insets(0, 5, 0, 0));
        output.setText("0");

        add.addActionListener(opBtnHandler);
        sub.addActionListener(opBtnHandler);
        div.addActionListener(opBtnHandler);
        mul.addActionListener(opBtnHandler);
        clear.addActionListener(otherBtnHandler);
        del.addActionListener(otherBtnHandler);
        eq.addActionListener(otherBtnHandler);
        var.addActionListener(opBtnHandler);
        paro.addActionListener(opBtnHandler);
        parc.addActionListener(opBtnHandler);

        row1.setLayout(new BoxLayout(row1, BoxLayout.LINE_AXIS));
        row2.setLayout(new BoxLayout(row2, BoxLayout.LINE_AXIS));
        row3.setLayout(new BoxLayout(row3, BoxLayout.LINE_AXIS));
        row4.setLayout(new BoxLayout(row4, BoxLayout.LINE_AXIS));
        row5.setLayout(new BoxLayout(row5, BoxLayout.LINE_AXIS));

        row1.add(paro);
        row1.add(parc);
        row1.add(clear);
        row1.add(del);

        row2.add(nums[7]);
        row2.add(nums[8]);
        row2.add(nums[9]);
        row2.add(mul);

        row3.add(nums[4]);
        row3.add(nums[5]);
        row3.add(nums[6]);
        row3.add(add);

        row4.add(nums[1]);
        row4.add(nums[2]);
        row4.add(nums[3]);
        row4.add(sub);

        row5.add(var);
        row5.add(nums[0]);
        row5.add(eq);
        row5.add(div);

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.add(output);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(row1);
        mainPanel.add(row2);
        mainPanel.add(row3);
        mainPanel.add(row4);
        mainPanel.add(row5);

        this.add(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(new Dimension(270, 330));
        this.setVisible(true);
    }

    public void delete()
    {
        if (output.getText().length() > 0)
            output.setText(output.getText().substring(0, output.getText().length() - 1));
    }

    public void clear()
    {
        output.setText("0");
    }

    public void appendToOutput(String str)
    {
        if (output.getText().equals("0"))
            output.setText(str);
        else
            output.setText(output.getText() + str);
    }

    public void calculate()
    {
        output.setText(Computation.Compute(output.getText()).toString());
    }

    public static void main(String[] args)
    {
        new CalculatorBox();
    }
}
