import javax.security.auth.login.Configuration;
import java.lang.*;

public class Mathey {
    /* Write your own version of the Math.max method
     * Your method should be called max
     * This method should take **two integers** and return the larger integer
     * Ex. max(1, 2) => 2

     * You will need to practice writing the method signature below!
     * public static ...
     */
    // YOUR CODE HERE, METHOD HEADER ALSO REQUIRED
    public static int max(int x, int y) {
        if (x > y) {
            return x;
        }
        return y;
    }


    /* Write another method called max that takes **two doubles**
     * Ex. max(1.2, 4.0) => 4.0
     */
    // YOUR CODE HERE, METHOD HEADER ALSO REQUIRED
    public static double max(double x, double y) {
        if (x > y) {
            return x;
        }
        return y;
    }


    /* Write another method called max that takes **three integers**
     * Ex. max(1, 4, 2) => 4
     */
    // YOUR CODE HERE, METHOD HEADER ALSO REQUIRED
    public static int max(int x, int y, int z) {
        if ((x > y) && (x > z)) {
            return z;
        }
        if ((y > z) && (y > x)) {
            return y;
        }
        return z;
    }


    /* Write another method called max that takes **four doubles**
     * Ex. max(1.0, 4.25, 1.3, 2.1) => 4.25
     */
    // YOUR CODE HERE, METHOD HEADER ALSO REQUIRED
    public static double max(double a, double b, double c, double d) {
        if (max(a, b) > max(c, d)) {
            return max(a, b);
        }
        return max(c, d);
    }



    /* Write a method that takes **two integers** and generates a random
     * integer between the first integer and the second integer, inclusive
     * you may assume that the first integer is smaller than the second
     * You may use Math.random() here, but you can't use any other Math methods!
     * Ex. randomInteger(1, 4) => 3
     *     randomInteger(1, 4) => 2
     *     randomInteger(1, 4) => 4
     */
    // YOUR CODE HERE, METHOD HEADER ALSO REQUIRED

    public static int randomInteger(int a, int b) {
        return (int) (Math.random() * a - b + 1) + b;
    }


    /* Write a method that takes **one integer** and generates a random
     * integer between 0 and the integer, inclusive
     * you may assume that the integer is greater than 0
     * Ex. randomInteger(5) => 3
     *     randomInteger(5) => 5
     *     randomInteger(5) => 0
     */
    // YOUR CODE HERE, METHOD HEADER ALSO REQUIRED
    public static int randomInteger(int a) {
        return (int) (Math.random() * a + 1);
    }


    // YOU MAY WORK ON THE FOLLOWING METHODS IF YOU FINISH EARLY

    /* Write a method that takes **two integers** and calculates the exponent
     * that you get by raising the first integer to the second integer
     * you may assume that both integers are positive
     * Ex. pow(2, 5) => 32
     *     pow(3, 4) => 81
     */
    // YOUR CODE HERE, METHOD HEADER ALSO REQUIRED
    public static double pow(int x, int y) {
        // Special cases first.
        if (y == 0)
            return 1;
        if (y == 1)
            return x;
        if (y == -1)
            return 1 / x;
        if (x != x || y != y)
            return Double.NaN;

        // When x < 0, yisint tells if y is not an integer (0), even(1),
        // or odd (2).
        int yisint = 0;
        if (x < 0 && floor(y) == y)
            yisint = (y % 2 == 0) ? 2 : 1;
        double ax = abs(x);
        double ay = abs(y);

        // More special cases, of y.
        if (ay == Double.POSITIVE_INFINITY) {
            if (ax == 1)
                return Double.NaN;
            if (ax > 1)
                return y > 0 ? y : 0;
            return y < 0 ? -y : 0;
        }
        if (y == 2)
            return x * x;
        if (y == 0.5)
            return sqrt(x);

        // More special cases, of x.
        if (x == 0 || ax == Double.POSITIVE_INFINITY || ax == 1) {
            if (y < 0)
                ax = 1 / ax;
            if (x < 0) {
                if (x == -1 && yisint == 0)
                    ax = Double.NaN;
                else if (yisint == 1)
                    ax = -ax;
            }
            return ax;
        }
        if (x < 0 && yisint == 0)
            return Double.NaN;

        // Now we can start!
        double t;
        double t1;
        double t2;
        double u;
        double v;
        double w;
        if (ay > TWO_31) {
            if (ay > TWO_64) // Automatic over/underflow.
                return ((ax < 1) ? y < 0 : y > 0) ? Double.POSITIVE_INFINITY : 0;
            // Over/underflow if x is not close to one.
            if (ax < 0.9999995231628418)
                return y < 0 ? Double.POSITIVE_INFINITY : 0;
            if (ax >= 1.0000009536743164)
                return y > 0 ? Double.POSITIVE_INFINITY : 0;
            // Now |1-x| is <= 2**-20, sufficient to compute
            // log(x) by x-x^2/2+x^3/3-x^4/4.
            t = x - 1;
            w = t * t * (0.5 - t * (1 / 3.0 - t * 0.25));
            u = INV_LN2_H * t;
            v = t * INV_LN2_L - w * INV_LN2;
            t1 = (float) (u + v);
            t2 = v - (t1 - u);
        } else {
            long bits = Double.doubleToLongBits(ax);
            int exp = (int) (bits >> 52);
            if (exp == 0) // Subnormal x.
            {
                ax *= TWO_54;
                bits = Double.doubleToLongBits(ax);
                exp = (int) (bits >> 52) - 54;
            }
            exp -= 1023; // Unbias exponent.
            ax = Double.longBitsToDouble((bits & 0x000fffffffffffffL)
                    | 0x3ff0000000000000L);
            boolean k;
            if (ax < SQRT_1_5)  // |x|<sqrt(3/2).
                k = false;
            else if (ax < SQRT_3) // |x|<sqrt(3).
                k = true;
            else {
                k = false;
                ax *= 0.5;
                exp++;
            }

            // Compute s = s_h+s_l = (x-1)/(x+1) or (x-1.5)/(x+1.5).
            u = ax - (k ? 1.5 : 1);
            v = 1 / (ax + (k ? 1.5 : 1));
            double s = u * v;
            double s_h = (float) s;
            double t_h = (float) (ax + (k ? 1.5 : 1));
            double t_l = ax - (t_h - (k ? 1.5 : 1));
            double s_l = v * ((u - s_h * t_h) - s_h * t_l);
            // Compute log(ax).
            double s2 = s * s;
            double r = s_l * (s_h + s) + s2 * s2
                    * (L1 + s2 * (L2 + s2 * (L3 + s2 * (L4 + s2 * (L5 + s2 * L6)))));
            s2 = s_h * s_h;
            t_h = (float) (3.0 + s2 + r);
            t_l = r - (t_h - 3.0 - s2);
            // u+v = s*(1+...).
            u = s_h * t_h;
            v = s_l * t_h + t_l * s;
            // 2/(3log2)*(s+...).
            double p_h = (float) (u + v);
            double p_l = v - (p_h - u);
            double z_h = CP_H * p_h;
            double z_l = CP_L * p_h + p_l * CP + (k ? DP_L : 0);
            // log2(ax) = (s+..)*2/(3*log2) = exp + dp_h + z_h + z_l.
            t = exp;
            t1 = (float) (z_h + z_l + (k ? DP_H : 0) + t);
            t2 = z_l - (t1 - t - (k ? DP_H : 0) - z_h);
        }

        // Split up y into y1+y2 and compute (y1+y2)*(t1+t2).
        boolean negative = x < 0 && yisint == 1;
        double y1 = (float) y;
        double p_l = (y - y1) * t1 + y * t2;
        double p_h = y1 * t1;
        double z = p_l + p_h;
        if (z >= 1024) // Detect overflow.
        {
            if (z > 1024 || p_l + OVT > z - p_h)
                return negative ? Double.NEGATIVE_INFINITY
                        : Double.POSITIVE_INFINITY;
        } else if (z <= -1075) // Detect underflow.
        {
            if (z < -1075 || p_l <= z - p_h)
                return negative ? -0.0 : 0;
        }

        // Compute 2**(p_h+p_l).
        int n = Math.round((float) z);
        p_h -= n;
        t = (float) (p_l + p_h);
        u = t * LN2_H;
        v = (p_l - (t - p_h)) * LN2 + t * LN2_L;
        z = u + v;
        w = v - (z - u);
        t = z * z;
        t1 = z - t * (P1 + t * (P2 + t * (P3 + t * (P4 + t * P5))));
        double r = (z * t1) / (t1 - 2) - (w + z * w);
        z = scale(1 - (r - z), n);
        return negative ? -z : z;
    }


    /* Write a method that takes **one integer** and returns the
     * absolute value of that integer
     * Ex. abs(2) => 2
     *     abs(-2) => 2
     */
    // YOUR CODE HERE, METHOD HEADER ALSO REQUIRED
    public static int abs(int x) {
        if (x >= 0) {
            return x;
        }
        return x * -1;
    }


    /* Write a method that takes **one double** and returns the
     * integer value that you get by rounding that double
     * You may assume that the integer is positive
     * Ex. round(2.4) => 2
     *     round(2.5) => 2
     */
    // YOUR CODE HERE, METHOD HEADER ALSO REQUIRED
    public static double round(double x) {
        return (int) (x + .5);
    }


    /* Write a method that takes **one double** and returns the
     * floor of that value
     * The floor is defined as the **largest** integer that is **less than**
     * or equal to some value
     * You may assume that the integer is positive
     * Ex. floor(2.4) => 2
     *     floor(2.999999999999) => 2
     */
    // YOUR CODE HERE, METHOD HEADER ALSO REQUIRED
    public static int floor(double x) {
        return (int) x;
    }


    /* Write a method that takes **one double** and returns the
     * ceiling of that value
     * The ceiling is defined as the **smallest** integer that is **greater than**
     * or equal to some value
     * You may assume that the integer is positive
     * Ex. ceil(2.99999) => 3
     *     ceil(3.01) => 4
     */
    // YOUR CODE HERE, METHOD HEADER ALSO REQUIRED
    public static int ceil(double x) {
        return ((int) x) + 1;
    }


    /* Calculates the square root iteratively, using the Babylonian method
     * This method has been provided for you
     */
    public static double sqrt(double x) {
        double x1 = 5;
        double prev = 0;

        while (Math.abs(prev - x1) > 0.0001) {
            prev = x1;
            x1 = (x1 + x / x1) / 2;
        }

        return x1;
    }

    private static double scale(double x, int n)
    {

        if (x == 0 || x == Double.NEGATIVE_INFINITY
                || ! (x < Double.POSITIVE_INFINITY) || n == 0)
            return x;
        long bits = Double.doubleToLongBits(x);
        int exp = (int) (bits >> 52) & 0x7ff;
        if (exp == 0) // Subnormal x.
        {
            x *= TWO_54;
            exp = ((int) (Double.doubleToLongBits(x) >> 52) & 0x7ff) - 54;
        }
        exp += n;
        if (exp > 0x7fe) // Overflow.
            return Double.POSITIVE_INFINITY * x;
        if (exp > 0) // Normal.
            return Double.longBitsToDouble((bits & 0x800fffffffffffffL)
                    | ((long) exp << 52));
        if (exp <= -54)
            return 0 * x; // Underflow.
        exp += 54; // Subnormal result.
        x = Double.longBitsToDouble((bits & 0x800fffffffffffffL)
                | ((long) exp << 52));
        return x * (1 / TWO_54);
    }

    private static final double
            PI_L = 1.2246467991473532e-16, // Long bits 0x3ca1a62633145c07L.
            PIO2_1 = 1.5707963267341256, // Long bits 0x3ff921fb54400000L.
            PIO2_1L = 6.077100506506192e-11, // Long bits 0x3dd0b4611a626331L.
            PIO2_2 = 6.077100506303966e-11, // Long bits 0x3dd0b4611a600000L.
            PIO2_2L = 2.0222662487959506e-21, // Long bits 0x3ba3198a2e037073L.
            PIO2_3 = 2.0222662487111665e-21, // Long bits 0x3ba3198a2e000000L.
            PIO2_3L = 8.4784276603689e-32; // Long bits 0x397b839a252049c1L.

    /**
     * Natural log and square root constants, for calculation of
     */
    private static final double
            SQRT_1_5 = 1.224744871391589, // Long bits 0x3ff3988e1409212eL.
            SQRT_2 = 1.4142135623730951, // Long bits 0x3ff6a09e667f3bcdL.
            SQRT_3 = 1.7320508075688772, // Long bits 0x3ffbb67ae8584caaL.
            EXP_LIMIT_H = 709.782712893384, // Long bits 0x40862e42fefa39efL.
            EXP_LIMIT_L = -745.1332191019411, // Long bits 0xc0874910d52d3051L.
            CP = 0.9617966939259756, // Long bits 0x3feec709dc3a03fdL.
            CP_H = 0.9617967009544373, // Long bits 0x3feec709e0000000L.
            CP_L = -7.028461650952758e-9, // Long bits 0xbe3e2fe0145b01f5L.
            LN2 = 0.6931471805599453, // Long bits 0x3fe62e42fefa39efL.
            LN2_H = 0.6931471803691238, // Long bits 0x3fe62e42fee00000L.
            LN2_L = 1.9082149292705877e-10, // Long bits 0x3dea39ef35793c76L.
            INV_LN2 = 1.4426950408889634, // Long bits 0x3ff71547652b82feL.
            INV_LN2_H = 1.4426950216293335, // Long bits 0x3ff7154760000000L.
            INV_LN2_L = 1.9259629911266175e-8; // Long bits 0x3e54ae0bf85ddf44L.

    /**
     */
    private static final double
            LG1 = 0.6666666666666735, // Long bits 0x3fe5555555555593L.
            LG2 = 0.3999999999940942, // Long bits 0x3fd999999997fa04L.
            LG3 = 0.2857142874366239, // Long bits 0x3fd2492494229359L.
            LG4 = 0.22222198432149784, // Long bits 0x3fcc71c51d8e78afL.
            LG5 = 0.1818357216161805, // Long bits 0x3fc7466496cb03deL.
            LG6 = 0.15313837699209373, // Long bits 0x3fc39a09d078c69fL.
            LG7 = 0.14798198605116586; // Long bits 0x3fc2f112df3e5244L.

    /**
     */
    private static final double
            L1 = 0.5999999999999946, // Long bits 0x3fe3333333333303L.
            L2 = 0.4285714285785502, // Long bits 0x3fdb6db6db6fabffL.
            L3 = 0.33333332981837743, // Long bits 0x3fd55555518f264dL.
            L4 = 0.272728123808534, // Long bits 0x3fd17460a91d4101L.
            L5 = 0.23066074577556175, // Long bits 0x3fcd864a93c9db65L.
            L6 = 0.20697501780033842, // Long bits 0x3fca7e284a454eefL.
            P1 = 0.16666666666666602, // Long bits 0x3fc555555555553eL.
            P2 = -2.7777777777015593e-3, // Long bits 0xbf66c16c16bebd93L.
            P3 = 6.613756321437934e-5, // Long bits 0x3f11566aaf25de2cL.
            P4 = -1.6533902205465252e-6, // Long bits 0xbebbbd41c5d26bf1L.
            P5 = 4.1381367970572385e-8, // Long bits 0x3e66376972bea4d0L.
            DP_H = 0.5849624872207642, // Long bits 0x3fe2b80340000000L.
            DP_L = 1.350039202129749e-8, // Long bits 0x3e4cfdeb43cfd006L.
            OVT = 8.008566259537294e-17; // Long bits 0x3c971547652b82feL.

    /**
     */
    private static final double
            S1 = -0.16666666666666632, // Long bits 0xbfc5555555555549L.
            S2 = 8.33333333332249e-3, // Long bits 0x3f8111111110f8a6L.
            S3 = -1.984126982985795e-4, // Long bits 0xbf2a01a019c161d5L.
            S4 = 2.7557313707070068e-6, // Long bits 0x3ec71de357b1fe7dL.
            S5 = -2.5050760253406863e-8, // Long bits 0xbe5ae5e68a2b9cebL.
            S6 = 1.58969099521155e-10; // Long bits 0x3de5d93a5acfd57cL.

    /**
     */
    private static final double
            C1 = 0.0416666666666666, // Long bits 0x3fa555555555554cL.
            C2 = -1.388888888887411e-3, // Long bits 0xbf56c16c16c15177L.
            C3 = 2.480158728947673e-5, // Long bits 0x3efa01a019cb1590L.
            C4 = -2.7557314351390663e-7, // Long bits 0xbe927e4f809c52adL.
            C5 = 2.087572321298175e-9, // Long bits 0x3e21ee9ebdb4b1c4L.
            C6 = -1.1359647557788195e-11; // Long bits 0xbda8fae9be8838d4L.

    /**
     */
    private static final double
            T0 = 0.3333333333333341, // Long bits 0x3fd5555555555563L.
            T1 = 0.13333333333320124, // Long bits 0x3fc111111110fe7aL.
            T2 = 0.05396825397622605, // Long bits 0x3faba1ba1bb341feL.
            T3 = 0.021869488294859542, // Long bits 0x3f9664f48406d637L.
            T4 = 8.8632398235993e-3, // Long bits 0x3f8226e3e96e8493L.
            T5 = 3.5920791075913124e-3, // Long bits 0x3f6d6d22c9560328L.
            T6 = 1.4562094543252903e-3, // Long bits 0x3f57dbc8fee08315L.
            T7 = 5.880412408202641e-4, // Long bits 0x3f4344d8f2f26501L.
            T8 = 2.464631348184699e-4, // Long bits 0x3f3026f71a8d1068L.
            T9 = 7.817944429395571e-5, // Long bits 0x3f147e88a03792a6L.
            T10 = 7.140724913826082e-5, // Long bits 0x3f12b80f32f0a7e9L.
            T11 = -1.8558637485527546e-5, // Long bits 0xbef375cbdb605373L.
            T12 = 2.590730518636337e-5; // Long bits 0x3efb2a7074bf7ad4L.

    /**
     */
    private static final double
            PS0 = 0.16666666666666666, // Long bits 0x3fc5555555555555L.
            PS1 = -0.3255658186224009, // Long bits 0xbfd4d61203eb6f7dL.
            PS2 = 0.20121253213486293, // Long bits 0x3fc9c1550e884455L.
            PS3 = -0.04005553450067941, // Long bits 0xbfa48228b5688f3bL.
            PS4 = 7.915349942898145e-4, // Long bits 0x3f49efe07501b288L.
            PS5 = 3.479331075960212e-5, // Long bits 0x3f023de10dfdf709L.
            QS1 = -2.403394911734414, // Long bits 0xc0033a271c8a2d4bL.
            QS2 = 2.0209457602335057, // Long bits 0x40002ae59c598ac8L.
            QS3 = -0.6882839716054533, // Long bits 0xbfe6066c1b8d0159L.
            QS4 = 0.07703815055590194; // Long bits 0x3fb3b8c5b12e9282L.

    /**
     */
    private static final double
            ATAN_0_5H = 0.4636476090008061, // Long bits 0x3fddac670561bb4fL.
            ATAN_0_5L = 2.2698777452961687e-17, // Long bits 0x3c7a2b7f222f65e2L.
            ATAN_1_5H = 0.982793723247329, // Long bits 0x3fef730bd281f69bL.
            ATAN_1_5L = 1.3903311031230998e-17, // Long bits 0x3c7007887af0cbbdL.
            AT0 = 0.3333333333333293, // Long bits 0x3fd555555555550dL.
            AT1 = -0.19999999999876483, // Long bits 0xbfc999999998ebc4L.
            AT2 = 0.14285714272503466, // Long bits 0x3fc24924920083ffL.
            AT3 = -0.11111110405462356, // Long bits 0xbfbc71c6fe231671L.
            AT4 = 0.09090887133436507, // Long bits 0x3fb745cdc54c206eL.
            AT5 = -0.0769187620504483, // Long bits 0xbfb3b0f2af749a6dL.
            AT6 = 0.06661073137387531, // Long bits 0x3fb10d66a0d03d51L.
            AT7 = -0.058335701337905735, // Long bits 0xbfadde2d52defd9aL.
            AT8 = 0.049768779946159324, // Long bits 0x3fa97b4b24760debL.
            AT9 = -0.036531572744216916, // Long bits 0xbfa2b4442c6a6c2fL.
            AT10 = 0.016285820115365782; // Long bits 0x3f90ad3ae322da11L.

    /**
     */
    private static final int
            CBRT_B1 = 715094163, // B1 = (682-0.03306235651)*2**20
            CBRT_B2 = 696219795; // B2 = (664-0.03306235651)*2**20

    /**
     */
    private static final double
            CBRT_C = 5.42857142857142815906e-01, // Long bits  0x3fe15f15f15f15f1L
            CBRT_D = -7.05306122448979611050e-01, // Long bits  0xbfe691de2532c834L
            CBRT_E = 1.41428571428571436819e+00, // Long bits  0x3ff6a0ea0ea0ea0fL
            CBRT_F = 1.60714285714285720630e+00, // Long bits  0x3ff9b6db6db6db6eL
            CBRT_G = 3.57142857142857150787e-01; // Long bits  0x3fd6db6db6db6db7L

    /**
     */
    private static final double
            EXPM1_Q1 = -3.33333333333331316428e-02, // Long bits  0xbfa11111111110f4L
            EXPM1_Q2 = 1.58730158725481460165e-03, // Long bits  0x3f5a01a019fe5585L
            EXPM1_Q3 = -7.93650757867487942473e-05, // Long bits  0xbf14ce199eaadbb7L
            EXPM1_Q4 = 4.00821782732936239552e-06, // Long bits  0x3ed0cfca86e65239L
            EXPM1_Q5 = -2.01099218183624371326e-07; // Long bits  0xbe8afdb76e09c32dL
    private static final double
            TWO_16 = 0x10000, // Long bits 0x40f0000000000000L.
            TWO_20 = 0x100000, // Long bits 0x4130000000000000L.
            TWO_24 = 0x1000000, // Long bits 0x4170000000000000L.
            TWO_27 = 0x8000000, // Long bits 0x41a0000000000000L.
            TWO_28 = 0x10000000, // Long bits 0x41b0000000000000L.
            TWO_29 = 0x20000000, // Long bits 0x41c0000000000000L.
            TWO_31 = 0x80000000L, // Long bits 0x41e0000000000000L.
            TWO_49 = 0x2000000000000L, // Long bits 0x4300000000000000L.
            TWO_52 = 0x10000000000000L, // Long bits 0x4330000000000000L.
            TWO_54 = 0x40000000000000L, // Long bits 0x4350000000000000L.
            TWO_57 = 0x200000000000000L, // Long bits 0x4380000000000000L.
            TWO_60 = 0x1000000000000000L, // Long bits 0x43b0000000000000L.
            TWO_64 = 1.8446744073709552e19, // Long bits 0x43f0000000000000L.
            TWO_66 = 7.378697629483821e19, // Long bits
            TWO_1023 = 8.98846567431158e307; // Long bits 0x7fe0000000000000L.
}