package seedu.studmap.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.text.TextFlow;
import seedu.studmap.model.student.Assignment;
import seedu.studmap.model.student.Student;

/**
 * An UI component that displays information of a {@code Student}.
 */
public class StudentCard extends UiPart<Region> {

    private static final String FXML = "StudentListCard.fxml";

    private static final float ATTENDANCE_THRESHOLD = 50;
    private static final float MARK_THRESHOLD = 2;
    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on StudMap level 4</a>
     */

    public final Student student;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private Label module;
    @FXML
    private Label studentId;
    @FXML
    private Label attendanceRate;
    @FXML
    private Label assignmentRate;
    @FXML
    private Label gitName;
    @FXML
    private Label handle;
    @FXML
    private FlowPane tags;
    @FXML
    private FlowPane attendances;
    @FXML
    private FlowPane assignments;

    /**
     * Creates a {@code StudentCode} with the given {@code Student} and index to display.
     */
    public StudentCard(Student student, int displayedIndex) {
        super(FXML);
        this.student = student;
        id.setText(displayedIndex + ". ");
        name.setText(student.getName().fullName);
        phone.setText(student.getPhone().toString());
        email.setText(student.getEmail().toString());
        module.setText(student.getModule().toString());
        studentId.setText(student.getId().toString());
        gitName.setText("Github: " + student.getGitName().toString());
        handle.setText("Telegram: " + student.getTeleHandle().toString());

        Label attendanceRateName = new Label("Attendance: ");
        attendanceRateName.setId("info");
        Label attendanceRateLabel = new Label(String.format("%.0f%%", student.getAttendancePercentage()));

        if (Float.isNaN(student.getAttendancePercentage())) {
            attendanceRateLabel.setStyle("-fx-text-fill: lightgrey;");
            attendanceRateLabel.setText("No Records");
        } else if (student.getAttendancePercentage() >= ATTENDANCE_THRESHOLD) {
            attendanceRateLabel.setId("success");
        } else {
            attendanceRateLabel.setId("fail");
        }

        TextFlow attTextFlow = new TextFlow(attendanceRateName, attendanceRateLabel);
        attendanceRate.setGraphic(attTextFlow);

        Label assignmentRateName = new Label("Unmarked: ");
        attendanceRateName.setId("info");
        Label assignmentRateLabel = new Label(String.format("%d",
                student.getAssignmentUnmarkedCount()));

        if (student.getAssignmentCount() == 0) {
            assignmentRateLabel.setStyle("-fx-text-fill: lightgrey;");
            assignmentRateLabel.setText("No Records");
        } else if (student.getAssignmentUnmarkedCount() >= MARK_THRESHOLD) {
            assignmentRateLabel.setId("fail");
        } else if (student.getAssignmentUnmarkedCount() > 0){
            assignmentRateLabel.setId("caution");
        } else {
            assignmentRateLabel.setId("fail");
        }

        TextFlow assTextFlow = new TextFlow(assignmentRateName, assignmentRateLabel);
        assignmentRate.setGraphic(assTextFlow);
        student.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        student.getAttendances().stream()
                .sorted(Comparator.comparing(attendance -> attendance.className))
                .map(attendance -> {
                    Label x = new Label(attendance.className);
                    x.setId(attendance.hasAttended ? "present" : "absent");
                    return x;
                })
                .forEach(attendance -> attendances.getChildren().add(attendance));
        student.getAssignments().stream()
                .sorted(Comparator.comparing(assignment -> assignment.assignmentName))
                .map(assignment -> {
                    Label x = new Label(assignment.assignmentName);
                    x.setId(Assignment.statusToString(assignment.markingStatus));
                    return x;
                })
                .forEach(assignment -> assignments.getChildren().add(assignment));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StudentCard)) {
            return false;
        }

        // state check
        StudentCard card = (StudentCard) other;
        return id.getText().equals(card.id.getText())
                && student.equals(card.student);
    }
}
