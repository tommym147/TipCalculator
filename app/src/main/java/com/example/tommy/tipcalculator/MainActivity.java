package com.example.tommy.tipcalculator;

import android.text.Editable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity
{
    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat = NumberFormat.getPercentInstance();

    private double billAmount = 0.0;
    private double percent = 0.15;
    private double tax = 0.0;
    private TextView amountTextView;
    private TextView percentTextView;
    private TextView tipTextView;
    private TextView totalTextView;
    private TextView taxTextView;
    private TextView taxedTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get reference to programmatically manipulated TextViews
        amountTextView = (TextView) findViewById(R.id.amountTextView);
        percentTextView = (TextView) findViewById(R.id.percentTextView);
        tipTextView = (TextView) findViewById(R.id.tipTextView);
        totalTextView = (TextView) findViewById(R.id.totalTextView);
        tipTextView.setText(currencyFormat.format(0));      // set text to 0
        totalTextView.setText(currencyFormat.format(0));    // set text to 0
        taxTextView = (TextView) findViewById(R.id.taxTextView);
        taxedTotal = (TextView) findViewById(R.id.taxedTotal);
        taxedTotal.setText(currencyFormat.format(0));

        // set taxEditText's TextWatcher
        EditText taxEditText = (EditText) findViewById(R.id.taxEditText);
        taxEditText.addTextChangedListener(taxEditTextWatcher);

        // set amountEditText's TextWatcher
        EditText amountEditText = (EditText) findViewById(R.id.amountEditText);
        amountEditText.addTextChangedListener(amountEditTextWatcher);

        // set percentSeekBar's onSeekBarChangeListener
        SeekBar percentSeekBar = (SeekBar) findViewById(R.id.percentSeekBar);
        percentSeekBar.setOnSeekBarChangeListener(seekBarListener);
    }

    private void calculate()
    {
        //format percent and display in percentTextView
        percentTextView.setText(percentFormat.format(percent));

        // calculate the tip and total
        double tip = billAmount * percent;
        double total = billAmount + tip;
        double taxTotal = total + (total * tax);

        // display tip and total formatted as currency
        tipTextView.setText(currencyFormat.format(tip));
        totalTextView.setText(currencyFormat.format(total));
        taxedTotal.setText(currencyFormat.format(taxTotal));
    }

    private final OnSeekBarChangeListener seekBarListener = new OnSeekBarChangeListener()
    {
        // update percent, then call calculate
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
        {
            percent = progress / 100.0; // set percent based on progress
            calculate(); // calculate and display tip and total
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private final TextWatcher amountEditTextWatcher = new TextWatcher()
    {
        //called when the user modifies the bill amount
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            try // get bill amount and display currency formatted value
            {
                billAmount = Double.parseDouble(s.toString())/ 100.0;
                amountTextView.setText(currencyFormat.format(billAmount));
            }
            catch (NumberFormatException e) // if s is empty or non-numeric
            {
                amountTextView.setText("");
                billAmount = 0.0;
            }

            calculate();    // update the tip and total TextViews
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private final TextWatcher taxEditTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int before, int count) {

        }

        //called when the user modifies the bill amount
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            try // get tax amount and display percent formatted value
            {
                tax = Double.parseDouble(s.toString())/ 100.0;
                taxTextView.setText(percentFormat.format(tax));
            }
            catch (NumberFormatException e) // if s is empty or non-numeric
            {
                taxTextView.setText("");
                tax = 0.0;
            }

            calculate();    // update the tip and total TextViews
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
