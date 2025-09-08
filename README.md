# Hashira Placements Assignment - Shamir's Secret Sharing Solution

## 🎯 Objective
Find the constant term (secret) of a polynomial using Shamir's Secret Sharing scheme from encoded points stored in different number bases.

## 🔑 **FINAL RESULTS - C VALUES (SECRETS)**

### **Test Case 1:**
- **C Value (Secret): 3**
- Input: n=4, k=3 (quadratic polynomial)

### **Test Case 2:**
- **C Value (Secret): -6290016743746469796**
- Input: n=10, k=7 (degree 6 polynomial)

---

## 🛠️ **Solution Approach**

### **Step 1: Parse JSON Input**
- Extract points with their x-coordinates, base values, and encoded y-values
- Handle multiple test cases with different polynomial degrees

### **Step 2: Base Conversion**
- Convert y-values from various bases (2, 3, 6, 8, 10, 12, 15, 16) to decimal
- Support for alphanumeric digits (0-9, a-z) for higher bases
- Example: base 2 "111" → 7, base 4 "213" → 39

### **Step 3: Polynomial Reconstruction using Lagrange Interpolation**
- Use first k points to reconstruct polynomial of degree k-1
- Apply Lagrange interpolation formula: **f(0) = Σ(yi × Li(0))**
- Where Li(0) = Π((0-xj)/(xi-xj)) for all j≠i

### **Step 4: Extract Constant Term**
- The secret is f(0) - the polynomial evaluated at x=0
- This gives us the constant term directly

### **Step 5: Verification**
- Verify polynomial with remaining points to ensure correctness
- All verification points matched successfully ✓

---

## 🧮 **Mathematical Foundation**

**Shamir's Secret Sharing:**
- Secret is hidden as the constant term of a polynomial
- Minimum k points needed to reconstruct polynomial of degree k-1
- Each point (x,y) represents a share of the secret

**Lagrange Interpolation Formula:**
```
f(x) = Σ(i=0 to k-1) yi × Li(x)
where Li(x) = Π(j≠i) (x-xj)/(xi-xj)

For constant term: f(0) = Σ(i=0 to k-1) yi × Li(0)
where Li(0) = Π(j≠i) (-xj)/(xi-xj)
```

---

## 📊 **Detailed Results**

### **Test Case 1 Analysis:**
- **Points after conversion:** (1,4), (2,7), (3,12), (6,39)
- **Used for reconstruction:** First 3 points
- **Polynomial degree:** 2 (quadratic)
- **Lagrange calculation:** 12 - 21 + 12 = 3
- **Final Secret:** **3** ✓

### **Test Case 2 Analysis:**
- **Total points:** 10 with complex base conversions
- **Used for reconstruction:** First 7 points  
- **Polynomial degree:** 6
- **Large number precision:** Handled with BigInteger
- **Final Secret:** **-6290016743746469796** ✓

---

## 💻 **Implementation Details**

- **Language:** Java (as per assignment requirements)
- **Libraries:** Pure Java (no external dependencies)
- **Precision:** BigInteger for handling large numbers
- **Verification:** All remaining points successfully verified

### **Key Files:**
- `HashiraSecretSharing.java` - Complete solution with detailed output
- `HashiraCleanOutput.java` - Clean version for final results
- `roots.json` - Test case 1 data
- `testcase2.json` - Test case 2 data

---

## 🚀 **How to Run**
```bash
# Compile and run
javac HashiraSecretSharing.java
java HashiraSecretSharing

# For clean output only
javac HashiraCleanOutput.java
java HashiraCleanOutput
```

---

## ✅ **Key Achievements**

1. ✅ **Correct Secret Recovery:** Both test cases solved accurately
2. ✅ **Multi-base Support:** Handles bases 2-16 seamlessly  
3. ✅ **Large Number Precision:** Uses BigInteger for accuracy
4. ✅ **Mathematical Rigor:** Proper Lagrange interpolation implementation
5. ✅ **Verification Success:** All remaining points verified
6. ✅ **Clean Code:** Well-documented, readable implementation
7. ✅ **No External Dependencies:** Pure Java solution

---

## 🎯 **Assignment Completion Status**

| Requirement | Status |
|-------------|--------|
| Read JSON input | ✅ Complete |
| Handle multiple bases | ✅ Complete |
| Implement Shamir's Secret Sharing | ✅ Complete |
| Find constant term (secret) | ✅ Complete |
| Language: Not Python | ✅ Java Used |
| Provide GitHub link | ✅ Ready |
| Show output | ✅ Complete |

**Final Secrets Found:**
- **Test Case 1: C = 3**
- **Test Case 2: C = -6290016743746469796**

