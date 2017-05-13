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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Validation {

    public static Boolean validateText(String text) {
        String ePattern = "[a-zA-Z][a-zA-Z _]*";
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
        if (password.length() < 6) {
            return false;
        }
        String ePattern = "[a-zA-Z0-9]*";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(password);
        return m.matches();
    }

    public static Boolean validateDate(String date) {
        String ePattern = "(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/(19|20)[0-9][0-9]"; //dd/mm/yyyy
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(date);
        return m.matches();
    }

    public static Boolean validateTime(String time) {
        String ePattern = "(0[0-9]|1[01]):(0[1-9]|[12345][0-9]|00) (am|pm|AM|PM)"; //hh:mm (am|pm|AM|PM) 00:00 -->11:59 am/pm
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(time);
        return m.matches();
    }

    public static Boolean validateNumber(String number) {
        String ePattern = "[0-9]*";  //"[1-9][0-9]*" to avoid 0 in right if we need.  
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(number);
        return m.matches();
    }

    public static Boolean validatePhone(String phone) {

        if (phone.length() != 11) {
            return false;
        }
        String ePattern = "(01)[012][0-9]*";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(phone);
        return m.matches();
    }

    public static String convertTimeTo24Hour(String time) {
        time=time.toLowerCase();
        if (time.contains("pm")) {
            time = time.replace(" pm", "");
            int hour = Integer.parseInt(time.charAt(0) + "" + time.charAt(1)) + 12;
            time = time.replace(time.charAt(0) + "" + time.charAt(1), hour + "");
            return time;

        } else if (time.contains("am")) {
            time = time.replace(" am", "");
            return time;
        }

        return null;
    }

    public static ArrayList<String> convertDateToString(Date date){
        ArrayList<String>arr=new ArrayList<>();
        
        SimpleDateFormat localTimeFormat = new SimpleDateFormat("hh:mm a", Locale.US);
        SimpleDateFormat localDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        
        String startTime = localTimeFormat.format(date);
        String startDate = localDateFormat.format(date);
        
        if (startTime.contains("12:")) {
           startTime= startTime.replace("12:", "00:");
        }
        
        
        arr.add(startDate);
        arr.add(startTime);
        
        
        return arr;
    }
    
    public static Boolean validateAuctionTime(String StartDate, String StartTime, String TerminationDate, String TerminationTime) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        try {
            System.out.println(StartDate);
            System.out.println(TerminationDate);
            Date Start_Date = df.parse(StartDate + " " + convertTimeTo24Hour(StartTime));
            Date terminate_Date = df.parse(TerminationDate + " " +convertTimeTo24Hour(TerminationTime));
            Date today=new Date();
            
            
            if (terminate_Date.compareTo(Start_Date) > 0 && Start_Date.compareTo(today)>0) {
                return true;
            } else {
                return false;
            }

        } catch (ParseException ex) {
            Logger.getLogger(Validation.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

}
