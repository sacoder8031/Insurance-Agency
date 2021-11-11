package anubahv.insuracne.insuranceagency.controllers;

import anubahv.insuracne.insuranceagency.models.*;
import anubahv.insuracne.insuranceagency.repository.FAQRepository;
import anubahv.insuracne.insuranceagency.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping({"/policy"})
public class PolicyController {

    TransactionService transactionService;
    UserService userService;
    VehicleService vehicleService;
    PropertyService propertyService;
    PolicyService policyService;
    SecurityService securityService;
    FAQRepository faqRepository;
    PolicyRecordService policyRecordService;
    private int id;
    private Model model;

    @Autowired
    public PolicyController(TransactionService transactionService, UserService userService, VehicleService vehicleService, PropertyService propertyService, PolicyService policyService, SecurityService securityService, FAQRepository faqRepository, PolicyRecordService policyRecordService) {
        this.transactionService = transactionService;
        this.userService = userService;
        this.vehicleService = vehicleService;
        this.propertyService = propertyService;
        this.policyService = policyService;
        this.securityService = securityService;
        this.faqRepository = faqRepository;
        this.policyRecordService = policyRecordService;
    }

    @RequestMapping({"","/"})
    public String policyCategories(Model model){
        String loggedInUSerName = securityService.findLoggedInUsername();
        if(loggedInUSerName==null){
            model.addAttribute("loginStatus",false);
        }else{
            model.addAttribute("loginStatus",true);
        }
        return "policy/policyCategories";
    }

    @RequestMapping({"/health"})
    public String healthPolicies(Model model){
        String loggedInUSerName = securityService.findLoggedInUsername();
        if(loggedInUSerName==null){
            model.addAttribute("loginStatus",false);
        }else{
            model.addAttribute("loginStatus",true);
        }
        model.addAttribute("category","health");
        model.addAttribute("policies",policyService.findActiveHealthPolicies());
        model.addAttribute("faqs",faqRepository.findByTopic("health"));
        return "policy/policyListing";
    }

    @RequestMapping({"/property"})
    public String propertyPolicies(Model model){
        String loggedInUSerName = securityService.findLoggedInUsername();
        if(loggedInUSerName==null){
            model.addAttribute("loginStatus",false);
        }else{
            model.addAttribute("loginStatus",true);
        }
        model.addAttribute("category","property");
        model.addAttribute("policies",policyService.findActivePropertyPolicies());
        model.addAttribute("faqs",faqRepository.findByTopic("property"));
        return "policy/policyListing";
    }

    @RequestMapping({"/vehicle"})
    public String vehiclePolicies(Model model){
        String loggedInUSerName = securityService.findLoggedInUsername();
        if(loggedInUSerName==null){
            model.addAttribute("loginStatus",false);
        }else{
            model.addAttribute("loginStatus",true);
        }
        model.addAttribute("category","vehicle");
        model.addAttribute("policies",policyService.findActiveVehiclePolicies());
        model.addAttribute("faqs",faqRepository.findByTopic("vehicle"));
        return "policy/policyListing";
    }
    @RequestMapping({"/life"})
    public String lifePolicies(Model model){
        String loggedInUSerName = securityService.findLoggedInUsername();
        if(loggedInUSerName==null){
            model.addAttribute("loginStatus",false);
        }else{
            model.addAttribute("loginStatus",true);
        }
        model.addAttribute("category","life");
        model.addAttribute("policies",policyService.findActiveLifePolicies());
        model.addAttribute("faqs",faqRepository.findByTopic("life"));
        return "policy/policyListing";
    }

    @RequestMapping({"/{id}"})
    public String policyDetails(@PathVariable("id") int id, Model model){
        String loggedInUSerName = securityService.findLoggedInUsername();
        if(loggedInUSerName==null){
            model.addAttribute("loginStatus",false);
        }else{
            model.addAttribute("loginStatus",true);
        }
        model.addAttribute("policy",policyService.findById(id));
        return "policy/policyDetails";
    }

    @GetMapping({"/{id}/buy"})
    public String buyPolicy(@PathVariable("id") int id,Model model){
        if(securityService.findLoggedInUsername()==null){
            return "redirect:/login";
        }
        model.addAttribute("loginStatus",true);
        User user = userService.findByUsername(securityService.findLoggedInUsername());
        model.addAttribute("loginStatus",true);
        Policy policy = policyService.findById(id);
        model.addAttribute("policy",policy);

        if(policy.getCategory().equals("life") || policy.getCategory().equals("health")){

            return "policy/buyPolicy";
        }else{
            if(policy.getCategory().equals("vehicle")){
                List<Vehicle> vehicles = vehicleService.getVehicleForBuyingPolicy(user.getId());
                if(vehicles.size()==0){
                    return "policy/cantBuy";
                }
                model.addAttribute("vehicles",vehicles);
                model.addAttribute("onVehicle",new Vehicle());
            }else{
                List<Property> properties = propertyService.getByUserForBuyingPolicy(user.getId());
                if( properties.size()==0){
                    return "policy/cantBuy";
                }
                model.addAttribute("onProperty",new Property());
                model.addAttribute("properties",properties);
            }
        }
        // make exclusive search for vehicle and implement check functionality
        return "policy/buyPolicy";
    }

    @PostMapping({"/{id}/buy"})
    public String buyPolicyPost(@PathVariable("id") int id, Model model,@RequestParam("objectId") int objectId){
        Policy policy = policyService.findById(id);
        User user = userService.findByUsername(securityService.findLoggedInUsername());

        model.addAttribute("loginStatus",true);
        // save the policy record
        PolicyRecord policyRecord = new PolicyRecord();
        policyRecord.setPolicyId(policy.getId());
        policyRecord.setUserId(user.getId());
        policyRecord.setStatus("active");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR,1);
        policyRecord.setExpiryDate(new Date(calendar.getTime().getTime()));
        policyRecordService.addRecord(policyRecord);
        policyRecord = policyRecordService.getPolicyRecord(user.getId(),policy.getId());

        // save the transaction
        Transaction transaction = new Transaction();
        transaction.setUserId(user.getId());
        transaction.setRecordId(policyRecord.getId());
        transaction.setAmount(policy.getPremium());
        transaction.setType("buy");
        // generating receipt number
        String alphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
        StringBuilder stringBuilder = new StringBuilder(12);
        for(int i=0;i<12;i++){
            int index = (int)(alphaNumericString.length()*Math.random());
            stringBuilder.append(alphaNumericString.charAt(index));
        }
        String receiptNumber = stringBuilder.toString();
        transaction.setReceiptNumber(receiptNumber);
        transactionService.add(transaction);


        // check for policy category and see if we have to add a reference
        if(policy.getCategory().equals("vehicle")){
            vehicleService.changeRecord(policyRecord.getId(),objectId);
        }
        if(policy.getCategory().equals("property")){
            propertyService.changeRecord(policyRecord.getId(),objectId);
        }
        model.addAttribute("receiptId",receiptNumber);
        return "policy/success";
    }
}
