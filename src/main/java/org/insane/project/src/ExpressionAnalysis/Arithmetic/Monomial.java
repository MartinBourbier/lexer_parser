package org.insane.project.src.ExpressionAnalysis.Arithmetic;

public final class Monomial
{
	private final int _coef;
	private int _degree;
	
	public boolean IsZero() { return _coef == 0; }
	public int Coef() { return _coef; }
	public int Degree() { return _degree; }
	
	public Monomial(int coef)
	{
		this(coef, 0);
	}
	
	public Monomial(int coef, int degree)
	{
		_coef = coef;
		_degree = degree;
		
		if (IsZero())
			_degree = -1;
		else if (_degree <= -1)
			throw new IllegalArgumentException();
	}
	
	public static boolean HasSameDegree(Monomial m1, Monomial m2)
	{
		return m1.Degree() == m2.Degree();
	}
	
	public static boolean Equals(Monomial m1, Monomial m2)
	{
		return HasSameDegree(m1, m2) && m1.Coef() == m2.Coef();
	}
	
	public static boolean NotEquals(Monomial m1, Monomial m2)
	{
		return !Equals(m1, m2);
	}
	
	public static Monomial Negate(Monomial m)
	{
		return new Monomial(-m.Coef(), m.Degree());
	}
	
	public static Monomial Add(Monomial m1, Monomial m2)
	{
		if (m1.IsZero())
			return m2;
		
		if (m2.IsZero())
			return m1;
		
		if (!HasSameDegree(m1, m2))
			throw new ArithmeticException("Can not sum monomials of different degrees.");
	
		return new Monomial(m1.Coef() + m2.Coef(), m1.Degree());
	}
	
	public static Monomial Substract(Monomial m1, Monomial m2)
	{
		return Add(m1, Negate(m2));
	}
	
	public static Monomial Multiply(Monomial m1, Monomial m2)
	{
		return new Monomial(m1.Coef() * m2.Coef(), m1.Degree() + m2.Degree());
	}
	
	public static Monomial Divide(Monomial m1, Monomial m2)
	{
		if (m2.IsZero())
			throw new ArithmeticException("Division by null monomial.");
		
		return new Monomial(m1.Coef() / m2.Coef(), m1.Degree() - m2.Degree());
	}
	
	@Override
	public String toString()
	{
		if (_degree == 0)
			return String.valueOf(_coef);
		
		if (_degree == 1)
			return String.valueOf(_coef) + 'x';
		
		String coef = "";
		
		if (_coef == -1)
			coef += '-';
		else if (_coef != 1)
			coef += String.valueOf(_coef);
		
		return coef + "x^" + _degree;
	}
}
