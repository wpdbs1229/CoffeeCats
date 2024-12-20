package org.hidevelop.coffeecats.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Password {

    @Column(table = "member_password")
    private String password;

    @Column(columnDefinition = "BINARY(16)", table = "member_password")
    private byte[] salt;

    public static Password of(String password, byte[] salt) {
        return new Password(password, salt);
    }
}
