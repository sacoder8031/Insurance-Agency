package anubahv.insuracne.insuranceagency.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class StorageServiceImpl implements StorageService{
    public String uploadDir = "src/main/resources/public/uploads/";

    @Override
    public String getUploadLocation(MultipartFile file, String username, String type) {
        return uploadDir+username+"/"+type+"/"+StringUtils.cleanPath(file.getOriginalFilename());
    }

    @Override
    public void uploadFile(MultipartFile file,String username,String type) {
        try{
            Path copyLocation = Paths.get(uploadDir+username+"/"+type+ File.separator + StringUtils.cleanPath(file.getOriginalFilename()));
            if(Files.notExists(copyLocation)){
                Files.createDirectories(copyLocation);
            }
            Files.copy(file.getInputStream(),copyLocation, StandardCopyOption.REPLACE_EXISTING);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
