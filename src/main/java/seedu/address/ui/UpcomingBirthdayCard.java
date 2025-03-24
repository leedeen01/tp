package seedu.address.ui;

import java.time.LocalDate;
import java.util.Comparator;

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

    public UpcomingBirthdayCard(Person person) {
        super(FXML);
        this.person = person;
        name.setText(person.getName().fullName);
        birthday.setText("Birthday: " + person.getBirthday().toString());
        setDaysUntilTextAndStyle(person);
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

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
