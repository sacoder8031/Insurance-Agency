package anubahv.insuracne.insuranceagency.repository;

import anubahv.insuracne.insuranceagency.models.VerificationToken;

public interface TokenRepository {
    public VerificationToken getVerificationToken(String token);
    public void save(VerificationToken verificationToken);
}
