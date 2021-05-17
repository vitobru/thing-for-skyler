package edu.sjsu.android.project1yanchen;

import java.util.Locale;

public class Currency {
    public static double currencyChange(float calc){
        // Get the country code for the user-selected locale.
        String deviceLocale= Locale.getDefault().getCountry();
        if(deviceLocale.equals("ja")){
            // If locale is Japan
            return calc * 0.0098;
        }
    }
}
