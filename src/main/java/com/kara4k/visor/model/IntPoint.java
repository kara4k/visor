package com.kara4k.visor.model;

import java.awt.*;

public class IntPoint {

	private int x;
	private int y;
	private Color color;

	public IntPoint(final int x, final int y) {
		this.x = x;
		this.y = y;
	}

	public IntPoint(final int x, final int y, final Color color) {
		this.x = x;
		this.y = y;
		this.color = color;
	}

	public int getX() {
		return x;
	}

	public void setX(final int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(final int y) {
		this.y = y;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(final Color color) {
		this.color = color;
	}

}
