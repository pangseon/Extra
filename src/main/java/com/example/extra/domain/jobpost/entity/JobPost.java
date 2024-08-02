package com.example.extra.domain.jobpost.entity;

import com.example.extra.domain.company.entity.Company;
import com.example.extra.domain.role.entity.Role;
import com.example.extra.domain.schedule.entity.Schedule;
import com.example.extra.global.entity.BaseEntity;
import com.example.extra.global.enums.Category;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TB_JOBPOST")
@Entity
public class JobPost extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String dramaTitle;

    @Column
    private String gatheringLocation;

    @Column
    private LocalDateTime gatheringTime;

    @Column
    private Boolean status;

    @Column
    private Integer hourPay;

    @Column
    private Category category;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "company_id",nullable = false)
    private Company company;

    @OneToMany(mappedBy = "jobPost",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Schedule> scheduleList = new ArrayList<>();

    @OneToMany(mappedBy = "jobPost",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Role> roleList = new ArrayList<>();

    @Builder
    public JobPost(
        final String dramaTitle,
        final String gatheringLocation,
        final LocalDateTime gatheringTime,
        final Boolean status,
        final Integer hourPay,
        final Category category,
        final Company company
    ){
        this.dramaTitle = dramaTitle;
        this.gatheringLocation = gatheringLocation;
        this.gatheringTime = gatheringTime;
        this.status = status;
        this.hourPay = hourPay;
        this.category = category;
        this.company = company;
    }
    public void updateJobPost(
         String dramaTitle,
         String gatheringLocation,
         LocalDateTime gatheringTime,
         Boolean status,
         Integer hourPay,
         Category category
    ){
        this.dramaTitle = dramaTitle;
        this.gatheringLocation = gatheringLocation;
        this.gatheringTime = gatheringTime;
        this.status = status;
        this.hourPay = hourPay;
        this.category = category;
    }

}
