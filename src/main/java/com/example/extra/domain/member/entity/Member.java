package com.example.extra.domain.member.entity;

import com.example.extra.domain.applicationrequest.entity.ApplicationRequestCompany;
import com.example.extra.domain.applicationrequest.entity.ApplicationRequestMember;
import com.example.extra.domain.attendancemanagement.entity.AttendanceManagement;
import com.example.extra.domain.costumeapprovalboard.entity.CostumeApprovalBoard;
import com.example.extra.domain.memberterms.entity.MemberTerms;
import com.example.extra.domain.tattoo.entity.Tattoo;
import com.example.extra.global.entity.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TB_MEMBER")
@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String name;

    @Column
    private Boolean sex;

    @Column
    private LocalDate birthday;

    @Column
    private String home;

    @Column
    private Float height;

    @Column
    private Float weight;

    @Column
    private String phone;

    @Column
    private String introduction;

    @Column
    private String license;

    @Column
    private String pros;

    @OneToOne(
        mappedBy = "member",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @ToString.Exclude
    private Tattoo tattoo;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private final List<ApplicationRequestCompany> applicationRequestCompanyList = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private final List<ApplicationRequestMember> applicationRequestMemberList = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private final List<AttendanceManagement> attendanceManagementList = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private final List<CostumeApprovalBoard> costumeApprovalBoardList = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private final List<MemberTerms> memberTermsList = new ArrayList<>();

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
        Tattoo tattoo
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
    }

    public void updateTattoo(Tattoo tattoo) {
        this.tattoo = tattoo;
    }
}
