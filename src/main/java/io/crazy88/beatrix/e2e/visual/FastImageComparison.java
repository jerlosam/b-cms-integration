package io.crazy88.beatrix.e2e.visual;

import internal.katana.core.reporter.Reporter;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class FastImageComparison {

    @Autowired static Reporter reporter;

    private static boolean compare(final byte[] image1, final byte[] image2) {
        return Objects.deepEquals(image1, image2);
    }

    public static boolean isEqual(File image1, File image2) {
        if (image1.length() == image2.length()) {
            try {
                return compare(FileUtils.readFileToByteArray(image1), FileUtils.readFileToByteArray(image2));
            } catch (IOException e) {
                reporter.debug("It wasn't possible to compare the screenshots");
                return false;
            }
        }
        return false;
    }
}
