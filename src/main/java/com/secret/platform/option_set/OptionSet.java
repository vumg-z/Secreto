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

    // add unique
    @Column(name = "code", nullable = false, length = 10)
    private String code;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "eff_date", nullable = false)
    private Date effDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "term_date")
    private Date termDate;

    @Column(name = "cr_date_empl", length = 50)
    private String crDateEmpl;

    @Column(name = "mod_date_empl", length = 50)
    private String modDateEmpl;

    @ManyToMany
    @JoinTable(
            name = "option_set_options",
            joinColumns = @JoinColumn(name = "option_set_id"),
            inverseJoinColumns = @JoinColumn(name = "option_id")
    )
    @Builder.Default
    private List<Options> options = new ArrayList<>();
}
