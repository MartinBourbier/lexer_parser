package org.insane.project.src.ExpressionAnalysis.Syntax;

import org.insane.project.src.ExpressionAnalysis.Arithmetic.*;

public final class Evaluator
{
    private Evaluator()
    {
        throw new UnsupportedOperationException();
    }


    public static Polynomial Evaluate(SyntaxTree syntaxTree)
    {
        Polynomial res = new Polynomial();

        switch (syntaxTree._key.GetKind())
        {
            case SumToken:
                res = Polynomial.Add(Evaluate(syntaxTree._left), Evaluate(syntaxTree._right));
                break;
            
            case DifferenceToken:
                res = Polynomial.Substract(Evaluate(syntaxTree._left), Evaluate(syntaxTree._right));
                break;

            case StarToken:
                res = Polynomial.Multiply(Evaluate(syntaxTree._left), Evaluate(syntaxTree._right));
                break;

            case SlashToken:
                res = Polynomial.Divide(Evaluate(syntaxTree._left), Evaluate(syntaxTree._right));
                break;

            case PlusToken:
                res = Evaluate(syntaxTree._right);
                break;

            case MinusToken:
                res = Polynomial.Negate(Evaluate(syntaxTree._right));
                break;

            case NumberToken:
                SyntaxToken number = syntaxTree._key;
                Monomial m = new Monomial(number.GetValue());
                Polynomial p = new Polynomial(m);
                res = p;
                break;

            case VariableToken:
                res = new Polynomial(new Monomial(1, 1));
                break;

            default:
                break;
        }

        return res;
    }
}
