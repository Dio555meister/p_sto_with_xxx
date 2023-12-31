# Документация JMStack
## Работа c git
### Клонирование проекта

1. На странице репозитория убедитесь, что выбрана ветка **dev** (1), нажмите кнопку **Clone** (2), скопируйте ссылку (3).

![](src/main/resources/static/images/git_tutor/git_clone_url.png)

2. Откройте **Intellij IDEA**, нажмите **Get from version control** на экране приветствия, либо **VCS | Git | Clone...** в меню.

![](src/main/resources/static/images/git_tutor/git_clone_get.png)

![](src/main/resources/static/images/git_tutor/git_clone_get_alt.png)

3. Вставьте скопированную ссылку в строку **URL**, нажмите **Clone**.

![](src/main/resources/static/images/git_tutor/git_clone_clone.png)

### Перед внесением изменений в код
Создайте новую ветку в git-репозитории и работайте в ней. Для этого:
1. Нажмите на текущую ветку **dev** в правом нижнем углу.


![](src/main/resources/static/images/git_tutor/git_branch.png)

2. Выберите **New branch**.

![](src/main/resources/static/images/git_tutor/git_branch_create.png)

3. Введите название своей новой ветки (на ваше усмотрение) и нажмите **Create**.

![](src/main/resources/static/images/git_tutor/git_branch_name.png)

### Добавление своего кода в общий репозиторий. Git push.

Прежде чем создать merge request вам необходимо подготовить вашу ветку к отправке в общий репозиторий.

1. Нажмите на текущую ветку в правом нижнем углу. Выберите опцию **dev | update**. 
Таким образом вы скачаете в свою локальную ветку **dev** все коммиты которые были замержены, 
пока вы работали в своей ветке.

![](src/main/resources/static/images/git_tutor/git_premerge_update_dev.png)

2. Убедитесь, что в данный момент активна ваша рабочая ветка (занчек ярлыка слева от имени, как у ветки my-branch на скриншоте).
Выберите опцию **dev | Merge into Current**. Таким образом вы добавите все изменения из ветки **dev** в вашу ветку. При возникновении конфликтов разрешите их.

![](src/main/resources/static/images/git_tutor/git_premerge_merge_dev.png)

3. ---**ВАЖНО**--- Убедитесь что проект собирается и запускается.

4. Выберите вашу ветку и нажмите на **Push...**, чтобы добавить её в общий репозиторий.

![](src/main/resources/static/images/git_tutor/git_premerge_push.png)

### Создание merge request

1. Создайте новый merge request. В качестве **Source branch** выберите свою ветку, **Target branch** - **dev**.

![](src/main/resources/static/images/git_tutor/git_merge_req.png)

![](src/main/resources/static/images/git_tutor/git_merge_req_new.png)

![](src/main/resources/static/images/git_tutor/git_merge_req_src_trg.png)

2. Проверьте данные, допишите комментарии при необходимости. Обратите внимание на опцию **Delete source branch when merge request is accepted**.
Завершите создание реквеста, приложите ссылку на него в карточку таска на Trello.

![](src/main/resources/static/images/git_tutor/git_merge_req_final.png)


## Сущности

### User

#### Поля:

- **id** - уникальный идентификационный номер пользователя;
- **fullName** - полное имя пользователя;
- **password** - пароль;
- **persistDateTime** - дата регистрации;
- **role** - идентификационный номер пользователя;
- **lastUpdateDateTime** - дата последней авторизации;
- **email** - адрес электронной почты;
- **about** - краткая информация о пользователе;
- **city** - город пользователя;
- **linkSite** - ссылка на сайт;
- **linkGitHub** - ссылка на github;
- **linkVk** - ссылка на страницу во Вконтакте;
- **reputationCount** - счетчик репутации;
- **isEnabled** - отметка, что учетная запись не заблокирована;
- **image** - фото пользователя;
```
Пользователь может задавать вопросы, отвечать на вопросы и давать комментарии к вопросам и ответам.
Наделен ролью.Может помечать понравившиеся вопросы, отмечать вопросы которые были полезны. Заданный
вопрос может отметить, как решенный, указав на ответ, который помог решить проблему.
```

### Role

#### Поля:

- **id** - уникальный идентификационный номер роли;
- **name** - имя роли;
```
Определяет порядок прав действий пользователя в системе.
```

### Question

#### Поля:

- **id** - уникальный идентификационный номер вопроса;
- **title** - заголовок вопроса;
- **description** - описание вопроса;
- **persistDateTime** - дата публикации вопроса;
- **viewCount** - количество просмотров;
- **user** - идентификационный номер пользователя, опубликовавший вопрос;
- **tags** - теги, которыми обозначен вопрос;
- **lastUpdateDateTime** - дата последней редакции вопроса или добавления ответа;
- **isDeleted** - флаг, помечающий объект, как удалённый. Отображаться при запросе данный вопрос не будет;
```
Сущность, которую инициализирует пользователь для публикации своего вопроса. Имеет заголовок, который кратко 
описывает суть вопроса, развернутое описание, с возможностью вставки фрагмента кода. Может быть помечен полями
“решен”, “любимый вопрос”. Отметка “решен” проставляется автором вопроса, с указанием ответа, который помог
решить возникший вопрос. Отметка “любимый вопрос” ставиться любым пользователем, который посчитал вопрос
актуальным и интересным. ”Тэг” проставляется автором вопроса, для классификации вопроса. Под вопросом может
также быть оставлен комментарий любым пользователем, включая автора вопроса.
```

### VoteQuestion

#### Поля

- **user** - пользователь, который отправил свой голос;
- **question** - вопрос, по которому ведётся голосование;
- **localDateTime** - дата и время отправки голоса;
- **vote** - значение голоса, который отправил пользователь по вопросу;
```
Таблица, которая содержит в себе записи голосования пользователей по вопросам. В таблице используется
сборный внешний ключ, который состоит из полей user, qustion, localDateTime. Для создания необходимо
передать сущности User, Question и значения голоса. Допускается передача значений только "1" и "-1".
Пользователь может проголосовать за один вопрос только с отклонением в 1 пункт. Допускается, что пользователь
может отменить свой голос, отправив противоположное значение предыдущего голоса. Или изменить свой итоговый
голос, при этом отправив повторно обратное значение. Все действия пользователя сохраняются в таблице.
Итоговое значение "полезности вопроса" является сумма всех голосов.
```

### Answer

#### Поля:

- **id** - уникальный идентификационный номер ответа;
- **user** - идентификационный номер пользователя, который опубликовал ответ;
- **question** - идентификационный номер вопроса, к которому относиться ответ;
- **htmlBody** - тело ответа;
- **persistDateTime** - дата публикации ответа;
- **updateDateTime** - дата публикации ответа;
- **isHelpful** - отметка, что именно этот ответ помог решить вопрос, к которому оно относиться. Эту
отметку может ставить только автор вопроса;
- **dateAcceptTime** - дата, решения вопроса;
- **isDeleted** - флаг, помечающий объект, как удалённый. Отображаться при запросе данный ответ не будет;
```
Сущность, которую инициализирует пользователь отвечая на вопрос. Привязан к сущности question. Ответ на
вопрос может оставлять любой пользователь. Может быть предложено несколько вариантов ответов на опубликованный
вопрос. Ответ может быть помечен автором вопроса, к которому был оставлен ответ, как “решение помогло”,
обозначая тем самым, что сам вопрос решён и помог прямо или косвенно данный ответ. Под ответом пользователи
могут оставлять комментарии, которые уточняют или дополняют предложенное решение. Каждый пользователь может
оставлять под вопросом только один ответ.
```
### AnswerVote

#### Поля

- **user** - пользователь, который отправил свой голос;
- **answer** - ответ, по которому ведётся голосование;
- **persistDateTime** - дата и время отправки голоса;
- **vote** - значение голоса, который отправил пользователь по ответу;
```
Таблица, которая содержит в себе записи голосования пользователей по ответам. В таблице используется
сборный внешний ключ, который состоит из полей user, answer, persistDateTime. Для создания необходимо
передать сущности User, Answer и значения голоса. Допускается передача значений только "1" и "-1".
Пользователь может проголосовать за один вопрос только с отклонением в 1 пункт. Не допускается, что пользователь
может отменить свой голос. Все действия пользователя сохраняются в таблице.
```


### Comment

#### Поля:

- **id** - уникальный идентификационный номер комментария;
- **user** - идентификационный номер пользователя, который оставил комментарий;
- **text** - содержание комментария;
- **persistDateTime** - дата публикации комментария;
- **lastUpdateDateTime** - дата последней редакции;
- **commentType** - тип комментария. Идентифицирует родительскую сущность, к которой был оставлен комментарий
(вопрос или ответ);
```
Комментарий оставляется пользователем под любым вопросом или ответом, для уточнения или дополнения к основному
посту.
```

### User_favorite_question

#### Поля:

- **id** - уникальный идентификационный номер записи об отмеченном вопросе;
- **persistDateTime** - дата постановки отметки “понравившейся вопрос”;
- **user** - идентификационный номер пользователя, который отметил вопрос, как понравившийся;
- **question** - идентификационный номер вопроса, который пользователь отметил, как понравившейся;
```
Отметка понравившегося вопроса проставляется пользователем, который счел вопрос интересным и/или полезным.
```

### Tag

#### Поля:

- **id** - уникальный идентификационный номер тега;
- **name** - название тега;
- **description** - описание тега;
- **persistDateTime** - дата создания тега;
- **questions** - список вопросов, которыми помечен данный тег;
```
Ставиться у сущности question для классификации вопроса.
```

### Related_tags

#### Поля:

- **id** - уникальный номер записи;
- **childTag** - идентификационный номер дочернего тега;
- **mainTag** - идентификационный номер родительского тега;
```
Категоризация тэгов. Показывает взаимосвязь общего с конкретным запросом. Например тэг “База данных” будет
иметь более широкую область запросов, в то время как тэг “Hibernate” будет более предметную область, которая
одновременно подходит под широкое использование тэга “База данных”.
```

### Tag_has_question

#### Поля

- **tag_id** - идентификационный номер тега;
- **question_id** - идентификационный номер вопроса;
- **persist_date** - дата отметки вопроса тегом;
```                                                  
Производная таблица связи many-to-many сущности вопросов и тегов.
```

### Editor

#### Поля:

- **id** - уникальный идентификационный номер редактора;
- **count** - правки сделанные за день
- **persist_date** - дата 
- **user_id** - идентификационный номер пользователя;
```
Сущность, которая хранит в себе историю редактирования вопроса, 
ответа или комментария сделанных пользователями.
```

### Moderator

#### Поля:

- **id** - уникальный идентификационный номер модератора;
- **persist_date** - дата назначения;
- **user_id** - идентификационный номер пользователя;
```
Сущность, которая хранит пользователей чей статус являеться модератором. 
Привилегия, выдаваемая системой в зависимости от уровня репутации участника.
```

### Reputation

#### Поля

- **id** - уникальный идентификационный номер репутации 
- **count** - баллы заработанные за день
- **persist_date** - дата 
- **user_id** - идентификационный номер пользователя
```
Сущность, которая хранит в себе историю репутации пользователей по дням. 
Новый день новая запись, для каждого пользователя (если пользователь заработал баллы иначе записи не будет).  
```

### Badges

#### Поля

- **id** - уникальный идентификационный номер знака 
- **badges** - имя знака
- **count** - минимальное количество очков репутации для получения знака
- **description** - описание знака
```
Сущность знаков.   
```

### user_badges 

#### Поля

- **id** - уникальный идентификационный номер знака 
- **ready** - имеет булевский тип, если помечается true знак получен
- **badges_id** - идентификационный номер знака
- **user_id** - идентификационный номер пользователя
```
Промежуточная сущность связывающая таблицы User и Badges.
User при регистрации получает все знаки лишь поле ready определяет заслужил пользователь знак или нет.
```

### Tag_ignored

#### Поля

- **id** - уникальный идентификационный номер знака 
- **user** - ссылка на профиль пользователя
- **ignoredTag** - ссылка на тег
- **persistDateTime** - дата добавления тега в справочник
```
Справочник тегов которые пользователь добавляет в игнорируемые
```

### Tag_tracked

#### Поля

- **id** - уникальный идентификационный номер знака 
- **user** - ссылка на профиль пользователя
- **trackedTag** - ссылка на тег
- **persistDateTime** - дата добавления тега в справочник
```
Справочник тегов которые пользователь добавляет в отслеживаемые 
```

### Bookmarks

#### Поля

- **id** - уникальный идентификационный номер закладки
- **user** - ссылка на профиль пользователя
- **question** - ссылка на вопрос
```
Таблица закладок
```

### Chat

#### Поля:

- **id** - уникальный идентификационный номер чата
- **title** - название чата
- **persistDate** - дата создания чата
- **chatType** - тип чата
```
Таблица чата
```

### group_chat

#### Поля:

- **id** - уникальный идентификационный номер group_chat
- **users** - пользователи привязанные к group_chat
- **chat** - связь чата с групповым чатом
```
Таблица группового чата, содержит в себе связь OneToOne с чатом и ManyToMany создающая производную таблицу
"groupchat_has_users" для привязки чата к пользователю 
```

### groupchat_has_users

#### Поля:

- **chat_id** - уникальный идентификационный номер чата
- **user_id** - уникальный идентификационный номер пользователя
```
Производная таблица обеспечивающая связь ManyToMany между чатом и пользователем
```

### single_chat

#### Поля:

- **id** - уникальный идентификационный номер single_chat
- **chat** - поле чата
- **userOne** - первый пользователь
- **userOne** - второй пользователь
```
Таблица одиночного чата, содержит в себе связь OneToOne между single_chat и chat и две связи ManyToOne для связи двух
пользователей с single_chat.
```

### message

#### Поля:

- **id** - уникальный идентификационный номер сообщения
- **message** - текст сообщения
- **lastRedactionDate** - время последнего изменения
- **persistDate** - дата сохранения сообщения
- **userSender** - пользователь отправивший сообщение
- **chat** - поле чата
```
Таблица сообщения, содержит в себе связь ManyToOne с чатом
```

### question_viewed

#### Поля:

- **id** - уникальный идентификационный номер просмотра вопроса
- **user** - поле пользователя просмотревшего вопрос
- **question** - поле вопроса который был просмотрен
- **localDateTime** - дата просмотра вопроса

```
Таблица просмотренных вопросов с датой просмотра, содержит в себе связи ManyToOne к вопросу и пользователю
```

### comment_answer

#### Поля:

- **id** - уникальный идентификационный номер comment_answer
- **comment** - поле комментария
- **answer** - поле ответа

```
Таблица комментария на ответ, содержит в себе связи OneToOne к комментарию и связь ManyToOne к ответу 
```

### comment_question

#### Поля:

- **id** - уникальный идентификационный номер comment_question
- **comment** - поле комментария
- **question** - поле вопроса

```
Таблица комментария к вопросу, содержит в себе связи OneToOne к комментарию и связь ManyToOne к вопросу 
```

### group_bookmark

#### Поля:

- **id** - уникальный идентификационный номер группы закладок
- **title** - заголовок группы закладок
- **bookMarks** - поле закладки
- **user** - поле пользователя

```
Таблица группы закладок с помощью которой можно распределять закладки по группам, содержит в себе связь
ManyToMany группы закладок к закладкам и связь ManyToOne к пользователю создавшему группу закладок
```

### group_bookmark_link

#### Поля:

- **group_bookmark_id** - уникальный идентификационный номер группы закладок
- **bookmark_id** - уникальный идентификационный номер закладки

```
Производная таблица обеспечивающая связь группы закладок к закладке
```

[Схема](https://dbdiagram.io/d/6086b027b29a09603d12295d)


## Как пользоваться конвертором MapStruct.

**MapStruct** - это генератор кода, который значительно упрощает реализацию сопоставлений между типами Java-компонентов
 на основе подхода соглашения по конфигурации.
Сгенерированный код сопоставления использует простые вызовы методов 
и, следовательно, является быстрым, безопасным по типам и простым для понимания.
Более подробно можно ознакомиться в официальной документации:https://mapstruct.org/ .

В текущем проекте **Developer Social** технология **MapStruct** используется,в основном, для 
преобразования **Dto** в **Entity** и наоборот.
Названия всех классов преобразования должны заканчиваться на «**Converter**» (например: **GroupChatConverter**) и должны храниться в пакете **converters**.
Такой класс должен быть абстрактным, помеченным аннотацией **@Mapper**.Эта аннотация отмечает класс
как класс сопоставления и позволяет процессору **MapStruct** включиться во время компиляции.
Методы должны быть абстрактными,из их названия должно быть явно понятно,какой класс
во что преобразуется. Например: (**chatDtoToGroupChat**- преобразует **ChatDto** в **GroupChat**).

Если соответствующие поля двух классов имеют разные названия, для их сопоставления
используется аннотация **@Mapping**. Эта аннотация ставится над абстрактным методом преобразования
и имеет следующие обязательные поля:

**source** - исходное поле преобразовываемого класса.
**target**- конечное поле класса,в котором должно быть значение из **source**.

Для разрешения неоднозначностей в именах полей классов можно указывать их с именем
соответствующего параметра метода преобразования.
например:(**source** = "**chatDto.title**", где **chatDto** - имя параметра метода преобразования)

По умолчанию, метод должен принимать объект преобразовываемого класса, но также
можно передавать различные другие параметры(например **Id**) и потставлять их в **source**,
чтобы в дальнейшем поле **target** приняло это значение.

Могут возникнуть ситуации,что нужно преобразовать поле в другой тип данных,например 
в коллекцию и наоборот.Тогда в аннотацию **@Mapping** следует добавить еще одно поле:
**qualifiedByName**, которое будет содержать имя метода, реализующего логику получения
нужного типа или значения. В таком случае этот метод должен быть помечен аннотацией
**@Named** c указанием названия для маппинга.
Ниже приведён общий пример:

````
{@Mapping(source = "chatDto.title", target = "title")
    @Mapping(source = "chatDto.image", target = "image")
    @Mapping(source = "userId",target ="users",qualifiedByName = "userIdToSet")
    public abstract GroupChat chatDtoToGroupChat(ChatDto chatDto,Long userId); }"
   

@Named("userIdToSet")
    public  Set<User> userIdToSet(Long userId) {
        User user = userService.getById(userId);
        Set<User> userSet = new HashSet<>();
        userSet.add(user);
        return userSet;
    }
````
## Настройка и выбор профиля для работы с БД

На текущий момент в приложении используются два основных профиля, для которых созданы соотвествующие .properites файлы **- local и dev**. Данные профили содержат шаблон конфигов для работы с БД, где данные для подключения к Вашей локальной БД заменены "заглушками". Для того чтобы выбрать профиль и прописать недостающие переменные, необходимо выполнить следующие действия:
1. Перейти в раздел **«Edit Configurations…»** вашего Spring-приложения
![](src/main/resources/static/images/profiles_tutor/profiles_config.png)
2. Выбрать **«Modify options»**
![](src/main/resources/static/images/profiles_tutor/profiles_modify.png)
3. Поставить галочки возле **«Active profiles»** и **«Environment variables»**
![](src/main/resources/static/images/profiles_tutor/profiles_run_options.png)
4. Указать один из существующих профилей (local или dev) и нажать иконку с **изображением страницы** напротив пункта **"Environment variables"**
![](src/main/resources/static/images/profiles_tutor/profiles_variables.png)
5. Нажатием кнопки **"+"** добавить следующие ключи с указанием соответствующих значений Вашей БД, где:  
**POSTGRESQL_HOST** - имя хоста  
**POSTGRESQL_PORT** - номер порта  
**POSTGRESQL_DB** - название Вашей БД  
**POSTGRESQL_USER** - имя пользователя БД  
**POSTGRESQL_PASS** - пароль пользователя БД  
![](src/main/resources/static/images/profiles_tutor/profiles_environment_variables_add.png)

Таким образом, мы добавили переменные окружения, необходимые для подключения к БД, которые в последствии будут использованы properties-файлами при создании контекста.

## Структура написания тестов

1. Все классы для тестов находиться в папке **.../api**.
2. Тесты создаются согласно REST-контроллерам. Например, если есть `ResourseAnswerController`, то есть тест `TestResourseAnswerController` и тестируются все api из контроллера.
3. Все тестовые классы должны наследоваться от абстрактного класса в котором описана все конфигурация тестов.
4. В пакете **test/resources/script** лежат скрипты для инициализации данных перед тестом и после его
5. Все сущности описанные в SQL-скриптах для загрузки тестовых данных начинаются с `id = 100`.
6. **НЕЛЬЗЯ ИЗМЕНЯТЬ УЖЕ НАПИСАННЫЕ СКРИПТЫ, ТОЛЬКО ЕСЛИ НЕ ОБАНУРЖЕН БАГ!**,
7. На каждый метод тестового класса написаны отдельные SQL-скрипты
8. Нельзя использовать аннотацию **@Transactional** для тестов.
9. SQL-скрипты должны находиться в пакете test/resources/script/[пакет с названием тестового контроллера]/[пакет с названием тестового метода]. В пакете должно находиться 2 скрипта Before.sql и After.sql, в Before SQL-скрипт загрузки данных для тестирования в БД. В After SQL-скрипт удаления (DELETE) всех данных, который будет исполнен после выполнения тестового метода
10. Аннотациями для указания Before, After скриптов для тестов должны быть помечены тестовые методы, НЕ классы(!)

## Инструкция по миграциям с помощью Flyway
**Как работает Flyway**

Для отслеживания когда, кем и какие миграции были применены, в схему базы данных добавляется специальная таблица с метаданными. В этой таблице также хранятся контрольные суммы миграций и информация о том успешна была миграция или нет.
Фреймворк работает следующим образом:
* Проверяет схему базы данных на наличие таблицы метаданных (по умолчанию SCHEMA_VERSION). Если таблица метаданных не существует, то создает ее.
* Сканирует classpath на наличие доступных миграций.
* Сравнивает миграции с таблицей метаданных. Если номер версии меньше или равен версии, помеченной как текущая, то игнорирует ее.
* Отмечает все оставшиеся миграции как ожидающие (pending). Потом сортирует их по возрастанию номеров версий и выполняет в указанном порядке.
* По мере применения миграций обновляет таблицу метаданных.

**Команды**

В Flyway есть следующие основные команды по управлению миграциями (mvn flyway:command):
1. info. Отображение текущего состояния / версии схемы базы данных. Информация о том, какие миграции ожидаются, какие были применены, состояние выполненных миграций и дата их выполнения.
2. migrate. Обновление схемы базы данных до текущей версии. Сканирование classpath для поиска доступных миграций и применение ожидающих миграций.
3. baseline. Установка версии схемы базы данных, игнорируя миграции до baselineVersion включительно. Baseline помогает использовать Flyway на уже существующей базе данных. Новые миграции применяются в обычном режиме.
4. validate. Проверка текущей схемы базы данных на соответствие доступным миграциям.
5. repair. Восстановление таблицы метаданных.
6. clean. Удаление всех объектов в схеме. Конечно, никогда не нужно использовать clean в продакшен базах данных.

**Требования**
1. Все скрипты миграций находятся в пакете resources/db/migration
2. Название скрипта обязательно начинается с Vx__ (нижнее подчеркивание двойное), где x - порядковый номер миграции

## Как пользоваться JWT токеном в Postman

1. Запустите наше приложение **(JmApplication)** и откройте Ваше рабочее пространство **(My Workspace)** на сайте **Postman** или в десктопном приложении и откройте новую вкладку.
![](src/main/resources/static/images/jwt_tutor/postman_new_tab.png)
2. Выберете тип запроса **"POST"** (1), пропишите url-адрес **"http://localhost:8091/api/auth/token"** (2), откройте вкладку **"Body"** (3), выберете тип **"raw"** (4) и формат **"JSON"** (5), пропишите **email** и **password** пользователя который Вам необходим и существует в нашей базе данных (6), нажмите кнопку отправить (7).
![](src/main/resources/static/images/jwt_tutor/postman_token_request.png)
3. Если **email** и **password** введены правильно, то программа авторизует пользователя и вернет его уникальный **токен**. В этом токене хранятся email и роли данного пользователя. Впоследствии при обращении к различным API нашего приложения мы будем использовать данный токен для авторизации.
![](src/main/resources/static/images/jwt_tutor/postman_token_response.png)
4. Для доступа к нужному API выберете вкладку **"Authorization"** (1), поставьте тип **"Bearer Token"** (2), вставьте токен пользователя (3) и нажмите кнопу отправить (4).
![](src/main/resources/static/images/jwt_tutor/postman_authorization_by_token.png)
5. Приложение достает из токена email пользователя, авторизует его и, при наличии необходимых прав, даст доступ к API. (В этом примере мы используем токен пользователя с ролью "USER" и обращаемся к API доступному пользователям с ролью "USER").
![](src/main/resources/static/images/jwt_tutor/postman_success_authorization_by_token.png)
6. При попытке обращения к данному API с помощью токена пользователя с ролью "ADMIN" нам вернется ошибка, т.к. доступа у пользователей с данной нет.
![](src/main/resources/static/images/jwt_tutor/postman_incorrect_authorization_attempt.png)
![](src/main/resources/static/images/jwt_tutor/postman_incorrect_authorization_error.png)

## Как добавить на html страницу header, footer, sidebar

Для добавления хедера, футера и сайдбара в .html файле перед закрывающим тегом ```</body>``` необходимо вставить 3 тега js-скриптов:
```
<script type="text/javascript" src="/js/sidebar.js"></script>
<script type="text/javascript" src="/js/header.js"></script>
<script type="text/javascript" src="/js/footer.js"></script>
```