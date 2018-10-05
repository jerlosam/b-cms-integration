package io.crazy88.beatrix.e2e.clients;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(url = "${feign.bulkChange.url}", name = "bulkChange")
public interface BulkChangeClient {
    @RequestMapping(method = RequestMethod.DELETE, value = "services/bulk-change/v1/jobs/{jobId}")
    void deleteJob(@PathVariable("jobId") String jobId);
}
