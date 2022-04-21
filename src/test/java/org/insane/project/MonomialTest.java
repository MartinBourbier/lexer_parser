package org.insane.project;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Test;

import org.insane.project.src.ExpressionAnalysis.Arithmetic.Monomial;

public class MonomialTest 
{
    @Test(expected = IllegalArgumentException.class)
    public void NegativeCoef_MustThrow_MinusFour()
    {
        new Monomial(1, -4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void NegativeCoef_MustThrow_MinusOne()
    {
        new Monomial(7, -1);
    }

    @Test
    public void BasicEqual()
    {
        Monomial m1 = new Monomial(1, 2);
        Monomial m2 = new Monomial(1, 2);

        assertTrue(Monomial.Equals(m1, m2));
        assertFalse(Monomial.NotEquals(m1, m2));
    }

    @Test
    public void BasicNotEqual()
    {
        Monomial m1 = new Monomial(1, 2);
        Monomial m2 = new Monomial(1, 3);

        assertFalse(Monomial.Equals(m1, m2));
        assertTrue(Monomial.NotEquals(m1, m2));
    }

    @Test
    public void SameCoefNotEqual()
    {
        Monomial m1 = new Monomial(1, 2);
        Monomial m2 = new Monomial(1, 10);

        assertFalse(Monomial.Equals(m1, m2));
        assertTrue(Monomial.NotEquals(m1, m2));
    }

    @Test
    public void SameDegreeNotEqual()
    {
        Monomial m1 = new Monomial(1, 2);
        Monomial m2 = new Monomial(3, 2);

        assertFalse(Monomial.Equals(m1, m2));
        assertTrue(Monomial.NotEquals(m1, m2));
    }

    @Test
    public void SameDegree()
    {
        Monomial m1 = new Monomial(1, 2);
        Monomial m2 = new Monomial(4, 2);

        Monomial expected = new Monomial(5, 2);

        assertTrue(Monomial.HasSameDegree(m1, m2));
        assertTrue(Monomial.HasSameDegree(m1, expected));
        assertTrue(Monomial.Equals(Monomial.Add(m1, m2), expected));
    }

    @Test
    public void SameDegree_NegativeCoef()
    {
        Monomial m1 = new Monomial(1, 2);
        Monomial m2 = new Monomial(-4, 2);

        Monomial expected = new Monomial(-3, 2);

        assertTrue(Monomial.HasSameDegree(m1, m2));
        assertTrue(Monomial.HasSameDegree(m1, expected));
        assertTrue(Monomial.Equals(Monomial.Add(m1, m2), expected));
    }

    private static void Test_Add_Monomial(Monomial m1, Monomial m2)
    {
        Monomial.Add(m1, m2);
    }

    @Test(expected = ArithmeticException.class)
    public void DifferentDegree_Basic()
    {
        Monomial m1 = new Monomial(1, 2);
        Monomial m2 = new Monomial(2, 1);

        Test_Add_Monomial(m1, m2);
    }

    @Test(expected = ArithmeticException.class)
    public void DifferentDegree_Zero_CoefNotZero()
    {
        Monomial m1 = new Monomial(1, 2);
        Monomial m2 = new Monomial(3);

        Test_Add_Monomial(m1, m2);
    }

    @Test
    public void SameDegree_Zero_CoefZero()
    {
        Monomial m1 = new Monomial(1, 2);
        Monomial m2 = new Monomial(0, 10);

        Monomial expected = new Monomial(1, 2);

        assertTrue(Monomial.Equals(Monomial.Add(m1, m2), expected));
    }

    @Test
    public void SameDegree_Zero_CoefZero_Reversed()
    {
        Monomial m1 = new Monomial(1, 2);
        Monomial m2 = new Monomial(0, 10);

        Monomial expected = new Monomial(1, 2);

        assertTrue(Monomial.Equals(Monomial.Add(m2, m1), expected));
    }

    @Test
    public void Simple()
    {
        Monomial m1 = new Monomial(1, 2);

        Monomial expected = new Monomial(-1, 2);
        assertTrue(Monomial.Equals(Monomial.Negate(m1), expected));
    }

    @Test
    public void Simple_DegreeZero()
    {
        Monomial m1 = new Monomial(14, 0);

        Monomial expected = new Monomial(-14, 0);
        assertTrue(Monomial.Equals(Monomial.Negate(m1), expected));
    }

    @Test
    public void Simple_CoefZero()
    {
        Monomial m1 = new Monomial(0, 2);

        Monomial expected = new Monomial(0, 2);
        assertTrue(Monomial.Equals(Monomial.Negate(m1), expected));
    }

    @Test
    public void Simple_Negative()
    {
        Monomial m1 = new Monomial(-1, 2);

        Monomial expected = new Monomial(1, 2);
        assertTrue(Monomial.Equals(Monomial.Negate(m1), expected));
    }

    @Test
    public void Substract_SameDegree()
    {
        Monomial m1 = new Monomial(1, 2);
        Monomial m2 = new Monomial(4, 2);

        Monomial expected = new Monomial(-3, 2);
        assertTrue(Monomial.Equals(Monomial.Substract(m1, m2), expected));
    }

    @Test
    public void Substract_SameDegree_NegativeCoef()
    {
        Monomial m1 = new Monomial(1, 2);
        Monomial m2 = new Monomial(-4, 2);

        Monomial expected = new Monomial(5, 2);
        assertTrue(Monomial.Equals(Monomial.Substract(m1, m2), expected));
    }

    private static void Test_Substract_Monomial(Monomial m1, Monomial m2)
    {
        Monomial.Substract(m1, m2);
    }

    @Test(expected = ArithmeticException.class)
    public void Substract_DifferentDegree_Basic()
    {
        Monomial m1 = new Monomial(1, 2);
        Monomial m2 = new Monomial(2, 1);

        Test_Substract_Monomial(m1, m2);
    }

    @Test(expected = ArithmeticException.class)
    public void Substract_DifferentDegree_Zero_CoefNotZero()
    {
        Monomial m1 = new Monomial(1, 2);
        Monomial m2 = new Monomial(3);

        Test_Substract_Monomial(m1, m2);
    }

    @Test
    public void Substract_SameDegree_Zero_CoefZero()
    {
        Monomial m1 = new Monomial(1, 2);
        Monomial m2 = new Monomial(0, 10);

        Monomial expected = new Monomial(1, 2);

        assertTrue(Monomial.Equals(Monomial.Substract(m1, m2), expected));
    }

    @Test
    public void Substract_SameDegree_Zero_CoefZero_Reversed()
    {
        Monomial m1 = new Monomial(1, 2);
        Monomial m2 = new Monomial(0, 10);

        Monomial expected = new Monomial(-1, 2);

        assertTrue(Monomial.Equals(Monomial.Substract(m2, m1), expected));
    }

    @Test
    public void Multiply_SameDegree()
    {
        Monomial m1 = new Monomial(1, 2);
        Monomial m2 = new Monomial(4, 2);

        Monomial expected = new Monomial(4, 4);
        assertTrue(Monomial.Equals(Monomial.Multiply(m1, m2), expected));
    }

    @Test
    public void Multiply_SameDegree_NegativeCoef()
    {
        Monomial m1 = new Monomial(1, 2);
        Monomial m2 = new Monomial(-4, 2);

        Monomial expected = new Monomial(-4, 4);
        assertTrue(Monomial.Equals(Monomial.Multiply(m1, m2), expected));
    }

    @Test
    public void Multiply_DifferentDegree()
    {
        Monomial m1 = new Monomial(1, 2);
        Monomial m2 = new Monomial(4, 3);

        Monomial expected = new Monomial(4, 5);
        assertTrue(Monomial.Equals(Monomial.Multiply(m1, m2), expected));
    }

    @Test
    public void Multiply_DifferentDegree_NegativeCoef()
    {
        Monomial m1 = new Monomial(1, 2);
        Monomial m2 = new Monomial(-4, 3);

        Monomial expected = new Monomial(-4, 5);
        assertTrue(Monomial.Equals(Monomial.Multiply(m1, m2), expected));
    }

    @Test
    public void Multiply_SameDegree_Zero_CoefZero()
    {
        Monomial m1 = new Monomial(1, 2);
        Monomial m2 = new Monomial(0, 10);

        Monomial expected = new Monomial(0, 0);

        assertTrue(Monomial.Equals(Monomial.Multiply(m1, m2), expected));
    }

    @Test
    public void Multiply_SameDegree_Zero_CoefZero_Reversed()
    {
        Monomial m1 = new Monomial(1, 2);
        Monomial m2 = new Monomial(0, 10);

        Monomial expected = new Monomial(0, 0);

        assertTrue(Monomial.Equals(Monomial.Multiply(m2, m1), expected));
    }

    @Test
    public void Multiply_SameDegree_Zero_Both()
    {
        Monomial m1 = new Monomial(0, 2);
        Monomial m2 = new Monomial(0, 10);

        Monomial expected = new Monomial(0, 0);

        assertTrue(Monomial.Equals(Monomial.Multiply(m1, m2), expected));
    }

    @Test
    public void Div_Simple()
    {
        Monomial m1 = new Monomial(8, 2);
        Monomial m2 = new Monomial(2, 0);

        Monomial expected = new Monomial(4, 2);
        assertTrue(Monomial.Equals(Monomial.Divide(m1, m2), expected));
    }

    @Test
    public void Div_Simple_TrickDivision()
    {
        Monomial m1 = new Monomial(8, 2);
        Monomial m2 = new Monomial(3, 0);

        Monomial expected = new Monomial(8 / 3, 2);
        assertTrue(Monomial.Equals(Monomial.Divide(m1, m2), expected));
    }

    @Test
    public void Div_Simple_TrickDivision_Bis()
    {
        Monomial m1 = new Monomial(8, 2);
        Monomial m2 = new Monomial(11, 0);

        Monomial expected = new Monomial(8 / 11, 2);
        assertTrue(Monomial.Equals(Monomial.Divide(m1, m2), expected));
    }

    @Test
    public void Div_Simple_BothDegree()
    {
        Monomial m1 = new Monomial(8, 5);
        Monomial m2 = new Monomial(4, 5);

        Monomial expected = new Monomial(2, 0);
        assertTrue(Monomial.Equals(Monomial.Divide(m1, m2), expected));
    }

    @Test
    public void Div_Simple_BothDegree_Zero()
    {
        Monomial m1 = new Monomial(8, 0);
        Monomial m2 = new Monomial(4, 0);

        Monomial expected = new Monomial(2, 0);
        assertTrue(Monomial.Equals(Monomial.Divide(m1, m2), expected));
    }

    private static void Test_Divide_Monomial(Monomial m1, Monomial m2)
    {
        Monomial.Divide(m1, m2);
    }

    @Test
    public void Div_ZeroDivision_Left()
    {
        Monomial m1 = new Monomial(0, 2);
        Monomial m2 = new Monomial(4, 1);

        Monomial expected = new Monomial(0, 0);
        assertTrue(Monomial.Equals(Monomial.Divide(m1, m2), expected));
    }

    @Test(expected = ArithmeticException.class)
    public void Div_ZeroDivision_Right()
    {
        Monomial m1 = new Monomial(4, 2);
        Monomial m2 = new Monomial(0, 1);

        Test_Divide_Monomial(m1, m2);
    }

    @Test(expected = ArithmeticException.class)
    public void Div_ZeroDivision_Both()
    {
        Monomial m1 = new Monomial(0, 2);
        Monomial m2 = new Monomial(0, 1);

        Test_Divide_Monomial(m1, m2);
    }
}