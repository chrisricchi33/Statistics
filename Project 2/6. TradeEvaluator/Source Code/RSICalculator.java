import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//Chris Ricchi
//12-1-23
//RSI Calculator
//Simple calculator to hard-read CSV data and calculate the RSI per-day

public class RSICalculator 
{
	// Main method
    public static void main(String[] args) 
    {
    	// Change csv name if using different data
        String inputCsvFile = "AAPL.csv";
        
        // Output to user's desktop
        String outputCsvFile = System.getProperty("user.home") + "\\Desktop\\output.csv";

        // Try to calculate RSI for each date and print to .csv
        try 
        {
            List<StockData> stockDataList = readCSV(inputCsvFile);
            calculateRSI(stockDataList);
            writeCSV(stockDataList, outputCsvFile);
            System.out.println("RSI calculation completed and saved to " + outputCsvFile);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to read CSV
    private static List<StockData> readCSV(String csvFile) throws IOException 
    {
    	// List to store StockData
        List<StockData> stockDataList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) 
        {
            String line;
            br.readLine();
            
            // Continue until no more data remains
            while ((line = br.readLine()) != null)
            {
                String[] data = line.split(",");
                StockData stockData = new StockData(data[0], Double.parseDouble(data[1]), Double.parseDouble(data[2]), Double.parseDouble(data[3]), Double.parseDouble(data[4]), Double.parseDouble(data[5]), Long.parseLong(data[6]));
                stockDataList.add(stockData);
            }
        }

        return stockDataList;
    }

    // Method to calculate RSI
    private static void calculateRSI(List<StockData> stockDataList) 
    {
    	// Using a 14 day period
        int period = 14;
        
        // Lists to keep track of gains and losses
        List<Double> gains = new ArrayList<>();
        List<Double> losses = new ArrayList<>();

        for (int i = 1; i < stockDataList.size(); i++) 
        {
        	// Calculate the price difference
            double priceDifference = stockDataList.get(i).getClose() - stockDataList.get(i - 1).getClose();
            if (priceDifference >= 0) 
            {
                gains.add(priceDifference);
                losses.add(0.0);
            } 
            else 
            {
                gains.add(0.0);
                losses.add(-priceDifference);
            }

            // Calculate the average gain and loss over the period
            if (i >= period) 
            {
                double averageGain = calculateAverage(gains.subList(i - period, i));
                double averageLoss = calculateAverage(losses.subList(i - period, i));
                double relativeStrength = averageGain / averageLoss;
                
                // Calculate RSI using relative strength
                double rsi = 100.0 - (100.0 / (1.0 + relativeStrength));

                stockDataList.get(i).setRsi(rsi);
            }
        }
    }

    // Method to calculate average
    private static double calculateAverage(List<Double> values) 
    {
    	// Zero check
        if (values.isEmpty()) 
        {
            return 0.0;
        }

        double sum = 0.0;
        
        // Simple for each loop
        for (double value : values) 
        {
            sum += value;
        }

        // Return the average
        return sum / values.size();
    }

    // Write the RSI to the CSV
    private static void writeCSV(List<StockData> stockDataList, String csvFile) throws IOException 
    {
        try (FileWriter writer = new FileWriter(csvFile)) 
        {
            writer.write("Date,Open,High,Low,Close,Adj Close,Volume,RSI\n");
            
            // Append the RSI to the end of the CSV
            for (StockData stockData : stockDataList)
                writer.write(stockData.toCSVString() + "," + stockData.getRsi() + "\n");
        }
    }
    
    // Private class to create StockData objects
    private static class StockData 
    {
    	// Variables for stockdata
        private String date;
        private double open;
        private double high;
        private double low;
        private double close;
        private double adjClose;
        private long volume;
        private double rsi;

        // Simple constructor
        public StockData(String date, double open, double high, double low, double close, double adjClose, long volume) 
        {
            this.date = date;
            this.open = open;
            this.high = high;
            this.low = low;
            this.close = close;
            this.adjClose = adjClose;
            this.volume = volume;
        }
        
        // Getters and setters

        public String getDate() {
            return date;
        }

        public double getClose() {
            return close;
        }

        public double getRsi() {
            return rsi;
        }

        public void setRsi(double rsi) {
            this.rsi = rsi;
        }

        // TO string (might not need)
        public String toCSVString() {
            return date + "," + open + "," + high + "," + low + "," + close + "," + adjClose + "," + volume;
        }
    }
}
