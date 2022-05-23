package company.policy.client.manager.dll;

import company.policy.client.core.PolicyQuestionModel;

public class Node {

    private final PolicyQuestionModel element;

    public Node prev, next;

    public Node(PolicyQuestionModel el, Node prev, Node next) {
        this.element = el;
        this.prev = prev;
        this.next = next;
    }

    public PolicyQuestionModel getElement() {
        return element;
    }
}
