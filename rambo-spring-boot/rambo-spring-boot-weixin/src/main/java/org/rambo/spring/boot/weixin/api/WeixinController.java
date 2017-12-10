package org.rambo.spring.boot.weixin.api;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;

import weixin.popular.bean.message.EventMessage;
import weixin.popular.bean.xmlmessage.XMLMessage;
import weixin.popular.bean.xmlmessage.XMLTextMessage;
import weixin.popular.support.ExpireKey;
import weixin.popular.support.expirekey.DefaultExpireKey;
import weixin.popular.util.SignatureUtil;
import weixin.popular.util.StreamUtils;
import weixin.popular.util.XMLConverUtil;



@Controller
@RequestMapping("/ws/weixin")
public class WeixinController {

	public static Logger log = LoggerFactory.getLogger(WeixinController.class);
	
	//从官方获取
	private String token = "rambo520";

	private String appId = "";				//appid 通过微信后台获取

	private String encodingToken = "";		//Token(令牌)   通过微信后台获取
	private String encodingAesKey = "";		//EncodingAESKey(消息加解密密钥) 通过微信后台获取

	//重复通知过滤
    private static ExpireKey expireKey = new DefaultExpireKey();
    
	@RequestMapping(method = RequestMethod.GET)
	public void get(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		ServletInputStream inputStream = request.getInputStream();
        ServletOutputStream outputStream = response.getOutputStream();
        
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");

        //首次请求申请验证,返回echostr
        if(echostr!=null){
        	outputStreamWrite(outputStream, echostr);
            return;
        }
        
        //验证请求签名
        if(!SignatureUtil.checkSignature(token, signature, timestamp, nonce)){
        	   //转换XML
            EventMessage eventMessage = XMLConverUtil.convertToObject(EventMessage.class,inputStream);
            String key = eventMessage.getFromUserName() + "__"
            				   + eventMessage.getToUserName() + "__"
            				   + eventMessage.getMsgId() + "__"
            				   + eventMessage.getCreateTime();
            if(expireKey.exists(key)){
            	//重复通知不作处理
            	return;
            }else{
            	expireKey.add(key);
            }

            //创建回复
            XMLMessage xmlTextMessage = new XMLTextMessage(
                    eventMessage.getFromUserName(),
                    eventMessage.getToUserName(),
                    "你好");
            //回复
            xmlTextMessage.outputStreamWrite(outputStream);
            return;
        }
        outputStreamWrite(outputStream, "Invalid singanature");
        return;
	}

	@RequestMapping(method = RequestMethod.POST)
	public void post(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
			// 暂时空着，在这里可处理用户请求
			// 调用核心业务类接收消息、处理消息

			ServletInputStream inputStream = request.getInputStream();
	        ServletOutputStream outputStream = response.getOutputStream();
	        String signature = request.getParameter("signature");
	        String timestamp = request.getParameter("timestamp");
	        String nonce = request.getParameter("nonce");
	        String echostr = request.getParameter("echostr");

	        //加密模式
	        String encrypt_type = request.getParameter("encrypt_type");
	        String msg_signature = request.getParameter("msg_signature");

	    	WXBizMsgCrypt wxBizMsgCrypt = null;
	    	//加密方式
	    	boolean isAes = "aes".equals(encrypt_type);
	    	if(isAes){
	    		try {
					wxBizMsgCrypt = new WXBizMsgCrypt(encodingToken, encodingAesKey, appId);
				} catch (AesException e) {
					e.printStackTrace();
				}
	    	}

	        //首次请求申请验证,返回echostr
	        if(isAes&&echostr!=null){
	        	try {
					echostr = URLDecoder.decode(echostr,"utf-8");
					String echostr_decrypt = wxBizMsgCrypt.verifyUrl(msg_signature, timestamp, nonce, echostr);
					outputStreamWrite(outputStream,echostr_decrypt);
					return;
				} catch (AesException e) {
					e.printStackTrace();
				}
	        }else if(echostr!=null){
	            outputStreamWrite(outputStream,echostr);
	            return;
	        }

	        EventMessage eventMessage = null;
	        if(isAes){
	        	try {
					//获取XML数据（含加密参数）
					String postData = StreamUtils.copyToString(inputStream, Charset.forName("utf-8"));
					//解密XML 数据
					String xmlData = wxBizMsgCrypt.decryptMsg(msg_signature, timestamp, nonce, postData);
					//XML 转换为bean 对象
					eventMessage = XMLConverUtil.convertToObject(EventMessage.class, xmlData);
				} catch (AesException e) {
					e.printStackTrace();
				}
	        }else{
		        //验证请求签名
		        if(!signature.equals(SignatureUtil.generateEventMessageSignature(token,timestamp,nonce))){
		            System.out.println("The request signature is invalid");
		            return;
		        }

		        if(inputStream!=null){
		        	//XML 转换为bean 对象
		            eventMessage = XMLConverUtil.convertToObject(EventMessage.class,inputStream);
		        }
	        }

	        String key = eventMessage.getFromUserName() + "__"
					   + eventMessage.getToUserName() + "__"
					   + eventMessage.getMsgId() + "__"
					   + eventMessage.getCreateTime();
			if(expireKey.exists(key)){
				//重复通知不作处理
				return;
			}else{
				expireKey.add(key);
			}

			//创建回复
			XMLMessage xmlTextMessage = new XMLTextMessage(
			     eventMessage.getFromUserName(),
			     eventMessage.getToUserName(),
			     "你好");
			//回复
			xmlTextMessage.outputStreamWrite(outputStream,wxBizMsgCrypt);
				
	}
	
	
    /**
     * 数据流输出
     * @param outputStream
     * @param text
     * @return
     */
    private void outputStreamWrite(OutputStream outputStream,String text){
        try {
            outputStream.write(text.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}


