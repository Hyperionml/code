package com.heima.item.canal;

import com.github.benmanes.caffeine.cache.Cache;
import com.heima.item.config.RedisHandler;
import com.heima.item.pojo.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.javatool.canal.client.annotation.CanalTable;
import top.javatool.canal.client.handler.EntryHandler;

@CanalTable("tb_item")
@Component
public class ItemHandler implements EntryHandler<Item> {

    @Autowired
    private RedisHandler redisHandler;
    @Autowired
    private Cache<Long, Item> itemCache;

    @Override
    public void insert(Item item) {
        // jvm
        itemCache.put(item.getId(), item);
        // redis
        redisHandler.saveItem(item);
    }

    @Override
    public void update(Item before, Item after) {
        // jvm
        itemCache.put(after.getId(), after);
        // redis
        redisHandler.saveItem(after);
    }

    @Override
    public void delete(Item item) {
        // jvm
        itemCache.invalidate(item.getId());
        // redis
        redisHandler.deleteItemById(item.getId());

    }


}
