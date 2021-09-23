package view.elements;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import lombok.Getter;
import lombok.Setter;
import model.Edible;
import view.model.EdibleOverview;
import view.model.EdibleSpecificView;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
/** Responsible for correct storage of day registry.
 * */
public class SpecificRegisterTableView<S extends EdibleSpecificView> extends TableView<S> {
    private int elements;
    private static List<Integer> usedEdiblesIds;

    public SpecificRegisterTableView() {
        super(FXCollections.observableArrayList());
        usedEdiblesIds = new ArrayList<>();
        this.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void add(Edible e, double grams) {
        if(!usedEdiblesIds.contains(e.getId())) {
            this.getItems().add((S) new EdibleSpecificView(e, grams));
            usedEdiblesIds.add(e.getId());
            elements++;
        }
    }

    public void removeSelected() {
        ObservableList <S> selectedItem = this.getSelectionModel().getSelectedItems();
        for(S x : selectedItem) {
            usedEdiblesIds.remove(Integer.valueOf(x.getId()));
            elements--;
        }
        this.getItems().removeAll(selectedItem);
    }
}
