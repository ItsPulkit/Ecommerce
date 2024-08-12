package com.project.ecommerce.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User implements UserDetails {
  @Id
  private String userId;
  @Column(name = "user_name")
  private String name;

  @Column(name = "user_email", unique = true)
  private String email;

  @Column(name = "user_password", length = 5000)
  private String password;

  private String gender;

  @Column(length = 1000)
  private String about;

  @Column(name = "user_image_name")
  private String imageName;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
  private List<Order> orders = new ArrayList<>();

  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private Set<Role> roles = new HashSet<>();

  @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
  private Cart cart;

  // private String provider;

  // must have to implement
  // TODO:
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    Set<SimpleGrantedAuthority> authorities = this.roles.stream()
        .map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toSet());
    return authorities;
  }

  @Override
  public String getUsername() {
    return this.email;
  }

  @Override
  public String getPassword() {

    return this.password;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
