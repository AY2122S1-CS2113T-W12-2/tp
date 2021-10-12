package seedu.exceptions;

public class AddException extends Exception {

    String message;

    public AddException(String input) {
        this.message = input;
    }

    public void printMessage() {
        System.out.println(message);
    }

}
