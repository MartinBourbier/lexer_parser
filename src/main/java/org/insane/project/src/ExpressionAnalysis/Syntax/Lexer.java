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

    // TODO: Refactor !?
    public static LinkedList<SyntaxToken> Run(String expression)
    {
        LinkedList<SyntaxToken> res = new LinkedList<SyntaxToken>();
        int pos = 0;

        while (pos < expression.length())
        {
            char c = expression.charAt(pos);
            SyntaxToken token = null;

            if (c >= '0' && c <= '9')
            {
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
            }
            else if (c == 'x')
            {
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
            }
            else if (c == '+')
            {
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
            }
            else if (c == '-')
            {
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
            }
            else if (c == '*')
            {
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
            }
            else if (c == '/')
            {
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
            }
            else if (c == '^')
            {
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
            }
            else if (c == '(')
            {
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
            }
            else if (c == ')')
            {
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
            }
            else if (c != ' ' && c != '\t' && c != '\r' && c != '\n')
                throw new IllegalArgumentException("Invalid expression: token of type " + token + " found after token " + res.getLast() + " (pos " + pos +')');

            if (token != null)
                res.addLast(token);
            
            ++pos;
        }

        return res;
    }
}
