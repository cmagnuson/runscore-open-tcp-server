package com.mtecresults.runscoreopentcpserver.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Passing {
    final String bibOrChip;
    final long timeMillis;
    final String chipcode;
    final String locationName;
}
