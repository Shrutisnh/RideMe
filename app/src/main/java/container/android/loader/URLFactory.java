package container.android.loader;

/**
 * Created by G09561636 on 8/12/2016.
 */
public class URLFactory {
    private String url="";

    public URLFactory(String url){
        setUrl(url);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
