package sharabh.insuracne.insuranceagency.repository;

import sharabh.insuracne.insuranceagency.models.VerificationToken;

public interface TokenRepository {
    public VerificationToken getVerificationToken(String token);
    public void save(VerificationToken verificationToken);
}
