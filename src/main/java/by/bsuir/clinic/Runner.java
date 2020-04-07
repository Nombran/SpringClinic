package by.bsuir.clinic;

import by.bsuir.clinic.config.PersistenceJPAConfig;
import by.bsuir.clinic.dao.user.UserDaoImpl;
import by.bsuir.clinic.model.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Optional;


public class Runner {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context
                = new AnnotationConfigApplicationContext(PersistenceJPAConfig.class);

        UserDaoImpl userDao = context.getBean(UserDaoImpl.class);
        Optional<User> user = userDao.findByUsername("admin");
        user.ifPresent(u -> System.out.println(u.getUsername()));
//        User user = new User("log2313",
//                "pas234212", "341", UserRole.ADMIN, "A234231");
//        user.setId(7);
//        user.setLogin("artem2123121");
//        userDao.update(user);
    }
}
