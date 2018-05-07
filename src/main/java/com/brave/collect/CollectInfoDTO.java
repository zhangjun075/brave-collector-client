package com.brave.collect;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CollectInfoDTO {
    public String spanId;
    public String clazz;
    public String method;
    public String cs;
    public String cr;
    public String sr;
    public String ss;
    public String traceId;
    public String parentSpandId;
    public String param;
    public String exField1;
    public String exField2;
    public String exField3;
    public String resp;
}
