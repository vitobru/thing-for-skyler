package edu.sjsu.android.project1yanchen;

/**
 * For calculate the monthly payment.
 *
 * @author Yan Chen
 */
class Calculator {
    public static float calculate(float P, float J, int N, float T) {
        if (J == 0) {
            return P / N + T;
        } else {
            float temp = (float) Math.pow((1 + J), -N);
            J = J / (1 - temp);
            return P * J + T;
        }
    }

}
