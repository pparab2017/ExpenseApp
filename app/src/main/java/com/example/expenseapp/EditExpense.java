package com.example.expenseapp;


import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
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

public class EditExpense extends AppCompatActivity {
    ArrayList<Expense> editExpenseList;
    ArrayAdapter<Category> spinnerArrayAdapter;
    int selectedExpense;
    EditText expense,amount,date;
    Spinner categoryList;
    ArrayAdapter adpater;
    ImageView cal;
    ImageView fileUpload;
    static final int REQUEST_IMAGE_GET = 1;
    static final int REQUEST_IMAGE_OPEN = 1;
    ImageView imageRecipt;
    Uri fullPhotoUri;
    Button btnSave,btnFinish;


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




         spinnerArrayAdapter = new ArrayAdapter<Category>(
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




    private void MakeAlert()
    {
         adpater = new ArrayAdapter<Expense>(this,android.R.layout.simple_list_item_1, editExpenseList){
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

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("Pick an Expense")
                .setCancelable(false)
                .setAdapter(adpater, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        expense =  (EditText) findViewById(R.id.editTextExpenseName);
                        amount = (EditText) findViewById(R.id.editTextAmount);
                        date = (EditText) findViewById(R.id.editTextDate);
                        imageRecipt = (ImageView) findViewById(R.id.imageViewFileUpload);
                        selectedExpense = which;
                        btnSave.setEnabled(true);

                        SimpleDateFormat properFormat = new SimpleDateFormat("M/d/yyyy");
                        String finalDate = properFormat.format(editExpenseList.get(which).date);
                        expense.setText(editExpenseList.get(which).expenseName);
                        date.setText(finalDate);
                        amount.setText(String.valueOf(editExpenseList.get(which).amount));
                        int pos= spinnerArrayAdapter.getPosition(editExpenseList.get(which).getCategory());
                        categoryList.setSelection(pos);
                        imageRecipt.setImageURI(editExpenseList.get(which).getImgUri());


                    }


                });

        final AlertDialog d = alertBuilder.create();
        d.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expense);
        categoryList = (Spinner) findViewById(R.id.spinnerCategory);
        if(getIntent().getExtras() != null)
        {

            editExpenseList = getIntent().getExtras().getParcelableArrayList(MainActivity.LIST_EDIT);

            setSpinner();


        }
        Button btnSelectExpense = (Button) findViewById(R.id.buttonSelectExpense);
        cal  = (ImageView)findViewById(R.id.imageViewCal);
        fileUpload = (ImageView) findViewById(R.id.imageViewFileUpload);
         btnSave = (Button) findViewById(R.id.btnSave);
        btnFinish = (Button) findViewById(R.id.btnCancel);
        date = (EditText) findViewById(R.id.editTextDate);

        date.setFocusable(false);
        date.setClickable(false);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                Category check = (Category) categoryList.getSelectedItem();


                if (amount.getText().length() == 0 || date.getText().length() == 0
                        || expense.getText().length() == 0 || check == Category.SelectCategory) {

                    Toast required = Toast.makeText(EditExpense.this, "Please make sure to add all the details!", Toast.LENGTH_SHORT);
                    required.show();
                } else {


                    editExpenseList.get(selectedExpense).category = (Category) categoryList.getSelectedItem();
                    editExpenseList.get(selectedExpense).amount = Double.parseDouble(amount.getText().toString());
                    editExpenseList.get(selectedExpense).imgUri = fullPhotoUri == null ? editExpenseList.get(selectedExpense).imgUri: fullPhotoUri;
                    editExpenseList.get(selectedExpense).expenseName = expense.getText().toString();
                    try {

                        editExpenseList.get(selectedExpense).date = dateFormat.parse(date.getText().toString());
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }


                    Intent toSend = new Intent();
                    toSend.putExtra(MainActivity.LIST_EDIT, editExpenseList);
                    setResult(RESULT_OK, toSend);
                    finish();


                }
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

        btnSelectExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MakeAlert();
            }
        });

    }
}
