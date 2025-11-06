package dev.dmbiee.securenote.features.friend;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendRepository friendRepository;

    public Friend addFriend(String user, String friendUsername) {
        if (user.equals(friendUsername)) {
            throw new RuntimeException("You cannot add yourself as a friend");
        }

        if (friendRepository.findByUserAndFriend(user, friendUsername).isPresent()) {
            throw new RuntimeException("This user is already in your friend list");
        }

        Friend friend = Friend.builder()
                .user(user)
                .friend(friendUsername)
                .build();

        return friendRepository.save(friend);
    }

    public void deleteFriend(String user, String friendUsername) {
        Friend existing = friendRepository.findByUserAndFriend(user, friendUsername)
                .orElseThrow(() -> new RuntimeException("Friend not found"));

        friendRepository.delete(existing);
    }

    public List<Friend> getMyFriends(String user) {
        return friendRepository.findByUser(user);
    }

    public List<Friend> getUsersWhoAddedMe(String user) {
        return friendRepository.findByFriend(user);
    }
}
