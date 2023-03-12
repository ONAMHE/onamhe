package test;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.fdfs.ThumbImageConfig;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FastDFSTest {
    /**
     * 楼下两个注入是原本就有的注解，不需要自己去写
     */
    @Autowired
    private FastFileStorageClient storageClient;

    @Autowired
    private ThumbImageConfig thumbImageConfig;

    /**
     * 上传文件但是不生成略缩图
     * @throws FileNotFoundException
     */
    @Test
    public void testUpload() throws FileNotFoundException {
        // 要上传的文件
        File file = new File("C:\\Users\\ONAM\\Pictures\\Camera Roll\\1677204640812.jpg");
        // 上传并保存图片，参数：1-上传的文件流 2-文件的大小 3-文件的后缀 4-可以不管他
        StorePath storePath = this.storageClient.uploadFile(
                new FileInputStream(file), file.length(), "jpg", null);
        // 带分组的路径  http://image.leyou.com/group1/M00/00/00/qf5gHGQEfbSActxjAEXalFRppeo176.jpg
        System.out.println(storePath.getFullPath());
        // 不带分组的路径
        System.out.println(storePath.getPath());
    }

    /**
     * 上传文件同时生成略缩图，返回原文件地址，和略缩图地址
     * @throws FileNotFoundException
     */
    @Test
    public void testUploadAndCreateThumb() throws FileNotFoundException {
        File file = new File("C:\\Users\\ONAM\\Pictures\\Camera Roll\\1671330215296.jpg");
        // 上传并且生成缩略图
        StorePath storePath = this.storageClient.uploadImageAndCrtThumbImage(
                new FileInputStream(file), file.length(), "jpg", null);
        // 带分组的路径
        System.out.println(storePath.getFullPath());
        // 不带分组的路径
        System.out.println(storePath.getPath());
        // 获取缩略图路径
        String path = thumbImageConfig.getThumbImagePath(storePath.getPath());
        System.out.println(path);
    }
}