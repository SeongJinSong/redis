package spring.redis.talk;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RoomMemberRepositoryTest {
    @Autowired RoomMemberRepository roomMemberRepository;
    @Autowired
    EntityManager em;

    @BeforeEach
    void beforeEach(){
        roomMemberRepository.save(new RoomMember("a", "1", 0L));
    }

    @Test
    void updateFindCount(){
        Optional<RoomMember> roomMemberOptional = roomMemberRepository.findByRoomIdAndUserId("a", "1");
        if(roomMemberOptional.isEmpty()){
            fail(new EntityNotFoundException());
        }
        RoomMember roomMember = roomMemberOptional.get();
        log.info("#count:{}", roomMember.getFindCount());

        roomMemberRepository.updateFindCount("a", "1");
        em.flush();
        em.clear();

        Optional<RoomMember> updateRoomMemberOptional = roomMemberRepository.findByRoomIdAndUserId("a", "1");
        if(updateRoomMemberOptional.isEmpty()){
            fail(new EntityNotFoundException());
        }
        RoomMember updateRoomMember = updateRoomMemberOptional.get();
        log.info("##count:{}", updateRoomMember.getFindCount());
        assertEquals(updateRoomMember.getFindCount(), 1);
    }
}