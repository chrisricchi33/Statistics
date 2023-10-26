import javax.swing.*;
import java.awt.*;
import java.math.BigInteger;
import java.util.ArrayList;

//Chris Ricchi
//9-8-23
//Stats Library GUI - This class creates a functional GUI for our Stats Library class and allows us to easily test and visualize all
// functions and results through an ApplicationWindow

public class StatsLibraryGUI 
{
	// Reference to the StatsLibrary class
    private StatsLibrary statsLibrary;

    // Constructor to initialize StatsLibrary object and show the GUI
    public StatsLibraryGUI() 
    {
        statsLibrary = new StatsLibrary();
        createAndShowGUI();
    }

    // Handle creating the GUI and displaying it
    private void createAndShowGUI() 
    {
        JFrame frame = new JFrame("Stats Library GUI by Chris Ricchi");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //frame.setResizable(false); // Leaving off for now so users can re-size it

        JPanel buttonPanel = new JPanel(new GridLayout(0, 2, 5, 5));

        // Array of buttons for each function with custom colors and borders
        JButton[] buttons = 
        {
            createStyledButton("Find Mean", Color.LIGHT_GRAY),
            createStyledButton("Find Median", Color.RED),
            createStyledButton("Find Mode", Color.GREEN),
            createStyledButton("Find Standard Deviation", Color.ORANGE),
            createStyledButton("Find Variance", Color.CYAN),
            createStyledButton("Find Factorial", Color.PINK),
            createStyledButton("Find Permutation", Color.MAGENTA),
            createStyledButton("Find Combination", Color.YELLOW),
            createStyledButton("Determine Correlation", Color.LIGHT_GRAY),
            createStyledButton("Is Dependent?", Color.GRAY),
            createStyledButton("Find Union", Color.MAGENTA),
            createStyledButton("Find Intersection", Color.BLUE),
            createStyledButton("Find Compliment", Color.RED),
            createStyledButton("Conditional Probability", Color.GREEN),
            createStyledButton("Bayes' Theorem", Color.ORANGE),
            createStyledButton("Binomial Distribution", Color.CYAN),
            createStyledButton("Geometric On or Before N", Color.PINK),
            createStyledButton("Geometric Success Before N", Color.MAGENTA),
            createStyledButton("Geometric On or After N", Color.YELLOW),
            createStyledButton("Geometric Success After N", Color.LIGHT_GRAY),
            createStyledButton("Hypergeometric Distribution", Color.GRAY),
            createStyledButton("Negative Binomial Distribution", Color.GREEN),
            createStyledButton("Poisson Distribution", Color.BLUE),
            createStyledButton("Chebyshev's Theorem", Color.RED)
        };

        // For each loop to add each button to the panel with the correct handleClick event (see method below)
        for (int i = 0; i < buttons.length; i++) {
            int finalI = i;
            buttons[i].addActionListener(e -> handleButtonClick(finalI));
            buttonPanel.add(buttons[i]);
        }

        // GUI customization options
        frame.add(buttonPanel);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(600, 600);
    }

    // Handle button clicks and call the correct methods
    private void handleButtonClick(int index) 
    {
        switch (index) 
        {
            case 0:
                showMeanInputDialog();
                break;
            case 1:
                showMedianInputDialog();
                break;
            case 2:
                showModeInputDialog();
                break;
            case 3:
                showStandardDeviationInputDialog();
                break;
            case 4:
                showVarianceInputDialog();
                break;
            case 5:
                showFactorialInputDialog();
                break;
            case 6:
                showPermutationInputDialog();
                break;
            case 7:
                showCombinationInputDialog();
                break;
            case 8:
                showCorrelationInputDialog();
                break;
            case 9:
                showDependencyInputDialog();
                break;
            case 10:
                showUnionInputDialog();
                break;
            case 11:
                showIntersectionInputDialog();
                break;
            case 12:
                showComplimentInputDialog();
                break;
            case 13:
                showConditionalProbabilityInputDialog();
                break;
            case 14:
                showBayesTheoremInputDialog();
                break;
            case 15:
                showBinomialDistributionInputDialog();
                break;
            case 16:
                showGeometricOnOrBeforeNInputDialog();
                break;
            case 17:
                showGeometricSuccessBeforeNInputDialog();
                break;
            case 18:
            	showGeometricSuccessOnOrBeforeN();
                break;
            case 19:
                showGeometricSuccessAfterNInputDialog();
                break;
            case 20:
                showHypergeometricDistributionInputDialog();
                break;
            case 21:
                showNegativeBinomialDistributionInputDialog();
                break;
            case 22:
                showPoissonDistributionInputDialog();
                break;
            case 23:
                showChebyshevTheoremInputDialog();
                break;
            default:
                break;
        }
    }

    // Create styled buttons with colors and borders
    private JButton createStyledButton(String label, Color color) 
    {
        JButton button = new JButton(label);
        button.setBackground(color);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        return button;
    }


    // Call Mean method and display
    private void showMeanInputDialog() 
    {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(1, 2));
        JTextField numbersField = new JTextField(20);
        
        // Get user input
        inputPanel.add(new JLabel("Enter numbers (comma-separated): "));
        inputPanel.add(numbersField);

        int result = JOptionPane.showConfirmDialog(null, inputPanel, "Enter Numbers", JOptionPane.OK_CANCEL_OPTION);
        
        if (result == JOptionPane.OK_OPTION) 
        {
        	// Get user input
            String[] numberStrings = numbersField.getText().split(",");
            ArrayList<Double> numbers = new ArrayList<>();
            
            for (String numberString : numberStrings) 
            {
                try {
                    numbers.add(Double.parseDouble(numberString.trim()));
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid user input, please make sure your numbers are valid.", "User Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Call the method and display the results
            double mean = statsLibrary.findMean(numbers);
            displayResult("Mean", mean);
        }
    }

	// Call Median method and display
    private void showMedianInputDialog() 
    {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(1, 2));
        
        // Get user input
        JTextField numbersField = new JTextField(20);
        inputPanel.add(new JLabel("Enter numbers (comma-separated): "));
        inputPanel.add(numbersField);

        int result = JOptionPane.showConfirmDialog(null, inputPanel, "Enter Numbers", JOptionPane.OK_CANCEL_OPTION);
        
        if (result == JOptionPane.OK_OPTION) 
        {
        	// Get user input
            String[] numberStrings = numbersField.getText().split(",");
            ArrayList<Double> numbers = new ArrayList<>();
            for (String numberString : numberStrings) 
            {
                try {
                    numbers.add(Double.parseDouble(numberString.trim()));
                } catch (NumberFormatException e) {
                	JOptionPane.showMessageDialog(null, "Invalid user input, please make sure your numbers are valid.", "User Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Call the method and display the results
            double median = statsLibrary.findMedian(numbers);
            displayResult("Median", median);
        }
    }

	// Call Mode method and display
    private void showModeInputDialog() 
    {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(1, 2));
        JTextField numbersField = new JTextField(20);
        
        // Get user input
        inputPanel.add(new JLabel("Enter numbers (comma-separated): "));
        inputPanel.add(numbersField);

        int result = JOptionPane.showConfirmDialog(null, inputPanel, "Enter Numbers", JOptionPane.OK_CANCEL_OPTION);
        
        if (result == JOptionPane.OK_OPTION) 
        {
        	// Get user input
            String[] numberStrings = numbersField.getText().split(",");
            ArrayList<Double> numbers = new ArrayList<>();
            
            for (String numberString : numberStrings) 
            {
                try {
                    numbers.add(Double.parseDouble(numberString.trim()));
                } catch (NumberFormatException e) {
                	JOptionPane.showMessageDialog(null, "Invalid user input, please make sure your numbers are valid.", "User Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Call the method and display the results
            double mode = statsLibrary.findMode(numbers);
            displayResult("Mode", mode);
        }
    }

	// Call Standard Deviation method and display
    private void showStandardDeviationInputDialog() 
    {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(1, 2));
        JTextField numbersField = new JTextField(20);
        
        // Get user input
        inputPanel.add(new JLabel("Enter numbers (comma-separated): "));
        inputPanel.add(numbersField);

        int result = JOptionPane.showConfirmDialog(null, inputPanel, "Enter Numbers", JOptionPane.OK_CANCEL_OPTION);
        
        if (result == JOptionPane.OK_OPTION) 
        {
        	// Get user input
            String[] numberStrings = numbersField.getText().split(",");
            ArrayList<Double> numbers = new ArrayList<>();
            
            for (String numberString : numberStrings) 
            {
                try {
                    numbers.add(Double.parseDouble(numberString.trim()));
                } catch (NumberFormatException e) {
                	JOptionPane.showMessageDialog(null, "Invalid user input, please make sure your numbers are valid.", "User Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Call the method and display the results
            double stdDev = statsLibrary.findStandardDeviation(numbers);
            displayResult("Standard Deviation", stdDev);
        }
    }

	// Call Variance method and display
    private void showVarianceInputDialog() 
    {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(1, 2));
        
        // Get user input
        JTextField numbersField = new JTextField(20);
        inputPanel.add(new JLabel("Enter numbers (comma-separated): "));
        inputPanel.add(numbersField);

        int result = JOptionPane.showConfirmDialog(null, inputPanel, "Enter Numbers", JOptionPane.OK_CANCEL_OPTION);
        
        if (result == JOptionPane.OK_OPTION) 
        {
        	// Get user input
            String[] numberStrings = numbersField.getText().split(",");
            ArrayList<Double> numbers = new ArrayList<>();
            
            for (String numberString : numberStrings) 
            {
                try {
                    numbers.add(Double.parseDouble(numberString.trim()));
                } catch (NumberFormatException e) {
                	JOptionPane.showMessageDialog(null, "Invalid user input, please make sure your numbers are valid.", "User Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Call the method and display the results
            double variance = statsLibrary.findVariance(numbers);
            displayResult("Variance", variance);
        }
    }

	// Call Factorial method and display
    private void showFactorialInputDialog() 
    {
        JTextField nField = new JTextField(10);
        int result = JOptionPane.showConfirmDialog(null, nField, "Enter n for Factorial", JOptionPane.OK_CANCEL_OPTION);
        
        if (result == JOptionPane.OK_OPTION) 
        {
            try 
            {
            	// Get user input
                int n = Integer.parseInt(nField.getText());

                // Calculate factorial using our method and display result
                BigInteger factorial = statsLibrary.findFactorial(n);
                displayResult("Factorial", factorial);
                
            } catch (NumberFormatException e) {
            	JOptionPane.showMessageDialog(null, "Invalid user input, please make sure your numbers are valid.", "User Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

	// Call Permutations method and display
    private void showPermutationInputDialog() 
    {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2));
        JTextField nField = new JTextField(10);
        JTextField rField = new JTextField(10);
        
        inputPanel.add(new JLabel("Enter n: "));
        inputPanel.add(nField);
        inputPanel.add(new JLabel("Enter r: "));
        inputPanel.add(rField);

        int result = JOptionPane.showConfirmDialog(null, inputPanel, "Enter n and r for Permutation", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) 
        {
            try 
            {
            	// Get user input
                int n = Integer.parseInt(nField.getText());
                int r = Integer.parseInt(rField.getText());

                // Call the method and display the results
                BigInteger permutation = statsLibrary.findPermutation(n, r);
                displayResult("Permutation", permutation);
                
            } catch (NumberFormatException e) {
            	JOptionPane.showMessageDialog(null, "Invalid user input, please make sure your numbers are valid.", "User Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

	// Call Combinations method and display
    private void showCombinationInputDialog() 
    {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2));
        JTextField nField = new JTextField(10);
        JTextField rField = new JTextField(10);
        
        inputPanel.add(new JLabel("Enter n: "));
        inputPanel.add(nField);
        inputPanel.add(new JLabel("Enter r: "));
        inputPanel.add(rField);

        int result = JOptionPane.showConfirmDialog(null, inputPanel, "Enter n and r for Combination", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) 
        {
            try 
            {
            	// Get user input
                int n = Integer.parseInt(nField.getText());
                int r = Integer.parseInt(rField.getText());

                // Call the method and display the results
                BigInteger combination = statsLibrary.findCombination(n, r);
                displayResult("Combination", combination);
                
            } catch (NumberFormatException e) {
            	JOptionPane.showMessageDialog(null, "Invalid user input, please make sure your numbers are valid.", "User Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

	// Call Correlations method and display
    private void showCorrelationInputDialog() 
    {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2));

        JTextField xField = new JTextField(10);
        JTextField yField = new JTextField(10);

        inputPanel.add(new JLabel("Enter numbers for X (comma-separated): "));
        inputPanel.add(xField);
        inputPanel.add(new JLabel("Enter numbers for Y (comma-separated): "));
        inputPanel.add(yField);

        int result = JOptionPane.showConfirmDialog(null, inputPanel, "Determine Correlation", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) 
        {
        	// Get user input
            String[] xNumberStrings = xField.getText().split(",");
            String[] yNumberStrings = yField.getText().split(",");
            
            // Create new lists for each
            ArrayList<Double> xNumbers = new ArrayList<>();
            ArrayList<Double> yNumbers = new ArrayList<>();

            for (String numberString : xNumberStrings) 
            {
                try 
                {
                	//Get user input
                    xNumbers.add(Double.parseDouble(numberString.trim()));
                } catch (NumberFormatException e) {
                	JOptionPane.showMessageDialog(null, "Invalid user input for X, please make sure your numbers are valid.", "User Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            for (String numberString : yNumberStrings) 
            {
                try 
                {
                	// Get user input
                    yNumbers.add(Double.parseDouble(numberString.trim()));
                } catch (NumberFormatException e) {
                	JOptionPane.showMessageDialog(null, "Invalid user input for Y, please make sure your numbers are valid.", "User Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Call the method and display the results
            double correlation = statsLibrary.determineCorrelation(xNumbers, yNumbers);
            displayResult("Correlation Coefficient", correlation);
        }
    }


	// Call Dependency method and display
    private void showDependencyInputDialog() 
    {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2));

        JTextField xField = new JTextField(10);
        JTextField yField = new JTextField(10);
        JTextField thresholdField = new JTextField(10);

        inputPanel.add(new JLabel("Enter numbers for X (comma-separated): "));
        inputPanel.add(xField);
        inputPanel.add(new JLabel("Enter numbers for Y (comma-separated): "));
        inputPanel.add(yField);
        inputPanel.add(new JLabel("Enter the correlation threshold: "));
        inputPanel.add(thresholdField);

        int result = JOptionPane.showConfirmDialog(null, inputPanel, "Is Dependent?", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) 
        {
        	// Get user input
            String[] xNumberStrings = xField.getText().split(",");
            String[] yNumberStrings = yField.getText().split(",");

            // Create new lists for each
            ArrayList<Double> xNumbers = new ArrayList<>();
            ArrayList<Double> yNumbers = new ArrayList<>();

            for (String numberString : xNumberStrings) 
            {
                try 
                {
                	// Get user input
                    xNumbers.add(Double.parseDouble(numberString.trim()));
                } catch (NumberFormatException e) {
                	JOptionPane.showMessageDialog(null, "Invalid user input for X, please make sure your numbers are valid.", "User Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            for (String numberString : yNumberStrings)
            {
                try 
                {
                	// Get user input
                    yNumbers.add(Double.parseDouble(numberString.trim()));
                } catch (NumberFormatException e) {
                	JOptionPane.showMessageDialog(null, "Invalid user input for Y, please make sure your numbers are valid.", "User Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            double threshold;
            try 
            {
            	// Get user input
                threshold = Double.parseDouble(thresholdField.getText().trim());
            } catch (NumberFormatException e) {
            	JOptionPane.showMessageDialog(null, "Invalid user input, please make sure your numbers are valid.", "User Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Check dependency using our method and display the results
            boolean dependent = statsLibrary.isDependent(xNumbers, yNumbers, threshold);
            String resultMessage = dependent ? "The datasets are dependent." : "The datasets are not dependent.";
            JOptionPane.showMessageDialog(null, resultMessage, "Dependency Result", JOptionPane.INFORMATION_MESSAGE);
        }
    }


	// Call Union method and display
    private void showUnionInputDialog() 
    {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2));

        JTextField list1Field = new JTextField(20);
        JTextField list2Field = new JTextField(20);

        inputPanel.add(new JLabel("Enter numbers for List 1 (comma-separated): "));
        inputPanel.add(list1Field);
        inputPanel.add(new JLabel("Enter numbers for List 2 (comma-separated): "));
        inputPanel.add(list2Field);

        int result = JOptionPane.showConfirmDialog(null, inputPanel, "Find Union", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) 
        {
        	// Get user input
            String[] list1NumberStrings = list1Field.getText().split(",");
            String[] list2NumberStrings = list2Field.getText().split(",");
            
            // Create new lists for each
            ArrayList<Double> list1 = new ArrayList<>();
            ArrayList<Double> list2 = new ArrayList<>();

            for (String numberString : list1NumberStrings) 
            {
                try 
                {
                	// Get user input
                    list1.add(Double.parseDouble(numberString.trim()));
                } catch (NumberFormatException e) {
                	JOptionPane.showMessageDialog(null, "Invalid user input for List 1, please make sure your numbers are valid.", "User Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            for (String numberString : list2NumberStrings) 
            {
                try 
                {
                	// Get user input
                    list2.add(Double.parseDouble(numberString.trim()));
                } catch (NumberFormatException e) {
                	JOptionPane.showMessageDialog(null, "Invalid user input for List 2, please make sure your numbers are valid.", "User Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Call the method and display the results
            ArrayList<Double> union = statsLibrary.findUnion(list1, list2);
            displayResult("Union of Lists", union);
        }
    }


	// Call Intersection method and display
    private void showIntersectionInputDialog() 
    {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2));

        JTextField list1Field = new JTextField(20);
        JTextField list2Field = new JTextField(20);

        inputPanel.add(new JLabel("Enter numbers for List 1 (comma-separated): "));
        inputPanel.add(list1Field);
        inputPanel.add(new JLabel("Enter numbers for List 2 (comma-separated): "));
        inputPanel.add(list2Field);

        int result = JOptionPane.showConfirmDialog(null, inputPanel, "Find Intersection", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) 
        {
        	// Get user input
            String[] list1NumberStrings = list1Field.getText().split(",");
            String[] list2NumberStrings = list2Field.getText().split(",");

            // Create new lists for each
            ArrayList<Double> list1 = new ArrayList<>();
            ArrayList<Double> list2 = new ArrayList<>();

            for (String numberString : list1NumberStrings) 
            {
                try 
                {
                	// Get user input
                    list1.add(Double.parseDouble(numberString.trim()));
                } catch (NumberFormatException e) {
                	JOptionPane.showMessageDialog(null, "Invalid user input for List 1, please make sure your numbers are valid.", "User Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            for (String numberString : list2NumberStrings) 
            {
                try 
                {
                	// Get user input
                    list2.add(Double.parseDouble(numberString.trim()));
                } catch (NumberFormatException e) {
                	JOptionPane.showMessageDialog(null, "Invalid user input for List 2, please make sure your numbers are valid.", "User Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Call the method and display the results
            ArrayList<Double> intersection = statsLibrary.findIntersection(list1, list2);
            displayResult("Intersection of Lists", intersection);
        }
    }

	// Call Compliment method and display
    private void showComplimentInputDialog() 
    {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2));

        JTextField list1Field = new JTextField(20);
        JTextField list2Field = new JTextField(20);

        inputPanel.add(new JLabel("Enter numbers for List 1 (comma-separated): "));
        inputPanel.add(list1Field);
        inputPanel.add(new JLabel("Enter numbers for List 2 (comma-separated): "));
        inputPanel.add(list2Field);

        int result = JOptionPane.showConfirmDialog(null, inputPanel, "Find Compliment", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) 
        {
        	// Get user input
            String[] list1NumberStrings = list1Field.getText().split(",");
            String[] list2NumberStrings = list2Field.getText().split(",");
            
            // Create new lists for each
            ArrayList<Double> list1 = new ArrayList<>();
            ArrayList<Double> list2 = new ArrayList<>();

            for (String numberString : list1NumberStrings) 
            {
                try 
                {
                	// Get user input
                    list1.add(Double.parseDouble(numberString.trim()));
                } catch (NumberFormatException e) {
                	JOptionPane.showMessageDialog(null, "Invalid user input for List 1, please make sure your numbers are valid.", "User Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            for (String numberString : list2NumberStrings) 
            {
                try 
                {
                	// Get user input
                    list2.add(Double.parseDouble(numberString.trim()));
                } catch (NumberFormatException e) {
                	JOptionPane.showMessageDialog(null, "Invalid user input for List 2, please make sure your numbers are valid.", "User Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Call the method and display the results
            ArrayList<Double> complement = statsLibrary.findComplement(list1, list2);
            displayResult("Complement of Lists", complement);
        }
    }

	// Call Conditional Probability method and display
    private void showConditionalProbabilityInputDialog() 
    {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2));

        JTextField probabilityAandBField = new JTextField(10);
        JTextField probabilityBField = new JTextField(10);

        inputPanel.add(new JLabel("Enter P(A and B): "));
        inputPanel.add(probabilityAandBField);
        inputPanel.add(new JLabel("Enter P(B): "));
        inputPanel.add(probabilityBField);

        int result = JOptionPane.showConfirmDialog(null, inputPanel, "Conditional Probability", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) 
        {
            double probabilityAandB;
            double probabilityB;

            try 
            {
            	// Get user input
                probabilityAandB = Double.parseDouble(probabilityAandBField.getText().trim());
                probabilityB = Double.parseDouble(probabilityBField.getText().trim());
            } catch (NumberFormatException e) {
            	JOptionPane.showMessageDialog(null, "Invalid user input, please make sure your numbers are valid.", "User Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Call the method and display the results
            double conditionalProbability = statsLibrary.conditionalProbability(probabilityAandB, probabilityB);
            displayResult("Conditional Probability", conditionalProbability);
        }
    }

	// Call Baye's Theoreom method and display
    private void showBayesTheoremInputDialog() 
    {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2));

        JTextField BgivenAField = new JTextField(10);
        JTextField priorAField = new JTextField(10);
        JTextField priorBField = new JTextField(10);

        inputPanel.add(new JLabel("Enter P(B|A): "));
        inputPanel.add(BgivenAField);
        inputPanel.add(new JLabel("Enter P(A): "));
        inputPanel.add(priorAField);
        inputPanel.add(new JLabel("Enter P(B): "));
        inputPanel.add(priorBField);

        int result = JOptionPane.showConfirmDialog(null, inputPanel, "Bayes' Theorem", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) 
        {
            double BgivenA;
            double priorA;
            double priorB;

            try 
            {
            	// Get user input
                BgivenA = Double.parseDouble(BgivenAField.getText().trim());
                priorA = Double.parseDouble(priorAField.getText().trim());
                priorB = Double.parseDouble(priorBField.getText().trim());
            } catch (NumberFormatException e) {
            	JOptionPane.showMessageDialog(null, "Invalid user input, please make sure your numbers are valid.", "User Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Call the method and display the results
            double bayesResult = statsLibrary.bayesTheorem(BgivenA, priorA, priorB);
            displayResult("Bayes' Theorem Result", bayesResult);
        }
    }

	// Call Binomial Distribution method and display
    private void showBinomialDistributionInputDialog() 
    {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2));

        JTextField nField = new JTextField(10);
        JTextField xField = new JTextField(10);
        JTextField pField = new JTextField(10);
        JTextField qField = new JTextField(10);

        inputPanel.add(new JLabel("Enter n: "));
        inputPanel.add(nField);
        inputPanel.add(new JLabel("Enter x: "));
        inputPanel.add(xField);
        inputPanel.add(new JLabel("Enter p (probability of success): "));
        inputPanel.add(pField);
        inputPanel.add(new JLabel("Enter q (probability of failure): "));
        inputPanel.add(qField);

        int result = JOptionPane.showConfirmDialog(null, inputPanel, "Binomial Distribution", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) 
        {
            int n;
            int x;
            double p;
            double q;

            try
            {
            	// Get user input
                n = Integer.parseInt(nField.getText().trim());
                x = Integer.parseInt(xField.getText().trim());
                p = Double.parseDouble(pField.getText().trim());
                q = Double.parseDouble(qField.getText().trim());
            } catch (NumberFormatException e) {
            	JOptionPane.showMessageDialog(null, "Invalid user input, please make sure your numbers are valid.", "User Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Call the method and display the results
            double binomialResult = statsLibrary.binomialDistribution(n, x, p, q);
            displayResult("Binomial Distribution Result", binomialResult);
        }
    }

	// Call Geometric OnOrBeforeN method and display
    private void showGeometricOnOrBeforeNInputDialog() 
    {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2));

        JTextField nField = new JTextField(10);
        JTextField pField = new JTextField(10);

        inputPanel.add(new JLabel("Enter n: "));
        inputPanel.add(nField);
        inputPanel.add(new JLabel("Enter p (probability of success): "));
        inputPanel.add(pField);

        int result = JOptionPane.showConfirmDialog(null, inputPanel, "Geometric On or Before N", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) 
        {
            int n;
            double p;

            try 
            {
            	// Get user input
                n = Integer.parseInt(nField.getText().trim());
                p = Double.parseDouble(pField.getText().trim());
            } catch (NumberFormatException e) {
            	JOptionPane.showMessageDialog(null, "Invalid user input, please make sure your numbers are valid.", "User Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Call the method and display the results
            double geometricResult = statsLibrary.geometricOnOrBeforeN(n, p);
            displayResult("Geometric On or Before N Result", geometricResult);
        }
    }

    // Call Geometric SuccessBeforeN method and display
    private void showGeometricSuccessBeforeNInputDialog() 
    {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2));

        JTextField nField = new JTextField(10);
        JTextField pField = new JTextField(10);

        inputPanel.add(new JLabel("Enter n: "));
        inputPanel.add(nField);
        inputPanel.add(new JLabel("Enter p (probability of success): "));
        inputPanel.add(pField);

        int result = JOptionPane.showConfirmDialog(null, inputPanel, "Geometric Success Before N", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) 
        {
            int n;
            double p;

            try 
            {
            	// Get user input
                n = Integer.parseInt(nField.getText().trim());
                p = Double.parseDouble(pField.getText().trim());
            } catch (NumberFormatException e) {
            	JOptionPane.showMessageDialog(null, "Invalid user input, please make sure your numbers are valid.", "User Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Call the method and display the results
            double geometricResult = statsLibrary.geometricSuccessBeforeN(n, p);
            displayResult("Geometric Success Before N Result", geometricResult);
        }
    }

    // Call Geometric SuccessOnOrBeforeN method and display
    private void showGeometricSuccessOnOrBeforeN() 
    {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2));

        JTextField nField = new JTextField(10);
        JTextField pField = new JTextField(10);

        inputPanel.add(new JLabel("Enter n: "));
        inputPanel.add(nField);
        inputPanel.add(new JLabel("Enter p (probability of success): "));
        inputPanel.add(pField);

        int result = JOptionPane.showConfirmDialog(null, inputPanel, "Geometric On or After N", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) 
        {
            int n;
            double p;

            try 
            {
            	// Get user input
                n = Integer.parseInt(nField.getText().trim());
                p = Double.parseDouble(pField.getText().trim());
            } catch (NumberFormatException e) {
            	JOptionPane.showMessageDialog(null, "Invalid user input, please make sure your numbers are valid.", "User Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Call the method and display the results
            double geometricResult = statsLibrary.geometricSuccessOnOrBeforeN(n, p);
            displayResult("Geometric On or After N Result", geometricResult);
        }
    }

    // Call Geometric SuccessAfterN method and display
    private void showGeometricSuccessAfterNInputDialog() 
    {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2));

        JTextField nField = new JTextField(10);
        JTextField pField = new JTextField(10);

        inputPanel.add(new JLabel("Enter n: "));
        inputPanel.add(nField);
        inputPanel.add(new JLabel("Enter p (probability of success): "));
        inputPanel.add(pField);

        int result = JOptionPane.showConfirmDialog(null, inputPanel, "Geometric Success After N", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) 
        {
            int n;
            double p;

            try 
            {
            	// Get user input
                n = Integer.parseInt(nField.getText().trim());
                p = Double.parseDouble(pField.getText().trim());
            } catch (NumberFormatException e) {
            	JOptionPane.showMessageDialog(null, "Invalid user input, please make sure your numbers are valid.", "User Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Call the method and display the results
            double geometricResult = statsLibrary.geometricSuccessAfterN(n, p);
            displayResult("Geometric Success After N Result", geometricResult);
        }
    }

    // Call Hypergeometric Distribution method and display
    private void showHypergeometricDistributionInputDialog() 
    {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2));

        JTextField yField = new JTextField(10);
        JTextField NField = new JTextField(10);
        JTextField rField = new JTextField(10);
        JTextField nField = new JTextField(10);

        inputPanel.add(new JLabel("Enter y: "));
        inputPanel.add(yField);
        inputPanel.add(new JLabel("Enter N: "));
        inputPanel.add(NField);
        inputPanel.add(new JLabel("Enter r: "));
        inputPanel.add(rField);
        inputPanel.add(new JLabel("Enter n: "));
        inputPanel.add(nField);

        int result = JOptionPane.showConfirmDialog(null, inputPanel, "Hypergeometric Distribution", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) 
        {
            int y;
            int N;
            int r;
            int n;

            try 
            {
            	// Get user input
                y = Integer.parseInt(yField.getText().trim());
                N = Integer.parseInt(NField.getText().trim());
                r = Integer.parseInt(rField.getText().trim());
                n = Integer.parseInt(nField.getText().trim());
            } catch (NumberFormatException e) {
            	JOptionPane.showMessageDialog(null, "Invalid user input, please make sure your numbers are valid.", "User Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Call the method and display the results
            double hypergeometricResult = statsLibrary.hypergeometricDistribution(y, N, r, n);
            displayResult("Hypergeometric Distribution Result", hypergeometricResult);
        }
    }

    // Call Negative Binomial Distribution method and display
    private void showNegativeBinomialDistributionInputDialog() 
    {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2));

        JTextField yField = new JTextField(10);
        JTextField pField = new JTextField(10);
        JTextField rField = new JTextField(10);

        inputPanel.add(new JLabel("Enter y: "));
        inputPanel.add(yField);
        inputPanel.add(new JLabel("Enter p (probability of success): "));
        inputPanel.add(pField);
        inputPanel.add(new JLabel("Enter r: "));
        inputPanel.add(rField);

        int result = JOptionPane.showConfirmDialog(null, inputPanel, "Negative Binomial Distribution", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) 
        {
            int y;
            double p;
            int r;

            try 
            {
            	// Get user input
                y = Integer.parseInt(yField.getText().trim());
                p = Double.parseDouble(pField.getText().trim());
                r = Integer.parseInt(rField.getText().trim());
            } catch (NumberFormatException e) {
            	JOptionPane.showMessageDialog(null, "Invalid user input, please make sure your numbers are valid.", "User Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Call the method and display the results
            double negativeBinomialResult = statsLibrary.negativeBinomialDistribution(y, p, r);
            displayResult("Negative Binomial Distribution Result", negativeBinomialResult);
        }
    }

    // Call Poisson Distribution method and display
    private void showPoissonDistributionInputDialog() 
    {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2));

        JTextField kField = new JTextField(10);
        JTextField lambdaField = new JTextField(10);

        inputPanel.add(new JLabel("Enter k: "));
        inputPanel.add(kField);
        inputPanel.add(new JLabel("Enter lambda: "));
        inputPanel.add(lambdaField);

        int result = JOptionPane.showConfirmDialog(null, inputPanel, "Poisson Distribution", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) 
        {
            int k;
            double lambda;

            try 
            {
            	// Get user input
                k = Integer.parseInt(kField.getText().trim());
                lambda = Double.parseDouble(lambdaField.getText().trim());
            } catch (NumberFormatException e) {
            	JOptionPane.showMessageDialog(null, "Invalid user input, please make sure your numbers are valid.", "User Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Call the method and display the results
            double poissonResult = statsLibrary.poissonDistribution(k, lambda);
            displayResult("Poisson Distribution Result", poissonResult);
        }
    }

    // Call Chebyshev's Theorem method and display
    private void showChebyshevTheoremInputDialog() 
    {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2));

        JTextField meanField = new JTextField(10);
        JTextField stdDevField = new JTextField(10);
        JTextField lowerBoundField = new JTextField(10);
        JTextField upperBoundField = new JTextField(10);

        inputPanel.add(new JLabel("Enter mean: "));
        inputPanel.add(meanField);
        inputPanel.add(new JLabel("Enter standard deviation: "));
        inputPanel.add(stdDevField);
        inputPanel.add(new JLabel("Enter lower bound: "));
        inputPanel.add(lowerBoundField);
        inputPanel.add(new JLabel("Enter upper bound: "));
        inputPanel.add(upperBoundField);

        int result = JOptionPane.showConfirmDialog(null, inputPanel, "Chebyshev's Theorem", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) 
        {
            double mean;
            double stdDev;
            double lowerBound;
            double upperBound;

            try 
            {
            	// Get user input
                mean = Double.parseDouble(meanField.getText().trim());
                stdDev = Double.parseDouble(stdDevField.getText().trim());
                lowerBound = Double.parseDouble(lowerBoundField.getText().trim());
                upperBound = Double.parseDouble(upperBoundField.getText().trim());
            } catch (NumberFormatException e) {
            	JOptionPane.showMessageDialog(null, "Invalid user input, please make sure your numbers are valid.", "User Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Call the method and display the results
            double chebyshevResult = statsLibrary.chebyshevTheoreom(mean, stdDev, lowerBound, upperBound);
            displayResult("Chebyshev's Theorem Result", chebyshevResult);
        }
    }

    // Method to display the result, referencing the function name and the result
    private void displayResult(String functionName, Object result) {
        JOptionPane.showMessageDialog(null, functionName + ": " + result, "Result", JOptionPane.INFORMATION_MESSAGE);
    }

    // Main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StatsLibraryGUI());
    }
}
