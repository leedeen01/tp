package seedu.address.ui;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.person.Person;

/**
 * Panel to show upcoming birthdays.
 */
public class BirthdayPanel extends UiPart<Region> {
    private static final String FXML = "BirthdayPanel.fxml";

    @FXML
    private VBox birthdayListContainer;

    /**
     * Creates a new BirthdayPanel and populates it with persons whose birthdays are within the next 30 days.
     *
     * @param persons The full list of persons to filter from.
     */
    public BirthdayPanel(List<Person> persons) {
        super(FXML);
        populateUpcomingBirthdays(persons);
    }

    /**
     * Filters and displays persons whose birthdays are within the next 30 days.
     *
     * @param persons Full list of persons.
     */
    private void populateUpcomingBirthdays(List<Person> persons) {
        LocalDate today = LocalDate.now();
        LocalDate cutoff = today.plusDays(30);

        List<Person> upcoming = persons.stream()
                .filter(p -> {
                    LocalDate birthDate = p.getBirthday().getValue();
                    // Adjust to current year's birthday
                    LocalDate nextBirthday = birthDate.withYear(today.getYear());

                    // If birthday has passed already this year, shift to next year
                    if (nextBirthday.isBefore(today) || nextBirthday.isEqual(today)) {
                        nextBirthday = nextBirthday.plusYears(1);
                    }

                    long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(today, nextBirthday);
                    return daysBetween <= 30;
                })
                .sorted((p1, p2) -> {
                    LocalDate b1 = p1.getBirthday().getValue().withYear(today.getYear());
                    LocalDate b2 = p2.getBirthday().getValue().withYear(today.getYear());
                    return b1.compareTo(b2);
                })
                .collect(Collectors.toList());
        for (Person person : upcoming) {
            Label label = new Label(person.getName().toString() + " - " + person.getBirthday());
            label.setWrapText(true);
            label.setStyle("-fx-text-fill: white;");
            birthdayListContainer.getChildren().add(label);
        }
    }
}
