import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

// Chris Ricchi
// 11/5/2023
// DataSmoothing
// Allows import of CSV data, salting and smoothing of data, plotting, and exporting

public class DataSmoothing extends JFrame 
{

	// Private list of GUI elements
    private JLabel statusLabel;
    private JButton loadCSVButton;
    private JButton smoothDataButton;
    private JButton saltDataButton;
    private JButton revertButton;
    private JButton exportCSVButton;
    private PlotPanel plotPanel;

    // Ref. of loaded file and arrays to store data
    private File loadedFile;
    private int[] originalXData;
    private int[] originalYData;
    private int[] xData;
    private int[] yData;

    // Constructor to build GUI
    public DataSmoothing() 
    {
        setTitle("CSV Loader");
        setSize(1000, 800);
        setMaximumSize(new Dimension(1000, 800));
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top panel
        JPanel topPanel = new JPanel();
        statusLabel = new JLabel("No CSV data loaded. Please load CSV file.");
        statusLabel.setForeground(Color.RED);
        topPanel.add(statusLabel);
        add(topPanel, BorderLayout.NORTH);

        // Center panel
        plotPanel = new PlotPanel();
        add(plotPanel, BorderLayout.CENTER);

        // Bottom panel
        JPanel bottomPanel = new JPanel();
        loadCSVButton = new JButton("Load CSV");
        smoothDataButton = new JButton("Smooth Data");
        saltDataButton = new JButton("Salt Data");
        revertButton = new JButton("Revert to Original");
        exportCSVButton = new JButton("Export as CSV");

        // Action listener for loading CSV
        loadCSVButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                loadCSV();
                
                // This will plot the data as soon as the .csv is loaded
                // so you don't have to press "plot"
                plotData();
            }
        });

        // Action listener for smoothing
        smoothDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                smoothData();
            }
        });

        // Action listener for salting
        saltDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saltData();
            }
        });

        // Action listener to revert to original
        revertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                revertToOriginal();
            }
        });

        // Action listener to export to CSV
        exportCSVButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportToCSV();
            }
        });

        // Add GUI elements to panel
        bottomPanel.add(loadCSVButton);
        bottomPanel.add(smoothDataButton);
        bottomPanel.add(saltDataButton);
        bottomPanel.add(revertButton);
        bottomPanel.add(exportCSVButton);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    // Method to load CSV
    private void loadCSV() 
    {
    	// Use JFileChooser to load a file
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) 
        {
        	// Store reference to selected file
            loadedFile = fileChooser.getSelectedFile();

            try 
            {
                Scanner scanner = new Scanner(loadedFile);

                // Empty CSV is loaded (no next line)
                if (!scanner.hasNextLine()) 
                {
                    showErrorMessage("CSV File is empty");
                    return;
                }

                // Read the header line
                String headerLine = scanner.nextLine();
                
                // Split by commas
                String[] headerTokens = headerLine.split(",");
                
                // Check csv is formatted for XY
                if (headerTokens.length != 2) 
                {
                    showErrorMessage("CSV File contains improperly formatted header");
                    return;
                }

                // Label axis
                String xAxisLabel = headerTokens[0].trim();
                String yAxisLabel = headerTokens[1].trim();

                // Initialize arrays for data storage
                originalXData = new int[0];
                originalYData = new int[0];
                xData = new int[0];
                yData = new int[0];

                // Parse CSV file
                while (scanner.hasNextLine()) 
                {
                    String dataLine = scanner.nextLine();
                    String[] dataTokens = dataLine.split(",");

                    if (dataTokens.length != 2) 
                    {
                        showErrorMessage("CSV File contains improperly formatted data");
                        return;
                    }

                    try 
                    {
                        int x = Integer.parseInt(dataTokens[0].trim());
                        int y = Integer.parseInt(dataTokens[1].trim());

                        // Resize arrays to accommodate new data point
                        originalXData = resizeArray(originalXData, originalXData.length + 1);
                        originalYData = resizeArray(originalYData, originalYData.length + 1);

                        // Store original data point
                        originalXData[originalXData.length - 1] = x;
                        originalYData[originalYData.length - 1] = y;

                        // Initialize current data point with original values
                        xData = Arrays.copyOf(originalXData, originalXData.length);
                        yData = Arrays.copyOf(originalYData, originalYData.length);
                        
                    // Number catch
                    } catch (NumberFormatException ex) {
                        showErrorMessage("CSV File contains improperly formatted data");
                        return;
                    }
                }

                // Update status to show the CSV we loaded
                statusLabel.setText(loadedFile.getName());
                statusLabel.setForeground(Color.GREEN);

            // File catch
            } catch (FileNotFoundException ex) {
                showErrorMessage("Error reading the file");
            }
        }
    }

    // Method to salt data
    private void saltData() 
    {
        // Salt each point with a random value within a reasonable range
        Random rand = new Random();
        
        // Establish a range (changeable in the future)
        int saltRange = 1;
        
        // Null check for data
        if (originalXData != null && originalYData != null) 
        {
        	// Parse through all xData
        	for (int i = 0; i < xData.length; i++) 
        	{
        		// Salt each individual point and set it
                int saltX = rand.nextInt(2 * saltRange + 1) - saltRange;
                int saltY = rand.nextInt(2 * saltRange + 1) - saltRange;

                xData[i] += saltX;
                yData[i] += saltY;
            }

        	// Replot data after salting has occurred
            plotData();
            
        }
        else
            showErrorMessage("No data detected. Please load a CSV file.");
    }

    // Method to smooth data
    private void smoothData() 
    {
    	// Using a simple moving average to smooth data.
    	// Chose a window size of 3 (this is changeable later)
    	if (originalXData != null && originalYData != null) 
    	{
    		// Parse through each xData
    		for (int i = 1; i < xData.length - 1; i++)
    		{
    			// Use a simple moving average to smooth each point by window size of 3
                xData[i] = (xData[i - 1] + xData[i] + xData[i + 1]) / 3;
                yData[i] = (yData[i - 1] + yData[i] + yData[i + 1]) / 3;
            }
    		
    		// Replot after smoothing
    		plotData();
        } 
    	else
        	showErrorMessage("No data detected. Please load a CSV file.");
    }

    // Method to restore to original data
    private void revertToOriginal() 
    {
        // Restore original data
        if (originalXData != null && originalYData != null) 
        {
        	// Use copyOf to restore datapoints of original length
            xData = Arrays.copyOf(originalXData, originalXData.length);
            yData = Arrays.copyOf(originalYData, originalYData.length);
            
            // Replot after restored
            plotData();
        } 
        else
            showErrorMessage("No original data to revert to.");
    }

    // Method to plot the points
    private void plotData() 
    {
    	// Null check on all points
        if (xData != null && yData != null) 
        {
        	// Update the axis on the plots
            plotPanel.updateData("X-Axis", "Y-Axis", xData, yData);
            
            // Enable revert button; cannot revert to anything if nothing has
            // been plotted yet
            revertButton.setEnabled(true);
        }
        else
            showErrorMessage("No data to plot. Please load a CSV file.");
    }

    // Method to export to CSV
    private void exportToCSV() 
    {
    	// Null check for points
        if (xData != null && yData != null) 
        {
        	// Create a file to export to
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) 
            {
                File outputFile = fileChooser.getSelectedFile();
                
                try (PrintWriter writer = new PrintWriter(outputFile)) 
                {
                	// Create headers for new csv file
                    writer.println("X-Axis,Y-Axis");
                    
                    // Loop through all points and write them to the csv file
                    for (int i = 0; i < xData.length; i++)
                        writer.println(xData[i] + "," + yData[i]);
                    
                    // File exception catch
                } catch (FileNotFoundException e) {
                    showErrorMessage("Error exporting data to CSV");
                }
            }
        } else {
            showErrorMessage("No data to export. Please load a CSV file.");
        }
    }

    // Class that contains the plot
    public class PlotPanel extends JPanel 
    {
    	// Private variables of plot data
        private String xAxisLabel;
        private String yAxisLabel;
        private int[] xData;
        private int[] yData;

        @Override
        protected void paintComponent(Graphics g) 
        {
            super.paintComponent(g);
            drawCoordinateGrid(g);
            if (xData != null && yData != null) {
                plotData(g);
            }
        }

        // Method to draw points
        private void drawCoordinateGrid(Graphics g) 
        {
            int margin = 30;

            // Find the maximum values in xData and yData
            int xMax = getMaxValue(xData);
            int yMax = getMaxValue(yData);

            // Draw horizontal grid lines
            for (int i = 0; i <= 10; i++) 
            {
                int y = margin + i * (getHeight() - 2 * margin) / 10;
                g.drawLine(margin, y, getWidth() - margin, y);
                
                // Display scale values on the side
                String label = String.valueOf((int) (yMax * (10 - i) / 10.0));
                g.drawString(label, margin - 25, y + 5);
            }

            // Draw vertical grid lines
            for (int i = 0; i <= 10; i++) 
            {
                int x = margin + i * (getWidth() - 2 * margin) / 10;
                g.drawLine(x, getHeight() - margin, x, margin);
                
                // Display scale values on the bottom
                String label = String.valueOf((int) (xMax * i / 10.0));
                g.drawString(label, x - 10, getHeight() - margin + 20);
            }
        }

        // Method to get the highest value of the array
        private int getMaxValue(int[] array) 
        {
        	// Null check or 0 check
            if (array == null || array.length == 0) 
                return 1;

            int max = array[0];
            
            // Use max of math class to find max value
            for (int value : array)
                max = Math.max(max, value);

            return Math.max(1, max);
        }

        private void plotData(Graphics g) 
        {
        	// Margin for padding
            int margin = 30;

            // Find the maximum values in xData and yData
            int xMax = getMaxValue(xData);
            int yMax = getMaxValue(yData);

            // Plot data points
            for (int i = 0; i < xData.length; i++) 
            {
                int x = margin + xData[i] * (getWidth() - 2 * margin) / xMax;
                int y = getHeight() - margin - yData[i] * (getHeight() - 2 * margin) / yMax;
                
                // Plot points as circles
                g.fillOval(x - 3, y - 3, 6, 6);

                // Display data values near the points
                g.drawString("(" + xData[i] + ", " + yData[i] + ")", x + 8, y - 8);
            }

            // Connect data points with lines
            g.setColor(Color.BLUE);
            
            // Plot data points
            for (int i = 1; i < xData.length; i++) 
            {
                int x1 = margin + xData[i - 1] * (getWidth() - 2 * margin) / xMax;
                int y1 = getHeight() - margin - yData[i - 1] * (getHeight() - 2 * margin) / yMax;
                int x2 = margin + xData[i] * (getWidth() - 2 * margin) / xMax;
                int y2 = getHeight() - margin - yData[i] * (getHeight() - 2 * margin) / yMax;
                g.drawLine(x1, y1, x2, y2);
            }
            
            g.setColor(Color.BLACK);
        }

        // Method to update the labels on the plot
        public void updateData(String xAxisLabel, String yAxisLabel, int[] xData, int[] yData) 
        {
            this.xAxisLabel = xAxisLabel;
            this.yAxisLabel = yAxisLabel;
            this.xData = xData;
            this.yData = yData;
            repaint();
        }
    }

    // Method to resize array to a new size
    private int[] resizeArray(int[] arr, int newSize) 
    {
        int[] newArray = new int[newSize];
        System.arraycopy(arr, 0, newArray, 0, Math.min(arr.length, newSize));
        return newArray;
    }

    // Method to call showMessageDialog with an error
    private void showErrorMessage(String message) 
    {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    //Main method
    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                DataSmoothing gui = new DataSmoothing();
                gui.setVisible(true);
            }
        });
    }
}
