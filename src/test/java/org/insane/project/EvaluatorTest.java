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

    @Test(expected = IllegalArgumentException.class)
    public void ErrorManagement_WrongVariable()
    {
        Computation.Compute("a");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ErrorManagement_GoodAndWrongVariable()
    {
        Computation.Compute(" x  a   ");
    }

    @Test
    public void NumberOnly_Addition()
    {
        Polynomial p = Computation.Compute("1+3");

        Polynomial expected = new Polynomial(new Monomial(1 + 3));

        assertTrue(PolyEquals(p, expected));
    }

    @Test
    public void NumberOnly_Add_WithWhitespace_TwoMembers()
    {
        Polynomial p = Computation.Compute(" 1 + 3 ");

        Polynomial expected = new Polynomial(new Monomial(1 + 3));

        assertTrue(PolyEquals(p, expected));
    }

    @Test
    public void NumberOnly_Add_WithWhitespace_ALotOfMembers()
    {
        Polynomial p = Computation.Compute(" 1 + 3+ 1 + 3 + 5");

        Polynomial expected = new Polynomial(new Monomial(1 + 3 + 1 + 3 + 5));

        assertTrue(PolyEquals(p, expected));
    }

    @Test
    public void NumberOnly_Minus()
    {
        Polynomial p = Computation.Compute("1-3");

        Polynomial expected = new Polynomial(new Monomial(1 - 3));

        assertTrue(PolyEquals(p, expected));
    }

    @Test
    public void NumberOnly_Minus_WithWhiteSpace_TwoMembers()
    {
        Polynomial p = Computation.Compute(" 1 - 3 ");

        Polynomial expected = new Polynomial(new Monomial(1 - 3));

        assertTrue(PolyEquals(p, expected));
    }

    @Test
    public void NumberOnly_Mix_AS_1()
    {
        Polynomial p = Computation.Compute("1-3+2+3");

        Polynomial expected = new Polynomial(new Monomial(1 - 3 + 2 + 3));

        assertTrue(PolyEquals(p, expected));
    }

    @Test
    public void NumberOnly_Mix_AS_2()
    {
        Polynomial p = Computation.Compute(" 1 - 3+2");

        Polynomial expected = new Polynomial(new Monomial(1 - 3 + 2));

        assertTrue(PolyEquals(p, expected));
    }

    @Test
    public void WithVariable_SimpleAddition()
    {
        Polynomial p = Computation.Compute("1+ x");

        Polynomial expected = new Polynomial(new Monomial(1));
        expected = Polynomial.Add(expected, new Polynomial(new Monomial(1, 1)));

        assertTrue(PolyEquals(p, expected));
    }

    @Test
    public void WithVariable_Mix_Result_Mix()
    {
        Polynomial p = Computation.Compute("1+ x + 4-x-x");

        Polynomial expected = new Polynomial(new Monomial(5));
        expected = Polynomial.Add(expected, new Polynomial(new Monomial(-1, 1)));
        
        assertTrue(PolyEquals(p, expected));
    }

    @Test
    public void WithVariable_Mix_Result_OnlyNumber()
    {
        Polynomial p = Computation.Compute("x + 1 -x");

        Polynomial expected = new Polynomial(new Monomial(1));

        assertTrue(PolyEquals(p, expected));
    }

    @Test
    public void WithVariable_Mix_Result_OnlyVariable()
    {
        Polynomial p = Computation.Compute("2 + x -2 + x");

        Polynomial expected = new Polynomial(new Monomial(2, 1));

        assertTrue(PolyEquals(p, expected));
    }

    @Test(expected = IllegalArgumentException.class)
    public void ErrorManagement_DoubleNumber()
    {
        Computation.Compute("1 4+ x");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ErrorManagement_DoubleVariable()
    {
        Computation.Compute("1+ x x");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ErrorManagement_TrailingOperator_Plus()
    {
        Computation.Compute("1 - 3 +");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ErrorManagement_TrailingOperator_Minus()
    {
        Computation.Compute("1 - 3 -");
    }

    @Test
    public void NumberOnly_Mult_Lot()
    {
        Polynomial p = Computation.Compute("1*3  *2 *3");

        Polynomial expected = new Polynomial(new Monomial(1 * 3 * 2 * 3));

        assertTrue(PolyEquals(p, expected));
    }

    @Test
    public void NumberOnly_Mult_Zero()
    {
        Polynomial p = Computation.Compute("1*0");

        Polynomial expected = new Polynomial(new Monomial(0));

        assertTrue(PolyEquals(p, expected));
    }

    @Test
    public void NumberOnly_Mult_Zero_Long()
    {
        Polynomial p = Computation.Compute(" 1*0 *12345678 * 42");

        Polynomial expected = new Polynomial(new Monomial(0));

        assertTrue(PolyEquals(p, expected));
    }

    @Test
    public void NumberOnly_MultDiv_NumberOnly_DivOnly()
    {
        Polynomial p = Computation.Compute("8/2");

        Polynomial expected = new Polynomial(new Monomial(4));

        assertTrue(PolyEquals(p, expected));
    }

    @Test
    public void NumberOnly_MultDiv_NumberOnly_Lot()
    {
        Polynomial p = Computation.Compute("8/2*3");

        Polynomial expected = new Polynomial(new Monomial(12));

        assertTrue(PolyEquals(p, expected));
    }

    @Test
    public void NumberOnly_MultDiv_Zero()
    {
        Polynomial p = Computation.Compute("46*5/ 46  / 5");

        Polynomial expected = new Polynomial(new Monomial(1));

        assertTrue(PolyEquals(p, expected));
    }

    @Test
    public void NumberOnly_Mix_NotTrick()
    {
        Polynomial p = Computation.Compute("2*3 + 7");

        Polynomial expected = new Polynomial(new Monomial(2 * 3 + 7));

        assertTrue(PolyEquals(p, expected));
    }

    @Test
    public void NumberOnly_Mix_PriorityTrick()
    {
        Polynomial p = Computation.Compute("7 + 2*3");

        Polynomial expected = new Polynomial(new Monomial(7 + 2 * 3));

        assertTrue(PolyEquals(p, expected));
    }

    @Test
    public void NumberOnly_Mix_PriorityTrick_Mix()
    {
        Polynomial p = Computation.Compute("7 + 2*3 -2/2  + 14-2*3");

        Polynomial expected = new Polynomial(new Monomial(7 + 2 * 3 - 2 / 2 + 14 - 2 * 3));

        assertTrue(PolyEquals(p, expected));
    }

    @Test
    public void WithVariable_SimpleMult_WithNumber()
    {
        Polynomial p = Computation.Compute("2*x");

        Polynomial expected = new Polynomial(new Monomial(2, 1));

        assertTrue(PolyEquals(p, expected));
    }

    @Test
    public void WithVariable_SimpleMult_WithVariable()
    {
        Polynomial p = Computation.Compute("x*x*x");

        Polynomial expected = new Polynomial(new Monomial(1, 3));

        assertTrue(PolyEquals(p, expected));
    }

    @Test
    public void WithVariable_Hard_NoDiv()
    {
        Polynomial p = Computation.Compute("2*x + 7 - x *x");

        Polynomial expected = new Polynomial(new Monomial(7));
        expected = Polynomial.Add(expected, new Polynomial(new Monomial(2, 1)));
        expected = Polynomial.Add(expected, new Polynomial(new Monomial(-1, 2)));

        assertTrue(PolyEquals(p, expected));
    }

    @Test(expected = ArithmeticException.class)
    public void ErrorManagement_DivByZero()
    {
        Computation.Compute("3- 4 / 0");
    }

    @Test(expected = ArithmeticException.class)
    public void ErrorManagement_DivByZero_Variable()
    {
        Computation.Compute("x / 0");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ErrorManagement_MultipleMult()
    {
        Computation.Compute("4*x**2");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ErrorManagement_MultipleMult_Far()
    {
        Computation.Compute("x *   * 2");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ErrorManagement_MultipleDiv()
    {
        Computation.Compute("4*x//2");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ErrorManagement_MultipleDiv_Far()
    {
        Computation.Compute("x /   / 2");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ErrorManagement_LeadingOperator_Mult()
    {
        Computation.Compute(" * 1 + 2 + 3 ");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ErrorManagement_LeadingOperator_Div()
    {
        Computation.Compute(" / 1 + 2 + 3 ");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ErrorManagement_TrailingOperator_Mult()
    {
        Computation.Compute("1 + 2 + 3 * ");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ErrorManagement_TrailingOperator_Div()
    {
        Computation.Compute("1 + 2 + 3 / ");
    }

    @Test
    public void Implicit_Multiplication_VarAfterNumber()
    {
        Polynomial p = Computation.Compute("2x");

        Polynomial expected = new Polynomial(new Monomial(2, 1));

        assertTrue(PolyEquals(p, expected));
    }

    @Test
    public void Implicit_Multiplication_NumberAfterVar()
    {
        Polynomial p = Computation.Compute("x2");

        Polynomial expected = new Polynomial(new Monomial(2, 1));

        assertTrue(PolyEquals(p, expected));
    }

    @Test
    public void Implicit_Multiplication_Hard()
    {
        Polynomial p = Computation.Compute("x3x4x");

        Polynomial expected = new Polynomial(new Monomial(12, 3));

        assertTrue(PolyEquals(p, expected));
    }

    @Test
    public void Unary_NumberOnly_OneNumber_Minus_Simple()
    {
        Polynomial p = Computation.Compute("-1");

        Polynomial expected = new Polynomial(new Monomial(-1));

        assertTrue(PolyEquals(p, expected));
    }

    @Test
    public void Unary_NumberOnly_OneNumber_Plus_Simple()
    {
        Polynomial p = Computation.Compute("+1");

        Polynomial expected = new Polynomial(new Monomial(1));

        assertTrue(PolyEquals(p, expected));
    }

    @Test
    public void Unary_NumberOnly_OneNumber_Minus_Multiple_Even()
    {
        Polynomial p = Computation.Compute("--------3");

        Polynomial expected = new Polynomial(new Monomial(3));

        assertTrue(PolyEquals(p, expected));
    }

    @Test
    public void Unary_NumberOnly_OneNumber_Minus_Multiple_Odd()
    {
        Polynomial p = Computation.Compute("-------3");

        Polynomial expected = new Polynomial(new Monomial(-3));

        assertTrue(PolyEquals(p, expected));
    }

    @Test
    public void Unary_NumberOnly_OneNumber_Plus_Multiple()
    {
        Polynomial p = Computation.Compute("+++++3");

        Polynomial expected = new Polynomial(new Monomial(3));

        assertTrue(PolyEquals(p, expected));
    }

    @Test
    public void Unary_NumberOnly_OneNumber_Mix_Simple()
    {
        Polynomial p = Computation.Compute("+-3");

        Polynomial expected = new Polynomial(new Monomial(-3));

        assertTrue(PolyEquals(p, expected));
    }

    @Test
    public void Unary_NumberOnly_OneNumber_Mix_Simple_Bis()
    {
        Polynomial p = Computation.Compute("-+3");

        Polynomial expected = new Polynomial(new Monomial(-3));

        assertTrue(PolyEquals(p, expected));
    }

    // @Test
    // public void Unary_NumberOnly_OneNumber_Minus_Parenthesis()
    // {
    //     Polynomial p = Computation.Compute("-(3)");

    //     Polynomial expected = new Polynomial(new Monomial(-3));

    //     assertTrue(PolyEquals(p, expected));
    // }

    @Test
    public void Unary_NumberOnly_OneNumber_Plus_Parenthesis()
    {
        Polynomial p = Computation.Compute("+(3)");

        Polynomial expected = new Polynomial(new Monomial(3));

        assertTrue(PolyEquals(p, expected));
    }
}