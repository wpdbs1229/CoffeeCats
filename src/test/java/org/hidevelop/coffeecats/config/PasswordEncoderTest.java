package org.hidevelop.coffeecats.config;

import org.hidevelop.coffeecats.model.entity.MemberEntity;
import org.hidevelop.coffeecats.model.entity.Password;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class PasswordEncoderTest {

    private static final Logger log = LoggerFactory.getLogger(PasswordEncoderTest.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("비밀번호를 암호화 할 수 있다.")
    void DisplayName() throws Exception {

        //given
        String email = "test@test.com";
        String password = "password";

        //when
        Password encryptedPassword = passwordEncoder.encrypt(email, password);

        //then
        log.info("password : " + password);
        log.info("encryptedPassword : " + encryptedPassword.getPassword());
    }

    @Test
    @DisplayName("비밀번호가 맞는지 확인할 수 있다.")
    void isCorrectPassword(){

        //given
        String email = "test@test.com";
        String inputPassword = "password";

        Password encryptedPassword = passwordEncoder.encrypt(email, inputPassword);

        MemberEntity memberEntity = MemberEntity.builder()
                .memberId(1L)
                .email(email)
                .nickname("nickname")
                .password(encryptedPassword)
                .build();

        //when
        assertThat(passwordEncoder.check(memberEntity, inputPassword)).isTrue();
    }

    @Test
    @DisplayName("비밀번호가 틀린지 확인할 수 있다.")
    void isIncorrectPassword(){

        //given
        String email = "test@test.com";
        String inputPassword = "password";
        String wrongPassword = "wrongPassword";

        Password encryptedPassword = passwordEncoder.encrypt(email, inputPassword);

        MemberEntity memberEntity = MemberEntity.builder()
                .memberId(1L)
                .email(email)
                .nickname("nickname")
                .password(encryptedPassword)
                .build();

        //when
        assertThat(passwordEncoder.check(memberEntity, wrongPassword)).isFalse();
    }

}