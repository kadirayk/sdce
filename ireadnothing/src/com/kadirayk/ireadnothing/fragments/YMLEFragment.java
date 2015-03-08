package com.kadirayk.ireadnothing.fragments;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.kadirayk.ireadnothing.R;
import com.kadirayk.ireadnothing.network.Controller.OnTitleResponseRecievedListener;
import com.kadirayk.ireadnothing.network.Controller.OnYMLEResponseRecievedListener;
import com.kadirayk.ireadnothing.network.YMLEParser;
import com.kadirayk.ireadnothing.network.model.YMLE;

public class YMLEFragment extends Fragment implements OnItemClickListener, OnTitleResponseRecievedListener, OnYMLEResponseRecievedListener{

	private View mView;
	private ListView fragment_ymle_listview;
	private TextView fragment_ymle_textview;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		mView = inflater.inflate(R.layout.fragment_ymle, container, false);
		setUI();
		
		YMLEParser networkHandler = new YMLEParser(getActivity(), this);
		//networkHandler.callTitleTask();
		networkHandler.callYMLETask();
		
		return mView;
	}
	
	private void setUI(){
		fragment_ymle_textview = (TextView) mView.findViewById(R.id.fragment_ymle_textview);
		fragment_ymle_listview = (ListView) mView.findViewById(R.id.fragment_ymle_listview);
		fragment_ymle_listview.setOnItemClickListener(this);
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void OnTitleResponseRecieved(String response) {
		
		fragment_ymle_textview.setText(response);
		
	}

	@Override
	public void OnYMLEResponseRecieved(List<String> YMLEs) {

		Toast.makeText(getActivity(), YMLEs.get(0), Toast.LENGTH_SHORT).show();
		
	}
	
}
