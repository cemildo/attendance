package ch.cemil.info.yoklama.ui.controller.login;


import ch.cemil.info.yoklama.domain.entity.User;
import ch.cemil.info.yoklama.domain.repository.AddressRepository;
import ch.cemil.info.yoklama.services.CustomUserDetailsService;
import ch.cemil.info.yoklama.services.OrganizationService;
import ch.cemil.info.yoklama.ui.controller.login.view.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class Index {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    @GetMapping("/")
    public String root() {
        //return "home";
        return "login/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login/login";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return  "login/access-denied";
    }

}
