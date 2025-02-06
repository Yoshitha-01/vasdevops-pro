package com.visualpathit.account.controller;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.visualpathit.account.model.User;
import com.visualpathit.account.service.UserService;
import com.visualpathit.account.utils.ElasticsearchUtil;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

@Controller
public class ElasticSearchController {

    @Autowired
    private UserService userService;

    // Endpoint to insert user data into Elasticsearch
    @RequestMapping(value="/user/elasticsearch", method=RequestMethod.GET)
    public String insert(Model model) throws IOException {
        List<User> users = userService.getList();  // Retrieve all users
        
        String resultMessage = "Users";  // Default result message
        for (User user : users) {
            // Creating an index request for each user
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
                    .endObject())
                .get();
                
            // Logging the response result
            System.out.println(response.getResult().toString());
        }
        
        model.addAttribute(resultMessage);  // Add result message to model
        return "elasticeSearchRes";  // Return the view
    }

    // Endpoint to view a user from Elasticsearch by ID
    @RequestMapping(value="/rest/users/view/{id}", method=RequestMethod.GET)
    public String view(@PathVariable String id, Model model) {
        GetResponse getResponse = ElasticsearchUtil.trannsportClient().prepareGet("users", "user", id).get();
        System.out.println(getResponse.getSource());  // Log user data
        
        model.addAttribute("res", getResponse.getSource().get("name"));  // Add user's name to model
        return "elasticeSearchRes";  // Return the view
    }

    // Endpoint to update a user's data in Elasticsearch
    @RequestMapping(value="/rest/users/update/{id}", method=RequestMethod.GET)
    public String update(@PathVariable String id, Model model) throws IOException {
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index("employee")
                     .type("id")
                     .id(id)
                     .doc(jsonBuilder()
                             .startObject()
                             .field("gender", "male")  // Example of data update
                             .endObject());

        try {
            // Update the user data and get the response status
            UpdateResponse updateResponse = ElasticsearchUtil.trannsportClient().update(updateRequest).get();
            System.out.println(updateResponse.status());  // Log the update status
            model.addAttribute("res", updateResponse.status());  // Add status to model
            return "elasticeSearchRes";  // Return the view
        } catch (InterruptedException | ExecutionException e) {
            System.out.println(e);  // Log any errors
        }
        
        return "elasticeSearchRes";  // Return the view
    }

    // Endpoint to delete a user from Elasticsearch by ID
    @RequestMapping(value="/rest/users/delete/{id}", method=RequestMethod.GET)
    public String delete(@PathVariable String id, Model model) {
        // Delete user by ID and get the response result
        DeleteResponse deleteResponse = ElasticsearchUtil.trannsportClient().prepareDelete("employee", "id", id).get();
        System.out.println(deleteResponse.getResult().toString());  // Log the delete result
        
        model.addAttribute("res", deleteResponse.getResult().toString());  // Add result to model
        return "elasticeSearchRes";  // Return the view
    }
}
