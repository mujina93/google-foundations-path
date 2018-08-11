/*
* File: CurrencyConverter.java
* ----------------------------
* This program implements a simple currency converter.
*/
import acm.gui.*;
import acm.program.*;
import java.awt.event.*;
import javax.swing.*;

public class CurrencyConverter extends Program {
    /** Initialize the graphical user interface */
    public void init() {
        setLayout(new TableLayout(3, 2));
        currencyTable = new CurrencyTable();
        leftChooser = new JComboBox(currencyTable.getCurrencyNames());
        leftChooser.setSelectedItem("US Dollar");
        rightChooser = new JComboBox(currencyTable.getCurrencyNames());
        rightChooser.setSelectedItem("Euro");
        leftField = new DoubleField();
        leftField.setFormat("0.00");
        leftField.setActionCommand("Convert ->");
        leftField.addActionListener(this);
        rightField = new DoubleField();
        rightField.setFormat("0.00");
        rightField.setActionCommand("<- Convert");
        rightField.addActionListener(this);
        add(leftChooser);
        add(rightChooser);
        add(leftField);
        add(rightField);
        add(new JButton("Convert ->"));
        add(new JButton("<- Convert"));
        addActionListeners();
    }
    /** Listen for a button action */
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (cmd.equals("Convert ->")) {
            double fromValue = leftField.getValue();
            double fromRate = getRateFromChooser(leftChooser);
            double toRate = getRateFromChooser(rightChooser);
            double toValue = fromValue * fromRate / toRate;
            rightField.setValue(toValue);
        } else if (cmd.equals("<- Convert")) {
            double fromValue = rightField.getValue();
            double fromRate = getRateFromChooser(rightChooser);
            double toRate = getRateFromChooser(leftChooser);
            double toValue = fromValue * fromRate / toRate;
            leftField.setValue(toValue);
        }
    }
    /* Gets a rate from the specified chooser */
    private double getRateFromChooser(JComboBox chooser) {
        String currencyName = (String) chooser.getSelectedItem();
        return currencyTable.getExchangeRate(currencyName);
    }
    /* Private instance variables */
    private CurrencyTable currencyTable;
    private JComboBox leftChooser;
    private JComboBox rightChooser;
    private DoubleField leftField;
    private DoubleField rightField;
}