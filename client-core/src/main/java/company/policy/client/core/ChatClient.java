package company.policy.client.core;

public interface ChatClient {

    void stop();

    void handle(String msg);

}
