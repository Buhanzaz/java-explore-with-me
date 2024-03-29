# Дипломный проек
# Explore With Me

## Идея
Свободное время — ценный ресурс. Ежедневно мы планируем, как его потратить — куда и с кем сходить. Сложнее всего в таком
планировании поиск информации и переговоры. Нужно учесть много деталей: какие намечаются мероприятия, свободны ли в этот
момент друзья, как всех пригласить и где собраться.

Приложение, которое вы будете создавать, — афиша. В этой афише можно предложить какое-либо событие от выставки до похода
в кино и собрать компанию для участия в нём.

![Project](https://raw.githubusercontent.com/Buhanzaz/java-explore-with-me/main/img/project_1.png)

## Стек
Java 11, Spring Boot , Lombok, Spring Data JPA, Hibernate, PostgreSQL, H2.

## База данных
![DataBase](https://raw.githubusercontent.com/Buhanzaz/java-explore-with-me/main/img/DataBase.png)

## Запуск проекта
1. Установите Docker на вашу машину `https://docs.docker.com/engine/install/`
2. Клонируйте репозиторий: `git clone https://github.com/Buhanzaz/java-explore-with-me.git`
3. Войдите в проект и создайте jar файлы: `mvn package`
4. Перейдите в каталог с проектом: `cd java-explore-with-me-main`
5. Извлеките изображения из Docker Hub или создайте их локально, если это необходимо: `docker-compose pull`
6. Запустите приложение: `docker-compose up -d`
7. Протестируйте приложение, обратившись к предоставленным URLs или endpoints.<br>
   Для этого воспользуйтесь Postman.<br>
   Тесты: <br>
   [Тест основного сервиса](https://github.com/Buhanzaz/java-explore-with-me/blob/main/postman/ewm-main-service.json) <br>
   [Тест сервиса статистики](https://github.com/Buhanzaz/java-explore-with-me/blob/main/postman/ewm-stat-service.json) <br>
   [Тест дополнительного функционала](https://github.com/Buhanzaz/java-explore-with-me/blob/main/postman/ewm-stat-service.json)
8. Чтобы запустить приложение, вы можете использовать команду docker-compose: `docker-compose run <service_name>`

## Техническое задание

Техническое задание
В рамках дипломного проекта вам предстоит разработать приложение ExploreWithMe (англ. «исследуй со мной»). Оно позволит пользователям делиться информацией об интересных событиях и находить компанию для участия в них.
Идея
Свободное время — ценный ресурс. Ежедневно мы планируем, как его потратить — куда и с кем сходить. Сложнее всего в таком планировании поиск информации и переговоры. Нужно учесть много деталей: какие намечаются мероприятия, свободны ли в этот момент друзья, как всех пригласить и где собраться.
Приложение, которое вы будете создавать, — афиша. В этой афише можно предложить какое-либо событие от выставки до похода в кино и собрать компанию для участия в нём.

### Вам нужно создать два сервиса:
 - "Основной сервис" будет содержать всё необходимое для работы продукта;
 - "Сервис статистики" будет хранить количество просмотров и позволит делать различные выборки для анализа работы приложения.

## Основной сервис
API основного сервиса разделите на три части:
- Публичная будет доступна без регистрации любому пользователю сети;
- Закрытая будет доступна только авторизованным пользователям;
- Административная — для администраторов сервиса.
К каждой из частей есть свои требования. Рассмотрим их.

### Требования к публичному API
Публичный API должен предоставлять возможности поиска и фильтрации событий. Учтите следующие моменты:
- Сортировка списка событий должна быть организована либо по количеству просмотров, которое будет запрашиваться в сервисе статистики, либо по датам событий;
- При просмотре списка событий должна возвращаться только краткая информация о мероприятиях;
- Просмотр подробной информации о конкретном событии нужно настроить отдельно (через отдельный эндпоинт);
- Каждое событие должно относиться к какой-то из закреплённых в приложении категорий;
- Должна быть настроена возможность получения всех имеющихся категорий и подборок событий (такие подборки будут составлять администраторы ресурса);
- Каждый публичный запрос для получения списка событий или полной информации о мероприятии должен фиксироваться сервисом статистики.

### Требования к API для авторизованных пользователей
Закрытая часть API должна реализовать возможности зарегистрированных пользователей продукта. Вот что нужно учесть:
- Авторизованные пользователи должны иметь возможность добавлять в приложение новые мероприятия, редактировать их и просматривать после добавления;
- Должна быть настроена подача заявок на участие в интересующих мероприятиях;
- Создатель мероприятия должен иметь возможность подтверждать заявки, которые отправили другие пользователи сервиса.

### Требования к API для администратора
Административная часть API должна предоставлять возможности настройки и поддержки работы сервиса. Обратите внимание на эти пункты:
- Нужно настроить добавление, изменение и удаление категорий для событий;
- Должна появиться возможность добавлять, удалять и закреплять на главной странице подборки мероприятий;
- Требуется наладить модерацию событий, размещённых пользователями, — публикация или отклонение;
- Также должно быть настроено управление пользователями — добавление, активация, просмотр и удаление.

### Модель данных
Жизненный цикл события должен включать несколько этапов.
1. Создание.
2. Ожидание публикации. В статус ожидания публикации событие переходит сразу после создания.
3. Публикация. В это состояние событие переводит администратор.
4. Отмена публикации. В это состояние событие переходит в двух случаях. Первый — если администратор решил, что его нельзя публиковать. Второй — когда инициатор события решил отменить его на этапе ожидания публикации.

## Сервис статистики
Второй сервис — сервис статистики. Он будет собирать информацию. Во-первых, о количестве обращений пользователей к спискам событий и, во-вторых, о количестве запросов к подробной информации о событии. На основе этой информации должна формироваться статистика о работе приложения.

### Функционал сервиса статистики должен содержать:
- Запись информации о том, что был обработан запрос к эндпоинту API;
- Предоставление статистики за выбранные даты по выбранному эндпоинту.

## Спецификация API
Для обоих сервисов мы разработали подробные спецификации API:
1. Cпецификация основного сервиса: ewm-main-service-spec.json;
2. Cпецификация сервиса статистики: ewm-stats-service.json.

## Фича для самостоятельного проектирования
Помимо основной части, была выбрана реализация дополнительного функциональнала.

### Администрирование:
Улучшение модерации событий администратором — возможность выгружать все события, ожидающие модерации, делать их проверку, а также оставлять комментарий для инициатора события, если оно не прошло модерацию. При этом у инициатора есть возможность исправить замечания и отправить событие на повторную модерацию.
