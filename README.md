# Hashira Placements Assignment - Secret Sharing Solution

## Problem Description
This is a Shamir's Secret Sharing implementation that reconstructs a polynomial from points stored in different number bases. The goal is to find the constant term (secret) of the polynomial.

## Solution Approach

### 1. Base Conversion
- Each point has coordinates (x, y) where y is given in a specific base
- Convert all y-values from their respective bases to decimal

### 2. Polynomial Reconstruction
- Use Lagrange interpolation to reconstruct the polynomial
- Take the first k points (minimum required for degree k-1 polynomial)
- Calculate f(0) which gives the constant term (the secret)

### 3. Verification
- Verify the polynomial with remaining points to ensure correctness

## Results

### Test Case 1
- **n = 4, k = 3** (quadratic polynomial)
- **Secret (Constant Term): 3**

Points after base conversion:
- (1, 4): base 10 "4" → 4
- (2, 7): base 2 "111" → 7  
- (3, 12): base 10 "12" → 12
- (6, 39): base 4 "213" → 39

### Test Case 2
- **n = 10, k = 7** (degree 6 polynomial)
- **Secret (Constant Term): -6290016743746469796**

## Mathematical Foundation

For Shamir's Secret Sharing:
- The secret is the constant term of a polynomial
- Given k points, we can reconstruct a polynomial of degree k-1
- Using Lagrange interpolation: f(0) = Σ(yi × Li(0))
- Where Li(0) is the Lagrange basis polynomial evaluated at x=0

## Implementation Details

- Language: Java
- Uses BigInteger for handling large numbers
- Simple JSON parsing without external libraries
- Lagrange interpolation for polynomial reconstruction
- Base conversion supporting bases 2-16 (and higher)

## Files
- `HashiraSecretSharing.java` - Main solution
- `roots.json` - Test case 1
- `testcase2.json` - Test case 2
- `README.md` - This documentation

## How to Run
```bash
javac HashiraSecretSharing.java
java HashiraSecretSharing
```

## Output
The program outputs the secret (constant term) for both test cases with detailed intermediate calculations and verification.
