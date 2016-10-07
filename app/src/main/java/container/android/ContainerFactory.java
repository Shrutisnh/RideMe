package container.android;


import container.common.Environment;
import container.loader.WebServicesHandler;

/**
 * Created by G09561636 on 8/11/2016.
 */
public class ContainerFactory implements container.common.ContainerFactory {

    private static WebServicesHandler handler;
    private static PostRequest postRequest;
    @Override
    public Container getContainer(container.common.Environment environment) throws Exception {
        return new container.android.Container(environment);
    }

    @Override
    public WebServicesHandler getWebServicesHandler() {
        if(handler==null){
            handler=new WebServicesHandler();
        }
        return handler;
    }

    @Override
    public  PostRequest getPostRequest() {
        if(postRequest==null)
       postRequest=new PostRequest();

        return postRequest;
    }

}
