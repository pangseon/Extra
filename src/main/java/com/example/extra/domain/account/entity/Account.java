package com.example.extra.domain.account.entity;

import com.example.extra.global.enums.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TB_ACCOUNT")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String email;

    @Column(nullable = false)
    String password;

    @Column
    String refreshToken = null;

    @Column
    String imageUrl;

    @Column
    String folderUrl;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    UserRole userRole;

    @Builder
    public Account(
        final String email,
        final String password,
        final String imageUrl,
        final String folderUrl,
        final UserRole userRole
    ) {
        this.email = email;
        this.password = password;
        this.imageUrl = imageUrl;
        this.folderUrl = folderUrl;
        this.userRole = userRole;
    }

    public void encodePassword(String password) {
        this.password = password;
    }
}
