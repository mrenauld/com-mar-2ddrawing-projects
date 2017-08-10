package com.mar.drawing.projects.mandel;

import java.util.ArrayList;

public class TreeNode {

	public static final String[] OPS = { "~", "z", "+", "-", "*", "/", "^",
			"exp", "sin", "cos" };

	public static final int OP_NOOP = 0;
	public static final int OP_Z = 1;
	public static final int OP_ADDITION = 2;
	public static final int OP_SUBTRACTION = 3;
	public static final int OP_TIMES = 4;
	public static final int OP_DIVIDED = 5;
	public static final int OP_POWER = 6;
	public static final int OP_EXP = 7;
	public static final int OP_SIN = 8;
	public static final int OP_COS = 9;

	public int operation;
	public Complex value;
	public TreeNode parent;
	public final ArrayList<TreeNode> children = new ArrayList<TreeNode>();

	public TreeNode() {

	}

	public String display() {
		final StringBuilder sb = new StringBuilder();
		display(sb, "");
		return sb.toString();
	}

	public boolean isOperation() {
		if (operation != OP_NOOP) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isParent() {
		if (!isOperation() && !isValue()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isValue() {
		return (value != null);
	}

	private void display(final StringBuilder sb, final String prefix) {
		sb.append(prefix);
		if (operation == 0 && value == null) {
			sb.append("Parent node\r\n");
		} else if (operation != 0) {
			sb.append("Op: " + OPS[operation] + "\r\n");
		} else {
			sb.append("Value: " + value.a);
			if (value.b >= 0) {
				sb.append("+");
			}
			sb.append(value.b + "i\r\n");
		}
		for (int i = 0; i < children.size(); ++i) {
			children.get(i).display(sb, prefix + "-");
		}
	}

}