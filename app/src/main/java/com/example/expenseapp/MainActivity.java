package com.example.expenseapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import java.text.BreakIterator;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<Expense> Expense_List = new ArrayList<Expense>();
    final static String LIST_ADD = "AddExpenseList";
    final static int LIST_ADD_CODE = 100;

    final static String LIST_EDIT = "EditExpenseList";
    final static int LIST_EDIT_CODE = 101;

    final static String LIST_DELETE = "DeleteExpenseList";
    final static int LIST_DELETE_CODE = 102;

    final static String LIST_SHOW = "ShowExpenseList";
    final static int LIST_SHOW_CODE = 103;

    Button addExpense,editExpense,deleteExpense,showExpense,btnFinish;

    private void SetButtons()
    {
        if(Expense_List.size()==0)
        {
            editExpense.setEnabled(false);
            deleteExpense.setEnabled(false);
            showExpense.setEnabled(false);
        }
        else
        {
            editExpense.setEnabled(true);
            deleteExpense.setEnabled(true);
            showExpense.setEnabled(true);
        }
    }

    private void SetTheAppIcon()
    {
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SetTheAppIcon();
        addExpense = (Button) findViewById(R.id.buttonAddExpense);
        editExpense = (Button)findViewById(R.id.buttonEditExpense);
        deleteExpense = (Button)findViewById(R.id.buttonDeleteExpense);
        showExpense = (Button)findViewById(R.id.buttonShowExpence);
        btnFinish = (Button) findViewById(R.id.buttonFinish);


        addExpense.setOnClickListener(this);
        editExpense.setOnClickListener(this);
        deleteExpense.setOnClickListener(this);
        showExpense.setOnClickListener(this);
        btnFinish.setOnClickListener(this);

        SetButtons();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(data != null)
        {
        switch (requestCode)
        {

            case LIST_ADD_CODE:
                Expense_List = data.getExtras().getParcelableArrayList(LIST_ADD);


                break;
            case LIST_EDIT_CODE:

                Expense_List = data.getExtras().getParcelableArrayList(LIST_EDIT);
                break;
            case LIST_DELETE_CODE:

                Expense_List = data.getExtras().getParcelableArrayList(LIST_DELETE);
                break;



        }}
        SetButtons();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.buttonAddExpense:
                Intent addExpense = new Intent(MainActivity.this, AddExpense.class);
                addExpense.putExtra(LIST_ADD,Expense_List);
                startActivityForResult(addExpense, LIST_ADD_CODE);
                break;
            case R.id.buttonEditExpense:
                Intent editExpense = new Intent(MainActivity.this, EditExpense.class);
                editExpense.putExtra(LIST_EDIT,Expense_List);
                startActivityForResult(editExpense,LIST_EDIT_CODE);
                break;
            case R.id.buttonDeleteExpense:
                Intent deleteExpense = new Intent(MainActivity.this, DeleteExpense.class);
                deleteExpense.putExtra(LIST_DELETE,Expense_List);
                startActivityForResult(deleteExpense,LIST_DELETE_CODE);
                break;

            case R.id.buttonShowExpence:
                Intent showExpense = new Intent(MainActivity.this, ShowExpenses.class);
                showExpense.putExtra(LIST_SHOW,Expense_List);
                startActivityForResult(showExpense,LIST_SHOW_CODE);
                break;

            case R.id.buttonFinish:
                finish();
                break;
        }


    }
}
