
% Chris Ricchi - 12/4/2023
% DataSmoothing - This octave program allows users to plot datapoints
% from a .csv file and either salt or smooth the points.

function DataSmoothing()

    % Specify the CSV name (the name must be accurate or it will not read)
    filename = 'data.csv';

    % Load data from CSV and store them as (X, Y) points
    data = csvread(filename);
    x = data(:, 1);
    y = data(:, 2);

    % Plot original data from the CSV file
    figure;
    h = plot(x, y, 'o');
    title('Original Data');
    xlabel('X-axis');
    ylabel('Y-axis');
    grid on;

    % Create a menu to allow the user to pick what action they would like to do
    choice = menu('Choose an option:', 'Salt Data', 'Smooth Data', 'Export Data', 'Exit');

    % Use a loop to keep the user in the menu until they choose to exit
    while choice ~= 4
        switch choice
            % Salting Data
            case 1
                salt_factor = str2double(inputdlg('Please enter a salt factor:'));
                if ~isnan(salt_factor)
                    y_salted = y + salt_factor * randn(size(y));
                    set(h, 'YData', y_salted);
                    title('Salted Data');
                    y = y_salted;
                else
                    disp('Invalid input. Please enter a valid number.');
                end
            % Smoothing Data
            case 2
                smooth_factor = str2double(inputdlg('Enter smoothing factor (e.g., 5):'));
                 if ~isnan(smooth_factor) && smooth_factor > 0
                    y_smoothed = movmean(y, smooth_factor);
                    y = y_smoothed;
                    set(h, 'YData', y_smoothed);
                    title('Smoothed Data');
                 else
                     disp('Invalid input. Please enter a valid positive number.');
                 end
            % Saving data to a new CSV
            case 3
                export_filename = inputdlg('Enter a filename (.csv):');
                if ~isempty(export_filename)
                    export_filename = export_filename{1};
                    export_data = [x, y];
                    csvwrite(export_filename, export_data);
                    disp(['Data exported to ', export_filename]);
                else
                    disp('Invalid input. Please enter a valid filename.');
                end
            otherwise
                disp('Invalid choice. Please choose again.');
        end

        % Redraw graph after end of loop
        drawnow;

        % Ask user for next input option
        choice = menu('Choose an option:', 'Salt Data', 'Smooth Data', 'Export Data', 'Exit');
    end

    disp('Exiting the program.');
end

% Call the main function
DataSmoothing();