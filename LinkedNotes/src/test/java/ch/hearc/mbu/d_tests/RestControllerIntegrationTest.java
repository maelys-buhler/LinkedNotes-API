package ch.hearc.mbu.d_tests;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class RestControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testUserGetApiKey() throws Exception {
        MvcResult res = mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"testUser\"}"))
                .andExpect(status().isOk())
                .andReturn();
        String content = res.getResponse().getContentAsString();
        String apiKey = content.split(":")[1].trim();
    }

    @Test
    public void testTagEndpointsWithValidApiKey() throws Exception {
        //get valid api key
        MvcResult res = mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"testUserTag\"}"))
                .andExpect(status().isOk())
                .andReturn();
        String content = res.getResponse().getContentAsString();
        String apiKey = content.split(":")[1].trim();

        //create tag
        mvc.perform(post("/" + apiKey + "/tags")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"testTag\"}"))
                .andExpect(status().isOk());

        //get tags
        res = mvc.perform(get("/" + apiKey + "/tags")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        content = res.getResponse().getContentAsString();
        System.out.println(content);
        JSONArray tags = (JSONArray) new JSONParser().parse(content);
        Assertions.assertThat(tags.size()).isGreaterThan(0);

        //get tag
        res = mvc.perform(get("/" + apiKey + "/tags/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testNoteEndpointsWithValidApiKey() throws Exception {
        MvcResult res = mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"testUserNote\"}"))
                .andExpect(status().isOk())
                .andReturn();
        String content = res.getResponse().getContentAsString();
        String apiKey = content.split(":")[1].trim();

        //create tag
       res = mvc.perform(post("/" + apiKey + "/tags")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"testNoteTag\"}"))
                .andExpect(status().isOk())
                .andReturn();
        content = res.getResponse().getContentAsString();
        long tagId = Long.parseLong(String.valueOf(content.split(":")[1].charAt(0)));

        //create note
        res = mvc.perform(post("/" + apiKey + "/notes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"testNote\",\"content\":\"testNoteContent\",\"tags\":[{\"id\":" + tagId + "}]}"))
                .andExpect(status().isOk())
                .andReturn();
        content = res.getResponse().getContentAsString();
        long noteId = Long.parseLong(String.valueOf(content.split(":")[1].charAt(0)));

        //get notes
        res = mvc.perform(get("/" + apiKey + "/notes")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();


        //get note
        res = mvc.perform(get("/" + apiKey + "/notes/" + noteId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //update note
        res = mvc.perform(put("/" + apiKey + "/notes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":" + noteId + ",\"title\":\"testNoteUpdated\",\"content\":\"testNoteContentUpdated\",\"tags\":[{\"id\":" + tagId + "}]}"))
                .andExpect(status().isOk())
                .andReturn();

        //delete note
        res = mvc.perform(delete("/" + apiKey + "/notes/" + noteId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    public void testTypeEndpointWithValidApiKey() throws Exception {
        //get api key
        MvcResult res = mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"testUserType\"}"))
                .andExpect(status().isOk())
                .andReturn();
        String content = res.getResponse().getContentAsString();
        String apiKey = content.split(":")[1].trim();

        //create type
        res = mvc.perform(post("/" + apiKey + "/types")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"testType\"}"))
                .andExpect(status().isOk())
                .andReturn();

        //get types
        res = mvc.perform(get("/" + apiKey + "/types")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //get type
        res = mvc.perform(get("/" + apiKey + "/types/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

    }

    @Test
    public void testLinkEndpointWithAValidApiKey() throws Exception {
        //get api key
        MvcResult res = mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"testUserLink\"}"))
                .andExpect(status().isOk())
                .andReturn();
        String content = res.getResponse().getContentAsString();
        String apiKey = content.split(":")[1].trim();

        //create tag
        res = mvc.perform(post("/" + apiKey + "/tags")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"testLinkTag\"}"))
                .andExpect(status().isOk())
                .andReturn();
        content = res.getResponse().getContentAsString();
        long tagId = Long.parseLong(String.valueOf(content.split(":")[1].charAt(0)));
        System.out.println("tagId: " + tagId); // "1

        //create note1
        res = mvc.perform(post("/" + apiKey + "/notes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"testNote1\",\"content\":\"testNoteContent1\",\"tags\":[{\"id\":" + tagId + "}]}"))
                .andExpect(status().isOk())
                .andReturn();
        content = res.getResponse().getContentAsString();
        long note1Id = Long.parseLong(String.valueOf(content.split(":")[1].charAt(0)));

        //create note2
        res = mvc.perform(post("/" + apiKey + "/notes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"testNote2\",\"content\":\"testNoteContent2\",\"tags\":[{\"id\":" + tagId + "}]}"))
                .andExpect(status().isOk())
                .andReturn();
        content = res.getResponse().getContentAsString();
        long note2Id = Long.parseLong(String.valueOf(content.split(":")[1].charAt(0)));

        //create type
        res = mvc.perform(post("/" + apiKey + "/types")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"testLinkType\"}"))
                .andExpect(status().isOk())
                .andReturn();
        content = res.getResponse().getContentAsString();
        long typeId = Long.parseLong(String.valueOf(content.split(":")[1].charAt(0)));

        //create link
        res = mvc.perform(post("/" + apiKey + "/links")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"testLink\", \"color\":\"#FFFFFF\",\"type\":{\"id\":" + typeId + "},\"note1\":{\"id\":" + note1Id + "},\"note2\":{\"id\":" + note2Id + "}}"))
                .andExpect(status().isOk())
                .andReturn();
        content = res.getResponse().getContentAsString();
        long linkId = Long.parseLong(String.valueOf(content.split(":")[1].charAt(0)));

        //get links
        res = mvc.perform(get("/" + apiKey + "/links")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //get link
        res = mvc.perform(get("/" + apiKey + "/links/" + linkId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //update link
        res = mvc.perform(put("/" + apiKey + "/links")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":"+ linkId + ",\"name\":\"testLinkUpdated\",\"color\":\"#FFFFFF\",\"type\":{\"id\":" + typeId + "},\"note1\":{\"id\":" + note1Id + "},\"note2\":{\"id\":" + note2Id + "}}"))
                .andExpect(status().isOk())
                .andReturn();

        //delete link
        res = mvc.perform(delete("/" + apiKey + "/links/" + linkId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();
    }
}
