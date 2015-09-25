/**
 * 
 */
package com.g.seed.web.service;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;

import com.g.seed.web.task.MyAsyncTask.AsyncResultListener;

/**
 * @ClassName: IWebService
 * @author zhigeng.ni
 * @date 2015年8月19日 上午11:11:25
 * @Description: TODO (描述作用)
 * 				
 */
public interface IWebService {
	
	HttpResponse syncPost(String action, Object po) throws ClientProtocolException, IOException;
	
	void asyncPost(String action, Object po, AsyncResultListener l);
	
}
