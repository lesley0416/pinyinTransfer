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
	
	public String transferLine(String line, String mode) {
		String result = "";
		try {
			result = PinyinHelper.convertToPinyinString(line, "|", PinyinFormat.WITH_TONE_NUMBER, mode);
		} catch (PinyinException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public void transferFile(String input, String output, String mode) {
		try {
			InputStreamReader reader = new InputStreamReader(new FileInputStream(input), "utf-8");
			BufferedReader bufferedReader = new BufferedReader(reader);
			
			OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(output), "utf-8");
			BufferedWriter bufferedWriter = new BufferedWriter(writer);
			
			String line = bufferedReader.readLine();
			int num = 0;
			while (line != null) {
				String result = transferLine(line, mode);
//				///////////////////// 输出原文
//				bufferedWriter.write(line);
//				bufferedWriter.write("\n");
//				/////////////////////
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
	
	public static void demo(String args[], String mode) throws Exception {
		String str = "你好世界";
		str = "好奇这个贫血淋淋的事实，哈哈";
		str = "我好奇贫血好不好治疗。";
//		PinyinHelper.addMutilPinyinDict("./user.dict");
//	    PinyinHelper.addPinyinDict("user.dict");
//		System.out.println(System.getProperty("user.dir"));
	    String x = PinyinHelper.convertToPinyinString(str, "|", PinyinFormat.WITH_TONE_MARK, mode); // nǐ,hǎo,shì,jiè
	    System.out.println(x);
	    x = PinyinHelper.convertToPinyinString(str, "|", PinyinFormat.WITH_TONE_NUMBER, mode); // ni3,hao3,shi4,jie4
	    System.out.println(x);
	    x = PinyinHelper.convertToPinyinString(str, "|", PinyinFormat.WITHOUT_TONE, mode); // ni,hao,shi,jie
	    System.out.println(x);
	    x = PinyinHelper.getShortPinyin(str, mode); // nhsj
	    System.out.println(x);
	}
	
	public static void main(String[] args) throws Exception {
//		/////// demo
//		String mode = "";
//		demo(args, mode);
		
		/////// local file
		String input = "input.txt";
		String output = "output.txt";
		String mode = "name";
		//// 处理姓名发音时，有些字做“姓”和“名”有不同的发音，可以单独处理一下（可以在每个姓名前加个特殊符号，词典中multi_name中加上所有“特殊符号+在‘姓’有不同发音的音”
		
//		String input = "x";
//		String output = "x.out";
//		String mode = "";
		
		runner run = new runner();
		run.transferFile(input, output, mode);
		
		System.err.println("[INFO]: OVER");
		return;
	}

}
