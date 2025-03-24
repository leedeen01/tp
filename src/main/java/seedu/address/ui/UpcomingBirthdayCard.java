package seedu.address.ui;

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

    public UpcomingBirthdayCard(Person person) {
        super(FXML);
        this.person = person;
        name.setText(person.getName().fullName);
        birthday.setText("Birthday: " + person.getBirthday().toString());
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
