package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.impl.recommender.CachingRecommender;
import org.apache.mahout.cf.taste.impl.recommender.slopeone.SlopeOneRecommender;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.jboss.logging.Logger;

import com.example.model.Item;
import com.example.model.User;
import com.example.repository.ItemRepository;
import com.example.repository.UserItemRepository;
import com.example.repository.UserRepository;

@Path("/hello")
public class ExampleResource {

	@Inject
	ItemRepository itemRepository;
	@Inject
	UserItemRepository userItemRepository;
	@Inject
	UserRepository userRepository;
	@Inject
	Logger log;

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String hello() throws TasteException {

		Optional<User> getUser= userRepository.findById(1L);
		log.info(getRecomendationForuser(getUser.get()));

		return "Hello RESTEasy";
	}
	private List<Item> getRecomendationForuser(User user) throws TasteException {
		List<Item> items = new ArrayList<>();
		DataModel model = new GenericDataModel((getUserData()));
		CachingRecommender cachingRecommender = new CachingRecommender(new SlopeOneRecommender(model));

		List<RecommendedItem> recommend = cachingRecommender.recommend(user.getIdUser(), 10);

		for (RecommendedItem recommendedItem : recommend) {
			Optional<Item> getItem = itemRepository.findById(recommendedItem.getItemID());
			if(getItem.isPresent()){
				items.add(getItem.get());
			}

		}
		return items;
	}

	private FastByIDMap<PreferenceArray> getUserData() {
		FastByIDMap<PreferenceArray> preferences = new FastByIDMap<PreferenceArray>();
		List<User> users = userRepository.findAll();
		users.forEach(user -> {
			PreferenceArray userPreferences = new GenericUserPreferenceArray(10);
			int i = 0;

			userItemRepository.findByUser(user).forEach(userItem -> {
				userItem.getItems().forEach(item -> {
					userPreferences.setUserID(i,userItem.getUser().getIdUser());
					userPreferences.setItemID(i,userItem.getPkUserItem());
					userPreferences.setValue(i,1);
				});
			});
			preferences.put(user.getIdUser(), userPreferences);
		});
		return preferences;
	}
}