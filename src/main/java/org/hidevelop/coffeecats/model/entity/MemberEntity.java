package org.hidevelop.coffeecats.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member")
@NoArgsConstructor (access = AccessLevel.PROTECTED)
@SecondaryTable(name = "memeber_password", pkJoinColumns = @PrimaryKeyJoinColumn(name = "member_id"))
public class MemberEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private PasswordEntity password;

}
