package com.example.demo.utils;

import gui.ava.html.parser.HtmlParser;
import gui.ava.html.parser.HtmlParserImpl;
import gui.ava.html.renderer.ImageRenderer;
import gui.ava.html.renderer.ImageRendererImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * HTML转化为图片
 *
 * @author fan
 */
@Component
public class HtmlToImage {

    private static String path;

    @Value("${ishelf.file.path}")
    public void setImgPath(String imgPath) {
        path = imgPath;
    }

    private static String fileSeparator = "/";

    /**
     * 保存生成后路径
     */
    public static void changeHtmlToImage(String html, String fileName) {
        HtmlParser htmlParser = new HtmlParserImpl();
        htmlParser.loadHtml(html);
        ImageRenderer imageRenderer = new ImageRendererImpl(htmlParser);
        BufferedImage bufferedImage = imageRenderer.getBufferedImage();
        BufferedImage image = getMonochromeImage(bufferedImage);
        File file = new File("D://" + fileName + ".png");
        try {
            ImageIO.write(image, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //imageRenderer.saveImage(path + fileSeparator + fileName + ".png");
    }

    /**
     * 将其他类型的图片转化为黑白图片
     *
     * @param image
     * @return
     */
    public static BufferedImage getMonochromeImage(BufferedImage image) {
		/*if(image.getType() == BufferedImage.TYPE_BYTE_BINARY){
			return image;
		}*/
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
        Graphics2D g2d = newImage.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        return newImage;
    }

}
