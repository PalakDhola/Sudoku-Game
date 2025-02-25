import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class SudokuGame extends Frame {
    TextField[][] cells = new TextField[9][9]; 
    int[][] solution = new int[9][9];         
    Panel board, number;
    int selectrow = -1, seleccol = -1;   
    Label result ; 

    public SudokuGame() {
        setTitle("Sudoku Game");
        setSize(400, 500);
        setLayout(new BorderLayout());

        board = new Panel(new GridLayout(9, 9));
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                cells[row][col] = new TextField();
                cells[row][col].setFont(new Font("Arial", Font.PLAIN, 20));
                cells[row][col].setEditable(false); 
                
                Color color1 = new Color(230, 230, 250);  
                Color color2 = new Color(240, 255, 240);  
                if ((row / 3 + col / 3) % 2 == 0) {
                    cells[row][col].setBackground(color1);
                } else {
                    cells[row][col].setBackground(color2);
                }
                
                board.add(cells[row][col]);

                final int r = row;
                final int c = col;
                cells[row][col].addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        selectrow = r;
                        seleccol = c;
                    }
                });
            }
        }
        add(board, BorderLayout.CENTER);

        number = new Panel(new GridLayout(4, 3)); 
        Color buttonColor = new Color(173, 216, 230); 
        for (int i = 1; i <= 9; i++) {
            addNumberButton(i, buttonColor);
        }
        addClearButton();
        addCheckButton();
        add(number, BorderLayout.SOUTH);

        result = new Label("");
        result.setFont(new Font("Arial", Font.PLAIN, 16));
        result.setAlignment(Label.CENTER);
        result.setForeground(Color.RED);
        add(result, BorderLayout.NORTH);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });

        generateSudoku();
        setVisible(true);
    }

    void addNumberButton(final int number, Color buttonColor) {
        Button button = new Button(String.valueOf(number));
        button.setFont(new Font("Arial", Font.PLAIN, 20));
        button.setBackground(buttonColor); 
        button.setForeground(Color.BLACK); 
        
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                inputNumber(number);
            }
        });
        
       // number.add(button);
    }

    void addClearButton() {
        Button clearButton = new Button("Clear");
        clearButton.setFont(new Font("Arial", Font.PLAIN, 20));
        clearButton.setBackground(new Color(255, 105, 180));  
        clearButton.setForeground(Color.WHITE);
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                inputNumber(0);  
            }
        });
        number.add(clearButton);
    }

    void addCheckButton() {
        Button checkButton = new Button("Check");
        checkButton.setFont(new Font("Arial", Font.PLAIN, 20));
        checkButton.setBackground(new Color(50, 205, 50));  
        checkButton.setForeground(Color.WHITE);
        checkButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkSolution();
            }
        });
        number.add(checkButton);
    }

    void inputNumber(int number) {
        if (selectrow != -1 && seleccol != -1) { 
            if (cells[selectrow][seleccol].isEditable()) {
                if (number == 0) {
                    cells[selectrow][seleccol].setText("");  
                } else {
                    cells[selectrow][seleccol].setText(String.valueOf(number));
                }
            }
        }
    }

    void checkSolution() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                String value = cells[row][col].getText();
                if (!value.isEmpty()) {
                    int num = Integer.parseInt(value);
                    if (num != solution[row][col]) {
                        result.setText("Incorrect solution!"); 
                        return;
                    }
                } else {
                    result.setText("The puzzle is not fully filled."); 
                    return;
                }
            }
        }
        result.setText("Congratulations! You solved the puzzle."); 
    }

    void generateSudoku() {
        Random rand = new Random();
        
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

        solution = presetSolution;  

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (rand.nextInt(100) < 50) {  
                    cells[row][col].setText("");
                    cells[row][col].setEditable(true);  
                } else {
                    cells[row][col].setText(String.valueOf(solution[row][col]));
                    cells[row][col].setEditable(false);  
                }
            }
        }
    }

    public static void main(String[] args) {
        new SudokuGame();
    }
}