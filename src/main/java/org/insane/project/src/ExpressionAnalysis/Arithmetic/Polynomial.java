package org.insane.project.src.ExpressionAnalysis.Arithmetic;

import java.util.ArrayList;
import java.util.List;

public final class Polynomial
{
	private List<Monomial> _monomials;
	
	private boolean IsZero() { return _monomials.size() == 0; }
	public List<Monomial> GetMonomials() { return _monomials; }
	
	public Polynomial()
	{
		_monomials = new ArrayList<Monomial>();
	}
	
	public Polynomial(Monomial m)
	{
		_monomials = new ArrayList<Monomial>();

		if (!m.IsZero())
			_monomials.add(m);
	}
	
	public static Polynomial Negate(Polynomial p)
	{
		Polynomial res = new Polynomial();
		
		for (Monomial m : p._monomials)
		{
			res._monomials.add(Monomial.Negate(m));
		}
		
		return res;
	}
	
	public static Polynomial Add(Polynomial p1, Polynomial p2)
	{
		Polynomial res = new Polynomial();
		
		int i = 0;
		int j = 0;
		
		int n = p1._monomials.size();
		int m = p2._monomials.size();
		
		while (i < n && j < m)
		{
			Monomial m1 = p1._monomials.get(i);
			Monomial m2 = p2._monomials.get(j);
			
			if (Monomial.HasSameDegree(m1, m2))
			{
				Monomial m3 = Monomial.Add(m1, m2);
				
				if (!m3.IsZero())
					res._monomials.add(m3);
				
				++i;
				++j;
			}
			else if (m1.Degree() < m2.Degree())
			{
				res._monomials.add(m1);
				++i;
			}
			else
			{
				res._monomials.add(m2);
				++j;
			}
		}
		
		while (i < n)
			res._monomials.add(p1._monomials.get(i++));

		while (j < m)
			res._monomials.add(p2._monomials.get(j++));
		
		return res;
	}
	
	public static Polynomial Substract(Polynomial p1, Polynomial p2)
	{
		return Add(p1, Negate(p2));
	}
	
	public static Polynomial Multiply(Polynomial p1, Polynomial p2)
	{
		Polynomial res = new Polynomial();
		
		int n = p1._monomials.size();
		int m = p2._monomials.size();
		
		for (int i = 0; i < n; ++i)
		{
			for (int j = 0; j < m; ++j)
			{
				res = Add(res, new Polynomial(Monomial.Multiply(p1._monomials.get(i), p2._monomials.get(j))));
			}
		}
		
		return res;
	}
	
	public static Polynomial divide(Polynomial p1, Polynomial p2)
	{
		if (p2.IsZero())
			throw new ArithmeticException("Division by null polynomial.");
		
		int i = p2._monomials.size();
		
		Polynomial q = new Polynomial();
		Polynomial r = p1;
		
		while (!r.IsZero() && r.GetDegree() >= p2.GetDegree() && i >= 0)
		{
			Polynomial t = new Polynomial(Monomial.Divide(r.GetLead(), p2.GetLead()));
			
			if (t.IsZero())
				return q;
			
			q = Add(q, t);
			r = Multiply(Substract(r, t), p2);
			
			--i;
		}
		
		return q;
	}
	
	public Monomial GetLead()
	{
		if (IsZero())
			return null;
		
		return _monomials.get(_monomials.size() - 1);
	}
	
	public int GetDegree()
	{
		Monomial lead = GetLead();
		return ((Object) lead == null) ? -1 : lead.Degree();
	}
	
	@Override
	public String toString()
	{
		if (IsZero())
			return "0";
		
		String res = "";
		
		for (int i = _monomials.size() - 1; i > 0; --i)
		{
			res += _monomials.get(i);
			res += " + ";
		}
		
		return res + _monomials.get(0);
	}
}