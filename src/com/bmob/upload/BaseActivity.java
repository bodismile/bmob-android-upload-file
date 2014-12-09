package com.bmob.upload;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;
import cn.bmob.v3.Bmob;

/**
 * 基类
 * @ClassName: BaseActivity
 * @Description: TODO
 * @author smile
 * @date 2014-5-20 上午9:55:34
 */
public abstract class BaseActivity extends Activity {

	public static String APPID = "";
	public static final String TAG = "bmob";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        // 使用时请将第二个参数Application ID替换成你在Bmob服务器端创建的Application ID
		// 初始化 Bmob SDK
		Bmob.initialize(this, APPID);
		setContentView();
		initViews();
		initListeners();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	/**
	 * 设置布局文件
	 */
	public abstract void setContentView();

	/**
	 * 初始化布局文件中的控件
	 */
	public abstract void initViews();

	/**
	 * 初始化控件的监听
	 */
	public abstract void initListeners();
	
	Toast mToast;

	public void ShowToast(String text) {
		if (!TextUtils.isEmpty(text)) {
			if (mToast == null) {
				mToast = Toast.makeText(getApplicationContext(), text,
						Toast.LENGTH_SHORT);
			} else {
				mToast.setText(text);
			}
			mToast.show();
		}
	}
	
	public void ShowToast(int resId) {
		if (mToast == null) {
			mToast = Toast.makeText(getApplicationContext(), resId,
					Toast.LENGTH_SHORT);
		} else {
			mToast.setText(resId);
		}
		mToast.show();
	}
	
}
