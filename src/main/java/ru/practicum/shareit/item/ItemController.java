package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.item.dto.AnswerItemDto;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;

import javax.validation.Valid;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<ItemDto> createItem(@RequestBody @Valid ItemDto itemDto,
                                     @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("POST /items : user ID {} creates item from DTO - {}", userId, itemDto);
        return ResponseEntity.ok().body(itemService.createItem(userId, itemDto));
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<ItemDto> updateItem(@PathVariable("itemId") Long itemId,
                              @RequestBody ItemDto itemDto,
                              @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("PATCH /items/{} : update item by ID from user ID {}, item DTO - {}", itemId, userId, itemDto);
        return ResponseEntity.ok().body(itemService.updateItem(itemId, itemDto, userId));
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<AnswerItemDto> getItem(@PathVariable("itemId") Long itemId,
                                 @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("GET /items/{} : get item by ID from user ID {}", itemId, userId);
        return ResponseEntity.ok().body(itemService.getItem(itemId, userId));
    }

    @GetMapping
    public ResponseEntity<List<AnswerItemDto>> getItemsByUser(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("GET /items : get list of items from user ID {}", userId);
        return ResponseEntity.ok().body(itemService.getItemsByUser(userId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ItemDto>> getUsersAvailableItems(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                @RequestParam String text) {
        log.info("GET /items/search?text={} : get list of available items of user ID {} with text", text, userId);
        return ResponseEntity.ok().body(itemService.getAvailableItems(userId, text));
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<CommentDto> createComment(@PathVariable Long itemId,
                                    @Valid @RequestBody CommentDto commentDto,
                                    @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("POST /items/{}/comment : user ID {} creates comment - {}", itemId, userId, commentDto);
        return ResponseEntity.ok().body(itemService.createComment(itemId, userId, commentDto));
    }

}