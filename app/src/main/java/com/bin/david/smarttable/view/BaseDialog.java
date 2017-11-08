package com.bin.david.smarttable.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

import com.bin.david.smarttable.R;


public class BaseDialog extends Dialog {

	private static final int INVALID = 0;
	private Builder builder;

	private BaseDialog(Builder builder) {
		super(builder.context, builder.dialogStyle);
		this.builder = builder;
		Window window = this.getWindow();
		window.setGravity(builder.gravity);
		if (builder.animStyle == INVALID) {
			switch (builder.gravity) {
			case Gravity.CENTER:
				window.setWindowAnimations(R.style.center_dialog_animation);
				break;
			case Gravity.BOTTOM:
				window.setWindowAnimations(R.style.bottom_dialog_animation);
				break;
				case Gravity.TOP:
					window.setWindowAnimations(R.style.top_dialog_animation);
				break;
			default:
				break;
			}
		} else {
			window.setWindowAnimations(builder.animStyle);
		}
		LayoutParams params = window.getAttributes();
		window.getDecorView().setPadding(0, 0, 0,0);
		Rect rect = getScreen((Activity) builder.context);
		if(builder.isFillHeight){
			params.height = rect.getHeight()- (builder.margin != null? builder.margin[1] + builder.margin[3] :0);
		}else {
			if(builder.height  == 0) {
				params.height = LayoutParams.WRAP_CONTENT;
			}else{
				params.height = builder.height;
			}
		}
		if(builder.isFillWidth){
			params.width = rect.getWidth()- (builder.margin != null ? builder.margin[0] + builder.margin[2] :0);
		}else{
			if(builder.width == 0) {
				params.width = (int) (rect.getWidth() * 0.5);
			}else{
				params.width = builder.width;
			}
		}
		getWindow().setAttributes(params);
		onWindowAttributesChanged(params);
		if(builder.contentView != null){
			setContentView(builder.contentView);
		}
		this.setCanceledOnTouchOutside(true);
	}

	/**
	 * 重新计算dialog的高度
	 * @param isFillHeight
	 */
	public void notifyHeight(boolean isFillHeight){
		Window window = this.getWindow();
		Rect rect = getScreen((Activity) builder.context);
		LayoutParams params = window.getAttributes();
		if(isFillHeight){
			params.height = rect.getHeight()- (builder.margin != null? builder.margin[1] + builder.margin[3] :0);
		}else {
			if(builder.height  == 0) {
				params.height = LayoutParams.WRAP_CONTENT;
			}else{
				params.height = builder.height;
			}
		}
		getWindow().setAttributes(params);
		onWindowAttributesChanged(params);
	}

	public void show(View contentView){
		setContentView(contentView);
		show();
	}

	@Override
	public void show() {
		if(builder != null) {
			if (builder.context != null && builder.context instanceof Activity) {
				Activity activity = (Activity) builder.context;
				if(!activity.isFinishing()) {
					super.show();
				}
			}
		}
	}

	@Override
	public void dismiss() {
		if(builder != null) {
			if (builder.context != null && builder.context instanceof Activity) {
				Activity activity = (Activity) builder.context;
				if (!activity.isFinishing()) {
					super.dismiss();
				}
			}
		}
	}

	/**
	 * 获取分辨率
	 */
	public static Rect getScreen(Activity activity) {

		int displayWidth = 0, displayHeight = 0;
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		displayWidth = dm.widthPixels;
		displayHeight = dm.heightPixels;
		Rect rect = new Rect(displayWidth, displayHeight);
		return rect;
	}

	public static class Builder {
		private Context context;
		private int gravity = Gravity.CENTER;
		private int animStyle;
		private int dialogStyle = R.style.base_dialog_style;
		private boolean isFillWidth;
		private boolean isFillHeight;
		private View contentView;
		private int width;
		private int height;
		private int margin[];

		public Builder(Context context) {
			this.context = context;
			int margin = 60;
			this.margin = new int[4];
			this.margin[0] = margin;
			this.margin[1] = margin;
			this.margin[2] = margin;
			this.margin[3] = margin;
		}

		public Builder setGravity(int gravity) {
			this.gravity = gravity;
			return this;
		}

		public Builder setAnimStyle(int animStyle) {
			this.animStyle = animStyle;
			return this;
		}

		public Builder setDialogStyle(int dialogStyle) {
			this.dialogStyle = dialogStyle;
			return this;
		}

		public Builder setFillWidth(boolean isFillWidth) {
			this.isFillWidth = isFillWidth;
			return this;
		}
		public Builder setFillHeight(boolean isFillHeight) {
			this.isFillHeight = isFillHeight;
			return this;
		}
		public Builder setContentView(View contentView) {
			this.contentView = contentView;
			return this;
		}

		public Builder setWidth(int width) {
			this.width = width;
			return this;
		}

		public Builder setHeight(int height) {
			this.height = height;
			return this;
		}
		public Builder setMargin(int left, int top, int right, int bottom) {
			this.margin = new int[4];
			margin[0] = left;
			margin[1] = top;
			margin[2] = right;
			margin[3] = bottom;
			return this;
		}

		public BaseDialog create() {
			return new BaseDialog(this);
		}

	}
	/**
	 * 矩形(长.宽)
	 */
	public static class Rect {
		private int width;
		private int height;

		public Rect() {
		}

		public Rect(int width, int height) {
			this.width = width;
			this.height = height;
		}

		public int getWidth() {
			return width;
		}

		public void setWidth(int width) {
			this.width = width;
		}

		public int getHeight() {
			return height;
		}

		public void setHeight(int height) {
			this.height = height;
		}
	}

}
