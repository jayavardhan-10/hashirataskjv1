import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.math.BigInteger;

/**
 * Hashira Placements Assignment - Shamir's Secret Sharing
 * This program reconstructs a polynomial from points stored in different bases
 * and finds the constant term (secret) using Lagrange interpolation.
 */
public class HashiraSecretSharing {
    
    public static void main(String[] args) {
        // Process both test cases
        System.out.println("=".repeat(60));
        System.out.println("HASHIRA PLACEMENTS ASSIGNMENT - SECRET SHARING SOLVER");
        System.out.println("=".repeat(60));
        
        processTestCase("roots.json", "Test Case 1");
        System.out.println("\n" + "=".repeat(60));
        processTestCase("testcase2.json", "Test Case 2");
    }
    
    private static void processTestCase(String filename, String testName) {
        try {
            System.out.println("\n" + testName + " - Processing: " + filename);
            System.out.println("-".repeat(40));
            
            // Read and parse JSON
            String content = new String(Files.readAllBytes(Paths.get(filename)));
            TestCase testCase = parseJsonTestCase(content);
            
            System.out.println("n (total points): " + testCase.n);
            System.out.println("k (minimum points needed): " + testCase.k);
            System.out.println("Polynomial degree: " + (testCase.k - 1));
            
            // Convert points from different bases to decimal
            List<Point> points = convertPointsToDecimal(testCase.points);
            
            System.out.println("\nConverted Points (x, y):");
            for (Point p : points) {
                System.out.println("(" + p.x + ", " + p.y + ")");
            }
            
            // Take first k points for interpolation
            List<Point> selectedPoints = points.subList(0, Math.min(testCase.k, points.size()));
            
            // Find the constant term using Lagrange interpolation
            BigInteger constantTerm = findConstantTerm(selectedPoints);
            
            System.out.println("\n" + "*".repeat(50));
            System.out.println("*** SECRET (CONSTANT TERM): " + constantTerm + " ***");
            System.out.println("*".repeat(50));
            
            // Verify with additional points if available
            if (points.size() > testCase.k) {
                System.out.println("\nVerification with remaining points:");
                verifyPolynomial(selectedPoints, points.subList(testCase.k, points.size()));
            }
            
        } catch (IOException e) {
            System.err.println("Error reading file " + filename + ": " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error processing " + filename + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Parse JSON test case using simple string parsing
     */
    private static TestCase parseJsonTestCase(String jsonContent) {
        TestCase testCase = new TestCase();
        
        // Parse n and k
        testCase.n = parseIntValue(jsonContent, "n");
        testCase.k = parseIntValue(jsonContent, "k");
        
        // Parse points
        testCase.points = new ArrayList<>();
        
        // Use regex to find point patterns
        java.util.regex.Pattern pointPattern = java.util.regex.Pattern.compile(
            "\"(\\d+)\":\\s*\\{[^}]*\"base\":\\s*\"(\\d+)\"[^}]*\"value\":\\s*\"([^\"]+)\"[^}]*\\}"
        );
        java.util.regex.Matcher matcher = pointPattern.matcher(jsonContent);
        
        while (matcher.find()) {
            int x = Integer.parseInt(matcher.group(1));
            int base = Integer.parseInt(matcher.group(2));
            String value = matcher.group(3);
            testCase.points.add(new PointData(x, base, value));
        }
        
        return testCase;
    }
    
    private static int parseIntValue(String jsonContent, String key) {
        String pattern = "\"" + key + "\":\\s*(\\d+)";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(pattern);
        java.util.regex.Matcher m = p.matcher(jsonContent);
        if (m.find()) {
            return Integer.parseInt(m.group(1));
        }
        return 0;
    }
    
    /**
     * Convert points from various bases to decimal
     */
    private static List<Point> convertPointsToDecimal(List<PointData> pointData) {
        List<Point> points = new ArrayList<>();
        
        for (PointData pd : pointData) {
            BigInteger y = convertToDecimal(pd.value, pd.base);
            points.add(new Point(BigInteger.valueOf(pd.x), y));
            System.out.println("Point " + pd.x + ": base " + pd.base + " value '" + 
                             pd.value + "' = " + y);
        }
        
        return points;
    }
    
    /**
     * Convert a number from given base to decimal
     */
    private static BigInteger convertToDecimal(String value, int base) {
        BigInteger result = BigInteger.ZERO;
        BigInteger baseValue = BigInteger.valueOf(base);
        
        for (int i = 0; i < value.length(); i++) {
            char digit = value.charAt(i);
            int digitValue;
            
            if (digit >= '0' && digit <= '9') {
                digitValue = digit - '0';
            } else if (digit >= 'a' && digit <= 'z') {
                digitValue = digit - 'a' + 10;
            } else if (digit >= 'A' && digit <= 'Z') {
                digitValue = digit - 'A' + 10;
            } else {
                throw new IllegalArgumentException("Invalid digit: " + digit);
            }
            
            if (digitValue >= base) {
                throw new IllegalArgumentException("Digit " + digit + " is invalid for base " + base);
            }
            
            result = result.multiply(baseValue).add(BigInteger.valueOf(digitValue));
        }
        
        return result;
    }
    
    /**
     * Find the constant term using Lagrange interpolation
     * The constant term is f(0) where f is the polynomial
     */
    private static BigInteger findConstantTerm(List<Point> points) {
        System.out.println("\nCalculating constant term using Lagrange interpolation...");
        
        // Calculate f(0) using Lagrange interpolation
        BigInteger result = BigInteger.ZERO;
        
        for (int i = 0; i < points.size(); i++) {
            Point pi = points.get(i);
            
            // Calculate Lagrange basis polynomial Li(0)
            BigInteger numerator = BigInteger.ONE;
            BigInteger denominator = BigInteger.ONE;
            
            for (int j = 0; j < points.size(); j++) {
                if (i != j) {
                    Point pj = points.get(j);
                    // Li(0) = product of (0 - xj) / (xi - xj) for all j != i
                    numerator = numerator.multiply(pj.x.negate()); // (0 - xj) = -xj
                    denominator = denominator.multiply(pi.x.subtract(pj.x)); // (xi - xj)
                }
            }
            
            // Add yi * Li(0) to result
            BigInteger term = pi.y.multiply(numerator).divide(denominator);
            result = result.add(term);
            
            System.out.println("Point " + pi.x + ": L" + i + "(0) = " + 
                             numerator + "/" + denominator + ", term = " + term);
        }
        
        return result;
    }
    
    /**
     * Verify the polynomial with additional points
     */
    private static void verifyPolynomial(List<Point> usedPoints, List<Point> verificationPoints) {
        for (Point vp : verificationPoints) {
            BigInteger calculatedY = evaluatePolynomial(usedPoints, vp.x);
            boolean matches = calculatedY.equals(vp.y);
            System.out.println("Verification point (" + vp.x + ", " + vp.y + 
                             "): calculated = " + calculatedY + 
                             (matches ? " ✓" : " ✗"));
        }
    }
    
    /**
     * Evaluate polynomial at point x using Lagrange interpolation
     */
    private static BigInteger evaluatePolynomial(List<Point> points, BigInteger x) {
        BigInteger result = BigInteger.ZERO;
        
        for (int i = 0; i < points.size(); i++) {
            Point pi = points.get(i);
            
            BigInteger numerator = BigInteger.ONE;
            BigInteger denominator = BigInteger.ONE;
            
            for (int j = 0; j < points.size(); j++) {
                if (i != j) {
                    Point pj = points.get(j);
                    numerator = numerator.multiply(x.subtract(pj.x));
                    denominator = denominator.multiply(pi.x.subtract(pj.x));
                }
            }
            
            BigInteger term = pi.y.multiply(numerator).divide(denominator);
            result = result.add(term);
        }
        
        return result;
    }
    
    // Data classes
    static class TestCase {
        int n, k;
        List<PointData> points;
    }
    
    static class PointData {
        int x, base;
        String value;
        
        PointData(int x, int base, String value) {
            this.x = x;
            this.base = base;
            this.value = value;
        }
    }
    
    static class Point {
        BigInteger x, y;
        
        Point(BigInteger x, BigInteger y) {
            this.x = x;
            this.y = y;
        }
    }
}
