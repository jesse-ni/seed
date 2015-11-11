/**
 * 
 */
package com.g.seed.web.download;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.Locale;

import com.g.seed.web.form.DownloadleForm;
import com.g.seed.web.result.Result;
import com.g.seed.web.result.StreamResult;
import com.g.seed.web.resultlistener.StreamResultListener;
import com.g.seed.web.service.Service;

/**
 * @ClassName: DownloadFragment
 * @author zhigeng.ni
 * @date 2015年11月10日 下午3:01:04
 * @Description: TODO (描述作用)
 * 				
 */
public class Fragment {
	public Fragment(Service downloadService, String action, long start, long end, File out) {
		super();
		this.action = action;
		this.start = start;
		this.end = end;
		this.downloadService = downloadService;
		this.out = out;
	}
	
	public Fragment(String action, long start, long end, File out) {
		this(Service.getInstance(), action, start, end, out);
	}
	
	private String action;
	private long start;
	private long end;
	private Service downloadService;
	private File out;
	private final int stepWidth = 1024;
	private FragmentListener listener = new FragmentListener() {
		@Override
		public void progress(int progress) {}
		
		@Override
		public void finish() {}
	};
	
	public void setFragmentListener(FragmentListener listener) {
		this.listener = listener;
	}
	
	public void execute() {
		final String endStr = end > -1 ? String.valueOf(end) : "";
		final String range = String.format(Locale.getDefault(), "bytes=%d-%s", start, endStr);
		downloadService.asyncSubmit(new DownloadleForm(action, range), new StreamResultListener() {
			@Override
			public void resultInBackground(StreamResult result) throws IOException {
				final InputStream is = result.getStream();
				final RandomAccessFile raf = new RandomAccessFile(out, "rw");
				raf.seek(start);
				byte[] data = new byte[stepWidth];
				int read;
				while ((read = is.read(data)) != -1) {
					raf.write(data, 0, read);
					start += read;
					listener.progress(read);
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {}
				}
				is.close();
				raf.close();
			}
			
			@Override
			public void normalResult(Result result) {
				listener.finish();
			}
			
			@Override
			public void abnormalResult(Result result) {
				super.abnormalResult(result);
			}
		});
	}
	
	public interface FragmentListener {
		void finish();
		
		void progress(int progress);
	}
	
	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}
	
	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}
	
	/**
	 * @return the start
	 */
	public long getStart() {
		return start;
	}
	
	/**
	 * @param start the start to set
	 */
	public void setStart(long start) {
		this.start = start;
	}
	
	/**
	 * @return the end
	 */
	public long getEnd() {
		return end;
	}
	
	/**
	 * @param end the end to set
	 */
	public void setEnd(long end) {
		this.end = end;
	}
	
}
