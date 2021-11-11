package anubahv.insuracne.insuranceagency.controllers;

import anubahv.insuracne.insuranceagency.services.FAQService;
import anubahv.insuracne.insuranceagency.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/FAQ")
public class FAQController {
    FAQService faqService;
    SecurityService securityService;

    @Autowired
    public FAQController(FAQService faqService, SecurityService securityService) {
        this.faqService = faqService;
        this.securityService = securityService;
    }

    @RequestMapping({"","/"})
    public String faqMain(Model model){
        String loggedInUSerName = securityService.findLoggedInUsername();
        if(loggedInUSerName==null){
            model.addAttribute("loginStatus",false);
        }else{
            model.addAttribute("loginStatus",true);
        }
        model.addAttribute("faqs",faqService.getAll());
        return "faq/faqs";
    }

    @RequestMapping({"/health"})
    public String faqHealth(Model model){
        String loggedInUSerName = securityService.findLoggedInUsername();
        if(loggedInUSerName==null){
            model.addAttribute("loginStatus",false);
        }else{
            model.addAttribute("loginStatus",true);
        }
        model.addAttribute("faqs",faqService.getHeathFaq());
        return "faq/faqs";
    }

    @RequestMapping({"/property"})
    public String faqProperty(Model model){
        String loggedInUSerName = securityService.findLoggedInUsername();
        if(loggedInUSerName==null){
            model.addAttribute("loginStatus",false);
        }else{
            model.addAttribute("loginStatus",true);
        }
        model.addAttribute("faqs",faqService.getPropertyFaq());
        return "faq/faqs";
    }

    @RequestMapping({"/vehicle"})
    public String faqVehicle(Model model){
        String loggedInUSerName = securityService.findLoggedInUsername();
        if(loggedInUSerName==null){
            model.addAttribute("loginStatus",false);
        }else{
            model.addAttribute("loginStatus",true);
        }
        model.addAttribute("faqs",faqService.getVehicleFaq());
        return "faq/faqs";
    }

    @RequestMapping({"/life"})
    public String faqLife(Model model){
        String loggedInUSerName = securityService.findLoggedInUsername();
        if(loggedInUSerName==null){
            model.addAttribute("loginStatus",false);
        }else{
            model.addAttribute("loginStatus",true);
        }
        model.addAttribute("faqs",faqService.getLifeFaq());
        return "faq/faqs";
    }
}
