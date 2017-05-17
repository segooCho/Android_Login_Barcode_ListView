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
        String patterExtraction = "";

        /**
         * _text = "[E1] Code Name"
         * () 를 이용하여 group으로 해당 패턴의 내용을 추출
         */
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("\\[([0-9a-zA-Z]*)\\].*");
        Matcher matcher = pattern.matcher(_text);
        while(matcher.find()) {
            //Log.v("WarrantyDate", "_warrantyCode  : " + groupMatcher.group(0));// 정규표현식에 일치한 전체 문자열
            patterExtraction = matcher.group(1);
        }
        return patterExtraction;
    }

}
