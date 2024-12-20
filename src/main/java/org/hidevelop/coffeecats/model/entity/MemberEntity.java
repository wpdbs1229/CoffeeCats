package org.hidevelop.coffeecats.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hidevelop.coffeecats.model.dto.SignUpReqDto;

@Entity
@Table(name = "member")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor (access = AccessLevel.PROTECTED)
@SecondaryTable(name = "member_password", pkJoinColumns = @PrimaryKeyJoinColumn(name = "member_id"))
public class MemberEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private Password password;


    public static MemberEntity of(SignUpReqDto signUpReqDto, Password password){
        return MemberEntity.builder()
                .email(signUpReqDto.email())
                .nickname(signUpReqDto.nickname())
                .password(password)
                .build();
    }

}
