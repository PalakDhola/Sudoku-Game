import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class SudokuGame extends Frame {
    private TextField[][] cells = new TextField[9][9]; // 9x9 grid for the Sudoku game
    private int[][] solution = new int[9][9];          // Back-end solution of the game
    private Panel boardPanel, numberPadPanel;
    private int selectedRow = -1, selectedCol = -1;    // To track the selected cell
    private Label resultLabel; // Label to display result messages

    public SudokuGame() {
        setTitle("Sudoku Game");
        setSize(400, 500);
        setLayout(new BorderLayout());

        // Initialize Sudoku board
        boardPanel = new Panel(new GridLayout(9, 9));
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                cells[row][col] = new TextField();
                cells[row][col].setFont(new Font("Arial", Font.PLAIN, 20));
                cells[row][col].setEditable(false); // Initially, cells are non-editable
                
                // Set background color based on the 3x3 grid location
                Color color1 = new Color(230, 230, 250);  // Light lavender
                Color color2 = new Color(240, 255, 240);  // Light green
                if ((row / 3 + col / 3) % 2 == 0) {
                    cells[row][col].setBackground(color1);
                } else {
                    cells[row][col].setBackground(color2);
                }
                
                boardPanel.add(cells[row][col]);

                // Add mouse listener to track the selected cell
                final int r = row;
                final int c = col;
                cells[row][col].addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        selectedRow = r;
                        selectedCol = c;
                    }
                });
            }
        }
        add(boardPanel, BorderLayout.CENTER);

        // Create a number pad for player input
        numberPadPanel = new Panel(new GridLayout(4, 3)); // 3x4 layout for digits 1-9 and "Clear"
        Color buttonColor = new Color(173, 216, 230); // Light blue color for all buttons
        for (int i = 1; i <= 9; i++) {
            addNumberButton(i, buttonColor);
        }
        addClearButton();
        addCheckButton();
        add(numberPadPanel, BorderLayout.SOUTH);

        // Initialize result label
        resultLabel = new Label("");
        resultLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        resultLabel.setAlignment(Label.CENTER);
        resultLabel.setForeground(Color.RED);
        add(resultLabel, BorderLayout.NORTH);

        // Add window close event
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });

        // Generate and display the game
        generateSudoku();
        setVisible(true);
    }

    // Add a button for number input (1-9) with a uniform background color
    private void addNumberButton(int number, Color buttonColor) {
        Button button = new Button(String.valueOf(number));
        button.setFont(new Font("Arial", Font.PLAIN, 20));
        button.setBackground(buttonColor); // Set uniform color
        button.setForeground(Color.BLACK); // Set text color to black
        
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                inputNumber(number);
            }
        });
        numberPadPanel.add(button);
    }

    // Add a clear button to clear the selected cell with a background color
    private void addClearButton() {
        Button clearButton = new Button("Clear");
        clearButton.setFont(new Font("Arial", Font.PLAIN, 20));
        clearButton.setBackground(new Color(255, 105, 180));  // Hot pink
        clearButton.setForeground(Color.WHITE);
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                inputNumber(0);  // Clear the selected cell
            }
        });
        numberPadPanel.add(clearButton);
    }

    // Add a button to check if the player has completed the puzzle correctly with a background color
    private void addCheckButton() {
        Button checkButton = new Button("Check");
        checkButton.setFont(new Font("Arial", Font.PLAIN, 20));
        checkButton.setBackground(new Color(50, 205, 50));  // Lime green
        checkButton.setForeground(Color.WHITE);
        checkButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkSolution();
            }
        });
        numberPadPanel.add(checkButton);
    }

    // Input the selected number into the currently selected cell
    private void inputNumber(int number) {
        if (selectedRow != -1 && selectedCol != -1) {  // Check if a cell is selected
            if (cells[selectedRow][selectedCol].isEditable()) {
                if (number == 0) {
                    cells[selectedRow][selectedCol].setText("");  // Clear cell
                } else {
                    cells[selectedRow][selectedCol].setText(String.valueOf(number));
                }
            }
        }
    }

    // Check if the player's solution is correct
    private void checkSolution() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                String value = cells[row][col].getText();
                if (!value.isEmpty()) {
                    int num = Integer.parseInt(value);
                    if (num != solution[row][col]) {
                        resultLabel.setText("Incorrect solution!"); // Display "Incorrect solution" message
                        return;
                    }
                } else {
                    resultLabel.setText("The puzzle is not fully filled."); // Display "Incomplete puzzle" message
                    return;
                }
            }
        }
        resultLabel.setText("Congratulations! You solved the puzzle."); // Display "Congratulations" message
    }

    // Generate a random Sudoku puzzle (this is a simple generation logic for demonstration)
    private void generateSudoku() {
        Random rand = new Random();
        // Initialize with a predefined solution (normally we would generate this)
        int[][] presetSolution = {
            {5, 3, 4, 6, 7, 8, 9, 1, 2},
            {6, 7, 2, 1, 9, 5, 3, 4, 8},
            {1, 9, 8, 3, 4, 2, 5, 6, 7},
            {8, 5, 9, 7, 6, 1, 4, 2, 3},
            {4, 2, 6, 8, 5, 3, 7, 9, 1},
            {7, 1, 3, 9, 2, 4, 8, 5, 6},
            {9, 6, 1, 5, 3, 7, 2, 8, 4},
            {2, 8, 7, 4, 1, 9, 6, 3, 5},
            {3, 4, 5, 2, 8, 6, 1, 7, 9}
        };

        solution = presetSolution;  // Store the solution for validation

        // Randomly clear some cells to create the puzzle (simple logic for demonstration)
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (rand.nextInt(100) < 50) {  // 50% chance to clear a cell
                    cells[row][col].setText("");
                    cells[row][col].setEditable(true);  // Allow user to input
                } else {
                    cells[row][col].setText(String.valueOf(solution[row][col]));
                    cells[row][col].setEditable(false);  // Pre-filled cells are not editable
                }
            }
        }
    }

    public static void main(String[] args) {
        new SudokuGame();
    }
}