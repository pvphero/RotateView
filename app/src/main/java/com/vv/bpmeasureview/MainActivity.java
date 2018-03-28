package com.vv.bpmeasureview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.vv.bpmeasureview.view.MeasureDegreeView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private MeasureDegreeView degreeView;
    private EditText etDegree;
    private Button btnAdd,btnReduction;
    private int mDegree;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        bindData();

    }

    private void bindData() {

    }

    private void initView() {
        degreeView=findViewById(R.id.degreeview);
        etDegree=findViewById(R.id.et_degree);
        btnAdd=findViewById(R.id.btn_add);
        btnReduction=findViewById(R.id.btn_reduction);
        btnAdd.setOnClickListener(this);
        btnReduction.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add:
                mDegree=mDegree+1;
                refreshView(mDegree);
                break;
            case R.id.btn_reduction:
                mDegree=mDegree-1;
                refreshView(mDegree);
                break;
        }
    }

    private void refreshView(int mDegree) {
        etDegree.setText(mDegree+"");
        degreeView.getAngle(mDegree);
    }
}
