package com.teenager.content.common;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

/**
 * Created on 17/6/7.
 * 短信API产品的DEMO程序,工程中包含了一个SmsDemo类，直接通过
 * 执行main函数即可体验短信产品API功能(只需要将AK替换成开通了云通信-短信产品功能的AK即可)
 * 工程依赖了2个jar包(存放在工程的libs目录下)
 * 1:aliyun-java-sdk-core.jar
 * 2:aliyun-java-sdk-dysmsapi.jar
 * <p>
 * 备注:Demo工程编码采用UTF-8
 * 国际短信发送请勿参照此DEMO
 */
public class SmsSender {

    //产品名称:云通信短信API产品,开发者无需替换
    private static final String product = "Dysmsapi";                            // 无需修改
    //产品域名,开发者无需替换
    private static final String domain = "dysmsapi.aliyuncs.com";                // 无需修改

    private static final String accessKeyId = "XXXXXXXXX";                       // 修改accessKeyId
    private static final String accessKeySecret = "YYYYYYYYYYYYY";               // 修改accessKeySecret

    /**
     * 短信发送
     *
     * @param phoneNumbers  待发送手机号，支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
     * @param signName      短信签名-可在短信控制台中找到
     * @param templateCode  短信模板编码-可在短信控制台中找到
     * @param templateParam 模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为 {\"name\":\"Tom\", \"code\":\"123\"}
     * @return
     */
    public static SendSmsResponse sendSms(String phoneNumbers, String signName, String templateCode, String templateParam) {

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        SendSmsResponse sendSmsResponse = null;
        try {
            //初始化acsClient,暂不支持region化
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
            IAcsClient acsClient = new DefaultAcsClient(profile);

            //组装请求对象-具体描述见控制台-文档部分内容
            SendSmsRequest request = new SendSmsRequest();
            //必填:待发送手机号
            request.setPhoneNumbers(phoneNumbers);
            //必填:短信签名-可在短信控制台中找到
            request.setSignName(signName);
            //必填:短信模板-可在短信控制台中找到
            request.setTemplateCode(templateCode);
            //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${title}"时,此处的值为
            request.setTemplateParam(templateParam);
            //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
            //request.setSmsUpExtendCode("90997");
            //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
//            request.setOutId("yourOutId");
            sendSmsResponse = acsClient.getAcsResponse(request);                             // 发送短信，且返回结果
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sendSmsResponse;
    }

    public static void main(String[] args) {
  		//发短信
        SendSmsResponse response = sendSms("13000000000","测试公司","SMS_11111111","{\"name\":\"Tom\", \"title\":\"123\"}");
        System.out.println("短信接口返回的数据----------------");
        System.out.println("Code=" + response.getCode());
        System.out.println("Message=" + response.getMessage());
        System.out.println("RequestId=" + response.getRequestId());
        System.out.println("BizId=" + response.getBizId());

    }
}