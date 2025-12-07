package translation;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.util.Arrays;


// TODO Task D: Update the GUI for the program to align with UI shown in the README example.
//            Currently, the program only uses the CanadaTranslator and the user has
//            to manually enter the language code they want to use for the translation.
//            See the examples package for some code snippets that may be useful when updating
//            the GUI.
public class GUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUI::run);
    }

    private static void run() {
        JPanel countryPanel = new JPanel();

        Translator translator = new CanadaTranslator();
        String[] items = new String[translator.getLanguageCodes().size()];

        JComboBox<String> languageComboBox = new JComboBox<>();
        int i = 0;
        for (String langaugeCode : translator.getLanguageCodes()) {
            items[i++] = langaugeCode;
        }

        // create the JList with the array of strings and set it to allow multiple
        // items to be selected at once.
        JList<String> list = getStringJList(items);

        JPanel languagePanel = new JPanel();
        JTextField languageField = new JTextField(10);
        languagePanel.add(new JLabel("Language:"));
        languagePanel.add(languageField);

        JPanel buttonPanel = new JPanel();
        JButton submit = new JButton("Submit");
        buttonPanel.add(submit);

        JLabel resultLabelText = new JLabel("Translation:");
        buttonPanel.add(resultLabelText);
        JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
        buttonPanel.add(resultLabel);


        // adding listener for when the user clicks the submit button
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String language = languageField.getText();
                String country = countryPanel.toString();

                // for now, just using our simple translator, but
                // we'll need to use the real JSON version later.
                Translator translator = new CanadaTranslator();

                String result = translator.translate(country, language);
                if (result == null) {
                    result = "no translation found!";
                }
                resultLabel.setText(result);

            }

        });

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(countryPanel);
        mainPanel.add(languagePanel);
        mainPanel.add(buttonPanel);

        // place the JList in a scroll pane so that it is scrollable in the UI
        JScrollPane scrollPane = new JScrollPane(list);
        countryPanel.add(scrollPane, 1);

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(countryPanel);

        JFrame frame = new JFrame("Country Name Translator");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


    }

    private static JList<String> getStringJList(String[] items) {
        JList<String> list = new JList<>(items);
        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        list.addListSelectionListener(new ListSelectionListener() {
            /**
             * Called whenever the value of the selection changes.
             *
             * @param e the event that characterizes the change.
             */
            @Override
            public void valueChanged(ListSelectionEvent e) {

                int[] indices = list.getSelectedIndices();
                String[] items = new String[indices.length];
                for (int i = 0; i < indices.length; i++) {
                    items[i] = list.getModel().getElementAt(indices[i]);
                }

                JOptionPane.showMessageDialog(null, "User selected:" +
                        System.lineSeparator() + Arrays.toString(items));

            }
        });
        return list;
    }
}
