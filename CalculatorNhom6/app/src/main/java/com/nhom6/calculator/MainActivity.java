package com.huyhoang.projectnhom_calculator;

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
    Button btnDel;
    boolean bflag=false;//cờ cho biết đã thực hiện 1 phép tính trước đó.
    boolean bflagLongDel=false;
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
        btnDel=(Button)findViewById(R.id.btnDelete);
        int[] idbutton={R.id.btn0,R.id.btn1,R.id.btn2,R.id.btn3,R.id.btn4,R.id.btn5,R.id.btn6,R.id.btn7,
                R.id.btn8,R.id.btn9,R.id.btnCham,R.id.btnChia,R.id.btnCong,R.id.btnTru,R.id.btnNhan,R.id.btnBang,R.id.btnDelete};
        for(int id:idbutton) {
            View v=(View)findViewById(id);
            v.setOnClickListener(this);
        }
        //sự kiện nhấn giữ nút xóa
        btnDel.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                bflagLongDel=true;
                stemp="";
                sphepTinh="";
                tvhienThi.setText("");
                schuoiHienThi="";
                tvketQua.setText("0");
                ArrphepTinh.clear();
                return false;
            }
        });
    }
    DecimalFormat df= new DecimalFormat("###.#####");//định dạng lại số double

    //ToDo:xu li khi click vao cac button

    public boolean kiemtraToanTu(String s, String s2){
        if((s.equals(".")||s.equals("+")||s.equals("-")||s.equals("*")||s.equals("/"))
                &&(s2.equals(".")||s2.equals("+")||s2.equals("-")||s2.equals("*")||s2.equals("/"))){
            return true;
        }
        return false;
    }
    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.btnDelete:
                    if(bflagLongDel==true){
                        bflagLongDel=false;
                        break;
                    }
                    stemp="";
                    sphepTinh="";
                    if(ArrphepTinh.size()>1) {
                        //nếu 2 phần tử liên tiếp đều là toán tử thì ta xóa cả 2
                        if (kiemtraToanTu(ArrphepTinh.get(ArrphepTinh.size() - 1), ArrphepTinh.get(ArrphepTinh.size() - 2)) == true) {
                            ArrphepTinh.remove(ArrphepTinh.size() - 1);
                            ArrphepTinh.remove(ArrphepTinh.size() - 1);
                        }
                        //ngược lại chỉ có 1 toán tử hoặc 1 tham số
                        else {
                            ArrphepTinh.remove(ArrphepTinh.size() - 1);
                        }
                    }
                    else {//nếu có 1 số thì đó chính là kq ta chỉ việc xóa nó đi
                        ArrphepTinh.remove(ArrphepTinh.size() - 1);
                    }
                    //lấy chuỗi hiển thị từ mảng để đưa lên màn hình
                    for(int i=0;i<ArrphepTinh.size();i++){
                        schuoiHienThi=schuoiHienThi+ArrphepTinh.get(i).toString();
                    }
                    tvhienThi.setText(schuoiHienThi);
                    if(schuoiHienThi.equals("")){
                        tvketQua.setText("0");
                    }
                    schuoiHienThi="";
                    break;
                case R.id.btnBang:
                    //mới vô không cho nhập dấu bằng
                    if(ArrphepTinh.size()==0){
                        break;
                    }
                    bflag=true;
                    double kq=0;
                    int dem=ArrphepTinh.size();
                    while(dem!=1){
                        if(dem>3){
                            if(ArrphepTinh.get(3).equals("*")||ArrphepTinh.get(3).equals("/")){
                                if (ArrphepTinh.get(3).equals("*")) {

                                    if(ArrphepTinh.size()<5){//tức size =4. VD: 1+3*
                                        kq=Double.parseDouble(ArrphepTinh.get(2));
                                    }
                                    else {
                                        //kiểm tra sau phép tính có phải là số hay không
                                        //nếu không. VD: 1+3**
                                        if (ArrphepTinh.get(4).equals("*")) {
                                            kq = Double.parseDouble(ArrphepTinh.get(2));//nếu không phải thì cho kq là tham số thứ nhất
                                        } else {
                                            //ngược lại thì ta tính toán bình thường, VD:1+3*4
                                            kq = Double.parseDouble(ArrphepTinh.get(2)) * Double.parseDouble(ArrphepTinh.get(4));
                                        }
                                    }
                                }
                                if(ArrphepTinh.get(3).equals("/")){
                                    if(ArrphepTinh.size()<5){
                                        kq=Double.parseDouble(ArrphepTinh.get(2));
                                    }
                                    else {
                                        if (ArrphepTinh.get(4).equals("0")) {
                                            tvhienThi.setText("Không thể chia cho 0");
                                            kq = 0;
                                            ArrphepTinh.clear();
                                            break;
                                        }
                                        if (ArrphepTinh.get(4).equals("/")) {
                                            kq = Double.parseDouble(ArrphepTinh.get(2));
                                        } else {
                                            kq = Double.parseDouble(ArrphepTinh.get(2)) / Double.parseDouble(ArrphepTinh.get(4));
                                        }
                                    }
                                }
                                if(ArrphepTinh.size()==4){
                                    for(int i=0;i<2;){
                                        ArrphepTinh.remove(2);
                                        i++;
                                    }
                                }
                                else {
                                    for (int i = 0; i < 3; ) {
                                        ArrphepTinh.remove(2);
                                        i++;
                                    }
                                }
                                ArrphepTinh.add(2,Double.toString(kq));
                                dem=ArrphepTinh.size();
                            }
                            else {
                                if(ArrphepTinh.get(1).equals("+")){
                                    kq=Double.parseDouble(ArrphepTinh.get(0))+ Double.parseDouble(ArrphepTinh.get(2));
                                }
                                if(ArrphepTinh.get(1).equals("-")){
                                    kq=Double.parseDouble(ArrphepTinh.get(0))- Double.parseDouble(ArrphepTinh.get(2));

                                }
                                if(ArrphepTinh.get(1).equals("*")){
                                    kq=Double.parseDouble(ArrphepTinh.get(0))* Double.parseDouble(ArrphepTinh.get(2));
                                }
                                if(ArrphepTinh.get(1).equals("/")){
                                    if(ArrphepTinh.get(2).equals("0")){
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
                            if(ArrphepTinh.get(1).equals("+")){
                                //nếu không có tham số thứ 2 thì cho kết quả bằng chính tham số thứ nhất
                                //VD: 1+
                                if(ArrphepTinh.get(dem-1).equals("+")) {
                                    kq=Double.parseDouble(ArrphepTinh.get(0));
                                }
                                //Ngược lại tính toán bình thường
                                else {
                                    kq=Double.parseDouble(ArrphepTinh.get(0))+ Double.parseDouble(ArrphepTinh.get(2));
                                }
                            }
                            if(ArrphepTinh.get(1).equals("-")){
                                if(ArrphepTinh.get(dem-1).equals("-")) {
                                    kq=Double.parseDouble(ArrphepTinh.get(0));
                                }
                                else {
                                    kq=Double.parseDouble(ArrphepTinh.get(0))- Double.parseDouble(ArrphepTinh.get(2));
                                }
                            }
                            if(ArrphepTinh.get(1).equals("*")){
                                if(ArrphepTinh.get(dem-1).equals("*")) {
                                    kq=Double.parseDouble(ArrphepTinh.get(0));
                                }
                                else {
                                    kq=Double.parseDouble(ArrphepTinh.get(0))* Double.parseDouble(ArrphepTinh.get(2));
                                }
                            }
                            if(ArrphepTinh.get(1).equals("/")){
                                if(ArrphepTinh.get(2).equals("0")){
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
                        if(stemp.equals(".")||stemp.equals("+")||stemp.equals("-")||stemp.equals("*")||stemp.equals("/")){
                            break;
                        }
                    }
                    //khong phai la toan tu(tuc la so)
                    if(!stemp.equals("+")&&!stemp.equals("-")&&!stemp.equals("*")&&!stemp.equals("/")){
                        if(bflag==true){
                            tvhienThi.setText("");
                            ArrphepTinh.clear();
                            bflag=false;
                            sphepTinh="";
                        }

                        sphepTinh=sphepTinh+stemp;
                        if((ArrphepTinh.size()%2!=0)){
                            ArrphepTinh.remove(ArrphepTinh.size()-1);
                            ArrphepTinh.add(sphepTinh);
                        }
                        else{
                            ArrphepTinh.add(sphepTinh);
                        }
                    }
                    //la toan tu
                    else {
                        if(bflag==true){
                            Double dKQ=Double.parseDouble(ArrphepTinh.get(0));
                            tvhienThi.setText(df.format(dKQ));
                            bflag=false;
                        }
                        if(ArrphepTinh.get(ArrphepTinh.size()-1).equals("+")||ArrphepTinh.get(ArrphepTinh.size()-1).equals("-")||
                                ArrphepTinh.get(ArrphepTinh.size()-1).equals("*")||ArrphepTinh.get(ArrphepTinh.size()-1).equals("/")){
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
