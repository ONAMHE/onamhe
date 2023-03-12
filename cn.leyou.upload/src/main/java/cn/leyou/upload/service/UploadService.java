package cn.leyou.upload.service;


import cn.leyou.upload.FastdfsUpload.FastdfsUpload;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class UploadService {
    @Autowired
    public FastdfsUpload fastdfsUpload;
    private static final List<String> CONTENT_TYPES=Arrays.asList("image/gif","image/jpeg","image/rew","image/jpg","image/png");
    private static final Logger LOGGER= LoggerFactory.getLogger(UploadService.class);
    public String uploadImage(MultipartFile file) {
        //得到文件名
        String originalFilename=file.getOriginalFilename();
        //获取文件类型  conTent-type  这个头就包含了文件类型,每个文件类型的该头不一样
       String contentType=file.getContentType();
       //得到文件的后缀
       String fileExtName= StringUtils.substringAfterLast(originalFilename,".");
        //校验文件类型,防止脚本入侵
        if (!CONTENT_TYPES.contains(contentType)){
            //楼下的这个方法是用{}来作为占位符,然后会自动将后面的参数传递到前面去 ,也可以用字符串拼接但是这样更加的优雅
            LOGGER.info("文件类型不合法:{}",originalFilename);
            return null;
        }
        try {
            BufferedImage bufferedImage= ImageIO.read(file.getInputStream());//校验文件是否合法
            if (bufferedImage == null) {
    //            还可以通过imageio得到文件的宽高  bufferreader.getWidth()  bufferreader.getHeight()
                LOGGER.info("文件内容不合法{}",originalFilename);
            }
            StorePath storePath = fastdfsUpload.uploadImage(file.getInputStream(), file.getSize(), fileExtName);
//            file.transferTo(new File("C:\\Users\\ONAM\\IdeaProjects\\leyou\\image\\"+originalFilename));//存储到本地的文件夹中
            return "http://image.leyou.com/"+storePath.getFullPath();
        } catch (IOException e) {
           LOGGER.info("服务器内部错误:{}",originalFilename);
           e.printStackTrace();
        }
        String[] name= {"hewenmao","laile"};
        List<String> strings = Arrays.asList(name);
//        楼上的方法可以直接将一个字符串转换为list的集合但是这个集合只能使用get set两个方法
        return null;
    }
}



























