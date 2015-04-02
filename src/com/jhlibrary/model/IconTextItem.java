package com.jhlibrary.model;

/**
 * Icon Resource와 String을 가지는 모델.
 * ListView에서 사용한다.
 * @author accenture
 *
 */
public class IconTextItem {
	public String tag;
	public int iconRes;

	public IconTextItem(String tag, int iconRes) {
		this.tag = tag;
		this.iconRes = iconRes;
	}
	public IconTextItem(String tag) {
		this.tag = tag;
		this.iconRes = -1;
	}
}
