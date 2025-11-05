package dev.dmbiee.securenote.features.friend;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    List<Friend> findByUser(String user);

    List<Friend> findByFriend(String friend);

    Optional<Friend> findByUserAndFriend(String user, String friend);
}