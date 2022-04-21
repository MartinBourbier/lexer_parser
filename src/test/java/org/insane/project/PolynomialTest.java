package org.insane.project;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

import org.insane.project.src.ExpressionAnalysis.Arithmetic.Monomial;
import org.insane.project.src.ExpressionAnalysis.Arithmetic.Polynomial;

public class PolynomialTest
{
    private static boolean PolyContains(Polynomial p, Monomial m)
    {
        for (Monomial m2 : p.GetMonomials())
        {
            if (Monomial.Equals(m, m2))
            {
                return true;
            }
        }
        return false;
    }

    private static boolean PolyEquals(Polynomial p1, Polynomial p2)
    {
        for (Monomial m : p1.GetMonomials())
        {
            if (!PolyContains(p2, m))
            {
                return false;
            }
        }

        return p1.GetMonomials().size() == p2.GetMonomials().size();
    }

    @Test
    public void Empty()
    {
        Polynomial p = new Polynomial();
        assertTrue(p.GetMonomials().size() == 0);
    }

    @Test
    public void Constructor_WithZero()
    {
        Polynomial p = new Polynomial(new Monomial(0));
        assertTrue(p.GetMonomials().size() == 0);
    }

    @Test
    public void Constructor_WithDifferentThanZero()
    {
        Polynomial p = new Polynomial(new Monomial(1));
        assertTrue(p.GetMonomials().size() == 1);
    }

    @Test
    public void Constructor_WithDifferentThanZero_Bis()
    {
        Polynomial p = new Polynomial(new Monomial(1, 3));
        assertTrue(p.GetMonomials().size() == 1);
    }

    @Test
    public void UnaryMinus_NumberOnly_Simple()
    {
        Polynomial p = new Polynomial(new Monomial(4, 0));
        p = Polynomial.Negate(p);

        Polynomial expected = new Polynomial(new Monomial(-4, 0));
        assertTrue(p.GetMonomials().size() == 1);
        assertTrue(PolyEquals(p, expected));
    }

    @Test
    public void UnaryMinus_NumberOnly_Negative()
    {
        Polynomial p = new Polynomial(new Monomial(-4, 0));
        p = Polynomial.Negate(p);

        Polynomial expected = new Polynomial(new Monomial(4, 0));
        assertTrue(p.GetMonomials().size() == 1);
        assertTrue(PolyEquals(p, expected));
    }

    @Test
    public void UnaryMinus_NumberOnly_Zero()
    {
        Polynomial p = new Polynomial(new Monomial(0, 0));
        p = Polynomial.Negate(p);

        Polynomial expected = new Polynomial(new Monomial(0, 0));
        assertTrue(p.GetMonomials().size() == 0);
        assertTrue(PolyEquals(p, expected));
    }

    @Test
    public void UnaryMinus_WithVariable_Simple()
    {
        Polynomial p = new Polynomial(new Monomial(4, 1));
        p = Polynomial.Negate(p);

        Polynomial expected = new Polynomial(new Monomial(-4, 1));
        assertTrue(p.GetMonomials().size() == 1);
        assertTrue(PolyEquals(p, expected));
    }

    @Test
    public void UnaryMinus_WithVariable_Negative()
    {
        Polynomial p = new Polynomial(new Monomial(-4, 1));
        p = Polynomial.Negate(p);

        Polynomial expected = new Polynomial(new Monomial(4, 1));
        assertTrue(p.GetMonomials().size() == 1);
        assertTrue(PolyEquals(p, expected));
    }

    @Test
    public void UnaryMinus_WithVariable_DegreeTwo_Only()
    {
        Polynomial p = new Polynomial(new Monomial(4, 2));
        p = Polynomial.Negate(p);

        Polynomial expected = new Polynomial(new Monomial(-4, 2));
        assertTrue(p.GetMonomials().size() == 1);
        assertTrue(PolyEquals(p, expected));
    }

    @Test
    public void UnaryMinus_WithVariable_DegreeTwo_Complete()
    {
        Polynomial p = new Polynomial(new Monomial(12, 0));
        p = Polynomial.Add(p, new Polynomial(new Monomial(-7, 1)));
        p = Polynomial.Add(p, new Polynomial(new Monomial(13, 2)));

        p = Polynomial.Negate(p);

        Polynomial expected = new Polynomial(new Monomial(-12, 0));
        expected = Polynomial.Add(expected, new Polynomial(new Monomial(7, 1)));
        expected = Polynomial.Add(expected, new Polynomial(new Monomial(-13, 2)));

        assertTrue(p.GetMonomials().size() == 3);
        assertTrue(PolyEquals(p, expected));
    }

    @Test
    public void Add_NumberOnly_Simple()
    {
        Polynomial p1 = new Polynomial(new Monomial(4));
        Polynomial p2 = new Polynomial(new Monomial(7));
        Polynomial res = Polynomial.Add(p1, p2);

        Polynomial expected = new Polynomial(new Monomial(11));
        assertTrue(res.GetMonomials().size() == 1);
        assertTrue(PolyEquals(res, expected));
    }

    @Test
    public void Add_NumberOnly_TrickyZero()
    {
        Polynomial p1 = new Polynomial(new Monomial(4));
        Polynomial p2 = new Polynomial(new Monomial(-4));
        Polynomial res = Polynomial.Add(p1, p2);

        Polynomial expected = new Polynomial(new Monomial(0));
        assertTrue(res.GetMonomials().size() == 0);
        assertTrue(PolyEquals(res, expected));
    }

    @Test
    public void Add_Basic_SameDegree()
    {
        Polynomial p1 = new Polynomial(new Monomial(4, 2));
        Polynomial p2 = new Polynomial(new Monomial(2, 2));
        Polynomial res = Polynomial.Add(p1, p2);

        Polynomial expected = new Polynomial(new Monomial(6, 2));
        assertTrue(res.GetMonomials().size() == 1);
        assertTrue(PolyEquals(res, expected));
    }

    @Test
    public void Add_Basic_SameDegree_Zero()
    {
        Polynomial p1 = new Polynomial(new Monomial(4, 2));
        Polynomial p2 = new Polynomial(new Monomial(-4, 2));
        Polynomial res = Polynomial.Add(p1, p2);

        Polynomial expected = new Polynomial(new Monomial(0));
        assertTrue(res.GetMonomials().size() == 0);
        assertTrue(PolyEquals(res, expected));
    }

    @Test
    public void Add_Basic_TwoDegree()
    {
        Polynomial p = new Polynomial(new Monomial(4, 2));
        p = Polynomial.Add(p, new Polynomial(new Monomial(7, 1)));
        p = Polynomial.Add(p, new Polynomial(new Monomial(-1, 1)));
        p = Polynomial.Add(p, new Polynomial(new Monomial(2, 2)));

        Polynomial expected = new Polynomial(new Monomial(6, 2));
        expected = Polynomial.Add(expected, new Polynomial(new Monomial(6, 1)));

        assertTrue(p.GetMonomials().size() == 2);
        assertTrue(PolyEquals(p, expected));
    }

    @Test
    public void Add_WithVariable_MixedOrder()
    {
        Polynomial p1 = new Polynomial(new Monomial(1, 2));
        p1 = Polynomial.Add(p1, new Polynomial(new Monomial(5, 1)));
        p1 = Polynomial.Add(p1, new Polynomial(new Monomial(4)));

        Polynomial p2 = new Polynomial(new Monomial(-5, 1));
        p2 = Polynomial.Add(p2, new Polynomial(new Monomial(-2)));
        p2 = Polynomial.Add(p2, new Polynomial(new Monomial(1, 2)));

        Polynomial res = Polynomial.Add(p1, p2);

        Polynomial expected = new Polynomial(new Monomial(2, 2));
        expected = Polynomial.Add(expected, new Polynomial(new Monomial(2)));

        assertTrue(res.GetMonomials().size() == 2);
        assertTrue(PolyEquals(res, expected));
    }

    @Test
    public void Minus_NumberOnly_Simple()
    {
        Polynomial p1 = new Polynomial(new Monomial(4));
        Polynomial p2 = new Polynomial(new Monomial(7));
        Polynomial res = Polynomial.Substract(p1, p2);

        Polynomial expected = new Polynomial(new Monomial(-3));
        assertTrue(res.GetMonomials().size() == 1);
        assertTrue(PolyEquals(res, expected));
    }

    @Test
    public void Minus_NumberOnly_NotZero()
    {
        Polynomial p1 = new Polynomial(new Monomial(4));
        Polynomial p2 = new Polynomial(new Monomial(-4));
        Polynomial res = Polynomial.Substract(p1, p2);

        Polynomial expected = new Polynomial(new Monomial(8));
        assertTrue(res.GetMonomials().size() == 1);
        assertTrue(PolyEquals(res, expected));
    }

    @Test
    public void Minus_NumberOnly_Zero()
    {
        Polynomial p1 = new Polynomial(new Monomial(4));
        Polynomial p2 = new Polynomial(new Monomial(4));
        Polynomial res = Polynomial.Substract(p1, p2);

        Polynomial expected = new Polynomial(new Monomial(0));
        assertTrue(res.GetMonomials().size() == 0);
        assertTrue(PolyEquals(res, expected));
    }

    @Test
    public void Minus_Basic_SameDegree()
    {
        Polynomial p1 = new Polynomial(new Monomial(4, 2));
        Polynomial p2 = new Polynomial(new Monomial(2, 2));
        Polynomial res = Polynomial.Substract(p1, p2);

        Polynomial expected = new Polynomial(new Monomial(2, 2));
        assertTrue(res.GetMonomials().size() == 1);
        assertTrue(PolyEquals(res, expected));
    }

    @Test
    public void Minus_Basic_SameDegree_Zero()
    {
        Polynomial p1 = new Polynomial(new Monomial(4, 2));
        Polynomial p2 = new Polynomial(new Monomial(4, 2));
        Polynomial res = Polynomial.Substract(p1, p2);

        Polynomial expected = new Polynomial(new Monomial(0));
        assertTrue(res.GetMonomials().size() == 0);
        assertTrue(PolyEquals(res, expected));
    }
}