package picshare.storage.config;


import com.kumuluz.ee.configuration.cdi.ConfigBundle;
import com.kumuluz.ee.configuration.cdi.ConfigValue;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@ConfigBundle("storage")
public class StorageConfig {

    private String imagefolder;
    @ConfigValue(watch = true)
    private String disablestorage;

    public String getImagefolder() {
        return imagefolder;
    }

    public void setImagefolder(String imagefolder) {
        this.imagefolder = imagefolder;
    }

    public String isDisablestorage() {
        return disablestorage;
    }

    public void setDisablestorage(String disablestorage) {
        this.disablestorage = disablestorage;
    }
}