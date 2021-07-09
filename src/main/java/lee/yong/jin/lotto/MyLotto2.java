package lee.yong.jin.lotto;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.StringUtil;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class MyLotto2 {
	
	@SuppressWarnings("rawtypes")
	static List<Map> dataList;
	
	public static void main(String[] args) {
		dataList = loadExcel();
		System.out.println("1=========================================================================");
		genNumber();
//		System.out.println("2=========================================================================");
//		genNumber();
//		System.out.println("3=========================================================================");
//		genNumber();
//		System.out.println("4=========================================================================");
//		genNumber();
//		System.out.println("5=========================================================================");
//		genNumber();
			
		//checkExistAll("31,33,42");//11,20,29,31,33,42
/*
7, 12, 24, 27, 37, 40
7, 10, 16, 19, 25, 37
5, 6, 16, 28, 41, 42
4, 8, 12, 22, 28, 37
8, 9, 14, 21, 39, 40
 */
	
		
	}
	
	static void genNumber() {
		boolean isContinue = true;
		
				
		while(isContinue){
			int[] arr = new int[6];
			
			arr[0] = getNextRandomNumber(arr);
			arr[1] = getNextRandomNumber(arr);
//			arr[0] = 4;
//			arr[1] = 44;
			arr[2] = getNextRandomNumber(arr);
			arr[3] = getNextRandomNumber(arr);
			arr[4] = getNextRandomNumber(arr);
			arr[5] = getNextRandomNumber(arr);
			
			Arrays.parallelSort(arr);
			System.out.println("================================");
			System.out.println("랜덤번호: "+Arrays.toString(arr));
			
			int MAX_CHECK_CNT = 108;
			int MAX_EXIST_CNT = 1;
			
			int maxCheckCnt = MAX_CHECK_CNT;
			int maxExistCnt = MAX_EXIST_CNT;
			if (checkExist(arr[0]+","+arr[1]+","+arr[2],maxCheckCnt,maxExistCnt)) {
				System.out.println(arr[0]+","+arr[1]+","+arr[2]+" exsit");
				continue;
			}
			
			maxCheckCnt = MAX_CHECK_CNT;
			maxExistCnt = MAX_EXIST_CNT;
			if (checkExist(arr[1]+","+arr[2]+","+arr[3],maxCheckCnt,maxExistCnt)) {
				System.out.println(arr[1]+","+arr[2]+","+arr[3]+" exsit");
				continue;
			}
			
			maxCheckCnt = MAX_CHECK_CNT;
			maxExistCnt = MAX_EXIST_CNT;
			if (checkExist(arr[2]+","+arr[3]+","+arr[4],maxCheckCnt,maxExistCnt)) {
				System.out.println(arr[2]+","+arr[3]+","+arr[4]+" exsit");
				continue;
			}
			
			maxCheckCnt = MAX_CHECK_CNT;
			maxExistCnt = MAX_EXIST_CNT;
			if (checkExist(arr[3]+","+arr[4]+","+arr[5],maxCheckCnt,maxExistCnt)) {
				System.out.println(arr[3]+","+arr[4]+","+arr[5]+" exsit");
				continue;
			}
			
						
			if (checkExistAll(arr[0]+","+arr[1]+","+arr[2]+","+arr[3])) {
				System.out.println(arr[0]+","+arr[1]+","+arr[2]+","+arr[3]+" exist");
				continue;
			}
			if (checkExistAll(arr[1]+","+arr[2]+","+arr[3]+","+arr[4])) {
				System.out.println(arr[1]+","+arr[2]+","+arr[3]+","+arr[4]+" exist");
				continue;
			}
			if (checkExistAll(arr[2]+","+arr[3]+","+arr[4]+","+arr[5])) {
				System.out.println(arr[2]+","+arr[3]+","+arr[4]+","+arr[5]+" exist");
				continue;
			}
			
			//중복4개 이상 
//			if (isCheckOverSameCnt(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], 4)) {
//				continue;
//			}
			
			isContinue = false;
		}
		System.out.println("all pass!");
		System.out.println("================================");
	}
	
	static int getNextRandomNumber(int[] arr) {
		int result = 0;
		while(true) {
			final int i = RandomUtils.nextInt(1, 45);
			//if (!Arrays.asList(arr).contains(i)) {
			if (!IntStream.of(arr).anyMatch(x -> x == i)) {
				result = i;
				break;
			}
		}
		return result;
	}

	static boolean checkExistRecent(int no, int src){
		int recentIndex = 10;
		for(int i=0; i < recentIndex; i++){
			Map data = dataList.get(i);
			if (src == MapUtils.getIntValue(data, "번호"+no)){
				return true;
			}
		}
		return false;
	}
	
	static boolean checkExistAll(String str){
		for (Map data: dataList) {
			String dataStr = ","+MapUtils.getString(data, "번호들")+",";
			if (dataStr.indexOf(StringUtils.replaceAll(str, " ", "")) > 0){
				System.out.println("exists===>"+MapUtils.getString(data, "회차")+"회차 : "+dataStr);
				return true;
			}
		}
		return false;
	}
	
	static boolean checkExist(String str, int maxCheckCnt, int maxExistCnt){
		System.out.println("checking nums = " + str);
		
		int checkedCnt = 0;
		int existCnt = 0;
		
		for (Map data: dataList) {
			checkedCnt++;
			//체크 회차 초과
			if (checkedCnt > maxCheckCnt) {
				System.out.println("maxCheckCnt over = " + checkedCnt);
				break;
			}
			//체크 회차 이내
			else {
				String dataStr = ","+MapUtils.getString(data, "번호들")+",";
				if (dataStr.indexOf(str) > 0){
					existCnt++;
				}
			}
		}
		
		//존재횟수 체크
		if (existCnt <= maxExistCnt) {
			System.out.println("existCnt = " + existCnt);
			return false;
		}else {
			System.out.println("existCnt over = " + existCnt);
			return true;
		}
	}
	
	static void checkExistAllList(String str){
		for (Map data: dataList) {
			String dataStr = ","+MapUtils.getString(data, "번호들")+",";
			if (dataStr.indexOf(StringUtils.replaceAll(str, " ", "")) > 0){
				System.out.println(str + " exists===>"+MapUtils.getString(data, "회차") + "회차:" + dataStr);
			}
		}
	}
	
	static void checkExistPer3(String allNo) {
		String[] allNos = StringUtils.split(allNo, ",");
		String step1 = StringUtils.replaceAll("," + allNos[0] + "," + allNos[1] + "," + allNos[2] + ",", " ","");
		checkExistAllList(step1);
		String step2 = StringUtils.replaceAll("," + allNos[1] + "," + allNos[2] + "," + allNos[3] + ",", " ","");
		checkExistAllList(step2);
		String step3 = StringUtils.replaceAll("," + allNos[2] + "," + allNos[3] + "," + allNos[4] + ",", " ","");
		checkExistAllList(step3);
		String step4 = StringUtils.replaceAll("," + allNos[3] + "," + allNos[4] + "," + allNos[5] + ",", " ","");
		checkExistAllList(step4);
	}
	
	static boolean isCheckOverSameCnt(int no1, int no2, int no3, int no4, int no5, int no6, int checkCnt) {
		boolean result = false;
		
		int existCnt = 0;
		for (Map data: dataList) {
			
			if ((no1+","+no2+","+no3+","+no4+","+no5+","+no6).equals(MapUtils.getString(data, "번호들"))) {
				continue;
			}
			
			String dataStr = ","+MapUtils.getString(data, "번호들")+",";
			
			if (dataStr.indexOf(","+no1+",") > -1){
				existCnt++;
			}
			if (dataStr.indexOf(","+no2+",") > -1){
				existCnt++;
			}
			if (dataStr.indexOf(","+no3+",") > -1){
				existCnt++;
			}
			if (dataStr.indexOf(","+no4+",") > -1){
				existCnt++;
			}
			if (dataStr.indexOf(","+no5+",") > -1){
				existCnt++;
			}
			if (dataStr.indexOf(","+no6+",") > -1){
				existCnt++;
			}
			//System.out.println(dataStr + " : 중복개수" + existCnt);
			if (existCnt>=checkCnt) {
				System.out.println("\r\n체크번호=["+no1+","+no2+","+no3+","+no4+","+no5+","+no6+"]"
						+ "중복회차="+MapUtils.getString(data, "회차") + ", 중복수: "+existCnt+",중복번호=" + MapUtils.getString(data, "번호들"));
				result = true;
				break;
			}
			existCnt = 0;
		}
		return result;
	}
	
	static String checkThreeSame(int no1, int no2, int no3, int no4, int no5, int no6) {
		String result = "";
		
		int existCnt = 0;
		for (Map data: dataList) {
			
			if ((no1+","+no2+","+no3+","+no4+","+no5+","+no6).equals(MapUtils.getString(data, "번호들"))) {
				continue;
			}
			
			String dataStr = ","+MapUtils.getString(data, "번호들")+",";
			
			if (dataStr.indexOf(","+no1+",") > 0){
				existCnt++;
			}
			if (dataStr.indexOf(","+no2+",") > 0){
				existCnt++;
			}
			if (dataStr.indexOf(","+no3+",") > 0){
				existCnt++;
			}
			if (dataStr.indexOf(","+no4+",") > 0){
				existCnt++;
			}
			if (dataStr.indexOf(","+no5+",") > 0){
				existCnt++;
			}
			if (dataStr.indexOf(","+no6+",") > 0){
				existCnt++;
			}
			//System.out.println(dataStr + " : 중복개수" + existCnt);
			if (existCnt>=3) {
				result += "\r\n체크번호=["+no1+","+no2+","+no3+","+no4+","+no5+","+no6+"]"
						+ "중복회차="+MapUtils.getString(data, "회차") + ", 중복수: "+existCnt+",중복번호=" + MapUtils.getString(data, "번호들");
				
			}
			existCnt = 0;
		}
		return result;
	}
	
	//문서 읽기
	@SuppressWarnings({ "unchecked", "rawtypes", "resource" })
	static List<Map> loadExcel() {
		List<Map> dataList = new ArrayList<Map>();

		try {
			FileInputStream file = new FileInputStream("C:\\Users\\jnj45\\OneDrive\\문서\\lotto_excel_3.xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			int rowindex=0;
			//시트 수 (첫번째에만 존재하므로 0을 준다)
			//만약 각 시트를 읽기위해서는 FOR문을 한번더 돌려준다
			XSSFSheet sheet=workbook.getSheetAt(0);
			//행의 수
			int rows=sheet.getPhysicalNumberOfRows();

			for(rowindex=3;rowindex<rows;rowindex++){
				//행을읽는다
				XSSFRow row=sheet.getRow(rowindex);
				if(row !=null){
					Map data = new HashMap();
					data.put("회차",  (int)row.getCell(1).getNumericCellValue());
					data.put("번호1", (int)row.getCell(13).getNumericCellValue());
					data.put("번호2", (int)row.getCell(14).getNumericCellValue());
					data.put("번호3", (int)row.getCell(15).getNumericCellValue());
					data.put("번호4", (int)row.getCell(16).getNumericCellValue());
					data.put("번호5", (int)row.getCell(17).getNumericCellValue());
					data.put("번호6", (int)row.getCell(18).getNumericCellValue());

					data.put("번호들",   MapUtils.getIntValue(data, "번호1") + "," +
										MapUtils.getIntValue(data, "번호2") + "," +
										MapUtils.getIntValue(data, "번호3") + "," +
										MapUtils.getIntValue(data, "번호4") + "," +
										MapUtils.getIntValue(data, "번호5") + "," +
										MapUtils.getIntValue(data, "번호6"));
					data.put("총합", MapUtils.getIntValue(data, "번호1") +
									MapUtils.getIntValue(data, "번호2") +
									MapUtils.getIntValue(data, "번호3") +
									MapUtils.getIntValue(data, "번호4") +
									MapUtils.getIntValue(data, "번호5") +
									MapUtils.getIntValue(data, "번호6"));
					//System.out.println(data);
					dataList.add(data);
				}
			}

		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("총건수:" + dataList.size());
		return dataList;
	}
	
}

