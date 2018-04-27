package comp34120.ex2.math.calculus;


import math.functions.IFunction;


/**
 * A static library for integral calculations of functions.
 *
 * @author Brian Norman
 * @version 0.1 beta
 */
public final class Integration {

   /**
    * Don't let anyone instantiate this class.
    */
   private Integration() {
   }

   /**
    * Returns the lower summation of the function over the specified range with the specified number of divisions. Taken
    * from Numerical Mathematics and Computing (6th Edition) by Ward Cheney and David Kincaid, page 185.
    *
    * @param f the function to summate.
    * @param a the starting point of the range.
    * @param b the ending point of the range.
    * @param n the number of divisions.
    * @return the lower sum of the function.
    */
   public static double sumLower(IFunction f, double a, double b, int n) {
      double h = (b - a) / n;
      double sum = 0.0;
      for (int i = n; i >= 1; i--) {
         sum += f.eval(a + i * h);
      }
      return sum * h;
   }

   /**
    * Returns the upper summation of the function over the specified range with the specified number of divisions. Taken
    * from Numerical Mathematics and Computing (6th Edition) by Ward Cheney and David Kincaid, page 185.
    *
    * @param f the function to summate.
    * @param a the starting point of the range.
    * @param b the ending point of the range.
    * @param n the number of divisions.
    * @return the upper sum of the function.
    */
   public static double sumUpper(IFunction f, double a, double b, int n) {
      return sumLower(f, a, b, n) + (b - a) * (f.eval(a) - f.eval(b)) / n;
   }

   /**
    * Returns the trapezoid summation of the function over the specified range with the specified number of divisions.
    * Taken from Numerical Mathematics and Computing (6th Edition) by Ward Cheney and David Kincaid, page 191.
    *
    * @param f the function to summate.
    * @param a the starting point of the range.
    * @param b the ending point of the range.
    * @param n the number of division.
    * @return the trapezoid sum of the function.
    */
   public static double trapizoid(IFunction f, double a, double b, int n) {
      double h = (b - a) / n;
      double sum = 1.0 / 2.0 * (f.eval(a) + f.eval(b));
      for (int i = 1; i < n; i++) {
         sum += f.eval(a + i * h);
      }
      return sum * h;
   }

   /**
    * Returns the Romberg extrapolated summation of the function over the specified range with the specified number of
    * divisions. Taken from Numerical Mathematics and Computing (6th Edition) by Ward Cheney and David Kincaid, page
    * 206.
    *
    * @param f the function to summate.
    * @param a the starting point of the range.
    * @param b the ending point of the range.
    * @param n the number of division.
    * @return the Romberg sum of the function.
    */
   public static double romberg(IFunction f, double a, double b, int n) {
      double[] r = new double[n + 1];

      double h = b - a;
      r[0] = (h / 2.0) * (f.eval(a) + f.eval(b));
      for (int i = 1; i <= n; i++) {
         h = h / 2.0;
         double sum = 0.0;
         for (int k = 1; k <= Math.pow(2, i) - 1; k += 2) {
            sum += f.eval(a + k * h);
         }
         r[i] = 1.0 / 2.0 * r[i - 1] + sum * h;
      }
      for (int i = 1; i <= n; i++) {
         for (int j = n; j >= i; j--) {
            r[j] = r[j] + (r[j] - r[j - 1]) / (Math.pow(4.0, i) - 1.0);
         }
      }

      return r[n];
   }

   /**
    * Returns the Simpson summation of the function over the specified range to the specified precision or max level of
    * division. Taken from Numerical Mathematics and Computing (6th Edition) by Ward Cheney and David Kincaid, page
    * 224.
    *
    * @param f         the function to summate.
    * @param a         the starting point of the range.
    * @param b         the ending point of the range.
    * @param epsilon   the desired precision.
    * @param level_max the max level of division.
    * @return the Simpson sum of the function.
    */
   public static double simpson(IFunction f, double a, double b, double epsilon, int level_max) {
      return simpson(f, a, b, epsilon, 0, level_max);
   }

   /**
    * Returns the Simpson summation of the function over the specified range to the specified precision or max level of
    * division. This function is designed to be recursive and has a level for each call. Taken from Numerical
    * Mathematics and Computing (6th Edition) by Ward Cheney and David Kincaid, page 224.
    *
    * @param f         the function to summate.
    * @param a         the starting point of the range.
    * @param b         the ending point of the range.
    * @param epsilon   the desired precision.
    * @param level     the current level.
    * @param level_max the max level of division.
    * @return the Simpson sum of the function.
    */
   private static double simpson(IFunction f, double a, double b, double epsilon, int level, int level_max) {
      level++;

      double h = b - a;
      double c = (a + b) / 2.0;
      double d = (a + c) / 2.0;
      double e = (c + b) / 2.0;

      double fa = f.eval(a);
      double fb = f.eval(b);
      double fc = f.eval(c);

      double one_simp = h * (fa + 4.0 * fc + fb) / 6.0;

      double two_simp = h * (fa + 4.0 * f.eval(d) + 2.0 * fc + 4.0 * f.eval(e) + fb) / 12.0;

      if (level >= level_max) {
         // System.out.println("Maximum level reached");
         return two_simp;
      }
      else if (Math.abs(two_simp - one_simp) < 15.0 * epsilon) {
         return two_simp + (two_simp - one_simp) / 15.0;
      }
      else {
         return simpson(f, a, c, epsilon / 2.0, level, level_max) + simpson(f, c, b, epsilon / 2.0, level, level_max);
      }
   }

}
