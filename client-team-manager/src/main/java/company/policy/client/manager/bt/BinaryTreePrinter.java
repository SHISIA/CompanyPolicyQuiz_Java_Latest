package company.policy.client.manager.bt;

import java.io.PrintStream;

public class BinaryTreePrinter {

    private final Node node;

    public BinaryTreePrinter(Node node) {
        this.node = node;
    }

    private String traversePreOrder(Node node) {

        if (node == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(node.value.getPolicyQuestionNumber());

        String pointerRight = "└──";
        String pointerLeft = (node.right != null) ? "├──" : "└──";

        traverseNodes(sb, "", pointerLeft, node.left, node.right != null);
        traverseNodes(sb, "", pointerRight, node.right, false);

        return sb.toString();
    }

    private void traverseNodes(StringBuilder sb, String padding, String pointer, Node node,
            boolean hasRightSibling) {

        if (node != null) {

            sb.append("\n");
            sb.append(padding);
            sb.append(pointer);
            sb.append(node.value.getPolicyQuestionNumber());

            StringBuilder paddingBuilder = new StringBuilder(padding);
            if (hasRightSibling) {
                paddingBuilder.append("│  ");
            } else {
                paddingBuilder.append("   ");
            }

            String paddingForBoth = paddingBuilder.toString();
            String pointerRight = "└──";
            String pointerLeft = (node.right != null) ? "├──" : "└──";

            traverseNodes(sb, paddingForBoth, pointerLeft, node.left, node.right != null);
            traverseNodes(sb, paddingForBoth, pointerRight, node.right, false);

        }

    }

    public void print(PrintStream os) {
        os.print(traversePreOrder(node));
    }

    public String print() {
        return traversePreOrder(node);
    }

}
