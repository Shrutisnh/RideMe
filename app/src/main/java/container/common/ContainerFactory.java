package container.common;


import container.loader.PostRequest;
import container.loader.WebServicesHandler;

/**
 * Created by G09561636 on 8/11/2016.
 */
public interface ContainerFactory {

    public Container getContainer(Environment environment)throws Exception;
    public WebServicesHandler getWebServicesHandler();
    public PostRequest getPostRequest();
}
