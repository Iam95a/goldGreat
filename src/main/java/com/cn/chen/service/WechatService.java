package com.cn.chen.service;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.chen.domain.model.TextMessage;
import com.cn.chen.util.HttpUtil;
import com.cn.chen.util.MessageUtil;

@Service
public class WechatService {
	
	@Autowired
	private WeatherService weatherService;
	
	public  String processRequest(String msg) {  
        String respMessage = null;  
        try {  
            // 默认返回的文本消息内容  
            String respContent = "请求处理异常，请稍候尝试！";  
  
            // xml请求解析  
            Map<String, String> requestMap = MessageUtil.parseXml(msg);  
  
            System.out.println("Event=="+requestMap.get("Event"));  
              
            // 发送方帐号（open_id）  
            String fromUserName = requestMap.get("FromUserName");  
            // 公众帐号  
            String toUserName = requestMap.get("ToUserName");  
            // 消息类型  
            String msgType = requestMap.get("MsgType");
            
            // lat
            String lat=requestMap.get("Location_X");

            //lon
            String lon=requestMap.get("Location_Y");
            // 回复文本消息  
            TextMessage textMessage = new TextMessage();  
            textMessage.setToUserName(fromUserName);  
            textMessage.setFromUserName(toUserName);  
            textMessage.setCreateTime(new Date().getTime());  
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);  
            textMessage.setFuncFlag(0);  
  
            // 文本消息  
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {  
                String content = requestMap.get("Content");  
                String cityId=weatherService.getCityIdByCityName(content);
                content=weatherService.getWeather(cityId);
                respContent = content;  
            }  
            // 图片消息  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {  
                respContent = "Sunlight提示：您发送的是图片消息！";  
            }  
            // 地理位置消息  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
            	String content = HttpUtil.queryaddr(lat,lon);
            	String cityId=weatherService.getCityIdByCityName(content);
                String content1=weatherService.getWeather(cityId);
                respContent = content+":"+content1;  
            }  
            // 链接消息  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) { 
            	
                respContent = "Sunlight提示：您发送的是链接消息！";  
            }  
            // 音频消息  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {  
                respContent = "Sunlight提示：您发送的是音频消息！";  
            }  
            // 事件推送  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {  
                // 事件类型  
                String eventType = requestMap.get("Event");  
                // 自定义菜单点击事件  
                if (eventType.equalsIgnoreCase(MessageUtil.EVENT_TYPE_CLICK)) {  
                    // 事件KEY值，与创建自定义菜单时指定的KEY值对应    
                    String eventKey = requestMap.get("EventKey");     
                    System.out.println("EventKey="+eventKey);  
                    respContent = "Sunlight提示：您点击的菜单KEY是"+eventKey;  
                }  
            }  
  
            textMessage.setContent(respContent);  
            respMessage = MessageUtil.textMessageToXml(textMessage);  
        } catch (Exception e) {  
            e.printStackTrace();  
            System.out.println(e);  
            respMessage="有异常了。。。";  
        }  
        return respMessage;  
    }  

}
