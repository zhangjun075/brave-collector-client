package com.brave.client;


import com.brave.collect.CollectInfoDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author junzhang
 */
@FeignClient(name = "hammerClient" ,url = "${hummerUrl}")
public interface HammerClient {

    @RequestMapping(value = "/ops/collector/gxb",method = RequestMethod.POST)
    void collector(@RequestBody CollectInfoDTO collectInfoDTO);
}
