package com.microservices.rest.webservice.restfulwebservices.user;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class UserSocialMediaDaoService {
    private static List<UserSocialMedia> users = new ArrayList<>();

    private static int usersCount = 3;

    static {
        users.add(new UserSocialMedia(1, "Adam", "20.06.1980"));
        users.add(new UserSocialMedia(2, "Eve", "20.06.1980"));
        users.add(new UserSocialMedia(3, "Jack", "20.06.1980"));
    }

    public List<UserSocialMedia> findAll() {
        return users;
    }

    public UserSocialMedia save(UserSocialMedia user) {
        if (user.getId() == null) {
            user.setId(++usersCount);
        }
        users.add(user);
        return user;
    }

    public UserSocialMedia findOne(int id) {
        for (UserSocialMedia user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public UserSocialMedia deleteById(int id) {
        Iterator<UserSocialMedia> iterator = users.iterator();
        while (iterator.hasNext()) {
            UserSocialMedia user = iterator.next();
            if (user.getId() == id) {
                iterator.remove();
                return user;
            }
        }
        return null;
    }
}
