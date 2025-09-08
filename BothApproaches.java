/**
 * Testing both interpretations to see which one is correct
 */
public class BothApproaches {
    public static void main(String[] args) {
        System.out.println("TESTING BOTH INTERPRETATIONS - Test Case 1");
        System.out.println("===========================================");
        
        // Data from Test Case 1
        System.out.println("Raw data:");
        System.out.println("\"1\": {\"base\": \"10\", \"value\": \"4\"}  → 4");
        System.out.println("\"2\": {\"base\": \"2\", \"value\": \"111\"} → 7"); 
        System.out.println("\"3\": {\"base\": \"10\", \"value\": \"12\"} → 12");
        System.out.println("\"6\": {\"base\": \"4\", \"value\": \"213\"} → 39");
        System.out.println();
        
        // APPROACH 1: Treat as polynomial roots
        System.out.println("APPROACH 1: Polynomial Roots Method");
        System.out.println("k=3, so degree=2, use first 2 roots: 4, 7");
        System.out.println("For polynomial with roots r1, r2:");
        System.out.println("(x - r1)(x - r2) = x² - (r1+r2)x + r1*r2");
        System.out.println("(x - 4)(x - 7) = x² - 11x + 28");
        System.out.println("Constant term c = 28");
        System.out.println();
        
        // APPROACH 2: Treat as Shamir's Secret Sharing points
        System.out.println("APPROACH 2: Shamir's Secret Sharing Points");
        System.out.println("Points: (1,4), (2,7), (3,12)");
        System.out.println("Using Lagrange interpolation:");
        
        // Manual calculation
        double term1 = 4 * ((0.0-2)*(0.0-3)) / ((1.0-2)*(1.0-3)); // 4 * 6/2 = 12
        double term2 = 7 * ((0.0-1)*(0.0-3)) / ((2.0-1)*(2.0-3)); // 7 * 3/(-1) = -21
        double term3 = 12 * ((0.0-1)*(0.0-2)) / ((3.0-1)*(3.0-2)); // 12 * 2/2 = 12
        
        System.out.println("f(0) = " + term1 + " + " + term2 + " + " + term3 + " = " + (term1+term2+term3));
        System.out.println("Constant term c = 3");
        System.out.println();
        
        // KEY QUESTION: Which interpretation matches the JSON structure?
        System.out.println("KEY ANALYSIS:");
        System.out.println("- JSON has keys \"1\", \"2\", \"3\", \"6\" (not sequential)");
        System.out.println("- If these were just roots, why use \"6\" instead of \"4\"?");
        System.out.println("- The structure suggests (x,y) coordinates where x=key, y=converted_value");
        System.out.println();
        
        // Let's test both interpretations
        System.out.println("VERIFICATION TEST:");
        System.out.println("If c=28 (roots approach), polynomial is x² - 11x + 28");
        System.out.println("Check: f(1) = 1 - 11 + 28 = 18 != 4 (WRONG)");
        System.out.println("Check: f(2) = 4 - 22 + 28 = 10 != 7 (WRONG)");
        System.out.println();
        System.out.println("If c=3 (Shamir approach), polynomial is x² + 3");
        System.out.println("Check: f(1) = 1 + 3 = 4 (CORRECT)");
        System.out.println("Check: f(2) = 4 + 3 = 7 (CORRECT)");
        System.out.println("Check: f(3) = 9 + 3 = 12 (CORRECT)");
        System.out.println();
        System.out.println("CONCLUSION: Shamir's Secret Sharing interpretation is CORRECT!");
    }
}
