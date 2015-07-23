package zjdf.zhaogongzuo.net;

import java.io.File;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;

import zjdf.zhaogongzuo.utils.BitmapTools;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.ViewGroup.LayoutParams;

/**
 * 图片异步下载队列
 * @author Eilin.Yang
 *
 */
public class ImageLoader {
	private Context context;
	private boolean isLoop = true;
//图片软引用
	private HashMap<String, SoftReference<Bitmap>> caches;
	
	private ArrayList<ImageLoadTask> taskQueue;

	private Thread thread = new Thread(){
		public void run() {
			while(isLoop){
				while(taskQueue.size()>0){
					try {
						ImageLoadTask task = taskQueue.remove(0);		
						byte[] bytes = HttpTools.getBytes(task.path, null, HttpTools.METHOD_GET);
						task.bitmap = BitmapTools.getBitmap(bytes, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
						if(task.bitmap!=null){
							caches.put(task.path, new SoftReference<Bitmap>(task.bitmap));
//							File dir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
							File dir = new File(task.folder);
							if(dir!=null && !dir.exists()){
								dir.mkdirs();
							}
							File file = new File(dir,task.name);
							BitmapTools.saveBitmap(file.getAbsolutePath(), task.bitmap);
							Message msg = Message.obtain();
							msg.obj = task;
							handler.sendMessage(msg);
						}
					} catch (ClientProtocolException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}				
				synchronized (this) {
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			
		};
	};
	private Handler handler = new Handler(){
    	public void handleMessage(android.os.Message msg) {
    		ImageLoadTask task = (ImageLoadTask)msg.obj;
			task.callback.imageLoaded(task.path,task.name, task.bitmap);
    		
    	};
    };
	
    /**
     * 图片任务
     * @author Eilin.Yang  VearyEast 
     * @since 2014年6月5日
     * @version v1.0.0
     * @modify
     */
	private class ImageLoadTask{
		/**
		 * URL
		 */
		String path;
		/**
		 * 存放目录
		 */
		String folder;
		/**
		 * 图片名称
		 */
		String name;
		/**
		 * 下载完成的 图片
		 */
		Bitmap bitmap;
		/**
		 * 回调监听
		 */
		Callback callback;
	}
	
	public interface Callback{
		void imageLoaded(String path,String names,Bitmap bitmap);
	}
	
	public void quit(){
		isLoop = false;
	}
	
	public void resume(){
		isLoop = true;
	}

	public ImageLoader(Context context){
		this.context = context;
		caches = new HashMap<String, SoftReference<Bitmap>>();
		taskQueue = new ArrayList<ImageLoader.ImageLoadTask>();
		thread.start();
	}

	/**
	 * 异步图片下载
	 * @param tag 状态标签。任意字符串
	 * @param path 图片URL
	 * @param folder 图片存放目录
	 * @param names 图片名称
	 * @param callback 回调监听
	 * @return
	 */
	public Bitmap loadImage(String tag, String path,String folder, String names, Callback callback){
		Bitmap bitmap = null;

		if(caches.containsKey(path)){
			bitmap = caches.get(path).get();

			if(bitmap==null)
				caches.remove(path);
			else
				return bitmap;
		}
//		File dir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);	
//		File file = new File(folder,path.substring(path.lastIndexOf("/")+1));
		File dir = new File(folder);
		if(dir!=null && !dir.exists()){
			dir.mkdirs();
		}
		File file = new File(folder,names);
		bitmap = BitmapTools.getBitmap(tag, file.getAbsolutePath());
		if(bitmap!=null)
			return bitmap;
		ImageLoadTask task = new ImageLoadTask();
		task.path = path;
		task.name=names;
		task.folder=folder;
		task.callback = callback;
		taskQueue.add(task);
		synchronized (thread) {
			thread.notify();
		}
		return null;
	}
}
