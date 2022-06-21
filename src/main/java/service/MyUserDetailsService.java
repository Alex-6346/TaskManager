package service;

import entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import repository.UsersRepository;


@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        final UserEntity user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No user with email: " + email));
        System.out.println(user);
        return User.builder()
                .username(email)
                .password(user.getPassword())
                .build();
    }


}

