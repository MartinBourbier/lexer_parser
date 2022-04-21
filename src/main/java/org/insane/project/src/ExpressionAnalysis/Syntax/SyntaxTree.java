package org.insane.project.src.ExpressionAnalysis.Syntax;

public final class SyntaxTree
{
    public SyntaxToken _key;
    public SyntaxTree _left;
    public SyntaxTree _right;

    public SyntaxTree()
    {
        this(null, null, null);
    }

    public SyntaxTree(SyntaxToken key, SyntaxTree left, SyntaxTree right)
    {
        _key = key;
        _left = left;
        _right = right;
    }
}
