package com.kadirayk.ireadnothing.network;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.kadirayk.ireadnothing.fragments.YMLEFragment;
import com.kadirayk.ireadnothing.network.model.YMLE;

public class YMLEParser {

	
	private Context mContext;
	private Fragment currentFragment;
	private TitleTask mTitleTask;
	private YMLETask mYMLETask;
	
	String YMLE_URL = "https://www.eksisozluk.com/debe";
	ProgressDialog mProgressDialog;
	
	
	public YMLEParser(Context context, Fragment fragmet) {
		mContext = context;
		currentFragment = fragmet;
		mTitleTask = new TitleTask();
		mYMLETask = new YMLETask();
	}
	
	
	public boolean isConnected(Context mContext) {
		ConnectivityManager connMgr = (ConnectivityManager) mContext.getSystemService(Activity.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected())
			return true;
		else
			return false;
	}
	
	public void callTitleTask() {
		if (isConnected(mContext)) {
			mTitleTask.execute();
		} else {
			Toast.makeText(mContext, "Sorun internet ba�lant�s�nda, bizle alakas� yok.", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void callYMLETask() {
		if (isConnected(mContext)) {
			mYMLETask.execute();
		} else {
			Toast.makeText(mContext, "Sorun internet ba�lant�s�nda, bizle alakas� yok.", Toast.LENGTH_SHORT).show();
		}
	}
	
	
	// Title AsyncTask
	private class TitleTask extends AsyncTask<Void, Void, String> {
		String title;
 
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressDialog = new ProgressDialog(mContext);
			mProgressDialog.setTitle("ba�l�k var, ba�l�k var");
			mProgressDialog.setMessage("Loading...");
			mProgressDialog.setIndeterminate(false);
			mProgressDialog.show();
		}
 
		@Override
		protected String doInBackground(Void... params) {
			try {
				// Connect to the web site
				Document document = Jsoup.connect(YMLE_URL).get();
				// Get the html document title
				title = document.title();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return title;
		}
 
		@Override
		protected void onPostExecute(String result) {
			if (result == null) {
				Toast.makeText(mContext, "ba�aramad�k :(", Toast.LENGTH_SHORT).show();
			} else {
				((YMLEFragment) currentFragment).OnTitleResponseRecieved(result);
			}
			super.onPostExecute(result);
			mProgressDialog.dismiss();
		}
	}
	
	//YMLE AsyncTask
	private class YMLETask extends AsyncTask<Void, Void, List<String>> {
		String title;
		List<String> YMLEs = new ArrayList<String>();
 
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressDialog = new ProgressDialog(mContext);
			mProgressDialog.setTitle("ba�l�k var, ba�l�k var");
			mProgressDialog.setMessage("Loading...");
			mProgressDialog.setIndeterminate(false);
			mProgressDialog.show();
		}
 
		@Override
		protected List<String> doInBackground(Void... params) {
			try {
				// Connect to the web site
				Document document = Jsoup.connect(YMLE_URL).get();
				// Get the html document title
				title = document.title();
				Elements YMLEElements = document.select("span[class=\"caption\"]");
				
				for(Element e : YMLEElements){
					YMLEs.add(e.text());
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			return YMLEs;
		}
 
		@Override
		protected void onPostExecute(List<String> result) {
			if (result == null) {
				Toast.makeText(mContext, "ba�aramad�k :(", Toast.LENGTH_SHORT).show();
			} else {
				((YMLEFragment) currentFragment).OnYMLEResponseRecieved(result);
			}
			super.onPostExecute(result);
			mProgressDialog.dismiss();
		}
	}
	
}