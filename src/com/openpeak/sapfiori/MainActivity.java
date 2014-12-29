package com.openpeak.sapfiori;

import java.io.BufferedReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends Activity{
	private static final String TAG = "MainActivity";
	final static String fioriUrl = "http://50.18.50.6:8000/sap/bc/ui5_ui5/ui2/ushell/shells/abap/FioriLaunchpad.html#";
//	final static String fioriUrl = "https://fioridemo.att.com/sap/bc/ui5_ui5/ui2/ushell/shells/abap/FioriLaunchpad.html";
	HttpGet fioriGet = new HttpGet();
	HttpPost fioriPost = new HttpPost();
	URI website;
	String responseString;
	HttpResponse response;
	BufferedReader in;
	ProgressBar progress;
	HttpHelper httpHelper;
	
	WebView webView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getWindow().requestFeature(Window.FEATURE_PROGRESS);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		httpHelper = new HttpHelper();
		webView = (WebView)findViewById(R.id.webView);
		webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		progress = (ProgressBar)findViewById(R.id.progress);
		progress.setVisibility(View.GONE);
		WebSettings webSettings = webView.getSettings();
//        webSettings.setSaveFormData(false);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(false);
        webSettings.setDomStorageEnabled(true);
		
        webSettings.setAllowContentAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setLoadsImagesAutomatically(true);
		
		webView.setWebViewClient(new WebViewClient() {
			@Override
		    public boolean shouldOverrideUrlLoading(WebView view, String url) {
		        view.loadUrl(url);
		        return true;

		    }
			   public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
			     Toast.makeText(MainActivity.this, "Oh no! " + description, Toast.LENGTH_SHORT).show();
			   }
			@Override
			public void onPageFinished(WebView view, String url) {
//				Toast.makeText(MainActivity.this, "Done loading", Toast.LENGTH_SHORT).show();
				
				super.onPageFinished(view, url);
			}
			 });
		
		
		
		
		new AsyncTask<Void, String, Void>() {

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				progress.setVisibility(View.VISIBLE);
				webView.loadUrl(fioriUrl);
				try {
					website = new URI(fioriUrl);
//					website = new URI("https://fioridemo.att.com/sap/bc/ui5_ui5/ui2/ushell/shells/abap/FioriLaunchpad.html?sap-system-login-oninputprocessing=onLogin&sap-urlscheme=&sap-system-login=onLogin&sap-system-login-basic_auth=&sap-client=200&sap-language=EN&sap-accessibility=&sap-login-XSRF=96GMYr1jzvqt-e-AtobLzpeVGDS-SLuR02GsBi7u4ug%3D&sap-system-login-cookie_disabled=true&sap-user=fioridev1&sap-password=Test123");
					
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				Log.d(TAG, "PRE EXECUTE");
			}

			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
				progress.setVisibility(View.GONE);
				Log.d(TAG, "POST EXECUTE");
				Log.d(TAG, "Login part 3: electric bagaloo"+responseString);
//				webView.loadData(responseString, "text/html; charset=utf-8", "UTF-8");
//				webView.loadDataWithBaseURL("", responseString, "text/html; charset=utf-8", "UTF-8", "");
			}

			@Override
			protected Void doInBackground(Void... arg0) {
				Log.d(TAG, "BACKGROUND EXECUTE");
				try{
					DefaultHttpClient httpclient = new DefaultHttpClient();
				httpclient = new DefaultHttpClient();

		        HttpGet httpget = new HttpGet(fioriUrl);

		        HttpResponse response = httpclient.execute(httpget);
		        HttpEntity entity = response.getEntity();

		        Log.d(TAG, "Initial form get: " + response.getStatusLine());
		        if (entity != null) {
		            entity.consumeContent();
		        }
		        Log.d(TAG, "Initial set of cookies:");
		        List<Cookie> cookies = httpclient.getCookieStore().getCookies();
		        if (cookies.isEmpty()) {
		        	Log.d(TAG, "Initial None");
		        } else {
		            for (int i = 0; i < cookies.size(); i++) {
		            	Log.d(TAG, "Initial - " + cookies.get(i).toString());
		            }
		        }
		        
//				DefaultHttpClient httpclient2 = new DefaultHttpClient();
//		        HttpPost httpost = new HttpPost(fioriUrl);
//
//		        Log.d(TAG, "Initial sap-system-login-cookie_disabled = "+cookies.get(0).getValue());
//		        List <NameValuePair> nvps = new ArrayList <NameValuePair>();
//		        nvps.add(new BasicNameValuePair("sap-login-XSRF", cookies.get(0).getValue()));
//		        nvps.add(new BasicNameValuePair("sap-urlscheme", ""));
//		        nvps.add(new BasicNameValuePair("sap-system-login", "onLogin"));
//		        nvps.add(new BasicNameValuePair("sap-system-login-basic_auth", ""));
//		        nvps.add(new BasicNameValuePair("sap-accessibility", ""));
//		        nvps.add(new BasicNameValuePair("sap-system-login-cookie_disabled", ""));
//		        nvps.add(new BasicNameValuePair("sap-client", "200"));
//		        nvps.add(new BasicNameValuePair("sap-language", "EN"));
//		        nvps.add(new BasicNameValuePair("sap-user", "fioridev1"));
//		        nvps.add(new BasicNameValuePair("sap-password", "Test123"));
		        
//		        [urlRequest2 addValue:@"application/x-www-form-urlencoded" forHTTPHeaderField:@"Content-Type"];
		        
		        HashMap<String, String> httpheaders = new HashMap<>();
		        HashMap<String, String> params = new HashMap<>();
		        
		        params.put("sap-login-XSRF", cookies.get(0).getValue());
		        params.put("sap-urlscheme", "");
		        params.put("sap-system-login", "onLogin");
		        params.put("sap-system-login-basic_auth", "");
		        params.put("sap-accessibility", "");
		        params.put("sap-system-login-cookie_disabled", "");
		        params.put("sap-client", "200");
		        params.put("sap-language", "EN");
		        params.put("sap-user", "fioridev1");
		        params.put("sap-password", "Test123");
		        
		        httpheaders.put("Content-Type", "application/x-www-form-urlencoded");
//		        httpost.setHeader("Content-Type", "application/x-www-form-urlencoded");
//		        httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
		        
		        String localresponseString = httpHelper.performGet(fioriUrl, "fioridev1", "Test123", httpheaders);
//				String localresponseString = httpHelper.performPost(fioriUrl, "fioridev1", "Test123", httpheaders, params);
				Log.d(TAG, "Login response string " + localresponseString);

		        
//		        response = httpclient2.execute(httpost);
//		        entity = response.getEntity();
		        

//		        Log.d(TAG, "Login form get: " + response.getStatusLine());
//		        Header[] headers = response.getAllHeaders();
//		        for(int i=0;i<headers.length;i++){
//		        	Log.d(TAG, "Login headers "+ headers[i]);
//		        }
//		        
//		        Log.d(TAG, "Login set of cookies:");
//		        List<Cookie> loginCookies = httpclient2.getCookieStore().getCookies();
//		        if (loginCookies.isEmpty()) {
//		        	Log.d(TAG, "Login None");
//		        } else {
//		            for (int i = 0; i < loginCookies.size(); i++) {
//		            	Log.d(TAG, "Login - " + loginCookies.get(i).toString());
//		            }
//		        }
		        

//				String line = EntityUtils.toString(entity);
				responseString = localresponseString;
				
				//this code snippit is for if something is needed to be saved to a file
//				FileOutputStream outputStream;
//				try {
//				  outputStream = openFileOutput("html.html", Context.MODE_PRIVATE);
//				  outputStream.write(line.getBytes());
//				  outputStream.close();
//				} catch (Exception e) {
//				  e.printStackTrace();
//				}
//				Log.d(TAG, "Login entity content: " + line);
		        
		        if (entity != null) {
		            entity.consumeContent();
		        }

		        // When HttpClient instance is no longer needed, 
		        // shut down the connection manager to ensure
		        // immediate deallocation of all system resources
		        httpclient.getConnectionManager().shutdown();    
//		        httpclient2.getConnectionManager().shutdown();    
//		    }
				}catch(Exception e){
		        	
		        }
				return null;
			}

			@Override
			protected void onProgressUpdate(String... values) {
				// TODO Auto-generated method stub
				super.onProgressUpdate(values);
			}
			 
		 }.execute();
		
		Log.d(TAG, "AFTER EXECUTE");
		
	}




	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
//		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            webView.loadUrl(fioriUrl);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

