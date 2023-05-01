package ru.practicum.shareit.request.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ItemRequestRepositoryTest {

    @Autowired
    private ItemRequestRepository itemRequestRepository;

    @Autowired
    private UserRepository userRepository;

    private User requester1;
    private User requester2;
    private ItemRequest itemRequest1;
    private ItemRequest itemRequest2;
    private ItemRequest itemRequest3;

    @BeforeAll
    void beforeAll() {
        requester1 = new User(1L, "user1", "mail1@ya.ru");
        requester2 = new User(2L, "user2", "mail2@ya.ru");
        userRepository.save(requester1);
        userRepository.save(requester2);
        itemRequest1 = new ItemRequest(1L, "req1", requester1, LocalDateTime.now());
        itemRequest2 = new ItemRequest(2L, "req2", requester2, LocalDateTime.now());
        itemRequest3 = new ItemRequest(3L, "req3", requester1, LocalDateTime.now());
        itemRequestRepository.save(itemRequest1);
        itemRequestRepository.save(itemRequest2);
        itemRequestRepository.save(itemRequest3);
    }

    @Test
    void findAllByRequesterIdOrderByCreatedDesc_userWithoutRequests() {
        List<ItemRequest> res = itemRequestRepository
                .findAllByRequesterIdOrderByCreatedDesc(999L);

        assertEquals(res.size(), 0);
    }

}