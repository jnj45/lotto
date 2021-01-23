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
		System.out.println("2=========================================================================");
		genNumber();
		System.out.println("3=========================================================================");
		genNumber();
		System.out.println("4=========================================================================");
		genNumber();
//		System.out.println("5=========================================================================");
//		genNumber();
		
		/*
		checkExistAllList("1,8,13");
		checkExistAllList("8,13,36");
		checkExistAllList("13,36,44");
		checkExistAllList("36,44,45");
		
		checkExistAllList("1,8,13,36"); //1,8,13,36,44,45
		checkExistAllList("8,13,36,44"); //10,12,18,35,42,43
		checkExistAllList("13,36,44,45"); //10,12,18,35,42,43
*/
		
		/*
		[4, 5, 7, 31, 39, 44]
		[2, 3, 20, 26, 31, 41]
		[4, 9, 14, 33, 34, 41]
		[1, 6, 16, 34, 38, 44]
		[1, 13, 28, 30, 40, 43]
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
			System.out.println(Arrays.toString(arr));
			
			int maxCheckCnt = 54;
			int maxExistCnt = 1;
			
			if (checkExist(arr[0]+","+arr[1]+","+arr[2],maxCheckCnt,maxExistCnt)) {
				System.out.println(arr[0]+","+arr[1]+","+arr[2]+" exsit");
				continue;
			}
			if (checkExist(arr[1]+","+arr[2]+","+arr[3],maxCheckCnt,maxExistCnt)) {
				System.out.println(arr[1]+","+arr[2]+","+arr[3]+" exsit");
				continue;
			}
			if (checkExist(arr[2]+","+arr[3]+","+arr[4],maxCheckCnt,maxExistCnt)) {
				System.out.println(arr[2]+","+arr[3]+","+arr[4]+" exsit");
				continue;
			}
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
			
			isContinue = false;
		}
		System.out.println("end!");
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
	
	static boolean checkExistRecent12(int no1, int no2, int cnt){
		for(int i=0; i < cnt; i++){
			Map data = dataList.get(i);
			if ((no1+","+no2).equals(MapUtils.getIntValue(data, "번호1")+","+MapUtils.getIntValue(data, "번호2"))){
				return true;
			}
		}
		return false;
	}

	static boolean checkExistAll(String str){
		for (Map data: dataList) {
			String dataStr = ","+MapUtils.getString(data, "번호들")+",";
			if (dataStr.indexOf(str) > 0){
				System.out.println("exists===>"+dataStr);
				return true;
			}
		}
		return false;
	}
	//[6, 16, 17, 25, 31, 33]
	//[4, 5, 7, 21, 26, 39]
	//[3, 4, 12, 15, 23, 29]
	//[5, 9, 15, 20, 38, 41]
	//[2, 25, 31, 35, 39, 41]
	static boolean checkExist(String str, int maxCheckCnt, int maxExistCnt){
		int checkedCnt = 0;
		int existCnt = 0;
		
		for (Map data: dataList) {
			checkedCnt++;
			String dataStr = ","+MapUtils.getString(data, "번호들")+",";
			if (dataStr.indexOf(str) > 0){
				existCnt++;
				if (checkedCnt < maxCheckCnt) {
					System.out.println("checkdCnt["+checkedCnt+"] exists===>"+dataStr);
					return true;
				}else {
					if (existCnt > maxExistCnt) {
						System.out.println("existCnt["+existCnt+"] exists===>"+dataStr);
						return true;
					}
				}
			}
		}
		return false;
	}
	
	static void checkExistAllList(String str){
		for (Map data: dataList) {
			String dataStr = ","+MapUtils.getString(data, "번호들")+",";
			if (dataStr.indexOf(str) > 0){
				System.out.println(str + " exists===>"+MapUtils.getString(data, "회차") + "회차:" + dataStr);
			}
		}
	}
	
	static String checkSame(int no1, int no2, int no3, int no4, int no5, int no6, int checkCnt) {
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
			if (existCnt>=checkCnt) {
				result += "\r\n체크번호=["+no1+","+no2+","+no3+","+no4+","+no5+","+no6+"]"
						+ "중복회차="+MapUtils.getString(data, "회차") + ", 중복번호=" + MapUtils.getString(data, "번호들");
				
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
						+ "중복회차="+MapUtils.getString(data, "회차") + ", 중복번호=" + MapUtils.getString(data, "번호들");
				
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

