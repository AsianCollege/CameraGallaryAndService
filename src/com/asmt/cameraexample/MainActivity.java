package com.asmt.cameraexample;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	// Activity request codes
    private static final int CAPTURE_IMAGE_ACTIVITY_REQ = 100;
    private static final int REQ_CODE = 200;
    private static final int PICK_GALLERY = 300;
    Boolean started= false;
    
	private Button btnCamera,btnPick,btnActivity,btnService;
	private ImageView img;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		img = (ImageView)findViewById(R.id.imageView);
		btnCamera = (Button)findViewById(R.id.btnCamera);		
		btnPick = (Button)findViewById(R.id.btnPick);
		btnPick.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pickGallery();
			}
		});
		btnActivity = (Button)findViewById(R.id.btnActivityB);
		btnActivity.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				openActivityB();
			}
		});
		btnService = (Button)findViewById(R.id.btnService);
		btnService.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startAndStopService();
			}
		});		
		btnCamera.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				captureImage();
			}
		});
	}
	
	public void captureImage(){		
 
        if (getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
        	
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, "img");
            // start the image capture Intent
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQ);

        } else {
            Toast.makeText(getApplication(), "Camera not supported", Toast.LENGTH_LONG).show();
        }
		
		
	}
	public void pickGallery(){
		Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
		photoPickerIntent.setType("image/*");
		startActivityForResult(photoPickerIntent, PICK_GALLERY); 
	}
	
	public void openActivityB(){
		Intent intent = new Intent(getApplicationContext(),ActivityB.class);
		startActivityForResult(intent,REQ_CODE);
	}
	public void startAndStopService(){
		
		if(started==false){
			startService(new Intent(this,MyServices.class));			
			started=true;
			btnService.setText("Stop Service");
		}else{
			stopService(new Intent(this,MyServices.class));
			started=false;
			btnService.setText("Start Service");
		}		
	}	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {		
		if(resultCode == RESULT_OK){
			 switch(requestCode) { 
			    case CAPTURE_IMAGE_ACTIVITY_REQ:      		      
		    		Bitmap photo = (Bitmap) data.getExtras().get("data");
		            img.setImageBitmap(photo);  
			        break;
			    case REQ_CODE:
		    		String returnedResult = data.getStringExtra("name");
		    		Toast.makeText(getApplicationContext(), returnedResult,Toast.LENGTH_LONG).show();		        	    	
			    	break;
			    case PICK_GALLERY: 
		            Uri selectedImage = data.getData();
		            img.setImageURI(selectedImage);	
			 		break;
			    }
		}
		else if (resultCode == RESULT_CANCELED) {			    	
	    	Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
	    } 
	}	
}
