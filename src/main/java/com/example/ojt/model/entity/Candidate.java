package com.example.ojt.model.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name", columnDefinition = "VARCHAR(50)")
    private String name;
    private boolean status;
    @Column(name = "birthday", columnDefinition = "VARCHAR(20)")
    private String birthday;
    private String address;
    @Column(name = "phone", columnDefinition = "VARCHAR(20)")
    private String phone;
    private String gender;
    @Column(name = "link_linkedin", columnDefinition = "VARCHAR(100)")
    private String linkLinkedin;
    @Column(name = "link_git", columnDefinition = "VARCHAR(100)")
    private String linkGit;
    @Column(name = "position", columnDefinition = "VARCHAR(100)")
    private String position;
    private Date createdAt;
    private Date updatedAt;
    @Column(columnDefinition = "LONGTEXT")
    private String avatar;
    @Column(columnDefinition = "LONGTEXT")
    private String aboutme;
    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;
    @OneToMany(mappedBy = "candidate")
    @JsonIgnore
    private Set<SkillsCandidate> skillsCandidates;
    @OneToMany(mappedBy = "candidate")
    @JsonIgnore
    private Set<EducationCandidate> educationCandidates;
    @OneToMany(mappedBy = "candidate")
    @JsonIgnore
    private Set<ExperienceCandidate> experienceCandidates;
}
