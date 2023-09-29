package com.secret.platform.secret;


import com.secret.platform.avatar.Avatar;
import jakarta.persistence.*;
import com.secret.platform.category.Category;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Secret {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateCreated;

    private String text;

    @ManyToOne
    @JoinColumn(name = "avatar_id", referencedColumnName = "id")
    private Avatar avatar;


    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "order_num")
    private Integer orderNum;

    @Column(name = "likes_count")
    private Integer likesCount = 0;


    @PrePersist
    @PreUpdate
    public void handleOrder() {

        this.dateCreated = LocalDateTime.now();

        if (this.text != null) {
            // Assuming the format is always "Secret X", where X is the number.
            String[] parts = this.text.split(" ");
            if (parts.length > 1) {
                try {
                    this.orderNum = Integer.parseInt(parts[1]);
                } catch (NumberFormatException e) {
                    // Handle this if necessary, for instance setting order to null
                    // or logging an error.
                    this.orderNum = null;
                }
            }
        }
    }


    // getters and setters
    public void setText(String text) {
        this.text = text;
    }


    public String getText() {
        return this.text;
    }


    public String getDateCreated() {
        if (this.dateCreated != null) {
            return this.dateCreated.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        return null;  // or you could return an empty string or a default value
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Long getId() {
        return this.id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public Avatar getAvatar() {
        return this.avatar;
    }

    public void incrementLikes() {
        this.likesCount++;
    }

    public int getLikesCount() {
        return likesCount;
    }
}
