package com.f2prateek.progressbutton;

import android.app.Activity;
import android.widget.CompoundButton;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
public class ProgressButtonTest {
  private Activity activity;
  private ProgressButton button;

  @Before
  public void setUp() throws Exception {
    activity = Robolectric.buildActivity(Activity.class).create().get();
    button = new ProgressButton(activity);
  }

  @Test
  public void testInit() throws Exception {
    assertThat(button).isNotNull();
  }

  @Test
  public void testPin() throws Exception {
    button.setPinned(false);
    assertThat(button.isChecked()).isEqualTo(button.isPinned()).isFalse();

    button.setPinned(true);
    assertThat(button.isChecked()).isEqualTo(button.isPinned()).isTrue();
  }

  @Test
  public void testToggle() throws Exception {
    // Setup
    button.setPinned(false);
    assertThat(button.isChecked()).isEqualTo(button.isPinned()).isFalse();

    // Toggle twice and verify each time
    button.toggle();
    assertThat(button.isChecked()).isEqualTo(button.isPinned()).isTrue();
    button.toggle();
    assertThat(button.isChecked()).isEqualTo(button.isPinned()).isFalse();
  }

  @Test
  public void testValidProgressValueZero() throws Exception {
    button.setProgress(0);
    assertThat(button.getProgress()).isEqualTo(0);
  }

  @Test
  public void testValidProgressValueMax() throws Exception {
    button.setProgress(100);
    assertThat(button.getProgress()).isEqualTo(100);
  }

  @Test
  public void testInvalidProgressValue() throws Exception {
    Exception exception = null;
    try {
      button.setProgress(101);
    } catch (Exception e) {
      exception = e;
    }

    assertThat(exception).isNotNull()
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Progress (101) must be between 0 and 100");
  }

  @Test
  public void testAnotherInvalidProgressValue() throws Exception {
    Exception exception = null;
    try {
      button.setProgress(-1);
    } catch (Exception e) {
      exception = e;
    }

    assertThat(exception).isNotNull()
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Progress (-1) must be between 0 and 100");
  }

  @Test
  public void testSetMaxToUnderZero() throws Exception {
    Exception exception = null;
    try {
      button.setMax(-1);
    } catch (Exception e) {
      exception = e;
    }

    assertThat(exception).isNotNull()
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Max (-1) must be > 0");
  }

  @Test
  public void testSetMaxToZero() throws Exception {
    Exception exception = null;
    try {
      button.setMax(0);
    } catch (Exception e) {
      exception = e;
    }

    assertThat(exception).isNotNull()
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Max (0) must be > 0");
  }

  @Test
  public void testOnCheckedChangeListenerIsNotified() throws Exception {
    CompoundButton.OnCheckedChangeListener publisher =
        mock(CompoundButton.OnCheckedChangeListener.class);
    button.setOnCheckedChangeListener(publisher);
    button.setPinned(true);
    verify(publisher).onCheckedChanged(button, true);
    button.setPinned(false);
    verify(publisher).onCheckedChanged(button, false);
  }

  @Test
  public void testOnCheckedChangeListenerIsNotifiedOnToggle() throws Exception {
    button.setPinned(true);
    CompoundButton.OnCheckedChangeListener publisher =
        mock(CompoundButton.OnCheckedChangeListener.class);
    button.setOnCheckedChangeListener(publisher);
    button.toggle();
    verify(publisher).onCheckedChanged(button, false);
  }
}