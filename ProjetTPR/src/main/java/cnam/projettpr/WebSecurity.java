package cnam.projettpr;

import cnam.projettpr.entity.Utilisateur;
import cnam.projettpr.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;


@Configuration
@EnableWebSecurity
public class WebSecurity{

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable().authorizeHttpRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin();

        http.logout().logoutUrl("/logout").logoutSuccessUrl("/login").deleteCookies("auth_code").invalidateHttpSession(true);

        http.formLogin().defaultSuccessUrl("/produitsstock");

        return http.build();
    }

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        List<Utilisateur> utils = utilisateurRepository.findAll();

        for (Utilisateur util:utils){
            auth.inMemoryAuthentication()
                    .passwordEncoder(NoOpPasswordEncoder.getInstance())
                    .withUser(util.getLogin()).password(util.getPassword()).roles(util.getRole() == 0 ? "USER":"ADMIN");
        }
    }
}