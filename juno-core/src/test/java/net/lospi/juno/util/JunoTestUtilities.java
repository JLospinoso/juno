

package net.lospi.juno.util;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class JunoTestUtilities {
    public static List<String> resource(String fileName) throws FileNotFoundException {
        InputStream fileAsStream = JunoTestUtilities.class.getClassLoader().getResourceAsStream(fileName);
        if (fileAsStream == null) {
            throw new FileNotFoundException("Cannot find file " + fileName);
        }
        BufferedReader br = null;
        List<String> result = new LinkedList<String>();
        String line;
        try {
            br = new BufferedReader(new InputStreamReader(fileAsStream));
            while ((line = br.readLine()) != null) {
                result.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}
