package org.insane.project;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

import org.insane.project.src.ExpressionAnalysis.Arithmetic.Polynomial;
import org.insane.project.src.ExpressionAnalysis.Arithmetic.Monomial;
import org.insane.project.src.ExpressionAnalysis.Computation;

public class EvaluatorTest
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
    public void Empty_Nothing()
    {
        assertTrue(true);
    }

    @Test
    public void PositiveNumber_Basic_Digit()
    {
        Polynomial p = Computation.Compute("3");

        Polynomial expected = new Polynomial(new Monomial(3));

        assertTrue(PolyEquals(p, expected));
    }

    @Test
    public void PositiveNumber_Basic_TripleDigit()
    {
        Polynomial p = Computation.Compute("123");

        Polynomial expected = new Polynomial(new Monomial(123));

        assertTrue(PolyEquals(p, expected));
    }

    @Test
    public void PositiveNumber_Basic_WhiteSpace_Begin()
    {
        Polynomial p = Computation.Compute("    123");

        Polynomial expected = new Polynomial(new Monomial(123));

        assertTrue(PolyEquals(p, expected));
    }

    @Test
    public void PositiveNumber_Basic_WhiteSpace_End()
    {
        Polynomial p = Computation.Compute("123    ");

        Polynomial expected = new Polynomial(new Monomial(123));

        assertTrue(PolyEquals(p, expected));
    }

    @Test
    public void PositiveNumber_Basic_WhiteSpace_BeginEnd()
    {
        Polynomial p = Computation.Compute("    123    ");

        Polynomial expected = new Polynomial(new Monomial(123));

        assertTrue(PolyEquals(p, expected));
    }

    @Test
    public void VaritableOnly_Basic_Digit()
    {
        Polynomial p = Computation.Compute("x");

        Polynomial expected = new Polynomial(new Monomial(1, 1));

        assertTrue(PolyEquals(p, expected));
    }

    @Test
    public void VaritableOnly_Basic_WhiteSpace_BeginEnd()
    {
        Polynomial p = Computation.Compute("   x   ");

        Polynomial expected = new Polynomial(new Monomial(1, 1));

        assertTrue(PolyEquals(p, expected));
    }
}