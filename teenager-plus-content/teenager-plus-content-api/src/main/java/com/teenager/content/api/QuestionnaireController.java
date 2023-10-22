package com.teenager.content.api;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.teenager.content.common.R;
import com.teenager.content.config.TokenUtils;
import com.teenager.content.model.dto.QuestionReturn;
import com.teenager.content.model.po.Option;
import com.teenager.content.model.po.Question;
import com.teenager.content.model.po.QuestionRecord;
import com.teenager.content.model.po.Questionnaire;
import com.teenager.content.service.QuestionRecordService;
import com.teenager.content.service.QuestionService;
import com.teenager.content.service.QuestionnaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Xue
 * @create 2023-05-07-10:20
 */
@RestController
@RequestMapping("/questionnaire")
public class QuestionnaireController {

    @Autowired
    private QuestionnaireService questionnaireService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuestionRecordService questionRecordService;

    /**
     * 获取问卷
     * @return
     */
    @GetMapping("/getQuestionnaires")
    public R<List<Questionnaire>> getQuestionnaires(){
        List<Questionnaire> list = questionnaireService.list();
//        对每一个问卷补充其问题信息。
        for (Questionnaire q:list){
            ArrayList<Question> questions = new ArrayList<>();
            q.setQuestionList(getQuestions(q.getId()));
        }
        return R.success(list);
    }

    /**
     * 获取得分并记录
     * @param request
     * @return
     */
    @PostMapping("/getResult")
    public R<String> getResult(@RequestBody QuestionReturn questionReturn, HttpServletRequest request){
//        计算心理状态得分
        int score=0;
        int[] values = questionReturn.getValues();
        for (int v:values){
            score+=v;
        }

//       将结果记录并返回
        String token = request.getHeader("token");
        TokenUtils.User user = TokenUtils.getUser(token);
        Long userId = user.getId();
        String result;
        if (questionReturn.getId()==1){
            if (score>=0&&score<=10){
                result="你现在的心理状态很棒，祝你生活中天天开心哦！";
            }else if (score>=10&&score<=20){
                result="你现在的心理状态良好，记得多留意生活中的开心事哦！";
            }else if (score>=20&&score<=30){
                result="你现在的心理状态欠佳，可以让自己适当放松一下哦！";
            }else if (score>=30&&score<=35){
                result="你现在的心理状态比较差，建议你联系心理咨询师咨询一下哦，大家都会帮助你的！";
            }else {
                result="你现在的心理状态很差哦，去找好朋友或者爸爸妈妈聊聊天吧，相信你会变得开心的！";
            }
        }else{
            if (score>=0&&score<=15){
                result="你现在的心理状态很棒，祝你生活中天天开心哦！";
            }else if (score>=15&&score<=25){
                result="你可能存在一些轻微的社交恐惧症状，但并不会对正常的社交活动产生明显的影响。你在社交互动中可能会稍微感到有些紧张或自我意识过强，但通常能够通过一些简单的应对策略来缓解这种压力。";
            }else if (score>=25&&score<=35){
                result="你可能存在相当严重的社交恐惧症状，这些症状已经对其正常的社交生活产生了很大的影响。你可能避免参加某些社交场合，或者在社交场合中保持安全距离，以避免被别人注意。你需要更积极地寻求专业帮助，以帮助自己处理自己的情绪状态和提高社交能力。";
            }else {
                result="你可能存在非常严重的社交焦虑症状，这些症状已经对其正常的生活产生了严重干扰。你可能避免所有的社交场合，并且很难与陌生人建立联系。你需要寻求专业治疗，并采取更积极的应对策略来处理自己的社交恐惧感受。";
            }
        }
        QuestionRecord questionRecord = new QuestionRecord();
        questionRecord.setUserId(userId);
        questionRecord.setTestTime(new Date());
        questionRecord.setResult(result);
        questionRecordService.save(questionRecord);
        return R.success(result);
    }



    public List<Question> getQuestions(Long questionnaireId){
        ArrayList<Question> questions = new ArrayList<>();
        LambdaQueryWrapper<Question> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Question::getQuestionnaireId,questionnaireId);
        List<Question> questionList = questionService.list(queryWrapper);
//        为每个问题补充选项
        for (Question q:questionList){
            ArrayList<Option> options = new ArrayList<>();
            options.add(new Option(1,"没有或很少时间"));
            options.add(new Option(2,"小部分时间"));
            options.add(new Option(3,"相当多时间"));
            options.add(new Option(4,"绝大部分或全部时间"));
            q.setOptions(options);
        }
        return questionList;
    }
}
