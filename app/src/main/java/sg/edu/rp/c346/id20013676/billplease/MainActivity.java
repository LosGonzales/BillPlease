package sg.edu.rp.c346.id20013676.billplease;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView total = findViewById(R.id.textTotal);
        TextView pays = findViewById(R.id.textPays);
        Button split = findViewById(R.id.buttonSplit);
        Button reset = findViewById(R.id.buttonReset);
        EditText amount = findViewById(R.id.inputAmount);
        EditText pax = findViewById(R.id.inputPax);
        EditText discount = findViewById(R.id.inputDiscount);
        ToggleButton svs = findViewById(R.id.toggleSvs);
        ToggleButton gst = findViewById(R.id.toggleGst);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        RadioButton radioCash = findViewById(R.id.radioCash);
        RadioButton radioPayNow = findViewById(R.id.radioPayNow);
        TextView methodText = findViewById(R.id.methodText);
        TextView errorText = findViewById(R.id.errorText);


        split.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(amount.getText().toString().trim().length()!=0 && pax.getText().toString().trim().length()!=0) {
                    if (Integer.parseInt(amount.getText().toString()) == 0) {
                        errorText.setText("Please enter an amount greater than 0");
                    } else if (Integer.parseInt(pax.getText().toString()) == 0) {
                        errorText.setText("Pax must be greater than 0");
                    } else {
                        double newAmt;
                        double multiplier = 1.0;
                        if (!svs.isChecked() && gst.isChecked()) {
                            multiplier = 1.07;
                        } else if (svs.isChecked() && !gst.isChecked()) {
                            multiplier = 1.1;
                        } else if (svs.isChecked() && gst.isChecked()) {
                            multiplier = 1.177;
                        }
                        newAmt = Double.parseDouble(amount.getText().toString()) * multiplier;

                        boolean valid = true;
                        if (discount.getText().toString().trim().length() != 0) {
                            if (Integer.parseInt(discount.getText().toString()) < 100 &&
                                    Integer.parseInt(discount.getText().toString()) >= 0) {
                                newAmt *= 1 - Double.parseDouble(discount.getText().toString()) / 100;
                                valid = true;
                            }
                            else {
                                valid = false;
                            }
                        }

                        if (valid) {
                            int numPax = Integer.parseInt(pax.getText().toString());
                            total.setText(String.format("$%.2f", newAmt));
                            pays.setText(String.format("$%.2f", newAmt / numPax));
                            int rgId = radioGroup.getCheckedRadioButtonId();
                            if (rgId == R.id.radioCash) {
                                methodText.setText("In Cash");
                            } else if (rgId == R.id.radioPayNow) {
                                methodText.setText("Via PayNow to 912345678");
                            }
                            errorText.setText("");
                        } else {
                            errorText.setText("Please enter a valid discount amount");
                        }
                    }
                }

                else {
//                    Toast toast = Toast.makeText((MainActivity.this), "Please do not leave Amount or Num of Pax empty",
//                            Toast.LENGTH_SHORT);
//                    toast.show();
                    errorText.setText("Please do not leave first two fields blank");
                }


            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount.setText("");
                pax.setText("");
                discount.setText("");
                total.setText("$0.00");
                pays.setText("$0.00");
                svs.setChecked(false);
                gst.setChecked(false);
                radioCash.setChecked(true);
                methodText.setText("");
                errorText.setText("");
            }
        });

    }
}