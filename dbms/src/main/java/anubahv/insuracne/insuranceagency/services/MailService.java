package anubahv.insuracne.insuranceagency.services;

import anubahv.insuracne.insuranceagency.models.PolicyRecord;
import anubahv.insuracne.insuranceagency.models.User;
import anubahv.insuracne.insuranceagency.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MailService {
    private UserRepository userRepository;
    private MailSender mailSender;

    @Autowired
    public MailService(UserRepository userRepository, MailSender mailSender) {
        this.userRepository = userRepository;
        this.mailSender = mailSender;
    }

    public void sendExpirationMail(){
        List<Pair<String,Integer>>mailDetails = userRepository.getExpiryMailDetails();
        for(int i=0;i<mailDetails.size();i++){
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(mailDetails.get(i).getFirst());
            simpleMailMessage.setSubject("Your policy has been expired!");
            String body = "Your policy, with record id : "+mailDetails.get(i).getSecond()+" has been expired today. please buy a new policy to stay protected.";
            simpleMailMessage.setText(body);
            mailSender.send(simpleMailMessage);
        }
    }


}
