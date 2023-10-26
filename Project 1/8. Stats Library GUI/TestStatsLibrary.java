
// Chris Ricchi
// 9-8-23
// Stats Library Test Class

import java.math.BigInteger;
import java.util.ArrayList;

public class TestStatsLibrary {

    public static void main(String[] args)
    {
        StatsLibrary test = new StatsLibrary();
        ArrayList<Double> testNumbers = new ArrayList<>();

        testNumbers.add(1.0);
        testNumbers.add(2.0);
        testNumbers.add(3.0);
        testNumbers.add(4.0);
        testNumbers.add(5.0);
        testNumbers.add(6.0);
        testNumbers.add(7.0);

        // Calculate mean
        double meanResults = test.findMean(testNumbers);
        System.out.println("Mean Test Results: " + meanResults);

        // Calculate median
        double medianResults = test.findMedian(testNumbers);
        System.out.println("Median Test Results: " + medianResults);

        // Calculate mode
        double modeResults = test.findMode(testNumbers);
        System.out.println("Mode Test Results: " + modeResults);

        // Calculate the standard deviation
        double standardDeviationResults = test.findStandardDeviation(testNumbers);
        System.out.println("Standard Deviation Test Results: " + standardDeviationResults);

        // Calculate variance
        ArrayList<Double> data = new ArrayList<>();
        data.add(2.0);
        data.add(4.0);
        data.add(6.0);
        data.add(8.0);
        data.add(10.0);
        
        double varianceResults = test.findVariance(data);
        System.out.println("Variance results: " + varianceResults);
        
        // Calculate the factorial of 5
        int factorial = 5;
        BigInteger factorialResults = test.findFactorial(factorial);
        System.out.println("Factorial of " + factorial + ": " + factorialResults);

        // Calculate the permutations of 5 choose 3
        int n = 5;
        int r = 3;
        BigInteger permutationsResults = test.findPermutation(n, r);
        System.out.println("Permutations (" + n + " P " + r + "): " + permutationsResults);

        // Calculate the combinations of 5 choose 3
        BigInteger combinationsResults = test.findCombination(n, r);
        System.out.println("Combinations (" + n + " C " + r + "): " + combinationsResults);

        // Create two arrays of data for correlation and dependency checks
        ArrayList<Double> x = new ArrayList<>();
        x.add(1.0);
        x.add(2.0);
        x.add(3.0);
        x.add(4.0);
        x.add(5.0);

        // Second array of data for correlation and dependency
        ArrayList<Double> y = new ArrayList<>();
        y.add(2.0);
        y.add(4.0);
        y.add(6.0);
        y.add(8.0);
        y.add(10.0);

        // Calculate the correlation coefficient between x and y
        double correlationResults = test.determineCorrelation(x, y);
        System.out.println("Correlation Coefficient: " + correlationResults);

        // Check if x and y are dependent with a threshold of 0.7
        double threshold = 0.7;
        boolean isDependentResults = test.isDependent(x, y, 0.7);
        System.out.println("Are x and y dependent (threshold " + threshold + ")? " + isDependentResults);

        // Union, Intersection, and Compliment
        ArrayList<Double> list1 = new ArrayList<>();
        ArrayList<Double> list2 = new ArrayList<>();
        
        list1.add(1.0);
        list1.add(2.0);
        list1.add(3.0);

        list2.add(2.0);
        list2.add(3.0);
        list2.add(4.0);
        
        // Union
        ArrayList<Double> unionResult = test.findUnion(list1, list2);
        System.out.println("Union Result: " + unionResult);

        // Intersection
        ArrayList<Double> intersectionResult = test.findIntersection(list1, list2);
        System.out.println("Intersection Result: " + intersectionResult);

        // Compliment
        ArrayList<Double> complementResult = test.findComplement(list1, list2);
        System.out.println("Complement Result: " + complementResult);
        
        //Calculate conditional probability
        double conditionalResult = test.conditionalProbability(0.4, 0.7);
        System.out.println("Conditional Probability: " + conditionalResult);
        
        // Calculate Baye's Theorem
        double bayesTheoremResult = test.bayesTheorem(0.8, 0.4, 0.6);
        System.out.println("Baye's Theorem Result: " + bayesTheoremResult);

        // Calculate the binomial distribution
        double binomialDistributionResults = test.binomialDistribution(10, 3, 0.2, 0.8);
        System.out.println("Binomial Distribution Probability: " + binomialDistributionResults);
        
        // Calculate Hypergeometric Distribution
        double hypergeometricDistributionResults = test.hypergeometricDistribution(5, 20, 5, 10);
        System.out.println("Hypergeometric Distribution Results: " + hypergeometricDistributionResults);
        
        // Calculate "extra methods" of geometric distribution
        double geometricOnOrBeforeNResults = test.geometricOnOrBeforeN(5, 0.2);
        double geometricSuccessBeforeNResults = test.geometricSuccessBeforeN(5, 0.2);
        double geometricSuccessOnOrBeforeNResults = test.geometricSuccessOnOrBeforeN(5, 0.2);
        double geometricSuccessAfterNResults = test.geometricSuccessAfterN(5, 0.2);
        System.out.println("Geometric On or Before 5 Results: " + geometricOnOrBeforeNResults);
        System.out.println("Geometric Success Before 5 Results: " + geometricSuccessBeforeNResults);
        System.out.println("Geometric Success On or Before 5 Results: " + geometricSuccessOnOrBeforeNResults);
        System.out.println("Geometric Success After 5 Results: " + geometricSuccessAfterNResults);
        
        // Calculate negative binomial distribution
        double negativeBinomialResults = test.negativeBinomialDistribution(10, 0.2, 3);
        System.out.println("Negative Binomial Distribution Results: " + negativeBinomialResults);
        
        // Calculate Poisson Distribution
        double poissonDistributionResults = test.poissonDistribution(3, 2.5);
        System.out.println("Poisson Distribution Results: " + poissonDistributionResults);
        
        // Calculate Chebyshev's Theorem
        double chebyshevResult = test.chebyshevTheoreom(151, 14, 123, 179);
        System.out.println("Chebyshev's Theorem Results: " + chebyshevResult);
    }
}
