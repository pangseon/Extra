package com.example.extra.domain.member.entity;

import com.example.extra.domain.tattoo.entity.Tattoo;
import com.example.extra.global.entity.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.Collection;
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
    private String phone;

    @Column
    private String introduction;

    @Column
    private String license;

    @Column
    private String pros;

    @Column
    private String bank;

    @Column
    private String accountNumber;

    // 계정 권한 설정
    // 공고글의 Role과 이름이 겹칠 것 같아 다른 네이밍 생각 필요 ex) authority | auth
    @Column(nullable = false)
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
        this.introduction = introduction;
        this.license = license;
        this.pros = pros;
        this.tattoo = tattoo;
        this.bank = bank;
        this.accountNumber = accountNumber;
        this.userRole = UserRole.USER;
    }

    public void updateTattoo(Tattoo tattoo) {
        this.tattoo = tattoo;
    }
}
