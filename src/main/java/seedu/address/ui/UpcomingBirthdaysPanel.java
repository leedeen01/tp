package seedu.address.ui;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

public class UpcomingBirthdaysPanel extends UiPart<Region> {
    private static final String FXML = "UpcomingBirthdaysPanel.fxml";

    @FXML
    private ListView<String> birthdayListView;

    public UpcomingBirthdaysPanel(List<Person> persons) {
        super(FXML);
        populateBirthdayList(persons);
    }

    private void populateBirthdayList(List<Person> persons) {
        LocalDate now = LocalDate.now();
        LocalDate upperBound = now.plusDays(30);

        List<String> birthdayMessages = persons.stream()
                .filter(p -> {
                    LocalDate birthday = p.getBirthday().getValue();
                    LocalDate nextBirthday = birthday.withYear(now.getYear());
                    if (nextBirthday.isBefore(now)) {
                        nextBirthday = nextBirthday.plusYears(1);
                    }
                    return !nextBirthday.isAfter(upperBound);
                })
                .sorted((a, b) -> {
                    LocalDate aNext = a.getBirthday().getValue().withYear(now.getYear());
                    if (aNext.isBefore(now)) aNext = aNext.plusYears(1);
                    LocalDate bNext = b.getBirthday().getValue().withYear(now.getYear());
                    if (bNext.isBefore(now)) bNext = bNext.plusYears(1);
                    return aNext.compareTo(bNext);
                })
                .map(p -> {
                    String name = p.getName().fullName;
                    LocalDate bday = p.getBirthday().getValue();
                    LocalDate next = bday.withYear(now.getYear());
                    if (next.isBefore(now)) next = next.plusYears(1);
                    return String.format("%s - %s", name, next);
                })
                .collect(Collectors.toList());

        birthdayListView.getItems().setAll(birthdayMessages);
    }
}
