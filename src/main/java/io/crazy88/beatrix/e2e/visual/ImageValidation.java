package io.crazy88.beatrix.e2e.visual;

import internal.qaauto.picasso.java.client.core.dto.PicassoRectangle;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ImageValidation {

    public static final double DEFAULT_TOLERANCE = 0.02;

    private Snapshot snapshot;

    private double tolerance;

    private PicassoRectangle region;

    private List<PicassoRectangle> ignoredRegionList;

    public static class ImageValidationBuilder {
        private double tolerance = DEFAULT_TOLERANCE;
    }
}
