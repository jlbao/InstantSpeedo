package com.instantspeedo.sender;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.instantspeedo.R;
import com.instantspeedo.config.IRequestCodeConfig;


public class SendPhotoActivity extends ActionBarActivity {

    Button pickImageButton;

    Button sendImageButton;

    ImageView selectedImageView;

    Uri selectedImageURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_photo);

        pickImageButton = (Button) findViewById(R.id.PickImageButton);
        sendImageButton = (Button) findViewById(R.id.sendImageButton);
        selectedImageView = (ImageView) findViewById(R.id.selectedImageView);

        pickImageButton.setOnClickListener((View v) -> {
            Intent imageIntent = new Intent();
            imageIntent.setType("image/*");
            imageIntent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), IRequestCodeConfig.SEND_PHOTO_REQUEST_CODE);
        });

        sendImageButton.setOnClickListener((View v) -> {
            // send image logic
            Toast.makeText(getApplicationContext(), selectedImageURI.getPath(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        if (requestCode == IRequestCodeConfig.SEND_PHOTO_REQUEST_CODE && resultCode == this.RESULT_OK) {
            selectedImageURI = imageReturnedIntent.getData();
            selectedImageView.setImageURI(selectedImageURI);
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
}
