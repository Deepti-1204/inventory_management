package loginpage;

import javax.swing.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.border.Border;

public class Tools extends JFrame {
    
    private JComboBox<String> dropdownMenu;
    private JButton insertButton;
    private JButton searchButton;
    private JButton displayButton;
    private JButton deleteButton;
    Connection con;
    Statement st;
    PreparedStatement ps;
    ResultSet rs;

    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JButton Insert;
    private JButton Clear;
    private JButton Exit;
    private JButton Search;

    public Tools() {
        initComponents();
        connectToDatabase();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initComponents() {
            // Your existing code for initializing components
            // ...
        
        String[][] allColNames = {
            {"MaterialId", "ToolID", "Description"},
            {"ToolID", "ToolName", "Quantity"},
            {"ToolID", "ToolName", "PowerIntake", "MFG", "weight"}, 
            {"ToolName", "ToolType"}, 
            {"MaterialId", "MaterialName", "SupplierID", "ImportDate", "Quantity"}, 
            {"SupplierID", "SupplierName", "ContactNo", "SupplierLocation"},
            {"FirmID", "OrderID", "ToolID", "DateOfDelivery", "DateOfPurchase", "Quantity", "TotalPrice"},
            {"FirmID", "City"},
            {"City", "Country"},  
            {"FirmID", "Firm_Name", "Email", "OrderType", "Contact"}    
        };
            
        String[] menuItems = {"ToolRequirement", "ToolList", "ToolDetails", "ToolDetailsType", "RawMaterial", "Supplier", "Orders", "FirmLocation", "FirmLocationDiv", "Firm"};
        int[] ColCount = {3, 3, 5, 2, 5, 4, 7, 2, 2, 5};
        String[][] primarykey = {
                {"MaterialId", "ToolID"},
                {"ToolID"},
                {"ToolID"},
                {"ToolName"},
                {"MaterialId"},
                {"SupplierID"},
                {"FirmID", "OrderID", "ToolID"},
                {"FirmID"},
                {"City"},
                {"FirmID"}
        };

        int[] PrimeCount = {2, 1, 1, 1, 1, 1, 3, 1, 1, 1};
        
        
        
        Font font = new Font("Rockwell", Font.BOLD, 30);
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.BLACK);

        dropdownMenu = new JComboBox<>(menuItems);
        dropdownMenu.setFont(font);
        dropdownMenu.setForeground(Color.BLACK);
        dropdownMenu.setPreferredSize(new Dimension(400, 50));

        Border border = BorderFactory.createLineBorder(Color.BLACK, 2);

        insertButton = new JButton("Insert");
        insertButton.setFont(font);
        insertButton.setPreferredSize(new Dimension(200, 50));
        insertButton.setBorder(border);
        insertButton.setForeground(Color.BLACK);

        searchButton = new JButton("Search");
        searchButton.setFont(font);
        searchButton.setPreferredSize(new Dimension(200, 50));
        searchButton.setBorder(border);
        searchButton.setForeground(Color.BLACK);

        displayButton = new JButton("Display");
        displayButton.setBorder(border);
        displayButton.setPreferredSize(new Dimension(200, 50));
        displayButton.setFont(font);
        displayButton.setForeground(Color.BLACK);

        deleteButton = new JButton("Delete");
        deleteButton.setBorder(border);
        deleteButton.setPreferredSize(new Dimension(200, 50));
        deleteButton.setFont(font);
        deleteButton.setForeground(Color.BLACK);
        
        JLabel label1 = new JLabel("Select Table: ");
        label1.setBorder(border);
        label1.setPreferredSize(new Dimension(250, 50));
        label1.setFont(font);
        label1.setForeground(Color.WHITE);

        // Add components to the panel using GridBagLayout
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10); // Add padding

        constraints.gridx=0;
        constraints.gridy=0;
        constraints.gridwidth = 2;
        panel.add(label1, constraints);
        
        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        panel.add(dropdownMenu, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        panel.add(insertButton, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(searchButton, constraints);

        constraints.gridx = 2;
        constraints.gridy = 1;
        panel.add(displayButton, constraints);

        constraints.gridx = 3;
        constraints.gridy = 1;
        panel.add(deleteButton, constraints);

        add(panel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Inventory Management: Tools");
        setSize(1200, 400);
        setLocationRelativeTo(null);
        setResizable(true);
        repaint();
        


        insertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) dropdownMenu.getSelectedItem();
                if (selectedOption.equals("None")) {
                    return;
                }
                int selectedIndex = dropdownMenu.getSelectedIndex();
                int columnCount = ColCount[selectedIndex];
                String[] ColNames = allColNames[selectedIndex];

                // Create a new frame for data entry
                JFrame inputFrame = new JFrame("Data Entry");
                JPanel inputPanel = new JPanel();
                inputPanel.setLayout(new GridLayout(columnCount + 1, 2));

                JTextField[] inputFields = new JTextField[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    JLabel label = new JLabel(ColNames[i]);
                    inputFields[i] = new JTextField(20);
                    inputPanel.add(label);
                    inputPanel.add(inputFields[i]);
                }

                JButton submitButton = new JButton("Submit");
                submitButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String tableName = selectedOption;
                        String[] values = new String[columnCount];

                        for (int i = 0; i < columnCount; i++) {
                            values[i] = inputFields[i].getText();
                        }

                        // Prepare the SQL statement
                        String sql = "INSERT INTO " + tableName + " VALUES (";
                        for (int i = 0; i < columnCount; i++) {
                            sql += "'" + values[i] + "'";
                            if (i < columnCount - 1) {
                                sql += ",";
                            }
                        }
                        sql += ")";
                        System.out.println(sql);
                        try {
                            // Execute the insert query
                            Statement statement = con.createStatement();
                            statement.executeUpdate(sql);
                            JOptionPane.showMessageDialog(inputFrame, "Data inserted into " + tableName);

                            // Clear the input fields
                            for (JTextField textField : inputFields) {
                                textField.setText("");
                            }

                            statement.close();
                        } catch (SQLException ex) {
                            System.out.println("Error!");
                            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
                            JOptionPane.showMessageDialog(inputFrame, ex.getMessage());
                        }
                    }
                });

                inputPanel.add(submitButton);
                inputFrame.add(inputPanel);
                inputFrame.pack();
                inputFrame.setVisible(true);
            }
        });



        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) dropdownMenu.getSelectedItem();
                int selectedIndex = dropdownMenu.getSelectedIndex();
                String[] primaryKeyColumns = primarykey[selectedIndex];
                int primaryKeyCount = PrimeCount[selectedIndex];

                // Create a new frame for search criteria
                JFrame searchFrame = new JFrame("Search");
                JPanel searchPanel = new JPanel();
                searchPanel.setLayout(new GridLayout(primaryKeyCount + 1, 2));

                JTextField[] searchFields = new JTextField[primaryKeyCount];
                for (int i = 0; i < primaryKeyCount; i++) {
                    JLabel label = new JLabel(primaryKeyColumns[i]);
                    searchFields[i] = new JTextField(20);
                    searchPanel.add(label);
                    searchPanel.add(searchFields[i]);
                }

                JButton submitButton = new JButton("Submit");
                submitButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // Build the search query
                        String tableName = selectedOption;
                        String[] searchValues = new String[primaryKeyCount];

                        for (int i = 0; i < primaryKeyCount; i++) {
                            searchValues[i] = searchFields[i].getText();
                        }

                        String sql = "SELECT * FROM " + tableName + " WHERE ";
                        for (int i = 0; i < primaryKeyCount; i++) {
                            sql += primaryKeyColumns[i] + " = '" + searchValues[i] + "'";
                            if (i < primaryKeyCount - 1) {
                                sql += " AND ";
                            }
                        }

                        System.out.println(sql);

                        try {
                            // Execute the search query
                            Statement statement = con.createStatement();
                            ResultSet resultSet = statement.executeQuery(sql);
                            statement.close();
                            JOptionPane.showMessageDialog(searchFrame,"The Data Exists in the Given Table! For Retrieving Values use Display Option");
                            
                        } catch (SQLException ex) {
                            System.out.println("Error!");
                            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
                            JOptionPane.showMessageDialog(searchFrame, ex.getMessage());
                        }
                    }
                });

                searchPanel.add(new JLabel()); // Empty label for layout alignment
                searchPanel.add(submitButton);
                searchFrame.add(searchPanel);
                searchFrame.pack();
                searchFrame.setVisible(true);
            }
        });

        displayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) dropdownMenu.getSelectedItem();
                int selectedIndex = dropdownMenu.getSelectedIndex();
                String[] primaryKeyColumns = primarykey[selectedIndex];
                int primaryKeyCount = PrimeCount[selectedIndex];

                // Create a new frame for search criteria
                JFrame searchFrame = new JFrame("Search");
                JPanel searchPanel = new JPanel();
                searchPanel.setLayout(new GridLayout(primaryKeyCount + 1, 2));

                JTextField[] searchFields = new JTextField[primaryKeyCount];
                for (int i = 0; i < primaryKeyCount; i++) {
                    JLabel label = new JLabel(primaryKeyColumns[i]);
                    searchFields[i] = new JTextField(20);
                    searchPanel.add(label);
                    searchPanel.add(searchFields[i]);
                }

                JButton submitButton = new JButton("Submit");
                submitButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // Build the search query
                        String tableName = selectedOption;
                        String[] searchValues = new String[primaryKeyCount];

                        for (int i = 0; i < primaryKeyCount; i++) {
                            searchValues[i] = searchFields[i].getText();
                        }

                        String sql = "SELECT * FROM " + tableName + " WHERE ";
                        for (int i = 0; i < primaryKeyCount; i++) {
                            sql += primaryKeyColumns[i] + " = '" + searchValues[i] + "'";
                            if (i < primaryKeyCount - 1) {
                                sql += " AND ";
                            }
                        }

                        System.out.println(sql);

                        try {
                            // Execute the search query
                            Statement statement = con.createStatement();
                            ResultSet resultSet = statement.executeQuery(sql);

                            // Process the search results
                            StringBuilder resultBuilder = new StringBuilder();
                            while (resultSet.next()) {
                                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                                    resultBuilder.append(resultSet.getMetaData().getColumnName(i))
                                            .append(": ")
                                            .append(resultSet.getString(i))
                                            .append("\n");
                                }
                                resultBuilder.append("\n");
                            }

                            if (resultBuilder.length() > 0) {
                                JOptionPane.showMessageDialog(searchFrame, resultBuilder.toString(), "Search Results", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(searchFrame, "No results found.", "Search Results", JOptionPane.INFORMATION_MESSAGE);
                            }

                            statement.close();
                        } catch (SQLException ex) {
                            System.out.println("Error!");
                            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
                            JOptionPane.showMessageDialog(searchFrame, ex.getMessage());
                        }
                    }
                });

                searchPanel.add(new JLabel()); // Empty label for layout alignment
                searchPanel.add(submitButton);
                searchFrame.add(searchPanel);
                searchFrame.pack();
                searchFrame.setVisible(true);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        String selectedOption = (String) dropdownMenu.getSelectedItem();
        int selectedIndex = dropdownMenu.getSelectedIndex();
        String[] primaryKeyColumns = primarykey[selectedIndex];
        int primaryKeyCount = PrimeCount[selectedIndex];

        // Create a new frame for delete criteria
        JFrame deleteFrame = new JFrame("Delete");
        JPanel deletePanel = new JPanel();
        deletePanel.setLayout(new GridLayout(primaryKeyCount + 1, 2));

        JTextField[] deleteFields = new JTextField[primaryKeyCount];
        for (int i = 0; i < primaryKeyCount; i++) {
            JLabel label = new JLabel(primaryKeyColumns[i]);
            deleteFields[i] = new JTextField(20);
            deletePanel.add(label);
            deletePanel.add(deleteFields[i]);
        }

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // Build the delete query
                        String tableName = selectedOption;
                        String[] deleteValues = new String[primaryKeyCount];

                        for (int i = 0; i < primaryKeyCount; i++) {
                            deleteValues[i] = deleteFields[i].getText();
                        }

                        String sql = "DELETE FROM " + tableName + " WHERE ";
                        for (int i = 0; i < primaryKeyCount; i++) {
                            sql += primaryKeyColumns[i] + " = '" + deleteValues[i] + "'";
                            if (i < primaryKeyCount - 1) {
                                sql += " AND ";
                            }
                        }

                        System.out.println(sql);

                        try {
                            // Execute the delete query
                            Statement statement = con.createStatement();
                            int affectedRows = statement.executeUpdate(sql);

                            if (affectedRows > 0) {
                                JOptionPane.showMessageDialog(deleteFrame, "Data deleted successfully from " + tableName);
                            } else {
                                JOptionPane.showMessageDialog(deleteFrame, "Data doesn't exist or constraint violation occurred.", "Delete Error", JOptionPane.ERROR_MESSAGE);
                            }

                            statement.close();
                        } catch (SQLException ex) {
                            System.out.println("Error!");
                            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
                            JOptionPane.showMessageDialog(deleteFrame, ex.getMessage(), "Delete Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });

                deletePanel.add(new JLabel()); // Empty label for layout alignment
                deletePanel.add(deleteButton);
                deleteFrame.add(deletePanel);
                deleteFrame.pack();
                deleteFrame.setVisible(true);
            }
        });

        
    }

    private void connectToDatabase() {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            JOptionPane.showMessageDialog(this, "Driver Loaded!");

            try {
                con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
                        "system", "01035");
                JOptionPane.showMessageDialog(this, "Connected to Oracle database!");
            } catch (SQLException ex) {
                Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void refresh() {
        dropdownMenu.setSelectedIndex(0);
    }

    private void InsertActionPerformed(java.awt.event.ActionEvent evt) {
        // Your existing code for inserting data
        // ...
    }

    private void ClearActionPerformed(java.awt.event.ActionEvent evt) {
        // Your existing code for clearing fields
        // ...
    }

    private void ExitActionPerformed(java.awt.event.ActionEvent evt) {
        System.exit(0);
    }

    private void SearchActionPerformed(java.awt.event.ActionEvent evt) {
        // Your existing code for searching data
        // ...
    }
}
