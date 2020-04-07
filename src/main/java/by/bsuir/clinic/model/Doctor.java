package by.bsuir.clinic.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "doctors")
public class Doctor extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "specialization")
    private String specialization;

    @Column(name = "category")
    @Enumerated(value = EnumType.STRING)
    private MedicalCategory medicalCategory;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "department_id")
    private Department department;
}
