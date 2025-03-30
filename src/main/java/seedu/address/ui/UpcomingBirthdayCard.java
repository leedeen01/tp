package seedu.address.ui;

import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

/**
 * An UI component that displays summary info of a {@code Person} with an upcoming birthday.
 */
public class UpcomingBirthdayCard extends UiPart<Region> {

    private static final String FXML = "UpcomingBirthdayCard.fxml";

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label birthday;
    @FXML
    private FlowPane tags;
    @FXML
    private Label daysUntil;

    /**
     * Constructs a card displaying the given person's upcoming birthday info.
     *
     * @param person Person whose birthday info is shown.
     */
    public UpcomingBirthdayCard(Person person) {
        super(FXML);
        this.person = person;
        name.setText(person.getName().fullName);
        birthday.setText(person.getBirthday().toString());
        setDaysUntilTextAndStyle(person);
    }

    /**
     * Calculates how many days are left until the person's next birthday and styles the label accordingly.
     *
     * - "Today" and "Tomorrow" are shown in green and bold.
     * - Other days are shown in gray italic.
     */
    private void setDaysUntilTextAndStyle(Person person) {
        LocalDate today = LocalDate.now();
        LocalDate birthday = person.getBirthday().getValue();
        LocalDate nextBirthday = birthday.withYear(today.getYear());

        if (nextBirthday.isBefore(today)) {
            nextBirthday = nextBirthday.plusYears(1);
        }

        long daysLeft = java.time.temporal.ChronoUnit.DAYS.between(today, nextBirthday);

        if (daysLeft == 0) {
            daysUntil.setText("Today! 🎂");
            daysUntil.setStyle("-fx-text-fill: #6eaa7c; -fx-font-weight: bold;");
        } else if (daysLeft == 1) {
            daysUntil.setText("Tomorrow!");
            daysUntil.setStyle("-fx-text-fill: #6eaa7c; -fx-font-weight: bold;");
        } else {
            daysUntil.setText("In " + daysLeft + " days");
            daysUntil.setStyle("-fx-text-fill: #aaaaaa; -fx-font-style: italic;");
        }
    }
}
