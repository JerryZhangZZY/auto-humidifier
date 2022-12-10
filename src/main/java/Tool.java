import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tool {

    private static final String IP = "10.0.0.49";
    private static final String TOKEN = "8e601dacb8aca289db4a2686a2774587";

    private static final String PRE = "miiocli device --ip " + IP + " --token " + TOKEN + " raw_command ";
    private static final String SET_ON = "set_properties \"[{'did':'412498237','value':True,'siid':2,'piid':1}]\"";
    private static final String SET_1 = "set_properties \"[{'did':'412498237','value':1,'siid':2,'piid':5}]\"";
    private static final String SET_2 = "set_properties \"[{'did':'412498237','value':2,'siid':2,'piid':5}]\"";
    private static final String SET_3 = "set_properties \"[{'did':'412498237','value':3,'siid':2,'piid':5}]\"";
    private static final String SET_OFF = "set_properties \"[{'did':'412498237','value':False,'siid':2,'piid':1}]\"";
    private static final String[] SET_FAN = {SET_OFF, SET_1, SET_2, SET_3};
    private static final String GET_STATUS = "get_properties \"[{'did':'412498237','siid':2,'piid':1},{'did':'412498237','siid':2,'piid':5},{'did':'412498237','siid':3,'piid':1}]\"";

    public static ArrayList<Object> getStatus() {
        ArrayList<Object> status = new ArrayList<>();
        String cmd = PRE + GET_STATUS;
        String out = execWithOutput(cmd);
        if (out != null) {
            try {
                Pattern pattern = Pattern.compile("(?<='value': ).\\w*");
                Matcher matcher = pattern.matcher(out);
                if (matcher.find())
                    status.add(Boolean.parseBoolean(matcher.group()));
                if (matcher.find()) {
                    status.add(Integer.parseInt(matcher.group()));
                }
                if (matcher.find())
                    status.add(Integer.parseInt(matcher.group()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (status.size() == 3)
            return status;
        return null;
    }

    public static void setOn() {
        String cmd = PRE + SET_ON;
        exec(cmd);
    }

    public static void setOff() {
        String cmd = PRE + SET_FAN[0];
        exec(cmd);
    }

    public static void setFan(int level) {
        String cmd = PRE + SET_FAN[level];
        exec(cmd);
    }

    public static String execWithOutput(String cmd) {
        try {
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec(cmd);
            String outStr = getStreamStr(process.getInputStream());
            process.destroy();
            return outStr;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void exec(String cmd) {
        try {
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec(cmd);
            String outStr = getStreamStr(process.getInputStream());
            process.destroy();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getStreamStr(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "GBK"));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
            sb.append("\n");
        }
        br.close();
        return sb.toString();
    }
}