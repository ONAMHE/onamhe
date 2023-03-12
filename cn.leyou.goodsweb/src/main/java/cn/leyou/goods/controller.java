package cn.leyou.goods;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class controller {
    @GetMapping("item/{id}.html")
    public String toItemPage(@PathVariable("id")Long id, Model model){
//        使用了thymeleaf后会将楼下的解析为item.html同时到resource下的template的文件夹中去寻找这个文件
                //如果有这个页面那么就直接转跳到这个页面，没有的话那么就不转跳
//        同时在参数的Model中包含的是返回个前端的数据，这个数据可以直接解析到前端页面



//        使用这个转跳要使用live-server才能正常展开网页端因为要加载页面的静态数据
//        如果不适用live-server来开启页面，那么静态页面就灭有端口那么就无法是通过端口转跳到指定的页面
        return "item";
    }
}
