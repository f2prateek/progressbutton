package com.f2prateek.progressbutton.samples;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import com.f2prateek.progressbutton.ProgressButton;

public class MainActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ProgressButton pinProgress1 = (ProgressButton) findViewById(
                R.id.pin_progress_1);
        final ProgressButton pinProgress2 = (ProgressButton) findViewById(
                R.id.pin_progress_2);
        final ProgressButton pinProgress3 = (ProgressButton) findViewById(
                R.id.pin_progress_3);
        final ProgressButton pinProgress4 = (ProgressButton) findViewById(
                R.id.pin_progress_4);
        final ProgressButton pinProgress5 = (ProgressButton) findViewById(
                R.id.pin_progress_5);
        final ProgressButton pinProgress6 = (ProgressButton) findViewById(
                R.id.pin_progress_6);
        final ProgressButton pinProgress7 = (ProgressButton) findViewById(
                R.id.pin_progress_7);
        final ProgressButton pinProgress8 = (ProgressButton) findViewById(
                R.id.pin_progress_8);

        CompoundButton.OnCheckedChangeListener checkedChangeListener
                = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                updatePinProgressContentDescription((ProgressButton) compoundButton);
            }
        };

        pinProgress1.setOnCheckedChangeListener(checkedChangeListener);
        pinProgress2.setOnCheckedChangeListener(checkedChangeListener);
        pinProgress3.setOnCheckedChangeListener(checkedChangeListener);
        pinProgress4.setOnCheckedChangeListener(checkedChangeListener);
        pinProgress5.setOnCheckedChangeListener(checkedChangeListener);
        pinProgress6.setOnCheckedChangeListener(checkedChangeListener);
        pinProgress7.setOnCheckedChangeListener(checkedChangeListener);
        pinProgress8.setOnCheckedChangeListener(checkedChangeListener);

        SeekBar progressSeekBar = (SeekBar) findViewById(R.id.progress_seek_bar);
        progressSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                pinProgress1.setProgress(progress);
                pinProgress2.setProgress(progress);
                pinProgress3.setProgress(progress);
                pinProgress4.setProgress(progress);
                pinProgress5.setProgress(progress);
                pinProgress6.setProgress(progress);
                pinProgress7.setProgress(progress);
                pinProgress8.setProgress(progress);

                updatePinProgressContentDescription(pinProgress1);
                updatePinProgressContentDescription(pinProgress2);
                updatePinProgressContentDescription(pinProgress3);
                updatePinProgressContentDescription(pinProgress4);
                updatePinProgressContentDescription(pinProgress5);
                updatePinProgressContentDescription(pinProgress6);
                updatePinProgressContentDescription(pinProgress7);
                updatePinProgressContentDescription(pinProgress8);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        pinProgress1.setProgress(progressSeekBar.getProgress());
        pinProgress2.setProgress(progressSeekBar.getProgress());
        pinProgress3.setProgress(progressSeekBar.getProgress());
        pinProgress4.setProgress(progressSeekBar.getProgress());
        pinProgress5.setProgress(progressSeekBar.getProgress());
        pinProgress6.setProgress(progressSeekBar.getProgress());
        pinProgress7.setProgress(progressSeekBar.getProgress());
        pinProgress8.setProgress(progressSeekBar.getProgress());

        updatePinProgressContentDescription(pinProgress1);
        updatePinProgressContentDescription(pinProgress2);
        updatePinProgressContentDescription(pinProgress3);
        updatePinProgressContentDescription(pinProgress4);
        updatePinProgressContentDescription(pinProgress5);
        updatePinProgressContentDescription(pinProgress6);
        updatePinProgressContentDescription(pinProgress7);
        updatePinProgressContentDescription(pinProgress8);
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
}

