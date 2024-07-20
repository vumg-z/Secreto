package com.secret.platform.option_set;

import com.secret.platform.options.Options;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "option_sets")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OptionSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private Date effDate;
    private Date termDate;
    private String crDateEmpl;
    private String modDateEmpl;

    @ManyToMany
    @JoinTable(
            name = "option_set_options",
            joinColumns = @JoinColumn(name = "option_set_id"),
            inverseJoinColumns = @JoinColumn(name = "option_id")
    )
    private List<Options> options;
}

