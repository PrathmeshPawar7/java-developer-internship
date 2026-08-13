/** Available quiz categories. */
public enum Category {
    JAVA_PROGRAMMING("Java Programming"),
    GENERAL_KNOWLEDGE("General Knowledge"),
    COMPUTER_SCIENCE("Computer Science");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
