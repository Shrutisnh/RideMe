package container.common;

/**
 * Created by G09561636 on 8/11/2016.
 */
public abstract class Environment {
    private String url;
    public abstract ContainerFactory getContainerFactory();

    public Environment(String url){
        setUrl(url);
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
