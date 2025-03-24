package seedu.address.ui;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

/**
 * Panel displaying persons with birthdays in the next 30 days.
 */
public class UpcomingBirthdaysPanel extends UiPart<Region> {
    private static final String FXML = "UpcomingBirthdaysPanel.fxml";

    @FXML
    private ListView<Person> birthdayListView;

    public UpcomingBirthdaysPanel(ObservableList<Person> upcomingBirthdays) {
        super(FXML);
        birthdayListView.setItems(upcomingBirthdays);
        birthdayListView.setCellFactory(listView -> new BirthdayListViewCell());
    }

    class BirthdayListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new UpcomingBirthdayCard(person).getRoot());
            }
        }
    }
}