package com.f2prateek.progressbutton.samples;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import com.f2prateek.progressbutton.ProgressButton;

public class MainActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ProgressButton progressButton1 = (ProgressButton) findViewById(R.id.pin_progress_1);
        final ProgressButton progressButton2 = (ProgressButton) findViewById(R.id.pin_progress_2);
        final ProgressButton progressButton3 = (ProgressButton) findViewById(R.id.pin_progress_3);
        final ProgressButton progressButton4 = (ProgressButton) findViewById(R.id.pin_progress_4);
        final ProgressButton progressButton5 = (ProgressButton) findViewById(R.id.pin_progress_5);
        final ProgressButton progressButton6 = (ProgressButton) findViewById(R.id.pin_progress_6);
        final ProgressButton progressButton7 = (ProgressButton) findViewById(R.id.pin_progress_7);
        final ProgressButton progressButton8 = (ProgressButton) findViewById(R.id.pin_progress_8);
        final LinearLayout container = (LinearLayout) findViewById(R.id.container);
        final ProgressButton progressButton9 = addProgressButton(container);
        final ProgressButton progressButton10 = addProgressButton(container);
        final ProgressButton progressButton11 = addProgressButton(container);
        final ProgressButton progressButton12 = addProgressButton(container);

        CompoundButton.OnCheckedChangeListener checkedChangeListener
                = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                updatePinProgressContentDescription((ProgressButton) compoundButton);
            }
        };

        progressButton1.setOnCheckedChangeListener(checkedChangeListener);
        progressButton2.setOnCheckedChangeListener(checkedChangeListener);
        progressButton3.setOnCheckedChangeListener(checkedChangeListener);
        progressButton4.setOnCheckedChangeListener(checkedChangeListener);
        progressButton5.setOnCheckedChangeListener(checkedChangeListener);
        progressButton6.setOnCheckedChangeListener(checkedChangeListener);
        progressButton7.setOnCheckedChangeListener(checkedChangeListener);
        progressButton8.setOnCheckedChangeListener(checkedChangeListener);
        progressButton9.setOnCheckedChangeListener(checkedChangeListener);
        progressButton10.setOnCheckedChangeListener(checkedChangeListener);
        progressButton11.setOnCheckedChangeListener(checkedChangeListener);
        progressButton12.setOnCheckedChangeListener(checkedChangeListener);

        SeekBar progressSeekBar = (SeekBar) findViewById(R.id.progress_seek_bar);
        progressSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressButton1.setProgress(progress);
                progressButton2.setProgress(progress);
                progressButton3.setProgress(progress);
                progressButton4.setProgress(progress);
                progressButton5.setProgress(progress);
                progressButton6.setProgress(progress);
                progressButton7.setProgress(progress);
                progressButton8.setProgress(progress);
                progressButton9.setProgress(progress);
                progressButton10.setProgress(progress);
                progressButton11.setProgress(progress);
                progressButton12.setProgress(progress);

                updatePinProgressContentDescription(progressButton1);
                updatePinProgressContentDescription(progressButton2);
                updatePinProgressContentDescription(progressButton3);
                updatePinProgressContentDescription(progressButton4);
                updatePinProgressContentDescription(progressButton5);
                updatePinProgressContentDescription(progressButton6);
                updatePinProgressContentDescription(progressButton7);
                updatePinProgressContentDescription(progressButton8);
                updatePinProgressContentDescription(progressButton9);
                updatePinProgressContentDescription(progressButton10);
                updatePinProgressContentDescription(progressButton11);
                updatePinProgressContentDescription(progressButton12);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        progressButton1.setProgress(progressSeekBar.getProgress());
        progressButton2.setProgress(progressSeekBar.getProgress());
        progressButton3.setProgress(progressSeekBar.getProgress());
        progressButton4.setProgress(progressSeekBar.getProgress());
        progressButton5.setProgress(progressSeekBar.getProgress());
        progressButton6.setProgress(progressSeekBar.getProgress());
        progressButton7.setProgress(progressSeekBar.getProgress());
        progressButton8.setProgress(progressSeekBar.getProgress());
        progressButton9.setProgress(progressSeekBar.getProgress());
        progressButton10.setProgress(progressSeekBar.getProgress());
        progressButton11.setProgress(progressSeekBar.getProgress());
        progressButton12.setProgress(progressSeekBar.getProgress());

        updatePinProgressContentDescription(progressButton1);
        updatePinProgressContentDescription(progressButton2);
        updatePinProgressContentDescription(progressButton3);
        updatePinProgressContentDescription(progressButton4);
        updatePinProgressContentDescription(progressButton5);
        updatePinProgressContentDescription(progressButton6);
        updatePinProgressContentDescription(progressButton7);
        updatePinProgressContentDescription(progressButton8);
        updatePinProgressContentDescription(progressButton9);
        updatePinProgressContentDescription(progressButton10);
        updatePinProgressContentDescription(progressButton11);
        updatePinProgressContentDescription(progressButton12);
    }

    private void updatePinProgressContentDescription(ProgressButton button) {
        int progress = button.getProgress();
        if (progress <= 0) {
            button.setContentDescription(getString(button.isChecked()
                    ? R.string.content_desc_pinned_not_downloaded
                    : R.string.content_desc_unpinned_not_downloaded));
        } else if (progress >= 100) {
            button.setContentDescription(getString(button.isChecked()
                    ? R.string.content_desc_pinned_downloaded
                    : R.string.content_desc_unpinned_downloaded));
        } else {
            button.setContentDescription(getString(button.isChecked()
                    ? R.string.content_desc_pinned_downloading
                    : R.string.content_desc_unpinned_downloading));
        }
    }

    private ProgressButton addProgressButton(LinearLayout container) {
        final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
        final ProgressButton progressButton = new ProgressButton(this);
        progressButton.setLayoutParams(layoutParams);
        container.addView(progressButton);
        return progressButton;
    }
}

