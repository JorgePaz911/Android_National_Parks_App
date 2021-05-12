package com.example.nationalparks.data;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.nationalparks.controller.AppController;
import com.example.nationalparks.model.Activities;
import com.example.nationalparks.model.EntranceFees;
import com.example.nationalparks.model.Images;
import com.example.nationalparks.model.OperatingHours;
import com.example.nationalparks.model.Park;
import com.example.nationalparks.model.StandardHours;
import com.example.nationalparks.model.Topics;
import com.example.nationalparks.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//This is where JSON will be fetched
public class Repository {

    //each park that gets created is then added to this ArrayList
    static List<Park> parkList = new ArrayList<>();

    //Fetches JSON information and puts it into a JSONArray
    public static void getParks(final AsyncResponse callback, String stateCode){
        String url = Util.getParksUrl(stateCode);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {

            try {
                JSONArray jsonArray = response.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    Park park = new Park();
                    //Gets the full JSONArray then puts each element into a JSONObject
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    //The getString must be the exact same string as the JSON field from the website.
                    park.setId(jsonObject.getString("id"));
                    park.setFullName(jsonObject.getString("fullName"));
                    park.setLatitude(jsonObject.getString("latitude"));
                    park.setLongitude(jsonObject.getString("longitude"));
                    park.setParkCode(jsonObject.getString("parkCode"));
                    park.setStates(jsonObject.getString("states"));

                    JSONArray imageList = jsonObject.getJSONArray("images");
                    List<Images> list = new ArrayList<>();
                    for (int j = 0; j < imageList.length(); j++) {
                        Images images = new Images();
                        images.setCredit(imageList.getJSONObject(j).getString("credit"));
                        images.setTitle(imageList.getJSONObject(j).getString("title"));
                        images.setUrl(imageList.getJSONObject(j).getString("url"));

                        list.add(images);
                    }
                    park.setImages(list);

                    park.setWeatherInfo(jsonObject.getString("weatherInfo"));
                    park.setName(jsonObject.getString("name"));
                    park.setDesignation(jsonObject.getString("designation"));

                    JSONArray activityArray = jsonObject.getJSONArray("activities");
                    List<Activities> activityList = new ArrayList<>();

                    for (int j = 0; j < activityArray.length(); j++) {
                        Activities activities = new Activities();
                        activities.setId(activityArray.getJSONObject(j).getString("id"));
                        activities.setName(activityArray.getJSONObject(j).getString("name"));
                        activityList.add(activities);
                    }
                    park.setActivities(activityList);

                    JSONArray topicsArray = jsonObject.getJSONArray("topics");
                    List<Topics> topicList = new ArrayList<>();
                    for (int j = 0; j < topicsArray.length(); j++) {
                        Topics topic = new Topics();
                        topic.setId(topicsArray.getJSONObject(j).getString("id"));
                        topic.setName(topicsArray.getJSONObject(j).getString("name"));
                        topicList.add(topic);
                    }
                    park.setTopics(topicList);

                    JSONArray opHours = jsonObject.getJSONArray("operatingHours");
                    List<OperatingHours> operatingHours = new ArrayList<>();
                    for (int j = 0; j < opHours.length(); j++) {
                        OperatingHours op = new OperatingHours();
                        op.setDescription(opHours.getJSONObject(j).getString("description"));
                        StandardHours standardHours = new StandardHours();
                        JSONObject hours = opHours.getJSONObject(j).getJSONObject("standardHours");

                        standardHours.setMonday(hours.getString("monday"));
                        standardHours.setTuesday(hours.getString("tuesday"));
                        standardHours.setWednesday(hours.getString("wednesday"));
                        standardHours.setThursday(hours.getString("thursday"));
                        standardHours.setFriday(hours.getString("friday"));
                        standardHours.setSaturday(hours.getString("saturday"));
                        standardHours.setSunday(hours.getString("sunday"));

                        op.setStandardHours(standardHours);
                        operatingHours.add(op);
                    }
                    park.setOperatingHours(operatingHours);

                    park.setDirectionsInfo(jsonObject.getString("directionsInfo"));

                    JSONArray entranceFeesArray = jsonObject.getJSONArray("entranceFees");
                    List<EntranceFees> entranceFeesList = new ArrayList<>();
                    for (int j = 0; j < entranceFeesArray.length(); j++) {
                        EntranceFees entranceFees = new EntranceFees();
                        entranceFees.setCost(entranceFeesArray.getJSONObject(j).getString("cost"));
                        entranceFees.setDescription(entranceFeesArray.getJSONObject(j).getString("description"));
                        entranceFees.setTitle(entranceFeesArray.getJSONObject(j).getString("title"));
                        entranceFeesList.add(entranceFees);
                    }
                    park.setEntranceFees(entranceFeesList);

                    park.setWeatherInfo(jsonObject.getString("weatherInfo"));

                    park.setDescription(jsonObject.getString("description"));

                    parkList.add(park);
                }

                //Whoever uses this callback will have the parkList available to them.
                if(callback != null){
                    callback.processParks(parkList);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        },error -> {
           error.printStackTrace();
        });
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
    }
}
