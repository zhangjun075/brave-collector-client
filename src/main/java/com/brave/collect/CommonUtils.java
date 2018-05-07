package com.brave.collect;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author junzhang
 */
@Slf4j
public class CommonUtils {

    static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss A");

    public static ExecutorService executorService = Executors.newFixedThreadPool(100);

    public static ThreadLocal<List<CollectInfoDTO>> localCollects = new ThreadLocal<>();

    public static ThreadLocal<String> traceId = new ThreadLocal<>();

    public static ThreadLocal<ConcurrentHashMap<String,String>> parentSpandId = new ThreadLocal<>();

    /**
     * @return
     */
    public static String getNowDateTimeString() {
        return LocalDateTime.now().format(dateTimeFormatter);
    }

    /**
     * @return
     */
    public static String getMethodName() {
        String funcName2 = new Throwable().getStackTrace()[1].getMethodName();
        return funcName2;
    }

    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    public static void setParentSpanId(String method,String parentId) {
        if(parentSpandId.get() == null ){
            ConcurrentHashMap<String,String> parMap = new ConcurrentHashMap<>();
            parMap.put(method,parentId);
            parentSpandId.set(parMap);
        }else {
            parentSpandId.get().put(method,parentId);
        }
    }

    public static String getParentSpanId(String method) {
        if(parentSpandId.get() == null) {
            return "";
        }
        String parentSId = parentSpandId.get().get(method);
        parentSpandId.get().remove(method);
        return parentSId;
    }

    public static void setLocalCollects(CollectInfoDTO collects) {
        if(localCollects.get() == null) {
            List<CollectInfoDTO> list = new ArrayList<>();
            list.add(collects);
            localCollects.set(list);
        }else {
            localCollects.get().add(collects);
        }
    }

}
