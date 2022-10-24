package seedu.studmap.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.studmap.model.student.*;
import seedu.studmap.model.tag.Tag;
import seedu.studmap.model.util.SampleDataUtil;

/**
 * A utility class to help with building Student objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_ID = "A1234567B";
    public static final String DEFAULT_GIT = "GitUser";
    public static final String DEFAULT_TELE = "@CS2103T";

    private Name name;
    private Phone phone;
    private Email email;
    private StudentID id;
    private GitName gitName;
    private TeleHandle teleHandle;
    private Address address;
    private Set<Tag> tags;
    private Set<Attendance> attendances;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        id = new StudentID(DEFAULT_ID);
        gitName = new GitName(DEFAULT_GIT);
        teleHandle = new TeleHandle(DEFAULT_TELE);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
        attendances = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code studentToCopy}.
     */
    public PersonBuilder(Student studentToCopy) {
        name = studentToCopy.getName();
        phone = studentToCopy.getPhone();
        email = studentToCopy.getEmail();
        address = studentToCopy.getAddress();
        id = studentToCopy.getId();
        gitName = studentToCopy.getGitName();
        teleHandle = studentToCopy.getTeleHandle();
        tags = new HashSet<>(studentToCopy.getTags());
        attendances = new HashSet<>(studentToCopy.getAttendances());
    }

    /**
     * Sets the {@code Name} of the {@code Student} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Student} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Parses the {@code classNames} which the user has attended into a
     * {@code Set<Attendance>} and adds it to the {@code Student} that we are building.
     */
    public PersonBuilder addAttended(String ... classNames) {
        this.attendances.addAll(SampleDataUtil.getAttendedSet(classNames));
        return this;
    }

    /**
     * Parses the {@code classNames} which the user has attended into a
     * {@code Set<Attendance>} and adds it to the {@code Student} that we are building.
     */
    public PersonBuilder addNotAttended(String ... classNames) {
        this.attendances.addAll(SampleDataUtil.getNotAttendedSet(classNames));
        return this;
    }

    public PersonBuilder setAttended(Set<Attendance> attendances) {
        this.attendances = attendances;
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Student} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Student} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Student} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    public PersonBuilder withId(String id) {
        this.id = new StudentID(id);
        return this;
    }

    public PersonBuilder withGitName(String name) {
        this.gitName = new GitName(name);
        return this;
    }

    public PersonBuilder withTeleHandle(String teleHandle) {
        this.teleHandle = new TeleHandle(teleHandle);
        return this;
    }

    /**
     * Builds the student using the given parameters.
     * @return New Student.
     */
    public Student build() {
        StudentData studentData = new StudentData();
        studentData.setName(this.name);
        studentData.setPhone(this.phone);
        studentData.setEmail(this.email);
        studentData.setAddress(this.address);
        studentData.setId(this.id);
        studentData.setGitUser(this.gitName);
        studentData.setTeleHandle(this.teleHandle);
        studentData.setTags(this.tags);
        studentData.setAttendances(this.attendances);
        return new Student(studentData);
    }

}
