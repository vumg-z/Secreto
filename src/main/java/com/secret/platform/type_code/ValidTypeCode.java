package com.secret.platform.type_code;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "valid_type_codes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValidTypeCode {

    /*
    typeCode: A two-character alphanumeric code indicating the RA Type Code.
    description: A short description of up to 20 characters for the Type Code.
    note1 to note4: Additional notes or descriptions, each up to 40 characters.
    postingCode: A single alphanumeric character indicating the GL posting table.
    chauffeured: Indicates if the RA is for a chauffeur rental (Y, R, or N).
    reqIns: Indicates if insurance information is required (Y or N).
    raPrintLibraryNumber: The RA print library number to be used.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type_code", nullable = false, length = 2)
    private String typeCode;

    @Column(name = "description", length = 20)
    private String description;

    @Column(name = "note1", length = 40)
    private String note1;

    @Column(name = "note2", length = 40)
    private String note2;

    @Column(name = "note3", length = 40)
    private String note3;

    @Column(name = "note4", length = 40)
    private String note4;

    @Column(name = "posting_code", length = 1)
    private String postingCode;

    @Column(name = "chauffeured", length = 1)
    private String chauffeured;

    @Column(name = "req_ins", length = 1)
    private String reqIns;

    @Column(name = "ra_print_library_number", length = 2)
    private String raPrintLibraryNumber;
}
