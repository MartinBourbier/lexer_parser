package org.insane.project.src.ExpressionAnalysis.Syntax;

import java.util.LinkedList;
import java.util.Stack;

public final class Parser
{
    // This class can not be instanciated nor inherited from
    private Parser()
    {
        throw new UnsupportedOperationException();
    }

    private static Stack<SyntaxToken> _ToPolishNotation(LinkedList<SyntaxToken> tokens)
    {
        Stack<SyntaxToken> res = new Stack<SyntaxToken>();
        Stack<SyntaxToken> op = new Stack<SyntaxToken>();

        SyntaxToken curr = tokens.getFirst();

        while (curr != null)
        {
            if (curr.IsOperator())
                ParseOperator(res, op, curr);
            else if (curr.IsKind(SyntaxKind.OpenParenthesisToken))
                op.push(curr);
            else if (curr.IsKind(SyntaxKind.CloseParenthesisToken))
                ParseExpression(res, op);
            else
                res.push(curr);

            try
            {
                curr = tokens.removeFirst();
            }
            catch (Exception e)
            {
                curr = null;
            }
        }

        while (op.size() > 0)
        {
            SyntaxToken token = op.pop();

            if (token.IsKind(SyntaxKind.OpenParenthesisToken) ||
                token.IsKind(SyntaxKind.CloseParenthesisToken))
                throw new IllegalArgumentException("Mismatched parenthesis in expression");
            
            res.push(token);
        }

        return res;
    }

    private static void ParseExpression(Stack<SyntaxToken> res, Stack<SyntaxToken> op)
    {
        boolean found = false;
        SyntaxToken top = op.pop();

        while (!found && top != null)
        {
            if (top.IsKind(SyntaxKind.OpenParenthesisToken))
                found = true;
            else
                res.push(top);

            top = op.pop();
        }

        if (!found)
            throw new IllegalArgumentException("Mismatched parenthesis in expression");
    }

    private static void ParseOperator(Stack<SyntaxToken> res, Stack<SyntaxToken> op, SyntaxToken curr)
    {
        SyntaxToken top;

        try
        {
            top = op.peek();
        }
        catch (Exception e)
        {
            top = null;
        }

        while (top != null && 
              (top.HasGreaterPrecedence(curr) || 
              (top.HasEqualPrecedence(curr) && curr.IsLeftAssociative())) &&
              !top.IsKind(SyntaxKind.OpenParenthesisToken))
        {
            res.push(op.pop());
            top = op.peek();
        }

        op.push(curr);
    }

    private static SyntaxTree _BuildSyntaxTree(Stack<SyntaxToken> tokens)
    {
        if (tokens.size() <= 0)
            return null;

        SyntaxTree syntaxTree = new SyntaxTree();
        SyntaxToken token = tokens.pop();
        syntaxTree._key = token;

        if (token.IsOperator())
        {
            if (tokens.size() <= 0)
                throw new IllegalArgumentException("Missing right hand wide of operator '" + token + "'");

            syntaxTree._right = _BuildSyntaxTree(tokens);

            if (!token.IsUnaryOperator())
            {
                if (tokens.size() <= 0)
                    throw new IllegalArgumentException("Missing left hand wide of operator '" + token + "'");

                syntaxTree._left = _BuildSyntaxTree(tokens);
            }
        }

        return syntaxTree;
    }

    public static SyntaxTree buildSyntaxTree(LinkedList<SyntaxToken> tokens)
    {
        Stack<SyntaxToken> polish = _ToPolishNotation(tokens);

        if (polish.size() <= 0)
            throw new IllegalArgumentException("Expression is empty");

        return _BuildSyntaxTree(polish);
    }
}