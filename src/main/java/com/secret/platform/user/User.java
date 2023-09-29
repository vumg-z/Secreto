package com.secret.platform.user;

import com.secret.platform.avatar.Avatar;
import jakarta.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "avatar_id")
    private Avatar avatar;



    // ... getters and setters ...
}