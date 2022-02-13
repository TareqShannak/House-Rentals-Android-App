package com.example.rentingcompany.NavigationDrawerFragments;

import static com.example.rentingcompany.MainActivity.accountStatus;
import static com.example.rentingcompany.MainActivity.email;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.rentingcompany.DataBase.DataBaseHelper;
import com.example.rentingcompany.DataBase.SHA;
import com.example.rentingcompany.LogInActivity;
import com.example.rentingcompany.Models.RentingAgency;
import com.example.rentingcompany.Models.Tenant;
import com.example.rentingcompany.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    int numFlag = 0;
    int upperCaseflag = 0;
    int specialCharFlag = 0;
    int lowerCaseFlag = 0;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(accountStatus.equalsIgnoreCase("Guest")){
            Toast toast =Toast.makeText(getActivity(), "Log In First..",Toast.LENGTH_SHORT);
            toast.show();
            Intent intent = new Intent(getActivity(), LogInActivity.class);
            getActivity().startActivity(intent);
            getActivity().finish();
        }

        LinearLayout rentingAgencyLayout = (LinearLayout) getActivity().findViewById(R.id.Renting_Agency);
        LinearLayout tenantLayout = (LinearLayout) getActivity().findViewById(R.id.Tenant);


        if (accountStatus.equalsIgnoreCase("RENTINGAGENCY")) {

            tenantLayout.setVisibility(View.INVISIBLE);
            rentingAgencyLayout.setVisibility(View.VISIBLE);


        } else {

            rentingAgencyLayout.setVisibility(View.INVISIBLE);
            tenantLayout.setVisibility(View.VISIBLE);

        }





        /*
         *#################################################################################################################################################
         * #################################################################################################################################################
         * ****************************************************           RENTING AGENCY          ******************************************************************
         * #################################################################################################################################################
         * #################################################################################################################################################
         */









        int rentingAgency_arr[] = {0, 0, 0, 0, 0, 0};


        EditText agencyName = (EditText) getActivity().findViewById(R.id.updateRenting_agencyName);
        EditText password = (EditText) getActivity().findViewById(R.id.updateRenting_password);
        EditText confirmpassword = (EditText) getActivity().findViewById(R.id.updateRenting_confirmpassword);
        EditText phoneNumber = (EditText) getActivity().findViewById(R.id.updateRenting_phoneNumber);

        TextView phoneNumberError = (TextView) getActivity().findViewById(R.id.updateRenting_phoneNumberError);
        TextView countrySpinnerError = (TextView) getActivity().findViewById(R.id.updateRenting_countrySpinnerError);
        TextView citySpinnError = (TextView) getActivity().findViewById(R.id.updateRenting_citySpinnError);
        TextView passwordError = (TextView) getActivity().findViewById(R.id.updateRenting_passwordError);
        TextView agencyNameError = (TextView) getActivity().findViewById(R.id.updateRenting_agencyNameError);
        TextView confirmpasswordError = (TextView) getActivity().findViewById(R.id.updateRenting_confirmpasswordError);

        Button confirm = (Button) getActivity().findViewById(R.id.updateRenting_confirm);

        String[] currentResidenceCountryoptions = {"Palestine", "Algeria", "Jordan", "Qatar", "Syria", "Lebanon"};
        Spinner countrySpinner = (Spinner) getActivity().findViewById(R.id.updateRenting_countrySpinner);

        ArrayAdapter<String> arrayAdapter_parent = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, currentResidenceCountryoptions);
        countrySpinner.setAdapter(arrayAdapter_parent);


        String[] palestineCities = {"Jerusalem", "Ramallah", "Gaza", " Hebron", "Nablus", "Akka", "Bethlehem"};
        String[] AlgeriaCities = {"Oran", "Oran", "Constantine", "Annaba", "Djelfa", "Biskra", "Setif"};
        String[] JordanCities = {"Amman", "Zarqa", "Irbid", "Russeifa", "Wadi as-Ser", "Madaba", "al-Baq'a", "Sahab"};
        String[] QatarCities = {"Doha", "Abu az Zuluf", "Abu Thaylah", "Al Ghanim", "Al Ghuwariyah", "Al `Arish"};
        String[] SyriaCities = {"Aleppo", "Damascus", "Homs", "Latakia", "Hama", "Qamishli", "Tartus"};
        String[] LebanonCities = {"Beirut", "Tripoli", "Sidon", "Zahle", "Batroun", "Tyre"};


        Spinner citySpinn = (Spinner) getActivity().findViewById(R.id.updateRenting_citySpinn);

        ArrayAdapter<String> objCCClityArr = new
                ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, palestineCities);
        citySpinn.setAdapter(objCCClityArr);

        phoneNumber.setText("00970");

        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    ArrayAdapter<String> arrayAdapter_child = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, palestineCities);
                    citySpinn.setAdapter(arrayAdapter_child);
                    phoneNumber.setText("00970");
                }
                if (i == 1) {
                    ArrayAdapter<String> arrayAdapter_child = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, AlgeriaCities);
                    citySpinn.setAdapter(arrayAdapter_child);
                    phoneNumber.setText("00213");
                }
                if (i == 2) {
                    ArrayAdapter<String> arrayAdapter_child = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, JordanCities);
                    citySpinn.setAdapter(arrayAdapter_child);
                    phoneNumber.setText("00962");
                }
                if (i == 3) {
                    ArrayAdapter<String> arrayAdapter_child = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, QatarCities);
                    citySpinn.setAdapter(arrayAdapter_child);
                    phoneNumber.setText("00974");
                }
                if (i == 4) {
                    ArrayAdapter<String> arrayAdapter_child = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, SyriaCities);
                    citySpinn.setAdapter(arrayAdapter_child);
                    phoneNumber.setText("00963");
                }
                if (i == 5) {
                    ArrayAdapter<String> arrayAdapter_child = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, LebanonCities);
                    citySpinn.setAdapter(arrayAdapter_child);
                    phoneNumber.setText("00961");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                ArrayAdapter<String> arrayAdapter_child = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, palestineCities);
                citySpinn.setAdapter(arrayAdapter_child);

            }
        });


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (agencyName.getText().toString().isEmpty()) {
                    rentingAgency_arr[0] = 0;
                    agencyNameError.setText("Required Field");
                } else if (agencyName.getText().toString().length() < 21) {
                    agencyNameError.setText("");
                    rentingAgency_arr[0] = 1;
                } else {
                    agencyName.setText("");
                    agencyNameError.setText("Large Input!! maximum letters is 20");
                    rentingAgency_arr[0] = 0;
                }


                if (password.getText().toString().isEmpty()) {
                    passwordError.setText("Required Field");
                    rentingAgency_arr[1] = 0;
                } else if (password.getText().toString().length() > 7 && password.getText().toString().length() < 16) {
                    String sample = password.getText().toString();
                    String specialChar = "$%#@!{&}";
                    char[] specialCHARS = specialChar.toCharArray();
                    char[] chars = sample.toCharArray();
                    for (char c : chars) {
                        if (Character.isDigit(c)) {
                            numFlag = 1;
                        }
                        if (Character.isUpperCase(c)) {
                            upperCaseflag = 1;
                        }
                        if (Character.isLowerCase(c)) {
                            lowerCaseFlag = 1;
                        }
                        //Check if it consist special character
                        for (char x : specialCHARS) {
                            System.out.println(x);
                            if (x == c) {
                                specialCharFlag = 1;
                                // emailAddressError.setText(x);
                            }
                        }

                    }

                    if (numFlag == 1 && upperCaseflag == 1 && specialCharFlag == 1 && lowerCaseFlag == 1) {
                        passwordError.setText("");
                        rentingAgency_arr[1] = 1;
                    } else {
                        passwordError.setText("Weak Password");
                        password.setText("");
                        rentingAgency_arr[1] = 0;
                    }
                } else {
                    passwordError.setText("Weak Password");
                    password.setText("");
                    rentingAgency_arr[1] = 0;
                }


                if (confirmpassword.getText().toString().isEmpty()) {
                    confirmpasswordError.setText("Required Field");
                    rentingAgency_arr[2] = 0;
                } else if (confirmpassword.getText().toString().equals(password.getText().toString())) {
                    confirmpasswordError.setText("");
                    rentingAgency_arr[2] = 1;
                    /*
                     * Encrypt using SHA
                     */
                } else {
                    confirmpassword.setText("");
                    confirmpasswordError.setText("Error! paswword is not the same");
                    rentingAgency_arr[2] = 0;
                }


                if (countrySpinner.getSelectedItem().toString().isEmpty()) {
                    countrySpinnerError.setText("Required Field");
                    rentingAgency_arr[3] = 0;
                } else {
                    countrySpinnerError.setText("");
                    rentingAgency_arr[3] = 1;
                }


                countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i == 0) {
                            ArrayAdapter<String> arrayAdapter_child = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, palestineCities);
                            citySpinn.setAdapter(arrayAdapter_child);
                            phoneNumber.setText("00970");
                        }
                        if (i == 1) {
                            ArrayAdapter<String> arrayAdapter_child = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, AlgeriaCities);
                            citySpinn.setAdapter(arrayAdapter_child);
                            phoneNumber.setText("00213");
                        }
                        if (i == 2) {
                            ArrayAdapter<String> arrayAdapter_child = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, JordanCities);
                            citySpinn.setAdapter(arrayAdapter_child);
                            phoneNumber.setText("00962");
                        }
                        if (i == 3) {
                            ArrayAdapter<String> arrayAdapter_child = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, QatarCities);
                            citySpinn.setAdapter(arrayAdapter_child);
                            phoneNumber.setText("00974");
                        }
                        if (i == 4) {
                            ArrayAdapter<String> arrayAdapter_child = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, SyriaCities);
                            citySpinn.setAdapter(arrayAdapter_child);
                            phoneNumber.setText("00963");
                        }
                        if (i == 5) {
                            ArrayAdapter<String> arrayAdapter_child = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, LebanonCities);
                            citySpinn.setAdapter(arrayAdapter_child);
                            phoneNumber.setText("00961");
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        ArrayAdapter<String> arrayAdapter_child = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, palestineCities);
                        citySpinn.setAdapter(arrayAdapter_child);

                    }
                });

                rentingAgency_arr[4] = 1;


                if (phoneNumber.getText().toString().length() < 5) {
                    rentingAgency_arr[5] = 0;
                    phoneNumberError.setText("Required Field");
                } else if (phoneNumber.getText().toString().matches("[-+]?\\d*\\.?\\d+")) {
                    phoneNumberError.setText("");
                    rentingAgency_arr[5] = 1;
                } else {
                    rentingAgency_arr[5] = 0;
                    phoneNumberError.setText("Invalid Input!!");
                }


                // Correct password & email
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {


                        for (int i = 0; i < 6; i++) {
                            if (rentingAgency_arr[i] == 0) {
                                Toast.makeText(getActivity().getApplicationContext(),
                                        "Sign Up process failed, Try again please", Toast.LENGTH_LONG).show();
                                break;
                            } else if (i == 5) {


                                //ADD DATA INTO DATABase

                                RentingAgency newRentingAgency = new RentingAgency();
                                newRentingAgency.setAgencyName(agencyName.getText().toString());
                                newRentingAgency.setPassword(SHA.encryptSHA512(password.getText().toString()));
                                newRentingAgency.setConfirmPassword(confirmpassword.getText().toString());
                                newRentingAgency.setCountry(countrySpinner.getSelectedItem().toString());
                                newRentingAgency.setCity(citySpinn.getSelectedItem().toString());
                                newRentingAgency.setPhoneNumber(phoneNumber.getText().toString());

                                DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity(), "EXP4", null, 1);
                                dataBaseHelper.updateRentingAgency(newRentingAgency, email);



                                /*
                                 *
                                 *   GO TO THE PREVIOUS FRAGMENT
                                 *
                                 */


                            }
                        }


                    }
                }, 1000);


            }

        });











        /*
         *#################################################################################################################################################
         * #################################################################################################################################################
         * ****************************************************           TENANT          ******************************************************************
         * #################################################################################################################################################
         * #################################################################################################################################################
         */






        EditText firstNameTextField = (EditText) getActivity().findViewById(R.id.tenantProfile_firstNameTextField);
        EditText lastNameTextField = (EditText) getActivity().findViewById(R.id.tenantProfile_lastNameTextField);
        EditText passwordTextField = (EditText) getActivity().findViewById(R.id.tenantProfile_passwordTextField);
        EditText confirmPasswordTextFiled = (EditText) getActivity().findViewById(R.id.tenantProfile_confirmPasswordTextFiled);
        EditText grossMonthlySalaryTextField = (EditText) getActivity().findViewById(R.id.tenantProfile_grossMonthlySalaryTextField);
        EditText occupationTextField = (EditText) getActivity().findViewById(R.id.tenantProfile_occupationTextField);
        EditText familySizeTextField = (EditText) getActivity().findViewById(R.id.tenantProfile_familySizeTextField);
        EditText phoneNumberTextField = (EditText) getActivity().findViewById(R.id.tenantProfile_phoneNumberTextField);



        TextView firstNameErrorTxtView = (TextView) getActivity().findViewById(R.id.tenantProfile_firstNameErrorTxtView);
        TextView lastNmaeErrorTxtView = (TextView) getActivity().findViewById(R.id.tenantProfile_lastNmaeErrorTxtView);
        TextView genderErrorTxtView = (TextView) getActivity().findViewById(R.id.tenantProfile_genderErrorTxtView);
        TextView passwordErrorTxtView = (TextView) getActivity().findViewById(R.id.tenantProfile_passwordErrorTxtView);
        TextView confirmPasswordErrorTxtView = (TextView) getActivity().findViewById(R.id.tenantProfile_confirmPasswordErrorTxtView);
        TextView nationalityErrorTxtView = (TextView) getActivity().findViewById(R.id.tenantProfile_nationalityErrorTxtView);
        TextView grossMonthlySalaryErrorTxtView = (TextView) getActivity().findViewById(R.id.tenantProfile_grossMonthlySalaryErrorTxtView);
        TextView occupationErrorTxtView = (TextView) getActivity().findViewById(R.id.tenantProfile_occupationErrorTxtView);
        TextView familySizeErrorTxtView = (TextView) getActivity().findViewById(R.id.tenantProfile_familySizeErrorTxtView);
        TextView currentresidenceErrorTxtView = (TextView) getActivity().findViewById(R.id.tenantProfile_currentresidenceErrorTxtView);
        TextView cityErrorTxtView = (TextView) getActivity().findViewById(R.id.tenantProfile_cityErrorTxtView);
        TextView phoneNumberErrorTxtView = (TextView) getActivity().findViewById(R.id.tenantProfile_phoneNumberErrorTxtView);





        Button confirmButton = (Button) getActivity().findViewById(R.id.tenantProfile_confirmButton);
        int tenant_rentingAgency_arr[] = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        String[] genderOptions = {"Male", "Female"};
        final Spinner genderSpinner = (Spinner)
                getActivity().findViewById(R.id.tenantProfile_genderSpinner);
        ArrayAdapter<String> objGenderArr = new
                ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, genderOptions);
        genderSpinner.setAdapter(objGenderArr);


        String[] nationalityOptions = { "Palestinian", "Algerian" ,"Jordanian","Qatari","Syrian","Lebanese","Egyptian","Turkey","Tunisia"};
        final Spinner nationalitySpinner =(Spinner)
                getActivity().findViewById(R.id.tenantProfile_nationalitySpinner);
        ArrayAdapter<String> objNationalityArr = new
                ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, nationalityOptions);
        nationalitySpinner.setAdapter(objNationalityArr);



        Spinner currentResidenceCountrySpinner = (Spinner) getActivity().findViewById(R.id.tenantProfile_currentResidenceCountrySpinner);

        currentResidenceCountrySpinner.setAdapter(arrayAdapter_parent);
        Spinner citySpinner = (Spinner)  getActivity().findViewById(R.id.tenantProfile_citySpinner);
        phoneNumberTextField.setText("00970");

        currentResidenceCountrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    ArrayAdapter<String> arrayAdapter_child = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, palestineCities);
                    citySpinner.setAdapter(arrayAdapter_child);
                    phoneNumberTextField.setText("00970");
                }
                if (i == 1) {
                    ArrayAdapter<String>arrayAdapter_child = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, AlgeriaCities);
                    citySpinner.setAdapter(arrayAdapter_child);
                    phoneNumberTextField.setText("00213");
                }
                if (i == 2) {
                    ArrayAdapter<String>arrayAdapter_child = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, JordanCities);
                    citySpinner.setAdapter(arrayAdapter_child);
                    phoneNumberTextField.setText("00962");
                }
                if (i == 3) {
                    ArrayAdapter<String>arrayAdapter_child = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, QatarCities);
                    citySpinner.setAdapter(arrayAdapter_child);
                    phoneNumberTextField.setText("00974");
                }
                if (i == 4) {
                    ArrayAdapter<String> arrayAdapter_child = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, SyriaCities);
                    citySpinner.setAdapter(arrayAdapter_child);
                    phoneNumberTextField.setText("00963");
                }
                if (i == 5) {
                    ArrayAdapter<String> arrayAdapter_child = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, LebanonCities);
                    citySpinner.setAdapter(arrayAdapter_child);
                    phoneNumberTextField.setText("00961");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                ArrayAdapter<String> arrayAdapter_child = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, palestineCities);
                citySpinner.setAdapter(arrayAdapter_child);

            }
        });


        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                phoneNumberTextField.setText("00970");



                if (firstNameTextField.getText().toString().isEmpty()) {
                    firstNameErrorTxtView.setText("Required Field");
                    tenant_rentingAgency_arr[0] = 0;
                } else {
                    if (2 < firstNameTextField.getText().toString().length() && firstNameTextField.getText().toString().length() < 21) {
                        firstNameErrorTxtView.setText("");
                        tenant_rentingAgency_arr[0] = 1;
                    } else if (firstNameTextField.getText().toString().length() > 20) {
                        firstNameTextField.setText("");
                        firstNameErrorTxtView.setText("Large Name ; maximum 20 letters");
                        tenant_rentingAgency_arr[0] = 0;
                    } else if (firstNameTextField.getText().toString().length() < 3) {
                        firstNameTextField.setText("");
                        firstNameErrorTxtView.setText("Short Name ; minimum 3 letters");
                        tenant_rentingAgency_arr[0] = 0;
                    }
                }







                if (lastNameTextField.getText().toString().isEmpty()) {
                    lastNmaeErrorTxtView.setText("Required Field");
                    tenant_rentingAgency_arr[1] = 0;
                } else {
                    if (2 < lastNameTextField.getText().toString().length() && lastNameTextField.getText().toString().length() < 21) {
                        lastNmaeErrorTxtView.setText("");
                        tenant_rentingAgency_arr[1] = 1;
                    } else if (lastNameTextField.getText().toString().length() > 20) {
                        lastNameTextField.setText("");
                        lastNmaeErrorTxtView.setText("Large Name ; maximum 20 letters");
                        tenant_rentingAgency_arr[1] = 0;
                    } else if (lastNameTextField.getText().toString().length() < 3) {
                        lastNameTextField.setText("");
                        lastNmaeErrorTxtView.setText("Short Name ; minimum 3 letters");
                        tenant_rentingAgency_arr[1] = 0;
                    }
                }






                if (genderSpinner.getSelectedItem().toString().isEmpty()) {
                    genderErrorTxtView.setText("Required Field");
                    tenant_rentingAgency_arr[2] = 0;
                } else {
                    genderErrorTxtView.setText("");
                    tenant_rentingAgency_arr[2] = 1;
                }





                if (passwordTextField.getText().toString().isEmpty()) {
                    passwordErrorTxtView.setText("Required Field");
                    tenant_rentingAgency_arr[3] = 0;
                } else if (passwordTextField.getText().toString().length() > 7 && passwordTextField.getText().toString().length() < 16) {
                    String sample = passwordTextField.getText().toString();
                    String specialChar = "$%#@!{&}";
                    char[] specialCHARS = specialChar.toCharArray();
                    char[] chars = sample.toCharArray();
                    for (char c : chars) {
                        if (Character.isDigit(c)) {
                            numFlag = 1;
                        }
                        if (Character.isUpperCase(c)) {
                            upperCaseflag = 1;
                        }
                        if (Character.isLowerCase(c)) {
                            lowerCaseFlag = 1;
                        }
                        //Check if it consist special character
                        for (char x : specialCHARS) {
                            if (x == c) {
                                specialCharFlag = 1;
                            }
                        }

                    }

                    if (numFlag == 1 && upperCaseflag == 1 && specialCharFlag == 1 && lowerCaseFlag == 1) {
                        passwordErrorTxtView.setText("");
                        tenant_rentingAgency_arr[3] = 1;
                    }else {
                        passwordErrorTxtView.setText("Weak Password");
                        passwordTextField.setText("");
                        tenant_rentingAgency_arr[3] = 0;
                    }
                } else {
                    passwordErrorTxtView.setText("Weak Password");
                    passwordTextField.setText("");
                    tenant_rentingAgency_arr[3] = 0;
                }





                if (confirmPasswordTextFiled.getText().toString().isEmpty()) {
                    confirmPasswordErrorTxtView.setText("Required Field");
                    tenant_rentingAgency_arr[4] = 0;
                } else if (confirmPasswordTextFiled.getText().toString().equals(passwordTextField.getText().toString())) {
                    confirmPasswordErrorTxtView.setText("");
                    tenant_rentingAgency_arr[4] = 1;
                    /*
                     * Encrypt using SHA
                     */
                } else {
                    confirmPasswordTextFiled.setText("");
                    confirmPasswordErrorTxtView.setText("Error! paswword is not the same");
                    tenant_rentingAgency_arr[4] = 0;
                }



                if (nationalitySpinner.getSelectedItem().toString().isEmpty()) {
                    nationalityErrorTxtView.setText("Required Field");
                    tenant_rentingAgency_arr[5] = 0;
                } else {
                    nationalityErrorTxtView.setText("");
                    tenant_rentingAgency_arr[5] = 1;
                }



                if (grossMonthlySalaryTextField.getText().toString().isEmpty()) {
                    tenant_rentingAgency_arr[6] = 0;
                    grossMonthlySalaryErrorTxtView.setText("Required Field");
                } else if (grossMonthlySalaryTextField.getText().toString().matches("[-+]?\\d*\\.?\\d+")) {
                    grossMonthlySalaryErrorTxtView.setText("");
                    tenant_rentingAgency_arr[6] = 1;
                } else {
                    grossMonthlySalaryTextField.setText("");
                    grossMonthlySalaryErrorTxtView.setText("Invalid Input!! Use Numbers Only");
                    tenant_rentingAgency_arr[6] = 0;
                }


                if (occupationTextField.getText().toString().isEmpty()) {
                    tenant_rentingAgency_arr[7] = 0;
                    occupationErrorTxtView.setText("Required Field");
                } else if (occupationTextField.getText().toString().length() < 21) {
                    occupationErrorTxtView.setText("");
                    tenant_rentingAgency_arr[7] = 1;
                } else {
                    occupationTextField.setText("");
                    occupationErrorTxtView.setText("Large Input!! maximum letters is 20");
                    tenant_rentingAgency_arr[7] = 0;
                }




                if (familySizeTextField.getText().toString().isEmpty()) {
                    tenant_rentingAgency_arr[8] = 0;
                    familySizeErrorTxtView.setText("Required Field");
                } else if (familySizeTextField.getText().toString().matches("[-+]?\\d*\\.?\\d+")) {
                    familySizeErrorTxtView.setText("");
                    tenant_rentingAgency_arr[8] = 1;
                } else {
                    familySizeTextField.setText("");
                    familySizeErrorTxtView.setText("Invalid Input!! Use Numbers Only");
                    tenant_rentingAgency_arr[8] = 0;
                }


                if (currentResidenceCountrySpinner.getSelectedItem().toString().isEmpty()) {
                    currentresidenceErrorTxtView.setText("Required Field");
                    tenant_rentingAgency_arr[9] = 0;
                } else {
                    currentresidenceErrorTxtView.setText("");
                    tenant_rentingAgency_arr[9] = 1;
                }

                currentResidenceCountrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i == 0) {
                            ArrayAdapter<String> arrayAdapter_child = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, palestineCities);
                            citySpinner.setAdapter(arrayAdapter_child);
                            phoneNumberTextField.setText("00970");
                        }
                        if (i == 1) {
                            ArrayAdapter<String>arrayAdapter_child = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, AlgeriaCities);
                            citySpinner.setAdapter(arrayAdapter_child);
                            phoneNumberTextField.setText("00213");
                        }
                        if (i == 2) {
                            ArrayAdapter<String>arrayAdapter_child = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, JordanCities);
                            citySpinner.setAdapter(arrayAdapter_child);
                            phoneNumberTextField.setText("00962");
                        }
                        if (i == 3) {
                            ArrayAdapter<String>arrayAdapter_child = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, QatarCities);
                            citySpinner.setAdapter(arrayAdapter_child);
                            phoneNumberTextField.setText("00974");
                        }
                        if (i == 4) {
                            ArrayAdapter<String> arrayAdapter_child = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, SyriaCities);
                            citySpinner.setAdapter(arrayAdapter_child);
                            phoneNumberTextField.setText("00963");
                        }
                        if (i == 5) {
                            ArrayAdapter<String> arrayAdapter_child = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, LebanonCities);
                            citySpinner.setAdapter(arrayAdapter_child);
                            phoneNumberTextField.setText("00961");
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        ArrayAdapter<String> arrayAdapter_child = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, palestineCities);
                        citySpinner.setAdapter(arrayAdapter_child);

                    }
                });

                tenant_rentingAgency_arr[10]=1;


                if (phoneNumberTextField.getText().toString().length() < 5) {
                    tenant_rentingAgency_arr[11] = 0;
                    phoneNumberErrorTxtView.setText("Required Field");
                } else if (phoneNumberTextField.getText().toString().matches("[-+]?\\d*\\.?\\d+")) {
                    phoneNumberErrorTxtView.setText("");
                    tenant_rentingAgency_arr[11] = 1;
                } else {
                    tenant_rentingAgency_arr[11] = 0;
                    phoneNumberErrorTxtView.setText("Invalid Input!!");
                }

                // Correct password & email
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {


                        for(int i=0 ;i<12;i++){
                            if(tenant_rentingAgency_arr[i]==0){
                                Toast.makeText(getActivity().getApplicationContext(),
                                        "Sign Up process failed, Try again please", Toast.LENGTH_LONG).show();
                                break;
                            }else if(i==11){
                                //ADD DATA INTO DATABase

                                Tenant newTenant = new Tenant();

                                newTenant.setFirstName(firstNameTextField.getText().toString());
                                newTenant.setLastName(lastNameTextField.getText().toString());
                                newTenant.setGender(genderSpinner.getSelectedItem().toString());
                                newTenant.setPassword(SHA.encryptSHA512(passwordTextField.getText().toString()));
                                newTenant.setConfirmPassword(SHA.encryptSHA512(confirmPasswordTextFiled.getText().toString()));
                                newTenant.setNationality(nationalitySpinner.getSelectedItem().toString());
                                newTenant.setGrossMonthlySalary(grossMonthlySalaryTextField.getText().toString());
                                newTenant.setOccupation(occupationTextField.getText().toString());
                                newTenant.setFamilySize(familySizeTextField.getText().toString());
                                newTenant.setCurrentResidenceCountry(currentResidenceCountrySpinner.getSelectedItem().toString());
                                newTenant.setCity(citySpinner.getSelectedItem().toString());
                                newTenant.setPhoneNumber(phoneNumberTextField.getText().toString());

                                DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity(), "EXP4", null, 1);
                                dataBaseHelper.updateTenant(newTenant,email);

                                /*
                                 *
                                 *   Go to home page
                                 */



                            }
                        }



                    }
                }, 1000);


            }

        });

    }
}