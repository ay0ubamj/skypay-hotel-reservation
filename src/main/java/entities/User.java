package entities;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class User {
    private int userId;
    private int balance;

    @Override
    public String toString() {
        return "User {userId=" + userId + ", currentBalance=" + balance + "}";
    }
}
