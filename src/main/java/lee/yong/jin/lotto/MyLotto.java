package lee.yong.jin.lotto;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class MyLotto {
	
	static List<Map> dataList;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		dataList = loadExcel();
		/*for (Map data : dataList) {
			System.out.println(data);
		}*/

		//System.out.println(checkExistAll("2,13,34"));
		genNumber();

	}
	
	@SuppressWarnings("unchecked")
	static List<Map> loadExcel() {
		List<Map> dataList = new ArrayList<Map>();

		try {
			FileInputStream file = new FileInputStream("C:\\Users\\jnj45\\OneDrive\\문서\\lotto_excel.xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			int rowindex=0;
			int columnindex=0;
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
					System.out.println(data);
					dataList.add(data);
				}
			}

		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("총건수:" + dataList.size());
		return dataList;
	}
	
	static void genNumber() {
		System.out.println("=========================================================================");
		boolean isOk = true;
		/*
		//첫번째 번호
		int no1 = 0;
		while(isOk){
			no1 = RandomUtils.nextInt(1, 15);
			isOk = !checkExistRecent(1, no1);
		}
		System.out.println("no1:"+no1);

		//2번째번호
		isOk = true;
		int no2 = 0;
		while(isOk){
			no2 = RandomUtils.nextInt(no1+1, 25);
			isOk = !checkExistRecent(2, no2) && !checkExistAll(no1+","+no2);
		}
		System.out.println("no2:"+no2);
*/
		int no1 = 1; int no2 = 0;
		while(isOk){
			int a = RandomUtils.nextInt(1, 17);
			int b = RandomUtils.nextInt(1, 17);
			if (a < b){
				no1 = a; no2 = b;
			}else{
				no1 = b; no2 = a;
			}
			isOk = a==b;
		}
		System.out.println("no1:"+no1);
		System.out.println("no2:"+no2);

		//3번째번호
		isOk = true;
		int no3 = 0;
		while(isOk){
			no3 = RandomUtils.nextInt(no2+1, 30);
			isOk = checkExistAll(no1+","+no2+","+no3);
		}
		System.out.println("no3:"+no3);

		//4번째번호
		isOk = true;
		int no4 = 0;
		while(isOk){
			no4 = RandomUtils.nextInt(no3+1, 37);
			isOk = checkExistAll(no2+","+no3+","+no4);
		}
		System.out.println("no4:"+no4);

		//5번째번호
		isOk = true;
		int no5 = 0;
		while(isOk){
			no5 = RandomUtils.nextInt(no4+1, 45);
			isOk = checkExistAll(no3+","+no4+","+no5);
		}
		System.out.println("no5:"+no5);

		//6번째번호
		isOk = true;
		int no6 = 0;
		while(isOk){
			no6 = RandomUtils.nextInt(no5+1, 45);
			isOk = checkExistAll(no4+","+no5+","+no6);
		}
		System.out.println("no6:"+no6);
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
			if (dataStr.indexOf(str) > 0){
				return true;
			}
		}
		return false;
	}

}
/*

no1:4
no2:16
no3:19
no4:24
no5:29
no6:41

no1:15
no2:18
no3:30
no4:38
no5:42
no6:44

no1:6
no2:13
no3:15
no4:29
no5:33
no6:42

no1:9
no2:10
no3:23
no4:34
no5:38
no6:44

no1:6
no2:15
no3:20
no4:32
no5:34
no6:35
 * */
