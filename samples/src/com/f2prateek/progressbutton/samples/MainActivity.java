/*
 * Copyright 2013 Prateek Srivastava (@f2prateek)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.f2prateek.progressbutton.samples;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import com.f2prateek.progressbutton.ProgressButton;
import java.util.ArrayList;

/**
 * Examples.
 * 1 : Default
 * 2 : Pinned
 * 3 : Pinned Clickable
 * 4 : Colored Clickable
 * <p/>
 * Row 1 : from xml
 * Row 2 : from code (duplicating row 1)
 */
public class MainActivity extends Activity {
  // Don't forget that this is offset by 1!
  ArrayList<ProgressButton> progressButtons = new ArrayList<ProgressButton>(8);
  SeekBar progressSeekBar;

  Handler handler = new Handler();
  Runnable progressRunner = new Runnable() {
    @Override
    public void run() {
      if (progress < 100) {
        progress += 2;
        progressSeekBar.setProgress(progress);
        handler.postDelayed(progressRunner, 50);
      }
    }
  };
  private int progress = 100;

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    progressButtons.add((ProgressButton) findViewById(R.id.pin_progress_1));
    progressButtons.add((ProgressButton) findViewById(R.id.pin_progress_2));
    progressButtons.add((ProgressButton) findViewById(R.id.pin_progress_3));
    progressButtons.add((ProgressButton) findViewById(R.id.pin_progress_4));

    final LinearLayout container = (LinearLayout) findViewById(R.id.container);

    /**
     * Default implementation of the {@link ProgressButton}.
     * By default, the {@link ProgressButton} is not clickable, and is unpinned.
     */
    progressButtons.add(addProgressButton(container));

    /**
     * A  {@link ProgressButton} that starts pinned, and is not clickable, so it
     * stays pinned.
     * @see ProgressButton#setPinned(boolean)
     * @see View#setClickable(boolean)
     */
    progressButtons.add(addProgressButton(container));
    progressButtons.get(5).setPinned(true);

    /**
     * A progress button that starts pinned, and is clickable, so it's
     * pinned state can be changed by the user.
     * @see ProgressButton#setPinned(boolean)
     * @see View#setClickable(boolean)
     */
    progressButtons.add(addProgressButton(container));
    progressButtons.get(6).setPinned(true);
    progressButtons.get(6).setClickable(true);
    progressButtons.get(6).setFocusable(true);

    /**
     * An example of how to use style the button in code.
     * @see ProgressButton#setPinnedDrawable(android.graphics.drawable.Drawable)
     * @see ProgressButton#setUnpinnedDrawable(android.graphics.drawable.Drawable)
     * @see ProgressButton#setShadowDrawable(android.graphics.drawable.Drawable)
     * @see ProgressButton#setProgressColor(int)
     * @see ProgressButton#setCircleColor(int)
     */
    progressButtons.add(addProgressButton(container));
    progressButtons.get(7).setProgressColor(getResources().getColor(R.color.holo_green_light));
    progressButtons.get(7).setCircleColor(getResources().getColor(R.color.holo_green_dark));
    progressButtons.get(7).setClickable(true);
    progressButtons.get(7).setFocusable(true);

    CompoundButton.OnCheckedChangeListener checkedChangeListener =
        new CompoundButton.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
            updatePinProgressContentDescription((ProgressButton) compoundButton);
          }
        };

    for (ProgressButton progressButton : progressButtons) {
      progressButton.setOnCheckedChangeListener(checkedChangeListener);
    }

    // Use this seekbar to see the bar at different states
    progressSeekBar = (SeekBar) findViewById(R.id.progress_seek_bar);
    progressSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
      @Override
      public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        for (ProgressButton progressButton : progressButtons) {
          updateProgressButton(progressButton, seekBar);
        }
      }

      @Override
      public void onStartTrackingTouch(SeekBar seekBar) {
      }

      @Override
      public void onStopTrackingTouch(SeekBar seekBar) {
      }
    });

    // Set the initial progress to match the seekbar
    for (ProgressButton progressButton : progressButtons) {
      updateProgressButton(progressButton, progressSeekBar);
    }

    Button progressRunnableButton = (Button) findViewById(R.id.progress_runnable_button);
    progressRunnableButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Log.d("Progress", "Value is " + progress);
        if (progress == 100) {
          progress = 0;
          progressRunner.run();
        }
      }
    });
  }

  /**
   * Helper method to update the progressButton's progress and it's
   * content description.
   */
  private void updateProgressButton(ProgressButton progressButton, SeekBar progressSeekBar) {
    progressButton.setProgress(progressSeekBar.getProgress());
    updatePinProgressContentDescription(progressButton);
  }

  private void updatePinProgressContentDescription(ProgressButton button) {
    int progress = button.getProgress();
    if (progress <= 0) {
      button.setContentDescription(getString(
          button.isChecked() ? R.string.content_desc_pinned_not_downloaded
              : R.string.content_desc_unpinned_not_downloaded));
    } else if (progress >= 100) {
      button.setContentDescription(getString(
          button.isChecked() ? R.string.content_desc_pinned_downloaded
              : R.string.content_desc_unpinned_downloaded));
    } else {
      button.setContentDescription(getString(
          button.isChecked() ? R.string.content_desc_pinned_downloading
              : R.string.content_desc_unpinned_downloading));
    }
  }

  /**
   * Helper function that creates a new progress button, adds it to the given layout.
   * Returns a reference to the progress button for customization.
   */
  private ProgressButton addProgressButton(LinearLayout container) {
    final LinearLayout.LayoutParams layoutParams =
        new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
    final ProgressButton progressButton = new ProgressButton(this);
    progressButton.setLayoutParams(layoutParams);
    container.addView(progressButton);
    return progressButton;
  }
}
