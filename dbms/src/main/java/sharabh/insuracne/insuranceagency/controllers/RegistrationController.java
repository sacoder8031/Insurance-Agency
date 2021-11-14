package sharabh.insuracne.insuranceagency.controllers;

import sharabh.insuracne.insuranceagency.event.OnRegistrationSuccessEvent;
import sharabh.insuracne.insuranceagency.models.User;
import sharabh.insuracne.insuranceagency.models.VerificationToken;
import sharabh.insuracne.insuranceagency.services.SecurityService;
import sharabh.insuracne.insuranceagency.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Calendar;

@Controller
public class RegistrationController {

    private UserService userService;
    private SecurityService securityService;
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    public RegistrationController(UserService userService, SecurityService securityService, ApplicationEventPublisher eventPublisher) {
        this.userService = userService;
        this.securityService = securityService;
        this.eventPublisher = eventPublisher;
    }

    @GetMapping({"/register"})
    public String register(Model model){
        model.addAttribute("user",new User());
        return "register";
    }

    @GetMapping("/registerError")
    public String registerError(Model model){
        model.addAttribute("user",new User());
        model.addAttribute("emailError",true);
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") User user, BindingResult result, WebRequest request, Model model, RedirectAttributes attributes){
        if(userService.userExists(user.getEmail())){
            return "redirect:/registerError";
        }
        userService.save(user);
        User user1 = userService.findByUsername(user.getEmail());
        try {
            String appUrl = request.getContextPath();
            eventPublisher.publishEvent(new OnRegistrationSuccessEvent(appUrl,user1));
        }catch (Exception re) {
            re.printStackTrace();
        }
        return "verification";
    }

    @GetMapping("/confirmRegistration")
    public String confirmRegistration(WebRequest request, Model model, @RequestParam("token") String token){
        VerificationToken verificationToken = userService.getVerificationToken(token);
        if(verificationToken == null) {
            return "redirect:/403";
        }
        User user = verificationToken.getUser();
        Calendar calendar = Calendar.getInstance();
        if(verificationToken.getExpiryDate().getTime()-calendar.getTime().getTime()<=0){
            return "redirect:/403";
        }
        userService.enableRegisteredUser(user);
        return "redirect:/login";
    }

}
