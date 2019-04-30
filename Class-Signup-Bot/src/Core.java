
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.JSONObject;
import static java.lang.System.out;

public class Core {
	static URL url;
	
	public Core ()
	{
		try {
			url = new URL("https://www.clark.edu/current_students/body.php");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void init(String sid, String pin) throws IOException
	{
		
	    String postData = buildPost("sid",new String(sid),"pin",new String(pin));
	    System.out.println(postData);
	}
	
	public String buildPost(String... args)
	{
		String retVal = null;
		int i = 0;
		
		for (String arg: args) //count how much stuff was passed in
		{
			i++;
		}
		
		if (i%2 != 0)
			return retVal; //return nothing if we dont get an even count of args (name and val)
		
		for (int x = 0; x < i; x+=2)
		{
			if (x == 0)
				retVal = "" +args[x] + "=" + args[x+1];
			else if (x%2 == 0)
				retVal += '&' + args[x] + "=" + args[x+1];
		}
		return retVal;
	}
}
