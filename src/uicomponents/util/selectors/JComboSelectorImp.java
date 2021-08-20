package uicomponents.util.selectors;

import uicomponents.util.SelectableState;
import uicomponents.util.State;
import utility.Maybe;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringJoiner;

public class JComboSelectorImp<T> extends JComboSelector<T> implements ActionListener, PopupMenuListener  {
    private final SelectableState<T> state;
    private final JComboBox<T> comboBox;
    private Maybe<State<T>> previewState = new Maybe<>();

    public JComboSelectorImp(SelectableState<T> state){
        this.state = state;
        comboBox = new JComboBox<>();
        comboBox.addActionListener(this);
        comboBox.addPopupMenuListener(this);

        this.setLayout(new BorderLayout());
        this.add(comboBox, BorderLayout.CENTER);
    }

    @Override
    public void setRenderer(ListCellRenderer<T> renderer) {
        comboBox.setRenderer(renderer);
    }

    @Override
    public void setPreviewState(State<T> previewState) {
        this.previewState = new Maybe<>(previewState);
    }

    @Override
    public void setSelectedIndex(int index) {
        comboBox.setSelectedIndex(index);
    }

    @Override
    public void refreshSelections() {
        comboBox.removeActionListener(this);
        comboBox.removeAllItems();
        for (T option : state.getOptions()){
            comboBox.addItem(option);
            }
        comboBox.setSelectedItem(state.getActive());
        comboBox.addActionListener(this);
    }

    @Override
    protected void addItem(T item) {
        comboBox.addItem(item);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedIndex = comboBox.getSelectedIndex();
        if (selectedIndex >= 0){
            T selectedItem = comboBox.getItemAt(selectedIndex);
            state.update(selectedItem);
        }
    }

    @Override
    public void popupMenuCanceled(PopupMenuEvent e) {
        for (State<T> preview : previewState){
            preview.update(state.getActive());
        }
    }

    @Override
    public void popupMenuWillBecomeVisible(PopupMenuEvent e) {

    }

    @Override
    public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

    }

    @Override
    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "[", "]");
        int itemCount = comboBox.getItemCount();
        for (int i = 0; i < itemCount; i++){
            T item = comboBox.getItemAt(i);
            stringJoiner.add(item.toString());
        }

        String optionString = stringJoiner.toString();

        int selectedIndex = comboBox.getSelectedIndex();
        if (selectedIndex >= 0){
            T selectedItem = comboBox.getItemAt(selectedIndex);
            String selectedItemString = selectedItem.toString();
            int index = optionString.indexOf(selectedItemString);

            StringBuilder newString = new StringBuilder();
            for (int i = 0; i < optionString.length(); i++){
                newString.append(optionString.charAt(i));
                if(i == (index - 1)){
                    newString.append("*");
                }
            }
            optionString = newString.toString();
        }

        return state.toString() + ", " + optionString;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof JComboSelectorImp<?>){
            JComboSelectorImp<?> compare = (JComboSelectorImp<?>) obj;
            if (comboBox.getItemCount() != compare.comboBox.getItemCount()){
                return false;
            }
            for (int i = 0; i < comboBox.getItemCount(); i++){
                if(!comboBox.getItemAt(i).equals(compare.comboBox.getItemAt(i)))
                    return false;
            }
            if (comboBox.getSelectedIndex() != compare.comboBox.getSelectedIndex()){
                return false;
            }
            return (state.equals(compare.state));
        }
        return false;
    }
}
