package container.loader;

import container.common.Container;

/**
 * Created by G09561636 on 8/12/2016.
 */
public abstract class PostRequest {
    private Container container;
    public PostRequest(){
        container=Container.getInstance();
    }

    public abstract Object load(RequestData reqData);
}
