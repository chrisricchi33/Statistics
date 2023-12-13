import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.apache.commons.math4.legacy.exception.MathIllegalArgumentException;
import org.apache.commons.math4.legacy.stat.descriptive.moment.Mean;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.TickUnits;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

//Chris Ricchi
//11/20/2023
//JFreeChart Smoothing
//Allows import of CSV data, salting and smoothing of data, plotting, and exporting using JFreeChart and Apache

public class JFreeChartSmoothing extends JFrame 
{
	// Private GUI elements
    private JLabel statusLabel;
    private JButton loadCSVButton;
    private JButton smoothDataButton;
    private JButton saltDataButton;
    private JButton revertButton;
    private JButton exportCSVButton;
    
    //JFreeChart ChartPanel
    private ChartPanel chartPanel;

    // Loaded file ref. and arrays for data points
    private File loadedFile;
    private double[] originalXData;
    private double[] originalYData;
    private double[] xData;
    private double[] yData;

    public JFreeChartSmoothing() 
    {
    	// Establishing GUI elements
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
        chartPanel = createChartPanel();
        add(chartPanel, BorderLayout.CENTER);

        // Bottom panel
        JPanel bottomPanel = new JPanel();
        loadCSVButton = new JButton("Load CSV");
        smoothDataButton = new JButton("Smooth Data");
        saltDataButton = new JButton("Salt Data");
        revertButton = new JButton("Revert to Original");
        exportCSVButton = new JButton("Export as CSV");

        // Action listener for loading csv
        loadCSVButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadCSV();
                
                // This will plot the data as soon as the .csv is loaded
                // so you don't have to press "plot"
                plotData();
            }
        });

        // ACtion listener for smoothing data
        smoothDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                smoothData();
            }
        });

        // Action listener for salting data
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

        exportCSVButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportToCSV();
            }
        });

        // Adding GUI elements
        bottomPanel.add(loadCSVButton);
        bottomPanel.add(smoothDataButton);
        bottomPanel.add(saltDataButton);
        bottomPanel.add(revertButton);
        bottomPanel.add(exportCSVButton);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    // Creating a chart using JFreeChart
    private ChartPanel createChartPanel() 
    {
        JFreeChart chart = ChartFactory.createXYLineChart("Data Plot", "X-Axis", "Y-Axis", null);

        // Initialize as an XYPlot
        XYPlot plot = chart.getXYPlot();
        
        // JFreeChart Shape Renderer (for points)
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        plot.setRenderer(renderer);

        // Setting up axis
        NumberAxis xAxis = new NumberAxis("X-Axis");
        NumberAxis yAxis = new NumberAxis("Y-Axis");
        plot.setDomainAxis(xAxis);
        plot.setRangeAxis(yAxis);

        // Tick units for graph invervals on JFreeChart
        TickUnits units = new TickUnits();
        units.add(new NumberTickUnit(1.0));
        units.add(new NumberTickUnit(5.0));
        units.add(new NumberTickUnit(10.0));
        xAxis.setStandardTickUnits(units);

        return new ChartPanel(chart);
    }

    // Method to load CSV
    private void loadCSV() 
    {
    	// Using JFileChooser for file selection
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) 
        {
            loadedFile = fileChooser.getSelectedFile();

            try 
            {
                Scanner scanner = new Scanner(loadedFile);

                // Ensure CSV has data
                if (!scanner.hasNextLine()) 
                {
                    showErrorMessage("CSV File is empty");
                    return;
                }

                // Read the header line
                String headerLine = scanner.nextLine();
                
                // Split to store data from commas
                String[] headerTokens = headerLine.split(",");
                
                if (headerTokens.length != 2) 
                {
                    showErrorMessage("CSV File contains improperly formatted header");
                    return;
                }

                // Set up X and Y labels
                String xAxisLabel = headerTokens[0].trim();
                String yAxisLabel = headerTokens[1].trim();

                // Initialize X and Y datapoints
                originalXData = new double[0];
                originalYData = new double[0];
                xData = new double[0];
                yData = new double[0];

                // Continue through CSV
                while (scanner.hasNextLine()) 
                {
                    String dataLine = scanner.nextLine();
                    String[] dataTokens = dataLine.split(",");

                    // Check header format
                    if (dataTokens.length != 2) 
                    {
                        showErrorMessage("CSV File contains improperly formatted data");
                        return;
                    }

                    // Storing with correct format
                    try 
                    {
                    	// Storing each points
                        double x = Double.parseDouble(dataTokens[0].trim());
                        double y = Double.parseDouble(dataTokens[1].trim());

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

                // Setting status label so user know CSV is loaded
                statusLabel.setText(loadedFile.getName());
                statusLabel.setForeground(Color.GREEN);

                // File exception
            } catch (FileNotFoundException ex) {
                showErrorMessage("Error reading the file");
            }
        }
    }
    
    // Private method to resizeArray
    private double[] resizeArray(double[] arr, int newSize) 
    {
        double[] newArray = new double[newSize];
        System.arraycopy(arr, 0, newArray, 0, Math.min(arr.length, newSize));
        return newArray;
    }

    // Method to salt data
    private void saltData() 
    {
        // Salt each point with a random value within a reasonable range
        Random rand = new Random();
        
        // Establish a range (changeable in the future)
        double saltRange = 1.0;

        // Null check for data
        if (originalXData != null && originalYData != null) 
        {
            for (int i = 0; i < xData.length; i++) 
            {
            	// Parse through all xData
                double saltX = rand.nextDouble() * 2 * saltRange - saltRange;
                double saltY = rand.nextDouble() * 2 * saltRange - saltRange;

                xData[i] += saltX;
                yData[i] += saltY;
            }
            
            // Replot data after salting has occurred
            plotData();
            
        } else
            showErrorMessage("No data detected. Please load a CSV file.");
    }

    // Method to smooth data
    private void smoothData() 
    {
    	// Null check
        if (originalXData != null && originalYData != null) 
        {
        	// Establish a window size (3 works good, can change later)
            int windowSize = 3;

            try 
            {
                for (int i = 1; i < xData.length - 1; i++) 
                {
                	// Create subsets of the orignial data in window size
                    double[] xSubset = Arrays.copyOfRange(xData, Math.max(0, i - windowSize / 2), Math.min(xData.length, i + windowSize / 2 + 1));
                    double[] ySubset = Arrays.copyOfRange(yData, Math.max(0, i - windowSize / 2), Math.min(yData.length, i + windowSize / 2 + 1));

                    // Calculate the mean value for the subset
                    Mean meanFunction = new Mean();
                    xData[i] = meanFunction.evaluate(xSubset);
                    yData[i] = meanFunction.evaluate(ySubset);
                }

                // Plot after smoothing has occurred
                plotData();
                
                // Number exception
            } catch (MathIllegalArgumentException e) {
                e.printStackTrace();
                showErrorMessage("Error smoothing data.");
            }
            
        } else
            showErrorMessage("No data detected. Please load a CSV file.");
    }

    // Method to make copies of original data and revert to original
    private void revertToOriginal() 
    {
        // Restore original data
        if (originalXData != null && originalYData != null) 
        {
            xData = Arrays.copyOf(originalXData, originalXData.length);
            yData = Arrays.copyOf(originalYData, originalYData.length);
            plotData();
        } 
        
        else
            showErrorMessage("No original data to revert to.");
    }

    // Method to Plot Data
    private void plotData() 
    {
    	// Null check
        if (xData != null && yData != null) 
        {
        	// Use JFreeChart to create a chart
            JFreeChart chart = ChartFactory.createXYLineChart(
                    "Data", "X-Axis", "Y-Axis",
                    
                    // Call a method to create the dataset
                    createDataset(),
                    
                    org.jfree.chart.plot.PlotOrientation.VERTICAL,
                    true, true, false
            );

            // Plot using XYPlot
            XYPlot plot = chart.getXYPlot();
            chartPanel.setChart(chart);

            // Enable the revert button after loaded data
            revertButton.setEnabled(true);
            
        } else
            showErrorMessage("No data to plot. Please load a CSV file.");
    }

    // Method of XYSeriesCollection JFreeChart to create dataset
    private XYSeriesCollection createDataset() 
    {
    	// Create new XYSeries
        XYSeries series = new XYSeries("Data");
        
        // Add X and Y points to series
        for (int i = 0; i < xData.length; i++)
            series.add(xData[i], yData[i]);

        // Add dataset to series
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        return dataset;
    }
    
    // Method to show errors in JOptionPane
    private void showErrorMessage(String message) 
    {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Method to export to CSV
    private void exportToCSV()
    {
    	// Null check
        if (xData != null && yData != null) 
        {
        	// Use JFileChooser to specific a file
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) 
            {
                File outputFile = fileChooser.getSelectedFile();
                
                try (PrintWriter writer = new PrintWriter(outputFile)) 
                {
                	// Write headers of csv
                    writer.println("X-Axis,Y-Axis");
                    
                    // Loop and write X and Y points
                    for (int i = 0; i < xData.length; i++)
                        writer.println(xData[i] + "," + yData[i]);
                    
                    //File exception
                } catch (FileNotFoundException e) {
                    showErrorMessage("Error exporting data to CSV");
                }
            }
        } else
            showErrorMessage("No data to export. Please load a CSV file.");
    }

    // Main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	JFreeChartSmoothing gui = new JFreeChartSmoothing();
                gui.setVisible(true);
            }
        });
    }
}
