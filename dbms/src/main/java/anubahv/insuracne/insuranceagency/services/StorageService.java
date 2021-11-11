package anubahv.insuracne.insuranceagency.services;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService{
    public  void uploadFile(MultipartFile file,String username,String type);
    public String getUploadLocation(MultipartFile file,String username,String type);
}
