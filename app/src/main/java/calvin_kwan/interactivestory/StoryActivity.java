package calvin_kwan.interactivestory;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.awt.font.TextAttribute;

import calvin_kwan.interactivestory.model.Page;
import calvin_kwan.interactivestory.model.Story;


public class StoryActivity extends AppCompatActivity
{

    public static final String TAG = StoryActivity.class.getSimpleName();

    private Story mStory = new Story();
    private ImageView mImageView;
    private TextView mTextView;
    private Button mChoice1;
    private Button mChoice2;
    private String mName;
    private Page mCurrentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        Intent intent = getIntent();
        String mName = intent.getStringExtra("name");
        //Toast.makeText(this,mName,Toast.LENGTH_LONG).show();
        if(mName == null)
        {
            mName = "friend";
        }
        //Log.d(TAG, mName);

        mImageView = (ImageView) findViewById(R.id.storyImageView);
        mTextView = (TextView)findViewById(R.id.storyTextView);
        mChoice1 = (Button)findViewById(R.id.choiceButton1);
        mChoice2 = (Button)findViewById(R.id.choiceButton2);

        loadPage(0);
    }

    private void loadPage(int choice)
    {
        mCurrentPage = mStory.getPage(choice);
        Drawable drawable = getResources().getDrawable(mCurrentPage.getmImageId());
        mImageView.setImageDrawable(drawable);
        Intent intent1 = getIntent();
        String mName = intent1.getStringExtra("name");
        //Toast.makeText(this,mName,Toast.LENGTH_LONG).show();
        String pageText = mCurrentPage.getText();
        // add the name if placeholde included. wont add if no placeholder

        pageText = String.format(pageText, mName);
        mTextView.setText(mCurrentPage.getText());
        if(mCurrentPage.isFinal())
        {
            mChoice1.setVisibility(View.INVISIBLE);
            mChoice2.setText("Play Again");
            mChoice2.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    finish();
                }
            });
        }
        else
        {
            mChoice1.setText(mCurrentPage.getChoice1().getText());
            mChoice2.setText(mCurrentPage.getChoice2().getText());

            mChoice1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int nextpage = mCurrentPage.getChoice1().getNextPage();
                    loadPage(nextpage);
                }
            });

            mChoice2.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int nextpage = mCurrentPage.getChoice2().getNextPage();
                    loadPage(nextpage);
                }
            });
        }

    }


}
