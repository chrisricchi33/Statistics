import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//Chris Ricchi
//12-1-23
//StockTradingSimulatorGUI

public class StockTradingSimulatorGUI extends JFrame 
{

	// Initialize GUI elements
    private JTextArea logTextArea;
    private JTextField balanceTextField;
    private JComboBox<String> heuristicComboBox;
    private List<StockData> loadedStockDataList;

    // Constructor to initialize GUI
    public StockTradingSimulatorGUI() 
    {
        setTitle("Stock Trading Simulator");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComponents();
    }

    // Init method to initialize GUI elements
    private void initComponents() 
    {
        // Content pane to keep everything organized
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        // Create a custom font and use it on the buttons
        Font customFont = new Font("Arial", Font.PLAIN, 14);
        UIManager.put("Button.font", customFont);
        UIManager.put("Label.font", customFont);
        UIManager.put("TextField.font", customFont);
        UIManager.put("ComboBox.font", customFont);

        // Create a panel for the top UI elements
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton loadCsvButton = new JButton("Load CSV");
        loadCsvButton.addActionListener(new LoadCsvActionListener());
        topPanel.add(loadCsvButton);

        JLabel balanceLabel = new JLabel("Starting Balance:");
        balanceTextField = new JTextField("10000", 10);
        topPanel.add(balanceLabel);
        topPanel.add(balanceTextField);

        JLabel heuristicLabel = new JLabel("Heuristic:");
        String[] heuristicOptions = {"RSI + MA", "Buy and Hold", "Trend Following"};
        heuristicComboBox = new JComboBox<>(heuristicOptions);
        topPanel.add(heuristicLabel);
        topPanel.add(heuristicComboBox);

        JButton beginEvaluationButton = new JButton("Begin Evaluation");
        beginEvaluationButton.addActionListener(new BeginEvaluationActionListener());
        topPanel.add(beginEvaluationButton);

        // Add the top panel to the contentPane
        contentPane.add(topPanel, BorderLayout.NORTH);

        // Create a panel for the log area to see the outputs
        logTextArea = new JTextArea();
        logTextArea.setEditable(false);
        logTextArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(logTextArea);

        // Add the logTextArea panel to the contentPane
        contentPane.add(scrollPane, BorderLayout.CENTER);
    }

    // Action listener to load CSV
    private class LoadCsvActionListener implements ActionListener 
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(StockTradingSimulatorGUI.this);
            if (result == JFileChooser.APPROVE_OPTION) 
            {
            	// Set file path to user selection
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                loadedStockDataList = readStockData(filePath);

                // Set the log to blank so it is a clean slate every run
                logTextArea.setText("");
                logTradeHeader();
                logTextArea.append("Loaded CSV file: " + filePath + "\n");
                logTextArea.append("Number of entries: " + loadedStockDataList.size() + "\n\n");
            }
        }
    }

    // Method to begin the evaluation of the stock
    private class BeginEvaluationActionListener implements ActionListener 
    {
    	// Action listener to retrieve balance from user input
        @Override
        public void actionPerformed(ActionEvent e) {
            double startingBalance = Double.parseDouble(balanceTextField.getText());

            // Check if stock data is loaded
            if (loadedStockDataList != null && !loadedStockDataList.isEmpty()) 
            {
            	logTextArea.setText("");
                simulateTrading(loadedStockDataList, startingBalance);
            }
            // Make sure a CSV is loaded
            else 
            {
                logTextArea.setText("Error: Please load a CSV file before beginning evaluation.\n");
            }
        }
    }

    // Method to read the stock data
    private static List<StockData> readStockData(String filePath) 
    {
        List<StockData> stockDataList = new ArrayList<>();

        // Attempt to read using BufferedReader
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) 
        {
            String line;
            br.readLine();
            
            // While loop to continue while there is data to read
            while ((line = br.readLine()) != null) 
            {
            	// Split the data by commas
                String[] data = line.split(",");
                
                // Store the data in a new StockData object
                StockData stockData = new StockData(data[0], Double.parseDouble(data[1]), Double.parseDouble(data[4]));
                
                // Add the new object into the arraylist of stockdata
                stockDataList.add(stockData);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stockDataList;
    }

    // Method to simulate the trading
    private void simulateTrading(List<StockData> stockDataList, double startingBalance) 
    {
    	// Initialize counters and starting balance
        double balance = startingBalance;
        int shares = 0;
        int numOfBuys = 0;
        int numOfSells = 0;
        int numOfHolds = 0;

        // Use the heuristic from the combo box on the GUI
        String selectedHeuristic = (String) heuristicComboBox.getSelectedItem();

        // Loop through the stockData
        for (StockData stockData : stockDataList) 
        {
            double rsi = calculateRSI(stockDataList, stockData);

            // tradeDecision determines the amount of shares to buy. This will be altered by each heuristic
            int tradeDecision;
            
            // Calculate the chosen heuristic on our stock list
            if ("RSI + MA".equals(selectedHeuristic)) 
            {
                double ma = calculateMovingAverage(stockDataList, stockData, 10);
                tradeDecision = tradeEvaluator(stockData, rsi, ma, balance, shares);
            } 
            else if ("Buy and Hold".equals(selectedHeuristic)) 
            {
                tradeDecision = buyAndHold(stockData, balance, shares);
            } 
            else if ("Trend Following".equals(selectedHeuristic)) 
            {
                tradeDecision = trendFollowing(stockData, stockDataList, balance, shares);
            } 
            else 
            {
                tradeDecision = 0;
            }

            // If trade decision is higher than 0 (buying)
            if (tradeDecision > 0) 
            {
                int numberOfSharesToBuy = Math.min(tradeDecision, (int) (balance / stockData.getClose()));
                balance -= numberOfSharesToBuy * stockData.getClose();
                shares += numberOfSharesToBuy;
                
                // Log transaction
                logTrade(stockData, numberOfSharesToBuy, "BUY");
                numOfBuys++;
            } 
            // If trade decision is less than 0 (selling)
            else if (tradeDecision < 0 && shares > 0) 
            {
                int numberOfSharesToSell = Math.min(-tradeDecision, shares);
                balance += numberOfSharesToSell * stockData.getClose();
                shares -= numberOfSharesToSell;
                
                // Log transaction
                logTrade(stockData, numberOfSharesToSell, "SELL");
                numOfSells++;
            } 
            else 
            {
            	// Do nothing
                logTrade(stockData, 0, "HOLD");
                numOfHolds++;
            }
        }

        // Calculate the value of remaining shares at the last closing price
        double remainingSharesValue = shares * stockDataList.get(stockDataList.size() - 1).getClose();

        // Log final balance, number of shares, profit, and trade counts
        double profit = balance - startingBalance + remainingSharesValue;
        
        logTextArea.append("\nNumber of Buys: " + numOfBuys + "\n");
        logTextArea.append("Number of Sells: " + numOfSells + "\n");
        logTextArea.append("Number of Holds: " + numOfHolds + "\n");
        logTextArea.append("Final Balance: $" + String.format("%.2f", balance) + "\n");
        logTextArea.append("Final Shares: " + shares + "\n");
        logTextArea.append("Total Profit: $" + String.format("%.2f", profit) + "\n");
    }

    // Method to calculate RSI
    private double calculateRSI(List<StockData> stockDataList, StockData currentData) 
    {
        
    	// Choosing a 14-day window
    	int period = 14;
        
        if (stockDataList.indexOf(currentData) < period)
            return 50.0;

        // Create lists to keep track of gains and losses
        List<Double> gains = new ArrayList<>();
        List<Double> losses = new ArrayList<>();

        // Loop through the data to keep track of gains and losses
        for (int i = stockDataList.indexOf(currentData); i > stockDataList.indexOf(currentData) - period; i--) 
        {
            double priceDiff = stockDataList.get(i).getClose() - stockDataList.get(i - 1).getClose();
            if (priceDiff >= 0) 
            {
                gains.add(priceDiff);
                losses.add(0.0);
            } else 
            {
                gains.add(0.0);
                losses.add(-priceDiff);
            }
        }

        // Calculate average gain and average loss
        double averageGain = gains.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        double averageLoss = losses.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);

        // Calculate RSI using the average gain and average loss
        double rs = (averageGain == 0) ? 0 : averageGain / averageLoss;
        return 100 - (100 / (1 + rs));
    }

    // Method to calculate moving average
    private double calculateMovingAverage(List<StockData> stockDataList, StockData currentData, int period) 
    {
    	// Check period
        if (stockDataList.indexOf(currentData) < period)
            return 0.0;

        double sum = 0.0;
        
        // Sum the closing prices
        for (int i = stockDataList.indexOf(currentData); i > stockDataList.indexOf(currentData) - period; i--)
            sum += stockDataList.get(i).getClose();

        // Divide the sum by the period
        return sum / period;
    }

    // Method to determine shares to buy
    private int tradeEvaluator(StockData stockData, double rsi, double ma, double balance, int shares) 
    {
    	// Establish thresholds
        double rsiOverboughtThreshold = 70.0;
        double rsiOversoldThreshold = 30.0;
        double maBuyThreshold = 0.02;

        // If RSI is higher than threshold
        if (rsi > rsiOverboughtThreshold && shares > 0) 
        {
            return -Math.min(shares, 1000);
        } 
        // If RSI is less than threshold
        else if (rsi < rsiOversoldThreshold && balance > 0) 
        {
            int maxSharesToBuy = (int) (balance / stockData.getClose());
            return Math.min(maxSharesToBuy, 1000);
        } 
        // If MA is greater than 0 and stock's closing price is less than RSI
        else if (ma > 0 && stockData.getClose() < (1 - maBuyThreshold) * ma && balance > 0)
        {
            int maxSharesToBuy = (int) (balance / stockData.getClose());
            return Math.min(maxSharesToBuy, 1000);
        } 
        // Nothing
        else
        {
            return 0;
        }
    }

    // Buy and hold heuristic
    private int buyAndHold(StockData stockData, double balance, int shares) 
    {
    	// Buy shares and hold them the entire period
        if (shares == 0 && balance > 0) 
        {
            int maxSharesToBuy = (int) (balance / stockData.getClose());
            return Math.min(maxSharesToBuy, 1000);
        } 
        else 
        {
            return 0;
        }
    }

    // Trend Following heuristic
    private int trendFollowing(StockData currentData, List<StockData> stockDataList, double balance, int shares)
    {
    	// Calculate a short-term MA and a long-term MA
        double maShort = calculateMovingAverage(stockDataList, currentData, 5);
        double maLong = calculateMovingAverage(stockDataList, currentData, 20);

        if (maShort > 0 && maLong > 0) 
        {
            if (maShort > maLong && shares == 0) 
            {
                // Buy if in an uptrend and not holding shares
                int maxSharesToBuy = (int) (balance / currentData.getClose());
                return Math.min(maxSharesToBuy, 1000);
                
            } 
            else if (maShort < maLong && shares > 0) 
            {
                // Sell if in a downtrend and holding shares
                return -Math.min(shares, 1000);
            }
        }
        
        return 0;
    }

    // Method to log the header of the output box in a formatted way
    private void logTradeHeader() 
    {
        logTextArea.append(String.format("%-12s%-12s%-12s%-12s%n", "DATE", "ACTION", "SHARES", "PRICE"));
    }

    // Method to log a trade (keep formatted)
    private void logTrade(StockData stockData, int numberOfShares, String action) 
    {
        logTextArea.append(String.format("%-12s%-12s%-12d$%.2f%n", stockData.getDate(), action, numberOfShares, stockData.getClose()));
    }

    // Main method
    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(() -> {
            new StockTradingSimulatorGUI().setVisible(true);
        });
    }
}

// Class to create StockData objects
class StockData 
{
    private String date;
    private double open;
    private double close;

    // Simple constructor
    public StockData(String date, double open, double close) 
    {
        this.date = date;
        this.open = open;
        this.close = close;
    }

    // Getters and setters
    
    public String getDate() {
        return date;
    }

    public double getOpen() {
        return open;
    }

    public double getClose() {
        return close;
    }
}
