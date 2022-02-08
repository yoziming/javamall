package yoziming.mall.common.exception;

public class UsernameException extends RuntimeException {
    public UsernameException() {
        super("存在相同的用戶名");
    }
}
