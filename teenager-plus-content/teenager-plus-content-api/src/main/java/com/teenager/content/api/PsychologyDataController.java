package com.teenager.content.api;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.teenager.content.common.R;
import com.teenager.content.config.TokenUtils;
import com.teenager.content.model.po.PsychologyData;
import com.teenager.content.model.po.User;
import com.teenager.content.service.PsychologyDataService;
import com.teenager.content.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Xue
 * @create 2023-05-07-9:07
 */
@RestController
@RequestMapping("/psychology")
public class PsychologyDataController {
    @Autowired
    private PsychologyDataService psychologyDataService;
    @Autowired
    private UserService userService;

    @PutMapping("/update")
    public R<String> update(HttpServletRequest request){
        String token = request.getHeader("token");
        Long userId = TokenUtils.getUserId(token);
        PsychologyData psychologyData = new PsychologyData();
        psychologyData.setId(userId);
        psychologyData.setIsShowed(true);
        psychologyDataService.updateById(psychologyData);
        return R.success("修改成功");
    }

    @GetMapping("/get")
    public R<PsychologyData> get(HttpServletRequest request){
        boolean flag=true;
        String token = request.getHeader("token");
        Long userId = TokenUtils.getUserId(token);
        LambdaQueryWrapper<User> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(User::getId,userId);
        User user = userService.getOne(queryWrapper1);
        LambdaQueryWrapper<PsychologyData> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PsychologyData::getId,userId);
        PsychologyData psychologyData = psychologyDataService.getOne(queryWrapper);

//         不存在记录
        if (psychologyData==null){
            psychologyData=new PsychologyData();
            psychologyData.setId(userId);
            flag=false;
        }

//        补充用户名
        psychologyData.setWeiboUsername(user.getWeiboUsername());
//        调用接口获取结果
        String result="积极";
        psychologyData.setResult(result);
//        补充建议
        if ("积极".equals(result)){
            psychologyData.setSuggestion("你现在的心理状态很棒，祝你天天开心！");
        }else if ("中性".equals(result)){
            psychologyData.setSuggestion("你现在的心理状态良好，可以做一些开心的事情！");
        }else {
            psychologyData.setSuggestion("你现在的心理状态欠佳，推荐你做一个心理自测哦！");
        }
        if(flag){
            psychologyDataService.updateById(psychologyData);
        }else {
            psychologyDataService.save(psychologyData);
        }
        return R.success(psychologyData);
    }
}
