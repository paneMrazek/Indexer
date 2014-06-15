package main.indexer.client.popups;

import main.indexer.client.models.IndexerDataModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class SuggestionsWindow extends JDialog{

    private JButton cancelButton;
    private JButton useSuggestionButton;
    private JList<Object> suggestions;

    private int row;
    private int col;
    private IndexerDataModel model;

    public SuggestionsWindow(JFrame parent, List<String> suggestions, int row, int col, IndexerDataModel model){
        super(parent,"See Suggestions",JDialog.DEFAULT_MODALITY_TYPE);
        this.suggestions = new JList<>(suggestions.toArray());

        this.row = row;
        this.col = col;
        this.model = model;

        createComponents();
    }

    private void createComponents(){
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.addWindowListener(windowAdapter);

        suggestions.setSize(190,100);
        suggestions.addListSelectionListener(selectionListener);
        suggestions.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(suggestions);
        scrollPane.setSize(190, 100);
        this.add(scrollPane, BorderLayout.CENTER);

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(buttonClickListener);

        useSuggestionButton = new JButton("Use Suggestion");
        useSuggestionButton.setEnabled(false);
        useSuggestionButton.addActionListener(buttonClickListener);

        Panel buttonPanel = new Panel();
        buttonPanel.add(cancelButton);
        buttonPanel.add(useSuggestionButton);

        this.add(buttonPanel, BorderLayout.SOUTH);
        this.pack();
    }

    private WindowAdapter windowAdapter = new WindowAdapter() {

        public void windowClosing(WindowEvent e) {
            setVisible(false);
        }
    };

    private ListSelectionListener selectionListener = new ListSelectionListener(){

        @Override
        public void valueChanged(ListSelectionEvent e){
            useSuggestionButton.setEnabled(true);
        }

    };

    private ActionListener buttonClickListener = new ActionListener(){

        public void actionPerformed(ActionEvent e){

            if (e.getSource() == useSuggestionButton){
                model.dataChange(row,col,suggestions.getSelectedValue().toString());
                setVisible(false);
            }else if (e.getSource() == cancelButton) {
                setVisible(false);
            }
        }
    };
}
