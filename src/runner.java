import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import main.java.com.PinyinException;
import main.java.com.PinyinFormat;
import main.java.com.PinyinHelper;

public class runner {
	
	public String transferLine(String line) {
		String result = "";
		try {
			result = PinyinHelper.convertToPinyinString(line, ",", PinyinFormat.WITH_TONE_MARK);
		} catch (PinyinException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public void transferFile(String input, String output) {
		try {
			InputStreamReader reader = new InputStreamReader(new FileInputStream(input), "utf-8");
			BufferedReader bufferedReader = new BufferedReader(reader);
			
			OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(output), "utf-8");
			BufferedWriter bufferedWriter = new BufferedWriter(writer);
			
			String line = bufferedReader.readLine();
			int num = 0;
			while (line != null) {
				String result = transferLine(line);
				bufferedWriter.write(result);
				bufferedWriter.write("\n");
				
				line = bufferedReader.readLine();
				
				if (num % 10000 == 0) {
					System.err.println("[INFO]: processed " + num + " lines");
				}
				num++;
			}
			bufferedReader.close();
			bufferedWriter.close();
			System.err.println("[INFO]: totally processed " + num + " lines");
		}
		catch (FileNotFoundException e) {
			// TODO: handle exception
			System.err.println("[ERROR]: cannot find the file, please check");
			e.printStackTrace();
		}
		catch (Exception e) {
			System.err.println("[ERROR]: there's something wrong, please check");
			e.printStackTrace();
		}
	}
	
	public static void demo(String args[]) throws Exception {
		String str = "你好世界";
		str = "好奇这个贫血淋淋的事实，哈哈";
		PinyinHelper.addMutilPinyinDict("./user.dict");
//		System.out.println(System.getProperty("user.dir"));
	    String x = PinyinHelper.convertToPinyinString(str, ",", PinyinFormat.WITH_TONE_MARK); // nǐ,hǎo,shì,jiè
	    System.out.println(x);
	    x = PinyinHelper.convertToPinyinString(str, ",", PinyinFormat.WITH_TONE_NUMBER); // ni3,hao3,shi4,jie4
	    System.out.println(x);
	    x = PinyinHelper.convertToPinyinString(str, ",", PinyinFormat.WITHOUT_TONE); // ni,hao,shi,jie
	    System.out.println(x);
	    x = PinyinHelper.getShortPinyin(str); // nhsj
	    System.out.println(x);
//	    PinyinHelper.addPinyinDict("user.dict");
	}
	
	public static void main(String[] args) throws Exception {
//		demo(args);
		
		String input = "input.txt";
		String output = "output.txt";
		
		runner run = new runner();
		run.transferFile(input, output);
		
		System.err.println("[INFO]: OVER");
		return;
	}

}
