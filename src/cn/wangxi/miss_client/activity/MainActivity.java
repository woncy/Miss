package cn.wangxi.miss_client.activity;

import android.app.Activity;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.TimeUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import cn.wangxi.miss_client.R;
import cn.wangxi.miss_client.config.Config;
import cn.wangxi.miss_client.services.MyService;
import android.os.Build;

public class MainActivity extends Activity {
	private String msg = Config.msg;

	
	/**
	 * 活动被创建时
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.d(msg, "启动页活动被创建");
	}
	
	
	/**
	 * 活动可见时
	 */
	@Override
	protected void onStart() {
		Log.d(msg, "启动页被获取焦点");
		super.onStart();
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			
			@Override
			public void run() {
				startLogin();
			}
		};
		
		timer.schedule(task, 3000);
	
	}
	
	private void startLogin(){
		
		Log.d(msg, "开启登录活动");
		try{
			Intent login = new Intent(MainActivity.this, LoginActivity.class);
			startActivity(login);
		}catch (Exception e) {
			Log.e(msg,e.getMessage());
		}
	}
	
	/**
	 * 活动失去焦点时
	 */
	@Override
	protected void onPause() {
		Log.d(msg, "启动页失去焦点");
		super.onPause();
	}
	
	/**
	 * 活动不可见时
	 */
	@Override
	protected void onStop() {
		Log.d(msg, "启动页不可见");
		super.onStop();
	}
	
	/**
	 * 活动被终止时
	 */
	@Override
	protected void onDestroy() {
		Log.d(msg, "启动页被销毁");
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.login_main, container, false);
			return rootView;
		}
	}

}
