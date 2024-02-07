package com.sarinzhan.utils;

public class TextDecorator {
    private static String RESET = "\u001B[0m";
    private static String GREEN = "\u001B[32m";
    private static String YELLOW = "\u001B[33m";
    private static String WHITE_BG = "\u001B[47m";
    private static String RED = "\u001B[31m";


    public static String start(String text) {
        return String.format("%s >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> %s  >>>>> %s\n" ,GREEN,text,RESET);
    }

    public static String end(String text) {
        return String.format("%s <<<<< %s  <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<%s\n" ,GREEN,text,RESET);
    }
    public static String greeter(){
        return String.format("Вы успешно подключились на сервер\n" +
                "%sРегистрация:%s  /register  <логин> <пароль> <имя> <фамилия> <дата_рождения(гггг-мм-дд)>\n" +
                "%sВход в аккаунт:%s /login  <логин> <пароль>\n" +
                "%s/help%s для вызова всех команд\n",
                GREEN,RESET,GREEN,RESET,GREEN,RESET);
    }
    public static String error(String text){
        return String.format("%sERROR%s: %s\n",RED,RESET,text);
    }
    public static String success(String text){
        return String.format("%sOK%s: %s\n",GREEN,RESET,text);
    }
}
