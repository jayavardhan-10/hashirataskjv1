/**
 * Manual verification of Test Case 1
 * Points: (1,4), (2,7), (3,12)
 * Find polynomial and verify c = 3
 */
public class ManualVerification {
    public static void main(String[] args) {
        // Points: (1,4), (2,7), (3,12)
        // Using Lagrange interpolation to find f(0)
        
        System.out.println("MANUAL VERIFICATION - Test Case 1");
        System.out.println("Points: (1,4), (2,7), (3,12)");
        System.out.println();
        
        // Lagrange interpolation: f(0) = sum of yi * Li(0)
        // Li(0) = product of (0-xj)/(xi-xj) for all j != i
        
        // For point (1,4): L0(0) = (0-2)(0-3) / (1-2)(1-3) = 6/2 = 3
        double L0_0 = ((0.0-2)*(0.0-3)) / ((1.0-2)*(1.0-3));
        double term0 = 4 * L0_0;
        System.out.println("Point (1,4): L0(0) = " + L0_0 + ", term = " + term0);
        
        // For point (2,7): L1(0) = (0-1)(0-3) / (2-1)(2-3) = 3/-1 = -3
        double L1_0 = ((0.0-1)*(0.0-3)) / ((2.0-1)*(2.0-3));
        double term1 = 7 * L1_0;
        System.out.println("Point (2,7): L1(0) = " + L1_0 + ", term = " + term1);
        
        // For point (3,12): L2(0) = (0-1)(0-2) / (3-1)(3-2) = 2/2 = 1
        double L2_0 = ((0.0-1)*(0.0-2)) / ((3.0-1)*(3.0-2));
        double term2 = 12 * L2_0;
        System.out.println("Point (3,12): L2(0) = " + L2_0 + ", term = " + term2);
        
        double constant = term0 + term1 + term2;
        System.out.println();
        System.out.println("f(0) = " + term0 + " + " + term1 + " + " + term2 + " = " + constant);
        System.out.println("CONSTANT TERM c = " + (int)constant);
        
        // Verify by finding the actual polynomial
        System.out.println("\nVERIFICATION - Finding actual polynomial:");
        System.out.println("Using points (1,4), (2,7), (3,12) to find ax² + bx + c");
        
        // System of equations:
        // a(1)² + b(1) + c = 4  →  a + b + c = 4
        // a(2)² + b(2) + c = 7  →  4a + 2b + c = 7
        // a(3)² + b(3) + c = 12 →  9a + 3b + c = 12
        
        // Solving: 
        // From eq1 and eq2: 3a + b = 3  →  b = 3 - 3a
        // From eq2 and eq3: 5a + b = 5  →  b = 5 - 5a
        // Therefore: 3 - 3a = 5 - 5a  →  2a = 2  →  a = 1
        // So: b = 3 - 3(1) = 0
        // And: c = 4 - a - b = 4 - 1 - 0 = 3
        
        double a = 1, b = 0, c = 3;
        System.out.println("Polynomial: " + a + "x² + " + b + "x + " + c);
        System.out.println("Constant term c = " + (int)c);
        
        // Double-check by substituting points back
        System.out.println("\nDouble-check:");
        for (int[] point : new int[][]{{1,4}, {2,7}, {3,12}}) {
            int x = point[0], y = point[1];
            double result = a*x*x + b*x + c;
            System.out.println("f(" + x + ") = " + result + " (expected " + y + ")");
        }
    }
}
