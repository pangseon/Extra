package com.example.extra.domain.jobpost.entity;

import com.example.extra.domain.company.entity.Company;
import com.example.extra.domain.role.entity.Role;
import com.example.extra.domain.schedule.entity.Schedule;
import com.example.extra.global.entity.BaseEntity;
import com.example.extra.global.enums.Category;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
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

    @Column(nullable = false)
    @Size(max = 30)
    private String title;

    @Column(nullable = false)
    private String gatheringLocation; // varchar(255)

    @Column(nullable = false)
    @Size(max = 30)
    private String gatheringTime;

    @Column(nullable = false)
    private Boolean status = true;

    @Column(nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @OneToMany(mappedBy = "jobPost", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Schedule> scheduleList = new ArrayList<>();

    @OneToMany(mappedBy = "jobPost", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Role> roleList = new ArrayList<>();

    @Builder
    public JobPost(
        final String title,
        final String gatheringLocation,
        final String gatheringTime,
        final Category category,
        final Company company
    ) {
        this.title = title;
        this.gatheringLocation = gatheringLocation;
        this.gatheringTime = gatheringTime;
        this.category = category;
        this.company = company;
    }

    public void updateJobPost(
        String title,
        String gatheringLocation,
        String gatheringTime,
        Boolean status,
        Category category
    ) {
        this.title = title;
        this.gatheringLocation = gatheringLocation;
        this.gatheringTime = gatheringTime;
        this.status = status;
        this.category = category;
    }

}
