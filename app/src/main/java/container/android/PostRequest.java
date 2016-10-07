package container.android;

import container.android.loader.URLFactory;
import container.common.*;
import container.loader.RequestData;

/**
 * Created by G09561636 on 8/12/2016.
 */
public class PostRequest extends container.loader.PostRequest{
    private container.common.Environment environment;
    private URLFactory urlFactory;
    public PostRequest(){
        environment= container.android.Container.getInstance().getEnvironment();
        urlFactory=new URLFactory(environment.getUrl());
    }
    @Override
    public Object load(RequestData reqData) {
        return null;
    }

    private void setUrlFactory(RequestData reqData){
        String url=reqData.getUrl();

    }
}
