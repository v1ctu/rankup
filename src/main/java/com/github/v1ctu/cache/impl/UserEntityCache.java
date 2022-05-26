package com.github.v1ctu.cache.impl;

import com.github.v1ctu.RankupPlugin;
import com.github.v1ctu.cache.Cache;
import com.github.v1ctu.entity.UserEntity;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class UserEntityCache extends Cache<UUID, UserEntity> {

    private final RankupPlugin rankupPlugin;

    public void load() {

        for (UserEntity userEntity : rankupPlugin.getUserEntityDao().find()) {
            put(userEntity.getUuid(), userEntity);
        }
    }

    public void save() {
        for (UserEntity userEntity : getValues()) {
            rankupPlugin.getUserEntityDao().replace(userEntity);
        }
    }

}
