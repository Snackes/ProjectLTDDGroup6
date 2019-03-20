package com.nhom6.calculator;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.FloatProperty;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener {
    TextView tvketQua;
    TextView tvhienThi;
    boolean bflag=false;

    ArrayList<String>ArrphepTinh=new ArrayList<>();
    String stemp="";
    String sphepTinh="";
    String schuoiHienThi="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvketQua=(TextView) findViewById(R.id.tvketQua);
        tvhienThi=(TextView) findViewById(R.id.tvphepTinh);
        int[] idbutton={R.id.btn0,R.id.btn1,R.id.btn2,R.id.btn3,R.id.btn4,R.id.btn5,R.id.btn6,R.id.btn7,
                R.id.btn8,R.id.btn9,R.id.btnCham,R.id.btnChia,R.id.btnCong,R.id.btnTru,R.id.btnNhan,R.id.btnBang,R.id.btnDelete};

        for(int id:idbutton) {
            View v=(View)findViewById(id);
            v.setOnClickListener(this);
        }
    }
    DecimalFormat df= new DecimalFormat("###.#####");
    //ToDo:xu li khi click vao cac button
    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.btnDelete:
                    stemp="";
                    sphepTinh="";
                    tvhienThi.setText("");
                    schuoiHienThi="";
                    tvketQua.setText("0");
                    ArrphepTinh.clear();
                    break;
                case R.id.btnBang:
                    bflag=true;
                    double kq=0;
                    int dem=ArrphepTinh.size();
                    while(dem!=1){
                        if(dem>3){
                            if(ArrphepTinh.get(3).contains("*")||ArrphepTinh.get(3).contains("/")){
                                if(ArrphepTinh.get(3).contains("*")){
                                    //kiểm tra sau phép tính có phải là số hay không
                                    if(ArrphepTinh.get(4).contains("*")) {
                                        kq=Double.parseDouble(ArrphepTinh.get(2));//nếu không phải thì cho kq là tham số thứ nhất
                                    }
                                    else {
                                        //ngược lại thì ta tính toán bình thường
                                        kq=Double.parseDouble(ArrphepTinh.get(2))* Double.parseDouble(ArrphepTinh.get(4));
                                    }
                                }
                                if(ArrphepTinh.get(3).contains("/")){
                                    if(ArrphepTinh.get(4).contains("0")){
                                        tvhienThi.setText("Không thể chia cho 0");
                                        kq=0;
                                        ArrphepTinh.clear();
                                        break;
                                    }
                                    if(ArrphepTinh.get(4).contains("/")) {
                                        kq=Double.parseDouble(ArrphepTinh.get(2));
                                    }
                                    else {
                                        kq=Double.parseDouble(ArrphepTinh.get(2))/ Double.parseDouble(ArrphepTinh.get(4));
                                    }
                                }
                                for(int i=0;i<3;){
                                    ArrphepTinh.remove(2);
                                    i++;
                                }
                                ArrphepTinh.add(2,Double.toString(kq));
                                dem=ArrphepTinh.size();
                            }
                            else {
                                if(ArrphepTinh.get(1).contains("+")){
                                    kq=Double.parseDouble(ArrphepTinh.get(0))+ Double.parseDouble(ArrphepTinh.get(2));
                                }
                                if(ArrphepTinh.get(1).contains("-")){
                                    kq=Double.parseDouble(ArrphepTinh.get(0))- Double.parseDouble(ArrphepTinh.get(2));

                                }
                                if(ArrphepTinh.get(1).contains("*")){
                                    kq=Double.parseDouble(ArrphepTinh.get(0))* Double.parseDouble(ArrphepTinh.get(2));
                                }
                                if(ArrphepTinh.get(1).contains("/")){
                                    if(ArrphepTinh.get(2).contains("0")){
                                        tvhienThi.setText("Không thể chia cho 0");
                                        kq=0;
                                        ArrphepTinh.clear();
                                        break;
                                    }
                                    kq=Double.parseDouble(ArrphepTinh.get(0))/ Double.parseDouble(ArrphepTinh.get(2));
                                }
                                for(int i=0;i<3;){
                                    ArrphepTinh.remove(0);
                                    i++;
                                }
                                ArrphepTinh.add(0,Double.toString(kq));
                                dem=ArrphepTinh.size();
                            }
                        }
                        else {
                            if(ArrphepTinh.get(1).contains("+")){
                                if(ArrphepTinh.get(dem-1).equals("+")) {//nếu không có tham số thứ 2 thì cho kết quả bằng chính tham số thứ nhất
                                    kq=Double.parseDouble(ArrphepTinh.get(0));
                                }
                                else {
                                    kq=Double.parseDouble(ArrphepTinh.get(0))+ Double.parseDouble(ArrphepTinh.get(2));
                                }
                            }
                            if(ArrphepTinh.get(1).contains("-")){
                                if(ArrphepTinh.get(dem-1).equals("-")) {
                                    kq=Double.parseDouble(ArrphepTinh.get(0));
                                }
                                else {
                                    kq=Double.parseDouble(ArrphepTinh.get(0))- Double.parseDouble(ArrphepTinh.get(2));
                                }
                            }
                            if(ArrphepTinh.get(1).contains("*")){
                                if(ArrphepTinh.get(dem-1).equals("*")) {
                                    kq=Double.parseDouble(ArrphepTinh.get(0));
                                }
                                else {
                                    kq=Double.parseDouble(ArrphepTinh.get(0))* Double.parseDouble(ArrphepTinh.get(2));
                                }
                            }
                            if(ArrphepTinh.get(1).contains("/")){
                                if(ArrphepTinh.get(2).contains("0")){
                                    tvhienThi.setText("Không thể chia cho 0");
                                    kq=0;
                                    ArrphepTinh.clear();
                                    break;
                                }
                                if(ArrphepTinh.get(dem-1).equals("/")) {
                                    kq=Double.parseDouble(ArrphepTinh.get(0));
                                }
                                else {
                                    kq=Double.parseDouble(ArrphepTinh.get(0))/ Double.parseDouble(ArrphepTinh.get(2));
                                }
                            }

                            for(int i=0;i<dem;){
                                ArrphepTinh.remove(0);
                                i++;
                            }
                            ArrphepTinh.add(0,Double.toString(kq));
                            dem=ArrphepTinh.size();
                        }
                    }
                    tvketQua.setText(df.format(kq));
                    break;
                //mac dinh la khi thao tac tren button them chuoi vao Array de lay cac phep tinh
                default:
                    stemp = ((Button) v).getText().toString();
                    //khong cho nhap toan tu va dau bang dau tien trong cac phep tinh
                    if(ArrphepTinh.size()==0){
                        if(stemp.contains(".")||stemp.contains("+")||stemp.contains("-")||stemp.contains("*")||stemp.contains("/")||stemp.contains("=")){
                            break;
                        }
                    }
                    //khong phai la toan tu(tuc la so)
                    if(!stemp.contains("+")&&!stemp.contains("-")&&!stemp.contains("*")&&!stemp.contains("/")){
                        if(bflag==true){
                            tvhienThi.setText("");
                            ArrphepTinh.clear();
                            bflag=false;
                            sphepTinh="";
                        }
                        sphepTinh=sphepTinh+stemp;
                        if(ArrphepTinh.size()>0){
                            ArrphepTinh.remove(ArrphepTinh.size()-1);
                        }
                        ArrphepTinh.add(sphepTinh);

                    }
                    //la toan tu
                    else {
                        if(bflag==true){
                            Double dKQ=Double.parseDouble(ArrphepTinh.get(0));
                            tvhienThi.setText(df.format(dKQ));
                            bflag=false;
                        }
                        if(ArrphepTinh.get(ArrphepTinh.size()-1).contains("+")||ArrphepTinh.get(ArrphepTinh.size()-1).contains("-")||
                                ArrphepTinh.get(ArrphepTinh.size()-1).contains("*")||ArrphepTinh.get(ArrphepTinh.size()-1).contains("/")){
                            break;//neu bam cung luc 2 phep toan tu thi se khong xu li
                        }
                        ArrphepTinh.add(stemp);
                        ArrphepTinh.add(stemp);
                        sphepTinh="";

                    }
                    //tvhienThi.setText(schuoiHienThi);
                    tvhienThi.setText(tvhienThi.getText().toString()+stemp);
            }
        }
        catch (Exception e){
            System.out.println("có lỗi xảy ra...!");
        };
    }
}