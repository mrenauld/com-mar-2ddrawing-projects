package com.mar.drawing.projects.mandel;

public class AlgebraicExpression {

	private TreeNode tree;

	public AlgebraicExpression(final String s) {
		parseString(s);
		clearEmptyNodes(tree);
		displayExpression();
	}

	public void displayExpression() {
		final String s = tree.display();
		System.out.println(s);
	}

	public Complex eval(final Complex z) {
		return this.eval(tree, z);
	}

	public int getMaxExp() {
		// TODO
		return 2;
	}

	private void clearEmptyNodes(final TreeNode node) {
		for (int i = 0; i < node.children.size(); ++i) {
			clearEmptyNodes(node.children.get(i));
		}

		if (node.children.size() == 0 && node.value == null
				&& node.operation == TreeNode.OP_NOOP) {
			final TreeNode parent = node.parent;
			if (parent != null) {
				parent.children.remove(node);
			}
		}
	}

	private Complex eval(final TreeNode node, final Complex z) {

		final Complex v = new Complex(0.0, 0.0);
		int currentOp = TreeNode.OP_ADDITION;
		for (int i = 0; i < node.children.size(); ++i) {
			final TreeNode child = node.children.get(i);
			if (child.isValue()) {
				op(v, currentOp, child.value);
			} else if (child.operation == TreeNode.OP_Z) {
				op(v, currentOp, z);
			} else if (child.isOperation()) {
				final int op = child.operation;
				if (op == TreeNode.OP_ADDITION || op == TreeNode.OP_SUBTRACTION
						|| op == TreeNode.OP_TIMES || op == TreeNode.OP_DIVIDED
						|| op == TreeNode.OP_POWER) {
					currentOp = op;
				} else if (op == TreeNode.OP_EXP || op == TreeNode.OP_SIN
						|| op == TreeNode.OP_COS) {
					final Complex vTemp = eval(child, z);
					op(vTemp, op, null);
					op(v, currentOp, vTemp);
				}
			} else if (child.isParent()) {
				op(v, currentOp, eval(child, z));
			}
		}

		return v;
	}

	private void op(final Complex a, final int op, final Complex b) {
		if (op == TreeNode.OP_ADDITION) {
			a.add(b);
		} else if (op == TreeNode.OP_SUBTRACTION) {
			a.subtract(b);
		} else if (op == TreeNode.OP_TIMES) {
			a.times(b);
		} else if (op == TreeNode.OP_DIVIDED) {
			a.divide(b);
		} else if (op == TreeNode.OP_POWER) {
			a.power((int) b.a);
		} else if (op == TreeNode.OP_EXP) {
			a.exp();
		} else if (op == TreeNode.OP_SIN) {
			a.sin();
		} else if (op == TreeNode.OP_COS) {
			a.cos();
		}
	}

	private void parseString(final String s) {
		tree = new TreeNode();

		String term = "";
		TreeNode parent = tree;
		TreeNode leaf = null;
		for (int i = 0; i < s.length(); ++i) {
			final char c = s.charAt(i);

			if (c == ' ') {
				leaf = processTerm(term, parent);
				term = "";
			} else if (c == '+') {
				processTerm(term, parent);
				term = "";
				leaf = new TreeNode();
				leaf.operation = TreeNode.OP_ADDITION;
				leaf.parent = parent;
				parent.children.add(leaf);
			} else if (c == '-') {
				processTerm(term, parent);
				term = "";
				leaf = new TreeNode();
				leaf.operation = TreeNode.OP_SUBTRACTION;
				leaf.parent = parent;
				parent.children.add(leaf);
			} else if (c == '*') {
				processTerm(term, parent);
				term = "";
				leaf = new TreeNode();
				leaf.operation = TreeNode.OP_TIMES;
				leaf.parent = parent;
				parent.children.add(leaf);
			} else if (c == '/') {
				processTerm(term, parent);
				term = "";
				leaf = new TreeNode();
				leaf.operation = TreeNode.OP_DIVIDED;
				leaf.parent = parent;
				parent.children.add(leaf);
			} else if (c == '^') {
				processTerm(term, parent);
				term = "";
				leaf = new TreeNode();
				leaf.operation = TreeNode.OP_POWER;
				leaf.parent = parent;
				parent.children.add(leaf);
			} else if (c == '(') {
				final TreeNode tempLeaf = processTerm(term, parent);
				term = "";
				if (tempLeaf != null) {
					leaf = tempLeaf;
				}
				parent = leaf;
			} else if (c == ')') {
				leaf = processTerm(term, parent);
				term = "";
				parent = parent.parent;
			} else {
				term += c;
			}
		}

		if (!term.isEmpty()) {
			processTerm(term, parent);
		}
	}

	private TreeNode processTerm(final String term, final TreeNode node) {

		final TreeNode leaf = new TreeNode();

		boolean isOp = false;
		for (int i = 0; i < TreeNode.OPS.length; ++i) {
			if (TreeNode.OPS[i].equals(term)) {
				leaf.operation = i;
				isOp = true;
				break;
			}
		}

		if (term.isEmpty()) {
			leaf.operation = TreeNode.OP_NOOP;
		} else {
			if (isOp == false) {
				Complex v;
				final char lastChar = term.charAt(term.length() - 1);
				if (lastChar != 'i') {
					final double d = Double.parseDouble(term);
					v = new Complex(d, 0.0);
				} else {
					final String ds = term.substring(0, term.length() - 1);
					double d = 1.0;
					if (!ds.isEmpty()) {
						d = Double.parseDouble(ds);
					}
					v = new Complex(0.0, d);
				}
				leaf.value = v;
			}
		}

		leaf.parent = node;
		node.children.add(leaf);

		return leaf;
	}

}