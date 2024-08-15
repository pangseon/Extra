package com.example.extra.domain.member.entity;

import com.example.extra.domain.account.entity.Account;
import com.example.extra.domain.tattoo.entity.Tattoo;
import com.example.extra.global.entity.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tattoo_id")
    @ToString.Exclude
    private Tattoo tattoo;

    @OneToOne(
        optional = false,
        fetch = FetchType.LAZY
    )
    private Account account;

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
        final String name,
        final Boolean sex,
        final LocalDate birthday,
        final String home,
        final Float height,
        final Float weight,
        final String phone,
        final String bank,
        final String accountNumber,
        final Tattoo tattoo,
        final Account account
    ) {
        this.name = name;
        this.sex = sex;
        this.birthday = birthday;
        this.home = home;
        this.height = height;
        this.weight = weight;
        this.phone = phone;
        this.introduction = null;
        this.license = null;
        this.pros = null;
        this.bank = bank;
        this.accountNumber = accountNumber;
        this.tattoo = tattoo;
        this.account = account;
    }

    public void updateTattoo(Tattoo tattoo) {
        this.tattoo = tattoo;
    }

    public String tokenId() {
        return this.id.toString() + "M";
    }
}
