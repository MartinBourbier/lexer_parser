package org.insane.project.src;

import org.insane.project.src.ExpressionAnalysis.Computation;

public class App
{
    public static void main(String[] args) 
    {
        while (true)
        {
            System.out.print("Enter expression ('Enter' to exit): ");
            String expression = System.console().readLine();
            if (expression == null || expression.length() <= 0)
                break;
            try
            {
                System.out.println("Result: " + Computation.Compute(expression));
            }
            catch (Exception e)
            {
                System.out.println("Error: " + e.getMessage());
            }
        }

        System.out.println("Bye!");
    }
}