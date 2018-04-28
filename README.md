# 中文使用说明
## 主类：CommonUtils
### 方法：getNowDateTimeString 获取当前时间，精确到毫秒，用于计时
### 方法：getMethodName 获取被调用的方法名字
### 方法：setParentSpanId 往会话中设置对应方法的parentSpanId
### 方法：getParentSpanId 根据方法名获取对应的parentSpanId
### 方法：setLocalCollects 收集到到信息对象在本次会话的汇总


## 方法实践
* controller方法调用service方法

```
@RestController
@Slf4j
public class Demo {

    @Autowired DemoService demoService;

    @GetMapping("/demos")
    public String demo(){
        String traceId = CommonUtils.generateUUID();
        CommonUtils.traceId.set(traceId);

        String spanId = CommonUtils.generateUUID();

        CollectInfoDTO collectInfoDTO = CollectInfoDTO.builder().clazz(this.getClass().getName()).method(
            CommonUtils.getMethodName()).sr(CommonUtils.getNowDateTimeString()).spanId(spanId).traceId(CommonUtils.traceId.get()).build();
        log.info("this is demo controller begin:{}",collectInfoDTO);
        CommonUtils.setParentSpanId("demo",spanId);

        demoService.demo();

        collectInfoDTO.setSs(CommonUtils.getNowDateTimeString());
        CommonUtils.setLocalCollects(collectInfoDTO);
        print();
        return "demo";
    }

    public void print() {
        CommonUtils.localCollects.get().stream().forEach(collectInfoDTO -> log.info("==>{}",collectInfoDTO));
    }
}

```
* service

```
@Service
@Slf4j
public class DemoService {

//    @HystrixCommand(fallbackMethod = "hcd")
    public String demo(){
        String parentSpanId = CommonUtils.getParentSpanId(CommonUtils.getMethodName());
        String traceId = CommonUtils.traceId.get();
        CollectInfoDTO collectInfoDTO = CollectInfoDTO.builder().clazz(this.getClass().getName()).method(
            CommonUtils.getMethodName()).sr(CommonUtils.getNowDateTimeString()).spanId(CommonUtils.generateUUID()).traceId(CommonUtils.traceId.get()).parentSpandId(parentSpanId).build();
        log.info("process.......");
        collectInfoDTO.setSs(CommonUtils.getNowDateTimeString());
        CommonUtils.setLocalCollects(collectInfoDTO);
        return "demo";
    }
    public String hcd(){
        return "hcd";
    }
}
```
