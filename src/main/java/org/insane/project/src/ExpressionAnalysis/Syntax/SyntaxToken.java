package org.insane.project.src.ExpressionAnalysis.Syntax;

public class SyntaxToken
{
	private final SyntaxKind _kind;
	private int _value;

	public SyntaxToken(SyntaxKind kind)
	{
		this(kind, 0xdeadbeef);
	}
	
	public SyntaxToken(SyntaxKind kind, int value)
	{
		_kind = kind;
		_value = value;
	}
	
	public boolean IsKind(SyntaxKind kind)
	{
		return _kind.equals(kind);
	}
	
	public boolean IsOperator()
	{
		return IsKind(SyntaxKind.PlusToken) ||
			   IsKind(SyntaxKind.MinusToken) ||
			   IsKind(SyntaxKind.SumToken) ||
			   IsKind(SyntaxKind.DifferenceToken) ||
			   IsKind(SyntaxKind.HatToken) ||
			   IsKind(SyntaxKind.StarToken) ||
			   IsKind(SyntaxKind.SlashToken);
	}
	
	public boolean IsUnaryOperator()
	{
		return IsKind(SyntaxKind.PlusToken) || IsKind(SyntaxKind.MinusToken);
	}
	
	public boolean IsLeftAssociative()
	{
		return IsKind(SyntaxKind.SumToken) ||
			   IsKind(SyntaxKind.DifferenceToken) ||
			   IsKind(SyntaxKind.StarToken) ||
			   IsKind(SyntaxKind.SlashToken);
	}
	
	public int GetPrecedence()
	{
		switch (_kind)
		{
		case PlusToken:
		case MinusToken:
			return 4;
			
		case HatToken:
			return 3;
	
		case StarToken:
		case SlashToken:
			return 2;
			
		case SumToken:
		case DifferenceToken:
			return 1;
			
		default:
			return 0;
		}
	}
	
	public boolean HasGreaterPrecedence(SyntaxToken other)
	{
		return GetPrecedence() > other.GetPrecedence();
	}
	
	public boolean HasEqualPrecedence(SyntaxToken other)
	{
		return GetPrecedence() == other.GetPrecedence();
	}
	
	public SyntaxKind GetKind()
	{
		return _kind;
	}

	public int GetValue()
	{
		return _value;
	}
	
	@Override
	public String toString()
	{
		switch (_kind)
		{
		case SumToken:
			return "<binary +>";
			
		case PlusToken:
			return "<unary +>";
			
		case DifferenceToken:
			return "<binary ->";
			
		case MinusToken:
			return "<unary ->";

		case StarToken:
			return "<binary *>";

		case SlashToken:
			return "<binary />";

		case HatToken:
			return "<binary ^>";

		case OpenParenthesisToken:
			return "(";
			
		case CloseParenthesisToken:
			return ")";

		case VariableToken:
			return "<variable>";

		case NumberToken:
			return "<number " + String.valueOf(_value) + ">";

		default:
			return "<unknown>";
		}
	}
}
