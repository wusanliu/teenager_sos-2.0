package com.teenager.content.api;

import com.teenager.content.common.R;
import com.teenager.content.model.po.Counselor;
import com.teenager.content.service.CounselorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Xue
 * @create 2023-04-25-11:19
 */
@Slf4j
@RestController
@RequestMapping("/counselor")
public class CounselorController {
    @Autowired
    private CounselorService counselorService;

    @GetMapping("/getCounselors")
    public R<List<Counselor>> getCounselors(){
        List<Counselor> counselors = counselorService.list();
        return R.success(counselors);
    }
}
