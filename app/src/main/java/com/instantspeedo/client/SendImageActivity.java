package com.instantspeedo.client;

import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.instantspeedo.R;
import com.instantspeedo.helper.IRequestCodeConfig;

import java.io.File;


public class SendImageActivity extends ActionBarActivity {

    Button pickImageButton;

    Button sendImageButton;

    ImageView selectedImageView;

    Uri selectedImageURI;

    ProgressBar loadingPanel;

    String hostAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_image);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            hostAddress = extras.getString("HOST_ADDRESS");
        }

        pickImageButton = (Button) findViewById(R.id.PickImageButton);
        sendImageButton = (Button) findViewById(R.id.sendImageButton);
        selectedImageView = (ImageView) findViewById(R.id.selectedImageView);
        loadingPanel = (ProgressBar) findViewById(R.id.loadingPanel);

        pickImageButton.setOnClickListener((View v) -> {
            Intent imageIntent = new Intent();
            imageIntent.setType("image/*");
            imageIntent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), IRequestCodeConfig.SEND_PHOTO_REQUEST_CODE);
        });

        sendImageButton.setOnClickListener((View v) -> {
            // send image logic
            File file = new File(getRealPathFromURI(selectedImageURI));
            Log.d("SendImageActivity", file.getAbsolutePath());
            Log.d("SendImageActivity", file.getName());
            new Thread(new SendImageThread(this, new File(file.getPath()), hostAddress)).start();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        if (requestCode == IRequestCodeConfig.SEND_PHOTO_REQUEST_CODE && resultCode == this.RESULT_OK) {
            selectedImageURI = imageReturnedIntent.getData();
            selectedImageView.setImageURI(selectedImageURI);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) selectedImageView.getLayoutParams();
            double aspectRatio = layoutParams.width / (double) layoutParams.height;
            if (aspectRatio >= 1) {
                layoutParams.width = getResources().getDisplayMetrics().widthPixels;
                layoutParams.height = (int) (layoutParams.width * aspectRatio);
            } else {
                layoutParams.height = (int) (getResources().getDisplayMetrics().heightPixels * 0.85);
                layoutParams.width = (int) (layoutParams.height / aspectRatio);
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_send_photo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }
}
