import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.math.BigInteger;

/**
 * Clean output version for Hashira assignment submission
 */
public class HashiraCleanOutput {
    
    public static void main(String[] args) {
        System.out.println("HASHIRA PLACEMENTS ASSIGNMENT - RESULTS");
        System.out.println("========================================");
        
        // Test Case 1
        System.out.println("\nTEST CASE 1:");
        processTestCase("roots.json");
        
        // Test Case 2  
        System.out.println("\nTEST CASE 2:");
        processTestCase("testcase2.json");
    }
    
    private static void processTestCase(String filename) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(filename)));
            
            // Parse basic info
            int n = parseIntValue(content, "n");
            int k = parseIntValue(content, "k");
            
            System.out.println("File: " + filename);
            System.out.println("n = " + n + ", k = " + k);
            
            // Parse and convert points
            List<Point> points = parseAndConvertPoints(content);
            
            // Calculate secret using first k points
            List<Point> selectedPoints = points.subList(0, Math.min(k, points.size()));
            BigInteger secret = calculateSecret(selectedPoints);
            
            System.out.println("SECRET (CONSTANT TERM): " + secret);
            
        } catch (Exception e) {
            System.err.println("Error processing " + filename + ": " + e.getMessage());
        }
    }
    
    private static List<Point> parseAndConvertPoints(String content) {
        List<Point> points = new ArrayList<>();
        
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(
            "\"(\\d+)\":\\s*\\{[^}]*\"base\":\\s*\"(\\d+)\"[^}]*\"value\":\\s*\"([^\"]+)\"[^}]*\\}"
        );
        java.util.regex.Matcher matcher = pattern.matcher(content);
        
        while (matcher.find()) {
            int x = Integer.parseInt(matcher.group(1));
            int base = Integer.parseInt(matcher.group(2));
            String value = matcher.group(3);
            
            BigInteger y = convertToDecimal(value, base);
            points.add(new Point(BigInteger.valueOf(x), y));
        }
        
        return points;
    }
    
    private static BigInteger convertToDecimal(String value, int base) {
        BigInteger result = BigInteger.ZERO;
        BigInteger baseValue = BigInteger.valueOf(base);
        
        for (char digit : value.toCharArray()) {
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
            
            result = result.multiply(baseValue).add(BigInteger.valueOf(digitValue));
        }
        
        return result;
    }
    
    private static BigInteger calculateSecret(List<Point> points) {
        BigInteger result = BigInteger.ZERO;
        
        for (int i = 0; i < points.size(); i++) {
            Point pi = points.get(i);
            
            BigInteger numerator = BigInteger.ONE;
            BigInteger denominator = BigInteger.ONE;
            
            for (int j = 0; j < points.size(); j++) {
                if (i != j) {
                    Point pj = points.get(j);
                    numerator = numerator.multiply(pj.x.negate());
                    denominator = denominator.multiply(pi.x.subtract(pj.x));
                }
            }
            
            BigInteger term = pi.y.multiply(numerator).divide(denominator);
            result = result.add(term);
        }
        
        return result;
    }
    
    private static int parseIntValue(String content, String key) {
        java.util.regex.Pattern p = java.util.regex.Pattern.compile("\"" + key + "\":\\s*(\\d+)");
        java.util.regex.Matcher m = p.matcher(content);
        return m.find() ? Integer.parseInt(m.group(1)) : 0;
    }
    
    static class Point {
        BigInteger x, y;
        Point(BigInteger x, BigInteger y) { this.x = x; this.y = y; }
    }
}
