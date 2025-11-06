package dev.dmbiee.securenote.features.friend;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/friends")
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

    // 🔸 Отримати своїх друзів
    @GetMapping
    public List<Friend> getMyFriends(Authentication authentication) {
        String username = authentication.getName();
        return friendService.getMyFriends(username);
    }

    // 🔸 Отримати користувачів, у кого я в друзях
    @GetMapping("/who-added-me")
    public List<Friend> getWhoAddedMe(Authentication authentication) {
        String username = authentication.getName();
        return friendService.getUsersWhoAddedMe(username);
    }

    // 🔸 Додати друга
    @PostMapping("/{friendUsername}")
    public Friend addFriend(@PathVariable String friendUsername, Authentication authentication) {
        String username = authentication.getName();
        return friendService.addFriend(username, friendUsername);
    }

    // 🔸 Видалити друга
    @DeleteMapping("/{friendUsername}")
    public void deleteFriend(@PathVariable String friendUsername, Authentication authentication) {
        String username = authentication.getName();
        friendService.deleteFriend(username, friendUsername);
    }
}
