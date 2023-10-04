package com.example.ex42.util;

public class TimeUtil {

    public static String chargeToString(long x){
        int hours = (int) (x / 3600000);
        int minutes = (int) (x - hours * 3600000) / 60000;
        int seconds = (int) (x - hours * 3600000 - minutes * 60000) / 1000;
        String time = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        return time;
    }

    public static long parseSimpleToCharge(String time) {
        String[] parts = time.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        int seconds = Integer.parseInt(parts[2]);
        long charge = hours * 3600000 + minutes * 60000 + seconds * 1000;
        return charge;
    }

}
