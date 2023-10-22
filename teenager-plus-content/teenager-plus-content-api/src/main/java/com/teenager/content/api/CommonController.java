package com.teenager.content.api;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.teenager.content.common.R;
import com.teenager.content.config.TokenUtils;
import com.teenager.content.model.po.User;
import com.teenager.content.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author Xue
 * @create 2023-04-25-9:35
 */
@Slf4j
@RestController
@RequestMapping("/common")
public class CommonController {

    //读取配置文件中设置好的路径
    @Value("${wusan.path}")
    private String basePath;
    @Autowired
    private UserService userService;

    /**
     * 获取首页轮播图
     * @return
     */
    @GetMapping("/picture")
    public R<List<String>> picture(){
        String[] pictures={
                "https://s2.loli.net/2023/04/25/1fz6wWX9qQNxL3P.png",
                "https://s2.loli.net/2023/04/25/OHndJVaPQfrZYc9.jpg",
                "https://s2.loli.net/2023/05/10/UFvcKzNBqi7WeCT.png"
        };
        List<String> pictureList = Arrays.asList(pictures);
        LocalDateTime localDateTime = LocalDateTime.now();
        int day = localDateTime.getDayOfMonth();
        int i=day % 2;
        return R.success(pictureList);
    }

    /**
     * 上传文件并转存到指定位置
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public R<String> upload(@RequestParam MultipartFile file, HttpServletRequest request){
//        获取源文件名
        String originalFilename = file.getOriginalFilename();
//        截取后缀，获取文件类型
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
//        使用UUID生成随机文件名
        String fileName = UUID.randomUUID().toString() + suffix;
//        创建file
        File file1 = new File(basePath);
        if(!file1.exists()){
            file1.mkdirs();
        }

        try {
//            将读入的文件输出到指定地址
            file.transferTo(new File(basePath+fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//

        String token = request.getHeader("token");
        TokenUtils.User user1 = TokenUtils.getUser(token);
        Long userId = user1.getId();
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId,userId);
        User user = userService.getOne(queryWrapper);
        user.setPicturePath(fileName);
        userService.updateById(user);
//        返回文件路径
        return R.success(fileName);
    }

    /**
     * 下载文件的实现
     * @param name
     * @param response
     */
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response){
        try {
//            输入流读入文件
            FileInputStream inputStream = new FileInputStream(new File(basePath + name));
//            获取输出流
            ServletOutputStream outputStream = response.getOutputStream();
//            设置返回文件类型为图片
            response.setContentType("image/jpeg");
//            读输入流并通过输出流输出
            int len=0;
            byte[] bytes = new byte[1024];
            while ((len=inputStream.read(bytes))!=-1){
//                输出到浏览器
                outputStream.write(bytes,0,len);
//                刷新输出流
                outputStream.flush();
            }
            outputStream.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
