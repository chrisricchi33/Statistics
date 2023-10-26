import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;

// Chris Ricchi
// 9-8-23
// Stats Library

public class StatsLibrary 
{
	// Mean
	public double findMean(ArrayList<Double> userInputNumbers) 
	{
		double sum = 0;
		
		for (double singleElement : userInputNumbers) {
			sum = sum + singleElement;
		}
		
		// Storing a value for clarity
		double result = sum / userInputNumbers.size();
		
		return result;
	}
	
	// Median
	public double findMedian(ArrayList<Double> userInputNumbers)
	{
		// Sort the ArrayList
		Collections.sort(userInputNumbers);

        int size = userInputNumbers.size();

        // Check if the size is even or odd
        if (size % 2 == 0) 
        {
        	// If even, average the two middle values
        	double m1 = userInputNumbers.get((size - 1) / 2);
            double m2 = userInputNumbers.get(size / 2);
            return (m1 + m2) / 2.0;
        } 

        // If odd, return the middle value
        else 
        	return userInputNumbers.get(size / 2);
	}
	
	// Mode
	public double findMode(ArrayList<Double> userInputNumbers)
	{
		// Start the mode at the first number in the list and track frequency
		double mode = userInputNumbers.get(0);
        int maxCount = 0;

        // Iterate the list
        for (int i = 0; i < userInputNumbers.size(); i++) 
        {
            double num = userInputNumbers.get(i);
            
            // Count can start at 1 because we know there's at least one occurrence
            int count = 1;

            // Use a nested for loop to compare our current number with the others in the list
            for (int j = i + 1; j < userInputNumbers.size(); j++) 
            {
            	// If we find a match, we can increment the count
                if (userInputNumbers.get(j).equals(num))
                    count++;
            }

            // If the count for the current number is greater than the max count
            if (count > maxCount) 
            {
                maxCount = count;
                
                // This number would be the new mode because it has a higher count
                mode = num;
            }
        }

        return mode;
	}
	
	// Standard Deviation
	public double findStandardDeviation(ArrayList<Double> userInputNumbers) 
	{
	    int n = userInputNumbers.size();
	    
	    // Calculate the mean of the list
	    double sum = 0.0;
	    for (double number : userInputNumbers)
	        sum += number;

	    double mean = sum / n;
	    
	    // Calculate the squared difference between each number and the mean
	    double squareSum = 0.0;
	    
	    for (double num : userInputNumbers) 
	    {
	        double d = num - mean;
	        squareSum += d * d;
	    }
	    
	    // Calculate the mean of those squared differences
	    double meanSqD = squareSum / (n - 1);
	    
	    return Math.sqrt(meanSqD);
	}
	
	// Find variance of a dataset
	public double findVariance(ArrayList<Double> data) 
	{
	    if (data == null || data.isEmpty())
	        throw new IllegalArgumentException("Data set is empty.");

	    // Find the mean and size of dataset
	    double mean = findMean(data);
	    int n = data.size();
	    double sumOfSquaredDifferences = 0.0;

	    // Calculate the sum of squared differences for each value in the dataset to find the variance
	    for (double value : data) 
	    {
	        double difference = value - mean;
	        sumOfSquaredDifferences += difference * difference;
	    }

	    return sumOfSquaredDifferences / n;
	}

	
	// Factorial
	public BigInteger findFactorial(int n) 
	{
		// Start at 1
        BigInteger result = BigInteger.ONE;
        
        // Multiply result by each integer from 1 to n
        for (int i = 1; i <= n; i++)
            result = result.multiply(BigInteger.valueOf(i));

        return result;
    }
	
	// Permutations
	public BigInteger findPermutation(int n, int r) 
	{
	    if (n < 0 || r < 0 || n < r)
	        throw new IllegalArgumentException("Permutation bounds invalid");
	    
	    // Calculate permutation formula ( n! / (n - r)! )
	    BigInteger numerator = findFactorial(n);
	    BigInteger denominator = findFactorial(n - r);
	    
	    return numerator.divide(denominator);
	}
	
	// Combinations
	public BigInteger findCombination(int n, int r) 
	{
	    if (n < 0 || r < 0 || n < r)
	        throw new IllegalArgumentException("Combination bounds invalid");
	    
	    // Calculate combination formula ( n! / (r! * (n - r)!) )
	    BigInteger numerator = findFactorial(n);
	    BigInteger denominator = findFactorial(r).multiply(findFactorial(n - r));
	    
	    return numerator.divide(denominator);
	}

	// Determine Correlation
	public double determineCorrelation(ArrayList<Double> x, ArrayList<Double> y) 
	{
        int n = x.size();

        // Calculate the means of x and y
        double mean_X = findMean(x);
        double mean_Y = findMean(y);

        // Calculate the sum of products of the differences
        double sum_of_XY = 0.0;
        double sum_of_X2 = 0.0;
        double sum_of_Y2 = 0.0;

        for (int i = 0; i < n; i++) 
        {
            double diffX = x.get(i) - mean_X;
            double diffY = y.get(i) - mean_Y;

            sum_of_XY += diffX * diffY;
            sum_of_X2 += diffX * diffX;
            sum_of_Y2 += diffY * diffY;
        }

        // Calculate the correlation coefficient
        return sum_of_XY / (Math.sqrt(sum_of_X2) * Math.sqrt(sum_of_Y2));
    }
	
	// Dependency Check
	public boolean isDependent(ArrayList<Double> x, ArrayList<Double> y, double threshold) 
	{
	    double correlationCoefficient = determineCorrelation(x, y);
	    return Math.abs(correlationCoefficient) >= threshold;
	}
	
	// Find Union of two datasets
	public ArrayList<Double> findUnion(ArrayList<Double> list1, ArrayList<Double> list2) 
	{
	    ArrayList<Double> union = new ArrayList<>(list1);
	    
	    // Check for unique elements from both lists
	    for (Double element : list2) 
	        if (!union.contains(element))
	            union.add(element);
	    
	    return union;
	}

	// Find Intersection of two datasets
	public ArrayList<Double> findIntersection(ArrayList<Double> list1, ArrayList<Double> list2) 
	{
	    ArrayList<Double> intersection = new ArrayList<>();
	    
	    // Find elements that are in both lists
	    for (Double element : list1) 
	        if (list2.contains(element))
	            intersection.add(element);
	    
	    return intersection;
	}
	
	// Find Compliment of two datasets
	public ArrayList<Double> findComplement(ArrayList<Double> list1, ArrayList<Double> list2) 
	{
	    ArrayList<Double> complement = new ArrayList<>(list1);
	    
	    // Remove all of the elements from list2 that are in list1
	    complement.removeAll(list2);
	    
	    return complement;
	}

	// Conditional Probability
	public double conditionalProbability(double probabilityAandB, double probabilityB) 
	{
	    if (probabilityB == 0)
	        throw new IllegalArgumentException("Invalid probability for B.");

	    return probabilityAandB / probabilityB;
	}

	// Baye's Theorem
	public double bayesTheorem(double BgivenA, double priorA, double priorB) 
	{
	    if (priorB == 0)
	        throw new IllegalArgumentException("Invalid probability for B.");

	    return (BgivenA * priorA) / priorB;
	}

    // Binomial Distribution
    public double binomialDistribution(int n, int x, double p, double q) 
    {
        if (n < 0 || x < 0 || n < x || p < 0 || p > 1 || q < 0 || q > 1)
            throw new IllegalArgumentException("Invalid Binomial Distribution bounds.");

        // Calculate binomial distribution formula ( (n choose x) * p^x * q^(n - x) )
        BigInteger combinations = findCombination(n, x);
        double probability = combinations.doubleValue() * Math.pow(p, x) * Math.pow(q, n - x);

        return probability;
    }
    
    // Geometric Distribution for success on or before nth trial
    public double geometricOnOrBeforeN(int n, double p) 
    {
    	if (n < 1 || p < 0 || p > 1)
            throw new IllegalArgumentException("Invalid geometric bounds");
        
    	// Return the answer based on the formula inputs
        return 1.0 - Math.pow(1.0 - p, n);
    }

    // Geometric Distribution for success before the nth trial
    public double geometricSuccessBeforeN(int n, double p) 
    {
    	if (n < 1 || p < 0 || p > 1)
            throw new IllegalArgumentException("Invalid geometric bounds");
    	
    	// Return the answer based on the formula inputs
        return 1.0 - Math.pow(1.0 - p, n - 1);
    }
    
    // Geometric Distribution for success on or after the nth trial
    public double geometricSuccessOnOrBeforeN(int n, double p) 
    {
    	if (n < 1 || p < 0 || p > 1)
            throw new IllegalArgumentException("Invalid geometric bounds");
    	
    	// Return the answer based on the formula inputs
        return Math.pow(1.0 - p, n - 1);
    }

    // Geometric Distribution for success after the nth trial
    public double geometricSuccessAfterN(int n, double p) 
    {
    	if (n < 1 || p < 0 || p > 1)
            throw new IllegalArgumentException("Invalid geometric bounds");
    	
    	// Return the answer based on the formula inputs
        return Math.pow(1.0 - p, n);
    }

    // Hypergeometric Distribution ((r choose y)((N-r)/(n-y) choose (N/n))
    public double hypergeometricDistribution(int y, int N, int r, int n) 
    {
    	if (y < 0 || r < 0 || n < 0 || N < 0 || r > N || y > r || n > N)
            throw new IllegalArgumentException("Invalid hypergeometric distribution bounds.");
        
    	// Calculate the numerator and denominator by using combinations based on the formula
        BigInteger numerator = findCombination(r, y).multiply(findCombination(N - r, n - y));
        BigInteger denominator = findCombination(N, n);

        // Return the answer as a fraction using doubleValue to convert from BigInteger to double
        return numerator.doubleValue() / denominator.doubleValue();
    }
    
    // Negative Binomial Probability Distribution ((y-1 choose r-1) * (p^r) * (q^(y-r)))
    public double negativeBinomialDistribution(int y, double p, int r) 
    {
    	if (y < r || p < 0 || p > 1 || r < 1)
            throw new IllegalArgumentException("Invalid negative binomial distribution bounds.");
    	
        // Calculate the negative binomial PMF: (y-1 choose r-1) * (p^r) * (q^(y-r))
        BigInteger numerator = findCombination(y - 1, r - 1);
        double q = 1.0 - p;

        // Return the answer based on the formula
        return numerator.doubleValue() * Math.pow(p, r) * Math.pow(q, y - r);
    }
    
    // Poisson Distribution ( P(X=x) = (lambda^x / x!) * e^-lambda
    public double poissonDistribution(int k, double lambda) 
    {
        if (k < 0 || lambda < 0)
            throw new IllegalArgumentException("Invalid poisson distribution bounds.");

        // Calculate each part of the Poisson Distribution theoreom
        double x1 = Math.exp(-lambda);
        double x2 = Math.pow(lambda, k);
        long x3 = findFactorial(k).longValue();

        // Return the answer based on the formula
        return x1 * x2 / x3;
    }

    // Chevyshev's Theorem
    public double chebyshevTheoreom(double mean, double stdDev, double lowerBound, double upperBound) 
    {
        if (stdDev <= 0 || upperBound <= lowerBound)
            throw new IllegalArgumentException("Invalid chebyshev bounds.");

        // Calculate the upper and lower bounds using the formula
        double x1 = (lowerBound - mean) / stdDev;
        double x2 = (upperBound - mean) / stdDev;

        // Calculate the values within k1 and k2 standard deviations
        double result = 1.0 - 1.0 / (Math.max(x1 * x1, x2 * x2));

        // Return result as a percentage as opposed to decimal
        return result * 100.0;
    }
}
