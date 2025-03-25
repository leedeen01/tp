package seedu.address.ui;

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

    /**
     * Creates a panel and sets up its ListView to show upcoming birthdays using {@code UpcomingBirthdayCard}.
     *
     * @param upcomingBirthdays Observable list of persons with upcoming birthdays.
     */
    public UpcomingBirthdaysPanel(ObservableList<Person> upcomingBirthdays) {
        super(FXML);
        birthdayListView.setItems(upcomingBirthdays);
        birthdayListView.setCellFactory(listView -> new BirthdayListViewCell());
    }

    /**
     * A custom {@code ListCell} that displays a {@code Person} using an {@code UpcomingBirthdayCard}.
     */
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
