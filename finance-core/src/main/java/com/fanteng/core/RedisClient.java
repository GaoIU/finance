package com.fanteng.core;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class RedisClient {

	private ShardedJedisPool shardedJedisPool;

	public ShardedJedisPool getShardedJedisPool() {
		return shardedJedisPool;
	}

	public void setShardedJedisPool(ShardedJedisPool shardedJedisPool) {
		this.shardedJedisPool = shardedJedisPool;
	}

	public ShardedJedis getJedis() {
		ShardedJedis jedis = shardedJedisPool.getResource();
		return jedis;
	}

	public String get(String key) {
		String json = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			json = jedis.get(key);
		}
		return json;
	}

	public byte[] get(byte[] key) {
		byte[] bs = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			bs = jedis.get(key);
		}
		return bs;
	}

	public String set(String key, String value) {
		String set = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			set = jedis.set(key, value);
		}
		return set;
	}

	public String set(byte[] key, byte[] value) {
		String set = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			set = jedis.set(key, value);
		}
		return set;
	}

	public String set(String key, String value, String nxxx) {
		String set = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			set = jedis.set(key, value, nxxx);
		}
		return set;
	}

	public String set(byte[] key, byte[] value, byte[] nxxx) {
		String set = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			set = jedis.set(key, value, nxxx);
		}
		return set;
	}

	public String set(String key, String value, String nxxx, String expx, long time) {
		String set = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			set = jedis.set(key, value, nxxx, expx, time);
		}
		return set;
	}

	public String set(byte[] key, byte[] value, byte[] nxxx, byte[] expx, long time) {
		String set = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			set = jedis.set(key, value, nxxx, expx, time);
		}
		return set;
	}

	public String setex(String key, int seconds, String value) {
		String set = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			set = jedis.setex(key, seconds, value);
		}
		return set;
	}

	public String setex(byte[] key, int seconds, byte[] value) {
		String set = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			set = jedis.setex(key, seconds, value);
		}
		return set;
	}

	public Long append(String key, String value) {
		Long append = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			append = jedis.append(key, value);
		}
		return append;
	}

	public Long append(byte[] key, byte[] value) {
		Long append = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			append = jedis.append(key, value);
		}
		return append;
	}

	public Long del(String key) {
		Long del = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			del = jedis.del(key);
		}
		return del;
	}

	public Long del(byte[] key) {
		Long del = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			del = jedis.del(key);
		}
		return del;
	}

	public Boolean exists(String key) {
		Boolean exists = false;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			exists = jedis.exists(key);
		}
		return exists;
	}

	public Boolean exists(byte[] key) {
		Boolean exists = false;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			exists = jedis.exists(key);
		}
		return exists;
	}

	public Long expire(String key, int seconds) {
		Long expire = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			expire = jedis.expire(key, 1);
		}
		return expire;
	}

	public Long expire(byte[] key, int seconds) {
		Long expire = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			expire = jedis.expire(key, 1);
		}
		return expire;
	}

	public Long expireAt(String key, long unixTime) {
		Long expireAt = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			expireAt = jedis.expireAt(key, unixTime);
		}
		return expireAt;
	}

	public Long expireAt(byte[] key, long unixTime) {
		Long expireAt = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			expireAt = jedis.expireAt(key, unixTime);
		}
		return expireAt;
	}

	public String hmset(String key, Map<String, String> hash) {
		String hmset = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			hmset = jedis.hmset(key, hash);
		}
		return hmset;
	}

	public String hmset(byte[] key, Map<byte[], byte[]> hash) {
		String hmset = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			hmset = jedis.hmset(key, hash);
		}
		return hmset;
	}

	public Long hset(String key, String field, String value) {
		Long hset = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			hset = jedis.hset(key, field, value);
		}
		return hset;
	}

	public Long hset(byte[] key, byte[] field, byte[] value) {
		Long hset = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			hset = jedis.hset(key, field, value);
		}
		return hset;
	}

	public Long hsetnx(String key, String field, String value) {
		Long hsetnx = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			hsetnx = jedis.hsetnx(key, field, value);
		}
		return hsetnx;
	}

	public Long hsetnx(byte[] key, byte[] field, byte[] value) {
		Long hsetnx = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			hsetnx = jedis.hsetnx(key, field, value);
		}
		return hsetnx;
	}

	public Long hdel(String key, String... fields) {
		Long hdel = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			hdel = jedis.hdel(key, fields);
		}
		return hdel;
	}

	public Long hdel(byte[] key, byte[]... fields) {
		Long hdel = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			hdel = jedis.hdel(key, fields);
		}
		return hdel;
	}

	public Boolean hexists(String key, String field) {
		Boolean hexists = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			hexists = jedis.hexists(key, field);
		}
		return hexists;
	}

	public Boolean hexists(byte[] key, byte[] field) {
		Boolean hexists = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			hexists = jedis.hexists(key, field);
		}
		return hexists;
	}

	public String hget(String key, String field) {
		String hget = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			hget = jedis.hget(key, field);
		}
		return hget;
	}

	public byte[] hget(byte[] key, byte[] field) {
		byte[] hget = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			hget = jedis.hget(key, field);
		}
		return hget;
	}

	public Map<String, String> hgetAll(String key) {
		Map<String, String> map = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			map = jedis.hgetAll(key);
		}
		return map;
	}

	public Map<byte[], byte[]> hgetAll(byte[] key) {
		Map<byte[], byte[]> map = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			map = jedis.hgetAll(key);
		}
		return map;
	}

	public Set<String> hkeys(String key) {
		Set<String> hkeys = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			hkeys = jedis.hkeys(key);
		}
		return hkeys;
	}

	public Set<byte[]> hkeys(byte[] key) {
		Set<byte[]> hkeys = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			hkeys = jedis.hkeys(key);
		}
		return hkeys;
	}

	public List<String> hmget(String key, String... fields) {
		List<String> list = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			list = jedis.hmget(key, fields);
		}
		return list;
	}

	public List<byte[]> hmget(byte[] key, byte[]... fields) {
		List<byte[]> list = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			list = jedis.hmget(key, fields);
		}
		return list;
	}

	public List<String> hvals(String key) {
		List<String> list = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			list = jedis.hvals(key);
		}
		return list;
	}

	public Collection<byte[]> hvals(byte[] key) {
		Collection<byte[]> list = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			list = jedis.hvals(key);
		}
		return list;
	}

	public Long hlen(String key) {
		Long hlen = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			hlen = jedis.hlen(key);
		}
		return hlen;
	}

	public Long hlen(byte[] key) {
		Long hlen = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			hlen = jedis.hlen(key);
		}
		return hlen;
	}

	public Long lpush(String key, String... strings) {
		Long lpush = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			lpush = jedis.lpush(key, strings);
		}
		return lpush;
	}

	public Long lpush(byte[] key, byte[]... strings) {
		Long lpush = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			lpush = jedis.lpush(key, strings);
		}
		return lpush;
	}

	public List<String> lrange(String key, int start, int end) {
		List<String> list = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			list = jedis.lrange(key, start, end);
		}
		return list;
	}

	public List<byte[]> lrange(byte[] key, int start, int end) {
		List<byte[]> list = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			list = jedis.lrange(key, start, end);
		}
		return list;
	}

	public String lindex(String key, int index) {
		String lindex = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			lindex = jedis.lindex(key, index);
		}
		return lindex;
	}

	public byte[] lindex(byte[] key, int index) {
		byte[] lindex = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			lindex = jedis.lindex(key, index);
		}
		return lindex;
	}

	public String lset(String key, int index, String value) {
		String lset = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			lset = jedis.lset(key, index, value);
		}
		return lset;
	}

	public String lset(byte[] key, int index, byte[] value) {
		String lset = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			lset = jedis.lset(key, index, value);
		}
		return lset;
	}

	public Long llen(String key) {
		Long llen = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			llen = jedis.llen(key);
		}
		return llen;
	}

	public Long llen(byte[] key) {
		Long llen = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			llen = jedis.llen(key);
		}
		return llen;
	}

	public Long lpushx(String key, String... strings) {
		Long lpushx = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			lpushx = jedis.lpushx(key, strings);
		}
		return lpushx;
	}

	public Long lpushx(byte[] key, byte[]... strings) {
		Long lpushx = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			lpushx = jedis.lpushx(key, strings);
		}
		return lpushx;
	}

	public Long lrem(String key, long count, String value) {
		Long lrem = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			lrem = jedis.lrem(key, count, value);
		}
		return lrem;
	}

	public Long lrem(byte[] key, long count, byte[] value) {
		Long lrem = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			lrem = jedis.lrem(key, count, value);
		}
		return lrem;
	}

	public String ltrim(String key, long start, long end) {
		String ltrim = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			ltrim = jedis.ltrim(key, start, end);
		}
		return ltrim;
	}

	public String ltrim(byte[] key, long start, long end) {
		String ltrim = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			ltrim = jedis.ltrim(key, start, end);
		}
		return ltrim;
	}

	public String lpop(String key) {
		String lpop = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			lpop = jedis.lpop(key);
		}
		return lpop;
	}

	public byte[] lpop(byte[] key) {
		byte[] lpop = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			lpop = jedis.lpop(key);
		}
		return lpop;
	}

	public String substr(String key, int start, int end) {
		String substr = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			substr = jedis.substr(key, start, end);
		}
		return substr;
	}

	public byte[] substr(byte[] key, int start, int end) {
		byte[] substr = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			substr = jedis.substr(key, start, end);
		}
		return substr;
	}

	public Long move(String key, int dbIndex) {
		Long move = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			move = jedis.move(key, dbIndex);
		}
		return move;
	}

	public Long move(byte[] key, int dbIndex) {
		Long move = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			move = jedis.move(key, dbIndex);
		}
		return move;
	}

	public Long persist(String key) {
		Long persist = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			persist = jedis.persist(key);
		}
		return persist;
	}

	public Long persist(byte[] key) {
		Long persist = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			persist = jedis.persist(key);
		}
		return persist;
	}

	public Long pexpire(String key, long milliseconds) {
		Long pexpire = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			pexpire = jedis.pexpire(key, milliseconds);
		}
		return pexpire;
	}

	public Long pexpire(byte[] key, long milliseconds) {
		Long pexpire = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			pexpire = jedis.pexpire(key, milliseconds);
		}
		return pexpire;
	}

	public Long pexpireAt(String key, long millisecondsTimestamp) {
		Long pexpireAt = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			pexpireAt = jedis.pexpireAt(key, millisecondsTimestamp);
		}
		return pexpireAt;
	}

	public Long pexpireAt(byte[] key, long millisecondsTimestamp) {
		Long pexpireAt = null;
		try (ShardedJedis jedis = shardedJedisPool.getResource()) {
			pexpireAt = jedis.pexpireAt(key, millisecondsTimestamp);
		}
		return pexpireAt;
	}

}
