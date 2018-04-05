package org.erian.modules.utils;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

/**
* IP校验
*
 * @copyright {@link bcc.ac.cn}
 * @author rambo
 * @version  2013-10-15 11:01:53
 * @see com.bcc.rnd.billing.common.utils.IPUtils
*/



public class IPUtils {
	  /**
     * ip校验
     * @param s
     * @return Boolean
     */
    public static Boolean isIpAddress(String s){
            String regex = "(((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.](((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.](((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.](((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(s);
            return m.matches();
    }

    /**
     * 获取客户端ip
     * @param request
     * @return String
     */
    public static String getClientAddress(HttpServletRequest request) {
    	/**
    	 * servlet request getHeader("x-forwarded-for") 获取真实IP
    	 */
        String address = request.getHeader("X-Forwarded-For");
        if (address != null &&isIpAddress(address)) {
            return address;
        }
        return request.getRemoteAddr();
    }
}
