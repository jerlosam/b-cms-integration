package io.crazy88.beatrix.e2e.bulkchangemanager.dto;

import java.util.Optional;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class JobInfo {

    String jobName;

    Optional<String> internalMessage;
}