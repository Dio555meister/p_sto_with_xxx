package com.javamentor.qa.platform.api;


import com.javamentor.qa.platform.AbstractTestApi;
import org.hamcrest.collection.IsEmptyCollection;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

public class TestTagResourceController extends AbstractTestApi {

    @Test
    @Sql(value = {"/script/TestTagResourceController/testGetRelatedTagsDtoList/Before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/script/TestTagResourceController/testGetRelatedTagsDtoList/After.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getRelatedTagsDtoListTest() throws Exception {
        String token = getToken("0@gmail.com", "0pwd");

        //success getting TOP-10 Tags from 15 in DB (ordered by countQuestion)
        this.mvc.perform(get("/api/user/tag/related").header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id", Is.is(100)))
                .andExpect(jsonPath("$.[0].title", Is.is("name1")))
                .andExpect(jsonPath("$.[0].countQuestion", Is.is(4)))
                .andExpect(jsonPath("$.[1].id", Is.is(101)))
                .andExpect(jsonPath("$.[1].title", Is.is("name2")))
                .andExpect(jsonPath("$.[1].countQuestion", Is.is(3)))
                .andExpect(jsonPath("$.[2].id", Is.is(102)))
                .andExpect(jsonPath("$.[2].title", Is.is("name3")))
                .andExpect(jsonPath("$.[2].countQuestion", Is.is(2)))
                .andExpect(jsonPath("$.[3].id", Is.is(103)))
                .andExpect(jsonPath("$.[3].title", Is.is("name4")))
                .andExpect(jsonPath("$.[3].countQuestion", Is.is(2)))
                .andExpect(jsonPath("$.[4].id", Is.is(104)))
                .andExpect(jsonPath("$.[4].title", Is.is("name5")))
                .andExpect(jsonPath("$.[4].countQuestion", Is.is(2)))
                .andExpect(jsonPath("$.[5].id", Is.is(105)))
                .andExpect(jsonPath("$.[5].title", Is.is("name6")))
                .andExpect(jsonPath("$.[5].countQuestion", Is.is(2)))
                .andExpect(jsonPath("$.[6].id", Is.is(106)))
                .andExpect(jsonPath("$.[6].title", Is.is("name7")))
                .andExpect(jsonPath("$.[6].countQuestion", Is.is(2)))
                .andExpect(jsonPath("$.[7].id", Is.is(107)))
                .andExpect(jsonPath("$.[7].title", Is.is("name8")))
                .andExpect(jsonPath("$.[7].countQuestion", Is.is(1)))
                .andExpect(jsonPath("$.[8].id", Is.is(108)))
                .andExpect(jsonPath("$.[8].title", Is.is("name9")))
                .andExpect(jsonPath("$.[8].countQuestion", Is.is(1)))
                .andExpect(jsonPath("$.[9].id", Is.is(109)))
                .andExpect(jsonPath("$.[9].title", Is.is("name10")))
                .andExpect(jsonPath("$.[9].countQuestion", Is.is(1)))
        ;
    }

    @Test
    @Sql(value = {"/script/TestTagResourceController/testAddIgnoredTag/Before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/script/TestTagResourceController/testAddIgnoredTag/After.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void addIgnoredTag() throws Exception {
        String token = getToken("0@gmail.com", "0pwd");

        //success adding ignored tag and returning TagDto
        this.mvc.perform(post("/api/user/tag/{id}/ignored", 100).header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", Is.is(100)))
                .andExpect(jsonPath("$.name", Is.is("name1")))
                .andExpect(jsonPath("$.description", Is.is("description1")));

        //wrong id
        this.mvc.perform(post("/api/user/tag/{id}/ignored", 1000).header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", Is.is("Tag with id = 1000 not found")));

        //user already has tag in ignored
        this.mvc.perform(post("/api/user/tag/{id}/ignored", 100).header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", Is.is("Tag with id = 100 already added by user with id = 100 in Ignored")));

        //user already has tag in checked
        this.mvc.perform(post("/api/user/tag/{id}/ignored", 101).header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", Is.is("Tag with id = 101 already added by user with id = 100 in Tracked")));


    }

    @Test
    @Sql(value = {"/script/TestTagResourceController/testAddTrackedTag/Before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/script/TestTagResourceController/testAddTrackedTag/After.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void addTrackedTag() throws Exception {
        String token = getToken("0@gmail.com", "0pwd");

        //success adding tracked tag and returning TagDto
        this.mvc.perform(post("/api/user/tag/{id}/tracked", 101).header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", Is.is(101)))
                .andExpect(jsonPath("$.name", Is.is("name2")))
                .andExpect(jsonPath("$.description", Is.is("description2")));

        //wrong id
        this.mvc.perform(post("/api/user/tag/{id}/tracked", 1000).header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", Is.is("Tag with id = 1000 not found")));

        //user already has tag in tracked
        this.mvc.perform(post("/api/user/tag/{id}/tracked", 101).header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", Is.is("Tag with id = 101 already added by user with id = 100 in Tracked")));

        //user already has tag in ignored
        this.mvc.perform(post("/api/user/tag/{id}/ignored", 100).header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", Is.is("Tag with id = 100 already added by user with id = 100 in Ignored")));


    }

    @Test
    @Sql(value = {"/script/TestTagResourceController/testGetAllUserIgnoredTag/Before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/script/TestTagResourceController/testGetAllUserIgnoredTag/After.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testGetAllUserIgnoredTag() throws Exception {

        //Successfully (User has IgnoredTag)
        String tokenUserHaveTag = getToken("4@gmail.com", "4pwd");

        this.mvc.perform(get("/api/user/tag/ignored")
                        .header("Authorization", "Bearer " + tokenUserHaveTag))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id", Is.is(100)))
                .andExpect(jsonPath("$.[0].name", Is.is("tag1")))
                .andExpect(jsonPath("$.[1].id", Is.is(101)))
                .andExpect(jsonPath("$.[1].name", Is.is("tag2")))
                .andExpect(jsonPath("$.[2].id", Is.is(103)))
                .andExpect(jsonPath("$.[2].name", Is.is("tag4")));

        //Unsuccessfully (User doesn't have IgnoredTag)
        String tokenUserDoesntHaveTag = getToken("5@gmail.com", "5pwd");

        this.mvc.perform(get("/api/user/tag/ignored")
                        .header("Authorization", "Bearer " + tokenUserDoesntHaveTag))
                .andExpect(jsonPath("$", IsEmptyCollection.empty()));

    }

    @Test
    @Sql(value = {"/script/TestTagResourceController/testGetSortedByDateTags/Before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/script/TestTagResourceController/testGetSortedByDateTags/After.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getSortedByDateTagList() throws Exception {
        String token = getToken("0@gmail.com", "0pwd");

        //success getting TOP-10 Tags from 15 in DB (ordered by countQuestion)
        this.mvc.perform(get("/api/user/tag/new?currentPageNumber=1&itemsOnPage=2").header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.currentPageNumber", Is.is(1)))
                .andExpect(jsonPath("$.totalPageCount", Is.is(8)))
                .andExpect(jsonPath("$.totalResultCount", Is.is(15)))
                .andExpect(jsonPath("$.items[0].id", Is.is(114)))
                .andExpect(jsonPath("$.items[0].title", Is.is("name15")))
                .andExpect(jsonPath("$.items[0].description", Is.is("description114")))
                .andExpect(jsonPath("$.items[0].questionCount", Is.is(6)))
                .andExpect(jsonPath("$.items[0].questionCountOneDay", Is.is(3)))
                .andExpect(jsonPath("$.items[0].questionCountWeekDay", Is.is(6)))
                .andExpect(jsonPath("$.items[1].id", Is.is(113)))
                .andExpect(jsonPath("$.items[1].title", Is.is("name14")))
                .andExpect(jsonPath("$.items[1].description", Is.is("description113")))
                .andExpect(jsonPath("$.items[1].questionCount", Is.is(1)))
                .andExpect(jsonPath("$.items[1].questionCountOneDay", Is.is(1)))
                .andExpect(jsonPath("$.items[1].questionCountWeekDay", Is.is(1)))
                .andExpect(jsonPath("$.itemsOnPage", Is.is(2)));


        this.mvc.perform(get("/api/user/tag/new?currentPageNumber=2&itemsOnPage=2").header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currentPageNumber", Is.is(2)))
                .andExpect(jsonPath("$.totalPageCount", Is.is(8)))
                .andExpect(jsonPath("$.totalResultCount", Is.is(15)))
                .andExpect(jsonPath("$.items[0].id", Is.is(112)))
                .andExpect(jsonPath("$.items[0].title", Is.is("name13")))
                .andExpect(jsonPath("$.items[0].description", Is.is("description112")))
                .andExpect(jsonPath("$.items[0].questionCount", Is.is(1)))
                .andExpect(jsonPath("$.items[0].questionCountOneDay", Is.is(0)))
                .andExpect(jsonPath("$.items[0].questionCountWeekDay", Is.is(1)))
                .andExpect(jsonPath("$.items[1].id", Is.is(111)))
                .andExpect(jsonPath("$.items[1].title", Is.is("name12")))
                .andExpect(jsonPath("$.items[1].description", Is.is("description111")))
                .andExpect(jsonPath("$.items[1].questionCount", Is.is(3)))
                .andExpect(jsonPath("$.items[1].questionCountOneDay", Is.is(2)))
                .andExpect(jsonPath("$.items[1].questionCountWeekDay", Is.is(3)))
                .andExpect(jsonPath("$.itemsOnPage", Is.is(2)));

        this.mvc.perform(get("/api/user/tag/new?currentPageNumber=1&itemsOnPage=6").header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.currentPageNumber", Is.is(1)))
                .andExpect(jsonPath("$.totalPageCount", Is.is(3)))
                .andExpect(jsonPath("$.totalResultCount", Is.is(15)))
                .andExpect(jsonPath("$.items[0].id", Is.is(114)))
                .andExpect(jsonPath("$.items[0].title", Is.is("name15")))
                .andExpect(jsonPath("$.items[0].description", Is.is("description114")))
                .andExpect(jsonPath("$.items[0].questionCount", Is.is(6)))
                .andExpect(jsonPath("$.items[0].questionCountOneDay", Is.is(3)))
                .andExpect(jsonPath("$.items[0].questionCountWeekDay", Is.is(6)))
                .andExpect(jsonPath("$.items[1].id", Is.is(113)))
                .andExpect(jsonPath("$.items[1].title", Is.is("name14")))
                .andExpect(jsonPath("$.items[1].description", Is.is("description113")))
                .andExpect(jsonPath("$.items[1].questionCount", Is.is(1)))
                .andExpect(jsonPath("$.items[1].questionCountOneDay", Is.is(1)))
                .andExpect(jsonPath("$.items[1].questionCountWeekDay", Is.is(1)))
                .andExpect(jsonPath("$.items[2].id", Is.is(112)))
                .andExpect(jsonPath("$.items[2].title", Is.is("name13")))
                .andExpect(jsonPath("$.items[2].description", Is.is("description112")))
                .andExpect(jsonPath("$.items[2].questionCount", Is.is(1)))
                .andExpect(jsonPath("$.items[2].questionCountOneDay", Is.is(0)))
                .andExpect(jsonPath("$.items[2].questionCountWeekDay", Is.is(1)))
                .andExpect(jsonPath("$.items[3].id", Is.is(111)))
                .andExpect(jsonPath("$.items[3].title", Is.is("name12")))
                .andExpect(jsonPath("$.items[3].description", Is.is("description111")))
                .andExpect(jsonPath("$.items[3].questionCount", Is.is(3)))
                .andExpect(jsonPath("$.items[3].questionCountOneDay", Is.is(2)))
                .andExpect(jsonPath("$.items[3].questionCountWeekDay", Is.is(3)))
                .andExpect(jsonPath("$.items[4].id", Is.is(110)))
                .andExpect(jsonPath("$.items[4].title", Is.is("name11")))
                .andExpect(jsonPath("$.items[4].description", Is.is("description110")))
                .andExpect(jsonPath("$.items[4].questionCount", Is.is(2)))
                .andExpect(jsonPath("$.items[4].questionCountOneDay", Is.is(1)))
                .andExpect(jsonPath("$.items[4].questionCountWeekDay", Is.is(2)))
                .andExpect(jsonPath("$.items[5].id", Is.is(109)))
                .andExpect(jsonPath("$.items[5].title", Is.is("name10")))
                .andExpect(jsonPath("$.items[5].description", Is.is("description109")))
                .andExpect(jsonPath("$.items[5].questionCount", Is.is(4)))
                .andExpect(jsonPath("$.items[5].questionCountOneDay", Is.is(1)))
                .andExpect(jsonPath("$.items[5].questionCountWeekDay", Is.is(4)))
                .andExpect(jsonPath("$.itemsOnPage", Is.is(6)));
    }

    @Test
    @Sql(value = {"/script/TestTagResourceController/testGetAllTrackedTagAuthenticatedUser/Before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/script/TestTagResourceController/testGetAllTrackedTagAuthenticatedUser/After.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testGetAllTrackedTagAuthenticatedUser() throws Exception {
        //The user has TrackedTag
        String token = getToken("5@gmail.com", "5pwd");
        this.mvc.perform(get("/api/user/tag/tracked")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", Is.is(2)))
                .andExpect(jsonPath("$.[0].id", Is.is(101)))
                .andExpect(jsonPath("$.[0].name", Is.is("Java")))
                .andExpect(jsonPath("$.[1].id", Is.is(103)))
                .andExpect(jsonPath("$.[1].name", Is.is("C#")));

        //The user does not have TrackedTag
        String token2 = getToken("3@gmail.com", "3pwd");
        this.mvc.perform(get("/api/user/tag/tracked")
                        .header("Authorization", "Bearer " + token2)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", Is.is(0)));
    }

    @Test
    @SqlGroup({
            @Sql(value = {"/script/TestTagResourceController/testGetPageWithListTagDtoSortedByName/Before.sql"},
                    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = {"/script/TestTagResourceController/testGetPageWithListTagDtoSortedByName/After.sql"},
                    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    public void testGetPageWithListTagDtoSortedByName() throws Exception {
        String token = getToken("0@gmail.com", "0pwd");

        // getting 4 Tags from page number 1 ordered by name
        this.mvc.perform(get("/api/user/tag/name")
                        .header("Authorization", "Bearer " + token)
                        .param("currentPageNumber", "1")
                        .param("itemsOnPage", "4"))

                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.currentPageNumber", Is.is(1)))
                .andExpect(jsonPath("$.totalPageCount", Is.is(3)))
                .andExpect(jsonPath("$.totalResultCount", Is.is(9)))
                .andExpect(jsonPath("$.itemsOnPage", Is.is(4)))

                .andExpect(jsonPath("$.items[0].id", Is.is(100)))
                .andExpect(jsonPath("$.items[0].title", Is.is("name1")))
                .andExpect(jsonPath("$.items[0].description", Is.is("description100")))
                .andExpect(jsonPath("$.items[0].questionCount", Is.is(1)))
                .andExpect(jsonPath("$.items[0].questionCountOneDay", Is.is(1)))
                .andExpect(jsonPath("$.items[0].questionCountWeekDay", Is.is(1)))

                .andExpect(jsonPath("$.items[1].id", Is.is(101)))
                .andExpect(jsonPath("$.items[1].title", Is.is("name2")))
                .andExpect(jsonPath("$.items[1].description", Is.is("description101")))
                .andExpect(jsonPath("$.items[1].questionCount", Is.is(2)))
                .andExpect(jsonPath("$.items[1].questionCountOneDay", Is.is(1)))
                .andExpect(jsonPath("$.items[1].questionCountWeekDay", Is.is(2)))

                .andExpect(jsonPath("$.items[2].id", Is.is(102)))
                .andExpect(jsonPath("$.items[2].title", Is.is("name3")))
                .andExpect(jsonPath("$.items[2].description", Is.is("description102")))
                .andExpect(jsonPath("$.items[2].questionCount", Is.is(3)))
                .andExpect(jsonPath("$.items[2].questionCountOneDay", Is.is(1)))
                .andExpect(jsonPath("$.items[2].questionCountWeekDay", Is.is(3)))

                .andExpect(jsonPath("$.items[3].id", Is.is(104)))
                .andExpect(jsonPath("$.items[3].title", Is.is("name4")))
                .andExpect(jsonPath("$.items[3].description", Is.is("description104")))
                .andExpect(jsonPath("$.items[3].questionCount", Is.is(4)))
                .andExpect(jsonPath("$.items[3].questionCountOneDay", Is.is(2)))
                .andExpect(jsonPath("$.items[3].questionCountWeekDay", Is.is(4)));


        // getting Tag from last page number 3
        this.mvc.perform(get("/api/user/tag/name")
                        .header("Authorization", "Bearer " + token)
                        .param("currentPageNumber", "3")
                        .param("itemsOnPage", "4"))

                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.currentPageNumber", Is.is(3)))
                .andExpect(jsonPath("$.totalPageCount", Is.is(3)))
                .andExpect(jsonPath("$.totalResultCount", Is.is(9)))
                .andExpect(jsonPath("$.itemsOnPage", Is.is(4)))

                .andExpect(jsonPath("$.items[0].id", Is.is(107)))
                .andExpect(jsonPath("$.items[0].title", Is.is("name9")))
                .andExpect(jsonPath("$.items[0].description", Is.is("description107")))
                .andExpect(jsonPath("$.items[0].questionCount", Is.is(1)))
                .andExpect(jsonPath("$.items[0].questionCountOneDay", Is.is(1)))
                .andExpect(jsonPath("$.items[0].questionCountWeekDay", Is.is(1)));


        // getting Tags if  itemsOnPage is empty
        this.mvc.perform(get("/api/user/tag/name")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("currentPageNumber", "1"))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.currentPageNumber", Is.is(1)))
                .andExpect(jsonPath("$.totalPageCount", Is.is(1)))
                .andExpect(jsonPath("$.totalResultCount", Is.is(9)))
                .andExpect(jsonPath("$.itemsOnPage", Is.is(10)))

                .andExpect(jsonPath("$.items[3].id", Is.is(104)))
                .andExpect(jsonPath("$.items[3].title", Is.is("name4")))
                .andExpect(jsonPath("$.items[3].description", Is.is("description104")))
                .andExpect(jsonPath("$.items[3].questionCount", Is.is(4)))
                .andExpect(jsonPath("$.items[3].questionCountOneDay", Is.is(2)))
                .andExpect(jsonPath("$.items[3].questionCountWeekDay", Is.is(4)));


        // getting Tags if  currentPageNumber is empty
        this.mvc.perform(get("/api/user/tag/name")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("itemsOnPage", "5"))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.currentPageNumber", Is.is(1)))
                .andExpect(jsonPath("$.totalPageCount", Is.is(2)))
                .andExpect(jsonPath("$.totalResultCount", Is.is(9)))
                .andExpect(jsonPath("$.itemsOnPage", Is.is(5)))

                .andExpect(jsonPath("$.items[3].id", Is.is(104)))
                .andExpect(jsonPath("$.items[3].title", Is.is("name4")))
                .andExpect(jsonPath("$.items[3].description", Is.is("description104")))
                .andExpect(jsonPath("$.items[3].questionCount", Is.is(4)))
                .andExpect(jsonPath("$.items[3].questionCountOneDay", Is.is(2)))
                .andExpect(jsonPath("$.items[3].questionCountWeekDay", Is.is(4)));


        // getting Tags for default settings
        this.mvc.perform(get("/api/user/tag/name")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.currentPageNumber", Is.is(1)))
                .andExpect(jsonPath("$.totalPageCount", Is.is(1)))
                .andExpect(jsonPath("$.totalResultCount", Is.is(9)))
                .andExpect(jsonPath("$.itemsOnPage", Is.is(10)));


        // getting Tags if currentPageNumber is 0
        this.mvc.perform(get("/api/user/tag/name")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("currentPageNumber", "0"))

                .andExpect(status().is4xxClientError());


        // getting Tags if itemsOnPage is 0
        this.mvc.perform(get("/api/user/tag/name")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("itemsOnPage", "0"))

                .andExpect(status().is4xxClientError());


        // user is not authenticated
        this.mvc.perform(get("/api/user/tag/name"))

                .andExpect(status().is4xxClientError());
    }


    @Test
    @SqlGroup({
            @Sql(value = {"/script/TestTagResourceController/testGetSortedByPopularity/Before.sql"},
                    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = {"/script/TestTagResourceController/testGetSortedByPopularity/After.sql"},
                    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    public void testGetSortedByPopularity() throws Exception {
        String token = getToken("0@gmail.com", "0pwd");

        // getting 4 Tags from page number 1 ordered by popular
        this.mvc.perform(get("/api/user/tag/popular")
                        .header("Authorization", "Bearer " + token)
                        .param("currentPageNumber", "1")
                        .param("itemsOnPage", "4"))

                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.currentPageNumber", Is.is(1)))
                .andExpect(jsonPath("$.totalPageCount", Is.is(3)))
                .andExpect(jsonPath("$.totalResultCount", Is.is(9)))
                .andExpect(jsonPath("$.itemsOnPage", Is.is(4)))

                .andExpect(jsonPath("$.items[0].id", Is.is(104)))
                .andExpect(jsonPath("$.items[0].title", Is.is("name4")))
                .andExpect(jsonPath("$.items[0].description", Is.is("description104")))
                .andExpect(jsonPath("$.items[0].questionCount", Is.is(5)))
                .andExpect(jsonPath("$.items[0].questionCountOneDay", Is.is(3)))
                .andExpect(jsonPath("$.items[0].questionCountWeekDay", Is.is(5)))

                .andExpect(jsonPath("$.items[1].id", Is.is(102)))
                .andExpect(jsonPath("$.items[1].title", Is.is("name3")))
                .andExpect(jsonPath("$.items[1].description", Is.is("description102")))
                .andExpect(jsonPath("$.items[1].questionCount", Is.is(3)))
                .andExpect(jsonPath("$.items[1].questionCountOneDay", Is.is(1)))
                .andExpect(jsonPath("$.items[1].questionCountWeekDay", Is.is(3)))

                .andExpect(jsonPath("$.items[2].id", Is.is(103)))
                .andExpect(jsonPath("$.items[2].title", Is.is("name5")))
                .andExpect(jsonPath("$.items[2].description", Is.is("description103")))
                .andExpect(jsonPath("$.items[2].questionCount", Is.is(3)))
                .andExpect(jsonPath("$.items[2].questionCountOneDay", Is.is(2)))
                .andExpect(jsonPath("$.items[2].questionCountWeekDay", Is.is(3)))

                .andExpect(jsonPath("$.items[3].id", Is.is(101)))
                .andExpect(jsonPath("$.items[3].title", Is.is("name2")))
                .andExpect(jsonPath("$.items[3].description", Is.is("description101")))
                .andExpect(jsonPath("$.items[3].questionCount", Is.is(2)))
                .andExpect(jsonPath("$.items[3].questionCountOneDay", Is.is(1)))
                .andExpect(jsonPath("$.items[3].questionCountWeekDay", Is.is(2)));


        // getting Tag from last page number 3
        this.mvc.perform(get("/api/user/tag/popular")
                        .header("Authorization", "Bearer " + token)
                        .param("currentPageNumber", "3")
                        .param("itemsOnPage", "4"))

                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.currentPageNumber", Is.is(3)))
                .andExpect(jsonPath("$.totalPageCount", Is.is(3)))
                .andExpect(jsonPath("$.totalResultCount", Is.is(9)))
                .andExpect(jsonPath("$.itemsOnPage", Is.is(4)))

                .andExpect(jsonPath("$.items[0].id", Is.is(105)))
                .andExpect(jsonPath("$.items[0].title", Is.is("name6")))
                .andExpect(jsonPath("$.items[0].description", Is.is("description105")))
                .andExpect(jsonPath("$.items[0].questionCount", Is.is(0)))
                .andExpect(jsonPath("$.items[0].questionCountOneDay", Is.is(0)))
                .andExpect(jsonPath("$.items[0].questionCountWeekDay", Is.is(0)));


        // getting Tags if  itemsOnPage is empty
        this.mvc.perform(get("/api/user/tag/popular")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("currentPageNumber", "1"))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.currentPageNumber", Is.is(1)))
                .andExpect(jsonPath("$.totalPageCount", Is.is(1)))
                .andExpect(jsonPath("$.totalResultCount", Is.is(9)))
                .andExpect(jsonPath("$.itemsOnPage", Is.is(10)))

                .andExpect(jsonPath("$.items[0].id", Is.is(104)))
                .andExpect(jsonPath("$.items[0].title", Is.is("name4")))
                .andExpect(jsonPath("$.items[0].description", Is.is("description104")))
                .andExpect(jsonPath("$.items[0].questionCount", Is.is(5)))
                .andExpect(jsonPath("$.items[0].questionCountOneDay", Is.is(3)))
                .andExpect(jsonPath("$.items[0].questionCountWeekDay", Is.is(5)));


        // getting Tags if  currentPageNumber is empty
        this.mvc.perform(get("/api/user/tag/popular")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("itemsOnPage", "4"))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.currentPageNumber", Is.is(1)))
                .andExpect(jsonPath("$.totalPageCount", Is.is(3)))
                .andExpect(jsonPath("$.totalResultCount", Is.is(9)))
                .andExpect(jsonPath("$.itemsOnPage", Is.is(4)))

                .andExpect(jsonPath("$.items[3].id", Is.is(101)))
                .andExpect(jsonPath("$.items[3].title", Is.is("name2")))
                .andExpect(jsonPath("$.items[3].description", Is.is("description101")))
                .andExpect(jsonPath("$.items[3].questionCount", Is.is(2)))
                .andExpect(jsonPath("$.items[3].questionCountOneDay", Is.is(1)))
                .andExpect(jsonPath("$.items[3].questionCountWeekDay", Is.is(2)));


        // getting Tags for default settings
        this.mvc.perform(get("/api/user/tag/popular")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.currentPageNumber", Is.is(1)))
                .andExpect(jsonPath("$.totalPageCount", Is.is(1)))
                .andExpect(jsonPath("$.totalResultCount", Is.is(9)))
                .andExpect(jsonPath("$.itemsOnPage", Is.is(10)));


        // getting Tags if currentPageNumber is 0
        this.mvc.perform(get("/api/user/tag/popular")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("currentPageNumber", "0"))

                .andExpect(status().is4xxClientError());


        // getting Tags if itemsOnPage is 0
        this.mvc.perform(get("/api/user/tag/popular")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("itemsOnPage", "0"))

                .andExpect(status().is4xxClientError());


        // user is not authenticated
        this.mvc.perform(get("/api/user/tag/popular"))

                .andExpect(status().is4xxClientError());
    }


    @Test
    @SqlGroup({
            @Sql(value = {"/script/TestTagResourceController/testGetPageWithListTagDtoSortedBySyllable/Before.sql"},
                    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = {"/script/TestTagResourceController/testGetPageWithListTagDtoSortedBySyllable/After.sql"},
                    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })

    public void testGetPageWithListTagDtoSortedBySyllable() throws Exception {
        String token = getToken("0@gmail.com", "0pwd");


        //get the page containing "me"
        this.mvc.perform(get("/api/user/tag/syllable")
                        .header("Authorization", "Bearer " + token)
                        .param("currentPageNumber", "1")
                        .param("itemsOnPage", "4")
                        .param("syllable", "me"))

                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.currentPageNumber", Is.is(1)))
                .andExpect(jsonPath("$.totalPageCount", Is.is(3)))
                .andExpect(jsonPath("$.totalResultCount", Is.is(9)))
                .andExpect(jsonPath("$.itemsOnPage", Is.is(4)))

                .andExpect(jsonPath("$.items[0].id", Is.is(100)))
                .andExpect(jsonPath("$.items[0].title", Is.is("name1")))
                .andExpect(jsonPath("$.items[0].description", Is.is("description100")))
                .andExpect(jsonPath("$.items[0].questionCount", Is.is(1)))
                .andExpect(jsonPath("$.items[0].questionCountOneDay", Is.is(1)))
                .andExpect(jsonPath("$.items[0].questionCountWeekDay", Is.is(1)))

                .andExpect(jsonPath("$.items[1].id", Is.is(101)))
                .andExpect(jsonPath("$.items[1].title", Is.is("name2")))
                .andExpect(jsonPath("$.items[1].description", Is.is("description101")))
                .andExpect(jsonPath("$.items[1].questionCount", Is.is(2)))
                .andExpect(jsonPath("$.items[1].questionCountOneDay", Is.is(1)))
                .andExpect(jsonPath("$.items[1].questionCountWeekDay", Is.is(2)))

                .andExpect(jsonPath("$.items[2].id", Is.is(102)))
                .andExpect(jsonPath("$.items[2].title", Is.is("name3")))
                .andExpect(jsonPath("$.items[2].description", Is.is("description102")))
                .andExpect(jsonPath("$.items[2].questionCount", Is.is(3)))
                .andExpect(jsonPath("$.items[2].questionCountOneDay", Is.is(1)))
                .andExpect(jsonPath("$.items[2].questionCountWeekDay", Is.is(3)))

                .andExpect(jsonPath("$.items[3].id", Is.is(103)))
                .andExpect(jsonPath("$.items[3].title", Is.is("name4")))
                .andExpect(jsonPath("$.items[3].description", Is.is("description103")))
                .andExpect(jsonPath("$.items[3].questionCount", Is.is(3)))
                .andExpect(jsonPath("$.items[3].questionCountOneDay", Is.is(2)))
                .andExpect(jsonPath("$.items[3].questionCountWeekDay", Is.is(3)));


        //get the last page number 3 containing "me"
        this.mvc.perform(get("/api/user/tag/syllable")
                        .header("Authorization", "Bearer " + token)
                        .param("currentPageNumber", "3")
                        .param("itemsOnPage", "4")
                        .param("syllable", "me"))

                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.currentPageNumber", Is.is(3)))
                .andExpect(jsonPath("$.totalPageCount", Is.is(3)))
                .andExpect(jsonPath("$.totalResultCount", Is.is(9)))
                .andExpect(jsonPath("$.itemsOnPage", Is.is(4)))

                .andExpect(jsonPath("$.items[0].id", Is.is(108)))
                .andExpect(jsonPath("$.items[0].title", Is.is("name95")))
                .andExpect(jsonPath("$.items[0].description", Is.is("description108")))
                .andExpect(jsonPath("$.items[0].questionCount", Is.is(2)))
                .andExpect(jsonPath("$.items[0].questionCountOneDay", Is.is(1)))
                .andExpect(jsonPath("$.items[0].questionCountWeekDay", Is.is(2)));


        // getting Tags containing "1" if  itemsOnPage is empty
        this.mvc.perform(get("/api/user/tag/syllable")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("currentPageNumber", "1")
                        .param("syllable", "1"))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.currentPageNumber", Is.is(1)))
                .andExpect(jsonPath("$.totalPageCount", Is.is(1)))
                .andExpect(jsonPath("$.totalResultCount", Is.is(2)))
                .andExpect(jsonPath("$.itemsOnPage", Is.is(10)))

                .andExpect(jsonPath("$.items[0].id", Is.is(100)))
                .andExpect(jsonPath("$.items[0].title", Is.is("name1")))
                .andExpect(jsonPath("$.items[0].description", Is.is("description100")))
                .andExpect(jsonPath("$.items[0].questionCount", Is.is(1)))
                .andExpect(jsonPath("$.items[0].questionCountOneDay", Is.is(1)))
                .andExpect(jsonPath("$.items[0].questionCountWeekDay", Is.is(1)))

                .andExpect(jsonPath("$.items[1].id", Is.is(104)))
                .andExpect(jsonPath("$.items[1].title", Is.is("name51")))
                .andExpect(jsonPath("$.items[1].description", Is.is("description104")))
                .andExpect(jsonPath("$.items[1].questionCount", Is.is(5)))
                .andExpect(jsonPath("$.items[1].questionCountOneDay", Is.is(3)))
                .andExpect(jsonPath("$.items[1].questionCountWeekDay", Is.is(5)));


        // getting Tags containing "1" if  currentPageNumber is empty
        this.mvc.perform(get("/api/user/tag/syllable")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("itemsOnPage", "10")
                        .param("syllable", "1"))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.currentPageNumber", Is.is(1)))
                .andExpect(jsonPath("$.totalPageCount", Is.is(1)))
                .andExpect(jsonPath("$.totalResultCount", Is.is(2)))
                .andExpect(jsonPath("$.itemsOnPage", Is.is(10)))

                .andExpect(jsonPath("$.items[0].id", Is.is(100)))
                .andExpect(jsonPath("$.items[0].title", Is.is("name1")))
                .andExpect(jsonPath("$.items[0].description", Is.is("description100")))
                .andExpect(jsonPath("$.items[0].questionCount", Is.is(1)))
                .andExpect(jsonPath("$.items[0].questionCountOneDay", Is.is(1)))
                .andExpect(jsonPath("$.items[0].questionCountWeekDay", Is.is(1)))

                .andExpect(jsonPath("$.items[1].id", Is.is(104)))
                .andExpect(jsonPath("$.items[1].title", Is.is("name51")))
                .andExpect(jsonPath("$.items[1].description", Is.is("description104")))
                .andExpect(jsonPath("$.items[1].questionCount", Is.is(5)))
                .andExpect(jsonPath("$.items[1].questionCountOneDay", Is.is(3)))
                .andExpect(jsonPath("$.items[1].questionCountWeekDay", Is.is(5)));


        // getting Tags if syllable is empty
        this.mvc.perform(get("/api/user/tag/syllable")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("currentPageNumber", "1")
                        .param("itemsOnPage", "10"))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.currentPageNumber", Is.is(1)))
                .andExpect(jsonPath("$.totalPageCount", Is.is(0)))
                .andExpect(jsonPath("$.totalResultCount", Is.is(0)))
                .andExpect(jsonPath("$.itemsOnPage", Is.is(10)));


        // getting Tags for default settings
        this.mvc.perform(get("/api/user/tag/syllable")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.currentPageNumber", Is.is(1)))
                .andExpect(jsonPath("$.totalPageCount", Is.is(0)))
                .andExpect(jsonPath("$.totalResultCount", Is.is(0)))
                .andExpect(jsonPath("$.itemsOnPage", Is.is(10)));


        // getting Tags if syllable is not found
        this.mvc.perform(get("/api/user/tag/syllable")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("currentPageNumber", "1")
                        .param("itemsOnPage", "10")
                        .param("syllable", "ZZ"))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.currentPageNumber", Is.is(1)))
                .andExpect(jsonPath("$.totalPageCount", Is.is(0)))
                .andExpect(jsonPath("$.totalResultCount", Is.is(0)))
                .andExpect(jsonPath("$.itemsOnPage", Is.is(10)));


        // getting Tags if currentPageNumber is 0
        this.mvc.perform(get("/api/user/tag/syllable")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("currentPageNumber", "0"))

                .andExpect(status().is4xxClientError());


        // getting Tags if itemsOnPage is 0
        this.mvc.perform(get("/api/user/tag/syllable")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("itemsOnPage", "0"))

                .andExpect(status().is4xxClientError());


        // user is not authenticated
        this.mvc.perform(get("/api/user/tag/syllable"))

                .andExpect(status().is4xxClientError());
    }
}