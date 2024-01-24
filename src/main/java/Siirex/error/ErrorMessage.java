package Siirex.error;

import java.util.Objects;

public class ErrorMessage {
    private final int statusCode;
    private final String message;

    public ErrorMessage(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorMessage that = (ErrorMessage) o;
        return statusCode == that.statusCode && message.equals(that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statusCode, message);
    }

    @Override
    public String toString() {
        return "ErrorMessage{" +
                "statusCode=" + statusCode +
                ", message='" + message + '\'' +
                '}';
    }
}
