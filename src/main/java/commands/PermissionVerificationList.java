package commands;

import com.google.inject.Inject;
import lombok.val;
import net.dv8tion.jda.core.entities.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Phong on 19/02/2017.
 */
public class PermissionVerificationList {

    private final Set<String> permissionGranted;

    @Inject
    public PermissionVerificationList(){
        this.permissionGranted = new HashSet<>();
        try {
            this.getUserForVerification();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getUserForVerification() throws IOException {
        val inputStream = ClassLoader.getSystemResourceAsStream("config/permissionlist.txt");
        BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = bf.readLine()) != null) {
            this.permissionGranted.add(line);
        }
    }

    public boolean userCheck(User user){
        return this.permissionGranted.contains(user.getId());
    }
}
