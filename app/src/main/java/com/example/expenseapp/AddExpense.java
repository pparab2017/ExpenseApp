package com.example.expenseapp;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class AddExpense extends AppCompatActivity {
    static final int REQUEST_IMAGE_GET = 1;
    static final int REQUEST_IMAGE_OPEN = 1;
    Spinner categoryList;
    EditText amount, date, expenseName;
    ImageView imageRecipt;
    Uri fullPhotoUri;
    ArrayList<Expense> addedExpense;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_IMAGE_OPEN && resultCode == RESULT_OK) {
            fullPhotoUri = data.getData();
            // Do work with full size photo saved at fullPhotoUri

            imageRecipt = (ImageView) findViewById(R.id.imageViewFileUpload);
            imageRecipt.setImageURI(fullPhotoUri);



        }
    }


    private void setSpinner() {
        categoryList = (Spinner) findViewById(R.id.spinnerCategory);



        final ArrayAdapter<Category> spinnerArrayAdapter = new ArrayAdapter<Category>(
                this, android.R.layout.simple_list_item_1, Category.values()) {


            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        categoryList.setAdapter(spinnerArrayAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);


        if (getIntent().getExtras() != null) {
            addedExpense = getIntent().getExtras().getParcelableArrayList(MainActivity.LIST_ADD);

        }
        date = (EditText) findViewById(R.id.editTextDate);
        date.setFocusable(false);
        date.setClickable(false);
        setSpinner();

        ImageView cal = (ImageView) findViewById(R.id.imageViewCal);
        ImageView fileUpload = (ImageView) findViewById(R.id.imageViewFileUpload);
        Button btnAddExpense = (Button) findViewById(R.id.buttonAddExpense);

        btnAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                amount = (EditText) findViewById(R.id.editTextAmount);
                expenseName = (EditText) findViewById(R.id.editTextExpenseName);
                date = (EditText) findViewById(R.id.editTextDate);
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                Category check = (Category) categoryList.getSelectedItem();
                Expense newExpense = new Expense();

                if (amount.getText().length() == 0 || date.getText().length() == 0
                        || expenseName.getText().length() == 0 || check == Category.SelectCategory) {

                    Toast required = Toast.makeText(AddExpense.this, getResources().getText(R.string.requiredFeilds) , Toast.LENGTH_SHORT);
                    required.show();
                } else {

                    newExpense.expenseID = addedExpense.size() + 1;
                    newExpense.category = (Category) categoryList.getSelectedItem();
                    newExpense.amount = Double.parseDouble(amount.getText().toString());
                    newExpense.imgUri = fullPhotoUri;
                    newExpense.expenseName = expenseName.getText().toString();
                    try {

                        newExpense.date = dateFormat.parse(date.getText().toString());
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }
                    addedExpense.add(newExpense);

                    Intent toSend = new Intent();
                    toSend.putExtra(MainActivity.LIST_ADD,addedExpense);
                    setResult(RESULT_OK,toSend);
                    finish();

                }

              //  Log.d("demo", newExpense.toString());

            }
        });

        fileUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new  Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, REQUEST_IMAGE_GET);
                }

            }


        });


        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText date = (EditText) findViewById(R.id.editTextDate);
                date.setFocusable(false);
                DateDialog d = new DateDialog();
                d.setTextDate(date);
                FragmentTransaction f = getFragmentManager().beginTransaction();
                d.show(f, "DatePicker");
            }
        });

    }
}
