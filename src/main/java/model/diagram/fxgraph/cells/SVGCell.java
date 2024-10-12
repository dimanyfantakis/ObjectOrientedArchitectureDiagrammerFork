package model.diagram.fxgraph.cells;

import java.io.IOException;
import java.io.StringReader;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import model.diagram.fxgraph.BufferedImageTranscoder;
import model.diagram.fxgraph.graph.Graph;
import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.svg.SVGDocument;

public class SVGCell extends AbstractCell {
    public static final String FOO =
            "<?xml version=\"1.0\" encoding=\"us-ascii\" standalone=\"no\"?><svg"
                + " xmlns=\"http://www.w3.org/2000/svg\""
                + " xmlns:xlink=\"http://www.w3.org/1999/xlink\" contentStyleType=\"text/css\""
                + " height=\"102.8125px\" preserveAspectRatio=\"none\""
                + " style=\"width:83px;height:102px;background:#FFFFFF;\" version=\"1.1\""
                + " viewBox=\"0 0 83 102\" width=\"83.4375px\""
                + " zoomAndPan=\"magnify\"><defs/><g><!--class VolatileVersionsStrategy--><g"
                + " id=\"elem_VolatileVersionsStrategy\"><rect codeLine=\"9\" fill=\"#FFFFE0\""
                + " height=\"96.4844\" id=\"VolatileVersionsStrategy\" rx=\"0.7813\" ry=\"0.7813\""
                + " style=\"stroke:#000000;stroke-width:0.15625;\" width=\"76.875\" x=\"2.1875\""
                + " y=\"2.1875\"/><ellipse cx=\"13.625\" cy=\"7.1875\" fill=\"#ADD1B2\""
                + " rx=\"3.4375\" ry=\"3.4375\""
                + " style=\"stroke:#181818;stroke-width:0.3125;\"/><path d=\"M14.5527,8.9502"
                + " Q14.3721,9.043 14.1719,9.0869 Q13.9717,9.1357 13.752,9.1357 Q12.9707,9.1357"
                + " 12.5557,8.623 Q12.1455,8.1055 12.1455,7.1289 Q12.1455,6.1523 12.5557,5.6348"
                + " Q12.9707,5.1172 13.752,5.1172 Q13.9717,5.1172 14.1719,5.166 Q14.377,5.2148"
                + " 14.5527,5.3076 L14.5527,6.1572 Q14.3574,5.9766 14.1719,5.8936 Q13.9863,5.8057"
                + " 13.791,5.8057 Q13.3711,5.8057 13.1563,6.1426 Q12.9414,6.4746 12.9414,7.1289"
                + " Q12.9414,7.7832 13.1563,8.1201 Q13.3711,8.4521 13.791,8.4521 Q13.9863,8.4521"
                + " 14.1719,8.3691 Q14.3574,8.2813 14.5527,8.1006 L14.5527,8.9502 Z \""
                + " fill=\"#000000\"/><text fill=\"#000000\" font-family=\"sans-serif\""
                + " font-size=\"4.375\" lengthAdjust=\"spacing\" textLength=\"51.875\" x=\"19.5\""
                + " y=\"8.7021\">VolatileVersionsStrategy</text><line"
                + " style=\"stroke:#000000;stroke-width:0.15625;\" x1=\"2.5\" x2=\"78.75\""
                + " y1=\"12.1875\" y2=\"12.1875\"/><rect fill=\"none\" height=\"1.875\""
                + " style=\"stroke:#C82930;stroke-width:0.3125;\" width=\"1.875\" x=\"4.6875\""
                + " y=\"18.0615\"/><text fill=\"#000000\" font-family=\"sans-serif\""
                + " font-size=\"4.375\" lengthAdjust=\"spacing\" textLength=\"15.9375\""
                + " x=\"8.4375\" y=\"17.4985\">history:</text><text fill=\"#000000\""
                + " font-family=\"sans-serif\" font-size=\"4.375\" lengthAdjust=\"spacing\""
                + " textLength=\"43.75\" x=\"8.4375\" y=\"22.5912\">ArrayList[Document]</text><line"
                + " style=\"stroke:#000000;stroke-width:0.15625;\" x1=\"2.5\" x2=\"78.75\""
                + " y1=\"24.873\" y2=\"24.873\"/><ellipse cx=\"5.625\" cy=\"31.6846\""
                + " fill=\"#84BE84\" rx=\"0.9375\" ry=\"0.9375\""
                + " style=\"stroke:#038048;stroke-width:0.3125;\"/><text fill=\"#000000\""
                + " font-family=\"sans-serif\" font-size=\"4.375\" lengthAdjust=\"spacing\""
                + " textLength=\"56.5625\" x=\"8.4375\""
                + " y=\"30.184\">VolatileVersionsStrategy():</text><text fill=\"#000000\""
                + " font-family=\"sans-serif\" font-size=\"4.375\" lengthAdjust=\"spacing\""
                + " textLength=\"25.625\" x=\"8.4375\" y=\"35.2768\">Constructor</text><ellipse"
                + " cx=\"5.625\" cy=\"44.4165\" fill=\"#84BE84\" rx=\"0.9375\" ry=\"0.9375\""
                + " style=\"stroke:#038048;stroke-width:0.3125;\"/><text fill=\"#000000\""
                + " font-family=\"sans-serif\" font-size=\"4.375\" lengthAdjust=\"spacing\""
                + " textLength=\"47.8125\" x=\"8.4375\""
                + " y=\"40.3696\">putVersion(Document</text><text fill=\"#000000\""
                + " font-family=\"sans-serif\" font-size=\"4.375\" lengthAdjust=\"spacing\""
                + " textLength=\"25.3125\" x=\"8.4375\" y=\"45.4623\">document):</text><text"
                + " fill=\"#000000\" font-family=\"sans-serif\" font-size=\"4.375\""
                + " lengthAdjust=\"spacing\" textLength=\"8.75\" x=\"8.4375\""
                + " y=\"50.5551\">void</text><ellipse cx=\"5.625\" cy=\"57.1484\" fill=\"#84BE84\""
                + " rx=\"0.9375\" ry=\"0.9375\""
                + " style=\"stroke:#038048;stroke-width:0.3125;\"/><text fill=\"#000000\""
                + " font-family=\"sans-serif\" font-size=\"4.375\" lengthAdjust=\"spacing\""
                + " textLength=\"28.125\" x=\"8.4375\" y=\"55.6479\">getVersion():</text><text"
                + " fill=\"#000000\" font-family=\"sans-serif\" font-size=\"4.375\""
                + " lengthAdjust=\"spacing\" textLength=\"22.8125\" x=\"8.4375\""
                + " y=\"60.7407\">Document</text><ellipse cx=\"5.625\" cy=\"69.8804\""
                + " fill=\"#84BE84\" rx=\"0.9375\" ry=\"0.9375\""
                + " style=\"stroke:#038048;stroke-width:0.3125;\"/><text fill=\"#000000\""
                + " font-family=\"sans-serif\" font-size=\"4.375\" lengthAdjust=\"spacing\""
                + " textLength=\"68.75\" x=\"8.4375\""
                + " y=\"65.8334\">setEntireHistory(List[Document]</text><text fill=\"#000000\""
                + " font-family=\"sans-serif\" font-size=\"4.375\" lengthAdjust=\"spacing\""
                + " textLength=\"27.8125\" x=\"8.4375\" y=\"70.9262\">documents):</text><text"
                + " fill=\"#000000\" font-family=\"sans-serif\" font-size=\"4.375\""
                + " lengthAdjust=\"spacing\" textLength=\"8.75\" x=\"8.4375\""
                + " y=\"76.019\">void</text><ellipse cx=\"5.625\" cy=\"82.6123\" fill=\"#84BE84\""
                + " rx=\"0.9375\" ry=\"0.9375\""
                + " style=\"stroke:#038048;stroke-width:0.3125;\"/><text fill=\"#000000\""
                + " font-family=\"sans-serif\" font-size=\"4.375\" lengthAdjust=\"spacing\""
                + " textLength=\"39.0625\" x=\"8.4375\""
                + " y=\"81.1118\">getEntireHistory():</text><text fill=\"#000000\""
                + " font-family=\"sans-serif\" font-size=\"4.375\" lengthAdjust=\"spacing\""
                + " textLength=\"33.125\" x=\"8.4375\" y=\"86.2045\">List[Document]</text><ellipse"
                + " cx=\"5.625\" cy=\"92.7979\" fill=\"#84BE84\" rx=\"0.9375\" ry=\"0.9375\""
                + " style=\"stroke:#038048;stroke-width:0.3125;\"/><text fill=\"#000000\""
                + " font-family=\"sans-serif\" font-size=\"4.375\" lengthAdjust=\"spacing\""
                + " textLength=\"37.1875\" x=\"8.4375\" y=\"91.2973\">removeVersion():</text><text"
                + " fill=\"#000000\" font-family=\"sans-serif\" font-size=\"4.375\""
                + " lengthAdjust=\"spacing\" textLength=\"8.75\" x=\"8.4375\""
                + " y=\"96.3901\">void</text></g><!--SRC=[TLBBJW8n5DttA-vo2878G7pCIdbf4A8HmOLnKQRNeQ7JJcxR8HFZlriOGJIng_QyxcbQkv6wuCHp48M4xXcpHs1Bl7YHmgsXSmgdYbiB7mp2wlDqav7nMWoCCWHAPcjNeL9cM_E615BDhLGGxz4R8hCz1JyPgrENHd4d5IwHh3JQpXrnXraLnhNMqZf3LRApyseQJgz3a_eSjNjZpV-SKIE1GJWxyccmisRXtRScEjX1V6-2jZHIi6Q6Hr703ZhMjEX6sad2kxfCzBl6CSYU9lrs1EPlTyBSb7WoS6_zP72DMlXSCVQek7QBsHJAMWITTdmMTziNqPWap7W5dHWwLqdSJNen34RpPzZn3HRTFax16ayfWWXZIQwy2m4DDk4bXoSVkkMOm4E1UZAyFm0mqgKae_UtdYndFuBpNgil7SoHGXLOpjWGtxbNBZXI8wJE4bWyZrkNRCfrvdaMib6pGVWYRlTsY_aN]--></g></svg>";
    private final String svg;

    public SVGCell() {
        this(FOO);
    }

    public SVGCell(String svg) {
        this.svg = svg;
    }

    @Override
    public Region getGraphic(Graph graph) {
        try {
            String parser = XMLResourceDescriptor.getXMLParserClassName();
            SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);
            StringReader reader = new StringReader(svg);
            SVGDocument doc = f.createSVGDocument("file:make-something-up", reader);
            TranscoderInput transIn = new TranscoderInput(doc);

            BufferedImageTranscoder transcoder = new BufferedImageTranscoder();
            transcoder.transcode(transIn, null);
            Image img = SwingFXUtils.toFXImage(transcoder.getBufferedImage(), null);

            ImageView view = new ImageView(img);
            final Pane pane = new Pane(view);
            pane.setPrefSize(100, 100);

            return pane;
        } catch (TranscoderException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
