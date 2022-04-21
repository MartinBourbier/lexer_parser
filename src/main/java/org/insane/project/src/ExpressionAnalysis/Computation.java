package org.insane.project.src.ExpressionAnalysis;

import java.util.LinkedList;

import org.insane.project.src.ExpressionAnalysis.Arithmetic.Polynomial;
import org.insane.project.src.ExpressionAnalysis.Syntax.Evaluator;
import org.insane.project.src.ExpressionAnalysis.Syntax.Lexer;
import org.insane.project.src.ExpressionAnalysis.Syntax.Parser;
import org.insane.project.src.ExpressionAnalysis.Syntax.SyntaxToken;
import org.insane.project.src.ExpressionAnalysis.Syntax.SyntaxTree;

public final class Computation
{
    private Computation()
    {
        throw new UnsupportedOperationException();
    }

    public static Polynomial Compute(String expression)
    {
        if (expression == null || expression.length() <= 0)
            throw new IllegalArgumentException("Expression is empty");

        LinkedList<SyntaxToken> tokens = Lexer.Run(expression);

        SyntaxTree syntaxTree = Parser.buildSyntaxTree(tokens);

        Polynomial res = Evaluator.Evaluate(syntaxTree);

        return res;
    }
}
