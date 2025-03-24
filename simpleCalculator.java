import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;

public class MyCalculator extends JFrame {
    String displayed = "";
    String operator = "";

    public MyCalculator() {
        setTitle("MyCalculator");
        setSize(400, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        mainPanel.setBorder(padding);
        mainPanel.setLayout(new BorderLayout(10, 10));

        JTextField display = new JTextField();
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setFont(new Font("Arial", Font.PLAIN, 30));
        display.setText("0");
        display.setEditable(false);
        display.setFocusable(false);
        display.setPreferredSize(new Dimension(0, 48));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 5, 5));

        String[] buttonLabels = {
                "7", "8", "9", "÷",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+",
                "C", "CE"
        };

        for (String label : buttonLabels) {
            JButton Button = new JButton(label);

            Button.addActionListener(e -> {
                if (label.equals("CE")) {
                    displayed = "";
                    operator = "";
                } else if (label.equals("C")) {
                    if (displayed.length() > 0) {
                        displayed = displayed.substring(0, displayed.length() - 1);
                    }

                    if (displayed.length() == 0) {
                        operator = "";
                    }
                } else if (label.equals("=")) {
                    printMsg("Displayed Operation: " + displayed);
                    printMsg("This is operator: " + operator);

                    // Find the position of the operator in the displayed string (starting from index 1 to handle negative numbers)
                    int opIndex = displayed.indexOf(operator, 1);
                    // Parse the first and second operands from the displayed string
                    Double num1 = Double.parseDouble(displayed.substring(0, opIndex));
                    Double num2 = Double.parseDouble(displayed.substring(opIndex + 1));

                    // Perform the calculation based on the operator
                    switch (operator) {
                        case "+":
                            displayed = formatAnswer(num1 + num2);
                            break;
                        case "-":
                            displayed = formatAnswer(num1 - num2);
                            break;
                        case "*":
                            displayed = formatAnswer(num1 * num2);
                            break;
                        case "÷":
                            if (num2 == 0) {
                                displayed = "Error";
                            } else {
                                displayed = formatAnswer(num1 / num2);
                            }
                            break;
                    }
                } else {
                    // Handle input of digits and operators
                    if (displayed.length() == 0) {
                        // If display is empty, only allow digits and minus sign (for negative numbers)
                        if (!label.equals("-") && !label.matches("[0-9]")) {
                            return;
                        }

                    } else {
                        printMsg("Displayed Value: " + displayed);
                        printMsg("Label: " + label);

                        // Get the last character and first character for validation
                        String lastchar = displayed.substring(displayed.length() - 1);
                        String firstchar = String.valueOf(displayed.charAt(0));

                        printMsg("Last Char: " + lastchar);
                        printMsg("First Char: " + firstchar);

                        // Prevent operations on just a negative sign
                        if (displayed.equals("-") && "+*÷-".contains(label)) {
                            return;
                        }

                        if (operator.length() > 0) {
                            // Prevent multiple operators: if last char is + or - and current input is an operator
                            if ("+-".contains(lastchar) && "+*÷-".contains(label)) {
                                return;
                            }
                            // Special case for * and ÷: only allow minus sign after them (for negative second operand)
                            if ("*÷".contains(lastchar) && !label.matches("[0-9]") && !label.equals("-")) {
                                return;
                            }
                        }

                        // When encountering an operator after a digit
                        if (lastchar.matches("[0-9]") && "+*÷-".contains(label)) {
                            // Only store the first operator to prevent multiple operations
                            if (operator.length() > 0) {
                                return;
                            } else {
                                // Set the operator for later calculation
                                operator = label;
                                printMsg("This the op: " + label);
                            }
                        }

                    }
                    displayed += label;
                }
                display.setText(displayed);
            });

            Button.setFont(new Font("Arial", Font.PLAIN, 20));
            Button.setBackground(Color.darkGray);
            Button.setForeground(Color.white);
            buttonPanel.add(Button);
        }

        mainPanel.add(display, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    // Format the calculation result - show as integer if it's a whole number
    public String formatAnswer(double answer) {
        if (answer == Math.floor(answer)) {
            return String.valueOf((int) answer);
        } else {
            return String.valueOf(answer);
        }
    }

    public void printMsg(String msg) {
        System.out.println(msg);
    }

    public static void main(String[] args) {
        String test = "*";
        System.out.println(test);
        new MyCalculator().setVisible(true);
    }
}
