package binarytree;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BinaryTreeView extends JPanel {
    private BinaryTree tree;
    private List<Node> traversalPath;
    private int currentStep;

    public BinaryTreeView(BinaryTree tree) {
        this.tree = tree;
        this.traversalPath = new ArrayList<>();
        this.currentStep = -1;
        setPreferredSize(new Dimension(800, 600));
        traverseTree(tree.getRoot());
    }

    private void traverseTree(Node node) {
        if (node != null) {
            traversalPath.add(node);
            traverseTree(node.left);
            traverseTree(node.right);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawTree(g, tree.getRoot(), getWidth() / 2, 50, getWidth() / 4);
    }

    private void drawTree(Graphics g, Node node, int x, int y, int gap) {
        if (node != null) {
            if (traversalPath.indexOf(node) == currentStep) {
                g.setColor(Color.RED);
            } else {
                g.setColor(Color.BLACK);
            }
            g.fillOval(x - 15, y - 15, 30, 30);
            g.setColor(Color.WHITE);
            g.drawString(Integer.toString(node.value), x - 7, y + 5);
            g.setColor(Color.BLACK);

            if (node.left != null) {
                g.drawLine(x, y + 15, x - gap, y + 50 - 15);
                drawTree(g, node.left, x - gap, y + 50, gap / 2);
            }
            if (node.right != null) {
                g.drawLine(x, y + 15, x + gap, y + 50 - 15);
                drawTree(g, node.right, x + gap, y + 50, gap / 2);
            }
        }
    }

    public void highlightNextNode() {
        currentStep++;
        if (currentStep < traversalPath.size()) {
            repaint();
            Timer timer = new Timer(1000, e -> highlightNextNode());
            timer.setRepeats(false);
            timer.start();
        }
    }

    public static void createAndShowGUI(BinaryTree tree) {
        JFrame frame = new JFrame("Binary Tree Visualization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BinaryTreeView treeView = new BinaryTreeView(tree);
        frame.add(treeView);
        frame.pack();
        frame.setVisible(true);
        treeView.highlightNextNode();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BinaryTree tree = new BinaryTree();
            tree.add(50);
            tree.add(30);
            tree.add(70);
            tree.add(20);
            tree.add(40);
            tree.add(60);
            tree.add(80);

            createAndShowGUI(tree);
        });
    }
}
