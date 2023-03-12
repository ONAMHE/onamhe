package cn.leyou;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@RequestMapping("onamhe")
@ResponseBody
public class test {

    @GetMapping("test")
    public String test(){
        return "测试成功";
    }
}
