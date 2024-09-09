import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ContactInfoView implements FocusListener {
    private final MenuItem chosenItem;
    private JTextField nameTxtF, phoneTxtF, otherInfoTxtF;
    private boolean nameCorrect, phoneCorrect;
    private JLabel nameFeedBackLbl, phoneFeedBackLbl;
    private String name, otherInfo;
    private long phoneNumber;


    public ContactInfoView(MenuItem _chosenItem){
        this.chosenItem = _chosenItem;
    }

    public JPanel displayContactInfoForm(){
        // Setup a return panel
        JPanel combinedPanel = new JPanel();
        combinedPanel.setLayout(new BorderLayout());
        combinedPanel.setPreferredSize(new Dimension(880, 480));

        // Setup a panel for collection user contact information
        JPanel contactInfoPanel = new JPanel();
        contactInfoPanel.setLayout(new FlowLayout());
        contactInfoPanel.setPreferredSize(new Dimension(300, 480));
        JLabel nameLbl = new JLabel("Name:", JLabel.LEFT);
        //nameLbl.setHorizontalTextPosition(JLabel.LEFT);
        this.nameTxtF = new JTextField(20);
        this.nameTxtF.addFocusListener(this);
        nameFeedBackLbl = new JLabel("");
        JLabel phoneLbl = new JLabel("Phone number:");
        this.phoneTxtF = new JTextField(20);
        this.phoneTxtF.addFocusListener(this);
        phoneFeedBackLbl = new JLabel("");
        JLabel otherInfoLbl = new JLabel("Specify any customisation below:");
        this.otherInfoTxtF = new JTextField(20);
        //this.otherInfoTxtF.setPreferredSize(new Dimension(100, 100));
        this.otherInfoTxtF.addFocusListener(this);
        contactInfoPanel.add(Box.createRigidArea(new Dimension(300, 25)));
        contactInfoPanel.add(nameLbl);
        contactInfoPanel.add(Box.createRigidArea(new Dimension(300, 5)));
        contactInfoPanel.add(this.nameTxtF);
        contactInfoPanel.add(this.nameFeedBackLbl);
        contactInfoPanel.add(Box.createRigidArea(new Dimension(300, 50)));
        contactInfoPanel.add(phoneLbl);
        contactInfoPanel.add(this.phoneTxtF);
        contactInfoPanel.add(this.phoneFeedBackLbl);
        contactInfoPanel.add(Box.createRigidArea(new Dimension(300, 50)));
        contactInfoPanel.add(otherInfoLbl);
        contactInfoPanel.add(this.otherInfoTxtF);

        // Setup a panel for displaying the chosen menu item
        JPanel itemDisplayPanel = new JPanel();
        itemDisplayPanel.setLayout(new FlowLayout());

        JLabel imageLbl = new JLabel(this.chosenItem.getImage(300, 300));
        imageLbl.setPreferredSize(new Dimension(300, 300));

        JTextArea textArea = new JTextArea(chosenItem.getMenuItemInformation());
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setPreferredSize(new Dimension(300, 300));

        itemDisplayPanel.add(imageLbl);
        itemDisplayPanel.add(textArea);

        // Set up a button for submitting the order
        JButton submitBtn = new JButton("Submit");
        submitBtn.setPreferredSize(new Dimension(900, 25));
        submitBtn.addActionListener(e -> {
            if(this.nameCorrect && this.phoneCorrect){
                submitOrder();
            }
        });
        combinedPanel.setBorder((BorderFactory.createTitledBorder("To place an order for " + chosenItem.getMenuItemName() +
                ", fill in the form below.")));
        combinedPanel.add(Box.createRigidArea(new Dimension(900, 50)), BorderLayout.NORTH);
        combinedPanel.add(contactInfoPanel, BorderLayout.WEST);
        combinedPanel.add(itemDisplayPanel, BorderLayout.EAST);
        combinedPanel.add(submitBtn, BorderLayout.SOUTH);
        return combinedPanel;
    }

    public void submitOrder() {
        Geek geek = new Geek(getName(), getPhoneNumber());
        String filePath = geek.getName().replace(" ","_") + "_" + this.chosenItem.getMenuItemIdentifier()+".txt";
        Path path = Path.of(filePath);
        String lineToWrite = "Order details:\n\t" +
                "Name: " + geek.getName()+
                " (0" + geek.getOrderNumber()+")";
        if(this.chosenItem.getMenuItemIdentifier() == 0) lineToWrite += "\n\nCUSTOM ORDER...\n" + this.chosenItem.getMenuItemInformation();
        else lineToWrite += "\n\tItem: " + this.chosenItem.getMenuItemName() + " ("+this.chosenItem.getMenuItemIdentifier() + ")\n\n";
        lineToWrite += "Customisation:\n" +
                getOtherInfo();
        try {
            Files.writeString(path, lineToWrite);
        }catch (IOException io){
            System.out.println("Order could not be placed. \nError message: "+io.getMessage());
            System.exit(0);
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        if(e.getSource() == this.nameTxtF){
            if(this.nameTxtF.getText().matches(".*\\d.*")){
                this.nameFeedBackLbl.setText("Name can't contain numbers");
                nameCorrect = false;
            } else {
                this.name = this.nameTxtF.getText();
                nameCorrect = true;
            }
        }

        if(e.getSource() == this.phoneTxtF){
            long phoneNumber=0;
            try {
                phoneNumber = Long.parseLong(this.phoneTxtF.getText());
            } catch (NumberFormatException nf) {
                this.phoneFeedBackLbl.setText("Phone number must not contain letters");
                this.phoneCorrect = false;
            }
            int length = String.valueOf(phoneNumber).length();
            if(length!=9) {
                this.phoneFeedBackLbl.setText("Invalid entry. Please enter your 10-digit phone number in the format 0412 123 345.");
                this.phoneCorrect = false;
            }
            this.phoneNumber = phoneNumber;
            this.phoneCorrect = true;
        }

        if(e.getSource() == this.otherInfoTxtF){
            this.otherInfo = this.otherInfoTxtF.getText();
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if(e.getSource() == this.nameTxtF){
            if(this.nameTxtF.getText().matches(".*\\d.*")){
                this.nameFeedBackLbl.setText("Name can't contain numbers");
                nameCorrect = false;
            } else {
                this.name = this.nameTxtF.getText();
                nameCorrect = true;
            }
        }

        if(e.getSource() == this.phoneTxtF){
            long phoneNumber=0;
            try {
                phoneNumber = Long.parseLong(this.phoneTxtF.getText());
            } catch (NumberFormatException nf) {
                this.phoneFeedBackLbl.setText("Phone number must not contain letters");
                this.phoneCorrect = false;
            }
            int length = String.valueOf(phoneNumber).length();
            if(length!=9) {
                this.phoneFeedBackLbl.setText("Invalid entry. Please enter your 10-digit phone number in the format 0412 123 345.");
                this.phoneCorrect = false;
            }
            this.phoneNumber = phoneNumber;
            this.phoneCorrect = true;
        }

        if(e.getSource() == this.otherInfoTxtF){
            this.otherInfo = this.otherInfoTxtF.getText();
        }
    }

    private String getName(){ return this.name; }

    private long getPhoneNumber(){ return this.phoneNumber; }
    private String getOtherInfo(){ return this.otherInfo; }
}
