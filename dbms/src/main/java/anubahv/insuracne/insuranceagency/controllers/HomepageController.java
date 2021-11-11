package anubahv.insuracne.insuranceagency.controllers;


import anubahv.insuracne.insuranceagency.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomepageController {
    SecurityService securityService;

    @Autowired
    public HomepageController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @RequestMapping("/403")
    public String accessDenied(){
        return "accessDenied";
    }
    @RequestMapping({"/","","/homepage"})
    public String homepage(Model model){
        String loggedInUSerName = securityService.findLoggedInUsername();
        if(loggedInUSerName==null){
            model.addAttribute("loginStatus",false);
        }else{
            model.addAttribute("loginStatus",true);
        }
        return "homepage";
    }

    @RequestMapping("/contacts")
    public  String contacts(Model model){
        String loggedInUSerName = securityService.findLoggedInUsername();
        if(loggedInUSerName==null){
            model.addAttribute("loginStatus",false);
        }else{
            model.addAttribute("loginStatus",true);
        }
        return "contacts";
    }
}
