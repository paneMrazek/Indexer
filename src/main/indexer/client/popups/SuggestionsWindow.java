package main.indexer.client.popups;

import main.indexer.client.models.IndexerDataModel;
import main.indexer.shared.communication.ClientCommunicator;
import main.indexer.shared.communication.params.ValidateUser_Params;
import main.indexer.shared.communication.results.ValidateUser_Result;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

//public class SuggestionsWindow extends JDialog{
public class SuggestionsWindow extends JFrame{

    private JButton cancelButton;
    private JButton useSuggestionButton;
    private JList<String> suggestions;

    private int row;
    private int col;
    private IndexerDataModel model;

    public SuggestionsWindow(List<String> suggestions, int row, int col, IndexerDataModel model){
        //super(parent,"See Suggestions",JDialog.DEFAULT_MODALITY_TYPE);
        super("See Suggestions");
        this.suggestions = new JList<>();
        suggestions.addAll(suggestions);

        this.row = row;
        this.col = col;
        this.model = model;

        createComponents();
    }

    private void createComponents(){
        this.setResizable(false);
        this.addWindowListener(windowAdapter);
        this.add(suggestions, BorderLayout.CENTER);

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(buttonClickListener);

        useSuggestionButton = new JButton("Use Suggestion");
        useSuggestionButton.addActionListener(buttonClickListener);

        Panel loginPanel = new Panel();
        loginPanel.add(cancelButton);
        loginPanel.add(useSuggestionButton);
    }

    private WindowAdapter windowAdapter = new WindowAdapter() {

        public void windowClosing(WindowEvent e) {
            setVisible(false);
        }
    };

    private ActionListener buttonClickListener = new ActionListener(){

        public void actionPerformed(ActionEvent e){

            if (e.getSource() == useSuggestionButton){

            }else if (e.getSource() == cancelButton) {
                setVisible(false);
            }
        }
    };
}
