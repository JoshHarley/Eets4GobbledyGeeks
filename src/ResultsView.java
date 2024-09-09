import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Map;

public class ResultsView {
    private final Map<String, MenuItem> searchResults;
    private MenuItem menuChoice;
    private boolean searchAgain;

    public ResultsView(Map<String, MenuItem> _searchResults){
        this.searchResults = _searchResults;
        this.searchAgain = false;
    }

    /**
     * A method to display the results that match the user input. Displays in a scrollable panel that dispays
     * the information of each matched item.
     * @return - A JPanel
     */
    public JPanel displayResults(){
        // Create panel to display results
        JPanel resultView = new JPanel();

        // Set panel to display vertically
        resultView.setLayout(new BoxLayout(resultView, BoxLayout.Y_AXIS));

        // For each menu item in the map, retrieve image and information and set to a panel
        for(MenuItem menuItem: searchResults.values()){
            JPanel panel = new JPanel();
            JTextArea textArea = new JTextArea();
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setPreferredSize(new Dimension(500, 300));
            textArea.setText(menuItem.getMenuItemInformation());
            JLabel imgLbl = new JLabel(menuItem.getImage(300, 300));
            imgLbl.setVisible(true);
            panel.setLayout(new FlowLayout());
            panel.add(imgLbl);
            panel.add(textArea);
            resultView.add(panel);
        }

        // Set up a scrollpane to contains the results panel
        JScrollPane displayScrollPane = new JScrollPane(resultView);
        displayScrollPane.setPreferredSize(new Dimension(880, 390));
        displayScrollPane.setVerticalScrollBarPolicy((JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED));
        SwingUtilities.invokeLater(() ->displayScrollPane.getViewport().setViewPosition(new Point(0, 0)));



        // Set up a panel to return
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new FlowLayout());
        resultPanel.add(displayScrollPane, BorderLayout.CENTER);
        resultPanel.add(resultBottomPanel(), BorderLayout.SOUTH);
        return resultPanel;
    }

    private JPanel resultBottomPanel(){
        // Create a panel to return
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setPreferredSize(new Dimension(880, 60));

        // Set up a combobox for menu choice
        JPanel menuChoicePanel = new JPanel();
        menuChoicePanel.setPreferredSize(new Dimension(450, 50));
        JComboBox<Object> menuChoiceCBox = new JComboBox<>(searchResults.keySet().toArray());
        menuChoiceCBox.setPreferredSize(new Dimension(300, 25));
        menuChoiceCBox.addActionListener(e -> {
            this.menuChoice = this.searchResults.get((String) menuChoiceCBox.getSelectedItem());
            MenuSearcher.getContactInfoAndSubmit(this.menuChoice);
        });
        menuChoicePanel.add(menuChoiceCBox);

        // Create a button for searching again/going back
        JPanel backBtnPanel = new JPanel();
        backBtnPanel.setPreferredSize(new Dimension(450, 50));
        JButton backBtn = new JButton("Back");
        backBtn.addActionListener(e -> MenuSearcher.back());
        backBtn.setPreferredSize(new Dimension(200, 25));
        backBtnPanel.add(backBtn);

        // Create a panel border
        bottomPanel.setBorder((BorderFactory.createTitledBorder("Please select which item you would like to order or select back to search again.")));

        // Add components to panel
        bottomPanel.add(Box.createRigidArea(new Dimension(50, 0)), BorderLayout.NORTH);
        bottomPanel.add(menuChoicePanel, BorderLayout.WEST);
        bottomPanel.add(backBtnPanel, BorderLayout.EAST);
        return bottomPanel;
    }

    public MenuItem getMenuChoice(){
        return menuChoice;
    }

    public boolean isSearchAgain(){
        return searchAgain;
    }
}
