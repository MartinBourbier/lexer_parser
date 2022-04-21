package org.insane.project.src.ExpressionAnalysis.Syntax;

import java.util.LinkedList;

public final class Lexer
{
    private static int _ReadNumber(String expression, int pos)
    {
        int val = 0;

        while (pos < expression.length())
        {
            char c = expression.charAt(pos);

            if (c >= '0' && c <= '9')
            {
                ++pos;
                val = val * 10 + (int) (c - '0');
            }
            else
            {
                --pos;
                return val;
            }
        }

        return val;
    }

    public static LinkedList<SyntaxToken> Run(String expression)
    {
        LinkedList<SyntaxToken> res = new LinkedList<SyntaxToken>();
        int pos = 0;

        while (pos < expression.length())
        {
            char c = expression.charAt(pos);
            SyntaxToken token = null;

            if (c >= '0' && c <= '9')
                token = LexNumber(expression, res, pos);
            else if (c == 'x')
                token = LexVariable(res, pos);
            else if (c == '+')
                token = LexOperatorPlus(res);
            else if (c == '-')
                token = LexOperatorMinus(res);
            else if (c == '*')
                token = LexOperatorStar(res, pos);
            else if (c == '/')
                token = LexOperatorSlash(res, pos);
            else if (c == '^')
                token = LexOperatorHat(res, pos);
            else if (c == '(')
                token = LexOpenParenthesis(res, pos);
            else if (c == ')')
                token = LexCloseParenthesis(res, pos);
            else if (c != ' ' && c != '\t' && c != '\r' && c != '\n')
                throw new IllegalArgumentException("Invalid expression: token of type " + token + " found after token " + res.getLast() + " (pos " + pos +')');

            if (token != null)
                res.addLast(token);
            
            ++pos;
        }

        return res;
    }

    private static SyntaxToken LexCloseParenthesis(LinkedList<SyntaxToken> res, int pos) {
        SyntaxToken token;
        token = new SyntaxToken(SyntaxKind.CloseParenthesisToken);

        SyntaxToken last;

        try
        {
            last = res.getLast();
        }
        catch (Exception e)
        {
            last = null;
        }

        boolean valid = (last == null ||
                         last.IsKind(SyntaxKind.NumberToken) ||
                         last.IsKind(SyntaxKind.VariableToken) ||
                         last.IsKind(SyntaxKind.CloseParenthesisToken));

        if (!valid)
            throw new IllegalArgumentException("Invalid.expression: token of type " + token + " found after token " + last + " (pos " + pos + ')');
        return token;
    }

    private static SyntaxToken LexOpenParenthesis(LinkedList<SyntaxToken> res, int pos) {
        SyntaxToken token;
        token = new SyntaxToken(SyntaxKind.OpenParenthesisToken);

        SyntaxToken last;

        try
        {
            last = res.getLast();
        }
        catch (Exception e)
        {
            last = null;
        }

        boolean valid = (last == null ||
                         last.IsOperator() ||
                         last.IsKind(SyntaxKind.NumberToken) ||
                         last.IsKind(SyntaxKind.VariableToken) ||
                         last.IsKind(SyntaxKind.OpenParenthesisToken) ||
                         last.IsKind(SyntaxKind.CloseParenthesisToken));

        if (!valid)
            throw new IllegalArgumentException("Invalid.expression: token of type " + token + " found after token " + last + " (pos " + pos + ')');
        else if (last != null &&
                 (last.IsKind(SyntaxKind.NumberToken) ||
                 last.IsKind(SyntaxKind.VariableToken) ||
                 last.IsKind(SyntaxKind.CloseParenthesisToken)))
            res.addLast(new SyntaxToken(SyntaxKind.StarToken));
        return token;
    }

    private static SyntaxToken LexOperatorHat(LinkedList<SyntaxToken> res, int pos) {
        SyntaxToken token;
        token = new SyntaxToken(SyntaxKind.HatToken);

        SyntaxToken last;

        try
        {
            last = res.getLast();
        }
        catch (Exception e)
        {
            last = null;
        }

        boolean valid = (last != null &&
                         (last.IsKind(SyntaxKind.NumberToken) ||
                         last.IsKind(SyntaxKind.VariableToken) ||
                         last.IsKind(SyntaxKind.CloseParenthesisToken)));

        if (!valid)
        {
            throw new IllegalArgumentException("Invalid expression: token of type " + token + " found after token " + last + " (pos " + pos + ')');
        }
        return token;
    }

    private static SyntaxToken LexOperatorSlash(LinkedList<SyntaxToken> res, int pos) {
        SyntaxToken token;
        token = new SyntaxToken(SyntaxKind.SlashToken);

        SyntaxToken last;

        try
        {
            last = res.getLast();
        }
        catch (Exception e)
        {
            last = null;
        }

        boolean valid = (last != null &&
                         (last.IsKind(SyntaxKind.NumberToken) ||
                         last.IsKind(SyntaxKind.VariableToken) ||
                         last.IsKind(SyntaxKind.CloseParenthesisToken)));

        if (!valid)
        {
            throw new IllegalArgumentException("Invalid expression: token of type " + token + " found after token " + last + " (pos " + pos + ')');
        }
        return token;
    }

    private static SyntaxToken LexOperatorStar(LinkedList<SyntaxToken> res, int pos) {
        SyntaxToken token;
        token = new SyntaxToken(SyntaxKind.StarToken);

        SyntaxToken last;

        try
        {
            last = res.getLast();
        }
        catch (Exception e)
        {
            last = null;
        }

        boolean valid = (last != null &&
                         (last.IsKind(SyntaxKind.NumberToken) ||
                         last.IsKind(SyntaxKind.VariableToken) ||
                         last.IsKind(SyntaxKind.CloseParenthesisToken)));

        if (!valid)
        {
            throw new IllegalArgumentException("Invalid expression: token of type " + token + " found after token " + last + " (pos " + pos + ')');
        }
        return token;
    }

    private static SyntaxToken LexOperatorMinus(LinkedList<SyntaxToken> res) {
        SyntaxToken token;
        SyntaxToken last;

        try
        {
            last = res.getLast();
        }
        catch (Exception e)
        {
            last = null;
        }

        boolean isUnary = (last == null ||
                           last.IsOperator() ||
                           last.IsKind(SyntaxKind.OpenParenthesisToken));

        if (isUnary)
            token = new SyntaxToken(SyntaxKind.MinusToken);
        else
            token = new SyntaxToken(SyntaxKind.DifferenceToken);
        return token;
    }

    private static SyntaxToken LexOperatorPlus(LinkedList<SyntaxToken> res) {
        SyntaxToken token;
        SyntaxToken last;

        try
        {
            last = res.getLast();
        }
        catch (Exception e)
        {
            last = null;
        }

        boolean isUnary = (last == null ||
                           last.IsOperator() ||
                           last.IsKind(SyntaxKind.OpenParenthesisToken));

        if (isUnary)
            token = new SyntaxToken(SyntaxKind.PlusToken);
        else
            token = new SyntaxToken(SyntaxKind.SumToken);
        return token;
    }

    private static SyntaxToken LexVariable(LinkedList<SyntaxToken> res, int pos) {
        SyntaxToken token;
        token = new SyntaxToken(SyntaxKind.VariableToken);

        SyntaxToken last;

        try
        {
            last = res.getLast();
        }
        catch (Exception e)
        {
            last = null;
        }

        boolean valid = (last == null ||
                         last.IsOperator() ||
                         last.IsKind(SyntaxKind.OpenParenthesisToken) ||
                         last.IsKind(SyntaxKind.NumberToken));

        if (!valid)
            throw new IllegalArgumentException("Invalid expression: token of type " + token + " found afer " + last + " (pos " + pos + ')');
        else if (last != null && last.IsKind(SyntaxKind.NumberToken))
            res.addLast(new SyntaxToken(SyntaxKind.StarToken));
        return token;
    }

    private static SyntaxToken LexNumber(String expression, LinkedList<SyntaxToken> res, int pos) {
        SyntaxToken token;
        token = new SyntaxToken(SyntaxKind.NumberToken, _ReadNumber(expression, pos));

        SyntaxToken last;

        try
        {
            last = res.getLast();
        }
        catch (Exception e)
        {
            last = null;
        }

        boolean valid = (last == null ||
                         last.IsOperator() ||
                         last.IsKind(SyntaxKind.VariableToken) ||
                         last.IsKind(SyntaxKind.OpenParenthesisToken) ||
                         last.IsKind(SyntaxKind.CloseParenthesisToken));

        if (!valid)
            throw new IllegalArgumentException("Invalid expression: token of type " + token + " found afer " + last + " (pos " + pos + ')');

        // Implicit multiplication?
        else if (last != null &&
                 (last.IsKind(SyntaxKind.VariableToken) ||
                 last.IsKind(SyntaxKind.CloseParenthesisToken)))
            res.addLast(new SyntaxToken(SyntaxKind.StarToken));
        return token;
    }
}
