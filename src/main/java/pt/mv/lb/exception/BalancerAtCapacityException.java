package pt.mv.lb.exception;

public class BalancerAtCapacityException extends RuntimeException {
    public BalancerAtCapacityException(String message) {
        super(message);
    }
}
