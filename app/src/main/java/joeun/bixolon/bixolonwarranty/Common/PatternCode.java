package joeun.bixolon.bixolonwarranty.Common;

import java.util.regex.Matcher;

import joeun.bixolon.bixolonwarranty.Activity.MainActivity;

/**
 * Created by admin on 2017. 5. 16..
 */

public class PatternCode {

    /**
     * PatternCodeMatcher
     * @param _text
     * @return
     */
    public String PatternCodeMatcher(String _text) {
        String patterText = "";

        //[E1] Code Name
        java.util.regex.Pattern groupPattern = java.util.regex.Pattern.compile("\\[([0-9a-zA-Z]*)\\].*");
        Matcher groupMatcher = groupPattern.matcher(_text);
        while(groupMatcher.find()) {
            //Log.v("WarrantyDate", "_warrantyCode  : " + groupMatcher.group(0));// 정규표현식에 일치한 전체 문자열
            //Log.v("WarrantyDate", "_warrantyCode  : " + groupMatcher.group(1));
            patterText = groupMatcher.group(1);
        }

        return patterText;
    }

}
