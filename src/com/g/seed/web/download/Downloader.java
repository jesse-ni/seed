/**
 * 
 */
package com.g.seed.web.download;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicLong;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.g.seed.web.result.Result;
import com.g.seed.web.result.StreamResult;
import com.g.seed.web.resultlistener.StreamResultListener;
import com.g.seed.web.service.Service;

import android.content.Context;

/**
 * @ClassName: Downloader
 * @author zhigeng.ni
 * @date 2015年11月10日 下午4:24:20
 * @Description: TODO (描述作用)
 * 				
 */
public class Downloader {
	public Downloader(Context context, final URI uri, final File out, final int count) {
		this.context = context;
		this.uri = uri;
		this.out = out;
		this.count = count;
	}
	
	private Context context;
	private URI uri;
	private File out;
	private File temp;
	private int count;
	private List<Fragment> fragments = Collections.synchronizedList(new ArrayList<Fragment>());
	private Timer timer = new Timer();
	private final int delay = 1000;
	private long length;
	private AtomicLong progress = new AtomicLong();
	private DownloadListener downloadListener = new DownloadListener() {
		@Override
		public void finish() {}
		
		@Override
		public void abnormalResult(Result result) {}
		
		@Override
		public void normalStart() {}
	};
	
	public void execute() {
		final Service service = new Service(uri.getScheme() + "://" + uri.getHost());
		service.asyncGet(uri.getPath(), null, new StreamResultListener() {
			@Override
			public void normalResult(Result result) {
				temp = new File(out.getPath() + ".temp");
				length = ((StreamResult) result).getContentLength();
				if (temp.exists()) {
					restoreFragment(service);
				} else {
					createFragment(service);
				}
				saveState();
				downloadListener.normalStart();
			}
			
			@Override
			public void abnormalResult(Result result) {
				downloadListener.abnormalResult(result);
			}
			
			@Override
			public void after(Result result) {
				try {
					((StreamResult) result).getStream().close();
				} catch (Exception e) {}
			}
		});
	}
	
	private String serialize() throws JSONException {
		JSONArray ja = new JSONArray();
		for (Fragment fragment : fragments) {
			JSONObject jo = new JSONObject();
			jo.put("action", fragment.getAction());
			jo.put("start", fragment.getStart());
			jo.put("end", fragment.getEnd());
			ja.put(jo);
		}
		JSONObject jo = new JSONObject();
		jo.put("progress", progress.get());
		jo.put("fragments", ja);
		return jo.toString();
	}
	
	private void restoreFragment(Service service) {
		try {
			StringBuffer sb = new StringBuffer();
			InputStreamReader is = new InputStreamReader(new FileInputStream(temp));
			BufferedReader br = new BufferedReader(is);
			String lineTxt = null;
			while ((lineTxt = br.readLine()) != null) {
				sb.append(lineTxt);
			}
			is.close();
			JSONObject rootJo = new JSONObject(sb.toString());
			progress = new AtomicLong(rootJo.getLong("progress"));
			JSONArray ja = rootJo.getJSONArray("fragments");
			for (int i = 0; i < ja.length(); i++) {
				JSONObject jo = ja.getJSONObject(i);
				final String path = temp.getPath();
				File out = new File(path.substring(0, path.lastIndexOf(".")));
				executeFragment(service, jo.getString("action"), jo.getLong("start"), jo.getLong("end"), out);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private void executeFragment(Service service, final String action, final long start, final long end, File file) {
		Fragment fragment = new Fragment(service, action, start, end, file);
		fragment.setFragmentListener(new MyFragmentListener(fragment));
		fragments.add(fragment);
		fragment.execute();
	}
	
	private File createTempFile() {
		final String path = out.getPath() + ".temp";
		final File file = new File(path);
		if (file.exists()) {
			file.delete();
		}
		try {
			file.createNewFile();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return file;
	}
	
	private void createFragment(final Service service) {
		long fragmentLength = length / count;
		for (int i = 0; i < count; i++) {
			final long start = i * fragmentLength;
			final long end = i == count - 1 ? length : (i + 1) * fragmentLength;
			executeFragment(service, uri.getPath(), start, end, out);
		}
	}
	
	public void saveState() {
		final File file = createTempFile();
		timer.schedule(new MyTimerTask(file), delay);
	}
	
	private class MyTimerTask extends TimerTask {
		private final File file;
		private MyTimerTask(File file) {
			this.file = file;
		}
		
		@Override
		public void run() {
			try {
				FileOutputStream os = new FileOutputStream(file);
				os.write(serialize().getBytes());
				os.flush();
				os.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			timer.schedule(new MyTimerTask(file), delay);
		}
	}

	class MyFragmentListener implements Fragment.FragmentListener {
		Fragment fragment;
		
		public MyFragmentListener(Fragment fragment) {
			this.fragment = fragment;
		}
		
		@Override
		public synchronized void finish() {
			fragments.remove(fragment);
			if (fragments.isEmpty()) {
				timer.cancel();
				temp.delete();
				downloadListener.finish();
			}
		}
		
		@Override
		public void progress(int progressValue) {
			progress.addAndGet(progressValue);
		}
		
	}
	
	public void setDownloadListener(DownloadListener downloadListener) {
		this.downloadListener = downloadListener;
	}
	
	public long getprogress() {
		return progress.get();
	}
	
	public long getLength() {
		return this.length;
	}
	
	public void cancel(){
		
	}
	
	public interface DownloadListener {
		void normalStart();
		
		void finish();
		
		void abnormalResult(Result result);
	}
	
}
