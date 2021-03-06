package by.bsuir.clinic.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "medical_cards")
public class MedicalCard extends BaseEntity{

    @OneToOne(mappedBy = "medicalCard")
    private Customer customer;

    @Column(name = "registration")
    private String registration;

    @Column(name = "height")
    private Short height;

    @Column(name = "weight")
    private Short weight;

    @Column(name = "chronic_diseases")
    private String chronicDiseases;

    @Column(name = "gender")
    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @Column(name = "allergies")
    private String allergies;

    @Column(name = "birthday")
    private LocalDate birthday;
}
