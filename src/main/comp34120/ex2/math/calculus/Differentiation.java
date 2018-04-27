package comp34120.ex2.math.calculus;


import math.functions.IFunction;


/**
 * A static library for derivative calculations of functions.
 *
 * @author Brian Norman
 * @version 0.1 beta
 */
public final class Differentiation {

   /**
    * Don't let anyone instantiate this class.
    */
   private Differentiation() {
   }

   /**
    * Returns a small value for h that is used for computing the derivative.
    *
    * @param x the point at which the h value will be used.
    * @return a small value for h.
    */
   public static double hValue(double x) {
      return Math.max(Math.abs(x / 1000.0), 0.0001);
   }

   /**
    * Returns the derivative of the specified function at the specified point. Taken from Numerical Mathematics and
    * Computing (6th Edition) by Ward Cheney and David Kincaid, pages 172-173.
    *
    * @param f the function to derive.
    * @param x the point of derivation.
    * @return the derivative of the function.
    */
   public static double derivative(IFunction f, double x) {
      double h = hValue(x);
      double xph = f.eval(x + h);
      double xmh = f.eval(x - h);
      return 1.0 / (2.0 * h) * (xph - xmh) -
             1.0 / (12.0 * h) * (f.eval(x + 2.0 * h) - 2.0 * xph + 2.0 * xmh - f.eval(x - 2.0 * h));
   }

   /**
    * Returns the functional representation of the derivative of the specified function. Every time the returned
    * function is evaluated at a point the derivative of the specified function is calculated.
    *
    * @param f the function to derive.
    * @return the derivative of the function.
    */
   public static IFunction derivative(final IFunction f) {
      return x -> derivative(f, x);
   }

   /**
    * Returns the Richardson Extrapolation of the derivative of the specified function at the specified point. The
    * default number of iterations for the Richardson Extrapolation is 10.
    *
    * @param f the function to derive.
    * @param x the point of derivation.
    * @return the derivative of the specified function.
    */
   public static double extrapDerivative(IFunction f, double x) {
      return extrapDerivative(f, x, 10);
   }

   /**
    * Returns the functional representation of the Richardson Extrapolation of the derivative of the specified function.
    * Every time the returned function is evaluated at a point the derivative of the specified function is calculated.
    * The default number of iterations for the Richardson Extrapolation is 10.
    *
    * @param f the function to derive.
    * @return the derivative of the function.
    */
   public static IFunction extrapDerivative(final IFunction f) {
      return x -> extrapDerivative(f, x);
   }

   /**
    * Returns the Richardson Extrapolation of the derivative of the specified function at the specified point. Computes
    * the specified number of iterations for the Richardson Extrapolation. Taken from Numerical Mathematics and
    * Computing (6th Edition) by Ward Cheney and David Kincaid, page 170.
    *
    * @param f the function to derive.
    * @param x the point of derivation.
    * @param n the number of Richardson Extrapolation iterations.
    * @return the derivative of the specified function.
    */
   public static double extrapDerivative(IFunction f, double x, int n) {
      double[] d = new double[n + 1];
      double h = hValue(x);

      for (int i = 0; i <= n; i++) {
         d[i] = (f.eval(x + h) - f.eval(x - h)) / (2.0 * h);
         h /= 2.0;
      }
      for (int i = 1; i <= n; i++) {
         for (int j = n; j >= i; j--) {
            d[j] = d[j] + (d[j] - d[j - 1]) / (Math.pow(4.0, i) - 1.0);
         }
      }
      return d[n];
   }

   /**
    * Returns the functional representation of the Richardson Extrapolation of the derivative of the specified function.
    * Every time the returned function is evaluated at a point the derivative of the specified function is calculated.
    *
    * @param f the function to derive.
    * @return the derivative of the function.
    */
   public static IFunction extrapDerivative(final IFunction f, final int n) {
      return x -> extrapDerivative(f, x, n);
   }

   /**
    * Returns the second derivative of the specified function at the specified point. Taken from Numerical Mathematics
    * and Computing (6th Edition) by Ward Cheney and David Kincaid, page 173.
    *
    * @param f the function to derive.
    * @param x the point of derivation.
    * @return the second derivative of the function.
    */
   public static double secondDerivative(IFunction f, double x) {
      double h = hValue(x);
      return 1.0 / (h * h) * (f.eval(x + h) - 2.0 * f.eval(x) + f.eval(x - h));
   }

   /**
    * Returns the functional representation of the second derivative of the specified function. Every time the returned
    * function is evaluated at a point the derivative of the specified function is calculated.
    *
    * @param f the function to derive.
    * @return the second derivative of the function.
    */
   public static IFunction secondDerivative(final IFunction f) {
      return x -> secondDerivative(f, x);
   }

}
