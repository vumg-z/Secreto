package com.secret.platform.rental;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "rental_type", discriminatorType = DiscriminatorType.STRING)
public abstract class RentalItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private boolean availability;
    private String category;
    private double price;  // Price for rental
    private LocalDateTime rentalStart;  // Rental start date/time
    private LocalDateTime rentalEnd;    // Rental end date/time
    private String condition;  // Condition/status of the item
    private String location;  // Location of the item
    private String imageUrl;  // URL of the item's image
    private String owner;  // Owner of the item
    private String model3D;  // URL or file path to the 3D model

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "\"year\"")  // Quoting the year column
    private Integer year;

    protected RentalItem(RentalItemBuilder<?> builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.description = builder.description;
        this.availability = builder.availability;
        this.category = builder.category;
        this.price = builder.price;
        this.rentalStart = builder.rentalStart;
        this.rentalEnd = builder.rentalEnd;
        this.condition = builder.condition;
        this.location = builder.location;
        this.imageUrl = builder.imageUrl;
        this.owner = builder.owner;
        this.model3D = builder.model3D;
        this.year = builder.year;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isAvailability() {
        return availability;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public LocalDateTime getRentalStart() {
        return rentalStart;
    }

    public LocalDateTime getRentalEnd() {
        return rentalEnd;
    }

    public String getCondition() {
        return condition;
    }

    public String getLocation() {
        return location;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getOwner() {
        return owner;
    }

    public String getModel3D() {
        return model3D;
    }

    public Integer getYear() {
        return year;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public static abstract class RentalItemBuilder<T extends RentalItemBuilder<T>> {
        private Long id;
        private String name;
        private String description;
        private boolean availability;
        private String category;
        private double price;
        private LocalDateTime rentalStart;
        private LocalDateTime rentalEnd;
        private String condition;
        private String location;
        private String imageUrl;
        private String owner;
        private String model3D;
        private Integer year;

        public T setId(Long id) {
            this.id = id;
            return self();
        }

        public T setName(String name) {
            this.name = name;
            return self();
        }

        public T setDescription(String description) {
            this.description = description;
            return self();
        }

        public T setAvailability(boolean availability) {
            this.availability = availability;
            return self();
        }

        public T setCategory(String category) {
            this.category = category;
            return self();
        }

        public T setPrice(double price) {
            this.price = price;
            return self();
        }

        public T setRentalStart(LocalDateTime rentalStart) {
            this.rentalStart = rentalStart;
            return self();
        }

        public T setRentalEnd(LocalDateTime rentalEnd) {
            this.rentalEnd = rentalEnd;
            return self();
        }

        public T setCondition(String condition) {
            this.condition = condition;
            return self();
        }

        public T setLocation(String location) {
            this.location = location;
            return self();
        }

        public T setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return self();
        }

        public T setOwner(String owner) {
            this.owner = owner;
            return self();
        }

        public T setModel3D(String model3D) {
            this.model3D = model3D;
            return self();
        }

        public T setYear(Integer year) {
            this.year = year;
            return self();
        }

        protected abstract T self();

        public abstract RentalItem build();
    }
}
