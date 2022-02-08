package yoziming.mall.thirdparty.controller;

import io.minio.MinioClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import yoziming.mall.common.utils.R;
import yoziming.mall.thirdparty.dto.MinioUploadDto;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/minio")
public class MinioController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MinioController.class);
    @Value("${minio.endpoint}")
    private String ENDPOINT;
    @Value("${minio.bucketName}")
    private String BUCKET_NAME;
    @Value("${minio.accessKey}")
    private String ACCESS_KEY;
    @Value("${minio.secretKey}")
    private String SECRET_KEY;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public R upload(@RequestParam("file") MultipartFile file) {
        try {
            // 創建一個MinIO的Java客戶端
            MinioClient minioClient = new MinioClient(ENDPOINT, ACCESS_KEY, SECRET_KEY);
            boolean isExist = minioClient.bucketExists(BUCKET_NAME);
            if (isExist) {
                LOGGER.info("存儲桶已經存在！");
            } else {
                // 創建存儲桶
                minioClient.makeBucket(BUCKET_NAME);
            }
            String filename = file.getOriginalFilename();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssZ");
            // 設置存儲物件名稱
            String objectName = sdf.format(new Date()) + filename;
            // 使用putObject上傳一個文件到存儲桶中
            minioClient.putObject(BUCKET_NAME, objectName, file.getInputStream(), file.getContentType());
            String objectUrl = minioClient.getObjectUrl(BUCKET_NAME, objectName);
            LOGGER.info("文件上傳成功!");
            MinioUploadDto minioUploadDto = new MinioUploadDto();
            minioUploadDto.setName(filename);
            minioUploadDto.setUrl(objectUrl);
            return R.ok().put("data", minioUploadDto);
        } catch (Exception e) {
            LOGGER.info("上傳發生錯誤: {}！", e.getMessage());
        }
        return R.error();
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public R delete(@RequestParam("objectName") String objectName) {
        try {
            MinioClient minioClient = new MinioClient(ENDPOINT, ACCESS_KEY, SECRET_KEY);
            minioClient.removeObject(BUCKET_NAME, objectName);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.error();
    }
}
