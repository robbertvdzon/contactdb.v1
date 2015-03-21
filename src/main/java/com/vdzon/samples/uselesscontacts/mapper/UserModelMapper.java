package com.vdzon.samples.uselesscontacts.mapper;

import com.vdzon.samples.uselesscontacts.data.User;
import com.vdzon.samples.uselesscontacts.model.UserModel;

public class UserModelMapper {

    public static void mergeModel(final UserModel model, User user){
        user.setUsername(model.getUsername());
    }

    public static UserModel toModel(final User user){
        UserModel userModel = new UserModel();
        userModel.setUsername(user.getUsername());
        userModel.setUuid(user.getUuid());
        return userModel;
    }

}
