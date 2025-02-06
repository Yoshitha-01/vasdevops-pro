package com.visualpathit.account.controller;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.google.gson.Gson;
import com.visualpathit.account.model.User;
import com.visualpathit.account.service.UserService;
import com.visualpathit.account.utils.ElasticsearchUtil;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

@Controller
@RequestMapping("/elasticsearch")
public class ElasticSearchController {

    @Autowired
    private UserService userService;

    /**
     * Inserts user data into Elasticsearch.
     */
    @GetMapping("/insert")
    public String addUserDataToElasticsearch(Model model) throws IOException {
        List<User> userList = userService.getList();
        String responseMessage = "";

        for (User user : userList) {
            IndexResponse response = ElasticsearchUtil.trannsportClient()
                    .prepareIndex("users", "user", String.valueOf(user.getId()))
                    .setSource(jsonBuilder()
                            .startObject()
                            .field("name", user.getUsername())
                            .field("DOB", user.getDateOfBirth())
                            .field("fatherName", user.getFatherName())
                            .field("motherName", user.getMotherName())
                            .field("gender", user.getGender())
                            .field("nationality", user.getNationality())
                            .field("phoneNumber", user.getPhoneNumber())
                            .endObject()
                    ).get();

            System.out.println("Indexing Status: " + response.getResult().toString());
            responseMessage = "User Data Indexed";
        }

        model.addAttribute("response", responseMessage);
        return "elasticeSearchRes";
    }

    /**
     * Retrieves a user record from Elasticsearch based on ID.
     */
    @GetMapping("/view/{id}")
    public String getUserById(@PathVariable String id, Model model) {
        GetResponse response = ElasticsearchUtil.trannsportClient()
                .prepareGet("users", "user", id)
                .get();

        System.out.println("Retrieved User Info: " + response.getSource());

        model.addAttribute("res", response.getSource().get("name"));
        return "elasticeSearchRes";
    }

    /**
     * Updates the gender field for a specific user.
     */
    @GetMapping("/update/{id}")
    public String modifyUser(@PathVariable String id, Model model) throws IOException {
        UpdateRequest updateRequest = new UpdateRequest("employee", "id", id)
                .doc(jsonBuilder()
                        .startObject()
                        .field("gender", "male")
                        .endObject());

        try {
            UpdateResponse updateResponse = ElasticsearchUtil.trannsportClient().update(updateRequest).get();
            System.out.println("Update Status: " + updateResponse.status());
            model.addAttribute("res", updateResponse.status());
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Update Failed: " + e.getMessage());
        }
        return "elasticeSearchRes";
    }

    /**
     * Deletes a user record from Elasticsearch using an ID.
     */
    @GetMapping("/delete/{id}")
    public String removeUser(@PathVariable String id, Model model) {
        DeleteResponse deleteResponse = ElasticsearchUtil.trannsportClient()
                .prepareDelete("employee", "id", id)
                .get();

        System.out.println("Deletion Status: " + deleteResponse.getResult().toString());
        model.addAttribute("res", deleteResponse.getResult().toString());
        return "elasticeSearchRes";
    }
}
