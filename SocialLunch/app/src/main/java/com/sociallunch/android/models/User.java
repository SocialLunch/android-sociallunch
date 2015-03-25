package com.sociallunch.android.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import org.json.JSONObject;

/**
 * Created by kelei on 3/22/15.
 */
@Table(name = "Users")
public class User extends Model {
    @Column(name = "uid", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private String uid;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "company")
    private String company;

    @Column(name = "pictureUrl")
    private String pictureUrl;

    @Column(name = "foodsLiked")
    private String foodsLiked;

    @Column(name = "foodsDisliked")
    private String foodsDisliked;

    public static User fromJSON(JSONObject jsonObject, boolean save) {
        User u = new User();
        try {
            u.uid = jsonObject.getString("id");
            u.firstName = jsonObject.getString("firstName");
            u.lastName = jsonObject.getString("lastName");
            u.company = jsonObject.getString("headline");
            u.pictureUrl = jsonObject.getString("pictureUrl");
            if (save) {
                u.save();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return u;
    }

    public static User byId(String id) {
        return new Select()
            .from(User.class)
            .where("uid = ?", id)
            .executeSingle();
    }

    public String getUid() {
        return uid;
    }

    public void setFoodsLiked(String foodsLiked) {
        this.foodsLiked = foodsLiked;
    }

    public void setFoodsDisliked(String foodsDisliked) {
        this.foodsDisliked = foodsDisliked;
    }
}
