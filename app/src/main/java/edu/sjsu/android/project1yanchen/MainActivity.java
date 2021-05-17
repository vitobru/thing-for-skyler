package edu.sjsu.android.project1yanchen;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.sjsu.android.project1yanchen.databinding.ActivityMainBinding;

/**
 * Main Activity for Mortgage Calculator.
 *
 * @author Yan Chen
 *
 * Localized by Skyler Hungerford
 */
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private String errorMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setLocale();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        // Set listeners for views that relate to user input
        // so that if user input changes, the result view will
        // ask user to click calculate button again.
        setListener();
    }

    /**
     * Respond to an on click event
     *
     * @param view the view being clicked
     */
    public void onClick(View view) {
        if (view.getId() == binding.cal.getId()) {
            calculate();
        }
        else if (view.getId() == binding.locale.getId()){
            //changeLocale();
        }
        else {
            uninstall();
        }
    }

    /**
     * Private helper method to calculate mortgage.
     */
    private void calculate() {
        String input = binding.input.getText().toString();
        if (!valid(input)) {
            Toast.makeText(this, "Invalid Input!",
                    Toast.LENGTH_LONG).show();
            binding.result.setText(errorMsg);
            return;
        }

        float P = Float.parseFloat(input);
        // divided by 12000 since rate is set to
        // 1 - 200 for 1 decimal place
        float J = binding.rate.getProgress() / 12000.0f;
        float T;
        int N;

        // Get loan term from radio group
        if (binding.radio15.isChecked()) N = 15 * 12;
        else if (binding.radio20.isChecked()) N = 20 * 12;
        else N = 30 * 12;

        // check if tax is included
        if (binding.tax.isChecked()) T = P * 0.1f / 100.0f;
        else T = 0.0f;

        // set result
        String result = String.format(getString(R.string.resultFormat),
                Currency.currencyChange(float calc = Calculator.calculate(P, J, N, T));
        binding.result.setText(result);
    }

    /**
     * Private helper method to uninstall the app.
     */
    private void uninstall() {
        Intent delete = new Intent(Intent.ACTION_DELETE,
                Uri.parse("package:" + getPackageName()));
        startActivity(delete);
    }

    /**
     * Private helper method to check user input
     *
     * @param input input string
     * @return false if empty; false if more than 2 decimal digits.
     */
    private boolean valid(String input) {
        if (input.length() == 0) {
            errorMsg = "Please enter the principle.\nThen " + getString(R.string.prompt);
            return false;
        } else if (!input.endsWith(".") && (input.contains(".")
                && input.split("\\.")[1].length() > 2)) {
            errorMsg = "Please enter a valid number. 2 decimal digits max." +
                    "\nThen " + getString(R.string.prompt);
            return false;
        } else return true;
    }

    /**
     * Private helper method to set listeners.
     * If user input changes,
     * reset the result to asking user to click calculate button.
     */
    private void setListener() {
        // Show interest rate got from rate
        // If rate changed, Reset the result to
        // asking user to click calculate button
        binding.rate.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar rate, int progress, boolean fromUser) {
                resetResult();
            }

            @Override
            public void onStartTrackingTouch(SeekBar rate) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar rate) {
            }
        });


        // when the amount borrowed is changed
        binding.input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                resetResult();
            }
        });

        // when loan term (radio group) is changed
        binding.terms.setOnCheckedChangeListener((group, checkedId) -> resetResult());

        // when state of check box is changed
        binding.tax.setOnCheckedChangeListener((buttonView, isChecked) -> resetResult());
    }

    /**
     * Private helper method to reset the result to ask user to click enter.
     */
    private void resetResult() {
        binding.result.setText(getString(R.string.prompt));
        String currentRate = "Interest rate: " + binding.rate.getProgress() / 10.0 + "%";
        binding.rateView.setText(currentRate);
    }

}