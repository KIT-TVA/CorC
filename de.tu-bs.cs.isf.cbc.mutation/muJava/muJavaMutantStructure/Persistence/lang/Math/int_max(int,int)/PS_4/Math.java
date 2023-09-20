// This is a mutant program.
// Author : ysma

package java.lang;


/**
 * The class {@code Math} contains methods for performing basic
 * numeric operations such as the elementary exponential, logarithm,
 * square root, and trigonometric functions.
 *
 * <p>Unlike some of the numeric methods of class
 * {@code StrictMath}, all implementations of the equivalent
 * functions of class {@code Math} are not defined to return the
 * bit-for-bit same results.  This relaxation permits
 * better-performing implementations where strict reproducibility is
 * not required.
 *
 * <p>By default many of the {@code Math} methods simply call
 * the equivalent method in {@code StrictMath} for their
 * implementation.  Code generators are encouraged to use
 * platform-specific native libraries or microprocessor instructions,
 * where available, to provide higher-performance implementations of
 * {@code Math} methods.  Such higher-performance
 * implementations still must conform to the specification for
 * {@code Math}.
 *
 * <p>The quality of implementation specifications concern two
 * properties, accuracy of the returned result and monotonicity of the
 * method.  Accuracy of the floating-point {@code Math} methods
 * is measured in terms of <i>ulps</i>, units in the last place.  For
 * a given floating-point format, an ulp of a specific real number
 * value is the distance between the two floating-point values
 * bracketing that numerical value.  When discussing the accuracy of a
 * method as a whole rather than at a specific argument, the number of
 * ulps cited is for the worst-case error at any argument.  If a
 * method always has an error less than 0.5 ulps, the method always
 * returns the floating-point number nearest the exact result; such a
 * method is <i>correctly rounded</i>.  A correctly rounded method is
 * generally the best a floating-point approximation can be; however,
 * it is impractical for many floating-point methods to be correctly
 * rounded.  Instead, for the {@code Math} class, a larger error
 * bound of 1 or 2 ulps is allowed for certain methods.  Informally,
 * with a 1 ulp error bound, when the exact result is a representable
 * number, the exact result should be returned as the computed result;
 * otherwise, either of the two floating-point values which bracket
 * the exact result may be returned.  For exact results large in
 * magnitude, one of the endpoints of the bracket may be infinite.
 * Besides accuracy at individual arguments, maintaining proper
 * relations between the method at different arguments is also
 * important.  Therefore, most methods with more than 0.5 ulp errors
 * are required to be <i>semi-monotonic</i>: whenever the mathematical
 * function is non-decreasing, so is the floating-point approximation,
 * likewise, whenever the mathematical function is non-increasing, so
 * is the floating-point approximation.  Not all approximations that
 * have 1 ulp accuracy will automatically meet the monotonicity
 * requirements.
 *
 * @author  unascribed
 * @author  Joseph D. Darcy
 * @since   JDK1.0
 */
public final class Math
{

/*@ public normal_behavior
	  @ ensures (a <  0) ==> (\result == -a);
	  @ ensures (a == 0) ==> (\result == a);
	  @ ensures (a == Integer.MIN_VALUE) ==> (\result == Integer.MIN_VALUE);
	  @*/
    public static /*@pure@*/ int abs(int a) 
    {
        return a < 0 ? -a : a;
    }

/*@ public normal_behavior
	  @ ensures a == b ==> \result == a;
	  @ ensures b >= a ==> \result == b;
	  @*/
    public static  int max( int a, int b )
    {
        return a >= b ? a : b;
    }

/*@ public normal_behavior
	  @ ensures a <= b ==> \result == a;
	  @ ensures b <= a ==> \result == b;
	  @*/
    public static  int min( int a, int b )
    {
        return a <= b ? a : b;
    }

}
