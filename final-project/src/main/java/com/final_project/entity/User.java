package com.final_project.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String username;
    String password;
    String email;
    String firstName;
    String lastName;
    String address;

    @ManyToMany
    Set<Role> roles;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    Cart cart;

}
