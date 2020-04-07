package by.bsuir.clinic.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "roles")
public class Role extends BaseEntity {

    @Column(name = "name")
    private String name;
}
