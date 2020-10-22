package com.sojay.testfunction.code;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.sojay.testfunction.R;

public class CodeActivity extends AppCompatActivity {

    private ImageView ivCode;
    private EditText etCode;
    private Button btnCheck;

    private Bitmap bitmap;
    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);

        initView();

        initCode();

    }

    private void initView() {
        ivCode = findViewById(R.id.iv_code);
        etCode = findViewById(R.id.et_input);
        btnCheck = findViewById(R.id.btn_check);
        // 校验不区分大小写，所以 验证码 和 输入框内容 转小写后比较
        btnCheck.setOnClickListener(v -> Toast.makeText(CodeActivity.this, code.toLowerCase().equals(etCode.getText().toString().toLowerCase()) ? "正确" : "错误", Toast.LENGTH_SHORT).show());
    }

    /**
     * 生成图片验证码
     */
    private void initCode() {
        // 获取工具类生成的图片验证码对象
        bitmap = CodeUtils.getInstance().createBitmap();
        // 获取当前图片验证码的对应内容用于校验
        code = CodeUtils.getInstance().getCode();

        ivCode.setImageBitmap(bitmap);

        ivCode.setOnClickListener(view -> {
            // 点击验证码重新生成
            bitmap = CodeUtils.getInstance().createBitmap();
            code = CodeUtils.getInstance().getCode();
            ivCode.setImageBitmap(bitmap);
        });
    }
}