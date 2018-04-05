import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.StringTokenizer;
import java.net.HttpURLConnection;

public class Filter {

    private BloomFilter<String> filter;

    public Filter() {
        // Create filter
        // http://www.baeldung.com/guava-bloom-filter
        Charset charset = Charset.defaultCharset();
        filter = BloomFilter.create(
                Funnels.stringFunnel(charset),
                500,
                0.01
        );
    }

    public JSONObject storeURL(String s) throws IOException {
        // CURL webpage and store contents in bloom storeURL

        String myURL = s;
        if(s.contains("&")) {
            myURL = s.substring(0, s.indexOf("&"));
        }
        URL url = new URL(myURL);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
            for (String line; (line = reader.readLine()) != null;) {
                // TODO String tokenizer - tokenize at what level? line by line? currently word by word
                // https://www.cis.upenn.edu/~bcpierce/courses/629/jdkdocs/api/java.util.StringTokenizer.html
                StringTokenizer st = new StringTokenizer(line);
                while (st.hasMoreTokens()) {
                    filter.put(st.nextToken());
                }
            }
        }

        // Build JSON response
        // TODO real values
        // TODO refactor to separate class
        JSONObject response = new JSONObject();
        response.put("estimatedWordCount", 0);
        response.put("targetWordCount", 0);
        response.put("expectedFalsePositiveProbability", 0);
        response.put("targetFalsePositiveProbability", 0);
        JSONObject obj = new JSONObject();
        obj.put("url", myURL);
        obj.put("bloomFilter", response);

        // Return JSON response
        return obj;
    }

    public JSONObject queryFilter(String url, String query) throws IOException {

        // Build JSON response
        // TODO real values
        JSONObject results = new JSONObject();
        results.put("estimatedWordCount", 0);
        results.put("targetWordCount", 0);
        results.put("expectedFalsePositiveProbability", 0);
        results.put("targetFalsePositiveProbability", 0);
        JSONObject obj = new JSONObject();
        obj.put("url", url);
        obj.put("word", query);
        obj.put("mightContain", filter.mightContain(query));
        obj.put("bloomFilter", results);

        // Return JSON response
        return obj;
    }
}
