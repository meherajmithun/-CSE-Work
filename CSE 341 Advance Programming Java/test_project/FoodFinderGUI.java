// IN THE NAME OF ALLAH

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

class Food {
    String name;
    double price;
    double rating;

    Food(String n, double p, double r) {
        name = n;
        price = p;
        rating = r;
    }

    public String toString() {
        return name + " - $" + price + " - ⭐" + rating;
    }
}

public class FoodFinderGUI {

    JFrame frame;

    JTextField priceField, searchField, ratingField;
    JTextField addNameField, addPriceField, addRatingField;

    JButton filterBtn, resetBtn, addBtn;
    JComboBox<String> sortBox;
    JLabel resultLabel;

    JList<Food> foodList;
    DefaultListModel<Food> listModel;
    JTextArea detailsArea;

    ArrayList<Food> foods = new ArrayList<>();

    public FoodFinderGUI() {
        frame = new JFrame("Campus Food Finder");
        frame.setSize(700, 550);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        // 🔹 TOP PANEL (Filters)
        JPanel topPanel = new JPanel(new GridLayout(3, 1));

        JPanel row1 = new JPanel();
        row1.add(new JLabel("Search:"));
        searchField = new JTextField(10);
        row1.add(searchField);

        row1.add(new JLabel("Max Price:"));
        priceField = new JTextField(5);
        row1.add(priceField);

        JPanel row2 = new JPanel();
        row2.add(new JLabel("Min Rating:"));
        ratingField = new JTextField(5);
        row2.add(ratingField);

        filterBtn = new JButton("Apply Filter");
        row2.add(filterBtn);

        resetBtn = new JButton("Reset");
        row2.add(resetBtn);

        JPanel row3 = new JPanel();
        sortBox = new JComboBox<>(new String[]{
                "Default", "Cheapest", "Highest Rated"
        });
        row3.add(new JLabel("Sort By:"));
        row3.add(sortBox);

        resultLabel = new JLabel("0 items");
        row3.add(resultLabel);

        topPanel.add(row1);
        topPanel.add(row2);
        topPanel.add(row3);

        frame.add(topPanel, BorderLayout.NORTH);

        // 🔹 CENTER LIST
        listModel = new DefaultListModel<>();
        foodList = new JList<>(listModel);
        frame.add(new JScrollPane(foodList), BorderLayout.CENTER);

        // 🔹 BOTTOM PANEL (Details + Add Item)
        JPanel bottomPanel = new JPanel(new GridLayout(2, 1));

        // Details
        detailsArea = new JTextArea();
        detailsArea.setEditable(false);
        detailsArea.setBackground(Color.LIGHT_GRAY);
        bottomPanel.add(detailsArea);

        // Add Item Panel
        JPanel addPanel = new JPanel();
        addPanel.add(new JLabel("Name:"));
        addNameField = new JTextField(8);
        addPanel.add(addNameField);

        addPanel.add(new JLabel("Price:"));
        addPriceField = new JTextField(5);
        addPanel.add(addPriceField);

        addPanel.add(new JLabel("Rating:"));
        addRatingField = new JTextField(5);
        addPanel.add(addRatingField);

        addBtn = new JButton("Add Item");
        addPanel.add(addBtn);

        bottomPanel.add(addPanel);

        frame.add(bottomPanel, BorderLayout.SOUTH);

        // 🔹 DATA
        foods.add(new Food("Burger", 3, 4.5));
        foods.add(new Food("Pizza", 5, 4.2));
        foods.add(new Food("Tea", 1, 4.8));

        showFoods(foods);

        // 🔹 ACTIONS
        filterBtn.addActionListener(e -> applyFilters());
        resetBtn.addActionListener(e -> resetFilters());
        addBtn.addActionListener(e -> addFood());

        foodList.addListSelectionListener(e -> {
            Food f = foodList.getSelectedValue();
            if (f != null) {
                detailsArea.setText(
                        "Name: " + f.name +
                        "\nPrice: $" + f.price +
                        "\nRating: " + f.rating
                );
            }
        });

        frame.setVisible(true);
    }

    void addFood() {
        try {
            String name = addNameField.getText();
            double price = Double.parseDouble(addPriceField.getText());
            double rating = Double.parseDouble(addRatingField.getText());

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Name required!");
                return;
            }

            foods.add(new Food(name, price, rating));
            showFoods(foods);

            // clear fields
            addNameField.setText("");
            addPriceField.setText("");
            addRatingField.setText("");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Invalid input!");
        }
    }

    void applyFilters() {
        String searchText = searchField.getText().toLowerCase();

        double maxPrice = Double.MAX_VALUE;
        double minRating = 0;

        try {
            if (!priceField.getText().isEmpty())
                maxPrice = Double.parseDouble(priceField.getText());

            if (!ratingField.getText().isEmpty())
                minRating = Double.parseDouble(ratingField.getText());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Invalid input!");
            return;
        }

        ArrayList<Food> filtered = new ArrayList<>();

        for (Food f : foods) {
            if (f.name.toLowerCase().contains(searchText)
                    && f.price <= maxPrice
                    && f.rating >= minRating) {
                filtered.add(f);
            }
        }

        String sortType = (String) sortBox.getSelectedItem();

        if (sortType.equals("Cheapest")) {
            Collections.sort(filtered, (a, b) -> Double.compare(a.price, b.price));
        } else if (sortType.equals("Highest Rated")) {
            Collections.sort(filtered, (a, b) -> Double.compare(b.rating, a.rating));
        }

        showFoods(filtered);
    }

    void resetFilters() {
        searchField.setText("");
        priceField.setText("");
        ratingField.setText("");
        sortBox.setSelectedIndex(0);

        showFoods(foods);
    }

    void showFoods(ArrayList<Food> list) {
        listModel.clear();

        if (list.isEmpty()) {
            resultLabel.setText("0 items");
            return;
        }

        for (Food f : list) {
            listModel.addElement(f);
        }

        resultLabel.setText(list.size() + " items found");
    }

    public static void main(String[] args) {
        new FoodFinderGUI();
    }
}