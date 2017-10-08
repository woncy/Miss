package cn.wangxi.miss_client.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import cn.wangxi.miss_client.R;


public class LoginActivity extends Activity{
	private Button submit;
	private TextView username;
	private TextView password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_main);
		final String username_tip = "请输入账号";
		final String password_tip = "请输入密码";

		this.submit = (Button) findViewById(R.id.btn_login);
		this.username = (TextView) findViewById(R.id.tex_username);
		this.password = (TextView) findViewById(R.id.tex_password);
		this.submit = (Button) findViewById(R.id.btn_login);
		
//		username.setText(username_tip+"hahah");
		submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String name = username.getText().toString();
				String pass = password.getText().toString();
				
			}
		});
		
	}
	
	
	

}
