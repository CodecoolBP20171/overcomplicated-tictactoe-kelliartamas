package avatar.controller;

import avatar.model.Avatar;
import org.springframework.web.bind.annotation.*;

@RestController
public class AvatarController {

    private String avatarFlag = "adorable"; // "adorable"(default) or "robohash"

    @GetMapping("/avatar")
    public Avatar getAvatar(@ModelAttribute("avatarString") String avatarString) {
        String url = "";
        switch (avatarFlag) {
            case "adorable":
                url = "https://api.adorable.io/avatars/" + avatarString;
                break;
            case "robohash":
                url = "https://robohash.org/" + avatarString + "?set=set4";
                break;
            default:
                url = "https://api.adorable.io/avatars/" + avatarString;
                break;
        }
        return new Avatar(url);
    }
}
