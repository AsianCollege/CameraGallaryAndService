package com.asmt.cameraexample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;

public class ActivityB extends Activity {
	TimePicker tim;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activityb);
		tim = (TimePicker)findViewById(R.id.timePicker);
		
		
	}
	
	public void returnFromThis(View v){
		Intent data = new Intent();
		data.putExtra("name",tim.getCurrentHour()+":"+tim.getCurrentMinute());
		setResult(RESULT_OK,data);
		finish();
	}
}
