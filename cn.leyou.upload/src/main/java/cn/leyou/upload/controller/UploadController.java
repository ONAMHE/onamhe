package cn.leyou.upload.controller;



//import org.mockito.internal.util.StringUtil;
//import com.ctc.wstx.util.StringUtil;
//import com.netflix.discovery.util.StringUtil;
import cn.leyou.upload.service.UploadService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("upload")
public class UploadController {
    @Autowired
  public   UploadService uploadService;

    /**
     * 上传图片文件
     * @param file  图片文件可以是一个集合
     * @return  返回值是一个文件地址,同时响应201状态码
     */
    @PostMapping("image")
    public ResponseEntity<String> uploadImage(@RequestParam("file")MultipartFile file){
//        上传图片只能使用MultipartFile来接收  同时上传方法也只能是post其他方法都不能上传文件
        String name=uploadService.uploadImage(file);
        if (StringUtils.isEmpty(name)) {
            return ResponseEntity.badRequest().build();
        }
//        create表示201当数据保存成功的时候响应该代码
        return ResponseEntity.status(HttpStatus.CREATED).body(name);
    }



//       StringUtils.isNotEmpty("这个方法表示判断字符串是否为空,如果为空就返回false  反之就返回true")
//               StringUtils.isEmpty("这个方法表示判断字符串是否为空,为空返回true反之返回false");
////    stringutils包lang3和通用mapper都有,所以在使用的时候需要导入其中一个











}
