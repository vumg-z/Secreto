package com.secret.platform.group_code;

import com.secret.platform.location.Location;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;

@Entity
@Table(name = "group_codes")
public class GroupCodes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("group_code")
    @Column(name = "group_code", nullable = false, unique = true, length = 6)
    private String groupCode;

    @JsonProperty("description")
    @Column(name = "description", nullable = false, length = 49)
    private String description;

    @JsonProperty("type")
    @Column(name = "type", nullable = false, length = 1)
    private String type;

    @ManyToMany
    @JoinTable(
            name = "group_code_members",
            joinColumns = @JoinColumn(name = "group_code_id"),
            inverseJoinColumns = @JoinColumn(name = "location_id")
    )
    private Set<Location> members;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<Location> getMembers() {
        return members;
    }

    public void setMembers(Set<Location> members) {
        this.members = members;
    }

    public void setCode(String s) {
        this.groupCode = s;
    }
}
