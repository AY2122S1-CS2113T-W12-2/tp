package seedu.exceptions;

public class IntegerException extends Exception {
    String message;

    public IntegerException(String input) {
        this.message = input;
    }

    public void printMessage() {
        System.out.println(message);
    }
}
