package anubahv.insuracne.insuranceagency.controllers;

import anubahv.insuracne.insuranceagency.models.*;
import anubahv.insuracne.insuranceagency.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class UserProfileController {
    UserService userService;
    SecurityService securityService;
    PropertyService propertyService;
    VehicleService vehicleService;
    PolicyRecordService policyRecordService;
    PropertyClaimsServices propertyClaimsServices;
    VehicleClaimsService vehicleClaimsService;
    HealthClaimServices healthClaimServices;
    LifeInsuranceClaimService lifeInsuranceClaimService;
    StorageService storageService;
    TransactionService transactionService;

    @Autowired
    public UserProfileController(UserService userService, SecurityService securityService, PropertyService propertyService, VehicleService vehicleService, PolicyRecordService policyRecordService, PropertyClaimsServices propertyClaimsServices, VehicleClaimsService vehicleClaimsService, HealthClaimServices healthClaimServices, LifeInsuranceClaimService lifeInsuranceClaimService, StorageService storageService, TransactionService transactionService) {
        this.userService = userService;
        this.securityService = securityService;
        this.propertyService = propertyService;
        this.vehicleService = vehicleService;
        this.policyRecordService = policyRecordService;
        this.propertyClaimsServices = propertyClaimsServices;
        this.vehicleClaimsService = vehicleClaimsService;
        this.healthClaimServices = healthClaimServices;
        this.lifeInsuranceClaimService = lifeInsuranceClaimService;
        this.storageService = storageService;
        this.transactionService = transactionService;
    }

    @RequestMapping("/self")
    public String profilePage(Model model){
        String loggedInUserName = securityService.findLoggedInUsername();
        if(loggedInUserName==null){
            return "redirect:/login";
        }
        model.addAttribute("loginStatus",true);
        User user =  userService.findByUsername(loggedInUserName);
        model.addAttribute("user",user);
        return "profile/userprofile";
    }

    @RequestMapping("/self/property")
    public String propertyPage(Model model){
        String loggedInUserName = securityService.findLoggedInUsername();
        if(loggedInUserName==null){
            return "redirect:/login";
        }
        model.addAttribute("loginStatus",true);
        User user = userService.findByUsername(loggedInUserName);
        List<Property> properties = propertyService.getByUser(user.getId());
        model.addAttribute("properties",properties);
        return "profile/property";
    }

    @GetMapping({"/self/property/add"})
    public String addProperty(Model model){
        String loggedInUserName = securityService.findLoggedInUsername();
        if(loggedInUserName==null){
            return "redirect:/login";
        }
        model.addAttribute("loginStatus",true);
        model.addAttribute("property",new Property());
        return "profile/addProperty";
    }

    @PostMapping("/self/property/add")
    public String addProperty(@RequestParam("file")MultipartFile file,@ModelAttribute Property property,Model model){
        String loggedInUserName = securityService.findLoggedInUsername();
        if(loggedInUserName==null){
            return "redirect:/login";
        }
        if(file.isEmpty() || property.getName()==null){
            model.addAttribute("link","/self/property/add");
            return "profile/formFailure";
        }
        storageService.uploadFile(file,loggedInUserName,"property/"+property.getName());
        User user = userService.findByUsername(loggedInUserName);
        property.setUserId(user.getId());
        property.setDocumentLocation(storageService.getUploadLocation(file,loggedInUserName,"property/"+property.getName()));
        propertyService.addProperty(property);
        return "redirect:/self/property";
    }

    @RequestMapping("/self/vehicle")
    public String vehiclePage(Model model){
        String loggedInUserName = securityService.findLoggedInUsername();
        if(loggedInUserName==null){
            return "redirect:/login";
        }
        model.addAttribute("loginStatus",true);
        User user = userService.findByUsername(loggedInUserName);
        List<Vehicle> vehicles = vehicleService.getByUser(user.getId());
        model.addAttribute("vehicles",vehicles);
        return "profile/vehicles";
    }

    @GetMapping("/self/vehicle/add")
    public String addVehicle(Model model){
        String loggedInUserName = securityService.findLoggedInUsername();
        if(loggedInUserName==null){
            return "redirect:/login";
        }
        model.addAttribute("loginStatus",true);
        model.addAttribute("vehicle",new Vehicle());
        return "profile/addVehicle";
    }

    @PostMapping("/self/vehicle/add")
    public String addVehicle(@RequestParam("file")MultipartFile file,@ModelAttribute("vehicle")Vehicle vehicle,Model model){
        String loggedInUserName = securityService.findLoggedInUsername();
        if(loggedInUserName==null){
            return "redirect:/login";
        }
        if(file.isEmpty() || vehicle.getVehicleNumber()==null){
            model.addAttribute("link","/self/vehicle/add");
            return "formFailure";
        }
        storageService.uploadFile(file,loggedInUserName,"vehicle/"+vehicle.getVehicleNumber());
        User user = userService.findByUsername(loggedInUserName);
        vehicle.setUserId(user.getId());
        vehicle.setDocumentLocation(storageService.getUploadLocation(file,loggedInUserName,"vehilce/"+vehicle.getVehicleNumber()));
        vehicleService.addVehicle(vehicle);
        return "redirect:/self/vehicle";
    }

    @RequestMapping("/self/policies")
    public String policyRecords(Model model){
        String loggedInUserName = securityService.findLoggedInUsername();
        if(loggedInUserName==null){
            return "redirect:/login";
        }
        model.addAttribute("loginStatus",true);
        User user = userService.findByUsername(loggedInUserName);
        model.addAttribute("policies",policyRecordService.getActiveOfUserWithCategory(user.getId()));
        return "profile/policyRecords";
    }

    @RequestMapping("/self/transactions")
    public String transactions(Model model){
        String loggedInUserName = securityService.findLoggedInUsername();
        if(loggedInUserName==null){
            return "redirect:/login";
        }
        model.addAttribute("loginStatus",true);
        User user = userService.findByUsername(loggedInUserName);
        List<Transaction> transactions = transactionService.getAllOfUser(user.getId());
        model.addAttribute("transactions",transactions);
        return "profile/transactions";
    }

    @RequestMapping("/self/activeclaims")
    public String activeClaims(Model model){
        String loggedInUserName = securityService.findLoggedInUsername();
        if (loggedInUserName==null){
            return "redirect:/login";
        }
        model.addAttribute("loginStatus",true);
        User user = userService.findByUsername(loggedInUserName);
        List<HealthClaim> healthClaims = healthClaimServices.allActiveOfUser(user.getId());
        model.addAttribute("healthClaims",healthClaims);
        List<VehicleClaims> vehicleClaims = vehicleClaimsService.allActiveOfUser(user.getId());
        model.addAttribute("vehicleClaims",vehicleClaims);
        List<PropertyClaim> propertyClaims = propertyClaimsServices.allActiveOfUser(user.getId());
        model.addAttribute("propertyClaims",propertyClaims);
        return "profile/activeClaims";
    }

    @RequestMapping("/self/processedclaims")
    public String processedClaims(Model model){
        String loggedInUserName = securityService.findLoggedInUsername();
        if (loggedInUserName==null){
            return "redirect:/login";
        }
        model.addAttribute("loginStatus",true);
        User user = userService.findByUsername(loggedInUserName);
        List<HealthClaim> healthClaims = healthClaimServices.allProcessedOfUser(user.getId());
        model.addAttribute("healthClaims",healthClaims);
        List<VehicleClaims> vehicleClaims = vehicleClaimsService.allProcessedOfUser(user.getId());
        model.addAttribute("vehicleClaims",vehicleClaims);
        List<PropertyClaim> propertyClaims = propertyClaimsServices.allProcessedOfUser(user.getId());
        model.addAttribute("propertyClaims",propertyClaims);
        return "profile/processedClaims";
    }
}
