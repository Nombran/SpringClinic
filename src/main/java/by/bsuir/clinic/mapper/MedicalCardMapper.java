package by.bsuir.clinic.mapper;

import by.bsuir.clinic.dao.appointment.AppointmentDao;
import by.bsuir.clinic.dao.user.UserDao;
import by.bsuir.clinic.dto.MedicalCardDto;
import by.bsuir.clinic.model.MedicalCard;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MedicalCardMapper extends AbstractMapper<MedicalCard, MedicalCardDto> {
    private final UserDao userDao;
    private final AppointmentDao appointmentDao;
    private final ModelMapper mapper;

    @Autowired
    public MedicalCardMapper(UserDao userDao,
                             AppointmentDao appointmentDao,
                             ModelMapper mapper) {
        super(MedicalCard.class, MedicalCardDto.class);
        this.userDao = userDao;
        this.appointmentDao = appointmentDao;
        this.mapper = mapper;
    }


}
