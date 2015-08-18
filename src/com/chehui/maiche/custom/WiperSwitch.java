package com.chehui.maiche.custom;

import com.chehui.maiche.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import cn.jpush.android.api.JPushInterface;

public class WiperSwitch extends View implements OnTouchListener {
	private Bitmap btOn, btOff, slipperBtn;
	/**
	 * 按下时的x和当前的x
	 */
	private float downX, nowX;

	/**
	 * 记录用户是否在滑动
	 */
	private boolean onSlip = false;

	/**
	 * 当前的状态̬
	 */
	private boolean nowStatus = false;

	/**
	 * 监听接口
	 */
	private OnChangedListener listener;

	public WiperSwitch(Context context) {
		super(context);
		init();
	}

	public WiperSwitch(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		btOn = BitmapFactory.decodeResource(getResources(), R.drawable.on_btn);
		btOff = BitmapFactory.decodeResource(getResources(), R.drawable.off_btn);
		slipperBtn = BitmapFactory.decodeResource(getResources(), R.drawable.white_btn);
		setOnTouchListener(this);
		setChecked(true);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 3*3的矩阵
		Matrix matrix = new Matrix();
		Paint paint = new Paint();
		float x = 0;
		System.out.println("nowX=" + nowX);
		System.out.println("--------------=" + btOn.getWidth() / 2);
		if (nowX < (btOn.getWidth() / 2 - 13)) {
			// 画出关闭时的背景
			canvas.drawBitmap(btOff, matrix, paint);
			JPushInterface.stopPush(getContext());
		} else {
			// 画出打开时的背景
			canvas.drawBitmap(btOn, matrix, paint);
			JPushInterface.resumePush(getContext());
		}

		if (onSlip) {// 是否是在滑动状态,
			if (nowX >= btOn.getWidth()) {// 是否划出指定范围,不能让滑块跑到外头,必须做这个判断

				x = btOn.getWidth() - slipperBtn.getWidth() / 2;// 减去滑块1/2的长度
			} else {
				x = nowX - slipperBtn.getWidth() / 2;
			}
		} else {
			if (nowStatus) {// 根据当前的状态设置滑块的x值ֵ
				x = btOn.getWidth() - slipperBtn.getWidth();
			} else {
				x = 0;
			}
		}

		// 对滑块滑动进行异常处理，不能让滑块出界
		if (x < 0) {
			x = 0;
		} else if (x > btOn.getWidth() - slipperBtn.getWidth()) {
			x = btOn.getWidth() - slipperBtn.getWidth();
		}
		// 画出滑块

		canvas.drawBitmap(slipperBtn, x + 2, 0, paint);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: {
			if (event.getX() > btOff.getWidth() || event.getY() > btOff.getHeight()) {
				return false;
			} else {
				onSlip = true;
				downX = event.getX();
				nowX = downX;
			}
			break;
		}
		case MotionEvent.ACTION_MOVE: {
			nowX = event.getX();
			break;
		}
		case MotionEvent.ACTION_UP: {
			onSlip = false;
			if (event.getX() >= (btOn.getWidth() / 2)) {
				nowStatus = true;
				nowX = btOn.getWidth() - slipperBtn.getWidth();
			} else {
				nowStatus = false;
				nowX = 0;
			}

			if (listener != null) {
				listener.OnChanged(WiperSwitch.this, nowStatus);
			}
			break;
		}
		}
		// 刷新界面
		invalidate();
		return true;
	}

	/**
	 * 设置滑动开关的初始状态，供外部调用 `
	 * 
	 * @param checked
	 */
	public void setChecked(boolean checked) {
		if (checked) {
			nowX = btOff.getWidth();
		} else {
			nowX = 0;
		}
		invalidate();
		nowStatus = checked;
	}

	/**
	 * 为WiperSwitch设置一个监听，供外部调用的方法
	 * 
	 * @param listener
	 */
	public void setOnChangedListener(OnChangedListener listener) {
		this.listener = listener;
	}

	/**
	 * 回调接口
	 * 
	 * @author len
	 * 
	 */
	public interface OnChangedListener {
		public void OnChanged(WiperSwitch wiperSwitch, boolean checkState);
	}

}
