package view.elements;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import model.Edible;
import view.model.EdibleOverview;

public class OverviewRegisterTableView<S extends EdibleOverview> extends TableView<S> {
    public OverviewRegisterTableView() {
        super(FXCollections.observableArrayList());
        this.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void add(Edible e) {
        this.getItems().add((S) new EdibleOverview(e));
    }

    public ObservableList<S> delete() {
        ObservableList<S> selectedItem = this.getSelectionModel().getSelectedItems();
        ObservableList<S> clone = FXCollections.observableArrayList(selectedItem);
        this.getItems().removeAll(selectedItem);
        return clone;
    }
}
