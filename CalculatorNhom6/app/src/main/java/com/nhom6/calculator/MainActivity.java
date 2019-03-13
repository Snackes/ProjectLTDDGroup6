package com.nhom6.calculator;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.FloatProperty;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends Activity implements View.OnClickListener {
    boolean bflag1=false;//=true nếu phép tính mới được thực hiện
    Float mthamSoThuNhat, mthamSoThuHai;
    String stoanTu, sketQua="";
    String shienThi="";//chuỗi phép tính trên textview
    EditText etGiaTri;
    TextView tvhienThi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etGiaTri=(EditText) findViewById(R.id.edtKetQua);
        tvhienThi=(TextView) findViewById(R.id.tPhepTinh);
        int[] idbutton={R.id.btn0,R.id.btn1,R.id.btn2,R.id.btn3,R.id.btn4,R.id.btn5,R.id.btn6,R.id.btn7,
                R.id.btn8,R.id.btn9,R.id.btnCham,R.id.btnChia,R.id.btnCong,R.id.btnTru,R.id.btnNhan,R.id.btnBang,R.id.btnDelete};

        for(int id:idbutton) {
            View v=(View)findViewById(id);
            v.setOnClickListener(this);
        }
    }
    DecimalFormat df= new DecimalFormat("###.#####");
    //TODO: Lấy giá trị cho tham số đầu
    private void ToanTu(){
        if(bflag1==true&& mthamSoThuHai==0.0f){
            bflag1=false;
        }
        mthamSoThuNhat= Float.parseFloat(sketQua.toString());//lấy giá trị của tham số đầu
        shienThi=df.format(mthamSoThuNhat)+""+stoanTu;
        etGiaTri.setText("0");
        tvhienThi.setText(shienThi);
        sketQua="0";
    }


    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.btnDelete:
                    mthamSoThuNhat = 0.0f;
                    mthamSoThuHai = 0.0f;
                    stoanTu = "";
                    tvhienThi.setText("");
                    etGiaTri.setText("0");
                    sketQua = "0";
                    break;
                case R.id.btnCong:
                    stoanTu = "+";
                    ToanTu();
                    break;
                case R.id.btnTru:
                    stoanTu = "-";
                    ToanTu();
                    break;
                case R.id.btnNhan:
                    stoanTu = "*";
                    ToanTu();
                    break;
                case R.id.btnChia:
                    stoanTu = "/";
                    ToanTu();
                    break;
                case R.id.btnBang:
                    bflag1 = true;
                    Float ketQua = null;
                    mthamSoThuHai = Float.parseFloat(etGiaTri.getText().toString());
                    if (stoanTu == "+") {
                        ketQua = mthamSoThuNhat + mthamSoThuHai;
                    }
                    if (stoanTu == "-") {
                        ketQua = mthamSoThuNhat - mthamSoThuHai;
                    }
                    if (stoanTu == "*") {
                        ketQua = mthamSoThuNhat * mthamSoThuHai;
                    }
                    if (stoanTu == "/") {
                        ketQua = mthamSoThuNhat / mthamSoThuHai;
                    }
                    etGiaTri.setText(String.valueOf(df.format(ketQua)));
                    mthamSoThuHai = 0.0f;
                    mthamSoThuNhat = 0.0f;
                    sketQua = ketQua + "";
                    Log.d("ketQua", "đáp số" + ketQua);
                    break;
                default:
                    if (bflag1 == true) {//nếu thực hiện phép tính mới thì set hiển thị phép tính lại từ đầu
                        mthamSoThuHai = 0.0f;
                        mthamSoThuNhat = 0.0f;
                        shienThi = "";
                        tvhienThi.setText(shienThi);
                        sketQua = "";
                        etGiaTri.setText(sketQua);
                        bflag1 = false;
                    }
                    if (sketQua.equals("0")) {
                        sketQua = "";
                    }

                    shienThi += ((Button) v).getText().toString();
                    tvhienThi.setText(shienThi);

                    sketQua += ((Button) v).getText().toString();
                    etGiaTri.setText(sketQua);
            }
        }
        catch (Exception e){
            System.out.println("có lỗi xảy ra...!");
        };
    }
}
