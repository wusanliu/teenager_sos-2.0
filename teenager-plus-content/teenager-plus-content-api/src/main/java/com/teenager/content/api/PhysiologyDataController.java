package com.teenager.content.api;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.teenager.content.common.R;
import com.teenager.content.model.po.HeartData;
import com.teenager.content.model.po.PhysiologyData;
import com.teenager.content.service.PhysiologyDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author Xue
 * @create 2023-03-26-9:21
 */
@Slf4j
@RestController
@RequestMapping("/physiologys")
public class PhysiologyDataController {
    @Autowired
    private PhysiologyDataService physiologyDataService;

    /**
     * 保存生理数据
     * @param heartData
     * @return
     */
    @PostMapping("/receive")
    public R<String> receive(@RequestBody HeartData heartData, HttpSession session){
        if(heartData.isFlag()){
//          模拟数据
            BigDecimal bloodHighPressure= BigDecimal.valueOf(RandomDouble(90, 180));
            BigDecimal bloodLowPressure= BigDecimal.valueOf(RandomDouble(30, 90));
            Integer bloodOxygen=RandomInt(80,100);
            BigDecimal temperature= BigDecimal.valueOf(RandomDouble(36, 40));
            BigDecimal sleepTime= BigDecimal.valueOf(RandomDouble(6, 12));
            Integer steps=RandomInt(5000,15000);
            Integer exerciseTime=RandomInt(steps/500,steps/700);
            BigDecimal distance=BigDecimal.valueOf((double)steps*6/10000);
            int climb=RandomInt(100,500);
            Integer heat=RandomInt(steps*36/1000,steps*48/1000)+RandomInt(climb/20,climb/10);
            Long userId = (Long)session.getAttribute("user");
//           设置数据
            PhysiologyData physiologyData = new PhysiologyData(null,heartData.getHeart(),bloodHighPressure,bloodLowPressure,bloodOxygen,temperature,sleepTime,steps,exerciseTime,distance,heat,climb,userId,new Date());
            physiologyDataService.save(physiologyData);
            return R.success("保存成功");
        }
        return R.error("数据不合法");
    }

    /**
     * 获取最近一次数据
     * @return
     */
    @GetMapping("/getLast")
    public R<PhysiologyData> getLast(HttpSession session){
//        Long userId = (Long)session.getAttribute("user");
        LambdaQueryWrapper<PhysiologyData> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(PhysiologyData::getCreateTime);
//        queryWrapper.eq(PhysiologyData::getUserId,userId);
        queryWrapper.last("limit 1");
        PhysiologyData physiologyData = physiologyDataService.getOne(queryWrapper);
        return R.success(physiologyData);
    }

    /**
     * 获取所有生理数据
     * @param session
     * @return
     */
    @GetMapping("/getAll")
    public R<List<PhysiologyData>> getAll(HttpSession session){
//        Long userId = (Long)session.getAttribute("user");
        LambdaQueryWrapper<PhysiologyData> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(PhysiologyData::getUserId,userId);
        queryWrapper.orderByAsc(PhysiologyData::getCreateTime);
        List<PhysiologyData> physiologyDataList = physiologyDataService.list(queryWrapper);
        return R.success(physiologyDataList);
    }

    /**
     * 获取最近七条数据
     * @param session
     * @return
     */
    @GetMapping("/getLastSeven")
    public R<List<PhysiologyData>> getLastSeven(HttpSession session){
//        Long userId = (Long)session.getAttribute("user");
        LambdaQueryWrapper<PhysiologyData> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(PhysiologyData::getUserId,userId);
        queryWrapper.orderByDesc(PhysiologyData::getCreateTime);
        queryWrapper.last("limit 7");
        List<PhysiologyData> physiologyDataList = physiologyDataService.list(queryWrapper);
        return R.success(physiologyDataList);
    }


    /**
     * 生成max到min范围的浮点数
     * */
    public static double RandomDouble(final double min, final double max) {
        return min + ((max - min) * new Random().nextDouble());
    }

    public static Integer RandomInt(final Integer min, final Integer max) {
        return min + (int)(Math.random() * (max-min+1));
    }
}
