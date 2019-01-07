package picshare.storage.config;


import com.kumuluz.ee.configuration.cdi.ConfigBundle;
import com.kumuluz.ee.configuration.cdi.ConfigValue;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@ConfigBundle("storage")
public class StorageConfig {

    private String imagefolder;
    @ConfigValue(watch = true)
    private boolean disablestorage;

    public String getImagefolder() {
        return imagefolder;
    }

    public void setImagefolder(String imagefolder) {
        this.imagefolder = imagefolder;
    }

    public boolean isDisablestorage() {
        return disablestorage;
    }

    public void setDisablestorage(boolean disablestorage) {
        this.disablestorage = disablestorage;
    }
}