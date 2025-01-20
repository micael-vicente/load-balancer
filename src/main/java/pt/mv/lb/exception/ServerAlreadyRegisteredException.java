package pt.mv.lb.exception;

public class ServerAlreadyRegisteredException extends RuntimeException {
    public ServerAlreadyRegisteredException(String message) {
        super(message);
    }
}
