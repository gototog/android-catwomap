package gotocorp.catwomapp2;

import android.app.Application;
import gotocorp.catwomapp2.entity.User;
/**
 * Created by goto on 16/02/16.
 */
public class MyGlobalCatwoman extends Application {

    private User userConnected;

    public User getUserConnected() {
        return userConnected;
    }

    public void setUserConnected(User userConnected) {
        this.userConnected = userConnected;
    }
}
