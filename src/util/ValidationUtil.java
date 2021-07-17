/*
 * Copyright (c) 2021  Dinusha Jayawardena. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 */

package util;

import java.time.LocalDate;

public class ValidationUtil {


    public static boolean isInteger(String input) {
        if (input.startsWith("+") || input.startsWith("-")) {
            return false;
        }
        try {
            Long.parseLong(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValid(String input, boolean checkSpaces, boolean checkDigits, char... symbols) {
        char[] chars = input.toCharArray();

        loop1:
        for (char aChar : chars) {
            if ((checkDigits && Character.isDigit(aChar)) || (checkSpaces && Character.isSpaceChar(aChar))) {
                continue;
            }

            for (char symbol : symbols) {
                if (aChar == symbol) {
                    continue loop1;
                }
            }

            if (!Character.isAlphabetic(aChar)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isValidDate(String date){
        try{
            LocalDate.parse(date);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /*public static boolean isValidEmail(String input){
        String[] split = input.split("@");

        if (split.length != 2){
            return false;
        }

        String firstPart = split[0];
        int lastindex = split[1].lastIndexOf('.');

        if (lastindex == -1){
            return false;
        }
        String secondPart = split[1].substring(0,lastindex);
        String thirdPart = split[1].substring(lastindex+1);

        if (!(secondPart.length() >= 2 && thirdPart.length() >= 2)){
            return false;
        }

        if(!isValid(firstPart, false, true, '.', '_') || (firstPart.startsWith(".") ||
                firstPart.startsWith("_") || firstPart.endsWith(".") || firstPart.endsWith("_"))){
            return false;
        }
        if (!(isValid(secondPart, false, true, '.') && !secondPart.startsWith(".") && !secondPart.endsWith("."))) {
            return false;
        }
        if (!isValid(thirdPart, false,true)){
            return false;
        }
        return true;
    }*/

}
