% Chris Ricchi
% TestPlot to learn .csv data reading

file_path = fullfile('C:/Users/chris/Desktop', 'data.csv');

% Load data from the file path
data = csvread(file_path);

% Set x and y from the file
x = data(:, 1);
y = data(:, 2);

% Plotting the test points
figure;
plot(x, y, 'o', 'MarkerSize', 8, 'MarkerFaceColor', 'b');
title('Scatter Plot');
xlabel('x');
ylabel('y');
grid on;

