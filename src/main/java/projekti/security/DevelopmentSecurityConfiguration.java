//package projekti.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Profile("!production")
//@Configuration
//@EnableWebSecurity
//public class DevelopmentSecurityConfiguration extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        System.out.println("********** ACTIVE PROFILE: DEVELOPMENT **********");
//        // poistetaan csrf-tarkistus käytöstä h2-konsolin vuoksi
//        http.csrf().disable();
//        // sallitaan framejen käyttö
//        http.headers().frameOptions().sameOrigin();
//
//        http.authorizeRequests()
//                .antMatchers("/register","/register/**").permitAll()
//                .antMatchers(HttpMethod.GET, "/").permitAll()
//                .antMatchers("/h2-console","/h2-console/**").permitAll()
//                .antMatchers(HttpMethod.GET, "/profile/**").permitAll()
//                .anyRequest().authenticated().and()
//                .formLogin()
//                    .loginPage("/login")
//                    .loginProcessingUrl("/processlogin").successForwardUrl("/profile").permitAll().and()
//                .logout().permitAll();
//    }
//
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
