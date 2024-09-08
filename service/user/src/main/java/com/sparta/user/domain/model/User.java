package com.sparta.user.domain.model;

import com.sparta.user.domain.model.vo.UserRole;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.SQLRestriction;

@Slf4j
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "p_users")
@SQLRestriction(value = "is_deleted is NULL")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Column(nullable = false)
    private Boolean isDelete;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "shipping_manager_id")
    private ShippingManager shippingManager;

    @Builder(access = AccessLevel.PROTECTED)
    public User(String password, UserRole role, ShippingManager shippingManager, String username, String email) {
        this.isDelete = false;
        this.password = password;
        this.role = role;
        this.shippingManager = shippingManager;
        this.username = username;
        this.email = email;
    }
}
