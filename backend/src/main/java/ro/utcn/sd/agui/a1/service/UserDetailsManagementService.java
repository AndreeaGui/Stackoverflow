package ro.utcn.sd.agui.a1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.utcn.sd.agui.a1.persistence.RepositoryFactory;

import java.util.Collections;
@Service
@RequiredArgsConstructor
public class UserDetailsManagementService implements UserDetailsService {
    private final RepositoryFactory repositoryFactory;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ro.utcn.sd.agui.a1.entity.User user = repositoryFactory.createUserRepository().findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Unknown user!"));
        return new User(user.getUsername(), user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getType())));
    }

    @Transactional
    public ro.utcn.sd.agui.a1.entity.User loadCurrentUser() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return repositoryFactory.createUserRepository().findByUsername(name).orElseThrow(() -> new UsernameNotFoundException("Unknown user!"));
    }
}
