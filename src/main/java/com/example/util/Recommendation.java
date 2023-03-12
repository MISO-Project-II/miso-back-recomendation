package com.example.util;

import com.example.model.Item;
import com.example.model.User;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.impl.recommender.CachingRecommender;
import org.apache.mahout.cf.taste.impl.recommender.slopeone.SlopeOneRecommender;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;

import java.util.ArrayList;
import java.util.List;

public class Recommendation {

    List<User> users = new ArrayList<>();




    public static void main(String[] args) {

    }
}
//    public FastByIDMap<PreferenceArray> getUserData() {
//
//        FastByIDMap<PreferenceArray> preferences = new FastByIDMap<>();
//        PreferenceArray userPreference = new GenericUserPreferenceArray(10);
//
//        for (Data data : list) {
////            userPreference.setUserID(data.getIdUser());
//            userPreference.setValue(data.getIdItem(),1);
//        }
//
//
//        return preferences;
//    }

//    public Recommender getRecommender() throws TasteException {
//        DataModel model = new GenericDataModel(getUserData());
//        CachingRecommender cachingRecommender = new CachingRecommender(new SlopeOneRecommender(model));
//        UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
//        UserNeighborhood neighborhood = new NearestNUserNeighborhood(
//                5, similarity, model);
//        Recommender recommender = new GenericUserBasedRecommender(model,neighborhood,similarity);
//        return recommender;
//    }
//    public List<RecommendedItem> getRecomendation(Recommender recommender
//    , int idUser, int numberOfRecomendations) throws TasteException {
//        return recommender.recommend(idUser, numberOfRecomendations);
//    }