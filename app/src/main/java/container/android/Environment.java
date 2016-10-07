package container.android;

import container.common.*;
import container.common.ContainerFactory;

/**
 * Created by G09561636 on 8/16/2016.
 */
public class Environment extends container.common.Environment{

    public Environment(String url){
        super(url);
    }
    private container.android.ContainerFactory factory;
    @Override
    public ContainerFactory getContainerFactory() {
        if(factory==null){
            factory=new container.android.ContainerFactory();

        }
        return factory;
    }
}
