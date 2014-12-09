package com.bmob.upload;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;
import cn.bmob.v3.listener.UploadFileListener;

/** �ļ��ϴ�+������������
  * @ClassName: MainActivity
  * @Description: TODO
  * @author smile
  * @date 2014-5-22 ����7:58:58
  * ע�����µĲ����ļ�·�������������ã��������������У��������滻��sd���ڲ��ļ�
  */

@SuppressLint({ "SdCardPath", "UseSparseArrays" })
public class MainActivity extends BaseActivity implements OnClickListener{

	Button tv_one_one;
	Button tv_one_many;
	Button tv_many_one;
	Button tv_many_many;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	@Override
	public void setContentView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_main);
	}

	@Override
	public void initViews() {
		// TODO Auto-generated method stub
		tv_one_one = (Button) findViewById(R.id.tv_one_one);
		tv_one_many = (Button) findViewById(R.id.tv_one_many);
		tv_many_one = (Button) findViewById(R.id.tv_many_one);
		tv_many_many = (Button) findViewById(R.id.tv_many_many);
	}

	@Override
	public void initListeners() {
		// TODO Auto-generated method stub
		tv_one_one.setOnClickListener(this);
		tv_one_many.setOnClickListener(this);
		tv_many_one.setOnClickListener(this);
		tv_many_many.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tv_one_one://���뵥�����ݣ�һ��BmobFile�У�
			insertDataWithOne();
			break;
		case R.id.tv_many_one://���������������-��ÿ�����ݶ�����һ��BmobFile��
			insertBatchDatasWithOne();
			break;
		case R.id.tv_one_many://���뵥�����ݣ����BmobFile�У�
			insertDataWithMany();
			break;
		case R.id.tv_many_many://���������������-��ÿ�����ݶ����ڶ��BmobFile��
			insertBatchDatasWithMany();
			break;
		}
	}
	
	//======================����BmobFile��=======================================
	/**
	 * ע�����µĲ����ļ�·�������������ã��������������У��������滻��sd���ڲ��ļ�·��
	 */
	String filePath_mp3 = "/mnt/sdcard/testbmob/test1.png";
	String filePath_lrc = "/mnt/sdcard/testbmob/test2.png";
	
	List<BmobObject> movies = new ArrayList<BmobObject>();
	
	
	/** ���뵥�����ݣ�����BmobFile�У�
	  * ���磺���뵥����Ӱ
	  * @return void
	  * @throws
	  */
	private void insertDataWithOne(){
		File mp3 = new File(filePath_mp3);
		uploadMovoieFile(mp3);
	}
	
	/**
	  * �˷�����������������������ÿ������ֻ��һ��BmobFile�ֶ�
	  * ���磺�����ϴ���ӰMovies
	  * @Title: insertBatchDatasWithOne
	  * @throws
	  */
	public void insertBatchDatasWithOne(){
		String[] filePaths = new String[2];
		filePaths[0] = filePath_mp3;
		filePaths[1] = filePath_lrc;
		//�����ϴ��ǻ������ϴ��ļ���������ļ�
		Bmob.uploadBatch(this, filePaths, new UploadBatchListener() {
			
			@Override
			public void onSuccess(List<BmobFile> files,List<String> urls) {
				// TODO Auto-generated method stub
				Log.i("life","insertBatchDatasWithOne -onSuccess :"+urls.size()+"-----"+files+"----"+urls);
				if(urls.size()==1){//�����һ���ļ��ϴ����
					Movie movie =new Movie("��������1",files.get(0));
					movies.add(movie);
				}else if(urls.size()==2){//�ڶ����ļ��ϴ��ɹ�
					Movie movie1 =new Movie("��������2",files.get(1));
					movies.add(movie1);
					insertBatch(movies);
				}
			}
			
			@Override
			public void onError(int statuscode, String errormsg) {
				// TODO Auto-generated method stub
				ShowToast("������"+statuscode +",����������"+errormsg);
			}

			@Override
			public void onProgress(int curIndex, int curPercent, int total,
					int totalPercent) {
				// TODO Auto-generated method stub
				Log.i("life","insertBatchDatasWithOne -onProgress :"+curIndex+"---"+curPercent+"---"+total+"----"+totalPercent);
			}
		});
		
	
	}
	

	/** ��������
	  * insertObject
	  * @return void
	  * @throws
	  */
	private void insertObject(final BmobObject obj){
		obj.save(MainActivity.this, new SaveListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				ShowToast("-->�������ݳɹ���" + obj.getObjectId());
				
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				ShowToast("-->��������ʧ�ܣ�" + arg0+",msg = "+arg1);
			}
		});
	}
	
	/** �ϴ�ָ��·���µĵ�Ӱ�ļ�
	  * @Title: uploadMovoieFile
	  * @Description: TODO
	  * @param @param type
	  * @param @param i
	  * @param @param file 
	  * @return void
	  * @throws
	  */
	private void uploadMovoieFile(File file) {
		final BmobFile bmobFile = new BmobFile(file);
		bmobFile.uploadblock(this, new UploadFileListener() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				Log.i(TAG, "��Ӱ�ļ��ϴ��ɹ������ص�����--"+bmobFile.getFileUrl(MainActivity.this));
				insertObject(new Movie("���⣺����֮��",bmobFile));
				
			}

			@Override
			public void onProgress(Integer arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				ShowToast("-->uploadMovoieFile-->onFailure:" + arg0+",msg = "+arg1);
			}

		});

	}
	
	//======================���BmobFile��=======================================
	
	List<BmobObject> songs = new ArrayList<BmobObject>();
	
	/**
	 * ���뵥�����ݣ����BmobFile��--������Ϊ����
	 * �����ϴ�MP3�ļ��͸��lrc�ļ���һ��Song������
	 */
	private void insertDataWithMany() {
		String[] filePaths = new String[2];
		filePaths[0] = filePath_mp3;
		filePaths[1] = filePath_lrc;
		Bmob.uploadBatch(this, filePaths, new UploadBatchListener() {
			
			@Override
			public void onSuccess(List<BmobFile> files,List<String> urls) {
				// TODO Auto-generated method stub
				Log.i("life","insertDataWithMany -onSuccess :"+urls.size()+"-----"+files+"----"+urls);
				if(urls.size()==2){//���ȫ���ϴ��꣬����¸�����¼
					Song song =new Song("����0","��������0",files.get(0),files.get(1));
					insertObject(song);
				}else{
					//�п����ϴ����������м���ܻ����δ�ϴ��ɹ����������������д���
				}
			}
			
			@Override
			public void onError(int statuscode, String errormsg) {
				// TODO Auto-generated method stub
				ShowToast("������"+statuscode +",����������"+errormsg);
			}

			@Override
			public void onProgress(int curIndex, int curPercent, int total,
					int totalPercent) {
				// TODO Auto-generated method stub
				Log.i("life","insertBatchDatasWithOne -onProgress :"+curIndex+"---"+curPercent+"---"+total+"----"+totalPercent);
			}
		});
		
	}

	/**
	  * �˷�����������������������ÿ�����ݶ��ж��BmobFile�ֶΣ�
	  * ���磺�����ϴ�����songs
	  * @Title: insertBatchDatasWithOne
	  * @throws
	  */
	private void insertBatchDatasWithMany() {
		File ff = new File("/mnt/sdcard/testbmob/");
		File[] fs = ff.listFiles();
		String[] filePaths = new String[fs.length];
		if(fs!=null && fs.length>0){
			final int len = fs.length;
			for(int i=0;i<len;i++){
				filePaths[i] = fs[i].getAbsolutePath();
			}
			Bmob.uploadBatch(this, filePaths, new UploadBatchListener() {
				
				@Override
				public void onSuccess(List<BmobFile> files,List<String> urls) {
					// TODO Auto-generated method stub
					Log.i("life","insertBatchDatasWithMany -onSuccess :"+urls.size()+"-----"+files+"----"+urls);
					if(urls.size()==len){//���ȫ���ϴ��꣬����������
						Log.i("life","====insertBatch=====");
						//��Ϊ�ҵ��ļ�������������ͼƬÿ�������뵽һ��������
						Song song =new Song("����0","��������0",files.get(0),files.get(1));
						songs.add(song);
						Song song1 =new Song("����1","��������1",files.get(2),files.get(3));
						songs.add(song1);
						//�����������
						insertBatch(songs);
					}else{
						//�п����ϴ����������м���ܻ����δ�ϴ��ɹ����������������д���
					}
				}
				
				@Override
				public void onError(int statuscode, String errormsg) {
					// TODO Auto-generated method stub
					ShowToast("������"+statuscode +",����������"+errormsg);
				}

				@Override
				public void onProgress(int curIndex, int curPercent, int total,
						int totalPercent) {
					// TODO Auto-generated method stub
					Log.i("life","insertBatchDatasWithOne -onProgress :"+curIndex+"---"+curPercent+"---"+total+"----"+totalPercent);
				}
			});
		}
	}
	
	/** �����������
	  * insertBatch
	  * @return void
	  * @throws
	  */
	public void insertBatch(List<BmobObject> files){
		new BmobObject().insertBatch(MainActivity.this, files, new SaveListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				ShowToast("---->�������³ɹ�");
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				ShowToast("---->��������ʧ��"+arg0);
				
			}
		});
	}
	
}
