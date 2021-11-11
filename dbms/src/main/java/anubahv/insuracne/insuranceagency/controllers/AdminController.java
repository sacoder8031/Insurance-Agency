package anubahv.insuracne.insuranceagency.controllers;

import anubahv.insuracne.insuranceagency.models.*;
import anubahv.insuracne.insuranceagency.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/admin")
@Controller
public class AdminController {
    PolicyService policyService;
    PolicyRecordService policyRecordService;
    UserService userService;
    SecurityService securityService;
    HealthClaimServices healthClaimServices;
    VehicleClaimsService vehicleClaimsService;
    PropertyClaimsServices propertyClaimsServices;
    LifeInsuranceClaimService lifeInsuranceClaimService;
    VehicleService vehicleService;
    PropertyService propertyService;

    @Autowired
    public AdminController(PolicyService policyService, PolicyRecordService policyRecordService, UserService userService, SecurityService securityService, HealthClaimServices healthClaimServices, VehicleClaimsService vehicleClaimsService, PropertyClaimsServices propertyClaimsServices, LifeInsuranceClaimService lifeInsuranceClaimService, VehicleService vehicleService, PropertyService propertyService) {
        this.policyService = policyService;
        this.policyRecordService = policyRecordService;
        this.userService = userService;
        this.securityService = securityService;
        this.healthClaimServices = healthClaimServices;
        this.vehicleClaimsService = vehicleClaimsService;
        this.propertyClaimsServices = propertyClaimsServices;
        this.lifeInsuranceClaimService = lifeInsuranceClaimService;
        this.vehicleService = vehicleService;
        this.propertyService = propertyService;
    }

    @RequestMapping({"","/"})
    public String adminHome(){
        String loggedInUserName = securityService.findLoggedInUsername();
        if(loggedInUserName==null){
            return "redirect:/login";
        }

        return "admin/adminHome";
    }

    @GetMapping({"/policy/add"})
    public String addPolicy(Model model){
        model.addAttribute("policy",new Policy());
        return "admin/addPolicy";
    }

    @PostMapping("/policy/add")
    public String addPolicy(@ModelAttribute("policy")Policy policy, Model model){
        policyService.addPolicy(policy);
        return "redirect:/admin";
    }

    @GetMapping("/policy/change")
    public String changeStatusOfPolicy(Model model){
        model.addAttribute("policies",policyService.findAll());
        return "admin/changeStatus";
    }

    @PostMapping("/policy/change")
    public String changeStatusOfPolicy(@RequestParam("status")String status,@RequestParam("id") int id){
        policyService.changeExpirationStatus(status,id);
        return "redirect:/admin";
    }

    @RequestMapping("/claims")
    public String seeAllClaims(Model model){
        model.addAttribute("healthClaims",healthClaimServices.getClaimsByStatus("active"));
        model.addAttribute("vehicleClaims",vehicleClaimsService.getClaimsByStatus("active"));
        model.addAttribute("propertyClaims",propertyClaimsServices.getClaimsByStatus("active"));
        model.addAttribute("lifeClaims",lifeInsuranceClaimService.getClaimsByStatus("active"));
        return "admin/claims";
    }

    @GetMapping("/claims/health/{id}/accept")
    public String acceptHealthClaim(@PathVariable("id")int id){
        String loggedInUserName = securityService.findLoggedInUsername();
        if(loggedInUserName==null){
            return "redirect:/login";
        }
        healthClaimServices.changeStatus("processed",id);
        return "redirect:/admin/claims";
    }

    @GetMapping("/claims/vehicle/{id}/accept")
    public String acceptVehicleClaim(@PathVariable("id")int id){
        String loggedInUserName = securityService.findLoggedInUsername();
        if(loggedInUserName==null){
            return "redirect:/login";
        }
        vehicleClaimsService.changeStatus("processed",id);
        VehicleClaims vehicleClaims = vehicleClaimsService.getClaim(id);
        vehicleService.removeRecord(vehicleClaims.getVehicleId());
        return "redirect:/admin/claims";
    }

    @GetMapping("/claims/property/{id}/accept")
    public String acceptPropertyClaim(@PathVariable("id")int id){
        String loggedInUserName = securityService.findLoggedInUsername();
        if(loggedInUserName==null){
            return "redirect:/login";
        }
        propertyClaimsServices.changeStatus("processed",id);
        PropertyClaim propertyClaim = propertyClaimsServices.getClaim(id);
        propertyService.removeRecord(propertyClaim.getPropertyId());
        return "redirect:/admin/claims";
    }

    @GetMapping("/claims/life/{id}/accept")
    public String acceptLifeClaim(@PathVariable("id")int id){
        String loggedInUserName = securityService.findLoggedInUsername();
        if(loggedInUserName==null){
            return "redirect:/login";
        }
        lifeInsuranceClaimService.updateStatus("processed",id);
        LifeInsuranceClaim lifeInsuranceClaim = lifeInsuranceClaimService.get(id);
        PolicyRecord policyRecord = policyRecordService.getPolicyRecord(lifeInsuranceClaim.getRecordId());
        userService.deadUser(policyRecord.getUserId());
        return "redirect:/admin/claims";
    }

    @GetMapping("/claims/health/{id}/reject")
    public String rejectHealthClaim(@PathVariable("id")int id){
        String loggedInUserName = securityService.findLoggedInUsername();
        if(loggedInUserName==null){
            return "redirect:/login";
        }
        healthClaimServices.changeStatus("rejected",id);
        return "redirect:/admin/claims";
    }

    @GetMapping("/claims/life/{id}/reject")
    public String rejectLifeClaim(@PathVariable("id")int id){
        String loggedInUserName = securityService.findLoggedInUsername();
        if(loggedInUserName==null){
            return "redirect:/login";
        }
        lifeInsuranceClaimService.updateStatus("rejected",id);
        return "redirect:/admin/claims";
    }

    @GetMapping("/claims/vehicle/{id}/reject")
    public String rejectVehicleClaim(@PathVariable("id")int id){
        String loggedInUserName = securityService.findLoggedInUsername();
        if(loggedInUserName==null){
            return "redirect:/login";
        }
        vehicleClaimsService.changeStatus("rejected",id);
        VehicleClaims vehicleClaims = vehicleClaimsService.getClaim(id);
        vehicleService.removeRecord(vehicleClaims.getVehicleId());
        return "redirect:/admin/claims";
    }

    @GetMapping("/claims/property/{id}/reject")
    public String rejectPropertyClaim(@PathVariable("id")int id){
        String loggedInUserName = securityService.findLoggedInUsername();
        if(loggedInUserName==null){
            return "redirect:/login";
        }
        propertyClaimsServices.changeStatus("rejected",id);
        PropertyClaim propertyClaim = propertyClaimsServices.getClaim(id);
        propertyService.removeRecord(propertyClaim.getPropertyId());
        return "redirect:/admin/claims";
    }
}
