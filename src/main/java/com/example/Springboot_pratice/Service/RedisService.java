package com.example.Springboot_pratice.Service;

import com.example.Springboot_pratice.dto.StudentDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    private static final Logger log = LoggerFactory.getLogger(RedisService.class);
    @Autowired
    public ObjectMapper objectMapper;

    @Autowired
    public RedisTemplate redisTemplate;

    private static final long EXPIRATION_TIME_IN_HOURS = 1;
    public String saveDataInCache(String key, StudentDto studentDto)
    {
        try {
            // step -1 convert desrialize to serialize
            String json_Serialize=objectMapper.writeValueAsString(studentDto);

            //step-2 set the redis templete
           redisTemplate.opsForValue().set(key,json_Serialize,EXPIRATION_TIME_IN_HOURS, TimeUnit.HOURS);
           return key;
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    public StudentDto getDataFromCache(String StudentId)
    {
        String Key=StudentId;
        String Json_deSerialize= (String) redisTemplate.opsForValue().get(Key);
        if (Json_deSerialize==null)
        {
            log.info("No key found to fetch the data");
            return null;
        }
        try {
           return objectMapper.readValue(Json_deSerialize,StudentDto.class);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    public StudentDto UpdateRedisData(String key, StudentDto studentDto) {
        try {
            // step -1 convert desrialize to serialize
            String json_Serialize = objectMapper.writeValueAsString(studentDto);

            // step-2 set the redis templete
            redisTemplate.opsForValue().set(key, json_Serialize, EXPIRATION_TIME_IN_HOURS, TimeUnit.HOURS);
            return studentDto;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
