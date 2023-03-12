package cn.leyou.search.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("item-service")
public interface GoodsClient {
}
