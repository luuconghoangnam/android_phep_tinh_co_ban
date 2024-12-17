package com.example.bai2;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView tvTitle, tvKetQua;
    EditText edtSoA, edtSoB;
    Button btnTinhToan;
    ListView lvCongViec;
    ArrayList<String> listCV;
    ArrayAdapter<String> adapterCV;
    Button btnThoat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        lvCongViec = findViewById(R.id.lvCongViec);
        btnThoat = findViewById(R.id.btnThoat);

        // Initialize listCV
        listCV = new ArrayList<>();
        listCV.add("Bài tập 1");
        listCV.add("Bài tập 2");
        listCV.add("Bài tập 3");

        adapterCV = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, listCV);
        lvCongViec.setAdapter(adapterCV);

        tvTitle = findViewById(R.id.tvTitle);
        tvKetQua = findViewById(R.id.tvKetQua);
        edtSoA = findViewById(R.id.edtSoA);
        edtSoB = findViewById(R.id.edtSoB);
        btnTinhToan = findViewById(R.id.btnTinhToan);

        btnTinhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double so_a = 0, so_b = 0;
                String ketqua = "";
                try {
                    so_a = Double.parseDouble(edtSoA.getText().toString());
                    so_b = Double.parseDouble(edtSoB.getText().toString());

                    double tong = so_a + so_b;
                    double hieu = so_a - so_b;
                    double tich = so_a * so_b;
                    double thuong = 0;

                    // Kiểm tra để tránh chia cho 0
                    if (so_b != 0) {
                        thuong = so_a / so_b;
                    } else {
                        thuong = Double.NaN; // hoặc một giá trị phù hợp khác
                    }

                    ketqua = so_a + " + " + so_b + " = " + tong + "\n";
                    ketqua += so_a + " - " + so_b + " = " + hieu + "\n";
                    ketqua += so_a + " * " + so_b + " = " + tich + "\n";
                    ketqua += so_a + " / " + so_b + " = " + (so_b != 0 ? thuong : "Không thể chia cho 0");
                    tvKetQua.setText(ketqua);
                    tvKetQua.setTextColor(Color.parseColor("#09EA2E")); // Reset màu nếu kết quả hợp lệ
                } catch (Exception e) {
                    e.printStackTrace();
                    so_a = so_b = 0;
                    tvKetQua.setText("Giá trị của a và b không hợp lệ! Vui lòng nhập lại.");
                    tvKetQua.setTextColor(Color.parseColor("#ebae07"));
                }
            }
        });

        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogThoat = new AlertDialog.Builder(MainActivity.this);
                dialogThoat.setTitle("Thoát ứng dụng");
                dialogThoat.setMessage("Bạn có chắc chắn muốn thoát không?");
                dialogThoat.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                dialogThoat.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialogThoat.create().show();
            }
        });

        lvCongViec.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "Bạn vừa chọn " + listCV.get(position) + "'", Toast.LENGTH_SHORT).show();
            }
        });

        lvCongViec.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String xoaCV = listCV.get(position);
                AlertDialog.Builder dialogXoa = new AlertDialog.Builder(MainActivity.this);
                dialogXoa.setTitle("Xóa phần tử");
                dialogXoa.setMessage("Bạn có chắc chắn muốn xóa công việc " + xoaCV + " không?");
                dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        listCV.remove(position);
                        adapterCV.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this, "Đã xóa '" + xoaCV + "'", Toast.LENGTH_SHORT).show();
                    }
                });
                dialogXoa.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialogXoa.create().show();
                return true; // Trả về true để xử lý sự kiện long click
            }
        });
    }
}
