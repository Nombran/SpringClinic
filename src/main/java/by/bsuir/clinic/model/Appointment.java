package by.bsuir.clinic.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "appointments")
public class Appointment extends BaseEntity{

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @Column(name = "diagnosis")
    private String diagnosis;

    @Column(name = "reason")
    private String reason;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private MedicalCard medicalCard;
}
