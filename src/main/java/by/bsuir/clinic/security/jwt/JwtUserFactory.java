package by.bsuir.clinic.security.jwt;

import by.bsuir.clinic.dto.UserDto;
import by.bsuir.clinic.model.Status;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {

    public JwtUserFactory() {
    }

    public static JwtUser create(UserDto user) {
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                mapToGrantedAuthorities(user.getRoles()),
                Status.ACTIVE.equals(user.getStatus()),
                user.getUpdated()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(Collection<String> userRoles) {
        return userRoles.stream()
                .map(SimpleGrantedAuthority::new
                ).collect(Collectors.toList());
    }
}
