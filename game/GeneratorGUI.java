package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Stack;

public class GeneratorGUI extends JFrame {
    private static final int SIZE = 30;
    private static final Color COLOR_ONE = Color.RED;
    private static final Color COLOR_TWO = Color.WHITE;
    private static final Color COLOR_NINE = Color.GREEN;
    private JButton[][] buttons = new JButton[SIZE][SIZE];
    private int[][] grid = new int[SIZE][SIZE];
    private boolean lineMode = false;
    private JButton firstPoint;
    private JButton secondPoint;
    private Stack<int[][]> undoStack = new Stack<>();
    private Stack<int[][]> redoStack = new Stack<>();

    public GeneratorGUI() {
        super("Array Grid Generator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(SIZE, SIZE));
        initializeGrid();
        initializeMenuBar();
        setVisible(true);
    }

    private void initializeGrid() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setOpaque(true);
                // Set border color to gray
                buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
                buttons[i][j].addActionListener(this::buttonPressed);
                grid[i][j] = 0; // Initialize all to 0
                updateButtonColor(i, j);
                add(buttons[i][j]);
            }
        }
    }

    private void buttonPressed(ActionEvent e) {
        if (lineMode) {
            if (firstPoint == null) {
                firstPoint = (JButton) e.getSource();
            } else if (secondPoint == null) {
                secondPoint = (JButton) e.getSource();
                saveState();  // Save state before drawing a line
                drawLine(firstPoint, secondPoint);
                firstPoint = null; // Reset for the next line
                secondPoint = null;
            }
        } else {
            // Assuming a typical change to grid, like toggling its value
            JButton pressedButton = (JButton) e.getSource();
            int i = -1, j = -1;
            for (int row = 0; row < SIZE; row++) {
                for (int col = 0; col < SIZE; col++) {
                    if (buttons[row][col] == pressedButton) {
                        i = row;
                        j = col;
                        break;
                    }
                }
            }
            if (i != -1 && j != -1) {
                saveState();  // Save state before changing the grid value
                grid[i][j] = (grid[i][j] + 1) % 10;  // Just a sample toggle operation
                if (grid[i][j] == 2) grid[i][j] = 9;  // Skip to 9 if the new value is 2
                updateButtonColor(i, j);
            }
        }
    }
    
    private void drawLine(JButton start, JButton end) {
        int startX = -1, startY = -1, endX = -1, endY = -1;

        // Find the grid coordinates of start and end
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (buttons[i][j] == start) {
                    startX = i;
                    startY = j;
                }
                if (buttons[i][j] == end) {
                    endX = i;
                    endY = j;
                }
            }
        }

        if (startX == -1 || startY == -1 || endX == -1 || endY == -1) {
            return; // Coordinates not found
        }

        // Bresenham's line algorithm
        int dx = Math.abs(endX - startX);
        int dy = -Math.abs(endY - startY);
        int sx = startX < endX ? 1 : -1;
        int sy = startY < endY ? 1 : -1;
        int err = dx + dy, e2; // error value e_xy

        while (true) {
            if (startX == endX && startY == endY) break;
            e2 = 2 * err;
            if (e2 >= dy) {
                if (startX == endX) break;
                err += dy;
                startX += sx;
            }
            if (e2 <= dx) {
                if (startY == endY) break;
                err += dx;
                startY += sy;
            }
            // Set this position to red if it's not a green (9)
            if (grid[startX][startY] != 9) {
                grid[startX][startY] = 1;
                buttons[startX][startY].setBackground(COLOR_ONE);
            }
        }
    }

    private void updateButtonColor(int i, int j) {
        switch (grid[i][j]) {
            case 1:
                buttons[i][j].setBackground(COLOR_ONE);
                break;
            case 9:
                buttons[i][j].setBackground(COLOR_NINE);
                break;
            default:
                buttons[i][j].setBackground(COLOR_TWO);
        }
    }

    private void initializeMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        
        // File menu
        JMenu fileMenu = new JMenu("File");
        JMenuItem exportItem = new JMenuItem("Export");
        exportItem.addActionListener(e -> exportArrayToFile());
        fileMenu.add(exportItem);
        JMenuItem clearItem = new JMenuItem("Clear");
        clearItem.addActionListener(e -> clearGrid());
        fileMenu.add(clearItem);
        
        //Edit menu
        JMenu editMenu = new JMenu("Edit");
        JMenuItem undoItem = new JMenuItem("Undo");
        undoItem.addActionListener(e -> undo());
        JMenuItem redoItem = new JMenuItem("Redo");
        redoItem.addActionListener(e -> redo());
        editMenu.add(undoItem);
        editMenu.add(redoItem);

        // Line menu
        JMenu lineMenu = new JMenu("Tools");
        JCheckBoxMenuItem lineModeItem = new JCheckBoxMenuItem("Enable Line");
        lineModeItem.addActionListener(e -> {
            lineMode = lineModeItem.isSelected();
            firstPoint = null; // Reset on mode change
            secondPoint = null;
        });

        lineMenu.add(lineModeItem);

        menuBar.add(fileMenu);
        menuBar.add(lineMenu);
        menuBar.add(editMenu);
        setJMenuBar(menuBar);
    }
    
    private void debugStacks() {
        System.out.println("Undo Stack Size: " + undoStack.size());
        System.out.println("Redo Stack Size: " + redoStack.size());
    }
    
    private void saveState() {
        undoStack.push(copyGrid(grid));
        redoStack.clear();  // Clear the redo stack whenever a new action is performed
    }

    private int[][] copyGrid(int[][] original) {
        int[][] copy = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            System.arraycopy(original[i], 0, copy[i], 0, SIZE);
        }
        return copy;
    }

    private void undo() {
        debugStacks();  // Debug print to see if undo is triggered and stack sizes
        if (!undoStack.isEmpty()) {
            redoStack.push(copyGrid(grid));
            int[][] previous = undoStack.pop();
            restoreGrid(previous);
            //debugStacks();  // Debug after operation
        }
    }

    private void redo() {
        debugStacks();  // Debug print to see if redo is triggered and stack sizes
        if (!redoStack.isEmpty()) {
            undoStack.push(copyGrid(grid));
            int[][] next = redoStack.pop();
            restoreGrid(next);
            //debugStacks();  // Debug after operation
        }
    }

    private void restoreGrid(int[][] state) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                grid[i][j] = state[i][j];
                updateButtonColor(i, j); // Ensure this method sets the button color based on the grid value
            }
        }
    }

    private void clearGrid() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                grid[i][j] = 0;  // Set all to 0
                buttons[i][j].setBackground(COLOR_TWO);  // Update button color to default
            }
        }
    }
    
    private boolean checkValidSpawnPoints() {
        int count = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (grid[i][j] == 9) {
                    count++;
                }
            }
        }
        return count >= 8;
    }

    private void exportArrayToFile() {
        // Prompt the user for the array name
        String arrayName = JOptionPane.showInputDialog(this, "Enter the name for the array:");
        if (arrayName == null || arrayName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Export cancelled, no name provided.", "Export Cancelled", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (checkValidSpawnPoints()) {
        	
            try (PrintWriter out = new PrintWriter(new FileWriter("output.txt"))) {
                // Start with the map insertion syntax
                out.println("maps.put(\"" + arrayName + "\", new int[][] {");
                for (int i = 0; i < grid.length; i++) {
                    out.print("\t{");
                    for (int j = 0; j < grid[i].length; j++) {
                        out.print(grid[i][j]);
                        if (j < grid[i].length - 1) {
                            out.print(", ");
                        }
                    }
                    if (i < grid.length - 1) {
                        out.println("},");
                    } else {
                        out.println("}");
                    }
                }
                out.println("});");
                JOptionPane.showMessageDialog(this, "Array exported successfully.", "Export Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Failed to write to file", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Not enough spawn points", "Export Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GeneratorGUI::new);
    }
}