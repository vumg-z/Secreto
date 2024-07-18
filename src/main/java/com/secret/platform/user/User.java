package com.secret.platform.user;

import jakarta.persistence.*;

@Entity
@Table(name = "\"user\"")  // Quoting the user table name
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "avatar_id")
    private Long avatarId;

    // Other fields and methods
}
