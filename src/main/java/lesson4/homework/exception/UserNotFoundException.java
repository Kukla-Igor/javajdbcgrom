package lesson4.homework.exception;

public class UserNotFoundException extends Exception   {
    public UserNotFoundException(String message) {
        super(message);
    }
}
