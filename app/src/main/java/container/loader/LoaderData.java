package container.loader;

/**
 * Created by G09561636 on 8/11/2016.
 */
public class LoaderData {
    private String key;
    private String value;

    public LoaderData(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
