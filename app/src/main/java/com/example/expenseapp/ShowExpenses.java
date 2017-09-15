package com.example.expenseapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ShowExpenses extends AppCompatActivity implements View.OnClickListener {
    ArrayList<Expense> editExpenseList;
    TextView expenseName, amount, category, date;
    ImageView receipt;
    int currntView,curIndex;
    ImageView left,right,first,last;
    Button btnFinish;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_expenses);


        left  = (ImageView) findViewById(R.id.imageViewLeft);
        right  = (ImageView) findViewById(R.id.imageViewRight);
        first  = (ImageView) findViewById(R.id.imageViewFirst);
        last  = (ImageView) findViewById(R.id.imageViewLast);
        btnFinish  = (Button) findViewById(R.id.buttonCancel);

        if(getIntent().getExtras() != null)
        {

            editExpenseList = getIntent().getExtras().getParcelableArrayList(MainActivity.LIST_SHOW);


            left.setOnClickListener(this);
            right.setOnClickListener(this);
            first.setOnClickListener(this);
            last.setOnClickListener(this);
            btnFinish.setOnClickListener(this);

            SetFirstItem(0);
            SetButtons();


        }
    }


    private void SetButtons()
    {
        first.setEnabled(true);
        last.setEnabled(true);

        left.setEnabled(true);
        right.setEnabled(true);

        if(currntView == 1)
        {
            first.setEnabled(false);
            left.setEnabled(false);
        }

        if(currntView == editExpenseList.size())
        {
            last.setEnabled(false);
            right.setEnabled(false);
        }
    }

    private void SetFirstItem(int index)
    {
         expenseName = (TextView)findViewById(R.id.textViewExpenseName);
         amount = (TextView)findViewById(R.id.textViewAmount);
         category = (TextView)findViewById(R.id.textViewCategory);
         date = (TextView)findViewById(R.id.textViewDate);
        receipt = (ImageView) findViewById(R.id.imageViewReceipt);

        SimpleDateFormat properFormat = new SimpleDateFormat("M/d/yyyy");
        String finalDate = properFormat.format(editExpenseList.get(index).date);
        expenseName.setText(editExpenseList.get(index).expenseName);
        date.setText(finalDate);
        amount.setText(" $ " + String.valueOf(editExpenseList.get(index).amount));
        category.setText(editExpenseList.get(index).getCategory().toString());
        receipt.setImageURI(editExpenseList.get(index).getImgUri());
      //  receipt.setMinimumHeight(80);

        currntView = index + 1;
        curIndex = index;
    }



    @Override
    public void onClick(View v) {
        switch (v.getId())
        {

            case R.id.imageViewLeft:
                SetFirstItem(curIndex - 1);
                break;
            case R.id.imageViewRight:
                SetFirstItem(curIndex + 1);
                break;
            case R.id.imageViewFirst:
                SetFirstItem(0);
                break;
            case R.id.imageViewLast:
                SetFirstItem(editExpenseList.size()-1);
                break;
            case R.id.buttonCancel:
                setResult(RESULT_OK);
                finish();
                break;
        }
        SetButtons();
    }
}
