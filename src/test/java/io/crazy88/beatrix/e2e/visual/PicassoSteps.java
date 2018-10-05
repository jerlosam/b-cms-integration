package io.crazy88.beatrix.e2e.visual;

import internal.katana.core.reporter.Reporter;
import internal.qaauto.picasso.java.client.core.PicassoConfiguration;
import internal.qaauto.picasso.java.client.core.PicassoOptions;
import internal.qaauto.picasso.java.client.core.dto.PicassoComparatorDescriptor;
import internal.qaauto.picasso.java.client.core.dto.PicassoComparisonResult;
import internal.qaauto.picasso.java.client.core.exceptions.PicassoException;
import internal.qaauto.picasso.java.client.jbehave.AbstractPicassoSteps;
import internal.qaauto.picasso.java.client.jbehave.PicassoClientUtils;
import io.crazy88.beatrix.e2e.E2EProperties;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.SoftAssertions;
import org.jbehave.core.annotations.FromContext;
import org.jbehave.core.annotations.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static io.crazy88.beatrix.e2e.visual.VisualSteps.IMAGES_TO_COMPARE;

@Component
public class PicassoSteps extends AbstractPicassoSteps{

    @Autowired
    private Reporter reporter;

    @Autowired
    private E2EProperties e2eProperties;

    private static final String TARGET_IMAGES_PATH = "target/jbehave/view/" + getPicassoPath();

    private static final String IMAGE_COMPARISON_MAP = "comparisonMap";

    private static final String IMAGE_SAMPLE_FILTERED = "sampleFilteredImage";

    private static final String IMAGE_ORIGINAL_FILTERED = "originalFilteredImage";

    PicassoConfiguration picassoConfiguration;

    PicassoComparatorDescriptor edge;

    PicassoComparatorDescriptor histogram;

    @Autowired
    public PicassoSteps(PicassoConfiguration picassoConfiguration) {
        this.picassoConfiguration = picassoConfiguration;
        edge = new PicassoComparatorDescriptor(PicassoComparatorDescriptor.Descriptor.EDGE);
        histogram = new PicassoComparatorDescriptor(PicassoComparatorDescriptor.Descriptor.HISTOGRAM);
    }

    private PicassoOptions buildPicassoOptions() {
        List<PicassoComparatorDescriptor> listComparators = new ArrayList<>();
        listComparators.add(edge);
        listComparators.add(histogram);
        return PicassoOptions.builder()
                .percentajeDifferenceTolerance(ImageValidation.DEFAULT_TOLERANCE)
                .listComparator(listComparators)
                .build();
    }

    @Override
    public PicassoConfiguration getPicassoConfiguration() {
        return this.picassoConfiguration;
    }

    @Override public void setPicassoComparisonResult(PicassoComparisonResult result,
            File screenshot, String alias) {
        //TODO something to fix in picasso client
    }

    public void setPicassoComparisonResult(PicassoComparisonResult result,
            File screenshot, String alias, StringBuffer html, double tolerance) {
        try {
            final BufferedImage image = ImageIO.read(screenshot);
            String name = alias+ ".png";
            makeTemporalImageFolder();
            FileSystemUtils.copyRecursively(screenshot, new File(TARGET_IMAGES_PATH + IMAGE_ORIGINAL_FILTERED + "_" + name));
            String originalPath = getPicassoPath() + IMAGE_ORIGINAL_FILTERED + "_" + name;
            html.append("<td><p>" + name + "</p><br><p>" + image.getWidth() + "x" + image.getHeight() + "</p></td>");
            html.append("<td><a class='fancybox' title='Image inserted from " + originalPath + "' target='_blank' href='" + originalPath + "'> <img align='center' width='100%' src='" + originalPath + "'"
                    + " alt='Screenshot' /> </a></td>");
            if (result.getSampleImage() != null) {
                String comparisonPath = getPicassoPath() + IMAGE_COMPARISON_MAP + "_" + name;
                String referencePath = getPicassoPath() + IMAGE_SAMPLE_FILTERED + "_" + name;

                ImageIO.write(result.getSampleImageAsBufferedImage(), PicassoClientUtils.IMAGE_FORMAT_NAME,
                        new File(TARGET_IMAGES_PATH + IMAGE_SAMPLE_FILTERED + "_" + name));
                html.append("<td><a class='fancybox' title='Image inserted from " + referencePath + "' target='_blank' href='" + referencePath + "'> <img align='center' width='100%' src='" + referencePath + "'"
                        + " alt='Screenshot' /> </a></td>");
                if(result.getComparisonMapAsBufferedImage() != null) {

                    ImageIO.write(result.getComparisonMapAsBufferedImage(), PicassoClientUtils.IMAGE_FORMAT_NAME,
                            new File(TARGET_IMAGES_PATH + IMAGE_COMPARISON_MAP + "_" + name));
                    html.append("<td><a class='fancybox' title='Comparison Map from " + comparisonPath + "' target='_blank' href='" + comparisonPath + "'> <img align='center' width='100%' src='" + comparisonPath + "'"
                            + " alt='Screenshot' /> </a></td>");
                } else {
                    if(result.getSampleImageAsBufferedImage() != null) {
                        final BufferedImage sample = result.getSampleImageAsBufferedImage();
                        html.append("<td><p>Images doesn't have same size, sample image:</p><br><p>" + sample.getWidth() + "x" + sample.getHeight() + "</p></td>");
                    } else {
                        html.append("<td></td>");
                    }
                }

            } else {
                html.append("<td></td><td></td>");
            }

            if (StringUtils.isNotBlank(result.getAcceptanceUri())) {
                html.append("<td>");
                html.append("<p>" + String.format("Difference percentage: %s", result.getDifferencePercentage()) + "</p>");
                if(result.getDifferencePercentage() > tolerance) {
                    html.append(
                            "<div class='info'><a style='cursor: pointer' onclick=\"var tag= $(this);$.get('" + result.getAcceptanceUri().replaceFirst("http", "https")
                                    + "').success(function(){tag.text('IMAGE VALIDATED');tag.prop( 'disabled', true );}).fail(function(){console.log('fail')})\"> VALIDATE </a></div>");
                    html.append("</td>");
                }
            } else {
                html.append("<td></td>");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Then("check that UX is correct")
    public void checkUX(@FromContext(IMAGES_TO_COMPARE) List<ImageValidation> images) throws IOException, PicassoException {
        SoftAssertions softAssertions = new SoftAssertions();
        StringBuffer html = new StringBuffer("<table style='width: 100%;' border='1' cellpadding='1'>"
                + "<tbody><tr><td>NAME</td><td>IMAGE TAKEN</td><td>REFERENCE IMAGE</td><td>COMPARISON</td><td>VALIDATION</td></tr>");
        for(ImageValidation image : images) {
            html.append("<tr>");
            PicassoComparisonResult result = compareImage(image.getSnapshot().getName()  + "_" + e2eProperties.getEnvironment() + "_" + e2eProperties.getBrandCode(), image.getSnapshot().getImage(),
                    buildPicassoOptions().builder().ignoreRegions(image.getIgnoredRegionList()).percentajeDifferenceTolerance(image.getTolerance()).region(image.getRegion()).build());
            setPicassoComparisonResult(result, image.getSnapshot().getImage(), image.getSnapshot().getName()+ "_" + e2eProperties.getEnvironment() + "_" + e2eProperties.getBrandCode(), html, image.getTolerance());
            softAssertions.assertThat(result.getDifferencePercentage()).isLessThan(image.getTolerance());
            html.append("</tr>");
        }
        html.append("</tbody></table>");
        reporter.insertHtml(html.toString());
        softAssertions.assertAll();
    }

    @Then("check that UX is correct without env differentiation")
    public void checkUXWithNoEnv(@FromContext(IMAGES_TO_COMPARE) List<ImageValidation> images) throws IOException, PicassoException {
        SoftAssertions softAssertions = new SoftAssertions();
        StringBuffer html = new StringBuffer("<table style='width: 100%;' border='1' cellpadding='1'>"
                + "<tbody><tr><td>NAME</td><td>IMAGE TAKEN</td><td>REFERENCE IMAGE</td><td>COMPARISON</td><td>VALIDATION</td></tr>");
        for(ImageValidation image : images) {
            html.append("<tr>");
            PicassoComparisonResult result = compareImage(image.getSnapshot().getName(), image.getSnapshot().getImage(),
                    buildPicassoOptions().builder().ignoreRegions(image.getIgnoredRegionList()).percentajeDifferenceTolerance(image.getTolerance()).region(image.getRegion()).build());
            setPicassoComparisonResult(result, image.getSnapshot().getImage(), image.getSnapshot().getName(), html, image.getTolerance());
            softAssertions.assertThat(result.getDifferencePercentage()).isLessThan(image.getTolerance());
            html.append("</tr>");
        }
        html.append("</tbody></table>");
        reporter.insertHtml(html.toString());
        softAssertions.assertAll();
    }

    private void makeTemporalImageFolder() {
        File folder = new File(TARGET_IMAGES_PATH);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    private static String getPicassoPath() {
        return "picasso/";
    }
}
