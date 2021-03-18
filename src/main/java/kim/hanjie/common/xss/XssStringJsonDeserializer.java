package kim.hanjie.common.xss;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;

import java.io.IOException;

/**
 * @author han
 * @date 2020/11/7
 */
public class XssStringJsonDeserializer extends StringDeserializer {

    private static final long serialVersionUID = 4840922448901170695L;

    @Override
    public String deserialize(JsonParser p, DeserializationContext ctx) throws IOException {
        String text = super.deserialize(p, ctx);
        if (StringUtils.isNotEmpty(text)) {
            text = StringEscapeUtils.escapeHtml4(text);
        }
        return text;
    }
}
