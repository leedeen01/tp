package seedu.address.ui;

import java.util.Comparator;
import java.util.stream.Collectors;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;
import seedu.address.model.person.Premium;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved
     * keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The
     *      issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label birthday;
    @FXML
    private TableView<Premium> premiumList;
    @FXML
    private TableColumn<Premium, String> premiumNameColumn;
    @FXML
    private TableColumn<Premium, String> premiumAmountColumn;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to
     * display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        birthday.setText(person.getBirthday().toString());
        String premiums = person.getPremiumList().premiumList.stream().sorted(Comparator.reverseOrder())
                .map(Premium:: displayPremium).collect(Collectors.joining(", "));

        premiumNameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getPremiumName()));
        premiumAmountColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty("$" + cellData.getValue().getPremiumAmount()));
        premiumList.setItems(FXCollections.observableArrayList(
                person.getPremiumList().premiumList.stream()
                        .filter(premium -> premium.getPremiumName() != null
                                && !premium.getPremiumName().isEmpty())
                        .collect(Collectors.toList())
        ));
        premiumList.setFixedCellSize(25);
        premiumList.prefHeightProperty().bind(Bindings.size(premiumList.getItems()).multiply(25).add(30));
        premiumList.widthProperty()
                .addListener((observable, oldValue, newValue) -> {
                    premiumNameColumn.setPrefWidth(newValue.doubleValue() / 2.03);
                    premiumNameColumn.setResizable(false);
                    premiumAmountColumn.setPrefWidth(newValue.doubleValue() / 2.03);
                    premiumAmountColumn.setResizable(false);
                });

        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> {
                    Label tagLabel = new Label(tag.tagName);
                    tagLabel.setMaxWidth(80);
                    tags.getChildren().add(tagLabel);
                });
    }
}
