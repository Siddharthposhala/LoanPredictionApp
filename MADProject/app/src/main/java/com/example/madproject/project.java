package com.example.madproject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class project extends AppCompatActivity {

    private EditText salaryEditText;
    private EditText collateralEditText;
    private EditText cibilScoreEditText;
    private TextView loanAmountTextView;

    private Map<String, Double> interestRates; // Map to store bank names and interest rates

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        salaryEditText = findViewById(R.id.salaryEditText);
        collateralEditText = findViewById(R.id.collateralEditText);
        cibilScoreEditText = findViewById(R.id.cibilScoreEditText);
        Button calculateButton = findViewById(R.id.calculateButton);
        loanAmountTextView = findViewById(R.id.loanAmountTextView);
        TextView teamDetailsTextView = findViewById(R.id.teamDetailsTextView);

        calculateButton.setOnClickListener(v -> calculateLoanAmount());

        teamDetailsTextView.setText("Developed by Team TechTrio");

        // Initialize banks and interest rates
        interestRates = new HashMap<>();
        interestRates.put("Axis Bank", 0.08);
        interestRates.put("ICICI Bank", 0.1);
        interestRates.put("HDFC Bank", 0.05);
        interestRates.put("DBS Bank", 0.07);
        interestRates.put("Kotak Bank", 0.09);
    }

    @SuppressLint("SetTextI18n")
    private void calculateLoanAmount() {
        String salaryString = salaryEditText.getText().toString().trim();
        String collateralString = collateralEditText.getText().toString().trim();
        String cibilScoreString = cibilScoreEditText.getText().toString().trim();

        if (!salaryString.isEmpty() && !collateralString.isEmpty() && !cibilScoreString.isEmpty()) {
            double salary = Double.parseDouble(salaryString);
            double collateral = Double.parseDouble(collateralString);
            int cibilScore = Integer.parseInt(cibilScoreString);

            boolean isEligible = true;
            String eligibilityMessage = "";

            double minSalary = 20000.0;
            double minCollateral = 50000.0;
            if (salary < minSalary && collateral < minCollateral) {
                eligibilityMessage = "Low salary " +
                        "and low collateral";
                isEligible = false;
            } else {
                if (salary < minSalary && collateral > minCollateral) {
                    eligibilityMessage += "Low salary";
                }

                if (salary > minSalary && collateral < minCollateral) {
                    eligibilityMessage += "Low collateral";
                }
            }

            int minCibilScore = 600;
            if (cibilScore < minCibilScore) {
                eligibilityMessage += " Low CIBIL score";
                isEligible = false;
            }

            if (!isEligible) {
                loanAmountTextView.setText("Ineligible for a loan due to: " + eligibilityMessage);
                return;
            }

            // Calculate loan amount based on the eligibility criteria
            double maxAmount = Math.max(salary, collateral);
            double loanAmount = (maxAmount * 0.5) * (cibilScore / 800.0);

            // Display loan amount for each bank
            StringBuilder loanAmountsBuilder = new StringBuilder("Loan Amounts:\n");
            for (Map.Entry<String, Double> entry : interestRates.entrySet()) {
                String bank = entry.getKey();
                double interestRate = entry.getValue();
                double totalLoanAmount = loanAmount * (1 + interestRate);
                loanAmountsBuilder.append(bank).append(": ").append(totalLoanAmount).append("\n");
            }
            loanAmountTextView.setText(loanAmountsBuilder.toString());
        } else {
            // Invalid input, display error message
            loanAmountTextView.setText("Invalid input");
        }
    }

    public void onback(View view) {
    }
}
