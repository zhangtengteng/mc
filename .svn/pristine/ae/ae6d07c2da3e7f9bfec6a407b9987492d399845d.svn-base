package com.chehui.maiche.utils;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Vibrator;
import android.util.DisplayMetrics;

import com.chehui.maiche.R;

/**
 * 
 * @author ztt
 *         <P>
 *         不明白这个APP要播放什么
 *         <P>
 * 
 */
public class DisplayUtils {

	private static final int ALARM_VIBRATOR_TIME = 300;

	private static final int ALARM_TIP_TIME_SPACE = 1000;

	private static DisplayMetrics mDisplayMetrics;

	private static int screenWidth, screenHeight;

	private static SoundPool soundPool;

	private static long lastTipTime;

	public static void setDisplayMetrics(Activity mAcivity) {
		if (null == mDisplayMetrics) {
			mDisplayMetrics = new DisplayMetrics();
			mAcivity.getWindowManager().getDefaultDisplay()
					.getMetrics(mDisplayMetrics);
		}
	}

	public static DisplayMetrics getDisplayMetrics() {
		return mDisplayMetrics;
	}

	public static void initScreenSize(int width, int height) {
		screenWidth = width;
		screenHeight = height;
	}

	public static int getScreenWidth() {
		if (0 == screenWidth) {
			LogN.e(DisplayUtils.class, "getScreenWidth not init");
		}
		return screenWidth;
	}

	public static int getScreenHeight() {
		if (0 == screenHeight) {
			LogN.e(DisplayUtils.class, "getScreenHeight not init");
		}
		return screenHeight;
	}

	public static int getGridImageSize(int totalNum, int column) {
		int size;

		if (totalNum < column) {
			size = screenWidth / (totalNum + 2);
		} else {
			size = screenWidth / (column + 2);
		}

		return size;
	}

	public static void doVibrator(Context context) {
		long currTime = System.currentTimeMillis();

		if (ALARM_TIP_TIME_SPACE > currTime - lastTipTime) {
			return;
		}

		lastTipTime = currTime;

		Vibrator vib = (Vibrator) context
				.getSystemService(Service.VIBRATOR_SERVICE);
		vib.vibrate(ALARM_VIBRATOR_TIME);
	}

	public static void playTipSound(Context context) {
		long currTime = System.currentTimeMillis();

		if (ALARM_TIP_TIME_SPACE > currTime - lastTipTime) {
			return;
		}

		lastTipTime = currTime;

		// 选择的情景模式是正常时才播放声音
		final AudioManager audioService = (AudioManager) context
				.getSystemService(Context.AUDIO_SERVICE);
		if (audioService.getRingerMode() == AudioManager.RINGER_MODE_NORMAL) {

			if (null == soundPool) {
				soundPool = new SoundPool(1, AudioManager.STREAM_NOTIFICATION,
						0);

				soundPool
						.setOnLoadCompleteListener(new OnLoadCompleteListener() {

							@Override
							public void onLoadComplete(SoundPool soundPool,
									int sampleId, int status) {
								float audioMaxVolumn = audioService
										.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION);
								float volumnCurrent = audioService
										.getStreamVolume(AudioManager.STREAM_NOTIFICATION);
								float volumnRatio = volumnCurrent
										/ audioMaxVolumn;

								soundPool.play(sampleId, volumnRatio,
										volumnRatio, 1, 0, 1f);
							}
						});
			}

			soundPool.load(context, R.raw.sound, 1);
		}
	}
}
