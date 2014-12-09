##Bmob文件上传

###前言：
相对于移动网络和数据服务而言，文件服务往往需要更长的i/o时间，因此也就涉及到更多的异步操作的问题。
不少朋友在用到Bmob文件服务的时候出错，原因就是没有充分理解同步和异步的本质。为方便大家理解Bmob的文件服务，这里提供一个上传文件的案例，
从如何往一个只有一列文件字段的表中插入一条或者多条，到如何往一个有两列甚至多列文件字段的表中插入一条或者多条数据进行详细阐述。

### 创建文件对象

创建文件对象方式如下：
```java
String picPath = "sdcard/temp.jpg";
BmobFile bmobFile = new BmobFile(new File(picPath));
```

### 上传单一文件

文件分片上传的方法非常简单，示例代码如下：

```java
String picPath = "sdcard/temp.jpg";
BmobFile bmobFile = new BmobFile(new File(picPath));
bmobFile.uploadblock(this, new UploadFileListener() {
	
	@Override
	public void onSuccess() {
		// TODO Auto-generated method stub
		toast("上传文件成功:" + bmobFile.getFileUrl());
	}
	
	@Override
	public void onProgress(Integer value) {
		// TODO Auto-generated method stub
		// 返回的上传进度（百分比）
	}
	
	@Override
	public void onFailure(int code, String msg) {
		// TODO Auto-generated method stub
		toast("上传文件失败：" + msg);
	}
});
```
### 批量上传文件

BmobSDK_V3.2.7新增批量上传文件的方法，使用起来也很简单，示例代码如下：

```java
//详细示例可查看BmobExample工程中BmobFileActivity类

String filePath_mp3 = "/mnt/sdcard/testbmob/test1.png";
String filePath_lrc = "/mnt/sdcard/testbmob/test2.png";
String[] filePaths = new String[2];
filePaths[0] = filePath_mp3;
filePaths[1] = filePath_lrc;
Bmob.uploadBatch(this, filePaths, new UploadBatchListener() {
			
	@Override
	public void onSuccess(List<BmobFile> files,List<String> urls) {
		// TODO Auto-generated method stub
		//1、files-上传完成后的BmobFile集合，是为了方便大家对其上传后的数据进行操作，例如你可以将该文件保存到表中
		//2、urls-上传文件的服务器地址
	}
	
	@Override
	public void onError(int statuscode, String errormsg) {
		// TODO Auto-generated method stub
		ShowToast("错误码"+statuscode +",错误描述："+errormsg);
	}

	@Override
	public void onProgress(int curIndex, int curPercent, int total,int totalPercent) {
		// TODO Auto-generated method stub
		//1、curIndex--表示当前第几个文件正在上传
		//2、curPercent--表示当前上传文件的进度值（百分比）
		//3、total--表示总的上传文件数
		//4、totalPercent--表示总的上传进度（百分比）
	}
});
```
