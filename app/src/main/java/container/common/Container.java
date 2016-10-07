package container.common;


import container.exceptions.ContainerInitializationException;

/**
 * Created by G09561636 on 8/11/2016.
 */
public class Container {
    private static Container container;
    private Environment environment;

    public static Container initialize(Environment environment)throws Exception{
        if(container!=null){
            throw new ContainerInitializationException("Container already present");
        }
        container= environment.getContainerFactory().getContainer(environment);
        return container;
    }
    public static Container getInstance(){
        if(container==null){
            throw new ContainerInitializationException("Container not initialised");
        }
        return container;
    }

    public ContainerFactory getFactory(){
        return environment.getContainerFactory();
    }
    protected Container(Environment environment){
        setEnvironment(environment);
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
