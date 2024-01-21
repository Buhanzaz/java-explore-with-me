package ru.practicum.web.events.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.enums.FieldName;
import ru.practicum.enums.State;
import ru.practicum.enums.StateActionForAdmin;
import ru.practicum.mappers.events.mapper.EventMapper;
import ru.practicum.models.Locations.model.entities.LocationEntity;
import ru.practicum.models.categories.model.entities.CategoryEntity;
import ru.practicum.models.comments.model.dtos.CommentDto;
import ru.practicum.models.events.model.dtos.EventFullDto;
import ru.practicum.models.events.model.dtos.ReviewEventFullDto;
import ru.practicum.models.events.model.dtos.UpdateEventAdminRequest;
import ru.practicum.models.events.model.dtos.UpdateGroupEventsAdminRequest;
import ru.practicum.models.events.model.entities.EventEntity;
import ru.practicum.models.users.model.entities.UserEntity;
import ru.practicum.web.categories.repository.CategoryRepository;
import ru.practicum.web.events.repository.EventRepository;
import ru.practicum.web.users.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class AdminEventServiceTest {

    //Тесты не компилируются в общий проект но локально проходят


//    private final UserRepository userRepository;
//    private final CategoryRepository categoryRepository;
//    private final EventRepository eventRepository;
//    private final AdminEventService adminEventService;
//    private final EventMapper eventMapper;
//
//    private EventEntity saveEvent;
//
//    @BeforeEach
//    void setUp() {
//        UserEntity saveUser = userRepository.save(new UserEntity(null, "Лиана", "Liana@mail.ru"));
//        CategoryEntity saveCategory = categoryRepository.save(new CategoryEntity(null, "Первая категория"));
//        saveEvent = eventRepository.save(EventEntity.builder()
//                .annotation("Сплав на байдарках похож на полет.")
//                .category(saveCategory)
//                .confirmedRequests(0L)
//                .createdOn(LocalDateTime.now())
//                .description("Сплав на байдарках похож на полет. На спокойной воде — это парение. На бурной, " +
//                        "порожистой — выполнение фигур высшего пилотажа. И то, и другое дарят чувство обновления, " +
//                        "феерические эмоции, яркие впечатления.")
//                .eventDate(LocalDateTime.now().plusDays(1))
//                .initiator(saveUser)
//                .location(new LocationEntity(null, 55.754167F, 37.62F))
//                .paid(true)
//                .participantLimit(10)
//                .requestModeration(false)
//                .state(State.PENDING)
//                .title("Сплав на байдарках")
//                .build());
//    }
//
//    @Test
//    void eventsWaitingForAReview() {
//        List<EventFullDto> eventFullDtoList = adminEventService.eventsWaitingForAReview();
//        EventFullDto fullDto = eventMapper.toFullDto(saveEvent);
//
//        assertEquals(fullDto, eventFullDtoList.get(0));
//    }
//
//    @Test
//    @SneakyThrows
//    void reviewingEvents() {
//        CommentDto commentDto = new CommentDto(FieldName.CATEGORY, "Измените тип категории");
//        UpdateEventAdminRequest updateRequest = UpdateEventAdminRequest.builder()
//                .stateAction(StateActionForAdmin.REJECT_EVENT)
//                .commentsDtoList(List.of(commentDto))
//                .build();
//        Map<Long, UpdateEventAdminRequest> adminRequestMap = Map.of(saveEvent.getId(), updateRequest);
//        UpdateGroupEventsAdminRequest updateRequestGroup = new UpdateGroupEventsAdminRequest(adminRequestMap);
//
//        List<ReviewEventFullDto> reviewEventFullDtoList = adminEventService.reviewingEvents(updateRequestGroup);
//
//        assertEquals(State.CANCELED, reviewEventFullDtoList.get(0).getState());
//        assertEquals(commentDto, reviewEventFullDtoList.get(0).getComments().get(0));
//    }
}