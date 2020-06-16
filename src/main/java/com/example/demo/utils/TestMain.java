package com.example.demo.utils;

import chrriis.common.UIUtils;
import chrriis.dj.nativeswing.swtimpl.NativeComponent;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserAdapter;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserEvent;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static sun.plugin.javascript.navig.JSType.URL;

/**
 * @author fan
 */
public class TestMain extends JPanel {
    /**
     * DJNativeSwing-SWT.jar
     */
    private static final long serialVersionUID = 1L;
    /**
     * 行分隔符
     */
    final static private String LS = System.getProperty("line.separator", "/n");
    /**
     * 文件分割符
     */
    final static private String FS = System.getProperty("file.separator", "//");
    /**
     * 以javascript脚本获得网页全屏后大小
     */
    final static StringBuffer JS_DIMENSION;

    static {
        JS_DIMENSION = new StringBuffer();
        JS_DIMENSION.append("var width = 0;").append(LS);
        JS_DIMENSION.append("var height = 0;").append(LS);
        JS_DIMENSION.append("if(document.documentElement) {").append(LS);
        JS_DIMENSION.append(
                "  width = Math.max(width, document.documentElement.scrollWidth);")
                .append(LS);
        JS_DIMENSION.append(
                "  height = Math.max(height, document.documentElement.scrollHeight);")
                .append(LS);
        JS_DIMENSION.append("}").append(LS);
        JS_DIMENSION.append("if(self.innerWidth) {").append(LS);
        JS_DIMENSION.append("  width = Math.max(width, self.innerWidth);")
                .append(LS);
        JS_DIMENSION.append("  height = Math.max(height, self.innerHeight);")
                .append(LS);
        JS_DIMENSION.append("}").append(LS);
        JS_DIMENSION.append("if(document.body.scrollWidth) {").append(LS);
        JS_DIMENSION.append(
                "  width = Math.max(width, document.body.scrollWidth);")
                .append(LS);
        JS_DIMENSION.append(
                "  height = Math.max(height, document.body.scrollHeight);")
                .append(LS);
        JS_DIMENSION.append("}").append(LS);
        JS_DIMENSION.append("return width + ':' + height;");
    }

    private TestMain(final String url, final int maxWidth, final int maxHeight) {
        super(new BorderLayout());
        JPanel webBrowserPanel = new JPanel(new BorderLayout());
        final String fileName = "D:/" + System.currentTimeMillis() + ".jpg";
        final JWebBrowser webBrowser = new JWebBrowser(null);
        webBrowser.setBarsVisible(false);
        webBrowser.navigate(url);
        webBrowserPanel.add(webBrowser, BorderLayout.CENTER);
        add(webBrowserPanel, BorderLayout.CENTER);
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 4));
        webBrowser.addWebBrowserListener(new WebBrowserAdapter() {
                                             // 监听加载进度
                                             @Override
                                             public void loadingProgressChanged(WebBrowserEvent e) {
                                                 // 当加载完毕时
                                                 if (e.getWebBrowser().getLoadingProgress() == 100) {
                                                     String result = (String) webBrowser
                                                             .executeJavascriptWithResult(JS_DIMENSION.toString());
                                                     int index = result == null ? -1 : result.indexOf(":");
                                                     NativeComponent nativeComponent = webBrowser
                                                             .getNativeComponent();
                                                     Dimension originalSize = nativeComponent.getSize();
                                                     Dimension imageSize = new Dimension(Integer.parseInt(result
                                                             .substring(0, index)), Integer.parseInt(result
                                                             .substring(index + 1)));
                                                     imageSize.width = Math.max(originalSize.width,
                                                             imageSize.width + 50);
                                                     imageSize.height = Math.max(originalSize.height,
                                                             imageSize.height + 50);
                                                     nativeComponent.setSize(imageSize);
                                                     BufferedImage image = new BufferedImage(imageSize.width,
                                                             imageSize.height, BufferedImage.TYPE_INT_RGB);
                                                     nativeComponent.paintComponent(image);
                                                     nativeComponent.setSize(originalSize);
                                                     // 当网页超出目标大小时
                                                     if (imageSize.width > maxWidth
                                                             || imageSize.height > maxHeight) {
                                                         //截图部分图形
                                                         image = image.getSubimage(0, 0, maxWidth, maxHeight);
                        /*此部分为使用缩略图
                                                         int width = image.getWidth(), height = image
                                                                 .getHeight();
                                                         AffineTransform tx = new AffineTransform();
                                                         tx.scale((double) maxWidth / width, (double) maxHeight
                                                                 / height);
                                                         AffineTransformOp op = new AffineTransformOp(tx,
                                                                 AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
                                                         //缩小
                                                         image = op.filter(image, null);*/
                                                     }
                                                     try {
                                                         // 输出图像
                                                         ImageIO.write(image, "jpg", new File(fileName));
                                                     } catch (IOException ex) {
                                                         ex.printStackTrace();
                                                     }
                                                     System.exit(0);
                                                 }
                                             }
                                         }
        );
        add(panel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
 /*       final String url = "<html><head></head><body><div style=\"position:relative;left:0;top:0;width:212px;height:110px\"><div style=\"position: absolute;left:0px;top:0px;width:212px;height:110px;z-index:1\"><div style=\"position:relative;width:212px;height:110px\"><div style=\"position:absolute;white-space:nowrap;overflow:hidden;color:rgba(255,0,0,1);location:center;background-color:rgba(255,255,255,0);width:212px;top:0px;left:0px;z-index:2;font-size:32px;font-family:Microsoft Yahei;letter-spacing:0px;\">碳烧咖啡22</div><div style=\"position:absolute;word-wrap:nowrap;word-break:break-all;overflow:hidden;color:rgba(0,0,0,1);location:center;background-color:rgba(255,255,255,0);width:50px;top:46px;left:69px;z-index:2;font-size:15px;font-family:SimSun;letter-spacing:0px;\">罐装1</div><div style=\"position:absolute;word-wrap:nowrap;word-break:break-all;overflow:hidden;color:rgba(0,0,0,1);location:center;background-color:rgba(255,255,255,0);width:50px;top:46px;left:114px;z-index:2;font-size:15px;font-family:SimSun;letter-spacing:0px;\">罐装1</div><div style=\"position:absolute;display: flex;justify-content: center;align-items: center;width:50px;height:50px;top:56px;left:0px;z-index:2;background-color:rgba(255,255,255,0);image-type:100;\"><img style=\"max-width:100%;max-height:100%\" src=\"http://10.6.3.81:8090/ishelf/imgs/upload/product/null/2020/6/11/1528444040.png\"></img></div><div style=\"position:absolute;word-wrap:nowrap;word-break:break-all;overflow:hidden;color:rgba(0,0,0,1);location:center;background-color:rgba(255,255,255,0);width:50px;top:69px;left:69px;z-index:2;font-size:15px;font-family:SimSun;letter-spacing:0px;\">好喝11</div></div></div><br/></div></body></html>";
        final String title = "DJ Test";
        UIUtils.setPreferredLookAndFeel();
        NativeInterface.open();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame(title);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().add(new TestMain(url), BorderLayout.CENTER);
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                frame.setLocationByPlatform(true);
                frame.setVisible(true);
            }
        });
        NativeInterface.runEventPump();*/


        NativeInterface.open();
        SwingUtilities.invokeLater(() -> {
            // SWT组件转Swing组件，不初始化父窗体将无法启动webBrowser
            JFrame frame = new JFrame("以DJ组件保存指定网页截图");
            // 加载指定页面，最大保存为640x480的截图
            frame.getContentPane().add(
                    new TestMain("http://10.6.3.239:8090/ishelf/imgs/index.html", 1920, 1080),
                    BorderLayout.CENTER);
            frame.setSize(1920, 1080);
            // 仅初始化，但不显示
            frame.invalidate();
            frame.pack();
            frame.setVisible(false);
        });
        NativeInterface.runEventPump();


    }

/*    public TestMain(final String html) {
        super(new BorderLayout());
        JPanel webBrowserPanel = new JPanel(new BorderLayout());
        JWebBrowser webBrowser = new JWebBrowser();
        webBrowser.setHTMLContent(html);
        webBrowser.setButtonBarVisible(false);
        webBrowser.setMenuBarVisible(false);
        webBrowser.setBarsVisible(false);
        webBrowser.setStatusBarVisible(false);
        webBrowserPanel.add(webBrowser, BorderLayout.CENTER);
        add(webBrowserPanel, BorderLayout.CENTER);

    }*/
}
