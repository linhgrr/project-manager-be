package com.taga.management.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class  User extends BaseEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fullname", length = 100)
    private String fullName;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "address", length = 200)
    private String address;

    @Getter
    @Column(name = "password", length = 200, nullable = false)
    private String password;

    @Column(name = "is_active")
    private boolean active;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(name = "picture_url")
    private String pictureUrl;

    @ManyToMany(mappedBy = "managers", fetch = FetchType.LAZY)
    private List<Project> managedProjects;

    @ManyToMany(mappedBy = "staffs")
    private List<Project> joinedProjects;

    @OneToMany(mappedBy = "assignee", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Task> tasks;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> comments;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PaymentTransaction> paymentTransactions;

    @Column(name = "is_subscribed")
    private boolean isSubscribed = false;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE_"+getRole().getName().toUpperCase()));
        return authorityList;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
