import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimplePolynomialSolver {
    
    public static void main(String[] args) {
        try {
            // Read JSON file
            String content = new String(Files.readAllBytes(Paths.get("roots.json")));
            
            // Simple JSON parsing for roots array
            List<Double> rootsList = parseRootsFromJson(content);
            double[] roots = rootsList.stream().mapToDouble(Double::doubleValue).toArray();
            
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
     * Simple JSON parser to extract roots array
     */
    private static List<Double> parseRootsFromJson(String jsonContent) {
        List<Double> roots = new ArrayList<>();
        
        // Find the roots array in JSON
        Pattern rootsPattern = Pattern.compile("\"roots\"\\s*:\\s*\\[(.*?)\\]");
        Matcher matcher = rootsPattern.matcher(jsonContent);
        
        if (matcher.find()) {
            String rootsArrayContent = matcher.group(1);
            // Split by comma and parse each number
            String[] rootStrings = rootsArrayContent.split(",");
            for (String rootStr : rootStrings) {
                try {
                    double root = Double.parseDouble(rootStr.trim());
                    roots.add(root);
                } catch (NumberFormatException e) {
                    System.err.println("Warning: Could not parse root value: " + rootStr);
                }
            }
        }
        
        return roots;
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
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("QUADRATIC POLYNOMIAL ANALYSIS");
        System.out.println("=".repeat(50));
        System.out.println("Standard form: ax² + bx + c = 0");
        System.out.println("Factored form: (x - " + r1 + ")(x - " + r2 + ") = 0");
        
        System.out.println("\nCoefficients:");
        System.out.printf("a = %.6f (coefficient of x²)\n", a);
        System.out.printf("b = %.6f (coefficient of x)\n", b);
        System.out.printf("c = %.6f ← CONSTANT TERM ←\n", c);
        
        System.out.println("\n" + "*".repeat(30));
        System.out.printf("*** ANSWER: c = %.6f ***\n", c);
        System.out.println("*".repeat(30));
        
        System.out.printf("\nComplete polynomial: %.6fx² + %.6fx + %.6f = 0\n", a, b, c);
        
        // Mathematical explanation
        System.out.println("\nMathematical derivation:");
        System.out.println("If polynomial has roots r₁ = " + r1 + " and r₂ = " + r2);
        System.out.println("Then: (x - r₁)(x - r₂) = 0");
        System.out.println("Expanding: x² - (r₁ + r₂)x + r₁×r₂ = 0");
        System.out.printf("Therefore: x² - (%.2f + %.2f)x + %.2f×%.2f = 0\n", r1, r2, r1, r2);
        System.out.printf("Simplified: x² + %.6fx + %.6f = 0\n", b, c);
        
        // Verify by substituting roots back
        System.out.println("\nVerification (substituting roots back into polynomial):");
        for (double root : roots) {
            double result = a * root * root + b * root + c;
            System.out.printf("f(%.2f) = %.2f×(%.2f)² + %.2f×(%.2f) + %.2f = %.8f\n", 
                            root, a, root, b, root, c, result);
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
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("CUBIC POLYNOMIAL ANALYSIS");
        System.out.println("=".repeat(50));
        System.out.println("Standard form: ax³ + bx² + cx + d = 0");
        System.out.printf("Factored form: (x - %.2f)(x - %.2f)(x - %.2f) = 0\n", r1, r2, r3);
        
        System.out.println("\nCoefficients:");
        System.out.printf("a = %.6f (coefficient of x³)\n", a);
        System.out.printf("b = %.6f (coefficient of x²)\n", b);
        System.out.printf("c = %.6f (coefficient of x)\n", c);
        System.out.printf("d = %.6f ← CONSTANT TERM ←\n", d);
        
        System.out.println("\n" + "*".repeat(30));
        System.out.printf("*** ANSWER: d = %.6f ***\n", d);
        System.out.println("*".repeat(30));
        
        System.out.printf("\nComplete polynomial: %.6fx³ + %.6fx² + %.6fx + %.6f = 0\n", a, b, c, d);
        
        // Mathematical explanation
        System.out.println("\nMathematical derivation:");
        System.out.printf("Roots: r₁ = %.2f, r₂ = %.2f, r₃ = %.2f\n", r1, r2, r3);
        System.out.println("For cubic: (x - r₁)(x - r₂)(x - r₃) = 0");
        System.out.printf("Constant term = -r₁×r₂×r₃ = -(%.2f×%.2f×%.2f) = %.6f\n", r1, r2, r3, d);
        
        // Verify by substituting roots back
        System.out.println("\nVerification (substituting roots back into polynomial):");
        for (double root : roots) {
            double result = a * root * root * root + b * root * root + c * root + d;
            System.out.printf("f(%.2f) = %.6f×(%.2f)³ + %.6f×(%.2f)² + %.6f×(%.2f) + %.6f = %.8f\n", 
                            root, a, root, b, root, c, root, d, result);
        }
    }
}
