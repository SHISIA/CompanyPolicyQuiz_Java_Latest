package company.policy.client.manager.bt;

import company.policy.client.core.PolicyQuestionModel;

public class Node {

    PolicyQuestionModel value;
    Node left;
    Node right;

    Node(PolicyQuestionModel value) {
        this.value = value;
        right = null;
        left = null;
    }
}
