package com.dwr;

import java.util.Collection;

import org.directwebremoting.Browser;
import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.ScriptSessionFilter;

public class MessagePush {

    public static void sendMsg(final String msg) {
        final String content=msg;  
        
        // 过滤器  
        ScriptSessionFilter filter = new ScriptSessionFilter() {  
            public boolean match(ScriptSession scriptSession) {  
//                String tag = (String) scriptSession.getAttribute("tag");  
//                System.out.println("tag="+tag);  
//                return "receiverTag".equals(tag);  
                return true;
            }  
        };  
  
        Runnable run = new Runnable() {  
            private ScriptBuffer script = new ScriptBuffer();  
            public void run() {  
                // 设置要调用的 js及参数  
                script.appendCall("showPushMsg", content);  
                // 得到所有ScriptSession  
                Collection<ScriptSession> sessions = Browser.getTargetSessions();
                // 遍历每一个ScriptSession  
                for (ScriptSession scriptSession : sessions) {  
                    scriptSession.addScript(script);  
                }  
            }  
        };  
        // 执行推送  
        Browser.withAllSessionsFiltered(filter, run); // 注意这里调用了有filter功能的方法  
     
    }
    
}
