package com.thg.accelerator23.connectn.ai.rosselanor;

public class Move {

	private int column;
	private int value;

	public Move() {
	}

	public Move(Integer col, int value) {
		if (col != null) {
			this.column = col;
		}
		this.value = value;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int col) {
		this.column = col;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}