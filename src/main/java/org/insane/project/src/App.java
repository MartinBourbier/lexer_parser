package org.insane.project.src;

import org.insane.project.src.ExpressionAnalysis.Computation;

public class App
{
    public static void main(String[] args) 
    {
        System.out.println(Computation.Compute("(1 + 2) * 3"));
    }
}