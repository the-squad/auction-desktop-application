/*
 * The MIT License
 *
 * Copyright 2017 Contributors.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package app;

public class Validation {

    public static Boolean validateText(String text) {
        String ePattern ="[a-zA-Z][a-zA-Z _]*";  
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(text);
        return m.matches();
    }

    public static Boolean validateEmail(String email) {
        String ePattern = "[a-zA-Z][a-zA-Z0-9._]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
           java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
           java.util.regex.Matcher m = p.matcher(email);
           return m.matches();
    }

    public static Boolean validatePassword(String password) {
        if (password.length()<6) {
             return false;
         }
        String ePattern ="[a-zA-Z0-9]*";  
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(password);
        return m.matches();
    }

    public static Boolean validateDate(String date) {
        String ePattern ="(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/(19|20)[0-9][0-9]"; //dd/mm/yyyy
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(date);
        return m.matches();
    }

    public static Boolean validateTime(String time) {
        String ePattern ="(0[0-9]|1[01]):(0[1-9]|[12345][0-9]|00) (am|pm)"; //hh:mm (am|pm) 00:00 -->11:59 am/pm
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(time);
        return m.matches();
    }

    public static Boolean validateNumber(String number) {
        String ePattern ="[0-9]*";  //"[1-9][0-9]*" to avoid 0 in right if we need.  
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(number);
        return m.matches();
    }
    
    
    public static Boolean validatePhone(String phone) {
         
         if (phone.length()!=11) {
             return false;
         }
        String ePattern ="(01)[012][0-9]*";  
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(phone);
        return m.matches();
    }
    
    public static String convertTimeTo24Hour(String time)
    {
        if (time.contains("pm")) {
            time=time.replace(" pm", "");
            int hour = Integer.parseInt(time.charAt(0)+""+time.charAt(1))+12;
            time=time.replace(time.charAt(0)+""+time.charAt(1), hour+"");
            return time;
            
        }else if (time.contains("am")) {
            time=time.replace(" am", "");
            return time;
        }
        
        return null;
    }
}
