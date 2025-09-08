import org.json.JSONObject;
import org.json.JSONArray;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PolynomialSolver {
    
    public static void main(String[] args) {
        try {
            // Read JSON file
            String content = new String(Files.readAllBytes(Paths.get("roots.json")));
            JSONObject json = new JSONObject(content);
            
            // Extract roots from JSON
            JSONObject polynomial = json.getJSONObject("polynomial");
            JSONArray rootsArray = polynomial.getJSONArray("roots");
            
            // Convert JSONArray to double array
            double[] roots = new double[rootsArray.length()];
            for (int i = 0; i < rootsArray.length(); i++) {
                roots[i] = rootsArray.getDouble(i);
            }
            
            System.out.println("Polynomial Coefficient Calculator");
            System.out.println("=================================");
            System.out.print("Roots from JSON: ");
            for (int i = 0; i < roots.length; i++) {
                System.out.print(roots[i]);
                if (i < roots.length - 1) System.out.print(", ");
            }
            System.out.println();
            
            // Calculate coefficients based on number of roots
            if (roots.length == 2) {
                calculateQuadraticCoefficients(roots);
            } else if (roots.length == 3) {
                calculateCubicCoefficients(roots);
            } else {
                System.out.println("This program supports quadratic (2 roots) and cubic (3 roots) polynomials.");
            }
            
        } catch (IOException e) {
            System.err.println("Error reading JSON file: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error processing JSON: " + e.getMessage());
        }
    }
    
    /**
     * Calculate coefficients for quadratic polynomial: ax^2 + bx + c = 0
     * If roots are r1 and r2, then: (x - r1)(x - r2) = x^2 - (r1+r2)x + r1*r2
     * So: a = 1, b = -(r1+r2), c = r1*r2
     */
    private static void calculateQuadraticCoefficients(double[] roots) {
        if (roots.length != 2) {
            System.err.println("Quadratic polynomial requires exactly 2 roots");
            return;
        }
        
        double r1 = roots[0];
        double r2 = roots[1];
        
        // For monic polynomial (leading coefficient = 1)
        double a = 1.0;
        double b = -(r1 + r2);  // Sum of roots with opposite sign
        double c = r1 * r2;     // Product of roots
        
        System.out.println("\nQuadratic Polynomial: ax² + bx + c = 0");
        System.out.println("Coefficients:");
        System.out.printf("a = %.2f\n", a);
        System.out.printf("b = %.2f\n", b);
        System.out.printf("c = %.2f ← CONSTANT TERM\n", c);
        
        System.out.printf("\nPolynomial: %.2fx² + %.2fx + %.2f = 0\n", a, b, c);
        
        // Verify by substituting roots back
        System.out.println("\nVerification:");
        for (double root : roots) {
            double result = a * root * root + b * root + c;
            System.out.printf("f(%.2f) = %.6f (should be ≈ 0)\n", root, result);
        }
    }
    
    /**
     * Calculate coefficients for cubic polynomial: ax^3 + bx^2 + cx + d = 0
     * If roots are r1, r2, r3, then: (x-r1)(x-r2)(x-r3)
     */
    private static void calculateCubicCoefficients(double[] roots) {
        if (roots.length != 3) {
            System.err.println("Cubic polynomial requires exactly 3 roots");
            return;
        }
        
        double r1 = roots[0];
        double r2 = roots[1];
        double r3 = roots[2];
        
        // For monic polynomial (leading coefficient = 1)
        double a = 1.0;
        double b = -(r1 + r2 + r3);                    // Sum of roots
        double c = r1*r2 + r1*r3 + r2*r3;             // Sum of products taken two at a time
        double d = -r1*r2*r3;                         // Product of all roots
        
        System.out.println("\nCubic Polynomial: ax³ + bx² + cx + d = 0");
        System.out.println("Coefficients:");
        System.out.printf("a = %.2f\n", a);
        System.out.printf("b = %.2f\n", b);
        System.out.printf("c = %.2f\n", c);
        System.out.printf("d = %.2f ← CONSTANT TERM\n", d);
        
        System.out.printf("\nPolynomial: %.2fx³ + %.2fx² + %.2fx + %.2f = 0\n", a, b, c, d);
        
        // Verify by substituting roots back
        System.out.println("\nVerification:");
        for (double root : roots) {
            double result = a * root * root * root + b * root * root + c * root + d;
            System.out.printf("f(%.2f) = %.6f (should be ≈ 0)\n", root, result);
        }
    }
}
