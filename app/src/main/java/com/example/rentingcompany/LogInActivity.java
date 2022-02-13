package com.example.rentingcompany;

import static com.example.rentingcompany.MainActivity.accountStatus;
import static com.example.rentingcompany.MainActivity.email;
import static com.example.rentingcompany.MainActivity.username;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rentingcompany.DataBase.DataBaseHelper;
import com.example.rentingcompany.DataBase.SHA;
import com.example.rentingcompany.SharedPrefrences.SharedPrefManager;
import com.example.rentingcompany.SignUpActivities.SignUpActivity;

import java.util.ArrayList;

public class LogInActivity extends AppCompatActivity {
    ArrayList<String> emailArrayList = new ArrayList();
    ArrayList<String> passwordArrayList = new ArrayList();
    ArrayList<String> status = new ArrayList();
    int emailExist, passwordExist, index = 0;
    int checkBoxFlag;
    SharedPrefManager sharedPrefManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);


        EditText emailText =
                (EditText) findViewById(R.id.emailPlainText);

        EditText passwordText =
                (EditText) findViewById(R.id.passwordPlainText);

        TextView emailError = (TextView) findViewById(R.id.emailErrorPlaintext);
        TextView passwordError = (TextView) findViewById(R.id.passwordErrorPlaintext);


        Button signInButton = (Button) findViewById(R.id.signInButton);
        Button signUpButton = (Button) findViewById(R.id.signUpButton);
        Button guestButton = (Button) findViewById(R.id.guestButton);

        CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);

        sharedPrefManager = SharedPrefManager.getInstance(this);
        checkBoxFlag = 0;

        emailText.setText(sharedPrefManager.readString("Email", ""));
        passwordText.setText(sharedPrefManager.readString("Password", ""));
        checkBox.setChecked(sharedPrefManager.readBoolean("RememberMe", false));

        if (sharedPrefManager.readBoolean("RememberMe", false))
            checkBoxFlag = 1;


        DataBaseHelper dataBaseHelper = new
                DataBaseHelper(LogInActivity.this, "EXP4", null, 1);
        Cursor allLogInCursor = dataBaseHelper.getAllSignInData();

        while (allLogInCursor.moveToNext()) {
            emailArrayList.add(allLogInCursor.getString(0));
            passwordArrayList.add(allLogInCursor.getString(1));
            status.add(allLogInCursor.getString(2));

        }

        /*DataBaseHelper dataBaseHelper1 = new DataBaseHelper(Log_in_page.this, "EXP4", null, 1);
        Cursor allCustomersCursor = dataBaseHelper1.getRentingAgencyData();
        while (allCustomersCursor.moveToNext()) {

            System.out.println("\n\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++" + "emailAddress= " + allCustomersCursor.getString(0)
                    + "\nagencyName= " + allCustomersCursor.getString(1)
                    + "\npassword= " + allCustomersCursor.getString(2)
                    + "\nconfirmpassword= " + allCustomersCursor.getString(3)
                    + "countrySpinner\n" + allCustomersCursor.getString(4)
                    + "citySpinn\n" + allCustomersCursor.getString(5)
                    + "phoneNumber\n" + allCustomersCursor.getString(6) + "\n\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

        }*/




        /*
         * ################################      SIGN IN      ################################
         */
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (emailText.getText().toString().isEmpty()) {
                    emailError.setText("Please Enter Your Email!!");

                } else {
                    for (String element : emailArrayList) {
                        System.out.println("email array list = " + element);
                        if (element.compareTo(emailText.getText().toString()) == 0) {
                            emailExist = 1;
                            accountStatus = status.get(emailArrayList.indexOf(element));
                            break;
                        } else {
                            emailExist = 0;
                        }
                        index += 1;
                    }
                }


                /*
                 * add case when both email and password empty
                 */
                if (passwordText.getText().toString().isEmpty()) {
                    passwordError.setText("Please Enter Email Password!!");
                } else if (emailExist == 1) {

                    if (SHA.encryptSHA512(passwordText.getText().toString()).compareTo(passwordArrayList.get(index)) == 0) {
                        passwordExist = 1;
                        index = 0;
                    } else {
                        passwordExist = 0;
                        passwordError.setText("Incorrect Password, try again ");

                    }

                }

                //status.get(index); : used inside password if else line 97 give email and password status Tenant or Renting Agency


                if (emailExist == 1 && passwordExist == 1 && checkBoxFlag == 1) {
                    sharedPrefManager.writeString("Email", emailText.getText().toString().trim());
                    sharedPrefManager.writeString("Password", passwordText.getText().toString().trim());
                    sharedPrefManager.writeBoolean("RememberMe", true);
                }

                if (emailExist == 1 && passwordExist == 1 && checkBoxFlag == 0) {
                    sharedPrefManager.writeString("Email", "");
                    sharedPrefManager.writeString("Password", "");
                    sharedPrefManager.writeBoolean("RememberMe", false);

                }

                if (emailExist == 1 && passwordExist == 1) {
                    MainActivity.email = emailText.getText().toString();
                    DataBaseHelper DBHelper = new DataBaseHelper(LogInActivity.this, "EXP4", null, 1);
                    Cursor cursor = DBHelper.getReadableDatabase().rawQuery("Select STATUS from SIGNIN WHERE EMAIL LIKE '" + emailText.getText().toString() + "'", null);
                    Cursor cursor2;
                    if (cursor.moveToNext()) {
                        if (cursor.getString(0).equalsIgnoreCase("RENTINGAGENCY")) {
                            cursor2 = DBHelper.getReadableDatabase().rawQuery("SELECT AGENCYNAME FROM RENTINGAGENCY WHERE EMAIL LIKE '" + emailText.getText().toString() + "'", null);
                            if (cursor2.moveToNext())
                                MainActivity.username = cursor2.getString(0);
                        } else {
                            cursor2 = DBHelper.getReadableDatabase().rawQuery("SELECT FIRSTNAME, LASTNAME FROM TENANT WHERE EMAIL LIKE '" + emailText.getText().toString() + "'", null);
                            if (cursor2.moveToNext())
                                MainActivity.username = cursor2.getString(0) + " " + cursor2.getString(1);
                        }
                    }


                    Intent intent;
                    if(accountStatus.equalsIgnoreCase("Renting Agency") || accountStatus.equalsIgnoreCase("RENTINGAGENCY"))
                        intent = new Intent(LogInActivity.this, RentingAgencyActivity.class);
                    else
                        intent = new Intent(LogInActivity.this, TenantActivity.class);

                    LogInActivity.this.startActivity(intent);
                    finish();


                } else if (emailExist == 1) {
                    passwordText.setText("");
                } else {
                    emailText.setText("");
                    passwordText.setText("");
                }

            }


        });

        /*
         * CheckBox case
         */
        checkBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                if (checkBox.isChecked())
                    checkBoxFlag = 1;
                else
                    checkBoxFlag = 0;
            }
        });

        //###################### SIGNUP ###############################

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
                LogInActivity.this.startActivity(intent);
                finish();
            }

        });

        //###################### Guest ###############################
        guestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = "";
                username = "Guest";
                accountStatus = "Guest";
                Intent intent = new Intent(LogInActivity.this, TenantActivity.class);
                LogInActivity.this.startActivity(intent);
                finish();
            }
        });


    }

}