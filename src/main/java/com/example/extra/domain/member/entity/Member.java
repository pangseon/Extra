package com.example.extra.domain.member.entity;

import com.example.extra.domain.tattoo.entity.Tattoo;
import com.example.extra.global.entity.BaseEntity;
import com.example.extra.global.enums.UserRole;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TB_MEMBER")
@Entity
@ToString
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
        nullable = false,
        unique = true
    )
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Size(min = 1, max = 10)
    private String name;

    @Column(nullable = false)
    private Boolean sex;

    @Column(nullable = false)
    private LocalDate birthday;

    @Column(nullable = false)
    private String home;

    @Column(nullable = false)
    private Float height;

    @Column(nullable = false)
    private Float weight;

    @Column(nullable = false)
    @Size(max = 15)
    private String phone;

    @Column
    private String introduction;

    @Column
    private String license;

    @Column
    private String pros;

    @Column
    @Size(max = 10)
    private String bank;

    @Column
    @Size(max = 30)
    private String accountNumber;

    @Column
    private String refreshToken;

    // 계정 권한 설정
    // 공고글의 Role과 이름이 겹칠 것 같아 다른 네이밍 생각 필요 ex) authority | auth
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @OneToOne(
        mappedBy = "member",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @ToString.Exclude
    private Tattoo tattoo;

//    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
//    private final List<ApplicationRequestCompany> applicationRequestCompanyList = new ArrayList<>();
//
//    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
//    private final List<ApplicationRequestMember> applicationRequestMemberList = new ArrayList<>();
//
//    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
//    private final List<AttendanceManagement> attendanceManagementList = new ArrayList<>();
//
//    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
//    private final List<CostumeApprovalBoard> costumeApprovalBoardList = new ArrayList<>();
//
//    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
//    private final List<MemberTerms> memberTermsList = new ArrayList<>();

    @Builder
    public Member(
        String email,
        String password,
        String name,
        Boolean sex,
        LocalDate birthday,
        String home,
        Float height,
        Float weight,
        String phone,
        String introduction,
        String license,
        String pros,
        Tattoo tattoo,
        String bank,
        String accountNumber
    ) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.sex = sex;
        this.birthday = birthday;
        this.home = home;
        this.height = height;
        this.weight = weight;
        this.phone = phone;
        this.introduction = "";
        this.license = "";
        this.pros = "";
        this.tattoo = tattoo;
        this.bank = bank;
        this.accountNumber = accountNumber;
        this.userRole = UserRole.USER;
    }

    public void updateTattoo(Tattoo tattoo) {
        this.tattoo = tattoo;
    }

    public void updateRole() {
        this.userRole = UserRole.ADMIN;
    }

    public void encodePassword(String password) {
        this.password = password;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void deleteRefreshToken() {
        this.refreshToken = null;
    }

    public String tokenId() {
        return this.id.toString() + "M";
    }

}
