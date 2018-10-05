package io.crazy88.beatrix.e2e.visual;

import internal.katana.selenium.BrowserManager;
import internal.katana.selenium.core.browser.BrowserDriverManager;
import internal.katana.selenium.core.browser.BrowserVendor;
import internal.qaauto.picasso.java.client.core.dto.PicassoRectangle;
import internal.qaauto.picasso.java.client.core.exceptions.PicassoException;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;
import lombok.SneakyThrows;
import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class VisualUtils {

    private static int IOS_STATUS_BAR = 22;

    private static int IOS_NAV_BAR = 40;

    private static int TAB_HEIGHT_SAFARI_IPAD = 34;

    private static int IPAD_HEADER = IOS_NAV_BAR + IOS_STATUS_BAR + TAB_HEIGHT_SAFARI_IPAD;

    private static int IPHONE_HEADER = IOS_NAV_BAR + IOS_STATUS_BAR;

    @Autowired BrowserManager browserManager;

    @Autowired BrowserWait browserWaits;

    @SneakyThrows
    public Snapshot captureWholeImage(String name) {
        File file = browserWaits.waitForNoAnimations();
        return new Snapshot(name, file);
    }

    @SneakyThrows
    public Snapshot captureImage(String name) {
        return captureImage(name, false);
    }

    @SneakyThrows
    public Snapshot captureImage(String name, boolean black) {
        File file = browserWaits.waitForNoAnimations();
        // Remove status, browser and tab bar of Safari
        if(browserManager.browserDriver().browser().getBrowserVendor() == BrowserVendor.SAFARI || browserManager.browserDriver().browser().getBrowserVendor() == BrowserVendor.INTERNET_EXPLORER) {
            Dimension windowSize = browserManager.driver().manage().window().getSize();
            file = getPictureFromImage(browserManager, file,
                    new PicassoRectangle(0, 0, windowSize.getWidth(), windowSize.getHeight() - IOS_NAV_BAR));
        }
        if(black) {
            convertToBlack(file);
        }
        return new Snapshot(name, file);
    }

    @SneakyThrows
    public Snapshot captureImage(String name, By locator) {
        return captureImage(name, getRectangle(locator), false);
    }

    @SneakyThrows
    public Snapshot captureImage(String name, By locator, boolean black) {
        return captureImage(name, getRectangle(locator), black);
    }

    @SneakyThrows
    public Snapshot captureImage(String name, PicassoRectangle rectangle, boolean black) {
        JavascriptExecutor executor = (JavascriptExecutor) browserManager.driver();
        Long value = (Long) executor.executeScript("return window.pageYOffset;");
        //ONLY IE: Scroll is working, but the picture is of the whole screen so we shouldn't take into account the scroll
        if(browserManager.browserDriver().browser().getBrowserVendor() != BrowserVendor.INTERNET_EXPLORER) {
            rectangle.setY(rectangle.getY() - value.intValue());
        }
        File screenshot = browserWaits.waitForNoAnimations();
        File file = getPictureFromImage(browserManager, screenshot, rectangle);
        if(black) {
            convertToBlack(file);
        }
        return new Snapshot(name, file);
    }

    public PicassoRectangle getRectangle(By locator) {
        System.out.println(locator);
        Point point = browserManager.driver().findElement(locator).getLocation();
        Dimension dimension = browserManager.driver().findElement(locator).getSize();
        return new PicassoRectangle(point.getX(), point.getY(), dimension.getWidth(), dimension.getHeight());
    }

    public PicassoRectangle getRectangle(By... locators) {
        PicassoRectangle rectangle = null;
        for(By locator : locators) {
            if(rectangle == null) {
                rectangle = getRectangle(locator);
            } else {
                rectangle = union(rectangle, locator);
            }
        }
        return rectangle;
    }

    public PicassoRectangle getRectangleIntersect(By... locators) {
        PicassoRectangle rectangle = null;
        for(By locator : locators) {
            if(rectangle == null) {
                rectangle = getRectangle(locator);
            } else {
                rectangle = intersection(rectangle, getRectangle(locator));
            }
        }
        return rectangle;
    }

    private PicassoRectangle union(PicassoRectangle rectangle, By locator) {
        Rectangle currentRectangle = new Rectangle(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
        Point point = browserManager.driver().findElement(locator).getLocation();
        Dimension dimension = browserManager.driver().findElement(locator).getSize();
        Rectangle newRectangle = new Rectangle(point.getX(), point.getY(), dimension.getWidth(), dimension.getHeight());
        currentRectangle.add(newRectangle);
        return new PicassoRectangle((int)currentRectangle.getX(), (int)currentRectangle.getY(), (int)currentRectangle.getWidth(), (int)currentRectangle.getHeight());
    }

    public PicassoRectangle intersection(PicassoRectangle screen, PicassoRectangle rectangle) {
        Rectangle screenRectangle = new Rectangle(screen.getX(), screen.getY(), screen.getWidth(), screen.getHeight());
        Rectangle newRectangle = new Rectangle(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
        Rectangle intersection = screenRectangle.intersection(newRectangle);
        return new PicassoRectangle((int)intersection.getX(), (int)intersection.getY(), (int)intersection.getWidth(), (int)intersection.getHeight() -1);
    }

    private static PicassoRectangle getWindow(BrowserDriverManager browserManager) {
        PicassoRectangle window;
        if(browserManager.browserDriver().browser().getBrowserVendor() == BrowserVendor.FIREFOX ||
                browserManager.browserDriver().browser().getBrowserVendor() == BrowserVendor.ANDROID ||
                browserManager.browserDriver().browser().getBrowserVendor() == BrowserVendor.EDGE) {
            Long windowWidth = (Long)((JavascriptExecutor) browserManager.driver()).executeScript("return window.outerWidth;");
            Long windowHeight = (Long)((JavascriptExecutor) browserManager.driver()).executeScript("return window.outerHeight;");
            window = new PicassoRectangle(0, 0, windowWidth.intValue(), windowHeight.intValue());
        } else if (browserManager.browserDriver().browser().isTablet()) {
            Dimension windowSize = browserManager.driver().manage().window().getSize();;
            window = new PicassoRectangle(0, IPAD_HEADER, windowSize.getWidth(), windowSize.getHeight());
        } else if(browserManager.browserDriver().browser().getBrowserVendor() == BrowserVendor.IOS && browserManager.browserDriver().browser().isMobile()) {
            Dimension windowSize = browserManager.driver().manage().window().getSize();
            window = new PicassoRectangle(0, IPHONE_HEADER, windowSize.getWidth(), windowSize.getHeight());
        } else if(browserManager.browserDriver().browser().getBrowserVendor() == BrowserVendor.SAFARI) {
            Dimension windowSize = browserManager.driver().manage().window().getSize();
            window =  new PicassoRectangle(0, 0, windowSize.getWidth(), windowSize.getHeight() - IOS_NAV_BAR);
        } else {
            Dimension windowSize = browserManager.driver().manage().window().getSize();
            window = new PicassoRectangle(0, 0, windowSize.getWidth(), windowSize.getHeight());
        }
        return window;
    }

    private static Dimension getWindowSize(BrowserDriverManager browserManager) {
        Dimension window;
        if(browserManager.browserDriver().browser().getBrowserVendor() == BrowserVendor.FIREFOX ||
                browserManager.browserDriver().browser().getBrowserVendor() == BrowserVendor.ANDROID ||
                browserManager.browserDriver().browser().getBrowserVendor() == BrowserVendor.EDGE) {
            Long windowWidth = (Long)((JavascriptExecutor) browserManager.driver()).executeScript("return window.outerWidth;");
            Long windowHeight = (Long)((JavascriptExecutor) browserManager.driver()).executeScript("return window.outerHeight;");
            window = new Dimension(windowWidth.intValue(), windowHeight.intValue());
        } else {
            Dimension windowSize = browserManager.driver().manage().window().getSize();
            window = new Dimension( windowSize.getWidth(), windowSize.getHeight());
        }
        return window;
    }

    private void convertToBlack(File file) throws IOException {
        BufferedImage image = ImageIO.read(file);
        if(image.getColorModel().hasAlpha()) {
            image = setPngTransparentToColor(image, Color.black);
        } else {
            for (int y = 0; y < image.getHeight(); ++y) {
                for (int x = 0; x < image.getWidth(); ++x) {
                    if (image.getRGB(x, y) <= -16448251) {
                        image.setRGB(x, y, 1);
                    }

                }
            }
        }
        ImageIO.write(image, "png", file);
    }

    private static BufferedImage setPngTransparentToColor(BufferedImage bufferedImage, Color color) {
        BufferedImage bi = new BufferedImage(bufferedImage.getWidth(),bufferedImage.getHeight(),BufferedImage.TYPE_INT_ARGB);
        for (int x=0;x<bufferedImage.getWidth();x++){
            for (int y=0;y<bufferedImage.getHeight();y++) {
                int rgba = bufferedImage.getRGB(x, y);
                boolean isTrans = (rgba & 0x00ffffff) <= 131586;
                if (isTrans) {
                    bi.setRGB(x, y, color.getRGB());
                } else {
                    bi.setRGB(x, y, rgba);
                }
            }
        }
        return bi;
    }

    private static File getPictureFromImage(BrowserDriverManager browserDriver, File screenshot, PicassoRectangle picassoRectangle) throws PicassoException {

        try {
            BufferedImage e = ImageIO.read(screenshot);

            Dimension windowSize = getWindowSize(browserDriver);

            BigDecimal screenWidth = new BigDecimal(windowSize.getWidth());
            BigDecimal imageWidth = new BigDecimal(e.getWidth());
            BigDecimal scale = imageWidth.divide(screenWidth,4, BigDecimal.ROUND_HALF_UP);
            int newX = new BigDecimal(picassoRectangle.getX()).multiply(scale).intValue();
            int newY = new BigDecimal(picassoRectangle.getY()).multiply(scale).intValue();
            int newWidth = new BigDecimal(picassoRectangle.getWidth()).multiply(scale).intValue();
            int newHeight = new BigDecimal(picassoRectangle.getHeight()).multiply(scale).intValue();
            e = e.getSubimage(newX, newY, newWidth, e.getHeight() < newHeight ? (e.getHeight() - newY) : newHeight);
            ImageIO.write(e, "png", screenshot);
            return screenshot;
        } catch (IOException var7) {
            throw new PicassoException("Error to take a screenshot by rectangle", var7);
        }
    }

    public List<PicassoRectangle> getIgnoredArea(By rectangle) {
        PicassoRectangle picassoRectangle = getRectangle(rectangle);
        return getIgnoredArea(picassoRectangle);
    }

    public List<PicassoRectangle> getIgnoredArea(PicassoRectangle rectangle) {
        Dimension windowSize = getWindowSize(browserManager);

        List<PicassoRectangle> ignoredRegions = new ArrayList<>();
        int endX = rectangle.getX() + rectangle.getWidth();
        int endY = rectangle.getY() + rectangle.getHeight();
        int endWidth = windowSize.getWidth() - (endX);
        int endHeight = windowSize.getHeight() - (endY);
        int ratio = (int) getDevicePixelRatio();

        // we have to add 8 rectangles, if some of then has width or height equals to 0 is not added.
        //firstRegion, top-left corner
        if(rectangle.getX() != 0 && rectangle.getY() != 0) {
            ignoredRegions.add(new PicassoRectangle(0,0, rectangle.getX() * ratio, rectangle.getY() * ratio));
        }
        //secondRegion, top
        if(rectangle.getY() != 0) {
            ignoredRegions.add(new PicassoRectangle(rectangle.getX() * ratio, 0, rectangle.getWidth() * ratio, rectangle.getY() * ratio));
        }
        //thirdRegion top-right corner
        if(rectangle.getY() != 0 && (endX != windowSize.getWidth())) {
            ignoredRegions.add(new PicassoRectangle(endX * ratio, 0, endWidth * ratio, rectangle.getY() * ratio));
        }
        //fourthRegion, left
        if(rectangle.getX() != 0) {
            ignoredRegions.add(new PicassoRectangle(0, rectangle.getY() * ratio, rectangle.getX() * ratio, rectangle.getHeight() * ratio));
        }
        //fifthRegion, right
        if(endX != windowSize.getWidth()) {
            ignoredRegions.add(new PicassoRectangle(endX * ratio, rectangle.getY() * ratio, endWidth * ratio, rectangle.getHeight() * ratio));
        }
        //sixthRegion, bottom-left corner
        if(rectangle.getX() != 0 && (endY != windowSize.getHeight())) {
            ignoredRegions.add(new PicassoRectangle(0,endY * ratio, rectangle.getX() * ratio, endHeight * ratio));
        }
        //seventhRegion, bottom
        if((endY != windowSize.getHeight())) {
            ignoredRegions.add(new PicassoRectangle(rectangle.getX() * ratio, endY * ratio, rectangle.getWidth() * ratio, endHeight * ratio));
        }
        //eigthRegion bottom-right corner
        if(rectangle.getY() != 0 && (endY != windowSize.getHeight())) {
            ignoredRegions.add(new PicassoRectangle(endX * ratio, endY * ratio, endWidth * ratio, endHeight * ratio));
        }
        return ignoredRegions;
    }

    public long getDevicePixelRatio() {
        try {
            return (Long) ((JavascriptExecutor) browserManager.driver()).executeScript("return window.devicePixelRatio;");
        }catch (Exception e) {
            e.printStackTrace();
            return 1L;
        }
    }

    @SneakyThrows
    public Snapshot takeSnapshot(final String name) {
        final File file = browserWaits.waitForNoAnimations();
        return new Snapshot(name, file);
    }
}
