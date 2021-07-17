/*
 * Copyright (c) 2021  Dinusha Jayawardena. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 */

package util;

import redis.clients.jedis.Jedis;

public class JedisClient {
    private static JedisClient jedisClient;
    private final Jedis client;

    private JedisClient(){
        client = new Jedis("localhost", 6060);
        client.auth("redis");
    }

    public static JedisClient getInstance(){
        return (jedisClient == null) ? jedisClient = new JedisClient() : jedisClient;
    }

    public Jedis getClient(){
        return client;
    }
}
