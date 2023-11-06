package com.zero.studentdashboard.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * This class generates table called University in database.
 */
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class University {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String universityName;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    @JsonIgnore
    @OneToMany(mappedBy = "university", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Course> courses = new ArrayList<>();
}

