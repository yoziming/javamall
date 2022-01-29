package yozi.mall.common.exception;

public class PhoneException extends RuntimeException {
    public PhoneException() {
        super("存在相同的手機號");
    }
}
