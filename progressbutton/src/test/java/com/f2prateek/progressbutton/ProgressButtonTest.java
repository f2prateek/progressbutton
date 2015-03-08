package com.f2prateek.progressbutton;

import android.app.Activity;
import android.os.Parcelable;
import android.widget.CompoundButton;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Fail.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class) @Config(manifest = "src/main/AndroidManifest.xml")
public class ProgressButtonTest {
  private Activity activity;
  private ProgressButton button;

  @Before public void setUp() {
    activity = Robolectric.buildActivity(Activity.class).create().get();
    button = new ProgressButton(activity);
  }

  @Test public void pint() {
    button.setPinned(false);
    assertThat(button.isChecked()).isEqualTo(button.isPinned()).isFalse();

    button.setPinned(true);
    assertThat(button.isChecked()).isEqualTo(button.isPinned()).isTrue();

    button.setChecked(false);
    assertThat(button.isChecked()).isEqualTo(button.isPinned()).isFalse();
  }

  @Test public void toggle() {
    // Setup
    button.setPinned(false);
    assertThat(button.isChecked()).isEqualTo(button.isPinned()).isFalse();

    // Toggle twice and verify each time
    button.toggle();
    assertThat(button.isChecked()).isEqualTo(button.isPinned()).isTrue();
    button.toggle();
    assertThat(button.isChecked()).isEqualTo(button.isPinned()).isFalse();
  }

  @Test public void validProgressValueZero() {
    button.setProgress(0);
    assertThat(button.getProgress()).isEqualTo(0);
  }

  @Test public void validProgressValue() {
    button.setProgress(50);
    assertThat(button.getProgress()).isEqualTo(50);
  }

  @Test public void validProgressValueMax() {
    button.setProgress(100);
    assertThat(button.getProgress()).isEqualTo(100);
  }

  @Test public void invalidProgressValue() {
    try {
      button.setProgress(101);
      fail("Setting progress > max should throw");
    } catch (IllegalArgumentException e) {
    }
  }

  @Test public void anotherInvalidProgressValue() {
    try {
      button.setProgress(-1);
      fail("Setting progress < 0 should throw");
    } catch (IllegalArgumentException e) {
    }
  }

  @Test public void setMaxToUnderZero() {
    try {
      button.setMax(-1);
      fail("Setting max <= 0 should throw");
    } catch (IllegalArgumentException e) {
    }
  }

  @Test public void setMaxToZero() {
    try {
      button.setMax(0);
      fail("Setting max <= 0 should throw");
    } catch (IllegalArgumentException e) {
    }
  }

  @Test public void onCheckedChangeListenerIsNotified() {
    CompoundButton.OnCheckedChangeListener publisher =
        mock(CompoundButton.OnCheckedChangeListener.class);
    button.setOnCheckedChangeListener(publisher);
    button.setPinned(true);
    verify(publisher).onCheckedChanged(button, true);
    button.setPinned(false);
    verify(publisher).onCheckedChanged(button, false);
  }

  @Test public void onCheckedChangeListenerIsNotifiedOnToggle() {
    button.setPinned(true);
    CompoundButton.OnCheckedChangeListener publisher =
        mock(CompoundButton.OnCheckedChangeListener.class);
    button.setOnCheckedChangeListener(publisher);
    button.toggle();
    verify(publisher).onCheckedChanged(button, false);
  }

  @Test public void onSaveInstanceState() {
    button.setProgress(72);
    button.setMax(842);
    final Parcelable parcelable = button.onSaveInstanceState();
    button.setProgress(2);
    button.setMax(50);
    assertThat(button.getProgress()).isEqualTo(2);
    assertThat(button.getMax()).isEqualTo(50);

    button.onRestoreInstanceState(parcelable);
    assertThat(button.getProgress()).isEqualTo(72);
    assertThat(button.getMax()).isEqualTo(842);
  }

  @Test public void settingMaxLessThanProgress() {
    button.setProgress(25);
    try {
      button.setMax(10);
      fail("Setting max < progress should throw");
    } catch (IllegalArgumentException e) {
    }
  }

  @Test public void settingProgressAndMax() {
    button.setMax(10);

    button.setProgressAndMax(20, 50);
    assertThat(button.getProgress()).isEqualTo(20);
    assertThat(button.getMax()).isEqualTo(50);

    button.setProgressAndMax(2, 5);
    assertThat(button.getProgress()).isEqualTo(2);
    assertThat(button.getMax()).isEqualTo(5);
  }

  @Test public void settingInvalidProgressAndMax() {
    try {
      button.setProgressAndMax(10, 5);
      fail("Setting progress > max should throw");
    } catch (IllegalArgumentException e) {
    }

    try {
      button.setProgressAndMax(0, 0);
      fail("Setting max = 0 should throw");
    } catch (IllegalArgumentException e) {
    }

    try {
      button.setProgressAndMax(-1, 10);
      fail("Setting progress < 0 should throw");
    } catch (IllegalArgumentException e) {
    }
  }
}
