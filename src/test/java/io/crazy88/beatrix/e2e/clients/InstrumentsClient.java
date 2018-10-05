package io.crazy88.beatrix.e2e.clients;

import io.crazy88.beatrix.e2e.payoutservice.dto.Instrument;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@FeignClient(url = "${feign.ecommapi.url}", configuration= InstrumentsFeignClientConfiguration.class, name = "instrument")
public interface InstrumentsClient {
    @RequestMapping(value = "payment_profiles/{profileId}/paymentInstruments", method = POST)
    Instrument create(@PathVariable("profileId") String profileId,
                      @RequestBody Instrument withdrawalInstrument,
                      @RequestHeader("x-ecomm-channel") String channelCode);
}
